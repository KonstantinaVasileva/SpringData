import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;

public class AddingANewAddressAndUpdatingEmployee {

    public static final String ADDERS = "Vitoshka 15";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        EntityManager entityManager = MyConnection.createEntityManager();

        entityManager.getTransaction().begin();

        List<Employee> resultList = entityManager.createQuery("from Employee where lastName =:ln", Employee.class)
                .setParameter("ln", name).getResultList();

        Address address = new Address();
        address.setText(ADDERS);

        entityManager.persist(address);

        for (Employee employee : resultList) {
            employee.setAddress(address);
        }

        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
