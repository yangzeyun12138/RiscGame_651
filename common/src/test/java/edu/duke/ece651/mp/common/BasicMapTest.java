package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class BasicMapTest {

      @Test
  public void test_constructor() {
        String testname = "test";
        String color = "Red";
        ArrayList<String> test_color = new ArrayList<String>();
        test_color.add("Red");
        test_color.add("Blue");
        HashSet<Territory> test_territory = new HashSet<>();
        LandTerritory lt1 = new LandTerritory("Hogwalts", "Red");
        test_territory.add(lt1);
        LandTerritory lt2 = new LandTerritory("Modor", "Blue");
        test_territory.add(lt2);
        BasicMap bm = new BasicMap(testname, test_color, test_territory);
        bm.assign_all();
        ArrayList<Player> player_list_get = bm.get_player_list();
        Player players_test = new Player(testname, test_territory);
        ArrayList<Player> player_list_test = player_list_get;
        player_list_test.add(players_test);
        assertTrue(player_list_test.equals(player_list_get));
        assertTrue(bm.TerritorySet.contains(lt1));
        assertTrue(bm.colorList.contains("Red"));
        assertEquals(2,bm.get_num_players());
  }
  @Test

      public void test_deep_copy() {
        String testname = "test";
        String color = "Red";
        ArrayList<String> test_color = new ArrayList<String>();
        test_color.add("Red");
        test_color.add("Blue");
        HashSet<Territory> test_territory = new HashSet<>();
        LandTerritory lt1 = new LandTerritory("Hogwalts", "Red");
        test_territory.add(lt1);
        LandTerritory lt2 = new LandTerritory("Modor", "Blue");
        test_territory.add(lt2);
        BasicMap bm = new BasicMap(testname, test_color, test_territory);
        BasicMap bm2 = bm.deep_copy();
        bm2.mapName = "change";
        assertNotEquals(bm.mapName, bm2.mapName);
  }


}
