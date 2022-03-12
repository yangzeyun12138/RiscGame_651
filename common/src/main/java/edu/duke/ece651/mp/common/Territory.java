package edu.duke.ece651.mp.common;

import java.util.ArrayList;

public interface Territory {

  @Override
  public boolean equals(Object o);
  

  public String getName();
  public String getColor();
  public void addNeigh(Territory neigh);
  public ArrayList<Territory> getNeigh();

  public boolean addUnit(Unit u);
  public int countUnit();
  public void setUnit(int numUnit);
  public boolean loseUnit();
  
}
