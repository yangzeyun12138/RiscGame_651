package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
public class OrdersTest {
  @Test
  public void test_orders() {
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 4);
    LandTerritory lt2 = new LandTerritory("Hogwarts", "Red", 2);
    lt1.addNeigh(lt2);
    lt2.addNeigh(lt1);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    test_territory.add(lt1);
    test_territory.add(lt2);
    Player player = new Player("Red", test_territory);
   
    String src1 = "Gondor";
    String dest1 = "Hogwarts";
    int numUnit1 = 2;
    Order order1 = new Order(player, src1, dest1, numUnit1);
    
    String src2 = "Gondor";
    String dest2 = "Oz";
    int numUnit2 = 3;
    Order order2 = new Order(player, src2, dest2, numUnit2);

   
    Orders orders = new Orders();
    orders.MoveList.add(order1);
    orders.AttackList.add(order2);
    assertEquals(1, orders.MoveList.size());
    assertEquals(1, orders.AttackList.size());
  }
  

}
