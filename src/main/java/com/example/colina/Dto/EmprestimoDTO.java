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

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private StatusEmprestimo status;
    @JsonProperty("cliente")
    private Cliente cliente;
    @JsonProperty("livros")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Livro> livros = new ArrayList<>();
}
