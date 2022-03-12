package edu.duke.ece651.mp.common;


public class BasicMapDisplay implements MapDisplay{
    private BasicMap basicMap;

    public BasicMapDisplay(BasicMap basicMap) {
        this.basicMap = basicMap;
    }

    /**
     * @return String to display the map
     */
    @Override
    public String display() {
        String res = new String();
        for (Player p : basicMap.get_player_list()) {
            res += p.toString();
        }
        return res;
    }
}
