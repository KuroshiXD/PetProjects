package com.test_task.bankdeposits.controllers;

import com.test_task.bankdeposits.models.Deposit;
import com.test_task.bankdeposits.services.DepositService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/deposits")
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @GetMapping()
    public List<Deposit> getAllDeposits() {
        return depositService.findAllDeposits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deposit> getDepositById(@PathVariable Long id) {
        Optional<Deposit> deposit = depositService.findDepositById(id);
        return deposit.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Deposit> createDeposit(@RequestBody Deposit deposit) {
        depositService.saveDeposit(deposit);
        return ResponseEntity.status(HttpStatus.CREATED).body(deposit);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Deposit> updateDeposit(@PathVariable Long id, @RequestBody Deposit depositItems) {
        Optional<Deposit> deposit = depositService.findDepositById(id);
        if (deposit.isPresent()) {
            Deposit updatedDeposit = deposit.get();
            updatedDeposit.setDepositPercentage(depositItems.getDepositPercentage());
            updatedDeposit.setTermInMonths(depositItems.getTermInMonths());
            depositService.saveDeposit(updatedDeposit);
            return ResponseEntity.ok(updatedDeposit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Deposit> deleteDeposit(@PathVariable Long id) {
        depositService.deleteDeposit(id);
        return ResponseEntity.noContent().build();
    }
}
