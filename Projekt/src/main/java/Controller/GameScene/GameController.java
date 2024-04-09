package Controller.GameScene;

import Controller.MenuScene.StartGameController;
import Controller.PopUpScene.ConfirmController;
import Controller.SuperController;
import Exceptions.IllegalFactoryArgument;
import Game.GameObjects.Dice;
import Game.GameObjects.Figure;
import Game.GameObjects.FigureManager;
import Game.Player.AbstractPlayer;
import Game.Player.ComPlayer;
import Game.Player.PlayerFactory;
import Game.Player.UserPlayer;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController extends SuperController implements Initializable {

    private static final Logger logger = LogManager.getLogger(GameController.class);
    public static final int COMPLAYERSPEED = 500;

    private StartGameController start;
    // Language Strings
    private String confirmRestartText;
    private String roundText;
    private String diceInstructionText;
    private String figureNotMovableText;
    private String chooseFigureText;
    private String skipRoundText;

    // Game board Elements
    @FXML private AnchorPane paneGame;
    @FXML private GridPane board;
    @FXML private Label roundLabel;
    @FXML private Button cheatButton;

    // Name Label of the players
    @FXML private Label redLabel;
    @FXML private Label greenLabel;
    @FXML private Label yellowLabel;
    @FXML private Label blueLabel;

    // Red FXML Elements
    @FXML private ToggleGroup redGroup;
    @FXML private ToggleButton redFigure1;
    @FXML private ToggleButton redFigure2;
    @FXML private ToggleButton redFigure3;
    @FXML private ToggleButton redFigure4;
    @FXML private ImageView redDice;
    @FXML private VBox redBox;
    @FXML private Label redErrorLabel;
    @FXML private ImageView shineRed;
    private RotateTransition redAnimation;

    // Blue FXML Elements
    @FXML private ToggleGroup blueGroup;
    @FXML private ToggleButton blueFigure1;
    @FXML private ToggleButton blueFigure2;
    @FXML private ToggleButton blueFigure3;
    @FXML private ToggleButton blueFigure4;
    @FXML private ImageView blueDice;
    @FXML private VBox blueBox;
    @FXML private Label blueErrorLabel;
    @FXML private ImageView shineBlue;
    private RotateTransition blueAnimation;

    // Yellow FXML Elements
    @FXML private ToggleGroup yellowGroup;
    @FXML private ToggleButton yellowFigure1;
    @FXML private ToggleButton yellowFigure2;
    @FXML private ToggleButton yellowFigure3;
    @FXML private ToggleButton yellowFigure4;
    @FXML private ImageView yellowDice;
    @FXML private VBox yellowBox;
    @FXML private Label yellowErrorLabel;
    @FXML private ImageView shineYellow;
    private RotateTransition yellowAnimation;

    // Green FXML Elements
    @FXML private ToggleGroup greenGroup;
    @FXML private ToggleButton greenFigure1;
    @FXML private ToggleButton greenFigure2;
    @FXML private ToggleButton greenFigure3;
    @FXML private ToggleButton greenFigure4;
    @FXML private ImageView greenDice;
    @FXML private VBox greenBox;
    @FXML private Label greenErrorLabel;
    @FXML private ImageView shineGreen;
    private RotateTransition greenAnimation;

    // Game Data
    private String username;
    private String userColor;
    private int comPlayerCount;
    private ImageView userDiceImg;
    private int round = 1;
    private boolean firstMove = true;
    // Array of all Players
    private AbstractPlayer[] playerArray;
    private final Dice DICE = new Dice();
    private final FigureManager FIGUREMANAGER = new FigureManager();
    private PlayerFactory factory;
    private int playerIndex;
    private int counter;
    private AbstractPlayer winner;
    // ComputerPlayer Transitions
    private PauseTransition pause;
    private PauseTransition rollPause;
    private PauseTransition rollPauseNormalRound;
    private PauseTransition movePauseNormalRound;
    private PauseTransition movePause;


    /**
     * Get the winner at the end of the game, so the Winner PopUp can work with it.
     * @return winner of the game.
     */
    public AbstractPlayer getWinner() {
        return winner;
    }

    /**
     * Method for changing the Game Scene to the Start-Game Scene.
     */
    public void resetGame(){
        factory.resetUserColors();
        resetGameAnimations();
        loadNextScene(getStartGameFXML() , paneGame);
        logger.info("The game has been resetted");
    }

    /**
     * Method for opening the settings popup in the Game Scene.
     */
    public void changeScreenToSettings(){
        createSoundThread("click6");
        loadPopUp(getSettingFXML() , paneGame , 640,420);
    }

    /**
     * Method for changing the Game Scene to the Main Menu Scene.
     */
    public void changeScreenToMenu(){
        factory.resetUserColors();
        resetGameAnimations();
        loadNextScene(getMainFXML() , paneGame);
    }

    /**
     * Method for opening the winner popup in the Game Scene after someone
     * has won.
     */
    public void changeScreenToWinner(){
        PauseTransition winnerTransition = new PauseTransition(Duration.millis(500));
        winnerTransition.setOnFinished( event -> {

            // Play sound depending on whether or not the human player won.
            if (winner.getName().equals(username)){
                createSoundThread("win");
            } else {
                createSoundThread("lose");
            }
            // Open Winner Pop Up
            loadPopUpForWinner(getWinnerFXML(), paneGame, 646, 406);
        });
        winnerTransition.play();
    }

    /**
     * This method will execute the chosen option from the winner popup (restart / to menu)
     */
    public void winnerChoice(){
        if (WinnerController.choice){
            resetGame();
        } else {
            changeScreenToMenu();
        }
    }

    /**
     * Method for opening a confirmation popup after clicking the restart button.
     * Therefore the user can't reset the game accidentally.
     */
    public void changeScreenToConfirm(){
        createSoundThread("click6");
        ConfirmController.setText(confirmRestartText);
        loadPopUp(getConfirmFXML(), paneGame, 646, 208);
        if (ConfirmController.getChoice()){
            resetGame();
        }
    }

    /**
     * Method for setting the language text on all necessary strings in the game.
     */
    public void getText(ResourceBundle resources){
        confirmRestartText = resources.getString("restartLabel");
        roundText = resources.getString("round");
        diceInstructionText = resources.getString("diceInstruction");
        figureNotMovableText = resources.getString("figureNotMovable");
        chooseFigureText = resources.getString("chooseFigure");
        skipRoundText = resources.getString("skipRound");
    }

    /**
     * Method for resetting the game with their ongoing animations and sound threads.
     */
    public void resetGameAnimations(){

        try {
            redAnimation.stop();
            blueAnimation.stop();
            yellowAnimation.stop();
            greenAnimation.stop();
            shineRed.setVisible(false);
            shineGreen.setVisible(false);
            shineYellow.setVisible(false);
            shineBlue.setVisible(false);
            pause.stop();
            rollPause.stop();
            movePause.stop();
            rollPauseNormalRound.stop();
            movePauseNormalRound.stop();
        } catch (Exception e){
            logger.debug("Animation Error catched, Round has been resetted");
        }
    }

    /**
     * This method will setup the Game with username, color, all Players and one Dice.
     */
    public void setUpGame(){
        // Set username, color and comPlayerCount settings from Start Game.
        this.username = start.getName();
        this.userColor = start.getColor();
        this.comPlayerCount = start.getComPlayer();
        userDiceImg = getDiceImageView(userColor);
        // Create all Players
        createAllPlayers();
        // Create a shadow effect for the ToggleGroup of the human player
        shadowEffect();
        // Set Visibility of the VBoxes from the participating colors
        createColorGroupVisibility();

        for (AbstractPlayer player : playerArray) {
            switch (player.getColor()){
                case "red": redAnimation = rotateAnimation(shineRed) ;break;
                case "blue": blueAnimation = rotateAnimation(shineBlue); break;
                case "yellow": yellowAnimation = rotateAnimation(shineYellow); break;
                case "green": greenAnimation = rotateAnimation(shineGreen); break;
            }
        }
        setTextTimer(diceInstructionText,6);
        //cheat(3);
        cheatButton.setOpacity(0);
    }

    /**
     * Stores all participating players in an array named playerArray.
     * This method is called by setUpGame() to initialize the Scene.
     */
    private void createAllPlayers() {
        try {
            factory = new PlayerFactory();
            playerArray = factory.getPlayerArray(comPlayerCount,username,userColor);
        } catch (IllegalFactoryArgument e){
            logger.fatal(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * this method controls the whole game until one player wins. If no one has won it will go on to the next round &
     * resets the playerIndex of the round.
     */
    public void playRound() {
        logger.info("ROUND: " + round);
        roundLabel.setText(roundText + round);
        if(didSomeOneWin()) {
            winner = FIGUREMANAGER.whoWon(playerArray);
            logger.info(winner.getName() + " won!");
            changeScreenToWinner();
        } else {
            // Play next round with the first person in the array.
            playerIndex = 0;
            playTurn();
        }
    }

    /**
     * This method controls the current turn of a player. If everyone has made their turn
     * in the round, the round will be increased by one and checks if someone won (By calling the playRound method)
     */
    private void playTurn() {
        if(playerIndex < playerArray.length && !didSomeOneWin()) {
            counter = 0;
            AbstractPlayer player = playerArray[playerIndex];
            // Play turn of Human / Computer
            chooseRoundRoutine(player);
        } else {
            round++;
            playRound();
        }
    }

    /**
     * This method creates an animation (shine behind the players name & dice picture)
     * and fades the animation in.
     * @param player currently playing their turn.
     */
    private void chooseRoundAnimation(AbstractPlayer player){
        switch (player.getColor()){
            case "red":
            shineRed.setVisible(true);
            redAnimation.play();
            makeShineFadeIn(shineRed);
            break;
            case "blue":
            shineBlue.setVisible(true);
            blueAnimation.play();
            makeShineFadeIn(shineBlue);
            break;
            case "yellow":
            shineYellow.setVisible(true);
            yellowAnimation.play();
            makeShineFadeIn(shineYellow);
            break;
            case "green":
            shineGreen.setVisible(true);
            greenAnimation.play();
            makeShineFadeIn(shineGreen);
            break;
        }
    }

    /**
     * This method stops the animation (shine behind the players name & dice picture)
     * and fades the animation out.
     * @param player that has finished their turn.
     */
    private void stopRoundAnimation(AbstractPlayer player){
        switch (player.getColor()){
            case "red":
                redAnimation.stop();
                makeShineFadeOut(shineRed); break;
            case "blue":
                blueAnimation.stop();
                makeShineFadeOut(shineBlue); break;
            case "yellow":
                yellowAnimation.stop();
                makeShineFadeOut(shineYellow);break;
            case "green":
                greenAnimation.stop();
                makeShineFadeOut(shineGreen);break;
        }
    }

    /**
     * This method plays an animation of the togglebutton figure that has been tackled.
     * @param figure that has been tackled.
     */
    private void tackleAnimation(ToggleButton figure){
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.2), figure);
        rotation.setCycleCount(1);
        rotation.setByAngle(20);
        rotation.setOnFinished(event -> {
            RotateTransition rotationBack = new RotateTransition(Duration.seconds(0.2), figure);
            rotationBack.setCycleCount(1);
            rotationBack.setByAngle(-20);
            rotationBack.play();
        });
        rotation.play();
    }

    /**
     * This method will execute a specific round routine of a players group type (Computer / Human).
     * @param player who has it's turn currently.
     */
    private void chooseRoundRoutine(AbstractPlayer player){
        chooseRoundAnimation(player);
        if (!factory.needsHumanInteraction(player)) {      // Computer Player Routine
            computerPlayerGameRoutine(player);
        } else {
            humanPlayerEventRoutine();          // Human Player Routine
        }
    }

    /**
     * This method plays the routine of a computer player.
     * First, his round will be in a 0,5 second delay after the player before him has finished their turn.
     */
    private void computerPlayerGameRoutine(AbstractPlayer player){
        pause = new PauseTransition(Duration.millis(COMPLAYERSPEED));
        pause.setOnFinished( event -> {

            // If its the first round / all figures in the base.
            if(round == 1 && FIGUREMANAGER.allInBase(player) || FIGUREMANAGER.allInBase(player)
                    || FIGUREMANAGER.onlyWith6Movable(player)) {

                // As long as counter < 6 or if a 6 has been rolled.
                if (counter < 3 || DICE.getNumber() == 6){
                    computerPlayerFirstRound(player);
                } else {
                    stopRoundAnimation(player);
                    playerIndex++;
                    playTurn();
                }

            } else {
                computerPlayerNormalRound(player);
            }
        });
        pause.play();
    }


    /**
     * If it's a Computer Players turn, the process will be slowed down a bit.
     * This Method is called in the first Round or when the player can only move with a 6
     * It is called for every ComPlayer
     * e.g. when all players are in the base
     * @param player is the player whose turn it is
     */
    private void computerPlayerFirstRound(AbstractPlayer player){
        // Roll in 0.5 sec
        rollPause = new PauseTransition(Duration.millis(COMPLAYERSPEED));
        rollPause.setOnFinished( e -> {

            playDiceAnimation(player.getColor());
            createSoundThread("diceRoll");
            DICE.firstRoundRoll();
            setDicePicture(player.getColor(), DICE.getNumber());
            logger.info(player.toString() +  " rolled: " + DICE.getNumber() + ", in try " + counter + "/3");
            counter++;

            if (DICE.getNumber() == 6){

                // Player can now move a figure.
                movePause = new PauseTransition(Duration.millis(COMPLAYERSPEED *2));
                movePause.setOnFinished( event -> {

                    chooseMove(player);
                    computerPlayerGameRoutine(player);

                });
                movePause.play();

            } else {
                // Player can't move a figure, so the round will go on.
                computerPlayerGameRoutine(player);
            }

        });
        rollPause.play();
    }

    /**
     * If it's a Computer Players turn, the process will be slowed down a bit.
     * This Method is called for every ComPlayer instead of computerPlayerFirstRound()
     * when the player can roll the dice only once
     * @param player is the player whose turn it is
     */
    private void computerPlayerNormalRound(AbstractPlayer player){
        rollPauseNormalRound = new PauseTransition(Duration.millis(COMPLAYERSPEED));
        rollPauseNormalRound.setOnFinished( e -> {

            playDiceAnimation(player.getColor());
            createSoundThread("diceRoll");
            DICE.roll();
            setDicePicture(player.getColor(), DICE.getNumber());
            logger.info(player.toString() +  " rolled: " + DICE.getNumber());


            movePauseNormalRound = new PauseTransition(Duration.millis(COMPLAYERSPEED *2));
            movePauseNormalRound.setOnFinished( event -> {
                chooseMove(player);

                if (DICE.getNumber() == 6){
                    computerPlayerGameRoutine(player);
                } else {
                    stopRoundAnimation(player);
                    playerIndex++;
                    playTurn();
                }

            });
            movePauseNormalRound.play();
        });
        rollPauseNormalRound.play();
    }

    /**
     * This method will tell the human players and computer players turns apart,
     * since the human players turn requires MouseClickEvents.
     * @param player Player that is currently having it's turn.
     */
    private void chooseMove(AbstractPlayer player) {
        // move for the Computer Player
            Figure selectedFigure =((ComPlayer) player).selectFigure(DICE.getNumber());
            if(player.getHasMoveableFigure()) {
                moveFigure(selectedFigure);
            }
    }

    /**
     * checks if a player has won
     * @return true if a player has all of his figures in his targetfields
     */
    private boolean didSomeOneWin() {
        winner = FIGUREMANAGER.whoWon(playerArray);
        return winner !=  null;
    }

    /**
     * This method will setup the Board at default.
     * Depending on the chosen color of the human player, the respective label will be
     * changed to the player's name.
     * It will also create a ToggleGroup of the color from the human player, a
     * group of the computer isn't needed.
     */
    public void setUpBoard(){
        switch (start.getColor()){
            case "red" :
                redLabel.setText(start.getName());
                redGroup = new ToggleGroup();
                redGroup.getToggles().addAll(redFigure1,redFigure2,redFigure3,redFigure4);
                break;
            case "blue" :
                blueLabel.setText(start.getName());
                blueGroup = new ToggleGroup();
                blueGroup.getToggles().addAll(blueFigure1,blueFigure2,blueFigure3,blueFigure4);
                break;
            case "green" :
                greenLabel.setText(start.getName());
                greenGroup = new ToggleGroup();
                greenGroup.getToggles().addAll(greenFigure1,greenFigure2,greenFigure3,greenFigure4);
                break;
            case "yellow" :
                yellowLabel.setText(start.getName());
                yellowGroup = new ToggleGroup();
                yellowGroup.getToggles().addAll(yellowFigure1,yellowFigure2,yellowFigure3,yellowFigure4);
                break;
        }
        setDicePictureDefault();
    }

    /**
     * This method will reset all dice pictures to the
     * original 0 state. (Start of the game)
     */
    public void setDicePictureDefault(){
        // Set Pictures to Default (No number shown)
        setDicePicture("red" , 0);
        setDicePicture("blue" , 0);
        setDicePicture("green" , 0);
        setDicePicture("yellow" , 0);
    }

    /**
     * This method will return the ToggleGroup of the player.
     * This is especially needed for the parameters of the methods
     * selectFigureEvent and moveFigure.
     * @return ToggleGroup of the human players color.
     */
    private ToggleGroup getPlayerGroup(){
        switch (userColor) {
            case "red":
                return redGroup;
            case "blue":
                return blueGroup;
            case "green":
                return greenGroup;
            default:
                return yellowGroup;
        }
    }

    /**
     * This method will return the Error Label of the player.
     * This is especially needed for any errors in the players turn, such as
     * "No figure movable" or "This figure can't move"
     * @return Label of the human players color.
     */
    private Label getPlayerErrorLabel(){
        switch (userColor){
            case "red":
                return redErrorLabel;
            case "blue":
                return blueErrorLabel;
            case "green":
                return greenErrorLabel;
            default:
                return yellowErrorLabel;
        }
    }

    /**
     * This method will listen to the human players Figure selection (Not computer!).
     * If the player has selected a figure, it will be moved (If possible) and
     * the listener will be deactivated until the human player makes a move again.
     */
    public void selectFigureEvent(){

        getPlayerGroup().selectedToggleProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                // If a togglebutton has been selected, it will be saved into the buttonSelected variable.
                ToggleButton buttonSelected = (ToggleButton) getPlayerGroup().getSelectedToggle();


                try {
                    if (buttonSelected != null) {

                        Figure figureSelected = getFigureOfToggleButton(buttonSelected);
                        int[] targetPos = FIGUREMANAGER.targetPos(figureSelected, DICE.getNumber());

                        if (targetPos == null) {
                            // The error text will be shown for 2 seconds, after that it will be erased.
                            setTextTimer(figureNotMovableText, 2);
                        }

                        if (FIGUREMANAGER.figureMovable(targetPos, playerArray[0])) {
                            moveFigure(buttonSelected);

                            // Reset the selected toggle, so the listener can be deactivated and activated again.
                            getPlayerGroup().getSelectedToggle().setSelected(false);
                            // Remove the Listener temporary, until it's the players turn again.
                            getPlayerGroup().selectedToggleProperty().removeListener(this);

                            if (DICE.getNumber() != 6) {
                                stopRoundAnimation(playerArray[0]);
                                // The round is finished and the next player can make a move.
                                playerIndex = 1;
                                playTurn();
                            } else {
                                // If a 6 has been rolled the player can roll again.
                                humanPlayerEventRoutine();
                            }

                        } else {
                            setTextTimer(figureNotMovableText, 2);
                        }
                    }
                } catch (Exception error) {
                    logger.fatal("figureSelected threw Exception");
                }
            }
        });

    }

    /**
     * This method will listen to the human players click on the dice image - to roll (Not computer!).
     * If the player has clicked on the image, it will be moved (If possible) and
     * the listener will be deactivated until the human player makes a move again.
     *
     * This method plays the routine of a human player, it includes
     * the dice Mouse-Click-Event and Figure-Click-Event.
     */
    public void humanPlayerEventRoutine() {
        // Event will be activated
        try {
            // If it's the first round or all Figures are still in the Base, the player rolls 3 times.
            if (round == 1 && FIGUREMANAGER.allInBase(playerArray[0]) || FIGUREMANAGER.allInBase(playerArray[0])
                    || FIGUREMANAGER.onlyWith6Movable(playerArray[0])) {

                if (counter < 3 || DICE.getNumber() == 6){
                    userDiceImg.addEventHandler(MouseEvent.MOUSE_CLICKED, diceClickFirstRound);
                } else {
                    stopRoundAnimation(playerArray[0]);
                    // The round is finished and the next player can make a move.
                    playerIndex = 1;
                    playTurn();
                }

            }  else {
                    // DiceEvent, but not for 3 times. A normal round will be played.
                    userDiceImg.addEventHandler(MouseEvent.MOUSE_CLICKED, diceClickNormalRound);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * This method will play a short shake animation for the human player dice.
     * The user will realize quicker if the rolled number is the same as before,
     * or if the user can't roll anymore (Indicating that a figure can be selected now, unless
     * stated otherwise)
     */
    private void playDiceAnimation(String color){
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.1), getDiceImageView(color));
        rotation.setCycleCount(1);
        rotation.setByAngle(20);
        rotation.setOnFinished(event -> {
            RotateTransition rotationBack = new RotateTransition(Duration.seconds(0.1), getDiceImageView(color));
            rotationBack.setCycleCount(1);
            rotationBack.setByAngle(-20);
            rotationBack.play();
        });
        rotation.play();
    }

    /**
     * Dice-Click-Event for the human player of the first round/all figures in the base.
     * If the dice has rolled a 6 the player will be able to move a figure out of the base.
     */
    EventHandler<MouseEvent> diceClickFirstRound = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            createSoundThread("diceRoll");
            playDiceAnimation(userColor);
            DICE.firstRoundRoll();

            setDicePicture(userColor, DICE.getNumber());
            logger.debug("You rolled: " + DICE.getNumber() + ", in try: " + counter + "/3");
            counter++;
            userDiceImg.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

            if (DICE.getNumber() == 6) {
                // Player can now move a figure.
                if (firstMove) {
                    setTextTimer(chooseFigureText, 4);
                    firstMove = false;
                }
                selectFigureEvent();
            } else {
                // Player can't move a figure, so the round will go on.
                humanPlayerEventRoutine();
            }
        }
    };

    /**
     * Dice-Click-Event for the human player of a normal round (Not all figures in the base & not round 1)
     * If the player has at least one movable figure, the select figure event will be executed.
     * If not, the turn will go on to the next player.
     */
    EventHandler<MouseEvent> diceClickNormalRound = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            createSoundThread("diceRoll");
            playDiceAnimation(userColor);
            DICE.roll();
            setDicePicture(userColor, DICE.getNumber());
            logger.info("You rolled: " + DICE.getNumber());


            userDiceImg.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

            // The player can move a figure if at least one in his set is movable.
            if (playerArray[0].hasMoveableFigure(DICE.getNumber())) {
                selectFigureEvent();
            } else {

                // The error text will be shown for 5 seconds, after that it will be erased.
                setTextTimer(skipRoundText, 2);
                // Stop the animation of the shine
                stopRoundAnimation(playerArray[0]);
                // Skip this round and go to the next player
                playerIndex = 1;
                playTurn();
            }
        }
    };

    /**
     * This method will show a message (Error or tip) for the human player
     * for a specific time.
     * @param text that needs to be shown.
     */
    private void setTextTimer(String text , int duration){
        getPlayerErrorLabel().setText(text);
        PauseTransition wait = new PauseTransition(Duration.seconds(duration));
        wait.setOnFinished((e) -> getPlayerErrorLabel().setText(null));
        wait.play();
    }

    /**
     * This method will return the ImageView of a certain color, in order to change it.
     * @param color of the player.
     * @return ImageView of the Dice from a color.
     */
    public ImageView getDiceImageView(String color){
        switch (color){
            case "red":
                return redDice;
            case "blue":
                return blueDice;
            case "green":
                return greenDice;
            default:
                return yellowDice;
        }
    }

    /**
     * This method will update the view and displays the rolled dice number a
     * player has gotten.
     * @param color of the current player.
     * @param diceRoll that has been rolled.
     */
    public void setDicePicture(String color, int diceRoll){
        String filepath = "/images/game/Dice" + diceRoll + ".png";
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(filepath)));
        switch (color){
            case "red":
                redDice.setImage(image);
                break;
            case "blue":
                blueDice.setImage(image);
                break;
            case "green":
                greenDice.setImage(image);
                break;
            default:
                yellowDice.setImage(image);
                break;
        }
    }

    /**
     * This method will move the button the user has chosen and places
     * it to the given coordinates. This is also a stand-alone method
     * and has to be called if the player is a computer(as there is no listener needed).
     * @param button that has been selected and needs to move.
     */
    private void moveFigure(ToggleButton button){
        Figure figure = getFigureOfToggleButton(button);

        move(figure, button);

    }

    /**
     * gets Toggle Button from UI and calles move
     * @param figure is the chosen Figure, that has to move
     */
    private void moveFigure(Figure figure) {
        ToggleButton button = getToggleButtonOfFigure(figure);
        move(figure, button);
    }

    /**
     * This method will move the ToggleButton on the GridPane and sets the new position
     * of that ToggleButton in it's associated Figure.
     * @param figure of the ToggleButton.
     * @param button that has been selected to move.
     */
    private void move(Figure figure, ToggleButton button) {
        // Calculate the targetPosition of the selected Figure
        int[] targetPos = FIGUREMANAGER.targetPos(figure,DICE.getNumber());
        Figure tackleFigure = FIGUREMANAGER.whichFigureTackle(playerArray, figure, targetPos);

        // If the Figure is movable & the position is not null
        if(FIGUREMANAGER.figureMovable(targetPos, getPlayerOfFigure(figure))) {
            if(tackleFigure != null) {
                tackleFigure(tackleFigure);
            }

            int column = targetPos[0];
            int row = targetPos[1];
            // Remove the ToggleButton from it's former position and set it to the new position.
            board.getChildren().remove(button);
            createSoundThread("figure");
            board.add(button,column,row);
            // Set the new position of the figure
            int[] pos = new int[] {column,row};
            figure.setPosition(pos);
            logger.debug("postion of " + figure.getColor() + figure.getFIGUREID() + " has changed to: " + figure.getPosition()[0] + ", " + figure.getPosition()[1]);

        } else {
            logger.error("this figure is not movable");
        }
    }

    /**
     * is called when another figure from another Player is already on the targetfield
     * this figure gets removed from the field and set to its base
     * @param figure is the currently moving figure, who tackles the other figure and stays on the targetField
     */
    private void tackleFigure(Figure figure) {
        ToggleButton button = getToggleButtonOfFigure(figure);
        AbstractPlayer playerOfTackledFig = getPlayerOfFigure(figure);
        assert playerOfTackledFig != null;
        int[] basePos = FIGUREMANAGER.getFreeBasePos(playerOfTackledFig);
        int column = basePos[0];
        int row = basePos[1];

        tackleAnimation(button);

        board.getChildren().remove(button);
        board.add(button,column,row);
        figure.setPosition(basePos);
        logger.debug("Figure " + figure.getColor() + figure.getFIGUREID() +  " got tackled: " + figure.getPosition()[0] + ", " + figure.getPosition()[1]);
    }

    /**
     * Set a special DropShadow Effect for the Human Players Colorgroup.
     */
    private void shadowEffect(){
        switch (userColor){
            case "red" :
                DropShadow redHover = new DropShadow(5, Color.DARKRED);
                redFigure1.setEffect(redHover);
                redFigure2.setEffect(redHover);
                redFigure3.setEffect(redHover);
                redFigure4.setEffect(redHover);
                break;

            case "blue" :
                DropShadow blueHover = new DropShadow(5, Color.DARKBLUE);
                blueFigure1.setEffect(blueHover);
                blueFigure2.setEffect(blueHover);
                blueFigure3.setEffect(blueHover);
                blueFigure4.setEffect(blueHover);
                break;

            case "green" :
                DropShadow greenHover = new DropShadow(5, Color.DARKGREEN);
                greenFigure1.setEffect(greenHover);
                greenFigure2.setEffect(greenHover);
                greenFigure3.setEffect(greenHover);
                greenFigure4.setEffect(greenHover);
                break;

            case "yellow" :
                DropShadow yellowHover = new DropShadow(5, Color.DARKORANGE);
                yellowFigure1.setEffect(yellowHover);
                yellowFigure2.setEffect(yellowHover);
                yellowFigure3.setEffect(yellowHover);
                yellowFigure4.setEffect(yellowHover);
                break;
        }
    }

    /**
     * This method will translate the Figure back into the ToggleButton Figure, so
     * it can be moved in the Gridpane afterwards.
     * @param figure of the Player
     * @return ToggleButton of said Figure from a Player.
     */
    private ToggleButton getToggleButtonOfFigure(Figure figure) {
        switch(figure.getColor()) {
            case "red":
                switch(figure.getFIGUREID()) {
                    case 0: return redFigure1;
                    case 1: return redFigure2;
                    case 2: return redFigure3;
                    default: return redFigure4;
                }
            case "blue":
                switch(figure.getFIGUREID()) {
                    case 0: return blueFigure1;
                    case 1: return blueFigure2;
                    case 2: return blueFigure3;
                    default: return blueFigure4;
                }
            case "yellow":
                switch(figure.getFIGUREID()) {
                    case 0: return yellowFigure1;
                    case 1: return yellowFigure2;
                    case 2: return yellowFigure3;
                    default: return yellowFigure4;
                }
            default:
                switch(figure.getFIGUREID()) {
                    case 0: return greenFigure1;
                    case 1: return greenFigure2;
                    case 2: return greenFigure3;
                    default: return greenFigure4;
                }
        }
    }

    /**
     * This method will translate the given ToggleButton Figure into
     * the Figure of the Player by searching through the participating
     * Players and their Figure positions.
     * @param button that has been selected in the event.
     * @return figure of the ToggleButton
     */
    private Figure getFigureOfToggleButton(ToggleButton button) {
        // line 977 - 985 fixes Bug from grid pane -> returns null when row or column Index is 0
        Integer row = GridPane.getRowIndex(button);
        if (row == null) {
            row = 0;
        }
        Integer column = GridPane.getColumnIndex(button);
        if (column == null) {
            column = 0;
        }
        int[] pos = { column, row};

        try{
            for (AbstractPlayer player : playerArray) {
                return Arrays.stream(player.getFigureSet())
                        .filter(fig -> Arrays.equals(fig.getPosition(), pos))
                        .findFirst()
                        .get();

            }
        } catch (Exception e) {
            logger.fatal("no figure matches the coordinates of the toggleButton");
        }
        return null;
    }

    /**
     * This method will turn the given Figure into the Player who owns it.
     * @param fig that has been selected (The selected ToggleButton is
     *            translated into a Figure first.)
     * @return Player who owns the given figure.
     */
    private AbstractPlayer getPlayerOfFigure(Figure fig) {
        String color = fig.getColor();
        for(AbstractPlayer player: playerArray) {
            if((player.getColor()).equals(color)) {
                return player;
            }
        }
        logger.error("No player to figure " + fig.getColor() + fig.getFIGUREID() + " found");
        return null;
    }

    /**
     * This method will set the active participating color groups
     * to visible and non-participating groups to invisible.
     */
    private void createColorGroupVisibility() {
        // The GUI automatically sets the elements to invisible. Participating players turn visible in the switch.
        for(AbstractPlayer player: playerArray) {
            switch(player.getColor()) {
                case"red" :
                    redBox.setVisible(true);
                    redFigure1.setVisible(true);
                    redFigure2.setVisible(true);
                    redFigure3.setVisible(true);
                    redFigure4.setVisible(true);
                    break;
                case "blue":
                    blueBox.setVisible(true);
                    blueFigure1.setVisible(true);
                    blueFigure2.setVisible(true);
                    blueFigure3.setVisible(true);
                    blueFigure4.setVisible(true);
                    break;
                case "green":
                    greenBox.setVisible(true);
                    greenFigure1.setVisible(true);
                    greenFigure2.setVisible(true);
                    greenFigure3.setVisible(true);
                    greenFigure4.setVisible(true);
                    break;
                case "yellow":
                    yellowBox.setVisible(true);
                    yellowFigure1.setVisible(true);
                    yellowFigure2.setVisible(true);
                    yellowFigure3.setVisible(true);
                    yellowFigure4.setVisible(true);
                    break;
            }
        }
    }

    private void cheat(int stepsToPathEnd) {
        FIGUREMANAGER.winnerCheat(playerArray,stepsToPathEnd);
        for(AbstractPlayer player : playerArray) {
            for(Figure fig : player.getFigureSet()) {
                ToggleButton button = getToggleButtonOfFigure(fig);
                board.getChildren().remove(button);
                board.add(button,fig.getPosition()[0],fig.getPosition()[1]);
            }
        }
    }

    public void createButtonCheat(){
        cheat(3);
        createSoundThread("figure");
        round++;
        resetGameAnimations();
        playRound();
    }

    /**
     * This method returns the current round. Used for
     * the winner PopUp!
     * @return round as a String
     */
    public String getRound(){
        return Integer.toString(round);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get controller from StartGame to use the data of the user.
        start = getInstance().getStartGameController();
        getText(resourceBundle);
        getInstance().setGameController(this);
        // Create Images, Labels and ToggleGroup.
        setUpBoard();
        // Create Game
        setUpGame();
        playRound();
    }
}
