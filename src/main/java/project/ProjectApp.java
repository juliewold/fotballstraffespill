package project;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class ProjectApp extends Application  { // Hovedklassen for JavaFX-applikasjonen

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException { // Starter JavaFX-applikasjonen og laster FXML-filen
        stage.setTitle("Straffespark");
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/project/App.fxml")).load()));
        stage.show(); 
    }
    
}
