package com.example.colina.controller;

import com.example.colina.DTO.LivroDTO;
import com.example.colina.service.LivroService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/livros")
@Log4j2

public class LivroController {
    @Autowired
    private LivroService livroService;

    private LivroDTO livroDTO;

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<LivroDTO> cadastraLivro(@RequestBody LivroDTO livroDTO){
        try{
            log.info("Entrando no controller de Cadastro de Livros");
            log.info("Livro DTO: {}", livroDTO);
            log.info("Chamando o serviço de cadastro de livros");
            livroService.cadastraLivro(livroDTO);
            log.info("Saindo do controller de cadastro");
            return ResponseEntity.status(201).body(livroDTO);
        }
        catch(Exception e){
            log.error(e.getMessage());
            log.error("Houve um erro ao cadastrar o livro!");
        }
        return null;
    }

    @GetMapping("lista/all")
    public ResponseEntity<List<LivroDTO>> listaLivros(){
    List<LivroDTO> listaLivros;
    try{
        log.info("Entrando no controller de listagem de livros");
        log.info("Chamando o serviço de listagem");
        listaLivros = livroService.listaLivros();
        return ResponseEntity.status(200).body(listaLivros);
    }
    catch(Exception e)
    {
        log.error(e.getMessage());
        log.error("Erro ao listar livros");
    }
    return null;
    }

    @PutMapping("atualiza/{id}")
    public ResponseEntity<LivroDTO> atualizaLivro(@PathVariable Long id, @RequestBody LivroDTO livroDTO ){
        log.info("Entrando no controller de atualização de dados");
        try {
            log.info("Chamando serviço de atualização");
            livroService.atualizaLivro(livroDTO, id);
            log.info("Voltando ao controller");
        }catch (Exception ex){
            log.error("Erro ao atualizar os dados do livro. {}", ex.getMessage());
        }
        return ResponseEntity.status(200).body(livroDTO);
    }

    @PutMapping("delete/{id}")
    public void excluiLivro(@PathVariable Long id){
        log.info("Entrando no controller de exclusão");
        try{
            log.info("Chamando serviço de exclusão");
            livroService.excluiLivro(id);
            log.info("Livro excluído com sucesso. Saindo do controller.");
        }
        catch(Exception ex){
            log.error("Erro ao excluir livro: {} ", ex.getMessage());
        }
    }


}
