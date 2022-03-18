package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

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
                "-----------\n" + "  0 units in Hogwalts (next to: Modor)\n\n");
        assertEquals(test_string, p.toString());
    }
  
  @Test
  public void test_checkLose(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Hogwalts", "Red");
    LandTerritory lt2 = new LandTerritory("Modor", "Blue");
    lt1.getNeigh().add(lt2);
    lt2.getNeigh().add(lt1);
    test_territory.add(lt1);
    Player p = new Player("Red", test_territory);
    assertFalse(p.checkLose());
    test_territory.clear();
    assertTrue(p.checkLose());
  }

  @Test
  public void test_deep_copy(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Hogwalts", "Red");
    LandTerritory lt2 = new LandTerritory("Modor", "Blue");
    lt1.getNeigh().add(lt2);
    lt2.getNeigh().add(lt1);
    test_territory.add(lt1);
    Player p = new Player("Red", test_territory);
    Player new_p = p.deep_copy();
    p.color = "Black";
    System.out.println("Previous Mordor: " + lt2);
    for(Territory t : new_p.player_terri_set){
      if(t.getName().equals("Hogwalts")){
        System.out.println("Later Mordor: " + t.getNeigh().get(0));
      }
    }
    
    assertNotEquals(p.color, new_p.color);
  }

}
