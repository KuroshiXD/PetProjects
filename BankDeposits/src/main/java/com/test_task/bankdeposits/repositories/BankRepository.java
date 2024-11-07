package com.test_task.bankdeposits.repositories;

import com.test_task.bankdeposits.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
