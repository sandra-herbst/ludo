package org.example;

import Game.GameObjects.Figure;
import Game.Player.ComPlayer;
import org.junit.*;

public class ComPlayerTest {

    private static final ComPlayer comPlayer = new ComPlayer();

    /**
     * Check if Figure is movable.
     */
    @Test
    public void testSelectFigure() {
        Figure[] figureSet = comPlayer.getFigureSet();

        //trapped in base.
        Assert.assertNull(comPlayer.selectFigure(3));
        Assert.assertNull(comPlayer.selectFigure(2));
        Assert.assertNull(comPlayer.selectFigure(5));
        Assert.assertNull(comPlayer.selectFigure(4));
        Assert.assertNotNull(comPlayer.selectFigure(6));

        figureSet[0].setPosition(new int[]{4, 10});
        figureSet[1].setPosition(new int[]{3, 6});

        Assert.assertEquals(figureSet[0], comPlayer.selectFigure(2));
        Assert.assertEquals(figureSet[1], comPlayer.selectFigure(5));
    }
}
