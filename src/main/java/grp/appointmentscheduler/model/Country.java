package grp.appointmentscheduler.model;

/** The country class.
 *
 */
public class Country {
    private int countryID;
    private String countryName;

    /** the constructor for the country class.
     *
     * @param countryID the country ID
     * @param countryName the country name
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /** The getter for the country ID.
     *
     * @return returns the countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /** The setter for the country ID.
     *
     * @param countryID the country ID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
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
     * @return returns the countryName
     */
    @Override
    public String toString(){
        return countryName;
    }
}
