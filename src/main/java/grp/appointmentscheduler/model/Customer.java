package grp.appointmentscheduler.model;

/** The customer class.
 *
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    private String divisionName;
    private String countryName;

    /** The constructor for the customer class.
     *
     * @param customerID the customer ID
     * @param customerName the customer name
     * @param address the address
     * @param postalCode the postal code
     * @param phoneNumber the phone number
     * @param divisionID the division ID
     * @param divisionName the division name
     * @param countryName the country name
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID, String divisionName, String countryName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    /** The getter for the customer ID.
     *
     * @return returns the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /** The setter for the customerID.
     *
     * @param customerID the customer ID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** The getter for the cusotmer name.
     *
     * @return returns the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /** The setter for the customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** The getter for the address.
     *
     * @return returns the address
     */
    public String getAddress() {
        return address;
    }

    /** The setter for the address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /** The getter for the postal code.
     *
     * @return returns the postalCode
     *
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** The setter for the postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** The getter for the phone number.
     *
     * @return returns the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** The setter for the phoneNumber.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** The getter for the division ID.
     *
     * @return returns the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /** The setter for the division ID.
     *
     * @param divisionID the division ID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** The getter for the division name.
     *
     * @return returns the divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /** The setter for the division name.
     *
     * @param divisionName the division name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /** The getter for the country name.
     *
     * @return returns the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /** The setter for the country name.
     *
     * @param countryName the country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /** Method that overrides the toString method.
     *
     * @return returns "[ID: " + customerID + "] - " + customerName
     */
    @Override
    public String toString(){
        return "[ID: " + customerID + "] - " + customerName;
    }
}
