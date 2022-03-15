package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OrderTest {
  @Test
  public void test_Order_getSrc_getDest_getNumUnit() {
    String src = "Gondor";
    String dest = "Oz";
    int numUnit = 3;
    Order order = new Order(src, dest, numUnit);
    assertEquals("Gondor", order.getSrc());
    assertEquals("Oz", order.getDest());
    assertEquals(3, order.getNumUnit());    
  }

}
