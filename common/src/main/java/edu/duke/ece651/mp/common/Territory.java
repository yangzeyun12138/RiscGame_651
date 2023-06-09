package edu.duke.ece651.mp.common;

import java.util.ArrayList;

public interface Territory {

  @Override
  public boolean equals(Object o);
  

  public String getName();
  public String getColor();
  public void addNeigh(Territory neigh);
  public ArrayList<Territory> getNeigh();

  public boolean addBasicUnit(int numUnit);
  public boolean addUnit(Unit u);
  public int countUnit();
  public int countLevelUnit(int level);
  public void sortUnit();
  public boolean upgradeUnit(int numUnit, int level, int newLevel);
  public void setBasicUnit(int numUnit);
  public boolean loseUnit();
  public boolean loseUnits(int numUnit);
  public void changeColor(String Color);
  public int getSize();
  public void loseUnitsAt(int idx);
  public Unit getUnitsFromIndex(int index);

  public boolean loseUnit(int level);
  public boolean loseUnits(int numUnit, int level);
  public int countLevel(int level);
  public Territory deep_copy();

  public void addSpy(String Color);
  public boolean loseSpy(String Color);
  public ArrayList<Spy> getSpyList();
  public void resetAllSpies();
  public int countSpy(String color);
  public void addInitSpy(String Color);

  int getBeGuarded();

  int getBeCloakedNum();

  void setBeGuarded(int beGuarded);

  void setBeCloakedNum(int beCloakedNum);
}
