import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManager relation = Persistence.createEntityManagerFactory("relations")
                .createEntityManager();

        relation.getTransaction().begin();


        relation.getTransaction().commit();
        relation.clear();
    }
}

