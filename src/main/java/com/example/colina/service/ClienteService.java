package com.example.colina.service;

import com.example.colina.Dto.ClienteDTO;
import com.example.colina.entity.Cliente;
import com.example.colina.repository.ClienteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ClienteDTO listarClientePorId(Long id) {
        try{
            log.info("Entrando no serviço de busca de cliente por id: {}", id);
            Optional<Cliente> cliente = clienteRepository.findById(id);
            ClienteDTO clienteDTO = new ClienteDTO();
            BeanUtils.copyProperties(cliente.get(), clienteDTO);
            log.info("cliente: {}, clienteDTO: {}", cliente, clienteDTO);
            return clienteDTO;
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

    }

    public List<ClienteDTO> listarClientes() {
        try{
            List<ClienteDTO> listaClientesDTO;
            log.info("Entrando no serviço de listagem de clientes");
            List<Cliente> listaClientes = clienteRepository.findAll();
            listaClientesDTO = listaClientes.stream()
                    .map(this::mapClienteToDTO)
                    .collect(Collectors.toList());
            return listaClientesDTO;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private ClienteDTO mapClienteToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setEndereco(cliente.getEndereco());

        return clienteDTO;
    }
    }

