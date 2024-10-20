package com.example.demo;

import com.example.demo.models.Account;
import com.example.demo.models.User;
import com.example.demo.services.AccountService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private UserService userService;
    private AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Ivan", 22);

        Account account = new Account(BigDecimal.valueOf(5000));
        account.setUser(user);
        user.addAccount(account);

        userService.registerUser(user);
        accountService.withdrawMoney(BigDecimal.valueOf(200), account.getId());
        accountService.transferMoney(BigDecimal.valueOf(500), account.getId());
    }

}
