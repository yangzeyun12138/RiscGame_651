package edu.duke.ece651.mp.common;

import java.util.*;
import java.util.Random;
import java.io.*;
public class V1Action implements AbstractActionFactory {
  @Override
  public String checkForMove(Player player, String src, String dest, int numUnit, int level){
    MoveChecker UnitCheck = new UnitRuleChecker(null);
    MoveChecker PathCheck = new PathRuleChecker(UnitCheck);
    MoveChecker NameCheck = new NameMoveRuleChecker(PathCheck);
    String result = NameCheck.checkMovement(player, src, dest, numUnit, 0);
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
    String result = checkForMove(player, src, dest, numUnit, 0);
    if(result == null){
      // find src territory and dest territory
      for(Territory curr_t: player.player_terri_set){
        if(curr_t.getName().equals(src)){
          curr_t.loseUnits(numUnit);
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
  @Override
  public String checkForAttack(Player attacker, String src, String dest, int numUnit, ArrayList<Player> players, int level){
    AttackChecker AdjacencyCheck = new AdjacencyRuleChecker(null);
    AttackChecker FactionCheck = new FactionRuleChecker(AdjacencyCheck);
    AttackChecker NameCheck = new NameAttackRuleChecker(FactionCheck);
    String result = NameCheck.checkAttack(attacker, src, dest, numUnit, players, level);
    return result;
  }

  /**
   * Attack() would implement the command of attack.
   * @param attacker is the Player who attacks others
   * @param defender is the Player who is attacked by others
   * @param src is the name of the source territory
   * @param dest is the name of the destination territory
   * @param numUnit is the number of Unit that join the attack.
   * @param players is the ArrayList<Player> of all players.
   */
  @Override

  public Player Attack (Player attacker, Player defender, String src, String dest, int numUnit, ArrayList<Player> players){
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
      changeAllColor(players, defenderTerri.getName(), attacker.getColor());
      return attacker;
    } else {
      return defender;
    }
    
  }

  /**
   * changeAllColor() would help to change whole the color of the Territory that is gotten by the attacker from all players.
   * To be more specific, owing to deep_copy(), the name of the Territory of all players' neighbor list are not the same.
   * @param players is all players.
   * @param t_name is the territory that is gotten by the attacker.
   * @param color is the color of the attacker.
*/
  public void changeAllColor(ArrayList<Player> players, String t_name, String color){
    for(Player p : players){
      for(Territory t : p.player_terri_set){
        for(Territory curr_neigh: t.getNeigh()){
          if(curr_neigh.getName().equals(t_name)){
            curr_neigh.changeColor(color);
          }
        }
      }
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
      // Attacker wins this round!
      return true;
    } else{
      // Defender wins this round
      return false;
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

        String result = checkForAttack(players.get(i), o.getSrc(), o.getDest(), o.getNumUnit(), players, o.currLevel);
        if (result != null) {
          throw new IllegalArgumentException(result);
        }

        boolean res = attackerTerri.loseUnits(o.getNumUnit());
        if (res == false) {
          throw new IllegalArgumentException(players.get(i).color + " player has invalid attack orders. The numUnits is insufficient.\n");
        }
      }
    }
  }

  @Override
  public ArrayList<Order> refineAttack(ArrayList<Order> attackList, ArrayList<Player> players) {
    ArrayList<Order> res = new ArrayList<Order>();
    for (Player p : players) {
      Order temp = new Order(p, " ", " ", 0, 0, 0);
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

  /**
   *getIndexFromPlayers() can provide the index of specific color from ArrayList<Player>.
   *@param players is the ArrayList<Player> that represents all players in the game.
   *@param color is the color of the player you want to find.
   *@return the index of specific color inside of the ArrayList<Player>.
*/
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

  /**
   *checkWin() can check whether there is a winner in a current round.
   *@param players is the ArrayList<Player> that represents all players in the game.
   *@return returns the color of the winner, and returns NULL as there is no winner now. 
*/
  @Override
  public String checkWin(ArrayList<Player> players){
    int index = 0;
    int counter = 0;
    int num_players = players.size();
    for(int i = 0; i < players.size(); i++){
      Player curr_player = players.get(i);
      if(!curr_player.checkLose()){
        // if curr_player hasn't lost the game
        index = i;
        counter++;
      }
    }
    if(counter == 1){
      return players.get(index).color;
    }
    return null;
  }

  @Override
  public void Move(Player player, String src, String dest, int numUnit, int level){
    
  }

  @Override
  public Player Attack (Player attacker, Player defender, String src, String dest, ArrayList<Unit> attackList, ArrayList<Player> players){
    return null;
  }

  @Override
  public String checkForUpgrade(Player player, String src, int numUnit, int curr_level, int new_level){
    return null;
  }
  @Override
  public void unitUpgrade(Player player, String src, int numUnit, int curr_level, int new_level) {}
  @Override
  public void createSpy(Player player,String src){};

  @Override
  public String checkForSpyMove(Player player, ArrayList<Player> players, String src, String dest){
    return null;
  };

  @Override
  public void spyMove(Player player, ArrayList<Player> players, String src, String dest){};

  @Override
  public void resetSpyMovables(ArrayList<Player> players){}

  @Override
  public void resetSkill(ArrayList<Player> players) {}

  ;
}
