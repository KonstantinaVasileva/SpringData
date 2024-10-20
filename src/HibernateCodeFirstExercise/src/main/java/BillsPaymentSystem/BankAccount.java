package BillsPaymentSystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetail{

    @Column
    private String name;

    @Column
    private int SWIFTCode;
}
