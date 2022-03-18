package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class AttackCheckerTest {
   @Test
  public void test_AllRules(){
    HashSet<Territory> test_territory = new HashSet<>();
    Territory lt3 = new LandTerritory("Hogwarts", "Red");
    Territory lt2 = new LandTerritory("Modor", "Red");
    Territory lt1 = new LandTerritory("Gondor", "Red");
    Territory lt4 = new LandTerritory("Oz", "Green");
    lt1.addNeigh(lt2);
    lt1.addNeigh(lt4);
    lt2.addNeigh(lt1);
    lt2.addNeigh(lt3);
    lt2.addNeigh(lt4);
    lt3.addNeigh(lt2);
    lt4.addNeigh(lt1);
    lt4.addNeigh(lt2);
    lt1.setBasicUnit(3);
    lt2.setBasicUnit(4);
    lt3.setBasicUnit(5);
    lt4.setBasicUnit(10);
    test_territory.add(lt1);
    test_territory.add(lt2);
    test_territory.add(lt3);
    Player player = new Player("Red", test_territory);
    String src = "Gondor";
    String dest = "Oz";
    int numUnit = 3;
    AttackChecker Name = new NameAttackRuleChecker(null);
    AttackChecker Adjacency = new AdjacencyRuleChecker(null);
    AttackChecker Faction = new FactionRuleChecker(Adjacency);
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(player);
    
    assertEquals("Red player. The Attack is Invalid: The name of the source territory or the destination territory is invalid\n", Name.checkAttack(player, "DDD", dest, numUnit, players));
    
    String result = Faction.checkAttack(player, src, dest, numUnit,players);
    assertNull(result);

    numUnit = 4;
    dest = "Hogwarts";
    result = Faction.checkAttack(player, src, dest, numUnit,players);
    assertEquals("Red player. Invalid Attack: The destination territory and the source territory are in the same faction!\n", result); 

    src = "Hogwarts";
    dest = "Oz";
    result = Faction.checkAttack(player, src, dest, numUnit,players);
    assertEquals("Red player. Invalid Attack: The destination territory is not adjacent to the source territory!\n", result);
   }

}
