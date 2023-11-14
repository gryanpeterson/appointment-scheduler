package grp.appointmentscheduler.DAO;

import grp.appointmentscheduler.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import grp.appointmentscheduler.model.Appointment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** The grp.appointmentscheduler.DAO class for Appointments.
 *
 */
public class AppointmentDAO {
    /** Method that gets all appointments from the database.
     *
      * @return returns the Observable List of appointments.
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID";
        try {
            // Makes the prepared statement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            // Makes the query
            ResultSet rs = ps.executeQuery();
            // Cycle through the result
            while (rs.next()) {
                // Extracts data from the database
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                // Makes an object instance
                Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID, contact);

                // Add object to list
                appointmentObservableList.add(appointment);
            }

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentObservableList;
    }

    /** Method that gets all appointments in the current month.
     *
     * @return returns the Observable List of appointments in the current month.
     */
    public static ObservableList<Appointment> getCurrentMonthAppointments() {
        ObservableList<Appointment> currentMonthAppointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE MONTH(Start) = MONTH(CURRENT_DATE)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID, contact);

                currentMonthAppointments.add(appointment);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return currentMonthAppointments;
    }

    /** Method that get all appointments in the current week.
     *
     * @return returns the Observable List of appointments in the current week.
     */
    public static ObservableList<Appointment> getCurrentWeekAppointments() {
        ObservableList<Appointment> currentWeekAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE WEEK(Start) = WEEK(CURRENT_DATE)";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID, contact);
                currentWeekAppointments.add(appointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currentWeekAppointments;
    }

    /** Method that gets all appointments by Contact ID.
     *
     * @param theContactID the contact ID
     * @return returns the Observable List of appointments by Contact ID
     */
    public static ObservableList<Appointment> getAppointmentsByContact(int theContactID) {
        ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE appointments.Contact_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, theContactID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contact = rs.getString("Contact_Name");

                Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, customerID, userID, contactID, contact);
                appointmentsByContact.add(appointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return appointmentsByContact;
    }
}
