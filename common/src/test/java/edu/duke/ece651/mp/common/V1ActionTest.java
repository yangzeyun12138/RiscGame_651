package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import java.awt.desktop.AppForegroundListener;
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
  public void test_loseAttackUnit(){
    V1Action action = new V1Action();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Orders> Orders  = new ArrayList<>();
    HashSet<Territory> test_territory1 = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Mordor", "Red");
    LandTerritory lt2 = new LandTerritory("Gondor", "Red");

    lt1.addNeigh(lt2);
    lt2.addNeigh(lt1);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    test_territory1.add(lt1);
    test_territory1.add(lt2);
    Player p1 = new Player("Red", test_territory1);

    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue");
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue");

    lt4.addNeigh(lt5);
    lt5.addNeigh(lt4);
    lt4.setBasicUnit(5);
    lt5.setBasicUnit(6);
    test_territory2.add(lt4);
    test_territory2.add(lt5);
    Player p2 = new Player("Blue", test_territory2);

    players.add(p1);
    players.add(p2);


    Orders os1 = new Orders();
    Orders os2 = new Orders();
    Order oA1 = new Order(p1, "Gondor", "Elantris", 1);
    Order oA2 = new Order(p2, "Scadrial", "Mordor",1);
    os1.AttackList.add(oA1);
    os2.AttackList.add(oA2);
    Orders.add(os1);
    Orders.add(os2);
    action.loseAttackUnit(Orders, players);
    int lt1unit = lt1.countUnit();
    int lt4unit = lt4.countUnit();
    assertEquals(3, lt1unit);
    assertEquals(4, lt4unit);
  }

  @Test
  public void test_refine() {
    V1Action action = new V1Action();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Orders> Orders  = new ArrayList<>();
    HashSet<Territory> test_territory1 = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Mordor", "Red");
    LandTerritory lt2 = new LandTerritory("Gondor", "Red");

    lt1.addNeigh(lt2);
    lt2.addNeigh(lt1);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    test_territory1.add(lt1);
    test_territory1.add(lt2);
    Player p1 = new Player("Red", test_territory1);

    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue");
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue");

    lt4.addNeigh(lt5);
    lt5.addNeigh(lt4);
    lt4.setBasicUnit(5);
    lt5.setBasicUnit(6);
    test_territory2.add(lt4);
    test_territory2.add(lt5);
    Player p2 = new Player("Blue", test_territory2);

    players.add(p1);
    players.add(p2);
    ArrayList<Order> oList = new ArrayList<Order>();
    Order o1 = new Order(players.get(0), "Mordor", "Elantris", 1);
    Order o2 = new Order(players.get(1), "Scadrial", "Elantris", 1);
    Order o3 = new Order(players.get(0), "Gondor", "Elantris", 2);
    Order o4 = new Order(players.get(1), "Gondor", "Elantris", 1);
    oList.add(o1);
    oList.add(o2);
    oList.add(o3);
    oList.add(o4);
    ArrayList<Order> res = action.refineAttack(oList, players);
    assertEquals(2, res.size());
    assertEquals(3, res.get(0).numUnit);
    assertEquals(2, res.get(1).numUnit);
  }
}
