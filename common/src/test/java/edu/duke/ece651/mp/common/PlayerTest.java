package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    public void test_toString(){
        HashSet<Territory> test_territory = new HashSet<>();
        LandTerritory lt1 = new LandTerritory("Hogwalts", "Red", 2);
        LandTerritory lt2 = new LandTerritory("Modor", "Blue", 3);
        lt1.getNeigh().add(lt2);
        lt2.getNeigh().add(lt1);
        test_territory.add(lt1);
        Player p = new Player("Red", test_territory);
        /*String test_string = new String("Red player:\n" +
          "-----------\n" + "  0 units in Hogwalts (next to: Modor)\n\n");*/
        String test_string = new String("Red player       Total tech level: 1       Resources: 140");
        assertEquals(test_string, p.toString().split("\n")[0]);
    }
  
  @Test
  public void test_checkLose(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Hogwalts", "Red",2);
    LandTerritory lt2 = new LandTerritory("Modor", "Blue",3);
    lt1.getNeigh().add(lt2);
    lt2.getNeigh().add(lt1);
    test_territory.add(lt1);
    Player p = new Player("Red", test_territory);
    assertFalse(p.checkLose());
    test_territory.clear();
    assertTrue(p.checkLose());
  }

  @Test
  public void test_deep_copy_getTerritory(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Hogwalts", "Red", 2);
    LandTerritory lt2 = new LandTerritory("Modor", "Blue", 3);
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
    
    assertEquals(lt1.getName(), p.getTerritory("Hogwalts").getName());
    assertNull(p.getTerritory("Oz"));
    assertNotEquals(p.color, new_p.color);
  }

  @Test
  public void test_getTechLevel(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Hogwalts", "Red", 2);
    LandTerritory lt2 = new LandTerritory("Modor", "Blue", 3);
    lt1.getNeigh().add(lt2);
    lt2.getNeigh().add(lt1);
    test_territory.add(lt1);
    Player p = new Player("Red", test_territory);
    assertEquals(1, p.getTechLevel());
    assertEquals(140, p.getFood());
    p.upgradeTechLevel();
    assertEquals(2, p.getTechLevel());
    assertThrows(IllegalArgumentException.class, ()->p.costFood(-1000));
    p.costFood(30);
    assertEquals(60, p.getFood());
    assertThrows(IllegalArgumentException.class, ()->p.addFood(-1000));
    p.addFood(5);
    assertEquals(65, p.getFood());

    p.costFood(1000);
    assertThrows(IllegalArgumentException.class, ()->p.upgradeTechLevel());
    
  }
  
}
