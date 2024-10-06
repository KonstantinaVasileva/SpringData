import entities.Project;

public class FindLatest10Projects {
    public static void main(String[] args) {
        MyConnection.createEntityManager()
                .createQuery("FROM Project ORDER BY startDate DESC , name", Project.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(p -> System.out.printf("Project name: %s\n" +
                                " \tProject Description: %s\n" +
                                " \tProject Start Date: %s\n" +
                                " \tProject End Date: %s\n",
                        p.getName(),
                        p.getDescription(),
                        p.getStartDate(),
                        p.getEndDate()));
    }
}
