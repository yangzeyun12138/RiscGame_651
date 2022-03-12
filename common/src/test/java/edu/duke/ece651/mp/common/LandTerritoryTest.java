package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LandTerritoryTest {
  @Test
  public void test_constructor() {
    String test = "Hello";
    String color = "Red";
    LandTerritory land = new LandTerritory(test, color);
    assertEquals(land.getName(), test);
    assertEquals(land.neighbors.size(), 0);
  }

  @Test
  public void test_equals(){
    String color = "Red";
    LandTerritory lt1 = new LandTerritory("hi", color);
    LandTerritory lt2 = new LandTerritory("hi", color);
    LandTerritory lt3 = new LandTerritory("hello", color);
    String lt4 = "GG";
    assertTrue(lt1.equals(lt2));
    assertFalse(lt2.equals(lt3));
    assertFalse(lt3.equals(lt4));
  }

  @Test
  public void test_getNeigh(){
    String color1 = "Red";
    String color2 = "Green";
    LandTerritory lt1 = new LandTerritory("territory1", color1);
    LandTerritory lt2 = new LandTerritory("territory2", color2);
    lt1.addNeigh(lt2);
    ArrayList<Territory> neighborList = lt1.getNeigh();
    for(Territory neighbor: neighborList){
      assertEquals("territory2", neighbor.getName());
      assertEquals("Green", neighbor.getColor());
    }
    
  }


  @Test
  public void test_units(){
    LandTerritory lt1 = new LandTerritory("territory1", "Red");
    LandTerritory lt2 = new LandTerritory("territory2", "Red");
    Unit u1 = new BasicUnit();
    Unit u2 = new BasicUnit();
    assertEquals(0,lt1.units.size());
    assertTrue(lt1.addUnit(u1));
    assertEquals(1,lt1.units.size());
    assertThrows(IllegalArgumentException.class, ()-> lt1.setBasicUnit(5));
    assertTrue(lt1.loseUnit());
    assertFalse(lt1.loseUnit());
    lt2.setBasicUnit(5);
    assertTrue(lt2.loseUnit());
    assertTrue(lt2.addBasicUnit(5));
    assertTrue(lt2.loseUnit(5));
  }

}
