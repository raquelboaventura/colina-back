package com.example.colina.service;

import com.example.colina.Dto.ClienteDTO;
import com.example.colina.entity.Cliente;
import com.example.colina.repository.ClienteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Log4j2
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public void cadastrarCliente(ClienteDTO clienteDTO) {
        try {
            log.info("Entrando no serviço de cadastro de cliente");
            log.info("ClienteDTO: {}", clienteDTO);
            Cliente cliente = new Cliente();
            BeanUtils.copyProperties(clienteDTO, cliente);
            log.info("Cliente Entity: {}", cliente);
            clienteRepository.save(cliente);
            log.info("Cliente salvo com sucesso.");
        } catch (Exception exception) {
            log.error("Um erro ocorreu ao cadastrar o cliente: {}", exception.getMessage());
        }
    }
}
