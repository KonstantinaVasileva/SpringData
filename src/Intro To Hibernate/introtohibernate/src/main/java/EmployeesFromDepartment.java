import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Comparator;

public class EmployeesFromDepartment {

    public static final String QUERY = "FROM Employee where department.name = 'Research and Development'";
    public static final String PRINT = "%s %s from Research and Development - $%.2f%n";

    public static void main(String[] args) {
        EntityManager entityManager = MyConnection.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(QUERY, Employee.class)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Employee::getSalary)
                        .thenComparing(Employee::getId))
                .forEach(e -> System.out.printf(PRINT, e.getFirstName(),
                        e.getLastName(),
                        e.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
