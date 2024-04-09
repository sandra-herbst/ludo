package Controller.MenuScene;

import Controller.PopUpScene.ConfirmController;
import Controller.SuperController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Start Game.Game Window (Can be opened by the 'Start Game.Game' option in the Main Window)
 */
public class StartGameController extends SuperController implements Initializable {

    private static final Logger logger = LogManager.getLogger(StartGameController.class);
    @FXML private ToggleButton blueFigure;
    @FXML private ToggleButton greenFigure;
    @FXML private ToggleButton yellowFigure;
    @FXML private ToggleButton redFigure;

    @FXML private ToggleButton comP1;
    @FXML private ToggleButton comP2;
    @FXML private ToggleButton comP3;

    @FXML private TextField usernameField;
    @FXML private Label errorUsername;

    @FXML private AnchorPane pane;

    private ToggleGroup groupFigure;
    private ToggleGroup groupComP;

    private String username;
    private String color;
    private int comPlayer;
    private String errorText;
    private String confirmLabel;

    /**
     * Method for changing the Start Game.Game Scene to the Main Scene
     */
    public void changeScreenToMenu() {
        createSoundThread("click6");
        loadNextScene("/fxml/MainView.fxml" , pane);
    }

    public void changeScreenToGame() {
        createSoundThread("click6");
        try {
            if (isInputValid()){
                ConfirmController.setText(confirmLabel);
                loadPopUp(getConfirmFXML() , pane , 646, 208);
                if (ConfirmController.getChoice()){
                    loadNextScene(getGameFXML() , pane);
                    ConfirmController.setChoice(false);
                }
            } else {
                errorUsername.setText(errorText);
            }
        } catch (Exception e){
            logger.fatal("Something went wrong here!\n");
            e.printStackTrace();
        }
    }

    /**
     * Method for checking whether or not the user has given
     * valid input: Username min 1 character, max 10.
     *              Color has been chosen.
     *              Computer Player amount has been chosen.
     * @return true if input is valid
     */
    private boolean isInputValid(){
        try {
            // Username is min 1 Char, max 10 characters
            boolean comPlayerSelected = groupComP.getSelectedToggle().isSelected();
            boolean userNameValid = (usernameField.getText().length() > 0) && (usernameField.getText().length() < 11) && (!usernameField.getText().contains(" "));
            boolean colorSelected = groupFigure.getSelectedToggle().isSelected();

            if (userNameValid && colorSelected && comPlayerSelected){
                this.username = usernameField.getText();
                setColor();
                setComPlayer();
                logger.info("\nThe chosen color is... " + color + "\nThe username is... " + username + "\nNumber of Computer Players is... " + comPlayer);
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            errorUsername.setText(errorText);
            return false;
        }
    }

    private void setComPlayer() {
        if (comP1.isSelected()){
            this.comPlayer = 1;
        } else if (comP2.isSelected()){
            this.comPlayer = 2;
        } else {
            this.comPlayer = 3;
        }
    }

    private void setColor(){
        if (blueFigure.isSelected()){
            this.color = "blue";
        } else if (greenFigure.isSelected()){
            this.color = "green";
        } else if (redFigure.isSelected()){
            this.color = "red";
        } else {
            this.color = "yellow";
        }
    }

    public void setTexts(ResourceBundle resources){
        errorText = resources.getString("ErrorText");
        confirmLabel = resources.getString("confirmLabel2");
    }


    /**
     * Method for returning the Name of the player, to create a User
     * Instance in the Game.Game class later on. GameController sets
     * Label to the String given in this method.
     */
    public String getName(){
        return username;
    }

    public String getColor(){
        return color;
    }

    public int getComPlayer(){
        return comPlayer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set Instance of the controller to pass parameters to another scene
        getInstance().setStartGameController(this);
        setTexts(resourceBundle);
        // Only one color can be selected at a time
        groupFigure = new ToggleGroup();
        groupFigure.getToggles().addAll(blueFigure,yellowFigure,redFigure,greenFigure);
        // Only one NPC amount option can be selected at a time
        groupComP = new ToggleGroup();
        groupComP.getToggles().addAll(comP1,comP2,comP3);

        pane.setOpacity(0);
        makeFadeInTransition(pane);
    }
}
