package Game.GameObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class can create Game.GameObjects.Dice Instances for a User.
 */
public class Dice {

    private static final Logger logger = LogManager.getLogger(Dice.class);

    private int number;             //integer [1,6]
    private boolean rollAgain;      //true when number == 6

    /**
     * Constructor for Dice
     * Creates dice, default value is 1
     */
    public Dice() {
        this.number = 1;        // value is not important, setted to have a valid Object
        this.rollAgain = false;
    }

    /**
     * This method rolls a number between 1-6 and checks whether or not
     * a 6 has been rolled.
     * The result can be accessed by get methods.
     */
    public synchronized void roll() {
        this.number = (int)Math.round(Math.random() * 5 + 1);
        this.rollAgain = (number == 6);
    }

    /**
     * Is called in the firstRound()
     * the probability to roll a 6 is higher (30%) than in a normal round
     */
    public synchronized void firstRoundRoll() {
        int randomTempNum = (int) Math.round(Math.random() * 100);
        if(randomTempNum <= 13) {
            this.number = 1;
            this.rollAgain = false;
        } else if(randomTempNum <= 27) {
            this.number = 2;
            this.rollAgain = false;
        } else if(randomTempNum <= 41) {
            this.number = 3;
            this.rollAgain = false;
        } else if(randomTempNum <= 55) {
            this.number = 4;
            this.rollAgain = false;
        } else if(randomTempNum <= 69) {
            this.number = 5;
            this.rollAgain = false;
        } else if(randomTempNum <= 100) {
            this.number = 6;
            this.rollAgain = true;
        }
        logger.trace("higher Probability for 6: rolledNumber is"+ randomTempNum);
    }

    /**
     * getter method of number
     * @return rolled Number as int
     */
    public int getNumber() {
        return number;
    }

    /**
     * getter Method of rollAgain
     * @return if the Player is allowed to roll Again, this is the case when number equals 6
     */
    public boolean getRollAgain() {
        return rollAgain;
    }
}
