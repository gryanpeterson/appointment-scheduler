package grp.appointmentscheduler.controller;

import grp.appointmentscheduler.DAO.AppointmentDAO;
import grp.appointmentscheduler.DAO.ContactDAO;
import grp.appointmentscheduler.DAO.CustomerDAO;
import grp.appointmentscheduler.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import grp.appointmentscheduler.model.Contact;
import grp.appointmentscheduler.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/** The reports controller class.
 *
 */
public class ReportsController implements Initializable {
    Stage stage;
    Parent scene;
    /** The tableview column for the appointment ID.
     *
     */
    @FXML
    private TableColumn<?, ?> appointmentIDTableViewCol;
    /** The contact combobox.
     *
     */
    @FXML
    private ComboBox<Contact> contactComboBox;
    /** The contact schedule tableview.
     *
     */
    @FXML
    private TableView<Appointment> contactScheduleTableView;
    /** The tableview column for the contact.
     *
     */
    @FXML
    private TableColumn<?, ?> contactTableViewCol;
    /** The customer ID combobox.
     *
     */
    @FXML
    private ComboBox<Customer> customerIDComboBox;
    /** The customer ID count label.
     *
     */
    @FXML
    private Label customerIDCountLabel;
    /** The tableview column for the customer ID.
     *
     */
    @FXML
    private TableColumn<?, ?> customerIDTableViewCol;
    /** The tableview column for the description.
     *
     */
    @FXML
    private TableColumn<?, ?> descriptionTableVIewCol;
    /** The tableview column for the end date and time.
     *
     */
    @FXML
    private TableColumn<?, ?> endTableViewCol;
    /** The tableview column for the location.
     *
     */
    @FXML
    private TableColumn<?, ?> locationTableViewCol;
    /** The month combo box.
     *
     */
    @FXML
    private ComboBox<Integer> monthComboBox;
    /** The count label for the month/type report.
     *
     */
    @FXML
    private Label monthTypeCountLabel;
    /** The tableview column for the start date and time.
     *
     */
    @FXML
    private TableColumn<?, ?> startTableViewCol;
    /** The tableview column for the title.
     *
     */
    @FXML
    private TableColumn<?, ?> titleTableViewCol;
    /** The type combobox.
     *
     */
    @FXML
    private ComboBox<Appointment> typeComboBox;
    /** The tableview column for the type.
     *
     */
    @FXML
    private TableColumn<?, ?> typeTableViewCol;


    /** Method for the cancel button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Method for the get count button.
     *
     * @param event the event
     */
    @FXML
    void onActionGetCountButton(ActionEvent event) {

        // Report for month and type count
        ObservableList<Appointment> monthTypeList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID WHERE MONTH(Start) = ? AND Type = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, monthComboBox.getValue());
            ps.setString(2, typeComboBox.getValue().getType());

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
                monthTypeList.add(appointment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int monthTypeCount = 0;
        for (Appointment appointment : monthTypeList) {
            monthTypeCount += 1;
        }

        monthTypeCountLabel.setText(String.valueOf(monthTypeCount));

    }

    /** Method that initializes the controller.
     *
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Populates the month ComboBox with months as integers
        monthComboBox.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);

        // Populates the type ComboBox with all appointment types
        typeComboBox.setItems(AppointmentDAO.getAllAppointments());

        // Report for Total Customer Appointments By Customer ID
        customerIDComboBox.setItems(CustomerDAO.getAllCustomers());
        customerIDComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                int appointmentCount = 0;
                for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
                    if (appointment.getCustomerID() == customerIDComboBox.getValue().getCustomerID()) { //
                    appointmentCount += 1;
                    }
                }
                customerIDCountLabel.setText(String.valueOf(appointmentCount));
            }
        });

        // Report for showing Contact Schedule
        contactComboBox.setItems(ContactDAO.getAllContacts());
        contactComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                contactScheduleTableView.setItems(AppointmentDAO.getAppointmentsByContact(contactComboBox.getValue().getContactID()));
            }
        });


        appointmentIDTableViewCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleTableViewCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTableVIewCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationTableViewCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeTableViewCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTableViewCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTableViewCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDTableViewCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactTableViewCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }
}