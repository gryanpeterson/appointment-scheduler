package grp.appointmentscheduler.controller;

import grp.appointmentscheduler.DAO.CountryDAO;
import grp.appointmentscheduler.DAO.FirstLevelDivisionsDAO;
import grp.appointmentscheduler.controller.CustomersController;
import grp.appointmentscheduler.helper.Helper;
import grp.appointmentscheduler.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import grp.appointmentscheduler.model.Country;
import grp.appointmentscheduler.model.Customer;
import grp.appointmentscheduler.model.FirstLevelDivision;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** The modify customers controller class.
 *
 */
public class ModifyCustomerController implements Initializable {
    Stage stage;
    Parent scene;
    /** The customer object selected by the user in the customers screen.
     *
     */
    private Customer selectedCustomer;
    /** The address text field.
     *
     */
    @FXML
    private TextField addressTextField;
    /** The country combo box.
     *
     */
    @FXML
    private ComboBox<Country> countryComboBox;
    /** The customer ID text field.
     *
     */
    @FXML
    private TextField customerIDTextField;
    /** The customer name text field.
     *
     */
    @FXML
    private TextField customerNameTextField;
    /** The phone number text field.
     *
     */
    @FXML
    private TextField phoneNumberTextField;
    /** The postal code text field.
     *
     */
    @FXML
    private TextField postalCodeTextField;
    /** The first level division combo box.
     *
     */
    @FXML
    private ComboBox<FirstLevelDivision> stateProvinceComboBox;

    /** Method that initializes the controller.
     *
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryComboBox.setItems(CountryDAO.getAllCountries());
        // Creates Observable Lists Containing That Will Contain Each Countries Provinces.
        ObservableList<FirstLevelDivision> usStatesList = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> canadaProvincesList = FXCollections.observableArrayList();
        ObservableList<FirstLevelDivision> ukDivisionsList = FXCollections.observableArrayList();

        // Adds Each Countries Respective Divisions To Their List.
        for (FirstLevelDivision state : FirstLevelDivisionsDAO.getAllDivisions()) {
            if (state.getCountryID() == 1) {
                usStatesList.add(state);
            } else if (state.getCountryID() == 2) {
                ukDivisionsList.add(state);
            }
            else {
                canadaProvincesList.add(state);
            }
        }

        // Disables State ComboBox until a Country is selected in the Country ComboBox
        stateProvinceComboBox.disableProperty().bind(countryComboBox.getSelectionModel().selectedItemProperty().isNull());

        countryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getCountryID() == 1) {
                stateProvinceComboBox.setItems(usStatesList);
            } else if (newValue.getCountryID() == 2) {
                stateProvinceComboBox.setItems(ukDivisionsList);
            }
            else {
                stateProvinceComboBox.setItems(canadaProvincesList);
            }
        });

        selectedCustomer = CustomersController.getCustomerToModify();

        int divisionID = selectedCustomer.getDivisionID();
        String divisionName = selectedCustomer.getDivisionName();
        int countryID = Helper.countryNameToCountryID(selectedCustomer.getCountryName());
        FirstLevelDivision customerDivision = new FirstLevelDivision(divisionID, divisionName, countryID);
        Country customerCountry = new Country(countryID, selectedCustomer.getCountryName());


        customerNameTextField.setText(selectedCustomer.getCustomerName());
        addressTextField.setText(selectedCustomer.getAddress());
        stateProvinceComboBox.setValue(customerDivision);
        stateProvinceComboBox.setItems(FirstLevelDivisionsDAO.getAllDivisions());
        countryComboBox.setValue(customerCountry);
        countryComboBox.setItems(CountryDAO.getAllCountries());
        postalCodeTextField.setText(selectedCustomer.getPostalCode());
        phoneNumberTextField.setText(selectedCustomer.getPhoneNumber());

    }

    /** Method for the cancel button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Method for the save button.
     *
     * @param event the event
     * @throws IOException the IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, customerNameTextField.getText());
            ps.setString(2,addressTextField.getText());
            ps.setString(3, postalCodeTextField.getText());
            ps.setString(4, phoneNumberTextField.getText());
            ps.setInt(5, Helper.divisionNameToDivisionID(String.valueOf(stateProvinceComboBox.getSelectionModel().getSelectedItem())));
            ps.setInt(6, selectedCustomer.getCustomerID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/Customers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}