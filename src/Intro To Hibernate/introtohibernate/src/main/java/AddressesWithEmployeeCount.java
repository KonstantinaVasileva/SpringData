import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressesWithEmployeeCount {

    private static final String QUERY = "FROM Address ORDER BY employees.size DESC";

    public static void main(String[] args) {
        EntityManager entityManager = MyConnection.createEntityManager();
        entityManager.getTransaction().begin();

        List<Address> resultList = entityManager.createQuery(QUERY, Address.class)
                .setMaxResults(10)
                .getResultList();

        resultList.forEach(a -> System.out.printf("%s, %s - %d employees",
                a.getText(), a.getTown().getName(), a.getEmployees().size()));

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
