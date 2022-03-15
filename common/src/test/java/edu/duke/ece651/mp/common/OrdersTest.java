package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OrdersTest {
  @Test
  public void test_orders() {
    String src1 = "Gondor";
    String dest1 = "Hogwarts";
    int numUnit1 = 2;
    Order order1 = new Order(src1, dest1, numUnit1);
    
    String src2 = "Gondor";
    String dest2 = "Oz";
    int numUnit2 = 3;
    Order order2 = new Order(src2, dest2, numUnit2);

    Orders orders = new Orders();
    orders.MoveList.add(order1);
    orders.AttackList.add(order2);
    assertEquals(1, orders.MoveList.size());
    assertEquals(1, orders.AttackList.size());
  }
  

}
