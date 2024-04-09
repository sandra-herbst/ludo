package Game.GameObjects;

import Game.Player.AbstractPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.IntStream;

public class FigureManager {

    private static final Logger logger = LogManager.getLogger(FigureManager.class);
    private static Figure[] figArr;


//    static private final int[][] allBasePos = new int[][] {{0,0},{1,0},{0,1},{1,1},{9,0},{10,0},{9,1},{10,1},{0,9},{1,9},{0,10},{1,10},{9,9},{10,9},{9,10},{10,10}};

    private final int[][] redBasePos = new int[][]{{0,0},{1,0},{0,1},{1,1}};     //Figures have to be on the right base pos, i.e. figureId = 2 --> BasePos[2]
    private final int[][] blueBasePos = new int[][]{{9,0},{10,0},{9,1},{10,1}};
    private final int[][] yellowBasePos = new int[][]{{0,9},{1,9},{0,10},{1,10}};
    private final int[][] greenBasePos = new int[][]{{9,9},{10,9},{9,10},{10,10}};

    private final int[] redFirstPathPos = new int[] {0,4};
    private final int[] blueFirstPathPos = new int[] {6,0};
    private final int[] yellowFirstPathPos = new int[] {4,10};
    private final int[] greenFirstPathPos = new int[] {10,6};

    private final int[][] redTarget = new int[][]{{1,5},{2,5},{3,5},{4,5}};
    private final int[][] blueTarget = new int[][]{{5,1},{5,2},{5,3},{5,4}};
    private final int[][] yellowTarget = new int[][]{{5,9},{5,8},{5,7},{5,6}};
    private final int[][] greenTarget = new int[][]{{9,5},{8,5},{7,5},{6,5}};

    private final int[][] redPath = new int[][] {{0,4},{1,4},{2,4},{3,4},{4,4},{4,3},{4,2},{4,1},{4,0},{5,0},{6,0},{6,1},{6,2},{6,3},{6,4},{7,4},{8,4},{9,4},{10,4},{10,5},{10,6},{9,6},{8,6},{7,6},{6,6},{6,7},{6,8},{6,9},{6,10},{5,10},{4,10},{4,9},{4,8},{4,7},{4,6},{3,6},{2,6},{1,6},{0,6},{0,5},{1,5},{2,5},{3,5},{4,5}};
    private final int[][] bluePath = new int[][] {{6,0},{6,1},{6,2},{6,3},{6,4},{7,4},{8,4},{9,4},{10,4},{10,5},{10,6},{9,6},{8,6},{7,6},{6,6},{6,7},{6,8},{6,9},{6,10},{5,10},{4,10},{4,9},{4,8},{4,7},{4,6},{3,6},{2,6},{1,6},{0,6},{0,5},{0,4},{1,4},{2,4},{3,4},{4,4},{4,3},{4,2},{4,1},{4,0},{5,0},{5,1},{5,2},{5,3},{5,4}};
    private final int[][] yellowPath = new int[][] {{4,10},{4,9},{4,8},{4,7},{4,6},{3,6},{2,6},{1,6},{0,6},{0,5},{0,4},{1,4},{2,4},{3,4},{4,4},{4,3},{4,2},{4,1},{4,0},{5,0},{6,0},{6,1},{6,2},{6,3},{6,4},{7,4},{8,4},{9,4},{10,4},{10,5},{10,6},{9,6},{8,6},{7,6},{6,6},{6,7},{6,8},{6,9},{6,10},{5,10},{5,9},{5,8},{5,7},{5,6}};
    private final int[][] greenPath = new int[][] {{10,6},{9,6},{8,6},{7,6},{6,6},{6,7},{6,8},{6,9},{6,10},{5,10},{4,10},{4,9},{4,8},{4,7},{4,6},{3,6},{2,6},{1,6},{0,6},{0,5},{0,4},{1,4},{2,4},{3,4},{4,4},{4,3},{4,2},{4,1},{4,0},{5,0},{6,0},{6,1},{6,2},{6,3},{6,4},{7,4},{8,4},{9,4},{10,4},{10,5},{9,5},{8,5},{7,5},{6,5}};

