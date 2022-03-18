package edu.duke.ece651.mp.common;

import java.util.*;
public interface AbstractActionFactory {
  /**
   *Move function is to help the player to move the units from source territory to the destination territory.
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the attack.
   */
  public void Move(Player player, String src, String dest, int numUnit);
  public Player Attack (Player attacker, Player defender, String src, String dest, int numUnit, ArrayList<Player> players);
  /**
   * Done function is the final command that the player would use, and it will help player to add 1 unit to all of his/her territories.
   *@param player is the current user.
   */
  public void Done(ArrayList<Player> players);
  public String checkForMove(Player player, String src, String dest, int numUnit);
  public String checkForAttack(Player attacker, String src, String dest, int numUnit, ArrayList<Player> players);

  public Player getPlayer(String dest, ArrayList<Player> players);
  public HashMap<String, ArrayList<Order>> arrangeAttackOrder(ArrayList<Orders> ordersList, ArrayList<Player> players);
  public ArrayList<Integer> getRandomIdx(int sz);
  
  public void loseAttackUnit(ArrayList<Orders> ordersList, ArrayList<Player> players);
  public ArrayList<Order> refineAttack(ArrayList<Order> attackList, ArrayList<Player> players);
  public int getIndexFromPlayers(ArrayList<Player> players, String color);
  public String checkWin(ArrayList<Player> players);
}
