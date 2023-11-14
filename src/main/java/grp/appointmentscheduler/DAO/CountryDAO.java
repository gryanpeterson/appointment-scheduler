package grp.appointmentscheduler.DAO;

import grp.appointmentscheduler.helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import grp.appointmentscheduler.model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** grp.appointmentscheduler.DAO Class for Countries.
 *
 */
public class CountryDAO {
    /** Method that gets all Countries.
     *
     * @return returns the Observable List of all Countries.
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryObservableList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM countries";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country country = new Country(countryID, countryName);

                countryObservableList.add(country);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countryObservableList;
    }
}
