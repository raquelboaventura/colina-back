package com.example.colina.controller;

import com.example.colina.Dto.EmprestimoDTO;
import com.example.colina.Dto.EmprestimoDetalhadoDTO;
import com.example.colina.exceptions.LimiteEmprestimosExcedidoException;
import com.example.colina.exceptions.LivroIndisponivelException;
import com.example.colina.service.EmprestimoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("emprestimo")
@Log4j2
@CrossOrigin
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping("")
    public ResponseEntity<EmprestimoDTO> cadastrarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
        try {
            log.info("Entrando no controller de cadastro de emprestimos: {}", emprestimoDTO);
            EmprestimoDTO retornoCadastro = emprestimoService.cadastrarEmprestimo(emprestimoDTO);
            log.info("Saindo do controller de cadastro de emprestimos.");
            return ResponseEntity.ok(retornoCadastro);
        } catch (LivroIndisponivelException | LimiteEmprestimosExcedidoException e) {
            log.error("Erro de negócio: {}", e.getMessage());
            return ResponseEntity.badRequest().body(emprestimoDTO);
        } catch (Exception e) {
            log.error("ERRO controller de emprestimo: {}", e.getMessage());
            return ResponseEntity.badRequest().body(emprestimoDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> buscarEmprestimoPorId(@PathVariable Long id) {
        try {
            log.info("Buscando emprestimo com id: {}", id);
            EmprestimoDTO dto = emprestimoService.buscarEmprestimoPorId(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Erro ao buscar emprestimo: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> atualizarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoDTO dto) {
        try {
            log.info("Atualizando emprestimo com id: {}", id);
            EmprestimoDTO atualizado = emprestimoService.atualizarEmprestimo(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            log.error("Erro ao atualizar emprestimo: {}", e.getMessage());
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<EmprestimoDTO> deleteLogico(@PathVariable Long id) {
        try {
            log.info("Deletando logicamente emprestimo com id: {}", id);
            EmprestimoDTO dto = emprestimoService.deleteLogico(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Erro ao deletar logicamente emprestimo: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/detalhado/{id}")
    public ResponseEntity<EmprestimoDetalhadoDTO> buscarEmprestimoDetalhadoPorId(@PathVariable Long id) {
        try {
            EmprestimoDetalhadoDTO dto = emprestimoService.buscarEmprestimoDetalhadoPorId(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
