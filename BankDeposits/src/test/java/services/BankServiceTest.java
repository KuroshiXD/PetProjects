package services;

import com.test_task.bankdeposits.models.Bank;
import com.test_task.bankdeposits.repositories.BankRepository;
import com.test_task.bankdeposits.services.BankService;
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

class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBank() {
        Bank bank = new Bank("Bank Name", "123456");
        when(bankRepository.save(bank)).thenReturn(bank);

        Bank savedBank = bankService.saveBank(bank);

        assertEquals("Bank Name", savedBank.getName());
        assertEquals("123456", savedBank.getBik());
        verify(bankRepository, times(1)).save(bank);
    }

    @Test
    void findBankById() {
        Bank bank = new Bank("Bank Name", "123456");
        when(bankRepository.findById(1L)).thenReturn(Optional.of(bank));

        Optional<Bank> foundBank = bankService.findBankById(1L);

        assertTrue(foundBank.isPresent());
        assertEquals("Bank Name", foundBank.get().getName());
        verify(bankRepository, times(1)).findById(1L);
    }

    @Test
    void findAllBanks() {
        Bank bank1 = new Bank("Bank One", "123456");
        Bank bank2 = new Bank("Bank Two", "654321");
        List<Bank> banks = Arrays.asList(bank1, bank2);
        when(bankRepository.findAll()).thenReturn(banks);

        List<Bank> allBanks = bankService.findAllBanks();

        assertEquals(2, allBanks.size());
        verify(bankRepository, times(1)).findAll();
    }

    @Test
    void deleteBank() {
        bankService.deleteBank(1L);
        verify(bankRepository, times(1)).deleteById(1L);
    }
}
