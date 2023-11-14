package grp.appointmentscheduler.controller;

import grp.appointmentscheduler.DAO.AppointmentDAO;
import grp.appointmentscheduler.helper.Helper;
import grp.appointmentscheduler.helper.JDBC;
import grp.appointmentscheduler.model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

/** The login controller class.
 *
 */
public class LoginController implements Initializable {
    Stage stage;
    Parent scene;
    /** The login button label.
     *
     */
    @FXML
    private Button loginButtonLabel;
    /** The password label.
     *
     */
    @FXML
    private Label passwordLabel;
    /** The password text field.
     *
     */
    @FXML
    private TextField passwordTextField;
    /** The time zone label.
     *
     */
    @FXML
    private Label timeZoneLabel;
    /** The username label.
     *
     */
    @FXML
    private Label userNameLabel;
    /** The users current time zone label.
     *
     */
    @FXML
    private Label userTimeZoneLabel;
    /** The username text field.
     *
     */
    @FXML
    private TextField usernameTextField;

    /** Method for the login button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {

        String usernameInput = usernameTextField.getText();
        String passwordInput = passwordTextField.getText();

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter output = new PrintWriter(fileWriter);

        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, usernameInput);
            ps.setString(2, passwordInput);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                if (usernameInput.equals(userName) && passwordInput.equals(password)) {
                    output.print("User: " + usernameInput + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/Appointments.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                    LocalDate apptDate = null;
                    LocalTime apptTime = null;
                    LocalTime currentTime = LocalTime.now();
                    boolean apptSoon = false;
                    int apptID = 0;
                    long timeDifference = 0;

                    for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
                        apptDate = appointment.getStart().toLocalDate();
                        apptTime = appointment.getStart().toLocalTime();
                        timeDifference = (ChronoUnit.MINUTES.between(apptTime, currentTime) + -1) * -1;
                        if (timeDifference <= 15 && timeDifference >= 0) {
                            apptDate = appointment.getStart().toLocalDate();
                            apptID = appointment.getAppointmentID();
                            apptTime = appointment.getStart().toLocalTime();
                            apptSoon = true;
                        }
                    }
                    if (apptSoon == true) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Notification");
                        alert.setHeaderText("APPOINTMENT IN " + timeDifference + " MINUTE(S)!");
                        alert.setContentText("Appointment ID: " + apptID + " Date: " + apptDate + " Time: " + apptTime);
                        alert.showAndWait();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Notification");
                        alert.setHeaderText("No Upcoming Appointments");
                        alert.setContentText("There are no appointments within 15 minutes");
                        alert.showAndWait();
                    }
                }
            }
            else {
                output.print("User: " + usernameInput + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                Helper.testAlert(5);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        output.close();
    }

    /** Method that initializes the controller.
     *
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());

        // Locale userLocale = Locale.getDefault();
        userNameLabel.setText(rb.getString("userNameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        loginButtonLabel.setText(rb.getString("loginButtonLabel"));
        timeZoneLabel.setText(rb.getString("timeZoneLabel"));
        userTimeZoneLabel.setText(ZoneId.systemDefault().toString());
    }
}