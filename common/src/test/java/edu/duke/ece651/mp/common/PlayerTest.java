package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    @Test
    public void test_toString(){
        HashSet<Territory> test_territory = new HashSet<>();
        LandTerritory lt1 = new LandTerritory("Hogwalts", "Red");
        LandTerritory lt2 = new LandTerritory("Modor", "Blue");
        lt1.getNeigh().add(lt2);
        lt2.getNeigh().add(lt1);
        test_territory.add(lt1);
        Player p = new Player("Red", test_territory);
        String test_string = new String("Red player:\n" +
                "-----------\n" + "  10 units in Hogwalts (next to: Modor)\n\n");
        assertEquals(test_string, p.toString());
    }


}