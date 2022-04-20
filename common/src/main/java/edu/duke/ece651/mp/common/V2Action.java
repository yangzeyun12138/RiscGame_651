package edu.duke.ece651.mp.common;

import java.util.*;
import java.util.Random;
import java.io.*;
public class V2Action implements AbstractActionFactory {
  @Override
  public String checkForMove(Player player, String src, String dest, int numUnit, int level){
    MoveChecker CostCheck = new MoveCostRuleChecker(null);
    MoveChecker UnitCheck = new UnitRuleChecker(CostCheck);
    MoveChecker PathCheck = new PathRuleChecker(UnitCheck);
    MoveChecker NameCheck = new NameMoveRuleChecker(PathCheck);
    String result = NameCheck.checkMovement(player, src, dest, numUnit, level);
    return result;
  }  
   /**
   *Move function is to help the player to move the units at given level from source territory to the destination territory.
   *@param player build the attack!
   *@param src is the name of the source territory
   *@param dest is the name of the dest territory
   *@param numUnit is the number of unit that player wants to do the movement.
   *@param level is the level of unit selected
   */
  @Override
  public void Move(Player player, String src, String dest, int numUnit, int level){
    String result = checkForMove(player, src, dest, numUnit, level);
    
    if(result == null){
      // find src territory and dest territory
      for(Territory curr_t: player.player_terri_set){
        if(curr_t.getName().equals(src)){
          curr_t.loseUnits(numUnit, level);
        }
      }

      for(Territory curr_t: player.player_terri_set){
        if(curr_t.getName().equals(dest)){
          this.addUnitHelper(curr_t, numUnit, level);
        }
      }

      int total_cost = this.findMinPath(player, src, dest) * numUnit;
      player.costFood(total_cost);
    }
    else{
      throw new IllegalArgumentException(result);
    } 
  }
  /**
     loseUnitHelper: a helper function that can help to lose unit within a territory given number and level
     @param: curr_t: the territory chosen
     @param: number of unit
     @param: level of unit
   */
  public void loseUnitHelper(Territory curr_t, int numUnit, int level){
    boolean result = curr_t.loseUnits(numUnit, level);
  }
  /**
     addUnitHelper: a helper function that can help to add unit within a territory given number and level
     @param: curr_t: the territory chosen
     @param: number of unit
     @param: level of unit
   */
  public void addUnitHelper(Territory curr_t, int numUnit, int level){
    for(int i = 0; i < numUnit; i++){
      Unit u = new BasicUnit(level);
      curr_t.addUnit(u);
    }
  }
  /**
     findMinPath: find the minimum path from the source territory to destination territory and return the minimum total size
     @param: player does the movement
     @param: source territory
     @param: destination territory
   */
  public int findMinPath(Player player, String src, String dest){
    HashSet<String> t_set = new HashSet<String>();
    for(Territory curr_t : player.player_terri_set){
      if(curr_t.getName().equals(src)){
        t_set.add(curr_t.getName());
        int min_size = minTotalSize(curr_t, dest,t_set);
        return min_size;
          //- curr_t.getSize();
      }
    }
    return -1;
  }
  /**
     minTotalSize(): find the minumum total size using recursive DFS
     @param: current territory
     @param: name of the destination territory
     @param: the hashset of names of visited territory
     @return: the minimum total size from current territory
   */
  public int minTotalSize(Territory curr_t, String dest, HashSet<String> t_set){
    if(curr_t.getName().equals(dest)){
      return 0;
    }
    String color = curr_t.getColor();
    HashSet<String> new_t_set = new HashSet<String>();
    for(String t: t_set){
      new_t_set.add(t);
    }
   
    ArrayList<Integer> sizeList = new ArrayList<Integer>();
    int counter = 0;
    for(Territory neigh : curr_t.getNeigh()){
      if(neigh.getColor().equals(color) && !new_t_set.contains(neigh.getName())){
        counter++;
        new_t_set.add(neigh.getName());
        sizeList.add(neigh.getSize() + minTotalSize(neigh, dest, new_t_set));
        
      }
    }
    if(counter == 0 && curr_t.getNeigh().size() > 0){
      return 300000;
    }else{
      return getMin(sizeList);
    }
    
  }
  /**
   * getMin(): get the minimum integer within the arraylist of integer
   *@param: the arraylist of integer
   *@return the minimum number within the arraylist
   */
  public int getMin(ArrayList<Integer> arr_list){
    int min = Integer.MAX_VALUE;
    for(int a : arr_list){
      if (a < min){
        min = a;
      }
    }
    return min;
  }


