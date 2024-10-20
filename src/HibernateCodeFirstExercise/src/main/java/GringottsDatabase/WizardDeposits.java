package GringottsDatabase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class WizardDeposits {

    @Id
    @Column
    private int id;

    @Column (length = 50, name = "first_name")

    private String firstName;

    @Column(length = 60, name = "last_name")
    private String lastName;

    @Column(length = 1000)
    private String notes;

    @Column
    @NonNull
    private int age;

    @Column(length = 100, name = "magic_wands_creator")
    private String magicWandsCreator;

    @Column(name = "magic_wand_size")
    private String magicWandSize;

    @Column(length = 20, name = "deposit_group")
    private String depositGroup;

    @Column(name = "deposit_start_date")
    private LocalDate depositStartDate;

    @Column(name = "deposit_amont")
    private BigDecimal depositAmount;

    @Column(name = "deposit_interest")
    private BigDecimal depositInterest;

    @Column(name = "deposit_charge")
    private BigDecimal depositCharge;

    @Column(name = "deposit_expiration_date")
    private LocalDate depositExpirationDate;

    @Column(name = "is_deposit_expiration")
    private boolean isDepositExpiration;
}
