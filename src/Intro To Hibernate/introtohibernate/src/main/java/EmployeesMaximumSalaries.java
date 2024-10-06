
public class EmployeesMaximumSalaries {
    public static void main(String[] args) {

        MyConnection.createEntityManager()
                .createQuery("SELECT department.name, max(salary) FROM Employee " +
                                "GROUP BY department.name HAVING max(salary) NOT BETWEEN 30000 AND 70000"
                        , Object[].class)
                .getResultList()
                .forEach(r -> System.out.printf("%s %.2f%n",
                        r[0], r[1]));

    }
}
