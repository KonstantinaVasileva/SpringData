package JavaDBAppIntroduction.Lab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final String GET_USER_BY_NAME = "SELECT user_name, first_name, last_name, count(*) as games_count\n" +
            "FROM users u\n" +
            "         JOIN users_games ug on u.id = ug.user_id\n" +
            "WHERE user_name = ?" +
            "GROUP BY user_id;";
    private static final String OUTPUT = "User: %s%n    %s %s has played %d games\n";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String name = scanner.nextLine();
        PreparedStatement findName;
        try (Connection connectionSQL = LogIn.logIn()) {

            findName = connectionSQL.prepareStatement(GET_USER_BY_NAME);
            findName.setString(1, name);

            PreparedStatement query = connectionSQL.prepareStatement(GET_USER_BY_NAME);
            query.setString(1, name);
            ResultSet result = query.executeQuery();

            if (result.next()){
                String username = result.getString("user_name");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                int gamesCount = Integer.parseInt(result.getString("games_count"));
                System.out.printf(OUTPUT, username, firstName, lastName, gamesCount);
            } else {
                System.out.println("No such user exists");
            }
        }

    }
}
