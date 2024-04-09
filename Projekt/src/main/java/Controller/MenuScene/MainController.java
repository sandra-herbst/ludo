package Controller.MenuScene;

import Controller.PopUpScene.ConfirmController;
import Controller.SuperController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main Window of the Application.
 */
public class MainController extends SuperController implements Initializable {

    @FXML private AnchorPane pane;
    @FXML private ImageView shineImage;
    private String exitLabelText;

    /**
     * Method for changing the Main Scene to the Start-Game Scene.
     */
    public void changeScreentoStartGame() {
        createSoundThread("click6");
        makeFadeOut(getStartGameFXML() , pane);
    }

     /**
     * Method for changing the Main Scene to the Rules Scene.
     */
    public void changeScreentoRules() {
        createSoundThread("click6");
        makeFadeOut(getRuleFXML(), pane);
    }

    /**
     * Method for changing the Main Scene to the Settings Scene(Loaded as a PopUp).
     */
    public void showPopUp() {
        createSoundThread("click6");
        loadPopUp(getSettingFXML(), pane, 640,420);
    }

    /**
     * Terminating the program & asking for user confirmation.
     */
    public void exitScreen(){
        createSoundThread("click6");
        ConfirmController.setText(exitLabelText);
        loadPopUp(getConfirmFXML() , pane, 646, 208);
        if (ConfirmController.getChoice()){
            System.exit(0);
        }
    }

    /**
     * This method will load Custom Fonts for the Application to use.
     */
    private void loadFonts(){
        try {
            // Loading Custom Font
            FileInputStream fontFile1 = new FileInputStream("target/classes/font/Gilroy-ExtraBold.otf");
            Font.loadFont(fontFile1,10);

            FileInputStream fontFile2 = new FileInputStream("target/classes/font/Gilroy-Bold.otf");
            Font.loadFont(fontFile2,10);

            FileInputStream fontFile3 = new FileInputStream("target/classes/font/Gilroy-Regular.otf");
            Font.loadFont(fontFile3,10);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getExitLabel(ResourceBundle resource){
        exitLabelText = resource.getString("ExitLabel");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFonts();
        rotateAnimation(shineImage).play();
        pane.setOpacity(0);
        makeFadeInTransition(pane);
        getExitLabel(resourceBundle);
    }
}
