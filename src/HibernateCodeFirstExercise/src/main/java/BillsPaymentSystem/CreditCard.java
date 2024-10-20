package BillsPaymentSystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "credit_card")
public class CreditCard extends BillingDetail {

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "expiration_month")
    private int expirationMonth;

    @Column(name = "expiration_year")
    private int expirationYear;
}
