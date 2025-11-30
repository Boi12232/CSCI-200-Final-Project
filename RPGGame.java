import java.util.*;
import java.io.*;

public class RPGGame 
{
  private Graph<Location> worldGraph;
  private Map<String, Location> locations = new HashMap<>();
  private Map<Location, List<String>> pendingNeighbors = new HashMap<>();
  private Location currentLocation;
  private Scanner scanner = new Scanner(System.in);
  private ErrorMessages errorMessages = new ErrorMessages();
  
  public RPGGame() 
  {
    worldGraph = new Graph<>(false);
    initializeWorldFromFile("locations.txt");
    currentLocation = locations.get("Village");
  }
  
  /**
   * Method to initialize the world graph
   */
  private void initializeWorldFromFile(String filename) 
  {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
    {
      String line;
      //PASS 1: Create all locations
      while ((line = br.readLine()) != null) 
      {
        String[] data = line.split(",");
        
        if (data.length < 6) 
          continue;
        
        String name = data[0].trim();
        Biome biome = Biome.valueOf(data[1].trim().toUpperCase());
        int encounterChance = Integer.parseInt(data[2].trim());
        int fleeInfluence = Integer.parseInt(data[3].trim());
        String description = data[4].trim();
        
        Location loc = new Location(name, encounterChance, fleeInfluence, description, biome);
        locations.put(name, loc);
        worldGraph.addVertex(loc);
        
        //Store neighbor names for pass 2
        List<String> neighborNames = new ArrayList<>();
        for (int i = 5; i < data.length; i++)
          neighborNames.add(data[i].trim());
  
        pendingNeighbors.put(loc, neighborNames);
      }
    } 
    catch (IOException e) 
    {
      System.out.println("Error reading file: " + e.getMessage());
    }
    
    //PASS 2: Connect edges with biome adjacency validation
    for (Map.Entry<Location, List<String>> entry : pendingNeighbors.entrySet()) 
    {
      Location loc = entry.getKey();
      for (String neighborName : entry.getValue()) 
      {
        Location neighbor = locations.get(neighborName);
        if (neighbor == null) 
          continue;
        
        Biome a = loc.getBiome();
        Biome b = neighbor.getBiome();
        
        //----BIOME VALIDATION----
        if (!a.canNeighbor(b)) {
          System.out.println("WARNING: Invalid biome adjacency: "
                               + loc.getName() + "(" + a + ") → "
                               + neighbor.getName() + "(" + b + ")");
          
          continue; //skip invalid connection
        }
        double difficulty = calculateDifficulty(loc, neighbor);
        worldGraph.addEdge(loc, neighbor, difficulty);
      }
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
  
  //Method to calculate the difficulty between two nodes to assign as edge weight
  private double calculateDifficulty(Location from, Location to) 
  {
    //For now, return a random difficulty between 0 and 10
    return Math.random() * 10;
  }
  
  /**
   * Game loop
   */
  public void startGame() throws IOException
  {
    List<String> messages = errorMessages.loadMessages();
    int invalidChoiceCount = 0;
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
        System.out.println(errorMessages.getRandomMessage(messages));
        invalidChoiceCount++;
        continue;
      }
      
      currentLocation = neighbors.get(choice - 1);
      System.out.println("Traveling to " + currentLocation.getName() + "...");
    }
  }
}
