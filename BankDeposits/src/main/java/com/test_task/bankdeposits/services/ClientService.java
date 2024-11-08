package com.test_task.bankdeposits.services;

import com.test_task.bankdeposits.models.Client;
import com.test_task.bankdeposits.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<Client> findAllClientsByLegalForm(Client.LegalForm legalForm, Pageable pageable) {
        return clientRepository.findByLegalForm(legalForm, pageable);
    }

    public Page<Client> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
