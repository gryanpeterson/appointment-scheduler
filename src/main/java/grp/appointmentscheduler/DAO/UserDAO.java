package grp.appointmentscheduler.DAO;

import grp.appointmentscheduler.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import grp.appointmentscheduler.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** grp.appointmentscheduler.DAO Class for Users.
 *
 */
public class UserDAO {
    /** Method that gets all users.
     *
     * @return returns the Observable List of all users.
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM users";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                User user = new User(userID, userName, password);

                userObservableList.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userObservableList;
    }
}
