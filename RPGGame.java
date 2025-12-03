import java.io.*;
import java.util.*;

public class RPGGame 
{
  private final Graph<BiomeRegion> worldGraph = new Graph<>(false);
  private final Map<String, Biome> biomes = new HashMap<>();
  private final Map<String, BiomeRegion> regions = new HashMap<>();
  private final Map<String, List<String>> pendingConnections = new HashMap<>();
  private final ErrorMessages errorMessages = new ErrorMessages();
  private final Random rnd = new Random();
  private final Scanner scanner = new Scanner(System.in);
  private List<Enemy> enemies = new ArrayList<>();
  private List<Item> items = new ArrayList<>();
  private List<String> messages;
  private BiomeRegion currentLocation;
  private Inventory inventory = new Inventory();
  private Player player;
  private int playerHP;
  
  public RPGGame(String biomeFile, String enemyFile, String itemFile) 
  {
    initializeWorldFromFile(biomeFile, enemyFile, itemFile);
    
    System.out.println("Enter name: ");
    String name = scanner.nextLine();
    
    //Create Player Object and initialize HP
    this.player = new Player(name, 100, 5, 5);
    this.playerHP = player.getHP();
    
    //Start at Village
    currentLocation = regions.get("Village");
    //Fallback if Village breaks
    if (currentLocation == null) 
    {
      System.out.println("Village region not found! Starting at first available region.");
      currentLocation = regions.values().stream().findFirst().orElse(null);
    }
    
    //Load invalid input messages
    try 
    {
      messages = ErrorMessages.load();
    } 
    catch (IOException e) 
    {
      System.out.println("Could not load error messages: " + e.getMessage());
      messages = new ArrayList<>();
      messages.add("Invalid input. Try again!"); //Fallback
    }
  }
  
  private void initializeWorldFromFile(String biomeFile, String enemyFile, String itemFile) 
  {
    FileLoader.WorldData data;
    
    try 
    {
      data = FileLoader.loadWorld(biomeFile);
    } 
    catch (IOException e) 
    {
      System.out.println("Error reading world file: " + e.getMessage());
      return;
    }
    
    //Load data into RPGGame
    this.biomes.putAll(data.biomes);
    this.regions.putAll(data.regions);
    this.pendingConnections.putAll(data.pendingConnections);
    
    //Add vertices to world graph
    for (BiomeRegion region : regions.values()) {
      worldGraph.addVertex(region);
    }
    
    //Load enemies
    try 
    {
      FileLoader.loadEnemies(enemyFile, biomes, enemies);
    } 
    catch (IOException e) 
    {
      System.out.println("Error loading enemies: " + e.getMessage());
    }
    
    //Load items
    try 
    {
      FileLoader.loadItems(itemFile, items);
    } 
    catch (IOException e) 
    {
      System.out.println("Error loading items: " + e.getMessage());
    }
    
    //Connect internal regions within each biome
    connectInternalRegions();
    
    //Connect external outskirts based on pendingConnections
    connectExternalOutskirts();
  }
  
  private void connectInternalRegions() 
  {
    for (Biome biome : biomes.values()) 
    {
      List<BiomeRegion> list = biome.getRegions();
      if (list.size() <= 1) continue;
      
      //Chain connection
      for (int i = 0; i < list.size() - 1; i++) 
      {
        BiomeRegion a = list.get(i);
        BiomeRegion b = list.get(i + 1);
        if (!worldGraph.edgeExists(a, b)) 
        {
          worldGraph.addEdge(a, b, calculateWeight(biome));
        }
      }
      
      //Add random extra edges
      int extra = Math.max(0, list.size() / 2);
      for (int i = 0; i < extra; i++) 
      {
        BiomeRegion a = list.get(rnd.nextInt(list.size()));
        BiomeRegion b = list.get(rnd.nextInt(list.size()));
        if (!a.equals(b) && !worldGraph.edgeExists(a, b)) 
        {
          worldGraph.addEdge(a, b, calculateWeight(biome));
        }
      }
    }
  }
  
