package JavaDBAppIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PrintAllMinionNames {

    private static final String GET_MINIONS_NAME = "SELECT name FROM minions ORDER BY id";

    public static void main(String[] args) throws SQLException {

        List<String> minionName = new ArrayList<>();
        Connection connectionSQL = LogIn.logIn();

        PreparedStatement getMinionName = connectionSQL.prepareStatement(GET_MINIONS_NAME);
        ResultSet resultMinionName = getMinionName.executeQuery();

        while (resultMinionName.next()) {
            String name = resultMinionName.getString("name");
            minionName.add(name);
        }
        int count = 0;
        int size = minionName.size();
        while (count <= (size - 1) / 2) {
            System.out.println(minionName.get(count));
            System.out.println(minionName.get(size - 1 - count));
            count++;
        }

        System.out.println();
        connectionSQL.close();
    }
}
