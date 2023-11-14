package grp.appointmentscheduler.model;

import java.time.LocalDateTime;

/** The appointment class.
 *
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;
    private int contactID;
    private String contact;

    /** The constructor for the appointment class.
     *
     * @param appointmentID the appointment ID
     * @param title the title
     * @param description the description
     * @param location the location
     * @param type the type
     * @param start the start date and time
     * @param end the end date and time
     * @param customerID the customer ID
     * @param userID the user ID
     * @param contactID the contact ID
     * @param contact the contact
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID, String contact) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contact = contact;
    }

    /** The getter for the appointment ID.
     *
     * @return returns the appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /** The setter for the appointment ID.
     *
     * @param appointmentID the appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /** The getter for the title.
     *
     * @return returns the title
     */
    public String getTitle() {
        return title;
    }

    /** The setter for the title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** The getter for the description.
     *
     * @return returns the description
     */
    public String getDescription() {
        return description;
    }

    /** The setter for the description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** The getter for the location.
     *
     * @return returns the location
     */
    public String getLocation() {
        return location;
    }

    /** The setter for the location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** The getter for the type.
     *
     * @return returns the type
     */
    public String getType() {
        return type;
    }

    /** The setter for the type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** The getter for the start date and time.
     *
     * @return returns the start date and time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /** The setter for the start date and time.
     *
     * @param start the start date and time
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** The getter for the end date and time.
     *
     * @return returns the end date and time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /** The setter for the end date and time.
     *
     * @param end the end date and time
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** The getter for the customer ID.
     *
     * @return returns the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /** The setter for the customer ID.
     *
     * @param customerID the customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** The getter for the user ID.
     *
     * @return returns the userID
     */
    public int getUserID() {
        return userID;
    }

    /** The setter for the user ID.
     *
     * @param userID the user ID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** The getter for the contact ID.
     *
     * @return returns the contactID
     */
    public int getContactID() {
        return contactID;
    }

    /** The setter for the contact ID.
     *
     * @param contactID the contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** The getter for the contact.
     *
     * @return returns the contact
     */
    public String getContact() {
        return contact;
    }

    /** The setter for the contact.
     *
     * @param contact the contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /** Method that overrides the toString method.
     *
     * @return returns the type
     */
    @Override
    public String toString(){
        return type;
    }
}
