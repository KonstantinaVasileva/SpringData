import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class ChangeCasing {
    public static void main(String[] args) {
        EntityManager entityManager = MyConnection.createEntityManager();
        entityManager.getTransaction().begin();

        List<Town> towns = entityManager.createQuery("From Town", Town.class).getResultList();

        for (Town town : towns) {
            if (town.getName().length() > 5){
                entityManager.detach(town);
            } else {
                town.setName(town.getName().toUpperCase());
            }
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
