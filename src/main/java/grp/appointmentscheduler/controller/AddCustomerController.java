package grp.appointmentscheduler.controller;

import grp.appointmentscheduler.DAO.CountryDAO;
import grp.appointmentscheduler.DAO.FirstLevelDivisionsDAO;
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
import grp.appointmentscheduler.model.FirstLevelDivision;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

/** The AddCustomer Controller Class.
 *
 */
public class AddCustomerController implements Initializable {
    Stage stage;
    Parent scene;
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
    /** THe phone number text field.
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

    /** Method that initializes the controller. A first and second lambda are used in this method.
     * The first lambda is used to loop through each of the first level divisions and add them to
     * their appropriate first level division list based on their country ID.
     * The lambda is used to simplify the code for the for each loop, making it more readable.
     * The second lambda is used to add a listener to the country combo box. The lambda simplifies
     * the code needed to add a listener to the combo box.
     *
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

        // LAMBDA # 1 (The first lambda is used to loop through each of the first level divisions and add them to
        // their appropriate first level division list based on their country ID.
        // The lambda is used to simplify the code for the for each loop, making it more readable.)
        FirstLevelDivisionsDAO.getAllDivisions().forEach(firstLevelDivision -> {
            if (firstLevelDivision.getCountryID() == 1) {
                usStatesList.add(firstLevelDivision);
            } else if (firstLevelDivision.getCountryID() == 2) {
                ukDivisionsList.add(firstLevelDivision);
            }
            else {
                canadaProvincesList.add(firstLevelDivision);
            }
        });
        // Disables State ComboBox until a Country is selected in the Country ComboBox
        stateProvinceComboBox.disableProperty().bind(countryComboBox.getSelectionModel().selectedItemProperty().isNull());

        // LAMBDA #2 (The second lambda is used to add a listener to the country combo box. The lambda simplifies
        // the code needed to add a listener to the combo box.)
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

       if (customerNameTextField.getText().isEmpty() || addressTextField.getText().isEmpty()
               || postalCodeTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty()) {
           Helper.testAlert(1);
       }
       else {
           String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
           try {
               PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
               ps.setString(1, customerNameTextField.getText());
               ps.setString(2, addressTextField.getText());
               ps.setString(3, postalCodeTextField.getText());
               ps.setString(4, phoneNumberTextField.getText());
               ps.setInt(5, Helper.divisionNameToDivisionID(String.valueOf(stateProvinceComboBox.getSelectionModel().getSelectedItem())));
               ps.executeUpdate();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }

           stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
           scene = FXMLLoader.load(getClass().getResource("/Customers.fxml"));
           stage.setScene(new Scene(scene));
           stage.show();
       }
    }
}