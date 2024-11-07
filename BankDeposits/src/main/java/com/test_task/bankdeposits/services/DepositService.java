package com.test_task.bankdeposits.services;

import com.test_task.bankdeposits.models.Deposit;
import com.test_task.bankdeposits.repositories.BankRepository;
import com.test_task.bankdeposits.repositories.ClientRepository;
import com.test_task.bankdeposits.repositories.DepositRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepositService {

    private final DepositRepository depositRepository;

    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public Deposit saveDeposit(Deposit deposit) {
        if (deposit.getClient() == null) throw new RuntimeException("Client not found!");
        if (deposit.getBank() == null) throw new RuntimeException("Bank not found!");
        return depositRepository.save(deposit);
    }

    public Optional<Deposit> findDepositById(Long id) {
        return depositRepository.findById(id);
    }

    public List<Deposit> findAllDeposits() {
        return depositRepository.findAll();
    }

    public void deleteDeposit(Long id) {
        depositRepository.deleteById(id);
    }

}
