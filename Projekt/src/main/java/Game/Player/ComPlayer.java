package Game.Player;

import Game.GameObjects.Figure;
import Game.GameObjects.FigureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * This class can create Game.Player.ComPlayer Instances.
 */
public class ComPlayer extends AbstractPlayer {

    private static final Logger logger = LogManager.getLogger(ComPlayer.class);
    private final FigureManager FIGUREMANAGER = new FigureManager();

    /**
     * creates valid ComPlayer Object
     */
    public ComPlayer() {
        super();
        String color = getUnusedColor();
        setColor(color);
        setName(color);
        setFigureSet(FIGUREMANAGER.createFigureSet(color));

        usedColors[Arrays.binarySearch(possibleColors,color)] = true;

        logger.info("ComPlayer created: color : " + super.getColor() + ", name: " + super.getName());
        logger.info("FigureSet for ComPlayer \"" + color + "\" created, color: " + getFigureSet()[0].getColor()); //all Figures in one FigureSet have the same color

    }

    /**
     * gets the next unused color from possibleColors array
     * @return not used color as String
     */
    private String getUnusedColor() {
        int index = 0;
        try {
            for (boolean colorUsed : usedColors) {
                if (!colorUsed) {
                    return possibleColors[index];
                }
                index++;
            }
            throw new Exception();
        } catch (Exception e) {
            logger.error("No color left");
            return "black";
        }
    }

    /**
     * Simulates selecting a Figure
     * @param diceNum is the number this player has rolled
     * @return a Figure if one is movable, if not this method returns null
     */
    public Figure selectFigure(int diceNum) {
        if(diceNum == 6) {
            if (FIGUREMANAGER.allInBase(this)) {
                setHasMovableFigures(true);
                return getFigureSet()[0];
            } else if (FIGUREMANAGER.noOneInBase(this)) {
                for (Figure f : getFigureSet()) {
                    if (FIGUREMANAGER.currPosOnFirstPathPos(f) && FIGUREMANAGER.figureMovable(FIGUREMANAGER.getFirstPathPos(this.getColor()), this)) {
                        setHasMovableFigures(true);
                        return f;
                    }
                }
                for (Figure f : getFigureSet()) {
                    int[] targetPos = FIGUREMANAGER.targetPos(f, diceNum);
                    if (FIGUREMANAGER.figureMovable(targetPos, this)) {
                        setHasMovableFigures(true);
                        return f;
                    }
                }
            } else {
                for (Figure f : getFigureSet()) {
                    if (FIGUREMANAGER.currPosOnFirstPathPos(f) && FIGUREMANAGER.figureMovable(FIGUREMANAGER.getFirstPathPos(this.getColor()), this)) {
                        setHasMovableFigures(true);
                        return f;
                    }
                }
                for (Figure f : getFigureSet()) {
                    if (FIGUREMANAGER.currPosInBase(f) && FIGUREMANAGER.figureMovable(FIGUREMANAGER.getFirstPathPos(this.getColor()), this)) {
                        setHasMovableFigures(true);
                        return f;
                    }
                }
                for (Figure f : getFigureSet()) {
                    int[] targetPos = FIGUREMANAGER.targetPos(f, diceNum);
                    if (!(FIGUREMANAGER.currPosInBase(f)) && FIGUREMANAGER.figureMovable(targetPos, this)) {
                        setHasMovableFigures(true);
                        return f;
                    }
                }
            }
        } else {
            for (Figure f : getFigureSet()) {
                int[] targetPos = FIGUREMANAGER.targetPos(f, diceNum);
                if (FIGUREMANAGER.currPosOnFirstPathPos(f) && FIGUREMANAGER.figureMovable(targetPos, this)) {
                    setHasMovableFigures(true);
                    return f;
                }
            }
            for (Figure f : getFigureSet()) {
                int[] targetPos = FIGUREMANAGER.targetPos(f, diceNum);
                if (!(FIGUREMANAGER.currPosInBase(f)) && FIGUREMANAGER.figureMovable(targetPos, this)) {
                    setHasMovableFigures(true);
                    return f;
                }
            }
        }

        logger.info("No figure is moveable: next Player");
        setHasMovableFigures(false);
        return null;
    }
}
