package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SpyTest {
  @Test
  public void test_Spy() {
    Spy s = new Spy("Red");
    assertTrue(s.getCanMove());
    assertEquals("Red",s.getColor());
    s.setCanMove(false);
    assertFalse(s.getCanMove());
    assertEquals(20,s.getCost());
  }

}