    /**
     * Figure Factory Method, creates 4 figures with ID 0 to 3
     * Is called by UserPlayer constructor and ComPlayer constructor
     * @param color gives the figures the color, which the player has
     * @return figArr as an Array of the four figures in the chosen color
     */
    public Figure[] createFigureSet(String color) {
        figArr = new Figure[4];
        Figure f0 = new Figure(color, 0);
        figArr[0] = f0;
        Figure f1 = new Figure(color, 1);
        figArr[1] = f1;
        Figure f2 = new Figure(color, 2);
        figArr[2] = f2;
        Figure f3 = new Figure(color, 3);
        figArr[3] = f3;
        return figArr;
    }


    /**
     * Calculates the Pos of a Figure for the Start of the game, depending on color and Id of the Figure
     * @param figure is Typ Figure for whom the calculated Pos is
     * @return int Array with column and row coordinates
     */
    public int[] figureStartPosition(Figure figure){
        int[] pos = new int[2];
        switch (figure.getColor()){
            case "red" :
                pos[0] = getColumnStartPosition("red", figure.getFIGUREID());
                pos[1] = getRowStartPosition("red", figure.getFIGUREID());
                break;
            case "blue" :
                pos[0] = getColumnStartPosition("blue", figure.getFIGUREID());
                pos[1] = getRowStartPosition("blue", figure.getFIGUREID());
                break;
            case "green" :
                pos[0] = getColumnStartPosition("green", figure.getFIGUREID());
                pos[1] = getRowStartPosition("green", figure.getFIGUREID());
                break;
            case "yellow" :
                pos[0] = getColumnStartPosition("yellow", figure.getFIGUREID());
                pos[1] = getRowStartPosition("yellow", figure.getFIGUREID());
                break;
        }
        return pos;
    }
    /**
     * This method will return the Column Position of the Start.
     * @param color of the figure
     * @param figureID ranges from 0-3. {0,1,2,3}
     * @return column position of the figureID.
     */
    private int getColumnStartPosition(String color, int figureID){
        switch (color){
            case "red" :
                return redBasePos[figureID][0];

            case "blue" :
                return blueBasePos[figureID][0];

            case "yellow" :
                return yellowBasePos[figureID][0];

            default :
                return greenBasePos[figureID][0];

        }
    }

    /**
     * This method will return the Row Position of the Start.
     * @param color of the figure
     * @param figureID ranges from 0-3. {0,1,2,3}
     * @return row position of the figureID.
     */
    private int getRowStartPosition(String color, int figureID){
        switch (color){
            case "red" :
                return redBasePos[figureID][1];

            case "blue" :
                return blueBasePos[figureID][1];

            case "yellow" :
                return yellowBasePos[figureID][1];

            default :
                return greenBasePos[figureID][1];

        }
    }

    /**
     *is called after a figure is selected and shows if this figure can be moved, if not the user has to select another figure
     * @param  targetPos is the targetField, this method checks if another figure with the same color is already on that field
     * @return false if on the targetPos of this figure is not a other figure with the same color
     */
    public boolean figureMovable(int[] targetPos, AbstractPlayer player) {

        if (targetPos == null) {
            return false;
        } else {
            Figure[] figSet = player.getFigureSet();
            return Arrays.stream(figSet)
                    .parallel()
                    .noneMatch(figure -> Arrays.equals(figure.getPosition(), targetPos));
        }
    }

    /**
     * calculates the target position of the parameter fig and returns it
     * @param fig Object of Figure and has position, diceNum is added to this position in the path
     * @param diceNum steps the fig moves to targetPos
     * @return the targetPos of the fig, or null if the target is not in the path
     */
    public int[] targetPos(Figure fig, int diceNum) {
        // If the selected Figure is in the Base & the diceNumber 6 (condition to move a figure out of their start yard)
        if (currPosInBase(fig) && diceNum == 6) {
            return getFirstPathPos(fig.getColor());
        } else if(currPosInBase(fig) && diceNum != 6) {
            //logger.info(fig.getColor() + fig.getFigureID() + " is not moveable");
            return null;
        }
        try{
            int[] curFigPos = fig.getPosition();
            int arrIndexOfCurPos;
            int[][] path = chooseRightPath(fig.getColor());

            arrIndexOfCurPos = IntStream.range(0, path.length)
                    .filter(i -> Arrays.equals(curFigPos,path[i]))
                    .findFirst()
                    .orElse(-1);	// return -1 if target is not found
            return (arrIndexOfCurPos != -1 ? path[arrIndexOfCurPos + diceNum] : null);

        } catch(Exception e) {
            logger.trace(fig.getColor() + "" + fig.getFIGUREID() + " not movable path has ended");
            return null;
        }
    }

