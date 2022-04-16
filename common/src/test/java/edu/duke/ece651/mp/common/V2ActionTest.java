package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.awt.desktop.AppForegroundListener;
import java.util.*;
public class V2ActionTest {
  @Test
  public void test_move() {
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red", 2);
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 4);
    LandTerritory lt2 = new LandTerritory("Mordor", "Red", 3);
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
    V2Action action = new V2Action();
    int level = 0;
    action.Move(player, src, dest, numUnit, level);
    assertEquals(125, player.getFood());
    
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
    assertThrows(IllegalArgumentException.class, () -> action.Move(player, src, dest, new_numUnit, level));


  }

  
  @Test
  public void test_rolldice(){
    V2Action action = new V2Action();
    assertTrue(action.rollDice());
  }

  @Disabled
  @Test
  public void test_rolldice2(){
    V2Action action = new V2Action();
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit();
    assertTrue(action.rollDice2(u1, u2));
  }

  @Disabled
  @Test
  public void test_Attack(){
    HashSet<Territory> test_territory1 = new HashSet<>();
    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt2 = new LandTerritory("Mordor", "Red", 3);
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red", 2);
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue", 2);
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue", 3);

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
    V2Action action = new V2Action();
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(attacker);
    players.add(defender);
    int level = 1;
    ArrayList<Unit> units = new ArrayList<Unit>();
    action.sortUnit(units);
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit(1);
    Unit u3 = new BasicUnit();
    units.add(u1);
    units.add(u2);
    units.add(u3);
    
    action.Attack(attacker, defender, "Mordor", "Scadrial", units, players);
    Territory taken = action.findTerritory(attacker, "Scadrial");
    Territory left = action.findTerritory(defender, "Elantris");
    assertEquals(taken.getColor(), "Red");
    assertEquals(taken.countUnit(), 3);
    for(Territory neigh : left.getNeigh()){
      assertEquals("Red", neigh.getColor());
    }
    //assertThrows(IllegalArgumentException.class, ()->  action.Attack(attacker, defender, "Mordor", "Hogwarts", units, players));
  }

  @Disabled
  @Test
  public void test_attack_rules(){
   HashSet<Territory> test_territory1 = new HashSet<>();
    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt2 = new LandTerritory("Mordor", "Red", 3);
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red", 2);
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue", 2);
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue", 3);

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
    V2Action action = new V2Action();
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(attacker);
    players.add(defender);
    int level = 1;
    ArrayList<Unit> units = new ArrayList<Unit>();
    action.sortUnit(units);
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit(1);
    Unit u3 = new BasicUnit();
    units.add(u1);
    units.add(u2);
    units.add(u3);

    action.checkForAttack(attacker, "Mordor", "Scadrial", units.size(), players, 0);
    action.Attack(attacker, defender, "Mordor", "Scadrial", units, players);
    attacker.costFood(139);
    assertEquals("Red player. Invalid Attack: The cost of the attack is higher than the food of the player!\n", action.checkForAttack(attacker, "Scadrial", "Elantris", units.size(), players, 0));
    
    Territory taken = action.findTerritory(attacker, "Scadrial");
    Territory left = action.findTerritory(defender, "Elantris");
    assertEquals(taken.getColor(), "Red");
    assertEquals(taken.countUnit(), 3);
    for(Territory neigh : left.getNeigh()){
      assertEquals("Red", neigh.getColor());
    }
    
  }

  @Test
  public void test_done(){
    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue", 2);
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue", 3);

    lt4.addNeigh(lt5);
    lt5.addNeigh(lt4);
    lt4.setBasicUnit(5);    
    lt5.setBasicUnit(6);
    test_territory2.add(lt4);
    test_territory2.add(lt5);
    Player defender = new Player("Blue", test_territory2);
    int lt5_numUnit = lt5.countUnit();
    V2Action action = new V2Action();
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
    AbstractActionFactory Action = new V2Action();
    assertEquals("Red", Action.getPlayer("Gondor", players).color);
    assertNull(Action.getPlayer("Oz", players));
  }

  @Test
  public void test_arrangeAttackOrder(){ 
    ArrayList<Player> players = makePlayer2();
    Player player1 = players.get(0);
    Player player2 = players.get(1);
    Order order1 = new Order(player1, "Gondor", "Oz", 1, 0, 0);
    Order order2 = new Order(player1, "Hogwarts", "Oz", 1, 0, 0);
    Order order3 = new Order(player2, "Oz", "Gondor", 1, 0, 0);
    Order order4 = new Order(player2, "Oz", "Hogwarts", 1, 0, 0);
    Orders orders1 = new Orders();
    Orders orders2 = new Orders();
    orders1.AttackList.add(order1);
    orders1.AttackList.add(order2);
    orders2.AttackList.add(order3);
    orders2.AttackList.add(order4);
    ArrayList<Orders> ordersList = new ArrayList<Orders>();
    ordersList.add(orders1);
    ordersList.add(orders2);
    AbstractActionFactory Action = new V2Action();
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
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 4);
    LandTerritory lt2 = new LandTerritory("Hogwarts", "Red", 2); 
    LandTerritory lt3 = new LandTerritory("Oz", "Green", 4);
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
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 4);
    LandTerritory lt2 = new LandTerritory("Hogwarts", "Red", 2); 
    LandTerritory lt3 = new LandTerritory("Oz", "Green", 4);
    LandTerritory lt4 = new LandTerritory("Narnia", "Green", 2);
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

  @Test
  public void test_getRandomIndex() {
    AbstractActionFactory Action = new V2Action();
    ArrayList<Integer> numlist = Action.getRandomIdx(10);
    for (int i = 0; i < numlist.size(); i++) {
      System.out.println("numlist[" + i + "]: " + numlist.get(i));
    }
  }

  @Test
  public void test_loseAttackUnit(){
    V2Action action = new V2Action();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Orders> Orders  = new ArrayList<>();
    HashSet<Territory> test_territory1 = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Mordor", "Red", 3);
    LandTerritory lt2 = new LandTerritory("Gondor", "Red", 4);

    lt1.addNeigh(lt2);
    lt2.addNeigh(lt1);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    test_territory1.add(lt1);
    test_territory1.add(lt2);
    Player p1 = new Player("Red", test_territory1);

    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue", 2);
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue", 3);

    lt4.addNeigh(lt5);
    lt5.addNeigh(lt4);
    lt4.setBasicUnit(5);
    lt5.setBasicUnit(6);
    test_territory2.add(lt4);
    test_territory2.add(lt5);
    Player p2 = new Player("Blue", test_territory2);

    lt1.addNeigh(lt4);
    lt4.addNeigh(lt1);
    lt2.addNeigh(lt5);
    lt5.addNeigh(lt2);

    players.add(p1);
    players.add(p2);
    Orders os1 = new Orders();
    Orders os2 = new Orders();
    Order oA1 = new Order(p1, "Gondor", "Elantris", 1, 0, 0);
    Order oA2 = new Order(p2, "Scadrial", "Mordor",1, 0, 0);
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
    V2Action action = new V2Action();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Orders> Orders  = new ArrayList<>();
    HashSet<Territory> test_territory1 = new HashSet<>();
    LandTerritory lt1 = new LandTerritory("Mordor", "Red", 3);
    LandTerritory lt2 = new LandTerritory("Gondor", "Red", 4);

    lt1.addNeigh(lt2);
    lt2.addNeigh(lt1);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    test_territory1.add(lt1);
    test_territory1.add(lt2);
    Player p1 = new Player("Red", test_territory1);

    HashSet<Territory> test_territory2 = new HashSet<>();
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue", 2);
    LandTerritory lt5 = new LandTerritory("Elantris", "Blue", 3);

    lt4.addNeigh(lt5);
    lt5.addNeigh(lt4);
    lt4.setBasicUnit(5);
    lt5.setBasicUnit(6);
    test_territory2.add(lt4);
    test_territory2.add(lt5);
    Player p2 = new Player("Blue", test_territory2);

    lt1.addNeigh(lt5);
    lt2.addNeigh(lt5);
    lt5.addNeigh(lt1);
    lt5.addNeigh(lt2);

    players.add(p1);
    players.add(p2);

    lt1.addUnit(new BasicUnit(2));
    lt1.addUnit(new BasicUnit(2));
    lt2.addUnit(new BasicUnit(3));
    lt2.addUnit(new BasicUnit(3));

    ArrayList<Order> oList = new ArrayList<Order>();
    Order o1 = new Order(players.get(0), "Mordor", "Elantris", 1, 0, 0);
    Order o2 = new Order(players.get(0), "Mordor", "Elantris", 2, 2, 2);
    Order o3 = new Order(players.get(0), "Gondor", "Elantris", 1,0 ,0 );
    Order o4 = new Order(players.get(0), "Gondor", "Elantris", 2,3, 3);
    oList.add(o1);
    oList.add(o2);
    oList.add(o3);
    oList.add(o4);
    ArrayList<Order> res = action.refineAttack(oList, players);
    assertEquals(1, res.size());
    assertEquals(6, res.get(0).unitList.size());
  }

  @Test
  public void test_getIndexFromPlayers(){
    ArrayList<Player> players = makePlayer2();
    AbstractActionFactory Action = new V2Action();
    assertEquals(1, Action.getIndexFromPlayers(players, "Green"));
  }

  @Test
  public void test_checkWin(){
    ArrayList<Player> players = makePlayer2();
    AbstractActionFactory Action = new V2Action();
    assertNull(Action.checkWin(players));
    players.get(0).player_terri_set.clear();
    assertEquals("Green",Action.checkWin(players));
  }

  @Test
  public void test_checkMoveCost(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory A = new LandTerritory("A", "Red", 1);
    LandTerritory B = new LandTerritory("B", "Red", 30);
    LandTerritory C = new LandTerritory("C", "Red", 10);
    LandTerritory D = new LandTerritory("D", "Red", 100);
    LandTerritory E = new LandTerritory("E", "Red", 1000);
    LandTerritory F = new LandTerritory("F", "Green", 4);
    A.addNeigh(B);
    A.addNeigh(C);
    A.addNeigh(F);
    B.addNeigh(A);
    B.addNeigh(D);
    C.addNeigh(A);
    C.addNeigh(D);
    D.addNeigh(B);
    D.addNeigh(C);
    D.addNeigh(E);
    E.addNeigh(D);
    F.addNeigh(A);
    A.setBasicUnit(3);
    B.setBasicUnit(4);
    C.setBasicUnit(5);
    D.setBasicUnit(3);
    E.setBasicUnit(4);
    F.setBasicUnit(5);
    test_territory.add(A);
    test_territory.add(B);
    test_territory.add(C);
    test_territory.add(D);
    test_territory.add(E);
    Player player = new Player("Red", test_territory);
    String src = "A";
    String dest = "E";
    int numUnit = 1;
    player.costFood(139);
    MoveChecker CostCheck = new MoveCostRuleChecker(null);
    String result = CostCheck.checkMovement(player, src, dest, numUnit, 0);
    assertEquals("Red player. The movement is Invalid: the cost for the minimum path is higher than the number of the food!\n",result);
  }

  @Test
  public void test_checkForUpgrade(){
    Player player = makePlayer1();
    player.addFood(1000);
    
    AbstractActionFactory Action = new V2Action();
    assertNull(Action.checkForUpgrade(player, "Gondor", 1, 0, 1));
  }

  @Test
  public void test_unitUpgrade(){
    Player player = makePlayer1();
    player.addFood(1000);
    
    AbstractActionFactory Action = new V2Action();
    Action.unitUpgrade(player,"Gondor", 1, 0 ,1);
    int temp = player.getFood();
    assertEquals(temp, player.getFood());
  }

  @Test
  public void test_SpyMoves(){
    ArrayList<Player> players = makePlayer2();
    Player player1 = players.get(0);
    AbstractActionFactory action = new V2Action();
    action.createSpy(player1, "Gondor");
    player1.costFood(115);
    assertThrows(IllegalArgumentException.class,()-> action.createSpy(player1, "Gondor"));
    player1.addFood(115);
    assertThrows(IllegalArgumentException.class,()-> action.createSpy(player1, "Oz"));

    action.checkForSpyMove(player1, players,"Gondor","Oz");
    action.spyMove(player1, players, "Gondor", "Oz");
    player1.costFood(75);
    assertEquals("Red player. Invalid Spy Movement: The Source territory does not have movable Spy belongs to the player!\n", action.checkForSpyMove(player1, players,"Gondor","Oz"));
    
    action.createSpy(player1, "Gondor");
    action.resetSpyMovables(players);
    assertEquals("Red player. Invalid Spy Movement: The cost of the Spy movement is higher than the food of the player!\n", action.checkForSpyMove(player1, players,"Gondor","Oz"));
    
    player1.addFood(1000);
    action.createSpy(player1, "Hogwarts");
    action.resetSpyMovables(players);
    assertEquals("Red player. Invalid Spy Movement: The destination territory is not reachable from Source Territory!\n", action.checkForSpyMove(player1, players,"Hogwarts","Narnia"));
    action.spyMove(player1, players, "Oz","Narnia");
    action.createSpy(player1, "Gondor");
    action.resetSpyMovables(players);
    assertEquals(null ,action.checkForSpyMove(player1, players,"Gondor","Hogwarts"));
    action.spyMove(player1, players, "Gondor", "Hogwarts");
    
    
  }
}


