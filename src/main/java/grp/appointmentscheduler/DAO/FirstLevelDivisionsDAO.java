package grp.appointmentscheduler.DAO;

import grp.appointmentscheduler.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import grp.appointmentscheduler.model.FirstLevelDivision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** grp.appointmentscheduler.DAO Class for First Level Divisions
 *
 */
public class FirstLevelDivisionsDAO {
    /** Method that gets all first level divisions.
     *
     * @return returns the Observable List of all first level divisions.
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions(){
        ObservableList<FirstLevelDivision> firstLevelDivisionObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");

                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionID, divisionName, countryID);

                firstLevelDivisionObservableList.add(firstLevelDivision);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return firstLevelDivisionObservableList;
    }
}
