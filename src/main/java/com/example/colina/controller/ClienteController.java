package com.example.colina.controller;

import com.example.colina.Dto.ClienteDTO;
import com.example.colina.service.ClienteService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


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
            return ResponseEntity.status(400).body(clienteDTO); //TODO
        }
    }

    @GetMapping
    public ResponseEntity<ClienteDTO> listarClientePorId(@RequestParam Long id){
        try{
            log.info("Entrando no controller de busca de cliente por id");
            ClienteDTO clienteRetorno = clienteService.listarClientePorId(id);
            return ResponseEntity.status(200).body(clienteRetorno);
        }
        catch(Exception e){
            log.info("deu erro ao listar cliente por ID: {}", e.getMessage());
        }
        return null;
    }


    @GetMapping("/all")
    public ResponseEntity<List<ClienteDTO>> listarClientes(){
        try{
            log.info("Entrando no controller de listagem de clientes");
            List<ClienteDTO> listaClientes = clienteService.listarClientes();
            return ResponseEntity.status(200).body(listaClientes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}