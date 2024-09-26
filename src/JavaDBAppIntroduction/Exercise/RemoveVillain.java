package JavaDBAppIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    private static final String GET_VILLAIN_NAME = "SELECT name From villains WHERE id = ?";
    private static final String COUNT_MINION =
            "SELECT count(minion_id) AS count_minion FROM minions_villains WHERE villain_id = ?";

    private static final String RELEASE_MINION =
            "DELETE FROM minions_villains WHERE villain_id = ?";
    private static final String DELETE_VILLAIN = "DELETE FROM villains WHERE id = ?";

    private static final String OUTPUT_MESSAGE = "%s was deleted%n%d minions released%n";
    private static final String NO_VILLAIN_OUTPUT_MESSAGE = "No such villain was found";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());

        Connection connectionSQL = LogIn.logIn();
        PreparedStatement getVillainName = connectionSQL.prepareStatement(GET_VILLAIN_NAME);
        getVillainName.setInt(1, villainId);
        ResultSet resultGetVillainName = getVillainName.executeQuery();

        if (!resultGetVillainName.next()) {
            System.out.println(NO_VILLAIN_OUTPUT_MESSAGE);
            connectionSQL.close();
            return;
        }
        String villainName = resultGetVillainName.getString("name");
        PreparedStatement getCountMinion = connectionSQL.prepareStatement(COUNT_MINION);
        getCountMinion.setInt(1, villainId);
        ResultSet resultCountMinion = getCountMinion.executeQuery();
        resultCountMinion.next();
        int countMinion = resultCountMinion.getInt("count_minion");

        connectionSQL.setAutoCommit(false);
       try( PreparedStatement releaseMinion = connectionSQL.prepareStatement(RELEASE_MINION);
            PreparedStatement deleteVillain = connectionSQL.prepareStatement(DELETE_VILLAIN)) {
           releaseMinion.setInt(1, villainId);
           releaseMinion.executeUpdate();

           deleteVillain.setInt(1, villainId);
           deleteVillain.executeUpdate();
           connectionSQL.commit();

           System.out.printf(OUTPUT_MESSAGE, villainName, countMinion);
       } catch (SQLException sqlException){
           connectionSQL.rollback();
       }

       connectionSQL.close();
    }
}