  /**
   * checkForAttack() would check whether the attack if legal or not.
   * @param attacker is the Player who attacks others
   * @param src is the name of the source territory
   * @param dest is the name of the destination territory
   * @param numUnit is the number of Unit that join the attack.
   * @param level is the unit level
   * @return returns null if there is no error. returns string argument when there is an error.
   */
  @Override
  public String checkForAttack(Player attacker, String src, String dest, int numUnit, ArrayList<Player> players, int level){
    AttackChecker AttackCostCheck = new AttackCostRuleChecker(null);
    AttackChecker AdjacencyCheck = new AdjacencyRuleChecker(AttackCostCheck);
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
   * @param attackUnits is the arraylist that is doing the attack
   * @param players is the ArrayList<Player> of all players.
   */
  @Override

  public Player Attack (Player attacker, Player defender, String src, String dest,ArrayList<Unit> attackUnits, ArrayList<Player> players){    
    //Territory attackerTerri = findTerritory(attacker, src);
    Territory defenderTerri = findTerritory(defender, dest);
    // Attacker lose units that is attacking
    //attackerTerri.loseUnit(numUnit);

    // Attacker win Defender
    int numUnit = attackUnits.size();
    // Sort the Units from minimum to maximum
    this.sortUnit(attackUnits);
    defenderTerri.sortUnit();
    boolean unitRemain = (numUnit > 0) && (defenderTerri.countUnit() > 0);
    while(unitRemain){
      // If attacker wins:
      int atk_last_index = attackUnits.size() - 1;
      if(rollDice2(attackUnits.get(atk_last_index), defenderTerri.getUnitsFromIndex(0))){
        defenderTerri.loseUnit(); // lose unit which has index 0!
      } else {
        attackUnits.remove(atk_last_index);
        numUnit = attackUnits.size();
      }
      unitRemain = (numUnit > 0) && (defenderTerri.countUnit() > 0);
    }
    if(defenderTerri.countUnit() == 0){
      //Attack win!
      defender.player_terri_set.remove(defenderTerri);
      defenderTerri.changeColor(attacker.getColor());
      this.putAttackUnits(defenderTerri, attackUnits);
      attacker.player_terri_set.add(defenderTerri);
      changeAllColor(players, defenderTerri.getName(), attacker.getColor());
      return attacker;
    } else {
      return defender;
    }
    
  }

  public void putAttackUnits(Territory defenTerritory, ArrayList<Unit> units){
    for(Unit u : units){
      defenTerritory.addUnit(u);
    }
  }

  /**
   * sort from minimum to maximum according to unit level
   * @param: the arraylist of unit
   */
  public void sortUnit(ArrayList<Unit> units){
    if(units.size() != 0){
      for(int i = 0; i < units.size() - 1; i++){
        for(int j = i + 1; j < units.size(); j++){
          if(units.get(i).getLevel() > units.get(j).getLevel()){
            switchUnit(units, i, j);
          }
        }
      }
    }
  }

  /**
     switchUnit: switch the element at two positions within the arraylist of unit
     @param: arraylist of unit
     @param: a the index of the first element want to be switched
     @param: b the index of the second  element want to be switched
   */
  public void switchUnit(ArrayList<Unit> unit_arr, int a, int b){
    Unit A_copy = unit_arr.get(a);
    unit_arr.set(a, unit_arr.get(b));
    unit_arr.set(b, A_copy);
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
   * rollDice2() would help the user to determine the winner of a round of attack
   * @note You can change the seed1 and seed2.
   * @note You can get actual random seed by using long time_seed.
   * @param: atkUnit the unit that is doing the attack
   * @param: defUnit the unit that is doing the defense
   * @return: the successfulness of attacker Unit, True if the atkUnit wins
*/
  public boolean rollDice2(Unit atkUnit, Unit defUnit){
    int min = 1;
    int max = 20;
    long time_seed = System.currentTimeMillis();
    int seed1 = 100;
    int seed2 = seed1+1;
    Random rand1 = new Random(seed1);
    Random rand2 = new Random(seed2);
    
    
    int Dice1 = rand1.nextInt(max - min + 1) + min + atkUnit.getBonus();
    int Dice2 = rand2.nextInt(max - min + 1) + min + defUnit.getBonus();
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
  @Override
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
   * Add food to each player according to the size of the territory that the player has
   *@param players is all the players
   */

  @Override
  public void Done(ArrayList<Player> players){
    resetSpyMovables(players);
    for (Player p : players) {
      for (Territory t : p.player_terri_set) {
        t.addBasicUnit(1);
        p.addFood(t.getSize() * 10);
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

  /**
   *arrangeAttackOrder: arrange the orders according to the destinations and players
   *@param: arraylist of Orders 
   *@param: arraylist of Players
   *@param: a new attackmap that stores HashMap<String, ArrayList<Order>>
   *@param: attackMap
   */
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

  /**
     updatePlayer: update the player info according to the player's color and order
     @param: order send by player
     @param: arraylist of players
   */
  public void updatePlayer(Order order, ArrayList<Player> players){
    for(Player new_player : players){
       if(order.getPlayer().getColor().equals(new_player.getColor())){
          order.player = new_player;
       }
    }
  }

  /**
   *getRandomIdx: generate a list of random indexs according to the size of the arraylist
   *@param: size of an arraylist
   *@return: arraylist of random indexs
   */
  @Override
  public ArrayList<Integer> getRandomIdx(int sz) {
    ArrayList<Integer> numlist = new ArrayList<Integer>();
    for (int i = 0; i < sz; i++) {
      numlist.add(i);
    }

    Collections.shuffle(numlist, new Random());
    return numlist;
  }
  /**
   *loseAttackUnit: subtract units from the territory according to the orders
   *@param: orderList is the arraylist of orders
   *@param: players is the arraylist of player
   *@throws exceptions prompted by rule chechers and check invalid operations
   */
  @Override
  public void loseAttackUnit(ArrayList<Orders> ordersList, ArrayList<Player> players) {
    for (int i = 0; i < ordersList.size(); i++) {
      for (Order o : ordersList.get(i).AttackList) {
        Territory attackerTerri = findTerritory(players.get(i), o.getSrc());

        String result = checkForAttack(players.get(i), o.getSrc(), o.getDest(), o.getNumUnit(), players, o.currLevel);
        if (result != null) {
          throw new IllegalArgumentException(result);
        }

        boolean res = attackerTerri.loseUnits(o.getNumUnit(), o.currLevel);
        if (res == false) {
          throw new IllegalArgumentException(players.get(i).color + " player has invalid attack orders. " +
                  "The numUnits of level " + o.currLevel + "is insufficient.\n");
        }
      }
    }
  }
  /**
   *refineAttack: combines the attack units which has the same destinations and the same player
   *@param: arraylist of order
   *@param: arraylist of players
   *@return: arraylist of order that combines the units and players
   */
  @Override
  public ArrayList<Order> refineAttack(ArrayList<Order> attackList, ArrayList<Player> players) {
    ArrayList<Order> res = new ArrayList<Order>();
    for (Player p : players) {
      Order temp = new Order(p, " ", " ", 0, 0 ,0);
      for (Order o : attackList) {
        if (p.color.equals(o.getPlayer().color)) {
          if (temp.getSrc().equals(" ")) {
            temp = o;
            for (int i = 0; i < o.numUnit; i++) {
              temp.unitList.add(new BasicUnit(o.currLevel));
            }
          }
          else {
            for (int i = 0; i < o.numUnit; i++) {
              temp.unitList.add(new BasicUnit(o.currLevel));
            }
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
   public void Move(Player player, String src, String dest, int numUnit){
     
   }

  @Override
  public Player Attack (Player attacker, Player defender, String src, String dest, int numUnit, ArrayList<Player> players){
    return null;
  }

  /**
   *checkForUpgrade: check all of the upgrade rule checkers in chain
   *@param: player: the player is doing the upgrade
   *@param: src: the name of the source territory
   *@param: numUnit: the number of unit is upgrading
   *@param: curr_level: the level of units that needs upgrade
   *@param: new_level: the level of units that needs upgrade to
   *@return: the string that tells what has went wrong, null if nothing is wrong
   */
  @Override
  public String checkForUpgrade(Player player, String src, int numUnit, int curr_level, int new_level) {
    if (new_level == 9){
      int SpyCreationCost = 20;
      boolean checkTerri = false;
      
      if(player.getFood() < SpyCreationCost*numUnit){
        return new String(player.getColor()+" Player: There is not enough food to create a spy!");
      }
      
      for(Territory curr_t : player.player_terri_set){
        if(curr_t.getName().equals(src)){
          if(curr_t.countLevel(0) < numUnit){
            return new String(player.getColor()+" Player: There is not enough Level 0 Units to create a spy!");
          }
          checkTerri = true;
        
        }
      }
    
      if(checkTerri == false){
        return new String(player.getColor()+" Player: There is no Spy in this Territory!");
      }
    }
    
    UpgradeChecker tech = new TechRuleChecker(null);
    UpgradeChecker level = new LevelRuleChecker(tech);
    UpgradeChecker downgrade = new NoDowngradeRuleChecker(level);
    UpgradeChecker cost = new UpgradeCostRuleChecker(downgrade);
    UpgradeChecker name = new NameUpgradeRuleChecker(cost);

    String res =null;
    res = name.checkUpgrade(player, src, numUnit, curr_level, new_level);
    return res;
    
  }
  /**
   *computeCost: compute the cost for upgrade from current level to the new level
   *@param: numUnit: the number of Units 
   *@param: curr_level: the current level of units
   *@param: new_level: the new level of units
   */
  public int computeCost(int numUnit, int curr_level, int new_level) {
    LevelInfo info = new LevelInfo();
    return numUnit * (info.getCost(new_level) - info.getCost(curr_level));
  }
  /**
   *unitUpgrade: do the upgrade of unit
   *@param: player: the player is doing the upgrade
   *@param: src: the name of the source territory
   *@param: numUnit: the number of unit is upgrading
   *@param: curr_level: the level of units that needs upgrade
   *@param: new_level: the level of units that needs upgrade to
   */
  @Override
  public void unitUpgrade(Player player, String src, int numUnit, int curr_level, int new_level) {
    if (new_level == 9){
      for(int i = 0; i<numUnit; i++){
        this.createSpy(player, src);
      }
    }else{
      for(Territory curr_t: player.player_terri_set){
        if(curr_t.getName().equals(src)){
          curr_t.upgradeUnit(numUnit, curr_level, new_level);
        }
      }
      player.costFood(computeCost(numUnit, curr_level, new_level));
    }
  }
  /**
   *createSpy: create a spy
   *@param: player: the player who wants to create the spy
   *@param: src: the name of the territory to place the spy

   */
  @Override
  public void createSpy(Player player, String src){
    int SpyCreationCost = 20;
    
    for(Territory curr_t : player.player_terri_set){
      if(curr_t.getName().equals(src)){
        curr_t.loseUnit(0);
        curr_t.addSpy(player.getColor());
        player.costFood(SpyCreationCost);
      }
    }
  }
  /**
   *checkForSpyMove: check all the rules before move a spy
   *@param: player: the player who wants to move the spy
   *@param: players: the list of all players
   *@param: src: the name of the source territory
   *@param: dest: the name of the destination territory
   *@return: result: null if there is no errors; Error message as a String
   */
  @Override
  public String checkForSpyMove(Player player, ArrayList<Player> players, String src, String dest){
    SpyChecker SpyCost = new SpyCostRuleChecker(null);
    SpyChecker SpyMove = new SpyMoveRuleChecker(SpyCost);
    SpyChecker SpyOnce = new SpyOnceRuleChecker(SpyMove);
    
    String result = SpyOnce.checkSpy(player, players, src, dest);
    return result;
  }

  /**
   *checkForSpyMove: move a spy of the player from the source to destination
   *@param: player: the player who wants to move the spy
   *@param: players: the list of all players
   *@param: src: the name of the source territory
   *@param: dest: the name of the destination territory
   */
  @Override
  public void spyMove(Player player, ArrayList<Player> players, String src, String dest){
    int SpyMoveCost = 20;
    for (Player p : players){
      for(Territory t : p.player_terri_set){
        if (t.getName().equals(src)){
          t.loseSpy(player.getColor());
        }
        if (t.getName().equals(dest)){
          t.addSpy(player.getColor());
        }
      }
    }
    player.costFood(SpyMoveCost);
  }

  /**
   *resetSpyMovables: set all spies in the map movable
   *@param: players: arraylist of player
   */
  @Override
  public void resetSpyMovables(ArrayList<Player> players){
    for (Player p : players){
      for (Territory t : p.player_terri_set){
        t.resetAllSpies();
      }
    }
  }
}