  private void connectExternalOutskirts() 
  {
    for (Map.Entry<String, List<String>> entry : pendingConnections.entrySet()) 
    {
      BiomeRegion from = regions.get(entry.getKey());
      if (from == null) continue;
      
      for (String toName : entry.getValue()) 
      {
        BiomeRegion to = regions.get(toName);
        if (to == null) continue;
        
        if (!worldGraph.edgeExists(from, to)) 
        {
          double weight = calculateWeight(from.getBiome(), to.getBiome());
          worldGraph.addEdge(from, to, weight);
        }
      }
    }
  }
  
  private double calculateWeight(Biome biome) 
  {
    double base = biome.getEncounterChance();
    return Math.min(9.9, (base / 100.0) * 8.0 + rnd.nextDouble() * 2.0);
  }
  
  private double calculateWeight(Biome a, Biome b) 
  {
    double avg = (a.getEncounterChance() + b.getEncounterChance()) / 2.0;
    return Math.min(9.9, (avg / 100.0) * 8.0 + rnd.nextDouble() * 2.0);
  }
  
  private List<BiomeRegion> getNeighbors(BiomeRegion loc) 
  {
    List<BiomeRegion> result = new ArrayList<>();
    for (Graph<BiomeRegion>.Edge e : worldGraph.getNeighbors(loc)) 
    {
      result.add(e.destination);
    }
    return result;
  }
  
  private int beginCombat() 
  {
    List<Enemy> biomeEnemies = currentLocation.getBiome().getEnemies();
    if (biomeEnemies.isEmpty()) 
    {
      System.out.println("No enemies found in this region.");
      return playerHP;
    }
    
    Enemy enemy = biomeEnemies.get(rnd.nextInt(biomeEnemies.size()));
    System.out.println("\nA wild " + enemy.getName() + " appears!");
    
    Combat combat = new Combat(player, enemy, currentLocation, inventory);
    playerHP = combat.startBattle();
    
    if (playerHP <= 0) 
    {
      System.out.println("\nGame Over! " + player.getName() + " has fallen in battle.");
    } 
    
    return playerHP;
  }
  
  public void startGame() throws InterruptedException 
  {
    if (currentLocation == null) 
    {
      System.out.println("No starting location defined!");
      return;
    }
    
    System.out.println("You begin in your humble " + currentLocation.getName() + " again, for a third time destined to explore the world.");
    System.out.println("What a sense of deja vu...");
    
    while (playerHP > 0) 
    {
      //Show current location, connected nodes, and allow for player input
      System.out.println("\nYou are in: " + currentLocation);
      List<BiomeRegion> neighbors = getNeighbors(currentLocation);
      
      System.out.println("Possible paths:");
      for (int i = 0; i < neighbors.size(); i++) 
      {
        System.out.println((i + 1) + ". " + neighbors.get(i).getName());
      }
      
      System.out.print("Choose destination: ");
      int choice;
      
      try 
      {
        choice = Integer.parseInt(scanner.nextLine().trim());
        if (choice < -2 || choice > neighbors.size()) 
        {
          System.out.println(ErrorMessages.getRandomMessage(messages));
          continue;
        }
        else if (choice == -1)
        {
          System.out.println("DEBUG MODE: Instantiating instance of combat.");
          playerHP = beginCombat();
          continue;
        }
        else if (choice == -2)
        {
          System.out.println("DEBUG MODE: Setting health to 0.");
          playerHP = 0;
          continue;
        }
      } 
      catch (NumberFormatException ex) 
      {
        System.out.println(ErrorMessages.getRandomMessage(messages));
        continue;
      }
      
      currentLocation = neighbors.get(choice - 1);
      System.out.print("\nTraveling to " + currentLocation.getName() + ".");
      Thread.sleep(500);
      System.out.print(".");
      Thread.sleep(500);
      System.out.print(".");
      System.out.println();
      
      //Check if an item is encountered
      if (rnd.nextInt(100) < 20)
      {
        System.out.println("You found a " + currentLocation.getBiome().getItems());
      }
      
      //Check if an enemy is encountered and begin combat
      if (rnd.nextInt(100) < currentLocation.getBiome().getEncounterChance()) 
      {
        playerHP = beginCombat();
      } 
      else 
      {
        continue;
      }
    }
    
    System.out.println("\nGame over.");
    System.out.println("Exiting game...");
    Thread.sleep(2500);
    System.exit(0);
  }
  
  //Debug helper
  public void debugPrintWorld() 
  {
    System.out.println("WORLD GRAPH:");
    worldGraph.printGraph();
  }
}
