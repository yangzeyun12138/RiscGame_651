package edu.duke.ece651.mp.common;
import java.util.ArrayList;
public abstract class SpyChecker{
  private final SpyChecker next;

  public SpyChecker(SpyChecker next){
    this.next = next;
  }

  protected abstract String checkMyRule(Player player, ArrayList<Player> players, String src, String dest);

  public String checkSpy(Player player, ArrayList<Player> players, String src, String dest){
    //if we fail our own rule: stop the movement is not legal
    String res = checkMyRule(player,players, src, dest);
    if (res != null){
      return res;
    }
    //otherwise, ask the rest of the chain of rules
    if (next != null){
      return next.checkSpy(player,players, src, dest);
    }
    //if there are no more rules, then the movement is legal
    return null;
  }
}
