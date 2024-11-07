package services;

import com.test_task.bankdeposits.models.Bank;
import com.test_task.bankdeposits.models.Client;
import com.test_task.bankdeposits.models.Deposit;
import com.test_task.bankdeposits.repositories.BankRepository;
import com.test_task.bankdeposits.repositories.ClientRepository;
import com.test_task.bankdeposits.repositories.DepositRepository;
import com.test_task.bankdeposits.services.DepositService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepositServiceTest {

    @Mock
    private DepositRepository depositRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private DepositService depositService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDeposit() {
        Client client = new Client("Qwerty", "Decanter", "Dec", Client.LegalForm.LLC);
        client.setId(1L);
        Bank bank = new Bank("ZXCV", "8481518456551");
        bank.setId(1L);
        Deposit deposit = new Deposit(LocalDate.now(), 5.0, 12);
        deposit.setClient(client);
        deposit.setBank(bank);

        when(depositRepository.save(deposit)).thenReturn(deposit);

        Deposit result = depositRepository.save(deposit);
        assertEquals(deposit, result);
        verify(depositRepository, times(1)).save(deposit);
    }

    @Test
    void findDepositById() {
        Deposit deposit = new Deposit(LocalDate.now(), 5.0, 12);
        when(depositRepository.findById(1L)).thenReturn(Optional.of(deposit));

        Optional<Deposit> foundDeposit = depositService.findDepositById(1L);

        assertTrue(foundDeposit.isPresent());
        assertEquals(5.0, foundDeposit.get().getDepositPercentage());
        verify(depositRepository, times(1)).findById(1L);
    }

    @Test
    void findAllDeposits() {
        Deposit deposit1 = new Deposit(LocalDate.now(), 5.0, 12);
        Deposit deposit2 = new Deposit(LocalDate.now(), 3.0, 24);
        List<Deposit> deposits = Arrays.asList(deposit1, deposit2);
        when(depositRepository.findAll()).thenReturn(deposits);

        List<Deposit> allDeposits = depositService.findAllDeposits();

        assertEquals(2, allDeposits.size());
        verify(depositRepository, times(1)).findAll();
    }

    @Test
    void deleteDeposit() {
        depositService.deleteDeposit(1L);
        verify(depositRepository, times(1)).deleteById(1L);
    }
}
