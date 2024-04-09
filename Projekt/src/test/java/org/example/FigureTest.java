package org.example;

import Game.GameObjects.Figure;
import org.junit.*;

/**
 * Unit test of {@link Figure}.
 */
public class FigureTest {
    private static Figure figure1;
    private static Figure figure2;
    private static Figure figure3;
    private static Figure figure4;

    /**
     * Create a new Figure instance.
     */
    public FigureTest() {
        figure1 = new Figure("red", 1);
        figure2 = new Figure("blue", 2);
        figure3 = new Figure("yellow", 3);
        figure4 = new Figure("green", 0);
    }

    /**
     * Should return the right FigureID.
     */
    @Test
    public void testGetFigureID() {
        Assert.assertEquals(1, figure1.getFIGUREID());
        Assert.assertEquals(2, figure2.getFIGUREID());
        Assert.assertEquals(3, figure3.getFIGUREID());
        Assert.assertEquals(0, figure4.getFIGUREID());

        Assert.assertNotEquals(0, figure1.getFIGUREID());
        Assert.assertNotEquals(3, figure2.getFIGUREID());
        Assert.assertNotEquals(1, figure3.getFIGUREID());
        Assert.assertNotEquals(2, figure4.getFIGUREID());
    }

    /**
     * Should return the right Color.
     */
    @Test
    public void testGetColor() {
        Assert.assertEquals("red", figure1.getColor());
        Assert.assertEquals("blue", figure2.getColor());
        Assert.assertEquals("yellow", figure3.getColor());
        Assert.assertEquals("green", figure4.getColor());

        Assert.assertNotEquals("lila", figure1.getColor());
        Assert.assertNotEquals("pink", figure2.getColor());
        Assert.assertNotEquals("brown", figure3.getColor());
        Assert.assertNotEquals("grey", figure4.getColor());
    }

    /**
     * Should return the right Position.
     */
    @Test
    public void testGetPosition() {
        figure1.setPosition(new int[]{0,9});
        Assert.assertEquals(0,figure1.getPosition()[0]);
        Assert.assertEquals(9,figure1.getPosition()[1]);

        figure2.setPosition(new int[]{6,5});
        Assert.assertEquals(6,figure2.getPosition()[0]);
        Assert.assertEquals(5,figure2.getPosition()[1]);

        figure3.setPosition(new int[]{8,2});
        Assert.assertEquals(8,figure3.getPosition()[0]);
        Assert.assertEquals(2,figure3.getPosition()[1]);

        figure4.setPosition(new int[]{3,9});
        Assert.assertEquals(3,figure4.getPosition()[0]);
        Assert.assertEquals(9,figure4.getPosition()[1]);
    }
}
