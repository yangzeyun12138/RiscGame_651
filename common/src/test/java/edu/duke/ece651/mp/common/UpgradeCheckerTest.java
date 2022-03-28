package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;

public class UpgradeCheckerTest {
  @Test
  public void test_AllRules() {
    HashSet<Territory> test_territory = new HashSet<>();
    Territory lt1 = new LandTerritory("Hogwarts", "Red", 2);
    test_territory.add(lt1);
    Player player = new Player("Red", test_territory);
    player.addFood(50);
    UpgradeChecker level = new LevelRuleChecker(null);
    UpgradeChecker tech = new TechRuleChecker(level);
    UpgradeChecker downgrade = new NoDowngradeRuleChecker(tech);
    UpgradeChecker cost = new UpgradeCostRuleChecker(downgrade);
  }

}
