package com.test_task.bankdeposits.repositories;

import com.test_task.bankdeposits.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {
}
