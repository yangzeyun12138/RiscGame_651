package edu.duke.ece651.mp.common;

import java.io.*;
import java.util.*;

public class Player implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    public String color;
    private int tech_level;
    private int food;
    public HashSet<Territory> player_terri_set;
    
    public HashMap<String, ArrayList<Territory>> old_view_map;
    public HashMap<String, ArrayList<Territory>> new_view_map;

  public Player(String color, HashSet<Territory> player_terri_set) {
        this.color = color;
        this.player_terri_set = player_terri_set;
        this.tech_level = 1; // set initial technology level to 1
        this.food = 140;// set initial food to 140
        this.old_view_map = viewMapInitializer(player_terri_set);
        this.new_view_map = viewMapInitializer(player_terri_set);
    }
  
    /**
     * @return a String that display the player color and their territories info
     */

    public String toString() {
        String res = new String(color + " player       Total tech level: " + this.getTechLevel() + "       " +
                "Resources: " + this.food + "\n");
        res += "-----------\n";
        for (Territory t: player_terri_set) {
            res += "Units in " + t.getName() + " (next to: ";
            Iterator<Territory> it = t.getNeigh().iterator();
            while(true) {
                res += it.next().getName();
                if(it.hasNext()) {
                    res += ", ";
                }
                else {
                    res += ")\n";
                    break;
                }
            }
            res += t.toString();
        }
        res += "\n";
        return res;
    }



  /**
   *getColor(), the the color of the player
   *@return: color of the player.
   */
  
  public String getColor(){
    return this.color;
  }

  /**
   * deep_copy() can get the deep copy of the current player
   * @return Player which is the deep copy of the current player.
*/
  public Player deep_copy() {
      Player deep_copy = null;
      try {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ObjectOutputStream oos = new ObjectOutputStream(baos);
          oos.writeObject(this);

          ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
          ObjectInputStream ois = new ObjectInputStream(bais);
          deep_copy = (Player) ois.readObject();
      } catch (ClassNotFoundException | IOException e) {
          e.printStackTrace();
      }
      return deep_copy;
  }


  /**
   * checkLose() can check whether the current player has lost the game or not
   * @return return true represents lose; return false represents current player hasn't lost
   */
  public boolean checkLose(){
    if(player_terri_set.size() == 0){
      return true;
    }
    return false;
  }
  
  /**
   *getTechLevel():
   *@return: the techonology level of the player
   */

  public int getTechLevel(){
    return this.tech_level;
  }
  /**
   *upgradeTechLevel
   *upgrade the technology level by 1
   */
  public void upgradeTechLevel(){
    LevelInfo info = new LevelInfo();
    int totalCost = (info.getTechCost(this.tech_level + 1) - info.getTechCost(this.tech_level));
    this.tech_level += 1;
    if(this.food < totalCost){
      throw new IllegalArgumentException("The number of food is lower than the totalCost when upgrading tech level!\n");
    }
    this.food -= totalCost;
  }
  /**
   *getFood():
   *@return: the remaining number of food of the player
   */
  public int getFood(){
    return this.food;
  }
  /**
   *costFood(): reduce the food of player by expense
   *@param: the number of food expense
   */
  public void costFood(int expense){
    if(expense < 0){
      throw new IllegalArgumentException("Cannot reduce negative food!");
    }
    this.food -= expense;
  }
  /**
   *costFood(): increase the food of player by add on number
   *@param: the number of food added on
   */
  public void addFood(int add){
    if(add < 0){
      throw new IllegalArgumentException("Cannot add negative food!");
    }
    this.food += add;
  }
  /**
   *getTerritory():
   *@param: the name of the territory
   *@return: territory according to the name
   */
  public Territory getTerritory(String name) {
      for (Territory t : player_terri_set) {
          if (t.getName().equals(name)){
              return t;
          }
      }
      return null;
  }
    
  /**
   * viewMapInitializer() is called by the Player's constructor, and it can help the constructor to initialize old_view_map, new_view_map.
   *@param player_terri_set is the latest information of the teritories owned by the player.
   *@return HashMap<String, ArrayList<Territory>> curr_new_map. 
   */
  public HashMap<String, ArrayList<Territory>> viewMapInitializer(HashSet<Territory> player_terri_set){
    ArrayList<Territory> cur_viewed_list = viewMapHelper(player_terri_set);
    ArrayList<Territory> cur_grey_list = new ArrayList<Territory>();
    // Prepare to return 
    HashMap<String, ArrayList<Territory>> cur_new_map = new HashMap<String, ArrayList<Territory>>();
    cur_new_map.put("viewed", cur_viewed_list);
    cur_new_map.put("grey", cur_grey_list);
    return cur_new_map;
  }

  private HashSet<String> getNameList(ArrayList<Territory> list){
    HashSet<String> temp_name = new HashSet<String>();
    for(Territory t : list){
      temp_name.add(t.getName());
    }
    return temp_name;
  }
  
  /**
   * updateNewViewMap() is used when the new round starts. This method would be called by the Client, and then help the Client to update new_view_map by comparing the old_view_map which is from the last round.
   *@return No return. This method will update HashMap<String, ArrayList<Territory>> new_view_map with cur_view_map.
   */
  public void updateNewViewMap(){
    ArrayList<Territory> old_viewed_list = this.new_view_map.get("viewed");
    ArrayList<Territory> old_grey_list = this.new_view_map.get("grey");
    
    ArrayList<Territory> cur_viewed_list = viewMapHelper(this.player_terri_set);
    
    ArrayList<Territory> cur_grey_list = new ArrayList<Territory>();
    HashSet<String> cur_viewed_name = getNameList(cur_viewed_list);
    HashSet<String> old_viewed_name = getNameList(old_viewed_list);
    
    // Logical part
    for(Territory t : old_grey_list){
      cur_grey_list.add(t.deep_copy());
    }
    //Start Comparing and Updating!
    for(String s : cur_viewed_name){
      if(!old_viewed_name.contains(s)){
        // remove the territory(whose name is s) from cur_grey!
        int index = 0;
        // If there is no grey territory in the previous greylist.
        if(cur_grey_list.size() == 0){
          break;
        }
        for(;index < cur_grey_list.size(); index++){
          Territory t = cur_grey_list.get(index);
          if(t.getName().equals(s)){
            break;
          }
        }
        cur_grey_list.remove(index);
      }
    }
    
    for(String s : old_viewed_name){
      System.out.println(s);
      if(!cur_viewed_name.contains(s)){
        // Move the territory(whose name is s) from old_viewed to cur_grey!
        int index = 0;
        for(; index < old_viewed_list.size(); index++){
          Territory t = old_viewed_list.get(index);
          if(t.getName().equals(s)){
            break;
          }
        }
        Territory t = old_viewed_list.get(index).deep_copy();
        cur_grey_list.add(t);
        
      }
    }
    HashMap<String, ArrayList<Territory>> cur_new_map = new HashMap<String, ArrayList<Territory>>();    
    cur_new_map.put("viewed", cur_viewed_list);
    cur_new_map.put("grey", cur_grey_list);
    this.new_view_map = cur_new_map;
    }

  /**
   *viewMapHelper() is called by viewMapInitializer() and updateViewMap(). This method would deep copy the information from the last territories, and then traverse whole the neighbors from those territories. Finally, Add those neighbors into the return ArrayList.
   *@param  player_terri_set is the latest information of the teritories owned by the player. 
   *@return ArrayList<Territory> cur_viewed_list back to viewMapInitializer() or updateViewMap().
   */
  private ArrayList<Territory> viewMapHelper(HashSet<Territory> player_terri_set){
    ArrayList<Territory> cur_viewed_list = new ArrayList<Territory>();
    HashSet<String> cur_viewed_name = new HashSet<String>();

    // 1. Adding the Territories that the player owns
    for(Territory t : player_terri_set){
      cur_viewed_list.add(t);
      cur_viewed_name.add(t.getName());
    }

    // Adding the Neighbors of those Territories
    // 2. Deep copy
    ArrayList<Territory> current_viewed_territory_copy = new ArrayList<Territory>();
    for(Territory t : cur_viewed_list){
      Territory copy = t.deep_copy();
      current_viewed_territory_copy.add(copy);
    }
    
    // 3. Traverse Neighbors now
    for(Territory terri : current_viewed_territory_copy){
      for(Territory neigh : terri.getNeigh()){
        // current_viewed_territory doesn't have the neigh territory
        if(!cur_viewed_name.contains(neigh.getName())){
          cur_viewed_list.add(neigh);
          cur_viewed_name.add(neigh.getName());
        }
      }
    }

    return cur_viewed_list;
  }

}
