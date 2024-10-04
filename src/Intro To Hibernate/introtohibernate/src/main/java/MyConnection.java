import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MyConnection {
    public static EntityManager createEntityManager() {
        EntityManagerFactory entity = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager manager = entity.createEntityManager();
        return manager;
    }


}
