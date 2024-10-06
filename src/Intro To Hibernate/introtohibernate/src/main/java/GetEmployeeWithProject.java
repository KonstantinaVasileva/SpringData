import entities.Employee;
import entities.Project;

import java.util.Scanner;
import java.util.stream.Collectors;

public class GetEmployeeWithProject {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());

        MyConnection.createEntityManager()
                .createQuery("FROM Employee WHERE id = :id", Employee.class)
                .setParameter("id", id)
                .getResultList()
                .forEach(e-> System.out.printf("%s %s - %s\n%s",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getJobTitle(),
                        String.join(System.lineSeparator(),
                                e.getProjects()
                                        .stream()
                                        .map(Project::getName)
                                        .sorted()
                                        .collect(Collectors.toList()))));

    }
}
