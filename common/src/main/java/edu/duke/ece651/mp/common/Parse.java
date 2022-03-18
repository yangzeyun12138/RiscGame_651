package edu.duke.ece651.mp.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Parse {
  /**readfile
   * @param: filename, which is the name of the text file that we want to access to. 
   * @return: arraylist of each line that read from the file
   */
  public ArrayList<String> readfile(String filename) throws IOException{
    String directory = System.getProperty("user.dir") + "/src/main/java/edu/duke/ece651/mp/";
    directory = directory + filename;
    File file = new File(directory);
    Scanner scan = new Scanner(file);
    String fileContent = "";
    ArrayList<String> lines = new ArrayList<String>();
    while(scan.hasNextLine()){
      lines.add(scan.nextLine());
    }
    return lines;    
  }
  /**
   *parseTerritory: generate hashset of territory with color and name
   *@calledby: parseTerritoryNeighbor() 
   *@param: lines is the whole the valid context for the whole text file of Territory(e.g. Territory3.txt)
   *@return: HashSet<territory>
   */
  public HashSet<Territory> parseTerritory(ArrayList<String> lines, int numPlayers) throws IOException{
    // Transform the HashSet<String> into HashSet<Territory>
    ArrayList<String> color_list = new ArrayList<String>(parseColor("Color.txt", numPlayers));
    HashSet<Territory> territorySet = new HashSet<Territory>();
    String separate= ":";
    int desiredLength = 2;

    // Start to scan through all lines
    for(int i = 0; i < lines.size(); i++){
      String line = lines.get(i);
      if(!line.contains(":")){
        throw new IllegalArgumentException("There is no content or There is no ':' in Territory3.txt\n");
      }
      String[] separateLine = line.split(separate);
      if(separateLine.length != desiredLength){
        throw new IllegalArgumentException("There is no territory name in front of the ':'\n");
      }
      String Color = color_list.get(i/(lines.size()/numPlayers));
      Territory currTerritory = new LandTerritory(separateLine[0], Color);
      territorySet.add(currTerritory);
    }
    return territorySet;
  }

  /**
   *parseTerritoryFile: read from file and store information as a map
   *@calledby: parseTerritoryNeighbor()
   *@param: lines is the whole the valid context for the whole text file of Territory(e.g. Territory3.txt)
   *@return: HashMap<String,ArrayList<String>>,of territory name
   */
  private HashMap<String,ArrayList<String>> parseTerritoryFile(String filename, ArrayList<String> lines) throws IOException{
    // Declaration for the return HashMap
    HashMap<String,ArrayList<String>> territoryMap = new HashMap<String,ArrayList<String>>();
    String separate = ":";
    String comma = ", ";

    // Use Nested For Loop to get the territoryMap from the neighborList, which consists of neighborArray 
    for(int i = 0; i < lines.size(); i++){
      String line = lines.get(i);
      String[] separateLine = line.split(separate);
      String[] neighborArray = separateLine[1].split(comma);
      // making neighborList by neighborArray
      ArrayList<String> neighborList = new ArrayList<String>();
      for(int j = 0 ; j < neighborArray.length; j++){
        neighborList.add(neighborArray[j]);
      }
      // put the (key, value) = (Territory's Name, neighborList) into the HashMap
      territoryMap.put(separateLine[0],neighborList);
    }
    return territoryMap;
  } 

  /**
   * find Territory(): find the territory in the territorySet according to the name
   *@calledby: parseTerritoryNeighbor()
   *@param:territorySet, which is whole the territorySet collected by parseTerritoryNeighbor()
   *@param: name, which is the name of the territory that we want to find
   *@return: Territory that has the name
   */
  private Territory findTerritory(HashSet<Territory> territorySet, String name){
    for (Territory t: territorySet){
      if (t.getName().equals(name)){
        return t;
      }
    }
    throw new IllegalArgumentException("The territory set do not contain this name: " + name);
  }
  /**
   *parseTerritoryNeighbor: add the neighbor territories to each territory
   *@param
   *@return: renewed territorySet with their territory neighbors
   */
   public HashSet<Territory> parseTerritoryNeighbor(String filename,int numPlayer) throws IOException{
    ArrayList<String> lines = readfile(filename);
    HashMap<String,ArrayList<String>> territoryMap = parseTerritoryFile(filename, lines);
    HashSet<Territory> territorySet = parseTerritory(lines, numPlayer);
    for (String key : territoryMap.keySet()){
      Territory currTerritory = findTerritory(territorySet,key);
      for (int j = 0; j<territoryMap.get(key).size();j++){
        String neighborName = territoryMap.get(key).get(j);
        Territory neighborTerri = findTerritory(territorySet,neighborName);
        currTerritory.addNeigh(neighborTerri);
      }
    }
    return territorySet;
      //return null;
    }
  
  /**
   *parseColor: parse the color acccording the number of players
   *@calledby: parseTerritory()
   *@param: filename, is the name of the file that we want to access to.
   *@return: HashSet of color
   */
  public ArrayList<String> parseColor(String filename, int numPlayers) throws IOException{
    // return type change from HashSet to ArrayList
    ArrayList<String> lines = readfile(filename);
    ArrayList<String> colorList = new ArrayList<String>();
    for(int i = 0; i < numPlayers; i++){
      colorList.add(lines.get(i));
      }
      return colorList;
  }

  
}
