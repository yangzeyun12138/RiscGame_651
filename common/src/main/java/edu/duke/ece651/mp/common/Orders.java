package edu.duke.ece651.mp.common;
import java.util.*;
public class Orders implements java.io.Serializable{
  private static final long serialVersionUID = 1L;
  public ArrayList<Order> MoveList;
  public ArrayList<Order> AttackList;
  public Player player;
  public Orders(Player player){
    this.MoveList = new ArrayList<Order>();
    this.AttackList = new ArrayList<Order>();
    this.player = player;
  }

}
