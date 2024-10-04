import entities.Employee;

import javax.persistence.EntityManager;

public class EmployeesWithSalaryOver50000{
    public static void main(String[] args) {
        EntityManager entityManager = MyConnection.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("From Employee WHERE salary > 50000", Employee.class)
                .getResultList()
                .forEach(e-> System.out.println(e.getFirstName()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
