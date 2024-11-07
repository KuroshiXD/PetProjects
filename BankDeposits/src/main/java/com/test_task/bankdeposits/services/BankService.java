package com.test_task.bankdeposits.services;

import com.test_task.bankdeposits.models.Bank;
import com.test_task.bankdeposits.repositories.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank saveBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public Optional<Bank> findBankById(Long id) {
        return bankRepository.findById(id);
    }

    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
    }
}
