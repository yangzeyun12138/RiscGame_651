package edu.duke.ece651.mp.common;

public class Order {
  final String src;
  final String dest;
  final int numUnit;
  public Order(String src, String dest, int numUnit){
    this.src = src;
    this.dest = dest;
    this.numUnit = numUnit;
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
}
