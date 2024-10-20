package HospitalDatabase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final String GET_PATIENT_INFO = "FROM patient where CONCAT(firstName, ' ', last_name) = ?";
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of yore patient:");

        String patientName = scanner.nextLine();

        MyConnector.createConnection("root", "123456789", "bill-payment-system");

        PreparedStatement getPatientInfo = MyConnector.getConnection().prepareStatement(GET_PATIENT_INFO);
        getPatientInfo.setString(1, patientName);
        getPatientInfo.executeQuery();


    }
}
