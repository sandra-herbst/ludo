package Game.Player;

import java.util.Arrays;

/**
 * This class can create User Instances.
 */
public class UserPlayer extends AbstractPlayer {

    /**
     * creates valid UserPlayer Object
     * passes color and name to AbstractPlayer, superclass of UserPlayer
     * @param color is the color in which the user want his figures to be
     * @param name is the name the user wants to be referred to in the game
     */
    public UserPlayer(String color, String name) {
        super(color, name);
        usedColors[Arrays.binarySearch(possibleColors,color)] = true;
    }
}
