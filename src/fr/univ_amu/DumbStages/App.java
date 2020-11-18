package fr.univ_amu.DumbStages;


import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
       /* VBox root = new VBox();
        HBox navbar = new HBox();
        ImageView IUT = new ImageView(new Image(getClass().getResourceAsStream("IUTStage.png")));
        ImageView download = new ImageView(new Image(getClass().getResourceAsStream("Download.png")));
        Label label = new Label("Generateur de forum");
        IUT.setPreserveRatio(true);
        IUT.setFitHeight(35);
        download.setPreserveRatio(true);
        download.setFitHeight(250);
        root.setAlignment(Pos.CENTER);
        navbar.getChildren().add(IUT);
        root.getChildren().addAll(navbar,download);*/

        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("resources/Icon.png")));
        stage.setTitle("IUT Stage");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
