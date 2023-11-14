package grp.appointmentscheduler.model;

/** The first level division class.
 *
 */
public class FirstLevelDivision {

    private int divisionID;
    private String divisionName;
    private int countryID;

    /** The constructor for the first level division class.
     *
     * @param divisionID the division ID
     * @param divisionName the division name
     * @param countryID the country ID
     */
    public FirstLevelDivision(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /** The getter for the division ID.
     *
     * @return return the divisionID
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

    /** Method that overrides the toString method.
     *
     * @return returns the divisionName
     */
    @Override
    public String toString(){
        return divisionName;
    }
}
