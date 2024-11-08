package services;

import com.test_task.bankdeposits.models.Client;
import com.test_task.bankdeposits.repositories.ClientRepository;
import com.test_task.bankdeposits.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveClient() {
        Client client = new Client("Qwerty", "Decanter", "Dec", Client.LegalForm.LLC);
        when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.saveClient(client);

        assertEquals("Decanter", savedClient.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void findClientById() {
        Client client = new Client("Qwerty", "Decanter", "Dec", Client.LegalForm.LLC);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> foundClient = clientService.findClientById(1L);

        assertTrue(foundClient.isPresent());
        assertEquals("Decanter", foundClient.get().getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void findAllClients() {
        Client client1 = new Client("Address1", "Name1", "Short1", Client.LegalForm.CJSC);
        Client client2 = new Client("Address2", "Name2", "Short2", Client.LegalForm.INDIVIDUAL);
        List<Client> clients = Arrays.asList(client1, client2);
        Page<Client> clientPage = new PageImpl<>(clients);

        Pageable pageable = PageRequest.of(0, 10);

        when(clientRepository.findAll(pageable)).thenReturn(clientPage);

        Page<Client> allClients = clientService.findAllClients(pageable);

        assertEquals(2, allClients.getTotalElements());
        verify(clientRepository, times(1)).findAll(pageable);
    }

    @Test
    void deleteClient() {
        clientService.deleteClient(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }
}
