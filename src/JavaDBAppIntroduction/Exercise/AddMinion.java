package JavaDBAppIntroduction.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AddMinion {
    private static final String GET_MINION_ID_BY_NAME = "SELECT id FROM minions WHERE name = ? ";
    private static final String GET_VILLAIN_ID_BY_NAME = "SELECT id FROM villains WHERE name = ? ";
    private static final String GET_TOWN_BY_NAME = "SELECT id FROM towns WHERE name = ? ";
    private static final String ADD_TOWN = "INSERT INTO towns (name) VALUES (?) ";

    private static final String ADD_VILLAIN = "INSERT INTO villains (name) VALUES (?)";
    private static final String INSERT_INTO_MINIONS_VILLAINS =
            "INSERT INTO minions_villains VALUES (?, ?)";
    private static final String ADD_MINION =
            "INSERT INTO minions (name, age, town_id) VALUES (?, ?, ?)";

    private static final String ADDED_TOWN_MESSAGE = "Town %s was added to the database.";
    private static final String ADDED_VILLAIN_MESSAGE = "Villain %s was added to the database.";
    private static final String ADDED_MINION_MESSAGE = "Successfully added %s to be minion of %s.";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Connection connectionSQL = LogIn.logIn();

        List<String> minionsList = List.of(scanner.nextLine().split(" "));
        List<String> villainsList = List.of(scanner.nextLine().split(" "));

        String minionName = minionsList.get(1);
        int minionAge = Integer.parseInt(minionsList.get(2));
        String townName = minionsList.get(3);
        String villainName = villainsList.get(1);

        ResultSet resultForTown = getResultSet(connectionSQL, townName);
        addTownIfNotExist(resultForTown, connectionSQL, townName);

        ResultSet resultForVillain = getVillainId(connectionSQL, villainName);
        addVillainIfNotExist(resultForVillain, connectionSQL, villainName, minionName);

        addMinion(connectionSQL, minionName, minionAge, townName, villainName);
    }

    private static void addMinion(Connection connectionSQL, String minionName, int minionAge, String townName, String villainName) throws SQLException {
        PreparedStatement addMinion = connectionSQL.prepareStatement(ADD_MINION);
        addMinion.setString(1, minionName);
        addMinion.setInt(2, minionAge);

        ResultSet townResult = getResultSet(connectionSQL, townName);
        if (townResult.next()) {
            int townId = townResult.getInt("id");
            addMinion.setInt(3, townId);
            addMinion.executeUpdate();
        }

        System.out.printf((ADDED_MINION_MESSAGE) + "%n", minionName, villainName);
    }

    private static void addVillainIfNotExist(ResultSet resultForVillain, Connection connectionSQL, String villainName, String minionName)
            throws SQLException {
        if (!resultForVillain.next()) {
            PreparedStatement addVillain = connectionSQL.prepareStatement(ADD_VILLAIN);
            addVillain.setString(1, villainName);
            addVillain.executeUpdate();
            System.out.printf((ADDED_VILLAIN_MESSAGE) + "%n", villainName);
            setServant(connectionSQL, villainName, minionName);

        }
    }

    private static void setServant(Connection connectionSQL, String villainName, String minionName) throws SQLException {
        PreparedStatement insertIntoMinionsVillains =
                connectionSQL.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);

        ResultSet villainResult = getVillainId(connectionSQL, villainName);
        if (villainResult.next()) {
            int villainId = villainResult.getInt("id");
            insertIntoMinionsVillains.setInt(1, villainId);
        }

        ResultSet minionResult = getMinionId(connectionSQL, minionName);
        if (minionResult.next()) {
            int minionId = minionResult.getInt("id");
            insertIntoMinionsVillains.setInt(2, minionId);
            insertIntoMinionsVillains.executeUpdate();
        }
    }

    private static ResultSet getMinionId(Connection connectionSQL, String minionName) throws SQLException {
        PreparedStatement getMinionId = connectionSQL.prepareStatement(GET_MINION_ID_BY_NAME);
        getMinionId.setString(1, minionName);
        ResultSet minionResult = getMinionId.executeQuery();
        return minionResult;
    }

    private static ResultSet getVillainId(Connection connectionSQL, String villainName) throws SQLException {
        PreparedStatement getVillainId = connectionSQL.prepareStatement(GET_VILLAIN_ID_BY_NAME);
        getVillainId.setString(1, villainName);
        ResultSet resultSet = getVillainId.executeQuery();
        return resultSet;
    }

    private static void addTownIfNotExist(ResultSet resultForTown, Connection connectionSQL, String townName) throws SQLException {
        if (!resultForTown.next()) {
            PreparedStatement addTown = connectionSQL.prepareStatement(ADD_TOWN);
            addTown.setString(1, townName);
            addTown.executeUpdate();
            System.out.printf((ADDED_TOWN_MESSAGE) + "%n", townName);
        }
    }

    private static ResultSet getResultSet(Connection connectionSQL, String townName) throws SQLException {
        PreparedStatement isTownExist = connectionSQL.prepareStatement(GET_TOWN_BY_NAME);
        isTownExist.setString(1, townName);
        return isTownExist.executeQuery();
    }
}
