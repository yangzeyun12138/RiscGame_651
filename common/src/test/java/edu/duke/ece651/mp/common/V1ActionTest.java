package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;
public class V1ActionTest {
  @Test
  public void test_move() {
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red");
    LandTerritory lt2 = new LandTerritory("Mordor", "Red");
    LandTerritory lt1 = new LandTerritory("Gondor", "Red");
    lt1.addNeigh(lt2);
    lt2.addNeigh(lt1);
    lt2.addNeigh(lt3);
    lt3.addNeigh(lt2);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    lt3.setBasicUnit(5);
    test_territory.add(lt1);
    test_territory.add(lt2);
    test_territory.add(lt3);
    Player player = new Player("Red", test_territory);
    String src = "Gondor";
    String dest = "Hogwarts";
    int numUnit = 3;
    V1Action action = new V1Action();
    action.Move(player, src, dest, numUnit);
    for(Territory t : player.player_terri_set){
      if(t.getName().equals("Gondor")){
        assertEquals(0, t.countUnit());
      }
      if(t.getName().equals("Mordor")){
        assertEquals(4, t.countUnit());
      }
      if(t.getName().equals("Hogwarts")){
        assertEquals(8, t.countUnit());
      }
      
    }
    int new_numUnit = 100;
    assertThrows(IllegalArgumentException.class, () -> action.Move(player, src, dest, new_numUnit));


  }

}
