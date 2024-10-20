package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Account extends BaseEntity {

    @Column
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Account(BigDecimal balance) {
        this.balance = balance;
    }

}
