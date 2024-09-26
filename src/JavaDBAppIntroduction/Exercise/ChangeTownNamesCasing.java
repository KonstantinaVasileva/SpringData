package JavaDBAppIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {

    private static final String GET_TOWN_NAMES = "SELECT name FROM towns WHERE country = ? ";
    private static final String COUNT_TOWNS_BY_COUNTRY =
            "SELECT count(name) AS names_count FROM towns WHERE country = ?";

    private static final String OUTPUT_FOR_COUNT = "%d town names were affected.%n";
    private static final String OUTPUT_FOR_NO_TOWNS = "No town names were affected.";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();

        Connection connectionSQL = LogIn.logIn();

        PreparedStatement countTowns = connectionSQL.prepareStatement(COUNT_TOWNS_BY_COUNTRY);
        countTowns.setString(1, country);
        ResultSet resultCountTown = countTowns.executeQuery();
        if (resultCountTown.next()) {
            int count = resultCountTown.getInt("names_count");
            if (count == 0) {
                System.out.println(OUTPUT_FOR_NO_TOWNS);
                connectionSQL.close();
                return;
            }
            System.out.printf(OUTPUT_FOR_COUNT, count);
        }

        List<String> townNamesList = new ArrayList<>();

        PreparedStatement getTownNames = connectionSQL.prepareStatement(GET_TOWN_NAMES);
        getTownNames.setString(1, country);
        ResultSet resultForTownNames = getTownNames.executeQuery();

        while (resultForTownNames.next()) {
            String townName = resultForTownNames.getString("name");
            townNamesList.add(townName.toUpperCase());
        }

        System.out.println(townNamesList);

    }
}
