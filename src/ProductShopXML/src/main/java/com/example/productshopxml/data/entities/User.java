package com.example.productshopxml.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Table(name = "users")
@Entity
@Setter
@Getter
@NoArgsConstructor
public class User extends BaseEntity{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column
    private int age;

    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    private Set<Product> sold;

    @OneToMany(mappedBy = "buyer")
    @Fetch(FetchMode.JOIN)
    private Set<Product> bought;

    @ManyToMany
//    @JoinTable(name = "users_friends",
//    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//    inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    @Fetch(FetchMode.JOIN)
    private Set<User> friends;

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
