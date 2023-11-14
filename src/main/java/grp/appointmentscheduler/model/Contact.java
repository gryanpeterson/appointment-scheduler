package grp.appointmentscheduler.model;

/** The contact class.
 *
 */
public class Contact {
    private int contactID;
    private String contactName;

    /** The constructor for the contact class.
     *
     * @param contactID the contact ID
     * @param contactName the contact name
     */
    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /** The getter for the contact ID.
     *
     * @return returns the contactID
     */
    public int getContactID() {
        return contactID;
    }

    /** The setter for the contact ID
     *
     * @param contactID the contact ID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** The getter for the contact name.
     *
     * @return returns the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /** The setter for the contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /** Method that overrides the toString method.
     *
     * @return returns the contactName
     */
    @Override
    public String toString(){
        return contactName;
    }
}
