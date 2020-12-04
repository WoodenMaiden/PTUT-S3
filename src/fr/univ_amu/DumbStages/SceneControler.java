package fr.univ_amu.DumbStages;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.compress.compressors.FileNameUtil;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class SceneControler {

    static File excel ;
    static String path ;

    @FXML
    private Label textid;

    @FXML
    private VBox box;

    @FXML
    private Button btnFolder;

    @FXML
    private ImageView drop;

    @FXML
    private void handleDragDone(DragEvent event) {

    }

    @FXML
    void handleDragDropped(DragEvent event) throws FileNotFoundException {
        List<File> files = event.getDragboard().getFiles();
        excel = files.get(0);
        path = excel.getAbsolutePath();
        this.textid.setText(path);
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
    void handleDragExit(DragEvent event) {
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
    void handleValiderAction(ActionEvent event) {
        String str = excel.getName();
        String strExtension = str.substring(str.indexOf('.'));
        if (strExtension.equals(".xlsx") || strExtension.equals(".XLSX") || strExtension.equals(".XLS")|| strExtension.equals(".XLS") ){
            textid.setText("Fichier accepter");
            LecteurExcel.start();
        }
        else
            textid.setText("Fichier invalide");
    }

}
