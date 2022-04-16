package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SpyTest {
  @Test
  public void test_Spy() {
    Spy s = new Spy("Red");
    assertTrue(s.getIsMove());
    assertEquals("Red",s.getColor());
    s.setIsMove(false);
    assertFalse(s.getIsMove());
    assertEquals(20,s.getCost());
  }

}
