package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.*;

public class UpgradeCheckerTest {
  
  @Test
  public void test_AllRules() {
    UpgradeChecker tech = new TechRuleChecker(null);
    UpgradeChecker level = new LevelRuleChecker(tech);
    UpgradeChecker downgrade = new NoDowngradeRuleChecker(level);
    UpgradeChecker cost = new UpgradeCostRuleChecker(downgrade);
    UpgradeChecker name = new  NameUpgradeRuleChecker(cost);
    
    HashSet<Territory> test_territory = new HashSet<>();
    Territory lt1 = new LandTerritory("Hogwarts", "Red", 2);
    Territory lt2 = new LandTerritory("Oz", "Red", 3);
    test_territory.add(lt1);
    test_territory.add(lt2);
    lt1.setBasicUnit(4);
    Player player = new Player("Red", test_territory);
    player.addFood(1000);
    player.upgradeTechLevel(); // Tech Level: 0 -> 1

    //Name Check
    assertEquals("Red player. Invalid Upgrade: The name of the source territory is invalid\n", name.checkUpgrade(player, "PPPPPP", 4, 0, 4));
    
    // Tech Check
    assertEquals("Red player. Invalid Upgrade: The technology level of player should be higher than the current level!\n", name.checkUpgrade(player, "Hogwarts", 4, 0, 4));

    // Level Check
    assertEquals("Red player. Invalid Upgrade: The number of Unit on the specific territory is not enough!\n", name.checkUpgrade(player, "Hogwarts", 4, 1, 2));

    lt1.upgradeUnit(3, 0, 1);

    // Downgrade Check
    assertEquals("Red player. Invalid Upgrade: The new level should be higher than the current level!\n", name.checkUpgrade(player, "Hogwarts", 2, 1, 0));

    player.costFood(1089);

    // Cost Check
    assertEquals("Red player. Invalid Upgrade: The player has insufficient food for Upgrade!\n", name.checkUpgrade(player, "Hogwarts", 1, 0, 1));
    
    player.addFood(2000);
    assertNull(name.checkUpgrade(player, "Hogwarts", 1, 0, 1));

    
    //player.upgradeTechLevel();
    
    
    
  }

}
