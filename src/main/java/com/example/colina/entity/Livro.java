package com.example.colina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_LIVRO")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(columnDefinition = "boolean default true")
    private boolean status = true;
}
