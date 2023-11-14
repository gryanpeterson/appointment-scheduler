package grp.appointmentscheduler.controller;

import grp.appointmentscheduler.DAO.CustomerDAO;
import grp.appointmentscheduler.helper.Helper;
import grp.appointmentscheduler.helper.JDBC;
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
import grp.appointmentscheduler.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** The customer controller class.
 *
 */
public class CustomersController implements Initializable {
    Stage stage;
    Parent scene;
    /** The customer object selected by the user.
     *
     */
    private static Customer customerToModify;

    /** Gets the customer object selected by the user in the customer tableview.
     *
     * @return returns the selected customer object.
     */
    public static Customer getCustomerToModify() {
        return customerToModify;
    }

    /** The tableview column for the address.
     *
     */
    @FXML
    private TableColumn<?, ?> addressTableViewCol;
    /** The tableview column for the country.
     *
     */

    @FXML
    private TableColumn<?, ?> countryTableViewCol;
    /** The tableview column for the customer ID.
     *
     */

    @FXML
    private TableColumn<?, ?> customerIDTableViewCol;
    /** The tableview column for the customer name.
     *
     */

    @FXML
    private TableColumn<?, ?> customerNameTableViewCol;
    /** The tableview for the customers screen.
     *
     */

    @FXML
    private TableView<Customer> customersTableView;
    /** The tableview column for the phone number.
     *
     */

    @FXML
    private TableColumn<?, ?> phoneNumberTableViewCol;
    /** The tableview column for the postal code.
     *
     */

    @FXML
    private TableColumn<?, ?> postalCodeTableViewCol;
    /** The tableview column for the first level division.
     *
     */
    @FXML
    private TableColumn<?, ?> stateProvinceTableViewCol;

    /** Method that initializes the controller.
     *
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> customerList = CustomerDAO.getAllCustomers();

        customersTableView.setItems(customerList);

        customerIDTableViewCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameTableViewCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressTableViewCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        stateProvinceTableViewCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        postalCodeTableViewCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        countryTableViewCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        phoneNumberTableViewCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    /** Method for the add customer button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Method for the delete customer button.
     *
     * @param event the event
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        if (customersTableView.getSelectionModel().getSelectedItem() == null) {

            Helper.testAlert(2);
        }
        else {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            try {
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setInt(1, selectedCustomer.getCustomerID());
                ps.executeUpdate();

                customersTableView.setItems(CustomerDAO.getAllCustomers());


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Helper.testAlert(3);
        }
    }

    /** Method for the modify customer button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {

        customerToModify = customersTableView.getSelectionModel().getSelectedItem();

        if (customerToModify == null) {
            Helper.testAlert(2);
        }
        else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/ModifyCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Method for the view appointments button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionViewAppointments(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }
}