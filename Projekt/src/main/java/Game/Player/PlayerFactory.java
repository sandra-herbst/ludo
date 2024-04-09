package Game.Player;

import Exceptions.IllegalFactoryArgument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Creates all Players used for one game, depending on how many comPlayer the user chose to play against.
 */
public class PlayerFactory {

    private static final Logger logger = LogManager.getLogger(PlayerFactory.class);
    private AbstractPlayer user;

    /**
     * This method will create the players and returns the participating player array.
     * @param comPlayerCount how many ComPlayers are participating in the game.
     * @param username of the user that is playing.
     * @param userColor of the user that is playing
     * @return Array of all participants
     * @throws IllegalFactoryArgument if comPlayerCount is invalid.
     */
    public AbstractPlayer[] getPlayerArray(int comPlayerCount, String username, String userColor) throws IllegalFactoryArgument {
        // Create AbstractPlayer Array to save all players in.
        AbstractPlayer[] playerArray = new AbstractPlayer[comPlayerCount + 1];
        // In the array, the Human Player will be the first one.
        user = new UserPlayer(userColor, username);
        playerArray[0] = user;

        // Create ComPlayers
        switch(comPlayerCount) {
            default : throw new IllegalFactoryArgument("Invalid number of Complayer amount value.");
            case 3: AbstractPlayer com3 = new ComPlayer();playerArray[3] = com3;
            case 2: AbstractPlayer com2 = new ComPlayer();playerArray[2] = com2;
            case 1: AbstractPlayer com1 = new ComPlayer();playerArray[1] = com1;
        }

        StringBuilder temp = new StringBuilder();       //temp Variable to log the playerArray, is not used anywhere else
        for (AbstractPlayer player: playerArray) {
            temp.append(player.toString()).append(", ");
        }
        logger.debug("Array of all Players: " + temp.substring(0,temp.length()-2));

        return playerArray;
    }

    /**
     * This method will reset all of the used colors from true to false, so the
     * color can be used again. Used if game is being resetted.
     */
    public void resetUserColors(){
        user.resetColors();
    }

    public boolean needsHumanInteraction(AbstractPlayer player) {
        return player instanceof UserPlayer;
    }
}


