package edu.duke.ece651.mp.common;

import java.util.HashSet;
import java.util.Iterator;

public class Player implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String color;
    public HashSet<Territory> player_terri_set;

    public Player(String color, HashSet<Territory> player_terri_set) {
        this.color = color;
        this.player_terri_set = player_terri_set;
    }

    /**
     * @return a String that display the player color and their territories info
     */
    @Override
    public String toString() {
        String res = new String(color + " player:\n");
        res += "-----------\n";
        for (Territory t: player_terri_set) {
            res += "  " + "10" + " units in " + t.getName() + " (next to: ";
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
}
