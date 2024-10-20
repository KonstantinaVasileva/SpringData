import SalesDatabase.Customer;
import SalesDatabase.Product;
import SalesDatabase.Sale;
import SalesDatabase.StoreLocation;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        EntityManager entityManager = Persistence.createEntityManagerFactory("bill_payment_system")
                .createEntityManager();
        entityManager.getTransaction().begin();

        Product product = new Product("cup", 5.0, new BigDecimal("1.50"));
        Customer customer = new Customer("Pesho", "pesho@mail.bg", "32168632135");
        StoreLocation storeLocation = new StoreLocation("Local");
        entityManager.persist(product);
        entityManager.persist(customer);
        entityManager.persist(storeLocation);
        entityManager.flush();
        entityManager.clear();
        Sale sale = new Sale(product, customer, storeLocation, LocalDate.now());
        entityManager.persist(sale);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
