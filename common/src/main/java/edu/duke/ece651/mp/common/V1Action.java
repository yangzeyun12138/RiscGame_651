package edu.duke.ece651.mp.common;

import java.util.*;
import java.util.Random;
import java.io.*;
public class V1Action implements AbstractActionFactory {
  @Override
  public String checkForMove(Player player, String src, String dest, int numUnit){
    MoveChecker UnitCheck = new UnitRuleChecker(null);
    MoveChecker PathCheck = new PathRuleChecker(UnitCheck);
    String result = PathCheck.checkMovement(player, src, dest, numUnit);
    return result;
  }  
   /**
   *Move function is to help the player to move the units from source territory to the destination territory.
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the attack.
   */
  @Override
  public void Move(Player player, String src, String dest, int numUnit){
    String result = checkForMove(player, src, dest, numUnit);
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

  /**
   * checkForAttack() would check whether the attack if legal or not.
   * @param attacker is the Player who attacks others
   * @param src is the name of the source territory
   * @param dest is the name of the destination territory
   * @param numUnit is the number of Unit that join the attack.
   * @return returns null if there is no error. returns string argument when there is an error.
   */
  public String checkForAttack(Player attacker, String src, String dest, int numUnit){
    AttackChecker AdjacencyCheck = new AdjacencyRuleChecker(null);
    AttackChecker FactionCheck = new FactionRuleChecker(AdjacencyCheck);
    String result = FactionCheck.checkAttack(attacker, src, dest, numUnit);
    return result;
  }

  /**
   * Attack() would implement the command of attack.
   * @param attacker is the Player who attacks others
   * @param defender is the Player who is attacked by others
   * @param src is the name of the source territory
   * @param dest is the name of the destination territory
   * @param numUnit is the number of Unit that join the attack.
   */
  @Override

  public Player Attack (Player attacker, Player defender, String src, String dest, int numUnit){

    String checkResult = checkForAttack(attacker, src, dest, numUnit);
    if (checkResult != null){
      throw new IllegalArgumentException(checkResult);
     }
    
    //Territory attackerTerri = findTerritory(attacker, src);
    Territory defenderTerri = findTerritory(defender, dest);

    // Attacker lose units that is attacking
    //attackerTerri.loseUnit(numUnit);

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
      return attacker;
    } else {
      return defender;
    }
    
  }

  /**
   * rollDice() would help the user to determine the winner of a round of attack
   * @note You can change the seed1 and seed2.
   * @note You can get actual random seed by using long time_seed.
*/
  public boolean rollDice(){
    int min = 1;
    int max = 20;
    long time_seed = System.currentTimeMillis();
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

  /**
   * findTerritory() can help you find the territory from a player.
   * @param player is the owner of the territory.
   * @param name is the name of the territory.
   * @return returns the territory.
   */
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
   *@param players is all the players
   */

  @Override
  public void Done(ArrayList<Player> players){
    for (Player p : players) {
      for (Territory t : p.player_terri_set) {
        t.addBasicUnit(1);
      }
    }
  }


   /**
   * getPlayer() can help the server to get the Player based on the name of the destination territory
   *@param dest is the name of the destination territory
   *@param players is the ArrayList of players
   *@return returns the player
   */
  @Override
  public Player getPlayer(String dest, ArrayList<Player> players){
    for(Player p : players){
      for(Territory t : p.player_terri_set){
        if(t.getName().equals(dest)){
          return p;
        }
      }
    }
    return null;
  }

  @Override
  public HashMap<String, ArrayList<Order>> arrangeAttackOrder(ArrayList<Orders> ordersList, ArrayList<Player> players){
    HashMap<String, ArrayList<Order>> AttackMap = new HashMap<String, ArrayList<Order>>();
    for(Orders orders : ordersList){
      for(Order order : orders.AttackList){
        String dest = order.getDest();
        updatePlayer(order, players);
        if(!AttackMap.containsKey(dest)){
          ArrayList<Order> temp = new ArrayList<Order>();
          temp.add(order);
          AttackMap.put(dest, temp);
        } else{
          AttackMap.get(dest).add(order);
        }
      }
    }
    return AttackMap;
  }

  
  public void updatePlayer(Order order, ArrayList<Player> players){
    for(Player new_player : players){
       if(order.getPlayer().getColor().equals(new_player.getColor())){
          order.player = new_player;
       }
    }
  }
  @Override
  public ArrayList<Integer> getRandomIdx(int sz) {
    ArrayList<Integer> numlist = new ArrayList<Integer>();
    for (int i = 0; i < sz; i++) {
      numlist.add(i);
    }

    Collections.shuffle(numlist, new Random());
    return numlist;
  }
  @Override
  public void loseAttackUnit(ArrayList<Orders> ordersList, ArrayList<Player> players) {
    for (int i = 0; i < ordersList.size(); i++) {
      for (Order o : ordersList.get(i).AttackList) {
        Territory attackerTerri = findTerritory(players.get(i), o.getSrc());
        boolean res = attackerTerri.loseUnit(o.getNumUnit());
        if (res == false) {
          throw new IllegalArgumentException(players.get(i).color + " player has invalid orders");
        }
      }
    }
  }

  @Override
  public ArrayList<Order> refineAttack(ArrayList<Order> attackList, ArrayList<Player> players) {
    ArrayList<Order> res = new ArrayList<Order>();
    for (Player p : players) {
      Order temp = new Order(p, " ", " ", 0);
      for (Order o : attackList) {
        if (p.color.equals(o.getPlayer().color)) {
          if (temp.getSrc().equals(" ")) {
            temp = o;
          }
          else {
            temp.numUnit += o.numUnit;
          }
        }
      }
      if (!temp.getSrc().equals(" ")) {
        res.add(temp);
      }
    }
    return res;
  }

  @Override
  public int getIndexFromPlayers(ArrayList<Player> players, String color){
    int counter = 0;
    for(Player player : players){
      if(player.color.equals(color)){
        break;
      } else{
        counter++;
      }
    }
    return counter;
  }
}
