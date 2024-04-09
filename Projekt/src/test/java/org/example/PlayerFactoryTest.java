package org.example;

import Exceptions.IllegalFactoryArgument;
import Game.Player.AbstractPlayer;
import Game.Player.PlayerFactory;
import org.junit.Test;

public class PlayerFactoryTest {

    @Test(expected = IllegalFactoryArgument.class)
    public void getPlayerArrayNegTest() throws IllegalFactoryArgument {
        PlayerFactory factory = new PlayerFactory();
        AbstractPlayer[] playerArray = factory.getPlayerArray(4,"username","red");
    }
}
