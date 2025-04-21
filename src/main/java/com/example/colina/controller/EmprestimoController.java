package com.example.colina.controller;

import com.example.colina.Dto.EmprestimoDTO;
import com.example.colina.entity.Emprestimo;
import com.example.colina.exceptions.LimiteEmprestimosExcedidoException;
import com.example.colina.exceptions.LivroIndisponivelException;
import com.example.colina.service.EmprestimoService;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            log.error("ERRO controller de cadastrarEmprestimo: {}", e.getMessage());
            return ResponseEntity.status(400).body(emprestimoDTO);

        } catch (LivroIndisponivelException | LimiteEmprestimosExcedidoException e) {
            return ResponseEntity.status(400).body(emprestimoDTO);
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<EmprestimoDTO>> listarEmprestimos() {
        try {
            List<EmprestimoDTO> emprestimosDTO = emprestimoService.listaEmprestimos();
            return ResponseEntity.status(200).body(emprestimosDTO);
        } catch (Exception e) {
            log.error("ERRO controller de listarEmprestimos: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<Boolean> renovarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO){
        try{
            Boolean emprestimo = emprestimoService.renovarEmprestimo(emprestimoDTO);
            if (emprestimo){
                return ResponseEntity.noContent().build();
            } else {
                throw new RuntimeException();
            }
        } catch(Exception e){
            log.error("ERRO controller de alterarEmprestimo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
