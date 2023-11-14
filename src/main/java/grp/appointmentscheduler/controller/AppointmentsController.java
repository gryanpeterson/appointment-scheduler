package grp.appointmentscheduler.controller;

import grp.appointmentscheduler.DAO.AppointmentDAO;
import grp.appointmentscheduler.helper.Helper;
import grp.appointmentscheduler.helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import grp.appointmentscheduler.model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** The appointments controller class.
 *
 */
public class AppointmentsController implements Initializable {
    Stage stage;
    Parent scene;
    /** The appointment object selected by the user in the table view.
     *
     */
    private static Appointment appointmentToModify;

    /** Gets the appointment object selected by the user in the table view.
     *
     * @return returns the appointment object.
     */
    public static Appointment getAppointmentToModify() {
        return appointmentToModify;
    }

    /** The tableview column for the appointments ID.
     *
     */
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDTableViewCol;
    /** The appointment tableview.
     *
     */
    @FXML
    private TableView<Appointment> appointmentTableView;
    /** The tableview column for the contact.
     *
     */
    @FXML
    private TableColumn<Appointment, String> contactTableViewCol;
    /** The tableview column for the customer ID.
     *
     */

    @FXML
    private TableColumn<Appointment, Integer> customerIDTableViewCol;
    /** The tableview column for the description.
     *
     */

    @FXML
    private TableColumn<Appointment, String> descriptionTableVIewCol;
    /** The tableview column for the end date and time.
     *
     */
    @FXML
    private TableColumn<Appointment, String> endTableViewCol;
    /** The tableview column for the location.
     *
     */
    @FXML
    private TableColumn<Appointment, String> locationTableViewCol;
    /** The tableview column for the start date and time.
     *
     */
    @FXML
    private TableColumn<Appointment, String> startTableViewCol;
    /** The tableview column for the title.
     *
     */
    @FXML
    private TableColumn<Appointment, String> titleTableViewCol;
    /** The tableview column for the type.
     *
     */
    @FXML
    private TableColumn<Appointment, String> typeTableViewCol;
    /** The tableview column for the user ID.
     *
     */
    @FXML
    private TableColumn<Appointment, Integer> userIDTableViewCol;
    /** The radio button for the viewing of all appointments.
     *
     */
    @FXML
    private RadioButton viewAllRB;
    /** The radio button for the viewing of appointment for the current month.
     *
     */
    @FXML
    private RadioButton viewByMonthRB;
    /** The radio button for the viewing of appointments for the current week.
     *
     */
    @FXML
    private RadioButton viewByWeekRB;
    /** The toggle group for the radio button views.
     *
     */
    @FXML
    private ToggleGroup viewToggleGroup;

    /** Method that initializes the controller.
     *
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTableView.setItems(AppointmentDAO.getAllAppointments());
        viewAllRB.setSelected(true);

        appointmentIDTableViewCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleTableViewCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableVIewCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableViewCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeTableViewCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTableViewCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTableViewCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDTableViewCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactTableViewCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        userIDTableViewCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /** Method for the add appointment button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Method for the delete appointment button.
     *
     * @param event the event
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Helper.testAlert(4);
        }
        else {
            int deletedAppointmentID = selectedAppointment.getAppointmentID();
            String deletedAppointmentType = selectedAppointment.getType();

            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            try {
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setInt(1, selectedAppointment.getAppointmentID());
                ps.executeUpdate();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            appointmentTableView.setItems(AppointmentDAO.getAllAppointments());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Appointment Deleted");
            alert.setContentText("Appointment ID: " + deletedAppointmentID + " with Type: " + deletedAppointmentType + " has been deleted.");
            alert.showAndWait();
        }

    }

    /** Method for the modify appointment button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {

        appointmentToModify = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointmentToModify == null) {
            Helper.testAlert(4);
        }
        else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/ModifyAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /** Method for the view by month radio button.
     *
     * @param event the event
     */
    @FXML
    void onActionViewByMonthRB(ActionEvent event) {
        appointmentTableView.setItems(AppointmentDAO.getCurrentMonthAppointments());

    }

    /** Method for the view by week radio button.
     *
     * @param event the event
     */
    @FXML
    void onActionViewByWeekRB(ActionEvent event) {
        appointmentTableView.setItems(AppointmentDAO.getCurrentWeekAppointments());

    }

    /** Method for the view all radio button.
     *
     * @param event the event
     */
    @FXML
    void onActionViewAllRB(ActionEvent event) {
        appointmentTableView.setItems(AppointmentDAO.getAllAppointments());

    }

    /** Method for the view customers button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionViewCustomers(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Method for the view reports button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionViewReports(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}