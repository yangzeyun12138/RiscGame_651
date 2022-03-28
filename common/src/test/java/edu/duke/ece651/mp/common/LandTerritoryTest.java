package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LandTerritoryTest {
  @Test
  public void test_constructor() {
    String test = "Hello";
    String color = "Red";
    LandTerritory land = new LandTerritory(test, color, 1);
    assertEquals(land.getName(), test);
    assertEquals(land.neighbors.size(), 0);
    assertEquals(1, land.getSize());
  }

  @Test
  public void test_equals(){
    String color = "Red";
    LandTerritory lt1 = new LandTerritory("hi", color, 1);
    LandTerritory lt2 = new LandTerritory("hi", color, 1);
    LandTerritory lt3 = new LandTerritory("hello", color, 1);
    String lt4 = "GG";
    assertTrue(lt1.equals(lt2));
    assertFalse(lt2.equals(lt3));
    assertFalse(lt3.equals(lt4));
  }

  @Test
  public void test_getNeigh(){
    String color1 = "Red";
    String color2 = "Green";
    LandTerritory lt1 = new LandTerritory("territory1", color1, 1);
    LandTerritory lt2 = new LandTerritory("territory2", color2, 1);
    lt1.addNeigh(lt2);
    ArrayList<Territory> neighborList = lt1.getNeigh();
    for(Territory neighbor: neighborList){
      assertEquals("territory2", neighbor.getName());
      assertEquals("Green", neighbor.getColor());
    }
    
  }


  @Test
  public void test_units(){
    LandTerritory lt1 = new LandTerritory("territory1", "Red", 1);
    LandTerritory lt2 = new LandTerritory("territory2", "Red", 1);
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit();
    assertEquals(0,lt1.units.size());
    assertTrue(lt1.addUnit(u1));
    assertEquals(1,lt1.units.size());
    assertTrue(lt1.loseUnit());
    assertFalse(lt1.loseUnit());
    lt2.setBasicUnit(5);
    assertTrue(lt2.loseUnit());
    assertTrue(lt2.addBasicUnit(5));
    assertTrue(lt2.loseUnit(5));
  }

  @Test
  public void test_change_color() {
    LandTerritory lt1 = new LandTerritory("territory1", "Red", 1);
    lt1.changeColor("Blue");
    assertEquals("Blue", lt1.color);
  }

  @Test
  public void test_sort(){
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit();
    Unit u3 = new BasicUnit();
    u1.UpgradeBasicUnit(4);
    u2.UpgradeBasicUnit(2);
    u3.UpgradeBasicUnit(3);
    LandTerritory lt1 = new LandTerritory("territory1", "Red", 1);
    lt1.addUnit(u1);
    lt1.addUnit(u2);
    lt1.addUnit(u3);
    assertEquals(1, lt1.countLevelUnit(4));
    assertEquals(0, lt1.countLevelUnit(5));
    lt1.sortUnit();
    assertEquals(2, lt1.getLevelFromPosition(0));
    assertEquals(4, lt1.getLevelFromPosition(2));
  }

  @Test
  public void test_upgrade(){
    LandTerritory lt1 = new LandTerritory("territory1", "Red", 1);
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit();
    lt1.addUnit(u1);
    lt1.addUnit(u2);
    assertFalse(lt1.upgradeUnit(3,1,3));
    lt1.upgradeUnit(2, 1, 3);
    assertEquals(3, lt1.getLevelFromPosition(0));
  }

}