    /**
     * Calculates which Figure is on the target Field and there for has to be moved away
     * @param playerArr is an Array with all Players, who are participating in the game
     * @param figure is the currently moving Figure, who tackles the other Figure
     * @param targetPos is an int Array with row and column coordinates, where the tackling Figure is going to land
     * @return the Figure that is on the target Field or null if no Figure is on that field
     * if null is return no one gets tackled
     */
    public Figure whichFigureTackle(AbstractPlayer[] playerArr, Figure figure, int[] targetPos) {
        AbstractPlayer[] newPlayerArr = Arrays.stream(playerArr)
                .filter(player -> !player.getColor().equals(figure.getColor()))
                .toArray(AbstractPlayer[]::new);

        for(AbstractPlayer player: newPlayerArr) {
            Figure targetFig = Arrays.stream(player.getFigureSet())
                    .filter(f -> Arrays.equals(f.getPosition(),targetPos))
                    .findFirst()
                    .orElse(null);
            if(targetFig != null) {
                return targetFig;
            }
        }
        return null;
    }

    /**
     * Calculates the BasePosition Array
     * depending on the color
     * @param color is the color the Base has
     * @return two dimensional Array of the Base Positions
     */
    private int[][] getBasePosByColor(String color) {
        switch(color) {
            case"red": return redBasePos;
            case"blue":return blueBasePos;
            case"yellow":return yellowBasePos;
            default :return greenBasePos;
        }
    }

    /**
     * gets a Pos on the Base of a specified Player and finds a Field where no Figure already is
     * @param tackledPlayer is the Player in which Base a free Position is calculated
     * @return int Array with row and column coordinates of a free field in the base
     */
    public int[] getFreeBasePos(AbstractPlayer tackledPlayer) {
        int[][] basePos = getBasePosByColor(tackledPlayer.getColor());
        Figure[] figInBase = Arrays.stream(tackledPlayer.getFigureSet())
                .parallel()
                .filter(this::currPosInBase)
                .toArray(Figure[]::new);
        return Arrays.stream(basePos)
                .parallel()
                .filter(pos -> Arrays.stream(figInBase).noneMatch(figure -> Arrays.equals(figure.getPosition(), pos)))
                .findFirst()
                .get();
    }



    /**
     * calculates if the current Pos of a Figure is in its Base
     * @param fig is typ Figure with a pos
     * @return true if the Pos the Figure currently has is in his base
     */
    public boolean currPosInBase(Figure fig) {
        int[][] basePos = getBasePosByColor(fig.getColor());
        return isIn(basePos, fig.getPosition());
    }

    /**
     * Calculates if the Base of a player is empty, that means all four Figures are not currently in the Base
     * @param player is the Player for whom this method checks if his base is empty
     * @return true if the base of the given player is empty
     */
    public boolean noOneInBase(AbstractPlayer player) {
        Figure[] figureSet = player.getFigureSet();
        int[][] basePos = getBasePosByColor(player.getColor());
        return Arrays.stream(figureSet)
                .parallel()
                .noneMatch(fig -> isIn(basePos,fig.getPosition()));
    }

    /**
     * Calculates if the Base of a player is full, that means all four Figures are currently in the Base
     * @param player is the Player for whom this method checks if his base is full
     * @return true if all Figures of the given Player are in his Base
     */
    public boolean allInBase(AbstractPlayer player) {
        Figure[] figureSet = player.getFigureSet();
        int[][] basePos = getBasePosByColor(player.getColor());
        return Arrays.stream(figureSet)
                .parallel()
                .allMatch(fig -> isIn(basePos,fig.getPosition()));
    }

