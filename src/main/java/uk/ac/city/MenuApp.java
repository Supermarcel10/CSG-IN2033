package uk.ac.city;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuApp extends Application {
   

    @Override
    public void start(Stage stage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MenuApp.class.getResource("/menu_view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Lancaster's Menu");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
