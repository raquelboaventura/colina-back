package com.example.colina.Dto;

import com.example.colina.entity.Cliente;
import com.example.colina.entity.Livro;
import com.example.colina.enums.StatusEmprestimo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmprestimoDTO {
    private Long id_emprestimo;
    private Long id_cliente; // Apenas o ID do cliente
    private List<Long> livrosIds = new ArrayList<>(); // Apenas os IDs dos livros
    // Remova os campos cliente e livros
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private StatusEmprestimo status;
}
