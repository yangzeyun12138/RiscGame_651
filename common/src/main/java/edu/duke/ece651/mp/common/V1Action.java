package edu.duke.ece651.mp.common;

import java.util.*;
public class V1Action implements AbstractActionFactory {
   /**
   *Move function is to help the player to move the units from source territory to the destination territory.
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the attack.
   */
  @Override
  public void Move(Player player, String src, String dest, int numUnit){
    MoveChecker UnitCheck = new UnitRuleChecker(null);
    MoveChecker PathCheck = new PathRuleChecker(UnitCheck);
    String result = PathCheck.checkMovement(player, src, dest, numUnit);
    if(result == null){
      // find src territory and dest territory
      for(Territory curr_t: player.player_terri_set){
        if(curr_t.getName().equals(src)){
          curr_t.loseUnit(numUnit);
        }
        if(curr_t.getName().equals(dest)){
          curr_t.addBasicUnit(numUnit);
        }
      }
    } else{
      throw new IllegalArgumentException(result);
    } 
  }

  // TODO: commit to server
  @Override
  public ArrayList<Player> Attack (ArrayList<Player> allPlayers){
    return null;
  }

  /**
   * Done function is the final command that the player would use, and it will help player to add 1 unit to all of his/her territories.
   *@param player is the current user.
   */
  @Override
  public void Done(Player player){
    for(Territory t : player.player_terri_set){
      t.addBasicUnit(1);
    }
  }
}