    /**
     * chooses right path of the four givenPaths (red,yellow,green,blue) and returns it
     * @param color is a String
     * @return path depending on parameter Color
     */
    private int[][] chooseRightPath(String color){
            switch(color) {
                case "red": return redPath;
                case "blue": return bluePath;
                case "yellow":return yellowPath;
                default: return greenPath;
            }
        }

    /**
     * Get a Figure from the FigureSet of a color
     * @param figureID ID of the Figure
     * @return Figure
     */
    Figure getFigure(int figureID){
        return figArr[figureID];
    }

    private int[][] getTargetByColor(String color) {
        switch(color) {
            case "red": return redTarget;
            case "blue": return blueTarget;
            case "green":return greenTarget;
            default: return yellowTarget;
        }
    }

    /**
     * this method returns true when one player has all his figures in his target
     * fieldset is called before rolling the dice except in round 1
     * @return if a player has won
     */
    public AbstractPlayer whoWon(AbstractPlayer[] playerArray) {

        for (AbstractPlayer player: playerArray) {
            int[][] targetPos = getTargetByColor(player.getColor());
            boolean someOneWon = Arrays.stream(player.getFigureSet())
                    .parallel()
                    .allMatch(figure -> isIn(targetPos, figure.getPosition()));
            if (someOneWon) {
                return player;
            }
        }
        return null;
    }

    /**
     * Calculates the first Field the Figure steps on then getting out of his Base
     * depending on the color
     * @param color is the color the Base has
     * @return int Array with column and row coordinates of the firstField
     */
    public int[] getFirstPathPos(String color) {
        switch (color) {
            case "red": return redFirstPathPos;
            case "blue": return blueFirstPathPos;
            case "yellow": return yellowFirstPathPos;
            default: return greenFirstPathPos;
        }
    }

    /**
     * Calculates if a int Array is in an two dimensional int Array
     * @param tarArr is a two dimensional array
     * @param pos is the one dimensional array, that is checked if it is in the tarArr
     * @return true if pos is in tarArr
     */
    private boolean isIn(int[][] tarArr, int[] pos ) {
        return Arrays.stream(tarArr)
                .parallel()
                .anyMatch(arrPos -> Arrays.equals(arrPos,pos));
    }

    /**
     * checks if the current Pos of a Figure is on the first Field of its path
     * @param fig is Typ Figure, for whom this method checks if it is on the first Field
     * @return true if the given Figure is on its first field, after getting out of base
     */
    public boolean currPosOnFirstPathPos(Figure fig) {
        switch (fig.getColor()) {
            case "red": return Arrays.equals(fig.getPosition(), redFirstPathPos);
            case "blue": return Arrays.equals(fig.getPosition(), blueFirstPathPos);
            case "yellow": return Arrays.equals(fig.getPosition(), yellowFirstPathPos);
            case "green": return Arrays.equals(fig.getPosition(), greenFirstPathPos);
        }
        logger.error("Can't resolve if figure is on first Field");
        return false;
    }

    /**
     * checks if a player can only move a Figure with a 6
     * @param player is the AbstractPlayer for whom this method checks if he has only Figures,
     *              that are movable with a 6
     * @return true if the given AbstractPlayer has only Figure that are movable with rolling a 6
     */
    public boolean onlyWith6Movable(AbstractPlayer player) {
        int[] numberArray = {1,2,3,4,5};
        Figure[] figureSet = player.getFigureSet();

        return Arrays.stream(figureSet)
                .parallel()
                .noneMatch(figure -> Arrays.stream(numberArray)
                                            .anyMatch(n -> targetPos(figure, n) != null && figureMovable(targetPos(figure, n),player)));
    }

    public void winnerCheat(AbstractPlayer[] playerArray, int stepsToPathEnd) {
        int counter;
        int[][] path;
        for (AbstractPlayer player :playerArray) {
            counter = 0;
            path = getPath(player.getColor());
            for(Figure fig: player.getFigureSet()) {
                fig.setPosition(path[path.length - counter - stepsToPathEnd - 1]);
                counter++;
            }
        }
    }

    public int[][] getPath(String color) {
        switch (color) {
            case "red": return redPath;
            case "blue": return bluePath;
            case "yellow": return yellowPath;
            default: return greenPath;
        }
    }
}
