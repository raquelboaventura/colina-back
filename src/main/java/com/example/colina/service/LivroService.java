package com.example.colina.service;

import com.example.colina.DTO.LivroDTO;
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
        try{
            log.info("Livro DTO: {} ", livroDTO);
            Livro livro = new Livro();
            BeanUtils.copyProperties(livroDTO, livro);
            log.info("Livro Entidade: {}", livro);
            log.info("Cópia dos dados para a entidade realizada");
            livroRepository.save(livro);
            log.info("Livro cadastrado com sucesso: {} ", livro.getTitulo());
        }catch(Exception e){
            log.error(e.getMessage());

            log.error("Erro ao cadastrar livro");
        }
        log.info("Saindo do serviço de cadastro de livros");
    }

    public List<LivroDTO> listaLivros() {
        List<Livro> livros;
        List<LivroDTO> livrosDTO;
        try{
            log.info("Entrando no service de listagem de livros");
            log.info("Chamando o repository");
            livros = livroRepository.findAll();
            log.info("Lista de livros: {}",livros);
            livrosDTO = converteListaEmDTO(livros);
            log.info("Lista de livros DTO: {}", livrosDTO);
            return livrosDTO;
        }catch(Exception e){
            log.error(e.getMessage());
            log.error("Erro ao listar os livros!");
        }
        return null;
    }

    public void atualizaLivro(LivroDTO livroDTO, Long id) {

        log.info("Entrando no serviço de atualização");
        try {
            log.info("livroDTO: {} ",livroDTO);
            log.info("Gravando os dados no banco de dados...");
            livroRepository.findById(id).
                    map(livro -> {
                        livro.setPreco(livroDTO.getPreco());
                        livro.setQuantidade(livroDTO.getQuantidade());
                        livro.setIsbn(livroDTO.getIsbn());
                        livro.setTitulo(livroDTO.getTitulo());
                        log.info("Livro atualizado, salvando no banco de dados...");
                        return livroRepository.save(livro);
                    })
                    .orElseGet(() -> {
                                log.info("Valores nulos? Entrou no Else Get!");
                                livroDTO.setId(id);
                                Livro livro = new Livro();
                                BeanUtils.copyProperties(livroDTO, livro);
                                return livroRepository.save(livro);
                            }
                    );
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }

    }

    public List<LivroDTO> converteListaEmDTO(List<Livro> livros){
        List<LivroDTO> livrosDTO = new ArrayList<>();
        log.info("Entrando no método de conversão");
        livrosDTO = objectMapper.convertValue(livros, livrosDTO.getClass());
        log.info("Teste: {}", livrosDTO);
        return livrosDTO;
    }

    public LivroDTO converteListaEmDTO(Livro livros){
        LivroDTO livrosDTO = new LivroDTO();
        log.info("Entrando no método de conversão");
        livrosDTO = objectMapper.convertValue(livros, livrosDTO.getClass());
        log.info("Teste: {}", livrosDTO);
        return livrosDTO;
    }


    public void excluiLivro(Long id) {
        log.info("Entrando no serviço de exclusão de livro");
        try{
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
        }
        catch(Exception ex){
            log.error("Erro ao excluir: {} ", ex.getMessage());
        }
    }
}
