//package HospitalManagementSystem;
//import java.sql.*;
//import java.util.Scanner;
//
//public class HospitalManagementSystem {
//    private static final String url = "jdbc:mysql://localhost:3306/hospital";
//    private static final String username = "root";
//    private static final String password = "Sushant12@#";
//
//    public static void main(String[] args) {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Scanner sc = new Scanner(System.in);
//        try {
//            Connection connection = DriverManager.getConnection(url, username, password);
//            Patient patient = new Patient(connection, sc);
//            Doctor doctor = new Doctor(connection);
//            while (true) {
//                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
//                System.out.println("1. Add Patient");
//                System.out.println("2. View Patient");
//                System.out.println("3. View Doctor");
//                System.out.println("4. Book Appointment");
//                System.out.println("5. Exit");
//                System.out.println("Enter your choice");
//                int choice = sc.nextInt();
//
//                switch (choice) {
//                    case 1:
//                        //Add patient
//                        patient.addPatient();
//                        System.out.println();
//                        break;
//                    case 2:
//                        //view patient
//                        patient.viewPatients();
//                        System.out.println();
//                        break;
//                    case 3:
//                        // view doctors
//                        doctor.viewDoctor();
//                        System.out.println();
//                        break;
//                    case 4:
//                        //book appointment
//                        bookappoinment(patient, doctor, connection, sc);
//                        System.out.println();
//                        break;
//
//                    case 5:
//                        //exit
//                        return;
//                    default:
//                        System.out.println("Enter valid choice");
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void bookappoinment(Patient patient,Doctor doctor,Connection connection, Scanner scanner) {
//        System.out.println("Enter Patient ID");
//        int patientID = scanner.nextInt();
//        System.out.println("Enter Doctor ID");
//        int doctorID = scanner.nextInt();
//        System.out.println("Enter Appointment Date (YYYY-MM-DD):");
//        String appointmentDate = scanner.next();
//        if (patient.getPatientBYID(patientID) && doctor.getDoctorBYID(doctorID)){
//            if (checkDoctorAvailability(doctorID,appointmentDate, connection)){
//                String appointmentQuery = "INSERT INTO appointments (patient_id,doctor_id,appointments_date) VALUES (?,?,?)";
//                try{
//                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
//                    preparedStatement.setInt(1, patientID);
//                    preparedStatement.setInt(2, doctorID);
//                    preparedStatement.setString(3, appointmentDate);
//                   int rowAffected= preparedStatement.executeUpdate();
//                   if(rowAffected>0){
//                       System.out.println("Appointment Booked");
//                   }else {
//                       System.out.println("Appointment Not Booked");
//                   }
//                } catch (SQLException e){
//                    e.printStackTrace();
//                }
//
//            }else{
//                System.out.println("Doctor is not available on this date");
//            }
//            System.out.println("Patient already exists");
//        } else{
//            System.out.println("Either docotor or doctor does not exist");
//        }
//    }
//    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection connection) {
//        String query ="SELECT COUNT(*) FROM appointments WHERE appointments_date=? AND appointments_date=?";
//        try{
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, doctorID);
//            preparedStatement.setString(2, appointmentDate);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()){
//                int count = resultSet.getInt(1);
//                if(count == 1){
//                    return false;
//                } else{
//                    return true;
//                }
//            }
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return true;
//
//    }
//
//}

package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Sushant12@#";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, sc);
            Doctor doctor = new Doctor(connection);
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice:");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, connection, sc);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Enter valid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter Patient ID:");
        int patientID = scanner.nextInt();
        System.out.println("Enter Doctor ID:");
        int doctorID = scanner.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD):");
        String appointmentDate = scanner.next();

        // Check if both patient and doctor exist
        if (patient.getPatientBYID(patientID) && doctor.getDoctorBYID(doctorID)) {
            // Check doctor availability for the given date
            if (checkDoctorAvailability(doctorID, appointmentDate, connection)) {
                // Book the appointment
                String appointmentQuery = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientID);
                    preparedStatement.setInt(2, doctorID);
                    preparedStatement.setString(3, appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appointment Booked");
                    } else {
                        System.out.println("Appointment Not Booked");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor is not available on this date");
            }
        } else {
            System.out.println("Either patient or doctor does not exist");
        }
    }

    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // If count is 0, doctor is available; otherwise, not available
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

