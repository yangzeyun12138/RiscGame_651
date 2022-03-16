package edu.duke.ece651.mp.common;

public class Order implements java.io.Serializable{
  private static final long serialVersionUID = 1L;
  final String src;
  final String dest;
  public int numUnit;
  public Player player;
  public Order(Player player, String src, String dest, int numUnit){
    this.src = src;
    this.dest = dest;
    this.numUnit = numUnit;
    this.player = player;
  }

  public String getSrc(){
    return this.src;
  }

  public String getDest(){
    return this.dest;
  }

  public int getNumUnit(){
    return this.numUnit;
  }

  public Player getPlayer(){
    return this.player;
  }
}
