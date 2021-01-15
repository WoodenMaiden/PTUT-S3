package fr.univ_amu.DumbStages;

import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Step2Controler implements Initializable {

    static File excel ;
    static String path ;
    static String endFile;

    @FXML
    public StackPane infoBox;
    @FXML
    public StackPane shadowBox;
    @FXML
    public Label textJourney;
    @FXML
    public JFXToggleButton switchButton = new JFXToggleButton();
    @FXML
    public Label textExcel;
    @FXML
    private ChoiceBox<Integer> numberBox = new ChoiceBox<Integer>();
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

    //Initialisation de certaine variables et leur propriété
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Intitialisation des valeur de bouton déroulant nombre d'heure
        numberBox.getItems().addAll(3,4,5,6,7,8);
        numberBox.setValue(5);

        //Initialisation de l'action du boutton "Valider"
        btnValider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    handleValiderAction(actionEvent,"Matin");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //Initialisation du Boutton Switch et attribution des methode selon son activation
        switchButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(switchButton.isSelected()) {
                    textid.setText("Glisser le fichier excel");
                    if(excel != null)
                        excel = null ;
                    textExcel.setText("Emploi du temps entreprises Apres-Midi.xlsx");
                    textJourney.setText("APRES-MIDI");
                    btnValider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                handleValiderAction(actionEvent,"Apres-Midi");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
                else{
                    textid.setText("Glisser le fichier excel");
                    textJourney.setText("MATIN");
                    if(excel != null)
                        excel = null;
                    textExcel.setText("Emploi du temps entreprises Matin.xlsx");
                    btnValider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                handleValiderAction(actionEvent,"Matin");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });

    }

    //Changement de scene vers la scene step1.fxml
    @FXML
    void goStep1(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("step1.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //Changement de scene vers la scene home.fxml
    @FXML
    void goHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //Récuperation du fichier lors d'un glisser-déposer
    @FXML
    void handleDragDropped(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        excel = files.get(0);
        path = excel.getAbsolutePath();
        this.textid.setText(path);
    }

    //Change l'affichage de l'image lorsque l'on lache ou quitte l'emplacement du glisser-deposer
    @FXML
    void handleDragExit(DragEvent event) {
        drop.setImage(new Image(getClass().getResourceAsStream("resources/Download2.png")));
    }

    //Change l'afficher au survole d'un fichier sur le glisser-deposer
    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
            drop.setImage(new Image(getClass().getResourceAsStream("resources/Download2Hover.png")));
        }
        else
            drop.setImage(new Image(getClass().getResourceAsStream("resources/Download2.png")));
    }

    //Configuration du button "Ouvrir" pour récuperer un fichier en entrée
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

    //Configuration du boutton "Valider" qui lance la génération si le fichier et la date sont correcte et affecte un String à ajouter à la fin des fichiers générer
    @FXML
    void handleValiderAction(ActionEvent event, String endFileStr) throws Exception {

        FileSystemView fsv = FileSystemView.getFileSystemView(); // Recuperation du chemin du bureau
        File desktopFile = fsv.getHomeDirectory();
        String desktopPath = desktopFile.getAbsolutePath();


        String str = excel.getName();
        String strExtension = str.substring(str.indexOf('.'));
        if (strExtension.equals(".xlsx") || strExtension.equals(".XLSX") || strExtension.equals(".XLS")|| strExtension.equals(".XLS") ){
            endFile = endFileStr;
            textid.setText("Fichier accepté");
            GenerateurEdt generateurEdt = new GenerateurEdt(excel.getAbsolutePath(),desktopPath,numberBox.getValue());
            generateurEdt.run();
            if (SystemTray.isSupported())
                displayProcessFinishWindow();
        }
        else
            textid.setText("Fichier invalide");
    }

    //Affichage du message de fin de génération des fichiers
    public void displayProcessFinishWindow() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("resources/Icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("IUT Stage");
        tray.add(trayIcon);
        trayIcon.displayMessage("Les fichiers ont été générés sur le bureau", textExcel.getText(), TrayIcon.MessageType.NONE);
        tray.remove(trayIcon);
    }

    //Ferme la fenetre d'information et ouvre le glisser déposer
    public void closeInfo(ActionEvent event) {
        infoBox.setVisible(false);
        shadowBox.setVisible(true);
    }

    //Ferme la fenetre d'information et ouvre le glisser déposer
    public void closeImage(MouseEvent mouseEvent) {
        infoBox.setVisible(false);
        shadowBox.setVisible(true);
    }


    //Ouvre la fenetre d'information et ferme le glisser déposer
    public void openInfo(MouseEvent mouseEvent) {
        infoBox.setVisible(true);
        shadowBox.setVisible(false);
    }


}
