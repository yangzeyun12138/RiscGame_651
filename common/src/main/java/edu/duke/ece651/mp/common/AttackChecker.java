package edu.duke.ece651.mp.common;
import java.util.*;
public abstract class AttackChecker {
  private final AttackChecker next;

  public AttackChecker(AttackChecker next){
    this.next = next;
  }
  
  protected abstract String checkMyRule(Player player, String src, String dest, int numUnit, ArrayList<Player> players);

  public String checkAttack(Player player, String src, String dest, int numUnit, ArrayList<Player> players){
    //if we fail our own rule: stop the attack which is not legal
    String res = checkMyRule(player, src, dest, numUnit, players);
    if (res != null){
      return res;
    }
    //otherwise, ask the rest of the chain of rules
    if (next != null){
      return next.checkAttack(player, src, dest, numUnit, players);
    }
    //if there are no more rules, then the attack is legal
    return null;
  }
}
