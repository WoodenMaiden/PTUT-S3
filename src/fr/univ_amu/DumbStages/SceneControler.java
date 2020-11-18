package fr.univ_amu.DumbStages;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class SceneControler {


    @FXML
    private Label textid;

    @FXML
    private VBox box;

    @FXML
    private ImageView drop;

    @FXML
    private void handleDragDone(DragEvent event) {

    }

    @FXML
    private void handleDragDropped(DragEvent event) throws FileNotFoundException {
        List<File> files = event.getDragboard().getFiles();
        File excel = files.get(0);
        String path = excel.getAbsolutePath();
        this.textid.setText(path);
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
            drop.setImage(new Image(getClass().getResourceAsStream("resources/DownloadHover.png")));
        }
        else
            drop.setImage(new Image(getClass().getResourceAsStream("resources/Download.png")));
    }

    @FXML
    private void handleDragExit(DragEvent event) {
            drop.setImage(new Image(getClass().getResourceAsStream("resources/Download.png")));
    }

}
