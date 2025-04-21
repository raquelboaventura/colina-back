package com.example.colina.Dto;

import com.example.colina.entity.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {
    private String isbn;
    private String titulo;
    private String autor;
    private String editora;
    private int paginas;
    private String publicacao;
    private float preco;
    private String genero;
    private int quantidade;
    private boolean status = true;
    private List<Emprestimo> emprestimos = new ArrayList<>();
}
