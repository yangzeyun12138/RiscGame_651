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
  public void setBasicUnit(int numUnit);
  public boolean loseUnit();
  public boolean loseUnit(int numUnit);
  
}
