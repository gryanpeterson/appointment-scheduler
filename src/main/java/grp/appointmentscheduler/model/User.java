package grp.appointmentscheduler.model;

/** The user class.
 *
 */
public class User {
    private int userID;
    private String userName;
    private String password;

    /** The constructor for the user class.
     *
     * @param userID the user ID
     * @param userName the username
     * @param password the password
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
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

    /** The getter for the username.
     *
     * @return returns the userName
     */
    public String getUserName() {
        return userName;
    }

    /** The setter for the userName.
     *
     * @param userName the username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** The getter for the password.
     *
     * @return returns the password
     */
    public String getPassword() {
        return password;
    }

    /** The setter for the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
