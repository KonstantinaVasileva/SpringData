import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Scanner;

public class ContainsEmployee {
    private static final String IS_CONTAINS =
            "FROM Employee WHERE concat_ws(' ', firstName, lastName) = : name";

    public static void main(String[] args) {
        EntityManager manager = MyConnection.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        manager.getTransaction().begin();

        TypedQuery<Employee> query = manager.createQuery(IS_CONTAINS, Employee.class);
        List<Employee> result = query.setParameter("name", name).getResultList();
        if (result.isEmpty()) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }

        manager.getTransaction().commit();
        manager.close();

    }
}

