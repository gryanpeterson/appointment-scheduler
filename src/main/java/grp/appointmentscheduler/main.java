package grp.appointmentscheduler;


import grp.appointmentscheduler.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/** The main class
 *
 */
public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //stage.setTitle("Login - Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /** Method that opens the database connection, launches the application, and closes
     * the database connection.
     *
     * @param args the args
     */
    public static void main(String[] args) {

        JDBC.openConnection();

        launch();

        JDBC.closeConnection();
    }
}