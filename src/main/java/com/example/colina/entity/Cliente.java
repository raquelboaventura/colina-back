package com.example.colina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_CLIENTES")
@Entity
public class Cliente {
    @Id
    @Column(name = "id_cliente",nullable = false, length = 11)
    private String cpf;
    private String nome;


}
