import java.util.*;
import java.io.*;

public class RPGGame 
{
  //Attribues
  private Graph<Location> worldGraph;
  private Map<String, Location> locations = new HashMap<>();
  private Map<String,Location> shelterLocations = new HashMap<>();
  private Map<Location, List<String>> pendingNeighbors = new HashMap<>();
  private Location currentLocation;
  private Scanner scanner = new Scanner(System.in);
  //private ErrorMessages errorMessages = new ErrorMessages();
  
  public RPGGame() 
  {
    //creates an undirected graph for our program
    worldGraph = new Graph<>(false);
    initializeWorldFromFile("locations.txt");
    initializeEnemiesFromFile("enemies.txt");
    //location starts at the Villiage
    currentLocation = locations.get("Village");
  }
  
  /**
   * This method takes in a file containing all the possible Locations, parses it, and initializes it to the worldGraph
   * @param filename represents the file name holding the Locations and its properties
   * 
   */
  private void initializeWorldFromFile(String filename) 
  {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
    {
      String line;
      // -------- PASS 1: create locations --------
      // 35-42 for every line, splits the file by commas and removes possible whitespace
      while ((line = br.readLine()) != null) 
      {
        String[] data = line.split(",");
        if (data.length < 5) continue;
        String name = data[0].trim();
        int encounterChance = Integer.parseInt(data[1].trim());
        int fleeInfluence = Integer.parseInt(data[2].trim());
        String description = data[3].trim();
        
        //creates a new location with the first 3 indicies of the line and adds it to the worldGraph and locations
        Location loc = new Location(name, encounterChance, fleeInfluence, description);
        
        locations.put(name, loc); //holds all locations that we can reference to
        worldGraph.addVertex(loc);
        
        
        //For the next indicies of the line, starting at index 4, the neighbors of the Locations are put in its own array and mapped according to the current Location
        List<String> neighborNames = new ArrayList<>();
        for (int i = 4; i < data.length; i++) 
        {
          neighborNames.add(data[i].trim());
        }
        pendingNeighbors.put(loc, neighborNames); //by the end of it, pending neighbors would have a map of every location and where it connects to 
      }
      //System.out.println(pendingNeighbors);
    } 
    catch (IOException e) 
    {
      System.out.println("Error reading the file: " + e.getMessage());
    }
    
    // -------- PASS 2: connect edges --------
    
    //for each entry in pending neighbor, get its key (The location its connected to)
    //and for each of the key's values (being its neighbors), get its neighbors and make and edge for the two with a calculated difficulty
    for (Map.Entry<Location, List<String>> entry : pendingNeighbors.entrySet())
    {
      Location loc = entry.getKey();
      for (String neighborName : entry.getValue()) 
      {
        Location neighbor = locations.get(neighborName);
        if (neighbor != null) 
        {
          double difficulty = calculateDifficulty(loc, neighbor);
          worldGraph.addEdge(loc, neighbor, difficulty);
        }
      }
    }
  }
  
  /**
   * This method takes a location, and return all its neighbors through its edges.
   * @param loc represents the location we want to find the neighbors of
   * @return List<Location> : a list of all connected locations.
   */
  private List<Location> getNeighborLocations(Location loc) 
  {
    List<Location> result = new ArrayList<>();
    //gets a set of edges, and for each edge, add the Locations its connected to into result and return
    for (Graph<Location>.Edge e : worldGraph.getNeighbors(loc)) 
    {
      result.add(e.destination);
    }
    return result;
  }
  
  /**
   * Method to calculate the difficulty between two nodes to assign as edge weight
   * @param from represents the start of the edge
   * @param to represents the biome at the end of the edge
   * 
   */
  private double calculateDifficulty(Location from, Location to) 
  {
    //For now, return a random difficulty between 0 and 10
    return Math.floor(Math.random() * 10);
  }
  
  
  /**
   * Parses through a file of enemies and initializes them in a cooresponding Location
   * @name represents the name of the file containing the enemies
   */
  private void initializeEnemiesFromFile(String name){
    try{
      File file = new File(name);
      Scanner enemiesFile = new Scanner(file);
      
      while(enemiesFile.hasNextLine()){
        String line = enemiesFile.nextLine();
        String[] info = line.split(",");
        
        Enemy createEnemy = new Enemy(info[0].toString().trim(),Integer.parseInt(info[1].trim()),Integer.parseInt(info[2].trim()),Integer.parseInt(info[3].trim()));
        
        //System.out.print(createEnemy);
        
        for(int i = 4; i < info.length; i++){
          Location biome = locations.get(info[i].trim());
          biome.enemies.add(createEnemy);
          //System.out.print(biome.enemies);
        }
        
        
      }
      enemiesFile.close();
    }
    catch(FileNotFoundException e){
      System.out.print("invalid file");
    }
  }
  
