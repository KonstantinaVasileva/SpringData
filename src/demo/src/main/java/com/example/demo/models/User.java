package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class User extends BaseEntity{

    @Column(unique = true)
    private String username;

    @Column
    private int age;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Account> accounts;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
        this.accounts = new HashSet<>();
    }
    public User(){
        this.accounts = new HashSet<>();
    }


    public void addAccount(Account account){
        accounts.add(account);
    }
}
