package edu.duke.ece651.mp.common;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

public class Player implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    public String color;
    public HashSet<Territory> player_terri_set;
    private int tech_level;
    private int food;

    public Player(String color, HashSet<Territory> player_terri_set) {
        this.color = color;
        this.player_terri_set = player_terri_set;
        this.tech_level = 1;
        this.food = 140;
    }

    /**
     * @return a String that display the player color and their territories info
     */

    public String toString() {
        String res = new String(color + " player:\n");
        res += "-----------\n";
        for (Territory t: player_terri_set) {
            String unit_info = Integer.toString(t.countUnit());
            res += "  " + unit_info + " units in " + t.getName() + " (next to: ";
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

  public int getTechLevel(){
    return this.tech_level;
  }
  //////////////////////////////////////////////
  public void upgradeTechLevel(){
    LevelInfo info = new LevelInfo();
    int totalCost = (info.getTechCost(this.tech_level + 1) - info.getTechCost(this.tech_level));
    this.tech_level += 1;
    if(this.food < totalCost){
      throw new IllegalArgumentException("The number of food is lower than the totalCost when upgrading tech level!\n");
    }
    this.food -= totalCost;
  }

  public int getFood(){
    return this.food;
  }

  public void costFood(int expense){
    if(expense < 0){
      throw new IllegalArgumentException("Cannot reduce negative food!");
    }
    this.food -= expense;
  }

  public void addFood(int add){
    if(add < 0){
      throw new IllegalArgumentException("Cannot add negative food!");
    }
    this.food += add;
  }
}
