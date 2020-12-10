package fr.univ_amu.DumbStages;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Step2Controler {

    static File excel ;
    static String path ;
    public Stage dialog;

    @FXML
    private VBox box;

    @FXML
    private ImageView drop;

    @FXML
    private Label textid;

    @FXML
    private Button btnFolder;

    @FXML
    private Button btnValider;

    @FXML
    private Pane step2;

    @FXML
    private ImageView arrow;

    @FXML
    void goStep1(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("step1.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void handleDragDone(DragEvent event) {

    }

    @FXML
    void handleDragDropped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        excel = files.get(0);
        path = excel.getAbsolutePath();
        this.textid.setText(path);
    }

    @FXML
    void handleDragExit(DragEvent event) {
        drop.setImage(new Image(getClass().getResourceAsStream("resources/Download.png")));
    }

    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
            drop.setImage(new Image(getClass().getResourceAsStream("resources/DownloadHover.png")));
        }
        else
            drop.setImage(new Image(getClass().getResourceAsStream("resources/Download.png")));
    }

    @FXML
    void handleFolderAction(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Fichier excel","*.xlsx"));
        excel = fc.showOpenDialog(null);
        if (excel != null){
            path = excel.getAbsolutePath();
            textid.setText(path);
        }
        else
            textid.setText("Fichier invalide");
    }

    @FXML
    void handleValiderAction(ActionEvent event) throws AWTException {
    }


}
