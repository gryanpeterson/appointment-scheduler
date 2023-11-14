package grp.appointmentscheduler.controller;

import grp.appointmentscheduler.DAO.AppointmentDAO;
import grp.appointmentscheduler.DAO.ContactDAO;
import grp.appointmentscheduler.helper.Helper;
import grp.appointmentscheduler.helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import grp.appointmentscheduler.model.Appointment;
import grp.appointmentscheduler.model.Contact;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/** AddAppointmentController Class
 *
 */
public class AddAppointmentController implements Initializable {
    Stage stage;
    Parent scene;
    /** The appointment ID text field.
     *
     */
    @FXML
    private TextField appointmentIDTextField;
    /** The Contact Combo Box.
     *
     */
    @FXML
    private ComboBox<Contact> contactComboBox;
    /** The customer ID text field.
     *
     */
    @FXML
    private TextField customerIDTextField;
    /** The description text field.
     *
     */
    @FXML
    private TextField descriptionTextField;
    /** The end date datepicker widget.
     *
     */
    @FXML
    private DatePicker endDateDatePicker;
    /** The location text field.
     *
     */
    @FXML
    private TextField locationTextField;
    /** The start date datepicker widget.
     *
     */
    @FXML
    private DatePicker startDateDatePicker;
    /** The end time text field.
     *
     */
    @FXML
    private TextField endTimeTextField;
    /** The start time text field
     *
     */
    @FXML
    private TextField startTimeTextField;
    /** The title text field.
     *
     */
    @FXML
    private TextField titleTextField;
    /** The type text field.
     *
     */
    @FXML
    private TextField typeTextField;
    /** The user ID text field.
     *
     */
    @FXML
    private TextField userIDTextField;

    /** Method that initializes the controller.
     *
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDateDatePicker.setValue(LocalDate.now());
        endDateDatePicker.setValue(LocalDate.now());
        contactComboBox.setItems(ContactDAO.getAllContacts());
    }

    /** Method for the cancel button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Method for the save button
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        if (titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty() || contactComboBox.getValue() == null || customerIDTextField.getText().isEmpty() || userIDTextField.getText().isEmpty() || startDateDatePicker.getValue() == null || endDateDatePicker.getValue() == null || startTimeTextField.getText().isEmpty() || endTimeTextField.getText().isEmpty()) {
            Helper.testAlert(1);
        }
        else {
            // Gets start LocalDate and start LocalTime and converts to LocalDateTime
            LocalDate startDate = startDateDatePicker.getValue();
            String startTimeText = startTimeTextField.getText();
            LocalTime startTime = LocalTime.parse(startTimeText);
            LocalDateTime startLDT = LocalDateTime.of(startDate, startTime);

            // Gets end LocalDate and end LocalTime and converts to LocalDateTime
            LocalDate endDate = endDateDatePicker.getValue();
            String endTimeText = endTimeTextField.getText();
            LocalTime endTime = LocalTime.parse(endTimeText);
            LocalDateTime endLDT = LocalDateTime.of(endDate, endTime);

            int customerID = Integer.parseInt(customerIDTextField.getText());

            if (Helper.isOutsideBusinessHours(startLDT)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Scheduling Appointment");
                alert.setHeaderText("Appointment Start Time Not Within Business Hours");
                alert.setContentText("Business Hours are between the times of 08:00 - 22:00 EST");
                alert.showAndWait();

                System.out.println("Start time is outside of business hours");
            } else if (Helper.isOutsideBusinessHours(endLDT)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Scheduling Appointment");
                alert.setHeaderText("Appointment End Time Not Within Business Hours");
                alert.setContentText("Business Hours are between the times of 08:00 - 22:00 EST");
                alert.showAndWait();
                System.out.println("End time is outside of business hours");
            } else {
                for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
                    LocalDateTime dbStart = appointment.getStart();
                    LocalDateTime dbEnd = appointment.getEnd();
                    if (customerID == appointment.getCustomerID()) {
                        // Start In Window
                        if ((startLDT.isAfter(dbStart) || startLDT.isEqual(dbStart)) && startLDT.isBefore(dbEnd)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Scheduling Appointment");
                            alert.setHeaderText("Appointment Overlap");
                            alert.setContentText("New appointment date and time for Customer ID: " + customerID + " overlaps with existing Appointment ID: " + appointment.getAppointmentID());
                            alert.showAndWait();
                            return;
                        }
                        // End in Window
                        if ((endLDT.isBefore(dbEnd) || endLDT.isEqual(dbEnd)) && endLDT.isAfter(dbStart)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Scheduling Appointment");
                            alert.setHeaderText("Appointment Overlap");
                            alert.setContentText("New appointment date and time for Customer ID: " + customerID + " overlaps with existing Appointment ID: " + appointment.getAppointmentID());
                            alert.showAndWait();
                            return;
                        }
                        // Start & End in Window
                        if ((startLDT.isBefore(dbStart) || startLDT.isEqual(dbStart)) && (endLDT.isAfter(dbEnd) || endLDT.isEqual(dbEnd))) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Scheduling Appointment");
                            alert.setHeaderText("Appointment Overlap");
                            alert.setContentText("New appointment date and time for Customer ID: " + customerID + " overlaps with existing Appointment ID: " + appointment.getAppointmentID());
                            alert.showAndWait();
                            return;
                        }
                    }
                }
                String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                    Timestamp startTS = Timestamp.valueOf(startLDT);
                    Timestamp endTS = Timestamp.valueOf(endLDT);
                    ps.setString(1, titleTextField.getText());
                    ps.setString(2, descriptionTextField.getText());
                    ps.setString(3, locationTextField.getText());
                    ps.setString(4, typeTextField.getText());
                    ps.setTimestamp(5, startTS);
                    ps.setTimestamp(6, endTS);
                    ps.setInt(7, Integer.parseInt(customerIDTextField.getText()));
                    ps.setInt(8, Integer.parseInt(userIDTextField.getText()));
                    ps.setInt(9, Helper.contactNameToContactID(String.valueOf(contactComboBox.getValue())));
                    ps.executeUpdate();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/Appointments.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }
}