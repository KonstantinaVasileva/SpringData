package JavaDBAppIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    private static final String QUERY = "select  m.name, m.age, v.name from minions m\n" +
            "JOIN minions_villains mv on m.id = mv.minion_id\n" +
            "join villains v on v.id = mv.villain_id\n" +
            "where v.id = ?;";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        Connection connectionSQL = LogIn.logIn();
        PreparedStatement query = connectionSQL.prepareStatement(QUERY);
        query.setInt(1, id);
        ResultSet result = query.executeQuery();

        int i = 0;
        while (result.next()) {
            if (i == 0){
                String vName = result.getString("v.name");
                System.out.println("Villain: %s".formatted(vName));
            }
            i++;
            String mName = result.getString("m.name");
            int age = result.getInt("age");
            System.out.println("%d. %s %d".formatted(i, mName, age));
        }
        if (i==0){
            System.out.println("No villain with ID %d exists in the database.".formatted(id));
        }
    }
}
