package com.test_task.bankdeposits.repositories;

import com.test_task.bankdeposits.models.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DepositRepository extends JpaRepository<Deposit, Long> {
}
