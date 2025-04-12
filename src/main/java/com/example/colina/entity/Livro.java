package com.example.colina.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "TBL_LIVRO")
@AllArgsConstructor
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_livro")
    private Long id_livro;
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
    @ToString.Exclude
    @ManyToMany(mappedBy = "livros")
    private List<Emprestimo> emprestimos = new ArrayList<>();
}
