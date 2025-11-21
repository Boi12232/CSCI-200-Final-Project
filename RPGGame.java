import java.util.*;

public class RPGGame {
  
  private Graph<Location> worldGraph;
  private Map<String, Location> locations = new HashMap<>();
  private Location currentLocation;
  private Scanner scanner = new Scanner(System.in);
  
  public RPGGame() {
    worldGraph = new Graph<>(false);
    initializeWorld();
  }
  
  /**
   * Method to initialize the world graph
   */
  private void initializeWorld() {
    //Change these into being read from a file later for ease of change
    Location forest   = new Location("Forest", 30, 5, "A lush forest full of trees.");
    Location desert   = new Location("Desert", 50, 10, "A scorching desert.");
    Location tundra   = new Location("Tundra", 40, 8, "A frozen wasteland.");
    Location coast    = new Location("Coast", 20, 2, "A windy coastline.");
    Location mountains = new Location("Mountains", 60, 15, "Dangerous rocky peaks.");
    
    //Store in Map
    locations.put(forest.getName(), forest);
    locations.put(desert.getName(), desert);
    locations.put(tundra.getName(), tundra);
    locations.put(coast.getName(), coast);
    locations.put(mountains.getName(), mountains);
    
    //Add nodes for each
    for (Location loc : locations.values()) {
      worldGraph.addVertex(loc);
    }
    
    //Add edges
    worldGraph.addEdge(forest, desert, 4.0);
    worldGraph.addEdge(forest, coast, 2.0);
    worldGraph.addEdge(desert, coast, 3.5);
    worldGraph.addEdge(forest, mountains, 6.0);
    worldGraph.addEdge(tundra, mountains, 5.0);
    
    //Start location
    currentLocation = forest;
  }
  
  //Get neighbors directly from the graph
  private List<Location> getNeighborLocations(Location loc) {
    List<Location> result = new ArrayList<>();
    for (Graph<Location>.Edge e : worldGraph.getNeighbors(loc)) {
      result.add(e.destination);
    }
    return result;
  }
  
  /**
   * Game loop
   */
  public void startGame() {
    while (true) {
      System.out.println("\nYou are at: " + currentLocation.getName());
      System.out.println("Possible paths:");
      
      List<Location> neighbors = getNeighborLocations(currentLocation);
      
      for (int i = 0; i < neighbors.size(); i++) {
        System.out.println((i + 1) + ". " + neighbors.get(i).getName());
      }
      
      System.out.print("Choose destination (number): ");
      int choice = scanner.nextInt();
      
      if (choice < 1 || choice > neighbors.size()) {
        System.out.println("Invalid choice.");
        continue;
      }
      
      currentLocation = neighbors.get(choice - 1);
      System.out.println("Traveling to " + currentLocation.getName() + "...");
    }
  }
}
