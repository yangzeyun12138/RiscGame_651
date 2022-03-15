package edu.duke.ece651.mp.common;

import java.util.*;
import java.util.Random;
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
    }
    else{
      throw new IllegalArgumentException(result);
    } 
  }

  // TODO: commit to server
  @Override
  public void Attack (Player attacker, Player defender, String src, String dest, int numUnit){
    Territory attackerTerri = findTerritory(attacker, src);
    Territory defenderTerri = findTerritory(defender, dest);
    // Attacker lose units that is attacking
    attackerTerri.loseUnit(numUnit);
    // Attacker win Defender
    boolean unitRemain = (numUnit > 0) && (defenderTerri.countUnit() > 0);
    while(unitRemain){
      if(rollDice()){
        defenderTerri.loseUnit();
      } else {
        numUnit -= 1;
      }
      unitRemain = (numUnit > 0) && (defenderTerri.countUnit() > 0);
    }
    if(defenderTerri.countUnit() == 0){
      //Attack win!
      defender.player_terri_set.remove(defenderTerri);
      defenderTerri.changeColor(attacker.getColor());
      defenderTerri.setBasicUnit(numUnit);
      attacker.player_terri_set.add(defenderTerri);
    }
    
  }

  public boolean rollDice(){
    int min = 1;
    int max = 20;
    int seed1 = 100;
    int seed2 = seed1+1;
    Random rand1 = new Random(seed1);
    Random rand2 = new Random(seed2);
    
    
    int Dice1 = rand1.nextInt(max - min + 1) + min;
    int Dice2 = rand2.nextInt(max - min + 1) + min;
    if (Dice1 > Dice2){
      return true;
    }else if (Dice2 > Dice1){
      return false;
    }else{
      return rollDice();
    }
  }

  public Territory findTerritory(Player player, String name){
    for(Territory t : player.player_terri_set){
      if(t.getName().equals(name)){
        return t;
      }
    }
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
