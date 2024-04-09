package Game.GameObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Figure {

    private static final Logger logger = LogManager.getLogger(Figure.class);

    private final int FIGUREID;      // integer [1,4]
    private int[] position;
    private final String color;

    /**
     * Constructor for Figure
     * Create a Figure with color, ID and Startposition
     * @param color is a String of the color the player has
     * @param figureID is a number to identify the figures from a player
     */
    public Figure(String color, int figureID) {
        this.color = color;
        this.FIGUREID = figureID;
        FigureManager figureManager = new FigureManager();
        this.position = figureManager.figureStartPosition(this);
        logger.trace(color + " has the Figure Number " + figureID + " with the position: " + position[0] + ", " + position[1]);
    }

    /**
     * getter Method of figureID
     * @return figureID as int, only 1 to 4 is possible
     */
    public int getFIGUREID() {
        return FIGUREID;
    }

    /**
     * getter Method of Position
     * @return position as int Array
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * getter Method of color
     * @return color of figure as String
     */
    public String getColor() {
        return color;               //Strings are Immutable Objects therefore this returns a string copy the objects to which the reference is attached can never be changed
    }

    /**
     * setter for the Position of a figure
     * @param position is an int Array with row and column coordinates
     */
    public synchronized void setPosition(int[] position) {
        this.position = position;
    }
}
