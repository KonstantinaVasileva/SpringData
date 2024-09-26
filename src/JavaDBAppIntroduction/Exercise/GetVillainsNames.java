package JavaDBAppIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames {
    private static final String QUERY = "SELECT name, count(distinct minion_id) AS minions_count FROM villains v\n" +
            "JOIN minions_villains mv on v.id = mv.villain_id\n" +
            "group by villain_id\n" +
            "having minions_count > 15\n" +
            "ORDER BY minions_count DESC ;";
    private static final String  OUTPUT = "%s %d";
    public static void main(String[] args) throws SQLException {
        Connection connection = LogIn.logIn();

        PreparedStatement query = connection.prepareStatement(QUERY);
        ResultSet resultSet = query.executeQuery();
        if (resultSet.next()){
            String name = resultSet.getString("name");
            int minionsCount = Integer.parseInt(resultSet.getString("minions_count"));
            System.out.printf(OUTPUT, name, minionsCount);
        }
        connection.close();

    }
}
