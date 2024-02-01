package com.example.colina.controller;

import com.example.colina.Dto.ClienteDTO;
import com.example.colina.service.ClienteService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("cliente")
@Log4j2
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Transactional
    @PostMapping("/cadastro")
    public ResponseEntity<ClienteDTO> cadastrarCliente(@RequestBody ClienteDTO clienteDTO){
        try{
            log.info("Entrando no controller de cadastro de cliente");
            clienteService.cadastrarCliente(clienteDTO);
            log.info("Saindo do controller de cadastro de cliente.");
            return ResponseEntity.status(200).body(clienteDTO);
        }
        catch(Exception exception){
            log.error("ERRO controller: {}", exception.getMessage());
            return ResponseEntity.status(400).body(clienteDTO);
        }
    }
}
