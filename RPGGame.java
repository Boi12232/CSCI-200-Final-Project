import java.util.*;
import java.io.*;

public class RPGGame 
{
  
  private Graph<Location> worldGraph;
  private Map<String, Location> locations = new HashMap<>();
  private Location currentLocation;
  private Scanner scanner = new Scanner(System.in);
  
  
  public RPGGame() 
  {
    worldGraph = new Graph<>(false);
    initializeWorldFromFile("locations.txt");
  }
  
  /**
   * Method to initialize the world graph
   */
  private void initializeWorldFromFile(String filename) 
  {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) 
      {
        String[] data = line.split(",");
        
        //Skip lines that don't have enough data
        if (data.length < 5) continue;
        
        //Parse the Location attributes
        String name = data[0].trim();
        int encounterChance = Integer.parseInt(data[1].trim());
        int fleeInfluence = Integer.parseInt(data[2].trim());
        String description = data[3].trim();
        
        //Create the Location object
        Location location = new Location(name, encounterChance, fleeInfluence, description);
        
        //Add to locations map
        locations.put(name, location);
        
        //Add to the graph
        worldGraph.addVertex(location);
        
        //Add edges based on neighboring locations
        for (int i = 4; i < data.length; i++) {
          String neighborName = data[i].trim();
          Location neighbor = locations.get(neighborName);
          
          //If the neighbor isn't found in the map yet, continue
          if (neighbor == null) continue;
          
          //Calculate the difficulty (this can be adjusted based on your game's needs)
          double difficulty = calculateDifficulty(location, neighbor);
          
          //Add an edge from this location to the neighbor
          worldGraph.addEdge(location, neighbor, difficulty);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading the file: " + e.getMessage());
    }
  }
  
  //Get neighbors directly from the graph
  private List<Location> getNeighborLocations(Location loc) 
  {
    List<Location> result = new ArrayList<>();
    for (Graph<Location>.Edge e : worldGraph.getNeighbors(loc)) 
    {
      result.add(e.destination);
    }
    return result;
  }
  
  private double calculateDifficulty(Location from, Location to) 
  {
    //For now, return a random difficulty between 0 and 10
    return Math.random() * 10;
  }

  /**
   * Game loop
   */
  public void startGame() 
  {
    while (true) 
    {
      System.out.println("\nYou are at: " + currentLocation.getName());
      System.out.println("Possible paths:");
      
      List<Location> neighbors = getNeighborLocations(currentLocation);
      
      for (int i = 0; i < neighbors.size(); i++) 
      {
        System.out.println((i + 1) + ". " + neighbors.get(i).getName());
      }
      
      System.out.print("Choose destination (number): ");
      int choice = scanner.nextInt();
      
      if (choice < 1 || choice > neighbors.size()) 
      {
        System.out.println("Invalid choice.");
        continue;
      }
      
      currentLocation = neighbors.get(choice - 1);
      System.out.println("Traveling to " + currentLocation.getName() + "...");
    }
  }
}
