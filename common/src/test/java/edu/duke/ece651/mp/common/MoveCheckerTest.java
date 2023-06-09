package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class MoveCheckerTest {
  @Test
  public void test_PathRuleChecker() {
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red", 2);
    LandTerritory lt2 = new LandTerritory("Modor", "Red", 3);
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 4);
    lt1.addNeigh(lt2);
    lt2.addNeigh(lt1);
    lt2.addNeigh(lt3);
    lt3.addNeigh(lt2);
    test_territory.add(lt1);
    test_territory.add(lt2);
    test_territory.add(lt3);
    Player player = new Player("Red", test_territory);
    String src = "Gondor";
    String dest = "Hogwarts";
    int numUnit = 2;
    MoveChecker MC = new PathRuleChecker(null);
    String result = MC.checkMyRule(player, src, dest, numUnit, 0);
    assertNull(result);

    //Test for Path!
    LandTerritory lt4 = new LandTerritory("Scadrial", "Blue", 2);
    dest = "Scadrial";
    result = MC.checkMyRule(player, src, dest, numUnit, 0);
    assertEquals("Red player. The movement is Invalid: the Path between src and dest Territory cannot be found\n", result );

    //Test for Color!
    lt2.addNeigh(lt4);
    lt3.addNeigh(lt4);
    lt4.addNeigh(lt2);
    lt4.addNeigh(lt3);
    dest = "Hogwarts";
    result = MC.checkMyRule(player, src, dest, numUnit, 0);
    assertNull(result);
    
  }

  @Test
  public void test_Unit(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red", 2);
    LandTerritory lt2 = new LandTerritory("Modor", "Red", 3);
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 4);
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
    MoveChecker MC = new UnitRuleChecker(null);
    String result = MC.checkMyRule(player, src, dest, numUnit, 0);
    assertNull(result);

    // Over
    numUnit = 4;
    result = MC.checkMyRule(player, src, dest, numUnit, 0);
    assertEquals("Red player. The movement is Invalid: the number of unit within the territory is less than the number you want to move!\n", result);
    // Smaller than 0
    numUnit = -1;
    result = MC.checkMyRule(player, src, dest, numUnit, 0);
    assertEquals("Red player. The movement is Invalid: the Unit to move cannot be negative!\n", result);
  }

  @Test
  public void test_NameMoveRuleChecker(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red", 2);
    LandTerritory lt2 = new LandTerritory("Modor", "Red", 3);
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 4);
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
    MoveChecker MC = new NameMoveRuleChecker(null);
    assertEquals("Red player. The movement is Invalid: The name of the source territory or the destination territory is invalid\n", MC.checkMyRule(player, "ddd", dest, numUnit, 0));

  }


  @Test
  public void test_AllRules(){
    HashSet<Territory> test_territory = new HashSet<>();
    LandTerritory lt3 = new LandTerritory("Hogwarts", "Red", 2);
    LandTerritory lt2 = new LandTerritory("Modor", "Red", 3);
    LandTerritory lt1 = new LandTerritory("Gondor", "Red", 4);
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
    MoveChecker UnitCheck = new UnitRuleChecker(null);
    MoveChecker PathCheck = new PathRuleChecker(UnitCheck);
    String result = PathCheck.checkMovement(player, src, dest, numUnit, 0);
    assertNull(result);

    numUnit = 4;
    result = PathCheck.checkMovement(player, src, dest, numUnit, 0);
    assertEquals("Red player. The movement is Invalid: the number of unit within the territory is less than the number you want to move!\n", result); 

  }

  @Test
  public void test_move_cost_checker(){
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
    MoveCostRuleChecker CostCheck = new MoveCostRuleChecker(null);
    //String result = CostCheck.checkMovement(player, src, dest, numUnit);o
    int min_cost = CostCheck.findMinPath(player, src, dest);
    assertEquals(1110 ,min_cost);

    src = "B";
    dest = "D";
    min_cost = CostCheck.findMinPath(player, src, dest);
    assertEquals(100, min_cost);

    src = "A";
    dest = "D";
    min_cost = CostCheck.findMinPath(player, src, dest);
    assertEquals(110, min_cost);
  }
}
