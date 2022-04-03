package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LevelInfoTest {
  @Test
  public void test_level_info() {
    LevelInfo Info = new LevelInfo();
    assertThrows(IllegalArgumentException.class, ()->Info.getBonus(-1));
    assertThrows(IllegalArgumentException.class, ()->Info.getCost(7));
    assertEquals(8, Info.getBonus(4));
    assertEquals(90, Info.getCost(5));
  }

}
