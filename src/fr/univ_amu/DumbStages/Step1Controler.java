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

public class Step1Controler {

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
    void goStep2(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("step2.fxml"));
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
    void handleValiderAction(ActionEvent event) throws AWTException, IOException {
        String str = excel.getName();
        String strExtension = str.substring(str.indexOf('.'));
        if (strExtension.equals(".xlsx") || strExtension.equals(".XLSX") || strExtension.equals(".XLS")|| strExtension.equals(".XLS") ){
            textid.setText("Fichier accepté");
            LecteurExcel.start();
            if (SystemTray.isSupported())
                displayProcessFinishWindow();
            else
                displayProcessFinish();
        }
        else
            textid.setText("Fichier invalide");
    }

    //Methode

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
        trayIcon.displayMessage("Les fichiers ont été générés sur le bureau", "Forum_Stage.html et Tableau_Etudiant_Entreprises.xls", TrayIcon.MessageType.NONE);
        tray.remove(trayIcon);
    }

    public void displayProcessFinish() throws IOException {
        dialog = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("alert.fxml"));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(null);
        dialog.initStyle(StageStyle.UTILITY);
        Scene dialogScene = new Scene(root);
        dialog.setScene(dialogScene);
        dialog.centerOnScreen();
        dialog.show();

    }


}
