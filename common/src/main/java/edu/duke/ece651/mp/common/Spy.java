package edu.duke.ece651.mp.common;

import java.util.ArrayList;
import java.util.HashMap;

public class Spy implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private String color;
  private boolean CanMove;
  private int cost;
  /**
   * Spy constructor: initialize level, bonus, and cost to 0
   */
  public Spy(String Color){
    this.color = Color;
    this.CanMove = true;
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
   *getCanMove: get the CanMove of the Spy
   *@return: the CanMove of the Spy as an boolean
   */
  public boolean getCanMove(){
    return this.CanMove;
  }
  /**
   *getCost: get the cost of the Spy
   *@return: the cost of the Spy as an integer
   */
  public int getCost(){
    return this.cost;
  }

  /**
   *setCanMove: Set the CanMove of the Spy
   */
  public void setCanMove(boolean B){
    this.CanMove = B;
  }
}
