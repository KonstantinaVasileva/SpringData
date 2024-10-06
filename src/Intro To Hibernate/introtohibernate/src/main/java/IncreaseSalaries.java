import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class IncreaseSalaries {
    public static void main(String[] args) {
        EntityManager entityManager = MyConnection.createEntityManager();
        List<Employee> employees = entityManager
                .createQuery("FROM Employee WHERE department.name IN " +
                                "('Engineering', 'Tool Design', 'Marketing', 'Information Services' )",
                        Employee.class)
                .getResultList();

        entityManager.getTransaction().begin();
        employees.forEach(e->e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12))));

        employees.forEach(e-> System.out.printf("%s %s ($%.2f)",
                e.getFirstName(),
                e.getLastName(),
                e.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();



    }
}
