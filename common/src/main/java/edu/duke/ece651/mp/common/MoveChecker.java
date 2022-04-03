package edu.duke.ece651.mp.common;

public abstract class MoveChecker{
  private final MoveChecker next;

  public MoveChecker(MoveChecker next){
    this.next = next;
  }

  protected abstract String checkMyRule(Player player, String src, String dest, int numUnit, int level);

  public String checkMovement(Player player, String src, String dest, int numUnit, int level){
    //if we fail our own rule: stop the movement is not legal
    String res = checkMyRule(player, src, dest, numUnit, level);
    if (res != null){
      return res;
    }
    //otherwise, ask the rest of the chain of rules
    if (next != null){
      return next.checkMovement(player, src, dest, numUnit, level);
    }
    //if there are no more rules, then the movement is legal
    return null;
  }
}
