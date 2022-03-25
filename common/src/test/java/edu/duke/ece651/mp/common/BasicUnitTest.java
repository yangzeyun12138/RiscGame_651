package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicUnitTest {
  @Test
  public void test_BasicUnit() {
    BasicUnit u = new BasicUnit();
    assertEquals(1,u.getLevel());
    assertEquals(1,u.getBonus());
    assertEquals(3,u.getCost());

    u.UpgradeBasicUnit(2);
    assertEquals(2,u.getLevel());
    assertEquals(3,u.getBonus());
    assertEquals(11,u.getCost());
    
  }

}
