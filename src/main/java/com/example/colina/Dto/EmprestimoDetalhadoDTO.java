package com.example.colina.Dto;

import java.time.LocalDate;
import java.util.List;

import com.example.colina.entity.Cliente;
import com.example.colina.entity.Livro;
import com.example.colina.enums.StatusEmprestimo;

import lombok.Data;


@Data
public class EmprestimoDetalhadoDTO {
private Long id_emprestimo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private StatusEmprestimo status;
    private Cliente cliente; // objeto completo
    private List<Livro> livros; // lista completa dos livros
}

