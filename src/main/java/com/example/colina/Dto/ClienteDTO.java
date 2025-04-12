package com.example.colina.Dto;

import com.example.colina.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id_cliente;
    private String nome;
    private String cpf;
}
