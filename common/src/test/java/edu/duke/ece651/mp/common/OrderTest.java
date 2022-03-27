package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
public class OrderTest {
  @Test
  public void test_Order_getSrc_getDest_getNumUnit() {
    String src = "Gondor";
    String dest = "Oz";
    int numUnit = 3;
    Player player = makePlayer();
    
    Order order = new Order(player, src, dest, numUnit, 0, 0);
    assertEquals("Gondor", order.getSrc());
    assertEquals("Oz", order.getDest());
    assertEquals(3, order.getNumUnit());
    assertEquals("Red", order.getPlayer().color);
  }

  public Player makePlayer(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Gondor", "Red");
    LandTerritory lt2 = new LandTerritory("Hogwarts", "Red");
    lt1.addNeigh(lt2);
    lt2.addNeigh(lt1);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    test_territory.add(lt1);
    test_territory.add(lt2);
    Player player = new Player("Red", test_territory);
    return player;
  }
}
