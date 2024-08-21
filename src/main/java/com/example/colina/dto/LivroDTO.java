package com.example.colina.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {
    private Long id;
    private Long isbn;
    private String titulo;
    private String autor;
    private String editora;
    private int paginas;
    private String publicacao;
    private float preco;
    private String genero;
    private int quantidade;
    private boolean status = true;
}
