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

  @Test
  public void test_rolldice(){
    V1Action action = new V1Action();
    assertTrue(action.rollDice());
  }

  @Test
  public void test_Attack(){
    HashSet<Territory> test_territory1 = new HashSet<>();
    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt2 = new LandTerritory("Mordor", "Red");
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red");
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue");
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue");

    lt2.addNeigh(lt3);
    lt2.addNeigh(lt4);
    lt3.addNeigh(lt2);
    lt3.addNeigh(lt4);
    lt4.addNeigh(lt2);
    lt4.addNeigh(lt3);
    lt4.addNeigh(lt5);
    lt5.addNeigh(lt4);
    lt2.setBasicUnit(4);
    lt3.setBasicUnit(5);
    lt4.setBasicUnit(5);    
    lt5.setBasicUnit(6);
    test_territory1.add(lt2);
    test_territory1.add(lt3);
    test_territory2.add(lt4);
    test_territory2.add(lt5);

    Player attacker = new Player("Red", test_territory1);
    Player defender = new Player("Blue", test_territory2);
    V1Action action = new V1Action();
    action.Attack(attacker, defender, "Mordor", "Scadrial", 1);
    Territory taken = action.findTerritory(attacker, "Scadrial");
    assertEquals(taken.getColor(), "Red");
    assertEquals(taken.countUnit(), 1);
   assertThrows(IllegalArgumentException.class, ()->  action.Attack(attacker, defender, "Mordor", "Hogwarts", 1));
  }

  @Test
  public void test_done(){
    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue");
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue");

    lt4.addNeigh(lt5);
    lt5.addNeigh(lt4);
    lt4.setBasicUnit(5);    
    lt5.setBasicUnit(6);
    test_territory2.add(lt4);
    test_territory2.add(lt5);
    Player defender = new Player("Blue", test_territory2);
    int lt5_numUnit = lt5.countUnit();
    V1Action action = new V1Action();
    ArrayList<Player> arrayList = new ArrayList<Player>();
    arrayList.add(defender);
    action.Done(arrayList);
    assertEquals(lt5_numUnit + 1, lt5.countUnit());
  }

  @Test
  public void test_getPlayer() {
    Player player = makePlayer1();
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(player);
    AbstractActionFactory Action = new V1Action();
    assertEquals("Red", Action.getPlayer("Gondor", players).color);
    assertNull(Action.getPlayer("Oz", players));
  }

  @Test
  public void test_arrangeAttackOrder(){ 
    ArrayList<Player> players = makePlayer2();
    Player player1 = players.get(0);
    Player player2 = players.get(1);
    Order order1 = new Order(player1, "Gondor", "Oz", 1);
    Order order2 = new Order(player1, "Hogwarts", "Oz", 1);
    Order order3 = new Order(player2, "Oz", "Gondor", 1);
    Order order4 = new Order(player2, "Oz", "Hogwarts", 1);
    Orders orders1 = new Orders();
    Orders orders2 = new Orders();
    orders1.AttackList.add(order1);
    orders1.AttackList.add(order2);
    orders2.AttackList.add(order3);
    orders2.AttackList.add(order4);
    ArrayList<Orders> ordersList = new ArrayList<Orders>();
    ordersList.add(orders1);
    ordersList.add(orders2);
    AbstractActionFactory Action = new V1Action();
    HashMap<String, ArrayList<Order>> AttackMap = Action.arrangeAttackOrder(ordersList, players);
    ArrayList<Order> Oz_dest = AttackMap.get("Oz");
    assertEquals(order1.getSrc(), Oz_dest.get(0).getSrc());
    assertEquals(order2.getSrc(), Oz_dest.get(1).getSrc());
    ArrayList<Order> Gondor_dest = AttackMap.get("Gondor");
    assertEquals(order3.getSrc(), Gondor_dest.get(0).getSrc());
    ArrayList<Order> Hogwarts_dest = AttackMap.get("Hogwarts");
    assertEquals(order4.getSrc(), Hogwarts_dest.get(0).getSrc());
    
  }

  public Player makePlayer1(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Gondor", "Red");
    LandTerritory lt2 = new LandTerritory("Hogwarts", "Red"); 
    LandTerritory lt3 = new LandTerritory("Oz", "Green");
    lt1.addNeigh(lt2);
    lt1.addNeigh(lt3);
    lt2.addNeigh(lt1);
    lt3.addNeigh(lt1);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    test_territory.add(lt1);
    test_territory.add(lt2);
    Player player = new Player("Red", test_territory);
    return player;
  }

  public ArrayList<Player> makePlayer2(){
    HashSet<Territory> test_territory1 = new HashSet<>();
    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Gondor", "Red");
    LandTerritory lt2 = new LandTerritory("Hogwarts", "Red"); 
    LandTerritory lt3 = new LandTerritory("Oz", "Green");
    LandTerritory lt4 = new LandTerritory("Narnia", "Green");
    lt1.addNeigh(lt2);
    lt1.addNeigh(lt3);
    lt2.addNeigh(lt1);
    lt2.addNeigh(lt3);
    lt3.addNeigh(lt1);
    lt3.addNeigh(lt2);
    lt3.addNeigh(lt4);
    lt4.addNeigh(lt3);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    lt3.setBasicUnit(3);
    lt4.setBasicUnit(4);
    test_territory1.add(lt1);
    test_territory1.add(lt2);
    test_territory2.add(lt3);
    test_territory2.add(lt4);
    Player player1 = new Player("Red", test_territory1);
    Player player2 = new Player("Green", test_territory2);
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(player1);
    players.add(player2);
    return players;
  }
}
