import entities.Address;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class RemoveTowns {

    private static final String TOWNS = "FROM Address WHERE town.name = :tn";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String townName = scanner.nextLine();

        EntityManager entityManager = MyConnection.createEntityManager();

        entityManager.getTransaction().begin();

        List<Address> addresses = entityManager
                .createQuery(TOWNS, Address.class)
                .setParameter("tn", townName)
                .getResultList();

        int count = addresses.size();

        addresses.forEach(a -> a.getEmployees().forEach(e -> e.setAddress(null)));

        addresses.forEach(entityManager::remove);

        entityManager.createQuery("DELETE FROM Town WHERE name = :tn")
                .setParameter("tn", townName);

        System.out.printf("%d %s in %s deleted",
                count,
                count == 1 ? "address" : "addresses",
                townName);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
