package Controller;

import Controller.GameScene.GameController;
import Controller.MenuScene.StartGameController;
import Controller.PopUpScene.SettingsController;
import Start.Main;
import Thread.Music.Sound;
import Thread.Music.ThreadSound;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.ResourceBundle;

import static Controller.PopUpScene.SettingsController.setCurrentStylesheet;

/**
 * This class minimizes duplicate code fragments for GUI Controllers and stores it in one class.
 * Includes: Scene Change, FadeIn/Out Animations, blur effect, Pop-Up method and getters for
 * the FXML Strings.
 */
public class SuperController {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final SuperController INSTANCE = new SuperController();
    private double xOffset = 0;
    private double yOffset = 0;

    private Stage stage;
    private Parent root;

    private StartGameController startGameController;
    private GameController gameController;
    // Language
    private static ResourceBundle bundle;

    /**
     * Method for setting Instance of a controller to use their parameters in another class.
     */
    public static SuperController getInstance() {
        return INSTANCE;
    }

    public StartGameController getStartGameController() {
        return startGameController;
    }

    public void setStartGameController(StartGameController controller) {
        this.startGameController = controller;
    }

    public GameController getGameController() { return gameController; }

    public void setGameController(GameController gameController) { this.gameController = gameController; }

    public void setBundle(ResourceBundle bundleLANG){
        bundle = bundleLANG;
    }

    public static ResourceBundle getBundle(){
        return bundle;
    }

    /**
     * Method is being called within the makeFadeOut transition method.
     * can be called for simply changing a scene.
     * @param url FXML file that is being loaded.
     */
    public void loadNextScene(String url, AnchorPane pane){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(url)),bundle);
            Scene scene = new Scene(root, 1280, 720);

            scene.getStylesheets().clear();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(setCurrentStylesheet())).toExternalForm());

            // Gets stage information
            Stage window = (Stage) pane.getScene().getWindow();
            window.setScene(scene);

        } catch (Exception e){

            logger.fatal("Loading next Scene failed!\n" + e.getMessage() + "\n");
            e.printStackTrace();

        }
    }

    /**
     * This method loads a Pop-Up window in the current window.
     * @param url FXML file that is being loaded.
     * @param width of the pop-up.
     * @param height of the pop-up.
     */
    public void loadPopUp(String url, AnchorPane pane, int width, int height) {
        try {
            stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            root = FXMLLoader.load(getClass().getResource(url), bundle);
            Scene scene = new Scene(root, width, height);
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            blurBG(pane);

            // This will make the window movable by dragging it elsewhere.
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            // The Owner Window is being paused until the PopUp has been closed
            stage.showAndWait();

        } catch (Exception e){

            logger.fatal("Loading Popup failed!");
            e.printStackTrace();

        }
    }

    /**
     * This method loads a Winner Pop-Up window in the current window.
     * @param url FXML file that is being loaded.
     * @param width of the pop-up.
     * @param height of the pop-up.
     */
    public void loadPopUpForWinner(String url, AnchorPane pane, int width, int height) {
        try {
            stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            root = FXMLLoader.load(getClass().getResource(url), bundle);
            Scene scene = new Scene(root, width, height);
            scene.setFill(Color.TRANSPARENT);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            blurBG(pane);

            // This will make the window movable by dragging it elsewhere.
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            // The Owner Window is being paused until the PopUp has been closed
            stage.show();

        } catch (Exception e){

            logger.fatal("Loading Popup failed!");
            e.printStackTrace();

        }
    }

    /**
     * Blur the owner window when the PopUp has been opened.
     * @param pane of the owner.
     */
    public void blurBG(AnchorPane pane){
        BoxBlur blur = new BoxBlur(3,3,3);
        pane.setEffect(blur);
    }

    /**
     * Unblur the owner window when the PopUp has been closed.
     * @param pane of the owner.
     */
    public void unblurBG(AnchorPane pane){
        pane.setEffect(null);
    }

    /**
     * Method JavaFX Animation for Scene Transition (FadeIN).
     * @param pane for Scene-building.
     */
    public void makeFadeInTransition(AnchorPane pane) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(pane);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    /**
     * Method JavaFX Animation for Scene Transition (FadeOUT).
     * @param url is the String url of the FXML file that is being loaded.
     * @param pane for Scene-building.
     */
    public void makeFadeOut(String url, AnchorPane pane) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));

        transition.setNode(pane);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.setOnFinished(event -> loadNextScene(url , pane));
        transition.play();
    }

    /**
     * Method JavaFX Animation for Scene Transition (FadeOUT).
     * @param image is the shine ImageView in the GameScene (for current player)
     */
    public void makeShineFadeOut(ImageView image) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(image);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.setOnFinished(event -> image.setVisible(false));
        transition.play();
    }

    /**
     * Method JavaFX Animation for Scene Transition (FadeIn).
     * @param image is the shine ImageView in the GameScene (for current player)
     */
    public void makeShineFadeIn(ImageView image) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(500));
        transition.setNode(image);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    /**
     * Method for rotate animation of the shine in the Main Scene behind the menu & in the game scene.
     */
    public RotateTransition rotateAnimation(ImageView image) {
        RotateTransition rt = new RotateTransition(Duration.millis(30000), image);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        return rt;
    }

    public String getMainFXML() {
        return "/fxml/MainView.fxml";
    }

    public String getRuleFXML() {
        return "/fxml/RuleView.fxml";
    }

    public String getSettingFXML() {
        return "/fxml/SettingsView.fxml";
    }

    public String getStartGameFXML() { return "/fxml/StartGameView.fxml"; }

    public String getGameFXML() { return "/fxml/GameView.fxml"; }

    public String getConfirmFXML() {
        return "/fxml/ConfirmView.fxml";
    }

    public String getWinnerFXML() {
        return "/fxml/WinnerView.fxml";
    }

    public void createSoundThread(String soundName) {
        Sound sound = new Sound(soundName);
        Thread tSound = new Thread(new ThreadSound(sound , false));
        tSound.start();
    }

}
