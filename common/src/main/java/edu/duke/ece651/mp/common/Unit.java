package edu.duke.ece651.mp.common;

import java.util.ArrayList;

public interface Unit {
  public void UpgradeBasicUnit(int newLevel);
  public int getLevel();
  public int getBonus();
  public int getCost();
}
