package Game.Player;

import Game.GameObjects.Figure;
import Game.GameObjects.FigureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

// abstract class, to prevent instances of this class,
// these objects have to be instances of a Subclass of SuperUser
// (User or ComPlayer)
public abstract class AbstractPlayer {

    private final FigureManager figureManager = new FigureManager();

    protected String[] possibleColors = {"blue", "green", "red", "yellow"};
    static protected boolean[] usedColors = {false,false,false,false}; // colors set to true are already used
    private String name;
    private String color;
    private Figure[] figureSet =  new Figure[4];
    private boolean hasMovableFigures = true;

    /**
     * A player can be identified by the following:
     * @param color of their figures on the board
     * @param name of the player
     */
    //Constructor for User
    public AbstractPlayer(String color, String name) {
        this.color = color;
        this.name = name;
        this.figureSet = figureManager.createFigureSet(color);
    }

    /**
     * is empty because fields are not able to be setted in the first line of ComPlayer constructor
     * is needed by subclass ComPlayer, because subclasses need to call an constructor, but ComPlayer has to set a Color first and sets the Values with Setter Method in the next Step (s. ComPlayer.java)
     */
    protected AbstractPlayer(){
    }

    /**
     * getter for hasMovableFigure
     * @return true, if the Player has at least one Figure that is movable with any dice number
     */
    public boolean getHasMoveableFigure() {
        return hasMovableFigures;
    }

    
    public boolean hasMoveableFigure(int diceNum) {
        for(Figure fig: this.figureSet) {
            int[] targPos = figureManager.targetPos(fig, diceNum);
            if(figureManager.figureMovable(targPos,this)) {
                return true;
            }
        }
        return false;
    }

    protected void setHasMovableFigures(boolean hasMoveableFigures) {
        this.hasMovableFigures = hasMoveableFigures;
    }

    /**
     * getter Method of private variable name
     * @return name of Player
     */
    public String getName() {
        return name;                //Strings are Immutable Objects therefore this returns a string copy the objects to which the reference is attached can never be changed
    }

    /**
     * getter Method of figureSet
     * @return copy of original figureSet
     */
    public Figure[] getFigureSet() {
        return figureSet;
    }

    /**
     * getter Method of private variable color
     * @return color of Player
     */
    public String getColor() {
       return color;
    }

    /**
     * setColor is called by GamePlayer constructor to get an valide ComPlayer Object
     * @param color is an string of the color the playerObject gets, it is not possible to have two players with the same color
     */
    protected synchronized void setColor(String color) {
        this.color = color;
    }

    /**
     * setColor is called by GamePlayer constructor to get an valide ComPlayer Object
     * @param name is an string of the name the playerObject gets, ComPlayers are called after their color
     */
    protected synchronized void setName(String name) {
        this.name = name;
    }

    /**
     * setFigureSet is called by GamePlayer constructor to get an valide ComPlayer Object
     * @param figureSet is an array of 4 figures in the given color, every player needs to play
     */
    protected synchronized void setFigureSet(Figure[] figureSet) {
        this.figureSet = figureSet;
    }

    /**
     * Reset used colors to initial boolean value, so the game can be restarted.
     */
    public void resetColors(){
        Arrays.fill(usedColors, false);
    }
    /**
     * is only needed by logger
     * @return name of the object
     */
    @Override
    public String toString() {
        return name;
    }
}
