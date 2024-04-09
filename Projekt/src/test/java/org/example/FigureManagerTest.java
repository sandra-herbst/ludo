package org.example;

import Exceptions.IllegalFactoryArgument;
import Game.GameObjects.Figure;
import Game.GameObjects.FigureManager;
import Game.Player.AbstractPlayer;
import Game.Player.PlayerFactory;
import Game.Player.UserPlayer;
import org.junit.*;

/**
 * Unit test of {@link FigureManager}.
 */
public class FigureManagerTest {
    private static final PlayerFactory factory = new PlayerFactory();
    private static AbstractPlayer[] playerArray;

    static {
        try {
            playerArray = factory.getPlayerArray(3, "Mia", "red");
        } catch (IllegalFactoryArgument illegalFactoryArgument) {
            illegalFactoryArgument.printStackTrace();
        }
    }

    private static final FigureManager figureManager = new FigureManager();
    //red
    private static final AbstractPlayer userPlayer1 = playerArray[0];
    private static final Figure[] userPlayer1FigureSet = userPlayer1.getFigureSet();
    //yellow
    private static final AbstractPlayer comPlayer1 = playerArray[1];
    private static final Figure[] comPlayer1FigureSet = comPlayer1.getFigureSet();
    //green
    private static final AbstractPlayer comPlayer2 = playerArray[2];
    private static final Figure[] comPlayer2FigureSet = comPlayer2.getFigureSet();
    //blue
    private static final AbstractPlayer comPlayer3 = playerArray[3];
    private static final Figure[] comPlayer3FigureSet = comPlayer3.getFigureSet();


    public static void resetAllFiguresInBase() {
        userPlayer1FigureSet[0].setPosition(new int[]{0, 0});
        userPlayer1FigureSet[1].setPosition(new int[]{1, 0});
        userPlayer1FigureSet[2].setPosition(new int[]{0, 1});
        userPlayer1FigureSet[3].setPosition(new int[]{1, 1});

        comPlayer1FigureSet[0].setPosition(new int[]{0, 9});
        comPlayer1FigureSet[1].setPosition(new int[]{1, 9});
        comPlayer1FigureSet[2].setPosition(new int[]{0, 10});
        comPlayer1FigureSet[3].setPosition(new int[]{1, 10});

        comPlayer2FigureSet[0].setPosition(new int[]{9, 9});
        comPlayer2FigureSet[1].setPosition(new int[]{10, 9});
        comPlayer2FigureSet[2].setPosition(new int[]{9, 10});
        comPlayer2FigureSet[3].setPosition(new int[]{10, 10});

        comPlayer3FigureSet[0].setPosition(new int[]{9, 0});
        comPlayer3FigureSet[1].setPosition(new int[]{10, 0});
        comPlayer3FigureSet[2].setPosition(new int[]{9, 1});
        comPlayer3FigureSet[3].setPosition(new int[]{10, 1});
    }

