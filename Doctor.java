package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) { // Constructor invoking
        this.connection = connection;
    }

    public void viewDoctor() {
        String query = "SELECT * FROM Doctor";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors:");
            System.out.println("+------------+----------------------+--------------------------+");
            System.out.println("| Doctor ID  | Name                 | Specialization           |");
            System.out.println("+------------+----------------------+--------------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|%-12d|%-20s|%-25s|\n", id, name, specialization);
                System.out.println("+------------+----------------------+--------------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorBYID(int id) {
        String query = "SELECT * FROM Doctor WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
}
}