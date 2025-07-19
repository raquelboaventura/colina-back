package com.example.colina.service;

import com.example.colina.Dto.EmprestimoDTO;
import com.example.colina.entity.Cliente;
import com.example.colina.entity.Emprestimo;
import com.example.colina.entity.Livro;
import com.example.colina.enums.StatusEmprestimo;
import com.example.colina.exceptions.LimiteEmprestimosExcedidoException;
import com.example.colina.exceptions.LivroIndisponivelException;
import com.example.colina.exceptions.LivroNaoEncontradoException;
import com.example.colina.repository.ClienteRepository;
import com.example.colina.repository.EmprestimoRepository;
import com.example.colina.repository.LivroRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;

    private final ClienteRepository clienteRepository;
    private final LivroRepository livroRepository;
    private final LivroService livroService;

    @Autowired
    public EmprestimoService(
            ClienteRepository clienteRepository,
            LivroRepository livroRepository,
            EmprestimoRepository emprestimoRepository,
            LivroService livroService) {
        this.clienteRepository = clienteRepository;
        this.livroRepository = livroRepository;
        this.emprestimoRepository = emprestimoRepository;
        this.livroService = livroService;
    }

    /**
     * Cadastra um novo empréstimo com múltiplos livros
     */
    public EmprestimoDTO cadastrarEmprestimo(EmprestimoDTO dto)
            throws LivroIndisponivelException,
            LimiteEmprestimosExcedidoException {
        // Log inicial com informações do DTO recebido
        try{
            log.info("Iniciando cadastro de empréstimo - DTO recebido: {}", dto);

            List<Livro> livros = dto.getLivros().stream()
                    .map(l -> livroRepository.findById(l.getId_livro())
                            .orElseThrow(() -> new RuntimeException("Livro não encontrado: " + l.getId_livro())))
                    .collect(Collectors.toList());


            // Log da validação do cliente
            log.debug("Validando cliente com ID: {}", dto.getCliente().getId_cliente());
            Cliente cliente = validarCliente(dto.getCliente().getId_cliente());
            log.debug("Cliente validado: {}", cliente);

            // Log da validação dos livros
            log.debug("Validando {} livros", livros.size());
            validarDisponibilidadeLivros(livros);
            log.debug("Todos os livros foram validados com sucesso");

            // Log da validação do limite
            log.debug("Validando limite de empréstimos do cliente");
            validarLimiteEmprestimos(cliente);
            log.debug("Limite de empréstimos validado");

            // Log da criação do empréstimo
            log.debug("Criando novo empréstimo");
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setCliente(cliente);
            emprestimo.setLivros(livros);
            emprestimo.setDataEmprestimo(LocalDate.now());
            emprestimo.setDataDevolucaoPrevista(this.getDataDevolucaoPrevista());
            emprestimo.setStatus(StatusEmprestimo.ATIVO);
            log.debug("Emprestimo criado: {}", emprestimo);

            // Log da persistência
            log.info("Persistindo empréstimo no banco de dados");
            emprestimoRepository.save(emprestimo);
            log.info("Emprestimo persistido com sucesso");

            // Log da conversão e retorno
            log.debug("Convertendo empréstimo para DTO");
            EmprestimoDTO dtoRetorno = converterParaDTO(emprestimo);
            log.info("Cadastro de empréstimo concluído com sucesso");

            return dtoRetorno;
        }
        catch(Exception e){
            log.error("Erro ao cadastrar emprestimo: {}", e);
            throw e;
        }
    }

    private LocalDate getDataDevolucaoPrevista() {
        return LocalDate.now().plusDays(7);
    }

    private Cliente validarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow();
    }

    private List<Livro> validarLivros(List<Long> livrosIds) {
        return livrosIds.stream()
                .map(id -> livroRepository.findById(id)
                        .orElseThrow())
                .collect(Collectors.toList());
    }

    private void validarLimiteEmprestimos(Cliente cliente) throws LimiteEmprestimosExcedidoException {
        int quantidadeEmprestimosAtivos = (int) emprestimoRepository.contarEmprestimosPorCliente(cliente.getId_cliente());

        if (quantidadeEmprestimosAtivos >= 5) {
            throw new LimiteEmprestimosExcedidoException(
                    "Cliente já atingiu o limite máximo de empréstimos");
        }
    }

    private void validarDisponibilidadeLivros(List<Livro> livros) throws LivroIndisponivelException {
        for (Livro livro : livros) {
            if (livro.isStatus()) continue;
            throw new LivroIndisponivelException(
                    "O livro '" + livro.getTitulo() + "' não está disponível");
        }
    }

    private EmprestimoDTO converterParaDTO(Emprestimo emprestimo) {
        EmprestimoDTO dto = new EmprestimoDTO();
        dto.setId_emprestimo(emprestimo.getId_emprestimo());
        dto.setCliente(emprestimo.getCliente());
        dto.setLivros(emprestimo.getLivros());
        dto.setDataEmprestimo(emprestimo.getDataEmprestimo());
        dto.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
        dto.setStatus(emprestimo.getStatus());
        return dto;
    }

    public EmprestimoDTO buscarEmprestimoPorId(Long id) {
    try {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com id: " + id));
        return converterParaDTO(emprestimo);
    } catch (Exception e) {
        log.error("Erro ao buscar empréstimo: {}", e.getMessage());
        throw e;
    }
}

public EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO dto) {
    try {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com id: " + id));

        // Atualiza cliente se informado
        if (dto.getCliente() != null && dto.getCliente().getId_cliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getCliente().getId_cliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + dto.getCliente().getId_cliente()));
            emprestimo.setCliente(cliente);
        }

        // Atualiza datas, se vierem no DTO
        if (dto.getDataEmprestimo() != null) {
            emprestimo.setDataEmprestimo(dto.getDataEmprestimo());
        }

        if (dto.getDataDevolucaoPrevista() != null) {
            emprestimo.setDataDevolucaoPrevista(dto.getDataDevolucaoPrevista());
        }

        if (dto.getDataDevolucaoReal() != null) {
            emprestimo.setDataDevolucaoReal(dto.getDataDevolucaoReal());
        }

        // Atualiza status, se informado
        if (dto.getStatus() != null) {
            emprestimo.setStatus(dto.getStatus());
        }

        // Atualiza livros, se informado
        if (dto.getLivros() != null && !dto.getLivros().isEmpty()) {
            List<Livro> livros = dto.getLivros().stream()
                    .map(l -> {
                        try {
                            return livroRepository.findById(l.getId_livro())
                                    .orElseThrow(() -> new LivroNaoEncontradoException("Livro não encontrado com id: " + l.getId_livro()));
                        } catch (LivroNaoEncontradoException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return l;
                    })
                    .collect(Collectors.toList());

            // Validar disponibilidade dos livros
            validarDisponibilidadeLivros(livros);

            emprestimo.setLivros(livros);
        }

        // Validar limite do cliente caso cliente tenha sido alterado ou livros atualizados
        validarLimiteEmprestimos(emprestimo.getCliente());

        Emprestimo salvo = emprestimoRepository.save(emprestimo);
        return converterParaDTO(salvo);

    } catch (LivroIndisponivelException | LimiteEmprestimosExcedidoException e) {
        log.error("Erro de negócio ao atualizar empréstimo: {}", e.getMessage());
    } catch (Exception e) {
        log.error("Erro ao atualizar empréstimo: {}", e.getMessage());
        throw e;
    }
    return dto;
}

public EmprestimoDTO deleteLogico(Long id) {
    try {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado com id: " + id));
        
        emprestimo.setStatus(StatusEmprestimo.CANCELADO); // Ou o status usado para delete lógico

        Emprestimo salvo = emprestimoRepository.save(emprestimo);
        return converterParaDTO(salvo);

    } catch (Exception e) {
        log.error("Erro ao realizar delete lógico no empréstimo: {}", e.getMessage());
        throw e;
        }
    }
}
