package grp.appointmentscheduler.DAO;

import grp.appointmentscheduler.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import grp.appointmentscheduler.model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** grp.appointmentscheduler.DAO Class for Customers.
 *
 */
public class CustomerDAO {
    /** Method that gets all customers.
     *
     * @return returns the Observable List of all customers.
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                String countryName = rs.getString("Country");

                Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber, divisionID, divisionName, countryName);

                customerObservableList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerObservableList;
    }
}
