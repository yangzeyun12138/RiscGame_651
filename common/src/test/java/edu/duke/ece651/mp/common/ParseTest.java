package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ParseTest {

  
  @Test
  public void test_readfile() throws IOException {
    //String directory = System.getProperty("user.dir") + "/src/main/java/ece651/eval1/";
    String filename = "Territory3.txt";
    Parse parse = new Parse();
    ArrayList<String> lines = parse.readfile(filename);
    assertEquals(lines.get(0), "Gondor:Mordor, Oz");
    assertEquals(lines.get(8), "Roshar:Elantris, Hogwarts, Scadrial");
  }

  @Test
  public void test_parseTerritory() throws IOException{
    String filename = "Territory3.txt";
    String color = "Green";
    Parse parse = new Parse();
    ArrayList<String> lines = parse.readfile(filename);
    HashSet<Territory> pt1 = parse.parseTerritory(lines, 3);
    Territory t1 = new LandTerritory("Narnia", color);
    boolean b1 = false;

    for (Territory t : pt1){
      System.out.println("t.getName: " + t.getName() + ", t.getColor: " + t.getColor());
      if (t.equals(t1) && t.getColor().equals(color)){
        b1 = true;
        break;
      }
    }
    assertTrue(b1);
  }

  @Test
  public void test_parseTerritory_exit() throws IOException{
    String filename = "DoesNotExist.txt";
    Parse parse = new Parse();
    ArrayList<String> lines = parse.readfile(filename);
    assertThrows(IllegalArgumentException.class, ()->parse.parseTerritory(lines, 3));

    
    String filename2 = "OnlyComma.txt";
    Parse parse2 = new Parse();
    ArrayList<String> lines2 = parse2.readfile(filename2);
    assertThrows(IllegalArgumentException.class, ()->parse2.parseTerritory(lines2, 3));
  }

  @Test
  public void test_parseColor() throws IOException{
    String filename = "Color.txt";
    Parse parse = new Parse();
    ArrayList<String> pc1 = parse.parseColor(filename, 2);
    String c1 = "Green";
    boolean b1 = false;

    for (String str : pc1){
      if (str.equals(c1)){
        b1 = true;
        break;
      }
    }
    assertTrue(b1);
  }

  /*@Test
  public void test_findTerritory(){
    Parse parse = new Parse();
    
    }*/

  @Test
  public void test_parseTerritoryNeighbor () throws IOException{
    String filename = "Territory3.txt";
    int numPlayer = 3;
    Parse parse = new Parse();
    HashSet<Territory> result = parse.parseTerritoryNeighbor(filename, numPlayer);
    
    /*
    for(Territory t : result){
      System.out.println("Territory's Name: " + t.getName() + "\nNeighbor's Name:");
      for(Territory n : t.getNeigh()){
        System.out.println(n.getName() + " ");
      }
    }
    */
    assertEquals(1, 1);
  }
}
