package com.test_task.bankdeposits.controllers;

import com.test_task.bankdeposits.models.Bank;
import com.test_task.bankdeposits.services.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping()
    public List<Bank> getAllBanks() {
        return bankService.findAllBanks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Long id) {
        Optional<Bank> bank = bankService.findBankById(id);
        // bank.isPresent() ? ResponseEntity.ok(bank.get()) : ResponseEntity.notFound().build();
        return bank.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Bank> createBank(@RequestBody Bank bankItems) {
        Bank bank = new Bank(bankItems.getName(), bankItems.getBik());
        Bank createdBank = bankService.saveBank(bank);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBank);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bankItems) {
        Optional<Bank> bank = bankService.findBankById(id);
        if (bank.isPresent()) {
            Bank updatedBank = bank.get();
            updatedBank.setName(bankItems.getName());
            updatedBank.setBik(bankItems.getBik());
            bankService.saveBank(updatedBank);
            return ResponseEntity.ok(updatedBank);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bank> deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
        return ResponseEntity.noContent().build();
    }
}
