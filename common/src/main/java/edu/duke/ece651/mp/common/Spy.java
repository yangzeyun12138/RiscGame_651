package edu.duke.ece651.mp.common;

import java.util.ArrayList;
import java.util.HashMap;

public class Spy implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private String color;
  private boolean isMove;
  private int cost;
  /**
   * Spy constructor: initialize level, bonus, and cost to 0
   */
  public Spy(String Color){
    this.color = Color;
    this.isMove = true;
    this.cost = 20;
  };
  

  /**
   *getColor: get the color of the Spy
   *@return: the color of the spy as a String
   */
  public String getColor(){
    return this.color;
  }
  /**
   *getIsMove: get the IsMove of the Spy
   *@return: the IsMove of the Spy as an boolean
   */
  public boolean getIsMove(){
    return this.isMove;
  }
  /**
   *getCost: get the cost of the Spy
   *@return: the cost of the Spy as an integer
   */
  public int getCost(){
    return this.cost;
  }

  /**
   *setIsMove: Set the isMove of the Spy
   */
  public void setIsMove(boolean B){
    this.isMove = B;
  }
}
