package edu.duke.ece651.mp.common;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

public class Player implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    public String color;
    public HashSet<Territory> player_terri_set;

    public Player(String color, HashSet<Territory> player_terri_set) {
        this.color = color;
        this.player_terri_set = player_terri_set;
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

}
