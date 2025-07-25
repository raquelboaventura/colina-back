package com.example.colina.service;

import com.example.colina.Dto.LivroDTO;
import com.example.colina.entity.Livro;
import com.example.colina.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class LivroService {
    @Autowired

    LivroRepository livroRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void cadastraLivro(LivroDTO livroDTO) {
        log.info("Entrando no service de cadastro de livro");
        try {
            log.info("Livro DTO: {} ", livroDTO);
            Livro livro = new Livro();
            BeanUtils.copyProperties(livroDTO, livro);
            log.info("Livro Entidade: {}", livro);
            log.info("Cópia dos dados para a entidade realizada");
            livroRepository.save(livro);
            log.info("Livro cadastrado com sucesso: {} ", livro.getTitulo());
        } catch (Exception e) {
            log.error(e.getMessage());

            log.error("Erro ao cadastrar livro");
        }
        log.info("Saindo do serviço de cadastro de livros");
    }

    public List<LivroDTO> listaLivros() {
        List<Livro> livros;
        List<LivroDTO> livrosDTO;
        try {
            log.info("Entrando no service de listagem de livros");
            log.info("Chamando o repository");
            livros = livroRepository.findAll();
            log.info("Lista de livros: {}", livros);
            livrosDTO = converteListaEmDTO(livros);
            log.info("Lista de livros DTO: {}", livrosDTO);
            return livrosDTO;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Erro ao listar os livros!");
        }
        return null;
    }

    public LivroDTO listaLivroPorId(Long id) {
        try{
            LivroDTO livroDTO;
            log.info("Entrando no servico de listagem por ID");
            Optional<Livro> livroRetornado = livroRepository.findById(id);
            log.info("Retornado livro via bd: {}", livroRetornado);
            livroDTO = converteLivroEmDTO(livroRetornado);
            log.info("Livro DTO: {}", livroDTO);
            return livroDTO;
        }catch(Exception ex){
            log.error("Ocorreu um erro ao buscar livro: {}", ex.getMessage());
        }
        return new LivroDTO();
    }


    public boolean atualizaLivro(LivroDTO livroDTO, Long id) {

        log.info("Entrando no serviço de atualização");
        try {
            log.info("livroDTO: {} ", livroDTO);
            log.info("Gravando os dados no banco de dados...");
            Optional<Livro> id_existe = livroRepository.findById(id);
            if (id_existe.isPresent()) {
                log.info("o ID existe: {}", id_existe);
                Livro livroExistente = id_existe.get();
                if (id_existe.get().getIsbn().equals(livroDTO.getIsbn())) {
                    log.info("ISBN válido, atualizando o livro...");

                    // Atualiza os campos necessários
                    if (livroDTO.getTitulo() != null) {
                        livroExistente.setTitulo(livroDTO.getTitulo());
                    }
                    if (livroDTO.getAutor() != null) {
                        livroExistente.setAutor(livroDTO.getAutor());
                    }
                    if (livroDTO.getEditora() != null) {
                        livroExistente.setEditora(livroDTO.getEditora());
                    }
                    if (livroDTO.getPaginas() != 0) {
                        livroExistente.setPaginas(livroDTO.getPaginas());
                    }
                    if (livroDTO.getPublicacao() != null) {
                        livroExistente.setPublicacao(livroDTO.getPublicacao());
                    }
                    if (livroDTO.getPreco() != 0) {
                        livroExistente.setPreco(livroDTO.getPreco());
                    }
                    if (livroDTO.getGenero() != null) {
                        livroExistente.setGenero(livroDTO.getGenero());
                    }
                    if (livroDTO.getQuantidade() > 0) {
                        livroExistente.setQuantidade(livroDTO.getQuantidade());
                    } else if (livroDTO.getQuantidade() == 0) {
                    livroExistente.setQuantidade(0);
                    livroExistente.setStatus(false); // Inativa o livro
                }
                log.info("Livro DTO: {}", livroDTO);
                log.info("Livro atualizado, salvando no banco de dados...{}", livroExistente);
                livroRepository.save(livroExistente);
                return true;
            } else {
                log.warn("O ISBN fornecido ({}) não corresponde ao livro encontrado ({}). Atualização cancelada.", livroDTO.getIsbn(), livroExistente.getIsbn());
                return false;
            }
        } else {
            log.info(" o ID não existe: {}", id_existe);
        }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return false;
    }

    public List<LivroDTO> converteListaEmDTO(List<Livro> livros) {
        List<LivroDTO> livrosDTO = new ArrayList<>();
        log.info("Entrando no método de conversão");
        livrosDTO = objectMapper.convertValue(livros, livrosDTO.getClass());
        log.info("Teste: {}", livrosDTO);
        return livrosDTO;
    }

    public LivroDTO converteLivroEmDTO(Optional<Livro> livros) {
        LivroDTO livrosDTO = new LivroDTO();
        log.info("Entrando no método de conversão");
        livrosDTO = objectMapper.convertValue(livros, livrosDTO.getClass());
        log.info("Teste: {}", livrosDTO);
        return livrosDTO;
    }

    public void excluiLivro(Long id) {
        log.info("Entrando no serviço de exclusão de livro");
        try {
            livroRepository.findById(id)
                    .map(livro -> {
                        log.info("Valor do campo status retornado = {}", livro.isStatus());
                        if (livro.isStatus()) {
                            log.info("Livro está com status = true");
                            livro.setStatus(false);
                            log.info("Status alterado para false");
                            livroRepository.save(livro);
                        }
                        return "deu certo";
                    });
        } catch (Exception ex) {
            log.error("Erro ao excluir: {} ", ex.getMessage());
        }
    }

    public void excluiLivroDefinitivo(Long id) {
        log.info("Entrando no serviço de exclusão de livro");
        try {
            livroRepository.deleteById(id);
            log.info("Livro excluído com sucesso");
        } catch (Exception ex) {
            log.error("Erro ao excluir: {} ", ex.getMessage());
        }
    }
}