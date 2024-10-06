import entities.Employee;

import java.util.Scanner;

public class FindEmployeesByFirstName {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();

        MyConnection.createEntityManager()
                .createQuery("FROM Employee WHERE firstName LIKE :ptrn", Employee.class)
                .setParameter("ptrn", pattern + "%")
                .getResultList()
                .forEach(e-> System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getJobTitle(),
                        e.getSalary()));

    }
}
