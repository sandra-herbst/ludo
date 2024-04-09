package Controller.PopUpScene;

import Controller.SuperController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmController extends SuperController implements Initializable {

    private static final Logger logger = LogManager.getLogger(ConfirmController.class);

    @FXML private Label choices;
    @FXML private AnchorPane pane;
    @FXML private Label textLabel;
    private static String labelText;
    private static boolean choice;
    private Stage stage;

    /**
     * Method for closing the screen when cancel button has been pushed.
     * No changed will be made.
     */
    public void cancelSettings(){
        createSoundThread("click6");
        choice = false;
        this.stage = (Stage) pane.getScene().getWindow();
        stage.close();

        unblurBG((AnchorPane) stage.getOwner().getScene().getRoot());
    }

    /**
     * Method for closing the screen when confirmation button has been pushed.
     * Settings will be changed accordingly.
     */
    public void confirmSettings() {
        createSoundThread("click6");
        choice = true;
        this.stage = (Stage) pane.getScene().getWindow();
        stage.close();

        unblurBG((AnchorPane) stage.getOwner().getScene().getRoot());
    }

    public static boolean getChoice(){
        return choice;
    }

    public static void setChoice(boolean choiceStart){
        choice = choiceStart;
    }

    public static void setText(String text){
        labelText = text;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textLabel.setText(labelText);
    }
}