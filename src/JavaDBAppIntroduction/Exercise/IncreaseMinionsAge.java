package JavaDBAppIntroduction.Exercise;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;


public class IncreaseMinionsAge {
    private static final String GET_MINION = "SELECT name, age FROM minions";
    private static final String UPDATE_MINION =
            "UPDATE minions SET age = age + 1, name = lower(name) WHERE id IN (?)";

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        int[] minionIds = Arrays.stream(scanner.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        Connection connectionSQL = LogIn.logIn();

        PreparedStatement updateMinion = connectionSQL.prepareStatement(UPDATE_MINION);
        for (int minionId : minionIds) {
            updateMinion.setInt(1, minionId);
            updateMinion.executeUpdate();
        }

        PreparedStatement getMinion = connectionSQL.prepareStatement(GET_MINION);
        ResultSet resultSet = getMinion.executeQuery();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int age = Integer.parseInt(resultSet.getString("age"));
            System.out.println(name + " " + age);
        }
        connectionSQL.close();
    }
}