  /**
   * This method allows the user to add a new location node into the Graph
   * @param name is a string that represents the name of the location that will be created (maybe types? but idk)
   */
  private void addLocation(String name)
  {
    
    if(shelterLocations.size() < 2){
      if(locations.containsKey(name)){
        System.out.println("This location already exists! Please Rename");
        name = scanner.nextLine();
      }
      
      Location loc = new Location(name);
      locations.put(name, loc);
      shelterLocations.put(name,loc);
      pendingNeighbors.get(currentLocation).add(name); //gets the array in current location and adds newly made shelter as a neighbor of currentLocation
      worldGraph.addEdge(loc,currentLocation,1);
    }
    else
    {
      System.out.println("You've reached the max number of locations!");
      
    }
  }
  
  /**
   * This method removes a location from the graph and all its references
   * @param name represents the location desired to be removed
   * 
   */
  private void removeLocation(String name)
  {
    Location removedLocation = locations.get(name); //references to removed location object
    System.out.println(removedLocation.getName());
    if(removedLocation == currentLocation){
      List<Location> neighborsOfRemoved = getNeighborLocations(removedLocation);
      currentLocation = neighborsOfRemoved.get(1);
    }
    
    if(shelterLocations.containsKey(name))
    {
      shelterLocations.remove(name);
    }
    
    locations.remove(name);
    pendingNeighbors.remove(name);
    worldGraph.removeVertex(locations.get(name));
    
    
    for(Map.Entry<Location,List<String>> setOfKeysAndNeighbors: pendingNeighbors.entrySet()){ //creates a set of pendingNeighbors map. For each element in the set, grab the neighbors and remove reference to removedLocation
      List<String> neighborsList = setOfKeysAndNeighbors.getValue();
      Location key = setOfKeysAndNeighbors.getKey();
      
      if(neighborsList.contains(name))
      { 
        //System.out.println("1. " + neighborsList);
        worldGraph.removeEdge(key, locations.get(name));
        neighborsList.remove(name);
        //System.out.println("2. " +neighborsList);
      }
    }
    
    //System.out.println("3. " + pendingNeighbors);
  }
  
  /**
   * This method switches the current location to a neighboring location based on the user's choice.
   */
  private void switchingBiomes(){
    
    System.out.println("Possible paths:");
    
    List<Location> neighbors = getNeighborLocations(currentLocation);
    
    for (int i = 0; i < neighbors.size(); i++) 
    {
      System.out.println((i + 1) + ". " + neighbors.get(i).getName());
    }
    
    System.out.print("Choose destination (number): ");
    
    int choice = 0;
    
    while(choice < 1 || choice > neighbors.size()) 
    {
      System.out.println("Invalid choice.");
      choice = scanner.nextInt();
      
    }
    
    currentLocation = neighbors.get(choice - 1);
    System.out.println("Traveling to " + currentLocation.getName() + "...");
    
  }
  
  
  
  /**
   * Game loop
   * Starts the game loop, always starting user at the "village" biome and providing the possible paths of the biome based on its avaliable edges.
   */
  public void startGame()
  {
    Stats playerStats = new Stats(10, 30, 3, 3, 50);
    MovementEvents playerEvents = new MovementEvents(playerStats,currentLocation);
    while (true) 
    {
      System.out.println("\nYou are at: " + currentLocation.getName() + ". Go Off and Explore!!!");
      
      String direction = scanner.next().toLowerCase();
      
      //System.out.println(direction);
      
      if(direction.equals("admin"))
      {
        System.out.println("What admin command do you want to execute?");
        String command = scanner.next();
        
        switch(command){
          
          case "printGraph": //prints worldGraph
            worldGraph.printGraph();
            break;
            
          case "removeLocation": //removes specified location
            System.out.println("Which location do you want to remove?");
            String userInput = "";
            while(!locations.containsKey(userInput)){
              userInput = scanner.next();
            }
            removeLocation(userInput);
            
            break;
            
          case "addLocation": //adds location at current node
            System.out.println("What will be the new location name?");
            addLocation(scanner.next());
            break;
            
          case "teleport":
            currentLocation = locations.get(scanner.next());
            break;
            
          case "getLocations": 
            System.out.println(locations);
            break;
            
          case "getNeighbors": 
            System.out.println(getNeighborLocations(currentLocation));
            
          default: 
            System.out.println("invalid input. No such command exists");
            break;
        }
      }
      else
      {
        if(direction.equals("r") || direction.equals("right")){
          System.out.println("You turned right!");
        }
        else if(direction.equals("l") || direction.equals("left")){
          System.out.println("You turned left!");
        }
        else{
          System.out.println("Invalid input");
        }
        
        
        
        playerStats.toString();
        
        //switchingBiomes();
        
        
      }
    }
  }
}
