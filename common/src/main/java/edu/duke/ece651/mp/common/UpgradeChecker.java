package edu.duke.ece651.mp.common;

public abstract class UpgradeChecker{
  private final UpgradeChecker next;

  public UpgradeChecker(UpgradeChecker next){
    this.next = next;
  }

  protected abstract String checkMyRule(Player player, String src, int numUnit, int level, int newLevel);

  public String checkUpgrade(Player player, String src, int numUnit, int level, int newLevel){
    //if we fail our own rule: stop the movement is not legal
    String res = checkMyRule(player, src, numUnit, level, newLevel);
    if (res != null){
      return res;
    }
    //otherwise, ask the rest of the chain of rules
    if (next != null){
      return next.checkUpgrade(player, src, numUnit, level, newLevel);
    }
    //if there are no more rules, then the movement is legal
    return null;
  }
}
