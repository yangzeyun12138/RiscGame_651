package edu.duke.ece651.mp.common;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapDisplayTest {
    @Test
    public void test_display() {
        String testname = "test";
        ArrayList<String> test_color = new ArrayList<String>();
        test_color.add("Red");
        test_color.add("Blue");
        HashSet<Territory> test_territory = new HashSet<>();
        LandTerritory lt1 = new LandTerritory("Hogwalts", "Red", 2);
        test_territory.add(lt1);
        LandTerritory lt2 = new LandTerritory("Modor", "Blue", 3);
        test_territory.add(lt2);
        lt1.getNeigh().add(lt2);
        lt2.getNeigh().add(lt1);
        BasicMap bm = new BasicMap(testname, test_color, test_territory);
        BasicMapDisplay bmd = new BasicMapDisplay(bm);
        bm.assign_all();
        String test_string = new String("Red player:\n" +
                "-----------\n" + "  0 units in Hogwalts (next to: Modor)\n\n");
        test_string += "Blue player:\n" +
                "-----------\n" + "  0 units in Modor (next to: Hogwalts)\n\n";
        assertEquals(test_string, bmd.display());
    }

    @Test
    public void test_parse_display() throws IOException {
        String filename = "Territory3.txt";
        int numPlayer = 3;
        Parse parse = new Parse();
        HashSet<Territory> result = parse.parseTerritoryNeighbor(filename, numPlayer);
        BasicMap bm = new BasicMap("Map 1", parse.parseColor("Color.txt", numPlayer), result);
        bm.assign_all();
        BasicMapDisplay bmd = new BasicMapDisplay(bm);
        String res = bmd.display();
        String expected = new String("Red player:\n" +
                "-----------\n" +
                "  10 units in Gondor (next to: Mordor, Oz)\n" +
                "  10 units in Mordor (next to: Gondor, Hogwarts, Oz, Scadrial)\n" +
                "  10 units in Hogwarts (next to: Mordor, Scadrial, Roshar)\n" +
                "\n" +
                "Green player:\n" +
                "-----------\n" +
                "  10 units in Oz (next to: Gondor, Mordor, Midkemia, Scadrial)\n" +
                "  10 units in Narnia (next to: Elantris, Midkemia)\n" +
                "  10 units in Midkemia (next to: Elantris, Scadrial, Oz, Narnia)\n" +
                "\n" +
                "Blue player:\n" +
                "-----------\n" +
                "  10 units in Scadrial (next to: Elantris, Hogwarts, Mordor, Midkemia, Oz, Roshar)\n" +
                "  10 units in Roshar (next to: Elantris, Hogwarts, Scadrial)\n" +
                "  10 units in Elantris (next to: Midkemia, Narnia, Roshar, Scadrial)\n" +
                "\n");
        //assertEquals(expected, res);
    }
}
