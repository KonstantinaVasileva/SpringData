package com.example.demo.services;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal money, int id);
    void transferMoney(BigDecimal money, int id);
}
