package JavaDBAppIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    private static final String CALL_PROCEDURE = "call usp_get_older (?) ";
    private static final String GET_MINION = "SELECT name, age FROM minions WHERE id = ?";

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        Connection connectionSQL = LogIn.logIn();

        PreparedStatement callProcedure = connectionSQL.prepareStatement(CALL_PROCEDURE);
        callProcedure.setInt(1, id);
        callProcedure.execute();

        PreparedStatement getMinion = connectionSQL.prepareStatement(GET_MINION);
        getMinion.setInt(1, id);
        ResultSet getMinionResult = getMinion.executeQuery();

        if (getMinionResult.next()) {
            String name = getMinionResult.getString("name");
            int age = getMinionResult.getInt("age");
            System.out.println(name + " " + age);
        }
        connectionSQL.close();
    }
}
