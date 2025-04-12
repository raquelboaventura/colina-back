package com.example.colina.controller;

import com.example.colina.Dto.EmprestimoDTO;
import com.example.colina.exceptions.LimiteEmprestimosExcedidoException;
import com.example.colina.exceptions.LivroIndisponivelException;
import com.example.colina.service.EmprestimoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("emprestimo")
@Log4j2
public class EmprestimoController {
    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("")
    public ResponseEntity<EmprestimoDTO> cadastrarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO){
        try{
            log.info("Entrando no controller de cadastro de emprestimos: {}", emprestimoDTO);
            EmprestimoDTO retornoCadastro = emprestimoService.cadastrarEmprestimo(emprestimoDTO);
            log.info("Saindo do controller de cadastro de emprestimos.");
            return ResponseEntity.status(200).body(retornoCadastro);
        }
        catch(Exception e){
            log.error("ERRO controller de emprestimo: {}", e.getMessage());
            return ResponseEntity.status(400).body(emprestimoDTO);

        } catch (LivroIndisponivelException | LimiteEmprestimosExcedidoException e) {
            return ResponseEntity.status(400).body(emprestimoDTO);
        }
    }
}