    /**
     * Should return all figures in the color that has been set.
     */
    @Test
    public void testCreateFigureSet() {
        Assert.assertEquals("red", figureManager.createFigureSet("red")[0].getColor());
        Assert.assertEquals("red", figureManager.createFigureSet("red")[1].getColor());
        Assert.assertEquals("red", figureManager.createFigureSet("red")[2].getColor());
        Assert.assertEquals("red", figureManager.createFigureSet("red")[3].getColor());
        Assert.assertEquals("blue", figureManager.createFigureSet("blue")[0].getColor());
        Assert.assertEquals("blue", figureManager.createFigureSet("blue")[1].getColor());
        Assert.assertEquals("blue", figureManager.createFigureSet("blue")[2].getColor());
        Assert.assertEquals("blue", figureManager.createFigureSet("blue")[3].getColor());
        Assert.assertEquals("green", figureManager.createFigureSet("green")[0].getColor());
        Assert.assertEquals("green", figureManager.createFigureSet("green")[1].getColor());
        Assert.assertEquals("green", figureManager.createFigureSet("green")[2].getColor());
        Assert.assertEquals("green", figureManager.createFigureSet("green")[3].getColor());
        Assert.assertEquals("yellow", figureManager.createFigureSet("yellow")[0].getColor());
        Assert.assertEquals("yellow", figureManager.createFigureSet("yellow")[1].getColor());
        Assert.assertEquals("yellow", figureManager.createFigureSet("yellow")[2].getColor());
        Assert.assertEquals("yellow", figureManager.createFigureSet("yellow")[3].getColor());

        Assert.assertNotEquals("brown", figureManager.createFigureSet("red")[0].getColor());
        Assert.assertNotEquals("lila", figureManager.createFigureSet("red")[1].getColor());
        Assert.assertNotEquals("green", figureManager.createFigureSet("red")[2].getColor());
        Assert.assertNotEquals("yellow", figureManager.createFigureSet("red")[3].getColor());
        Assert.assertNotEquals("pink", figureManager.createFigureSet("blue")[0].getColor());
        Assert.assertNotEquals("red", figureManager.createFigureSet("blue")[1].getColor());
        Assert.assertNotEquals("grey", figureManager.createFigureSet("blue")[2].getColor());
        Assert.assertNotEquals("black", figureManager.createFigureSet("blue")[3].getColor());
        Assert.assertNotEquals("violet", figureManager.createFigureSet("green")[0].getColor());
        Assert.assertNotEquals("pink", figureManager.createFigureSet("green")[1].getColor());
        Assert.assertNotEquals("yellow", figureManager.createFigureSet("green")[2].getColor());
        Assert.assertNotEquals("blue", figureManager.createFigureSet("green")[3].getColor());
        Assert.assertNotEquals("red", figureManager.createFigureSet("yellow")[0].getColor());
        Assert.assertNotEquals("black", figureManager.createFigureSet("yellow")[1].getColor());
        Assert.assertNotEquals("pink", figureManager.createFigureSet("yellow")[2].getColor());
        Assert.assertNotEquals("grey", figureManager.createFigureSet("yellow")[3].getColor());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testCreateFigureSetException() throws ArrayIndexOutOfBoundsException {
        AbstractPlayer userPlayerExample = new UserPlayer("pink","Magdalena Mirabella");
    }

    /**
     * Check if the right position returns.
     */
    @Test
    public void testFigureStartPosition() {
        Assert.assertEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[0])[0]);
        Assert.assertEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[0])[1]);
        Assert.assertEquals(1, figureManager.figureStartPosition(userPlayer1FigureSet[1])[0]);
        Assert.assertEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[1])[1]);
        Assert.assertEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[2])[0]);
        Assert.assertEquals(1, figureManager.figureStartPosition(userPlayer1FigureSet[2])[1]);
        Assert.assertEquals(1, figureManager.figureStartPosition(userPlayer1FigureSet[3])[0]);
        Assert.assertEquals(1, figureManager.figureStartPosition(userPlayer1FigureSet[3])[1]);

        Assert.assertNotEquals(1, figureManager.figureStartPosition(userPlayer1FigureSet[0])[0]);
        Assert.assertNotEquals(1, figureManager.figureStartPosition(userPlayer1FigureSet[0])[1]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[1])[0]);
        Assert.assertNotEquals(1, figureManager.figureStartPosition(userPlayer1FigureSet[1])[1]);
        Assert.assertNotEquals(1, figureManager.figureStartPosition(userPlayer1FigureSet[2])[0]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[2])[1]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[3])[0]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[3])[1]);
    }



    /**
     * Check if the column position of the Start returns.
     */
    @Test
    public void testGetColumnStartPosition() {
        Assert.assertEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[0])[0]);
        Assert.assertEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[0])[1]);
        Assert.assertEquals(0, figureManager.figureStartPosition(comPlayer1FigureSet[0])[0]);
        Assert.assertEquals(9, figureManager.figureStartPosition(comPlayer1FigureSet[0])[1]);
        Assert.assertEquals(9, figureManager.figureStartPosition(comPlayer2FigureSet[0])[0]);
        Assert.assertEquals(9, figureManager.figureStartPosition(comPlayer2FigureSet[0])[1]);
        Assert.assertEquals(9, figureManager.figureStartPosition(comPlayer3FigureSet[0])[0]);
        Assert.assertEquals(0, figureManager.figureStartPosition(comPlayer3FigureSet[0])[1]);

        Assert.assertNotEquals(9, figureManager.figureStartPosition(userPlayer1FigureSet[0])[0]);
        Assert.assertNotEquals(9, figureManager.figureStartPosition(userPlayer1FigureSet[0])[1]);
        Assert.assertNotEquals(9, figureManager.figureStartPosition(comPlayer1FigureSet[0])[0]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(comPlayer1FigureSet[0])[1]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(comPlayer2FigureSet[0])[0]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(comPlayer2FigureSet[0])[1]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(comPlayer3FigureSet[0])[0]);
        Assert.assertNotEquals(9, figureManager.figureStartPosition(comPlayer3FigureSet[0])[1]);
    }

    /**
     * Check if the row position of the Start returns.
     */
    @Test
    public void testGetRowStartPosition() {
        Assert.assertEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[0])[0]);
        Assert.assertEquals(0, figureManager.figureStartPosition(userPlayer1FigureSet[0])[1]);
        Assert.assertEquals(0, figureManager.figureStartPosition(comPlayer1FigureSet[0])[0]);
        Assert.assertEquals(9, figureManager.figureStartPosition(comPlayer1FigureSet[0])[1]);
        Assert.assertEquals(9, figureManager.figureStartPosition(comPlayer2FigureSet[0])[0]);
        Assert.assertEquals(9, figureManager.figureStartPosition(comPlayer2FigureSet[0])[1]);
        Assert.assertEquals(9, figureManager.figureStartPosition(comPlayer3FigureSet[0])[0]);
        Assert.assertEquals(0, figureManager.figureStartPosition(comPlayer3FigureSet[0])[1]);

        Assert.assertNotEquals(9, figureManager.figureStartPosition(userPlayer1FigureSet[0])[0]);
        Assert.assertNotEquals(9, figureManager.figureStartPosition(userPlayer1FigureSet[0])[1]);
        Assert.assertNotEquals(9, figureManager.figureStartPosition(comPlayer1FigureSet[0])[0]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(comPlayer1FigureSet[0])[1]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(comPlayer2FigureSet[0])[0]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(comPlayer2FigureSet[0])[1]);
        Assert.assertNotEquals(0, figureManager.figureStartPosition(comPlayer3FigureSet[0])[0]);
        Assert.assertNotEquals(9, figureManager.figureStartPosition(comPlayer3FigureSet[0])[1]);
    }

    /**
     * Check if Figure can be moved.
     */
    @Test
    public void testFigureMovable() {
        resetAllFiguresInBase();
        Assert.assertTrue(figureManager.figureMovable(new int[]{0, 4}, userPlayer1));
        Assert.assertTrue(figureManager.figureMovable(new int[]{0, 4}, comPlayer1));
        Assert.assertTrue(figureManager.figureMovable(new int[]{0, 4}, comPlayer2));
        Assert.assertTrue(figureManager.figureMovable(new int[]{0, 4}, comPlayer3));

        userPlayer1FigureSet[0].setPosition(new int[]{3, 4});
        comPlayer1FigureSet[0].setPosition(new int[]{4, 8});
        comPlayer2FigureSet[0].setPosition(new int[]{7, 6});
        comPlayer3FigureSet[0].setPosition(new int[]{6, 3});

        Assert.assertFalse(figureManager.figureMovable(new int[]{3, 4}, userPlayer1));
        Assert.assertFalse(figureManager.figureMovable(new int[]{4, 8}, comPlayer1));
        Assert.assertFalse(figureManager.figureMovable(new int[]{7, 6}, comPlayer2));
        Assert.assertFalse(figureManager.figureMovable(new int[]{6, 3}, comPlayer3));
    }

    /**
     * Check if Figure jumps to the target position.
     */
    @Test
    public void testTargetPos() {
        resetAllFiguresInBase();
        Assert.assertArrayEquals(new int[]{0, 4}, figureManager.targetPos(userPlayer1FigureSet[0], 6));
        Assert.assertArrayEquals(new int[]{4, 10}, figureManager.targetPos(comPlayer1FigureSet[0], 6));
        Assert.assertArrayEquals(new int[]{10, 6}, figureManager.targetPos(comPlayer2FigureSet[0], 6));
        Assert.assertArrayEquals(new int[]{6, 0}, figureManager.targetPos(comPlayer3FigureSet[0], 6));

        userPlayer1FigureSet[0].setPosition(new int[]{1, 4});
        comPlayer1FigureSet[0].setPosition(new int[]{4, 9});
        comPlayer2FigureSet[0].setPosition(new int[]{6, 0});
        comPlayer3FigureSet[0].setPosition(new int[]{6, 2});

        Assert.assertArrayEquals(new int[]{4, 2}, figureManager.targetPos(userPlayer1FigureSet[0], 5));
        Assert.assertArrayEquals(new int[]{4, 6}, figureManager.targetPos(comPlayer1FigureSet[0], 3));
        Assert.assertArrayEquals(new int[]{6, 4}, figureManager.targetPos(comPlayer2FigureSet[0], 4));
        Assert.assertArrayEquals(new int[]{6, 3}, figureManager.targetPos(comPlayer3FigureSet[0], 1));

        Assert.assertNotEquals(10, figureManager.targetPos(userPlayer1FigureSet[0], 5)[0]);
        Assert.assertNotEquals(10, figureManager.targetPos(userPlayer1FigureSet[0], 5)[1]);
        Assert.assertNotEquals(10, figureManager.targetPos(comPlayer1FigureSet[0], 3)[0]);
        Assert.assertNotEquals(10, figureManager.targetPos(comPlayer1FigureSet[0], 3)[1]);
        Assert.assertNotEquals(10, figureManager.targetPos(comPlayer2FigureSet[0], 4)[0]);
        Assert.assertNotEquals(10, figureManager.targetPos(comPlayer2FigureSet[0], 4)[1]);
        Assert.assertNotEquals(10, figureManager.targetPos(comPlayer3FigureSet[0], 1)[0]);
        Assert.assertNotEquals(10, figureManager.targetPos(comPlayer3FigureSet[0], 1)[1]);
    }

    /**
     *
     */
    @Test
    public void testWhichFigureTackle() {
        resetAllFiguresInBase();
        //No one gets tackled
        Assert.assertNull(figureManager.whichFigureTackle(playerArray, userPlayer1FigureSet[0], new int[]{2, 4}));
        Assert.assertNull(figureManager.whichFigureTackle(playerArray, comPlayer1FigureSet[0], new int[]{4, 8}));
        Assert.assertNull(figureManager.whichFigureTackle(playerArray, comPlayer2FigureSet[0], new int[]{6, 6}));
        Assert.assertNull(figureManager.whichFigureTackle(playerArray, comPlayer3FigureSet[0], new int[]{9, 4}));

        userPlayer1FigureSet[0].setPosition(new int[]{3, 4});
        comPlayer1FigureSet[0].setPosition(new int[]{4, 7});
        comPlayer2FigureSet[0].setPosition(new int[]{7, 6});
        comPlayer3FigureSet[0].setPosition(new int[]{6, 3});

        Assert.assertEquals(comPlayer1FigureSet[0], figureManager.whichFigureTackle(playerArray, userPlayer1FigureSet[0], new int[]{4, 7}));
        Assert.assertEquals(userPlayer1FigureSet[0], figureManager.whichFigureTackle(playerArray, comPlayer1FigureSet[0], new int[]{3, 4}));
        Assert.assertEquals(comPlayer3FigureSet[0], figureManager.whichFigureTackle(playerArray, comPlayer2FigureSet[0], new int[]{6, 3}));
        Assert.assertEquals(comPlayer2FigureSet[0], figureManager.whichFigureTackle(playerArray, comPlayer3FigureSet[0], new int[]{7, 6}));
    }

    /**
     * Check if Figure returns to the free field in the base.
     */
    @Test
    public void testGetFreeBasePos() {
        resetAllFiguresInBase();
        userPlayer1FigureSet[0].setPosition(new int[]{3, 4});
        comPlayer1FigureSet[0].setPosition(new int[]{4, 7});
        comPlayer2FigureSet[0].setPosition(new int[]{7, 6});
        comPlayer3FigureSet[0].setPosition(new int[]{6, 3});

        Assert.assertArrayEquals(new int[]{0, 0}, figureManager.getFreeBasePos(userPlayer1));
        Assert.assertArrayEquals(new int[]{0, 9}, figureManager.getFreeBasePos(comPlayer1));
        Assert.assertArrayEquals(new int[]{9, 9}, figureManager.getFreeBasePos(comPlayer2));
        Assert.assertArrayEquals(new int[]{9, 0}, figureManager.getFreeBasePos(comPlayer3));

        Assert.assertNotEquals(11, figureManager.getFreeBasePos(userPlayer1)[0]);
        Assert.assertNotEquals(11, figureManager.getFreeBasePos(userPlayer1)[1]);
        Assert.assertNotEquals(11, figureManager.getFreeBasePos(comPlayer3)[0]);
        Assert.assertNotEquals(11, figureManager.getFreeBasePos(comPlayer3)[1]);
    }

    /**
     * Check if the current position is in the base.
     */
    @Test
    public void testCurrPosInBase() {
        resetAllFiguresInBase();
        Assert.assertTrue(figureManager.currPosInBase(userPlayer1FigureSet[0]));
        Assert.assertTrue(figureManager.currPosInBase(comPlayer1FigureSet[0]));
        Assert.assertTrue(figureManager.currPosInBase(comPlayer2FigureSet[0]));
        Assert.assertTrue(figureManager.currPosInBase(comPlayer3FigureSet[0]));

        //set somewhere on the field
        userPlayer1FigureSet[0].setPosition(new int[]{1, 4});
        comPlayer1FigureSet[0].setPosition(new int[]{4, 9});
        comPlayer2FigureSet[0].setPosition(new int[]{9, 6});
        comPlayer3FigureSet[0].setPosition(new int[]{6, 1});

        Assert.assertFalse(figureManager.currPosInBase(comPlayer1FigureSet[0]));
        Assert.assertFalse(figureManager.currPosInBase(comPlayer2FigureSet[0]));
        Assert.assertFalse(figureManager.currPosInBase(userPlayer1FigureSet[0]));
        Assert.assertFalse(figureManager.currPosInBase(comPlayer3FigureSet[0]));
    }

    /**
     * Check if all figures are in base.
     */
    @Test
    public void testNoOneInBase() {
        resetAllFiguresInBase();
        Assert.assertFalse(figureManager.noOneInBase(userPlayer1));
        Assert.assertFalse(figureManager.noOneInBase(comPlayer1));
        Assert.assertFalse(figureManager.noOneInBase(comPlayer2));
        Assert.assertFalse(figureManager.noOneInBase(comPlayer3));

        //Set all figures on the path
        userPlayer1FigureSet[0].setPosition(new int[]{1, 4});
        userPlayer1FigureSet[1].setPosition(new int[]{2, 4});
        userPlayer1FigureSet[2].setPosition(new int[]{3, 4});
        userPlayer1FigureSet[3].setPosition(new int[]{4, 4});
        Assert.assertTrue(figureManager.noOneInBase(userPlayer1));

        comPlayer1FigureSet[0].setPosition(new int[]{6, 6});
        comPlayer1FigureSet[1].setPosition(new int[]{7, 6});
        comPlayer1FigureSet[2].setPosition(new int[]{8, 6});
        comPlayer1FigureSet[3].setPosition(new int[]{9, 6});
        Assert.assertTrue(figureManager.noOneInBase(comPlayer1));

        comPlayer2FigureSet[0].setPosition(new int[]{6, 7});
        comPlayer2FigureSet[1].setPosition(new int[]{7, 7});
        comPlayer2FigureSet[2].setPosition(new int[]{8, 7});
        comPlayer2FigureSet[3].setPosition(new int[]{9, 7});
        Assert.assertTrue(figureManager.noOneInBase(comPlayer2));

        comPlayer3FigureSet[0].setPosition(new int[]{6, 2});
        comPlayer3FigureSet[1].setPosition(new int[]{7, 2});
        comPlayer3FigureSet[2].setPosition(new int[]{8, 2});
        comPlayer3FigureSet[3].setPosition(new int[]{9, 2});
        Assert.assertTrue(figureManager.noOneInBase(comPlayer3));
    }

    /**
     * Check if all figures are in base.
     */
    @Test
    public void testAllInBase() {
        resetAllFiguresInBase();
        Assert.assertTrue(figureManager.allInBase(userPlayer1));
        Assert.assertTrue(figureManager.allInBase(comPlayer1));
        Assert.assertTrue(figureManager.allInBase(comPlayer2));
        Assert.assertTrue(figureManager.allInBase(comPlayer3));

        //Set one figure of each player somewhere one the field.
        comPlayer1FigureSet[0].setPosition(new int[]{10, 6});
        comPlayer2FigureSet[0].setPosition(new int[]{9, 6});
        userPlayer1FigureSet[0].setPosition(new int[]{7, 6});
        comPlayer3FigureSet[0].setPosition(new int[]{6, 6});

        Assert.assertFalse(figureManager.allInBase(comPlayer1));
        Assert.assertFalse(figureManager.allInBase(comPlayer2));
        Assert.assertFalse(figureManager.allInBase(userPlayer1));
        Assert.assertFalse(figureManager.allInBase(comPlayer3));
    }

    /**
     * Check if the right winnter returns, if alle Figures are in target field.
     */
    @Test
    public void testWhoWon() {
        //Nobody won
        Assert.assertNull(figureManager.whoWon(playerArray));

        //Set every figure in target fieldset
        userPlayer1FigureSet[0].setPosition(new int[]{1, 5});
        userPlayer1FigureSet[1].setPosition(new int[]{2, 5});
        userPlayer1FigureSet[2].setPosition(new int[]{3, 5});
        userPlayer1FigureSet[3].setPosition(new int[]{4, 5});

        Assert.assertEquals(userPlayer1, figureManager.whoWon(playerArray));
    }

    /**
     * Check the coordinates of the start position of each color.
     */
    @Test
    public void testGetFirstPathPos() {
        Assert.assertEquals(0, figureManager.getFirstPathPos("red")[0]);
        Assert.assertEquals(4, figureManager.getFirstPathPos("red")[1]);
        Assert.assertEquals(6, figureManager.getFirstPathPos("blue")[0]);
        Assert.assertEquals(0, figureManager.getFirstPathPos("blue")[1]);
        Assert.assertEquals(10, figureManager.getFirstPathPos("green")[0]);
        Assert.assertEquals(6, figureManager.getFirstPathPos("green")[1]);
        Assert.assertEquals(4, figureManager.getFirstPathPos("yellow")[0]);
        Assert.assertEquals(10, figureManager.getFirstPathPos("yellow")[1]);

        Assert.assertNotEquals(0, figureManager.getFirstPathPos("yellow")[0]);
        Assert.assertNotEquals(4, figureManager.getFirstPathPos("yellow")[1]);
        Assert.assertNotEquals(6, figureManager.getFirstPathPos("green")[0]);
        Assert.assertNotEquals(0, figureManager.getFirstPathPos("green")[1]);
        Assert.assertNotEquals(10, figureManager.getFirstPathPos("blue")[0]);
        Assert.assertNotEquals(6, figureManager.getFirstPathPos("blue")[1]);
        Assert.assertNotEquals(4, figureManager.getFirstPathPos("red")[0]);
        Assert.assertNotEquals(10, figureManager.getFirstPathPos("red")[1]);
    }

    /**
     * Check if the figure is on the start position.
     */
    @Test
    public void testCurrPosOnFirstPathPos() {
        resetAllFiguresInBase();
        Assert.assertFalse(figureManager.currPosOnFirstPathPos(userPlayer1FigureSet[0]));
        Assert.assertFalse(figureManager.currPosOnFirstPathPos(comPlayer1FigureSet[0]));
        Assert.assertFalse(figureManager.currPosOnFirstPathPos(comPlayer2FigureSet[0]));
        Assert.assertFalse(figureManager.currPosOnFirstPathPos(comPlayer3FigureSet[0]));

        //Set on the first path position
        userPlayer1FigureSet[0].setPosition(new int[]{0, 4});
        comPlayer1FigureSet[0].setPosition(new int[]{4, 10});
        comPlayer2FigureSet[0].setPosition(new int[]{10, 6});
        comPlayer3FigureSet[0].setPosition(new int[]{6, 0});

        Assert.assertTrue(figureManager.currPosOnFirstPathPos(userPlayer1FigureSet[0]));
        Assert.assertTrue(figureManager.currPosOnFirstPathPos(comPlayer1FigureSet[0]));
        Assert.assertTrue(figureManager.currPosOnFirstPathPos(comPlayer2FigureSet[0]));
        Assert.assertTrue(figureManager.currPosOnFirstPathPos(comPlayer3FigureSet[0]));
    }

    /**
     * Check if the given AbstractPlayer has only Figures that are movable with a 6.
     */
    @Test
    public void testOnlyWith6Movable() {
        resetAllFiguresInBase();
        Assert.assertTrue(figureManager.onlyWith6Movable(userPlayer1));
        Assert.assertTrue(figureManager.onlyWith6Movable(comPlayer1));
        Assert.assertTrue(figureManager.onlyWith6Movable(comPlayer2));
        Assert.assertTrue(figureManager.onlyWith6Movable(comPlayer3));

        userPlayer1FigureSet[1].setPosition(new int[]{10, 6});
        comPlayer1FigureSet[0].setPosition(new int[]{9, 6});
        comPlayer2FigureSet[0].setPosition(new int[]{3, 4});
        comPlayer3FigureSet[0].setPosition(new int[]{6, 0});

        Assert.assertFalse(figureManager.onlyWith6Movable(userPlayer1));
        Assert.assertFalse(figureManager.onlyWith6Movable(comPlayer1));
        Assert.assertFalse(figureManager.onlyWith6Movable(comPlayer2));
        Assert.assertFalse(figureManager.onlyWith6Movable(comPlayer3));
    }
}
