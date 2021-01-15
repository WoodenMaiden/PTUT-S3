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
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.Label;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.DragEvent;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.input.TransferMode;
        import javafx.scene.layout.Pane;
        import javafx.scene.layout.StackPane;
        import javafx.scene.layout.VBox;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;

        import java.awt.*;
        import java.io.File;
        import java.io.IOException;
        import java.net.URL;
        import java.time.LocalDate;
        import java.util.List;
        import java.util.ResourceBundle;
// Controleur du fichier step1.fxml on retrouve ici les methodes néccessaire au bon fonctionnement de l'affichage
public class Step1Controler {

    static File excel ;
    static String path ;
    static LocalDate localDate;
    public Stage dialog;

    @FXML
    public StackPane infoBox;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Label textHtml;
    @FXML
    public Label textExcel;
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
    private StackPane shadowBox;
    @FXML
    private ImageView arrow;

    //Changement de scene vers la scene step2.fxml
    @FXML
    void goStep2(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("step2.fxml"));
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
        drop.setImage(new Image(getClass().getResourceAsStream("resources/Download.png")));
    }

    //Change l'afficher au survole d'un fichier sur le glisser-deposer
    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
            drop.setImage(new Image(getClass().getResourceAsStream("resources/DownloadHover.png")));
        }
        else
            drop.setImage(new Image(getClass().getResourceAsStream("resources/Download.png")));
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
    void handleValiderAction(ActionEvent event) throws AWTException, IOException {
        String str = excel.getName();
        String strExtension = str.substring(str.indexOf('.'));
        if(localDate == null){
            textid.setText("Choisissez une date valide");

        }
        else if (strExtension.equals(".xlsx") || strExtension.equals(".XLSX") || strExtension.equals(".XLS")|| strExtension.equals(".XLS") ){
            textid.setText("Fichier accepté");
            LecteurExcel.start();
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
        trayIcon.displayMessage("Les fichiers ont été générés sur le bureau",  textHtml.getText() + " et "+ textExcel.getText(), TrayIcon.MessageType.NONE);
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
        infoBox.setDisable(true);
        shadowBox.setVisible(true);
    }

    //Ouvre la fenetre d'information et ferme le glisser déposer
    public void openInfo(MouseEvent mouseEvent) {
        infoBox.setVisible(true);
        infoBox.setDisable(false);
        shadowBox.setVisible(false);
    }

    //Affecte la date lorsque l'on choisit
    public void chooseDate(ActionEvent actionEvent) {
        localDate = datePicker.getValue();
    }


}
