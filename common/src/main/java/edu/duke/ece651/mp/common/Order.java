package edu.duke.ece651.mp.common;

import java.util.ArrayList;

public class Order implements java.io.Serializable{
  private static final long serialVersionUID = 1L;
  final String src;
  final String dest;
  public int numUnit;
  public Player player;
  public int currLevel;
  public int afterLevel;
  public ArrayList<Unit> unitList;
  public boolean moveSpy;
  public boolean doCloak;
  public boolean doGuard;

  public Order(Player player, String src, String dest, int numUnit, int currLevel, int afterLevel){
    this.src = src;
    this.dest = dest;
    this.numUnit = numUnit;
    this.player = player;
    this.currLevel = currLevel;
    this.afterLevel = afterLevel;
    this.unitList = new ArrayList<>();
    this.moveSpy = false;
    this.doCloak = false;
    this.doGuard = false;
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
