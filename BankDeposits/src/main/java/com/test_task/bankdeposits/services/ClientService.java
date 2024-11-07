package com.test_task.bankdeposits.services;

import com.test_task.bankdeposits.models.Client;
import com.test_task.bankdeposits.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
