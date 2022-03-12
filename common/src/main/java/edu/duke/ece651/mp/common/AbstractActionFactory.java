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
  // TODO: commit 
  public void Attack (Player attacker, Player defender, String src, String dest, int numUnit);
  /**
   * Done function is the final command that the player would use, and it will help player to add 1 unit to all of his/her territories.
   *@param player is the current user.
   */
  public void Done(Player player);
  
}
