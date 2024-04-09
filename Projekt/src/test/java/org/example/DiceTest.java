package org.example;

import Game.GameObjects.Dice;
import org.junit.*;

/**
 * Unit test of {@link Dice}.
 */
public class DiceTest {

    private static Dice dice;

    @BeforeClass
    public static void setDiceInstance(){
        dice = new Dice();
    }

    public boolean diceRange(int numberRolled){
        return numberRolled >= 0 && numberRolled <= 6;
    }

    public boolean isSix(int numberRolled){
        return numberRolled == 6;
    }

    @Test
    public void testRollRange(){
        for (int i = 0; i < 5; i++){
            dice.roll();
            Assert.assertTrue( diceRange(dice.getNumber()));
            System.out.println("Number rolled: " + dice.getNumber() + ", is 6: " + dice.getRollAgain() + " , is in range (1-6): " + diceRange(dice.getNumber()));
        }
    }

    @Test
    public void testRollAgain(){
        for (int i = 0; i < 5; i++){
            dice.roll();
            Assert.assertEquals(isSix(dice.getNumber()), dice.getRollAgain());
            System.out.println("Number rolled: " + dice.getNumber() + ", is 6: " + dice.getRollAgain());
        }
    }

    //@BeforeClass
    //@Before
    //@After

    //Assert.assertEquals( Welcher Wert wird erwartet , Objekt mit Methode, der Wert übergibt)
}