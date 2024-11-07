package services;

import com.test_task.bankdeposits.models.Client;
import com.test_task.bankdeposits.repositories.ClientRepository;
import com.test_task.bankdeposits.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveClient() {
        Client client = new Client("Bruh street", "Big Bob", "Short Bob", Client.LegalForm.JSC);
        when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.saveClient(client);

        assertEquals("Big Bob", savedClient.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void findClientById() {
        Client client = new Client("Address", "Client Name", "Short Name", Client.LegalForm.LLC);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> foundClient = clientService.findClientById(1L);

        assertTrue(foundClient.isPresent());
        assertEquals("Client Name", foundClient.get().getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void findAllClients() {
        Client client1 = new Client("Address1", "Name1", "Short1", Client.LegalForm.CJSC);
        Client client2 = new Client("Address2", "Name2", "Short2", Client.LegalForm.INDIVIDUAL);
        List<Client> clients = Arrays.asList(client1, client2);
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> allClients = clientService.findAllClients();

        assertEquals(2, allClients.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void deleteClient() {
        clientService.deleteClient(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }
}
