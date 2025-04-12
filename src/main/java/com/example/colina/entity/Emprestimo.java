package com.example.colina.entity;

import com.example.colina.enums.StatusEmprestimo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_EMPRESTIMO")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_emprestimo;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;

    @ManyToMany
    @JoinTable(
            name = "emprestimo_livro",
            joinColumns = @JoinColumn(name = "emprestimo_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> livros;
}
