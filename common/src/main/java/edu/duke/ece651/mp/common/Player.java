package edu.duke.ece651.mp.common;

import java.io.*;
import java.util.*;

public class Player implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    public String color;
    private int tech_level;
    private int food;
    public HashSet<Territory> player_terri_set;


    


  public Player(String color, HashSet<Territory> player_terri_set) {
        this.color = color;
        this.player_terri_set = player_terri_set;
        this.tech_level = 1; // set initial technology level to 1
        this.food = 140;// set initial food to 140
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



}
