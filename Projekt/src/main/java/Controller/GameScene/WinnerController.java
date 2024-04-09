package Controller.GameScene;

import Controller.SuperController;
import Game.Player.AbstractPlayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WinnerController extends SuperController implements Initializable {

    @FXML private Label winnerLabel;
    @FXML private AnchorPane pane;
    @FXML private Label roundLabel;
    @FXML private ImageView shine;
    private GameController gameController;
    private Stage stage;
    static boolean choice;

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
        gameController.winnerChoice();
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
        gameController.winnerChoice();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameController = getInstance().getGameController();
        AbstractPlayer winner = gameController.getWinner();
        rotateAnimation(shine).play();
        winnerLabel.setText(winner.getName() + " !");
        roundLabel.setText(resourceBundle.getString("totalRounds") + gameController.getRound());
    }
}