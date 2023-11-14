package grp.appointmentscheduler.helper;
import grp.appointmentscheduler.DAO.ContactDAO;
import grp.appointmentscheduler.DAO.CountryDAO;
import grp.appointmentscheduler.DAO.FirstLevelDivisionsDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import grp.appointmentscheduler.model.Contact;
import grp.appointmentscheduler.model.Country;
import grp.appointmentscheduler.model.FirstLevelDivision;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

/** The grp.appointmentscheduler.helper class.
 *
 */
public class Helper {
    /** Method that converts a division name to division ID.
     *
     * @param divisionName the division name
     * @return returns divisionID the division ID
     */
    public static int divisionNameToDivisionID(String divisionName) {
        ObservableList<FirstLevelDivision> firstLevelDivisionObservableList = FirstLevelDivisionsDAO.getAllDivisions();
        int divisionID = 0;
        for (FirstLevelDivision fld : firstLevelDivisionObservableList) {
            if (fld.getDivisionName().equals(divisionName)) {
                divisionID = fld.getDivisionID();
            }
        }
        return divisionID;
    }

    /** Method that converts a contact name to a contact ID.
     *
     * @param contactName the contact name
     * @return returns contactID
     */
    public static int contactNameToContactID(String contactName) {
        ObservableList<Contact> contactObservableList = ContactDAO.getAllContacts();
        int contactID = 0;
        for (Contact contact : contactObservableList) {
            if (contact.getContactName().equals(contactName)) {
                contactID = contact.getContactID();
            }
        }
        return contactID;
    }

    /** Method that converts a country name to a country ID.
     *
     * @param countryName the country name
     * @return returns country ID
     */
    public static int countryNameToCountryID(String countryName) {
        ObservableList<Country> countryObservableList = CountryDAO.getAllCountries();
        int countryID = 0;
        for (Country country : countryObservableList) {
            if (country.getCountryName().equals(countryName)) {
                countryID = country.getCountryID();
            }
        }
        return countryID;
    }

    /** Method that converts a division ID to a division name.
     *
     * @param divisionID the division ID
     * @return returns the divisionName
     */
    public static String divisionIDToDivisionName(int divisionID) {
        ObservableList<FirstLevelDivision> firstLevelDivisionObservableList = FirstLevelDivisionsDAO.getAllDivisions();
        String divisionName = null;
        for (FirstLevelDivision fld : firstLevelDivisionObservableList) {
            if (fld.getDivisionID() == divisionID) {
                divisionName = fld.getDivisionName();
            }
        }
        return divisionName;
    }

    /** Method that converts a contact ID to a contact name.
     *
     * @param contactID the contact ID
     * @return returns the contactName
     */
    public static String contactIDToContactName(int contactID) {
        ObservableList<Contact> contactObservableList = ContactDAO.getAllContacts();
        String contactName = null;
        for (Contact contact : contactObservableList) {
            if (contact.getContactID() == contactID) {
                contactName = contact.getContactName();
            }
        }
        return contactName;
    }

    /** Method that provides various alerts.
     *
     * @param alertNumber the alert number
     */
    public static void testAlert(int alertNumber) {
        ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());

        switch (alertNumber) {
            case 1:
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("Field(s) Empty");
                alert1.setContentText("Field(s) cannot be left blank.");
                alert1.showAndWait();
                break;
            case 2:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("No Customer Selected");
                alert2.setContentText("Please select a customer in the table to modify or delete.");
                alert2.showAndWait();
                break;
            case 3:
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setTitle("Success");
                alert3.setHeaderText("Customer Record Deleted");
                alert3.setContentText("The customer record has been deleted.");
                alert3.showAndWait();
                break;
            case 4:
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Error");
                alert4.setHeaderText("No Appointment Selected");
                alert4.setContentText("Please select an appointment in the table to modify or delete.");
                alert4.showAndWait();
                break;
            case 5:
                Alert alert5 = new Alert(Alert.AlertType.ERROR);
//                alert5.setTitle("Error");
                alert5.setHeaderText(rb.getString("invalidLoginHeader"));
                alert5.setContentText(rb.getString("invalidLoginText"));
                alert5.showAndWait();
                break;

        }
    }

    /** Method that checks if a time is outside business hours.
     *
     * @param localDateTime the local date and time
     * @return returns a boolean
     */
    public static boolean isOutsideBusinessHours(LocalDateTime localDateTime) {

        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        ZoneId systemZoneID = ZoneId.systemDefault();
        ZoneId estZoneID = ZoneId.of("America/New_York");
        ZonedDateTime systemZonedDateTime = ZonedDateTime.of(localDate, localTime, systemZoneID);
        Instant localToESTInstant = systemZonedDateTime.toInstant();
        ZonedDateTime estZonedDateTime = ZonedDateTime.ofInstant(localToESTInstant, estZoneID);
        LocalDateTime localDateTimeEST = estZonedDateTime.toLocalDateTime();

        LocalTime businessHour = localDateTimeEST.toLocalTime();
        LocalTime start = LocalTime.of(8,0);
        LocalTime end = LocalTime.of(22,0);
        if (businessHour.isBefore(start) || businessHour.isAfter(end))
            return true;
        return false;
    }
}
