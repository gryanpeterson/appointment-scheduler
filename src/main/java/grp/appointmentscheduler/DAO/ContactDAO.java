package grp.appointmentscheduler.DAO;

import grp.appointmentscheduler.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import grp.appointmentscheduler.model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** The grp.appointmentscheduler.DAO Class for Contacts.
 *
 */
public class ContactDAO {
    /** Method that gets all contacts.
     *
     * @return returns The Observable List of all contacts.
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");


                Contact contact = new Contact(contactID, contactName);

                contactObservableList.add(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactObservableList;
    }
}
