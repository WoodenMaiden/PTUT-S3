package fr.univ_amu.DumbStages;


import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("load.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/resources/scene.css");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/Icon.png")));
        stage.setTitle("IUT Stage");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
