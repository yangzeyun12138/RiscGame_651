
package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import java.util.*;

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
  /*
  @Test
  public void test_player_constructor(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Hogwarts", "Red", 2);
    LandTerritory lt2 = new LandTerritory("Mordor", "Red", 3);
    LandTerritory lt3 = new LandTerritory("Scadrial", "Blue", 4);
    LandTerritory lt4 = new LandTerritory("Narnia", "Blue", 5);
    lt1.getNeigh().add(lt2);
    lt1.getNeigh().add(lt3);
    lt2.getNeigh().add(lt1);
    lt2.getNeigh().add(lt3);
    lt3.getNeigh().add(lt1);
    lt3.getNeigh().add(lt2);
    lt3.getNeigh().add(lt4);
    lt4.getNeigh().add(lt3);
    test_territory.add(lt1);
    test_territory.add(lt2);
    Player p = new Player("Red", test_territory);
    p.initViewMap();
    ArrayList<Territory> viewed_list = p.old_view_map.get("viewed");
    boolean mordorCheck = false;
    boolean hogwartsCheck = false;
    boolean scadrialCheck = false;
    for(Territory t : viewed_list){
      if(t.getName().equals("Mordor")){
        mordorCheck = true;
      }
      if(t.getName().equals("Hogwarts")){
        hogwartsCheck = true;
      }
      if(t.getName().equals("Scadrial")){
        scadrialCheck = true;
      }
    }
    assertTrue(mordorCheck);
    assertTrue(hogwartsCheck);
    assertTrue(scadrialCheck);   
  }

   */

  /*
  @Test
  public void test_updateNewViewMap(){
    // first round
    // R: Gondor, Mordor, Hogwarts.
    // G: Oz.
    // B: Scadrial.
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 2);
    LandTerritory lt2 = new LandTerritory("Mordor", "Red", 3);
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Blue", 4);
    LandTerritory lt4 = new LandTerritory("Oz", "Red", 4);
    LandTerritory lt5 = new LandTerritory("Scadrial", "Red", 5);
    LandTerritory lt6 = new LandTerritory("Midkemia", "Green", 3);
    lt1.getNeigh().add(lt2);
    lt1.getNeigh().add(lt4);
    lt2.getNeigh().add(lt1);
    lt2.getNeigh().add(lt3);
    lt2.getNeigh().add(lt4);
    lt2.getNeigh().add(lt5);
    lt3.getNeigh().add(lt2);
    lt3.getNeigh().add(lt5);
    lt4.getNeigh().add(lt1);
    lt4.getNeigh().add(lt2);
    lt4.getNeigh().add(lt5);
    lt4.getNeigh().add(lt6);
    lt5.getNeigh().add(lt2);
    lt5.getNeigh().add(lt3);
    lt5.getNeigh().add(lt4);
    lt6.getNeigh().add(lt4);
    test_territory.add(lt1);
    test_territory.add(lt2);
    test_territory.add(lt4);
    test_territory.add(lt5);
    Player p = new Player("Red", test_territory);
    p.initViewMap();
    // second round
    // R: Gondor, Mordor, Hogwarts.
    // G: Midkemia, Oz.
    // B: Scadrial.
    lt3.changeColor("Red");
    lt4.changeColor("Green");
    lt5.changeColor("Blue");
    HashSet<Territory> test_territory2 = new HashSet<>();
    test_territory2.add(lt1);
    test_territory2.add(lt2);
    test_territory2.add(lt3);
    p.player_terri_set = test_territory2;
    p.updateNewViewMap();
    ArrayList<Territory> new_viewed_list = p.new_view_map.get("viewed");
    ArrayList<Territory> new_grey_list = p.new_view_map.get("grey");
    ArrayList<Territory> old_viewed_list = p.old_view_map.get("viewed");
    ArrayList<Territory> old_grey_list = p.old_view_map.get("grey");
    
    assertEquals(6, old_viewed_list.size());
    assertEquals(0, old_grey_list.size());
    assertEquals(1, new_grey_list.size());
    assertEquals(5, new_viewed_list.size());
    
    boolean gondorCheck = false;
    boolean mordorCheck = false;
    boolean hogwartsCheck = false;
    boolean ozCheck = false;
    boolean scadrialCheck = false;
    boolean midkemiaCheck = false;
    for(Territory t : new_viewed_list){
      if(t.getName().equals("Gondor")){
        gondorCheck = true;
      }
      if(t.getName().equals("Mordor")){
        mordorCheck = true;
      }
      if(t.getName().equals("Hogwarts")){
        hogwartsCheck = true;
      }
      if(t.getName().equals("Oz")){
        ozCheck = true;
      }
      if(t.getName().equals("Scadrial")){
        scadrialCheck = true;
      }
      if(t.getName().equals("Midkemia")){
        midkemiaCheck = true;
      }
    }
    assertTrue(gondorCheck);
    assertTrue(mordorCheck);
    assertTrue(hogwartsCheck);
    assertTrue(ozCheck);
    assertTrue(scadrialCheck);
    assertFalse(midkemiaCheck);

    midkemiaCheck = false;
    for(Territory t : new_grey_list){
      if(t.getName().equals("Midkemia")){
        midkemiaCheck = true;
      }
    }

    assertTrue(midkemiaCheck);

    // third round
    // R: Hogwarts
    // G: Gondor, Oz, Midkemia
    // B: Mordor, Scadrial
    lt1.changeColor("Green");
    lt2.changeColor("Blue");
    HashSet<Territory> test_territory3 = new HashSet<>();
    test_territory3.add(lt3);
    p.player_terri_set = test_territory3;
    p.updateNewViewMap();
    new_viewed_list = p.new_view_map.get("viewed");
    new_grey_list = p.new_view_map.get("grey");
    old_viewed_list = p.old_view_map.get("viewed");
    old_grey_list = p.old_view_map.get("grey");
    
    assertEquals(5, old_viewed_list.size());
    assertEquals(1, old_grey_list.size());
    assertEquals(3, new_grey_list.size());
    assertEquals(3, new_viewed_list.size());
    
    gondorCheck = false;
    mordorCheck = false;
    hogwartsCheck = false;
    ozCheck = false;
    scadrialCheck = false;
    midkemiaCheck = false;
    for(Territory t : new_viewed_list){
      if(t.getName().equals("Mordor")){
        mordorCheck = true;
      }
      if(t.getName().equals("Hogwarts")){
        hogwartsCheck = true;
      }
      if(t.getName().equals("Scadrial")){
        scadrialCheck = true;
      }
    }
    assertFalse(gondorCheck);
    assertTrue(mordorCheck);
    assertTrue(hogwartsCheck);
    assertFalse(ozCheck);
    assertTrue(scadrialCheck);
    assertFalse(midkemiaCheck);

    for(Territory t : new_grey_list){
      if(t.getName().equals("Gondor")){
        gondorCheck = true;
      }
      if(t.getName().equals("Oz")){
        ozCheck = true;
      }
      if(t.getName().equals("Midkemia")){
        midkemiaCheck = true;
      }
    }
    assertTrue(gondorCheck);
    assertTrue(ozCheck);
    assertTrue(midkemiaCheck);
    
  }

   */
}
