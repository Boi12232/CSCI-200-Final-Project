import java.io.*;
import java.util.*;

public class RPGGame 
{
  private final Graph<BiomeRegion> worldGraph = new Graph<>(false);
  private final Map<String, Biome> biomes = new HashMap<>();
  private final Map<String, BiomeRegion> regions = new HashMap<>();
  private Map<String,BiomeRegion> shelterLocations = new HashMap<>();
  private final Map<String, ArrayList<String>> pendingConnections = new HashMap<>();
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
  private boolean dfs = false;
  private boolean bfs = false; 
  private boolean dkj = false;
  
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
      messages = ErrorMessages.loadMessages();
    } 
    catch (IOException e) 
    {
      System.out.println("Could not load error messages: " + e.getMessage());
      messages = new ArrayList<>();
      messages.add("Invalid input. Try again!"); //Fallback
    }
  }
  
  
  /**
   * This method Initializes biomes, enemies, and items
   * @param
   * @param
   * @param
   */
  private void initializeWorldFromFile(String biomeFile, String enemyFile, String itemFile) 
  {
    FileLoader.WorldData data;
    
    //Loads Biomes
    try 
    {
      data = FileLoader.loadWorld(biomeFile);
    } 
    catch (IOException e) 
    {
      System.out.println("Error reading world file: " + e.getMessage());
      return;
    }
    
    //After biome is loaded in, initialize respective arrays with all the biomes,regions, and connections within the text file.
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
      FileLoader.loadEnemies(enemyFile, biomes, enemies); //load enemies, including references to all biomes and enemies maps
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
    for (Map.Entry<String, ArrayList<String>> entry : pendingConnections.entrySet()) 
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
  
  /**
   * Calculates the weight given 
   * 
   */
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
  
  /**
   * This method allows the user to add a new location node into the Graph
   * @param name is a string that represents the name of the location that will be created (maybe types? but idk)
   */
  private void addLocation()
  {
    System.out.println("What will be the new location name?");
    String shelterName = scanner.next();
    
    if(shelterLocations.size() < 2){
      
      if(biomes.containsKey(shelterName)){
        System.out.println("This location already exists! Please Rename");
        shelterName = scanner.nextLine();
      }
      
      BiomeRegion loc = new BiomeRegion(shelterName,biomes.get("Village"),false);
      //System.out.println(loc);
      shelterLocations.put(shelterName,loc);
      biomes.get("Village").getRegions().add(loc);
      
      
      List<String> currentGraphConns = pendingConnections.get(currentLocation.getName()); //gets the array in current location and adds newly made shelter as a neighbor of currentLocation
      currentGraphConns.add(shelterName);
      worldGraph.addVertex(loc);
      worldGraph.addEdge(loc,currentLocation,1);
    }
    else
    {
      System.out.println("You've reached the max number of locations!");
      return;
      
    }
  }
  
  private int beginCombat() 
  {
    List<Enemy> biomeEnemies = currentLocation.getBiome().getEnemies();
    if (biomeEnemies.isEmpty()) 
    {
      System.out.println("No enemies found in this region.");
      return playerHP;
    }
    
    int randomInt = rnd.nextInt(biomeEnemies.size());
    Enemy enemy = biomeEnemies.get(randomInt);
    biomeEnemies.remove(randomInt);
    biomeEnemies.add(enemy.duplicate()); //Removes Enemy and adds it back into respective array
    
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
      if(dfs == false && bfs == false && dkj == false){
        //Show current location, connected nodes, and allow for player input
        System.out.println("\nYou are in: " + currentLocation);
        List<BiomeRegion> neighbors = getNeighbors(currentLocation);
        
        System.out.println("Possible paths:");
        for (int i = 0; i < neighbors.size(); i++) 
        {
          System.out.println((i + 1) + ". " + neighbors.get(i).getName());
        }
        
        System.out.println(neighbors.size() + 1 + ". Other Options");
        
        System.out.print("Choose destination: ");
        int choice;
        
        try 
        {
          choice = Integer.parseInt(scanner.nextLine().trim());
          if (choice < -2 || choice > neighbors.size() + 1) 
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
        
        if(choice -1 == neighbors.size()){
          int choice2;
          System.out.println("Choose which other option you'd like? \n 1. DFS Through entire graph \n 2. BFS to the nearest Safe Zone \n 3. DKJ to \n 4. Add shelter \n 5. Go Back");
          try 
          {
            
            choice2 = Integer.parseInt(scanner.nextLine().trim());
            if (choice2 < 1 || choice2 > 5) 
            {
              System.out.println(ErrorMessages.getRandomMessage(messages));
              continue;
            }
          }
          catch(NumberFormatException ex) 
          {
            System.out.println(ErrorMessages.getRandomMessage(messages));
            continue;
          }
          
          if(choice2 == 1){ // DFS option
            dfs = true;
            bfs = false; 
            dkj = false;
          }
          else if(choice2 == 2){ //BFS option
            dfs = false;
            bfs = true; 
            dkj = false;
          }
          else if(choice2 == 3){ //DKJ option
            dfs = false;
            bfs = false; 
            dkj = true;
          }
          else if(choice2 == 4){
            addLocation();
            //debugPrintWorld();
          }
          else if(choice2 == 5){
          System.out.println("Going back");
          Thread.sleep(300);
          }
        }
        else
        {
        currentLocation = neighbors.get(choice - 1);
        System.out.print("\nTraveling to " + currentLocation.getName() + ".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        System.out.println();
        }
        
        //Check if an item is encountered
        if (rnd.nextInt(100) < 20)
        {
          System.out.println("You found a " + currentLocation.getBiome().getItems());
          System.out.println("Not completely done yet");
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
      else if(dfs == true){ //dfs will run through the entire program
        int choice = 0;
        System.out.println("DFS");
        List<BiomeRegion> list = worldGraph.traverseEntireGraph(currentLocation);
        list.remove(0);
        System.out.println(list);
        for(BiomeRegion biome: list){
          currentLocation = biome;
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
          
           if (rnd.nextInt(100) < 50) 
          {
            System.out.println("The weights for all saftey node has been increased...It will not be more difficult to reach it using Traversals");
            int size = biomes.get("Village").getRegions().size();
            BiomeRegion randomBiomeHeavy = biomes.get("Village").getRegions().get(rnd.nextInt(size));
            worldGraph.updateWeights(randomBiomeHeavy);
          } 
          
          System.out.println("Would you like to stay at " +  currentLocation.getName() + "? \n 1.Yes \n 2.No");
          choice = scanner.nextInt();
          try 
          {
            while(choice < 1 || choice > 2){
              choice = Integer.parseInt(scanner.nextLine().trim());
              System.out.println(ErrorMessages.getRandomMessage(messages));
              continue;
            }
          }
          catch(NumberFormatException ex) 
          {
            System.out.println(ErrorMessages.getRandomMessage(messages));
            continue;
          }
          
          if(choice == 1){
            dfs = false;
            break;
          }
          else{
            continue;
          }
        }
        dfs = false;
      }
      else if(bfs == true){
        
        //bfs will traverse to the closest shelter route
        List<BiomeRegion> list = worldGraph.traverseFrom(currentLocation);
        System.out.println(list);
        for(BiomeRegion biome: list){
          currentLocation = biome;
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
          
           if (rnd.nextInt(100) < 50) 
          {
            System.out.println("The weights for all saftey node has been increased...It will not be more difficult to reach it using Traversals");
            int size = biomes.get("Village").getRegions().size();
            BiomeRegion randomBiomeHeavy = biomes.get("Village").getRegions().get(rnd.nextInt(size));
            worldGraph.updateWeights(randomBiomeHeavy);
          } 
        }
        
        bfs = false;
      }
      else if(dkj == true){
        //dkj goes to a desired node by traversing only through the safest nodes
      
        System.out.println("Which Location do you want to traverse to?");
        
        Set<String> keysString = regions.keySet();
        ArrayList<String> keysArray = new ArrayList<String>();
        int counting = 0;
        for(String keyName: keysString){
        System.out.println( (counting + 1) + ". " + keyName);
        keysArray.add(keyName);
        counting ++;
        }
        
        int choice = scanner.nextInt();
        
        try 
        {
          while(choice < 1 || choice > keysString.size()){
            choice = Integer.parseInt(scanner.nextLine().trim());
            System.out.println(ErrorMessages.getRandomMessage(messages));
            continue;
          }
        }
        catch(NumberFormatException ex) 
        {
          System.out.println(ErrorMessages.getRandomMessage(messages));
          continue;
        }
        
        List<BiomeRegion> dkjList = worldGraph.traverseUsingLeast(currentLocation,regions.get(keysArray.get(choice-1)));
        dkjList.remove(0);
        System.out.println(dkjList);
        
        for(BiomeRegion biome: dkjList){
          currentLocation = biome;
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
          
          if (rnd.nextInt(100) < 50) 
          {
            System.out.println("The weights for all saftey node has been increased...It will not be more difficult to reach it using Traversals");
            int size = biomes.get("Village").getRegions().size();
            BiomeRegion randomBiomeHeavy = biomes.get("Village").getRegions().get(rnd.nextInt(size));
            worldGraph.updateWeights(randomBiomeHeavy);
          } 
        }
        dkj = false;
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
