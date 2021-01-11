package fr.univ_amu.DumbStages;


import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application{
    private Stage window;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage ;
        Parent root = FXMLLoader.load(getClass().getResource("load.fxml"));
        Scene scene = new Scene(root);
        window.getIcons().add(new Image(getClass().getResourceAsStream("resources/Icon.png")));
        window.setTitle("IUT Stage");
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
