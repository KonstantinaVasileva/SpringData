package com.example.demo.services;

import jakarta.persistence.EntityNotFoundException;
import com.example.demo.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, int id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            BigDecimal balance = account.get().getBalance();
            if (balance.compareTo(money) >= 0) {
                account.get().setBalance(balance.subtract(money));
            }
            else {
                throw new RuntimeException("Not enough money");
            }
        accountRepository.saveAndFlush(account.get());
        }
        else {
            throw new EntityNotFoundException("Account dose not exist");
        }

    }

    @Override
    public void transferMoney(BigDecimal money, int id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account dose not exist"));
        if (money.compareTo(BigDecimal.valueOf(0)) < 0){
            throw new NumberFormatException("The money is negative");
        }
        BigDecimal newBalance = account.getBalance().add(money);
        account.setBalance(newBalance);

        accountRepository.saveAndFlush(account);
    }
}
