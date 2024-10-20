package BillsPaymentSystem;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class User {

    @Id
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "lst_name")
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<BillingDetail> billingDetails;
}
