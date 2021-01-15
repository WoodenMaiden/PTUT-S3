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
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import javafx.stage.StageStyle;

        import java.awt.*;
        import java.io.File;
        import java.io.IOException;
        import java.net.URL;
        import java.time.LocalDate;
        import java.util.List;
        import java.util.ResourceBundle;

public class Step1Controler implements Initializable {

    static File excel ;
    static String path ;
    static String endFile;
    public Stage dialog;
    static LocalDate localDate;

    @FXML
    public StackPane infoBox;
    @FXML
    public DatePicker datePicker;

    @FXML
    public JFXToggleButton switchButton;

    @FXML
    public Label textJourney;

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

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(switchButton.isSelected()==true) {
                    textid.setText("Glisser le fichier excel");
                    if(excel != null)
                        excel = null ;
                    textHtml.setText("Forum Stage Apres-Midi.html");
                    textExcel.setText("Tableau Etudiant Entreprises Apres-Midi.xlsx");
                    textJourney.setText("APRES-MIDI");
                    btnValider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                handleValiderActionApresmidi(actionEvent);
                            } catch (AWTException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else{
                    textid.setText("Glisser le fichier excel");
                    if(excel != null)
                        excel = null ;
                    textHtml.setText("Forum Stage Matin.html");
                    textExcel.setText("Tableau Etudiant Entreprises Matin.xlsx");
                    textJourney.setText("MATIN");
                    btnValider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                handleValiderActionMatin(actionEvent);
                            } catch (AWTException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });
    }


    @FXML
    void goStep2(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("step2.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("load.fxml"));
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
    void handleValiderActionMatin(ActionEvent event) throws AWTException, IOException {
        textJourney.setText("MATIN");
        endFile = "Matin";
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

    @FXML
    void handleValiderActionApresmidi(ActionEvent event) throws AWTException, IOException {
        endFile = "Apres-Midi";
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


    public void closeInfo(ActionEvent event) {
        infoBox.setVisible(false);
        shadowBox.setVisible(true);
    }

    public void closeImage(MouseEvent mouseEvent) {
        infoBox.setVisible(false);
        shadowBox.setVisible(true);
    }


    public void openInfo(MouseEvent mouseEvent) {
        infoBox.setVisible(true);
        shadowBox.setVisible(false);
    }

    public void chooseDate(ActionEvent actionEvent) {
        localDate = datePicker.getValue();
    }


}
