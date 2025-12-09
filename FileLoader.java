/**
 * Shamin
 * 12/7/2025
 * FileLoader class: creates and contains the methods of FileLoader object. Loads and parses text files.
 * Comments: Mai Ke 
 */
import java.io.*;
import java.util.*;

public class FileLoader 
{
  
  public static class WorldData 
  {
    /**
     * Attributes
     */
    public final Map<String, Biome> biomes = new HashMap<>();
    public final Map<String, BiomeRegion> regions = new HashMap<>();
    public final Map<String, ArrayList<String>> pendingConnections = new HashMap<>();
    public final List<Enemy> enemies = new ArrayList<>();
    public final List<Item> items = new ArrayList<>();
  }
  
  /**
   * This method takes a location file and parses it to designate and create Biomes, Regions, and which nodes Connects. 
   * @param filename represents the name of the file
   * @throws IOException if file is not valid
   * @return the information within WorldData containing all biomes, regions, and connections data.
   */
  public static WorldData loadWorld(String filename) throws IOException 
  {
    WorldData data = new WorldData();
    
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
    {
      String line;
      while ((line = br.readLine()) != null) 
      {
        if (line.trim().isEmpty()) continue;
        String[] parts = line.split(",");
        switch (parts[0].trim().toUpperCase()) 
        {
          case "BIOME":
            String biomeName = parts[1].trim();
            int encounter = Integer.parseInt(parts[2].trim());
            int flee = Integer.parseInt(parts[3].trim());
            data.biomes.put(biomeName, new Biome(biomeName, encounter, flee));
            break;
            
          case "REGION":
            String rBiome = parts[1].trim();
            String rName = parts[2].trim();
            boolean isOutskirt = Boolean.parseBoolean(parts[3].trim());
            BiomeRegion region = new BiomeRegion(rName, data.biomes.get(rBiome), isOutskirt);
            data.biomes.get(rBiome).addRegion(region);
            data.regions.put(rName, region);
            break;
            
          case "CONNECT":
            String from = parts[1].trim();
            String[] tos = parts[2].split("\\|");
            ArrayList<String> newList = new ArrayList<String>();
          for(String biomes: tos){
            newList.add(biomes);
          }
            data.pendingConnections.put(from, newList);
            break;
        }
      }
    }
    return data;
  }
  
  /**
   * This method takes an enemy file and parses it into respective biomes and arrays. Creates Enemy objects from the file as well.
   * @param filename represents the name of the file
   * @param biomes represents the list of Biomes
   * @param allEnemies represents a list to add each enemy into
   * @throws IOException if file is not valid
   */
  public static void loadEnemies(String filename, Map<String, Biome> biomes, List<Enemy> allEnemies) throws IOException 
  {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
    {
      String line;
      while ((line = br.readLine()) != null) 
      {
        if (line.trim().isEmpty()) continue;
        
        String[] parts = line.split(",");
        if (parts.length < 4) continue; //Skip invalid lines
        
        String name = parts[0].trim();
        int health = Integer.parseInt(parts[1].trim());
        int attack = Integer.parseInt(parts[2].trim());
        int defense = Integer.parseInt(parts[3].trim());
        String[] biomeNames = parts[4].split("\\|");
        
        Enemy enemy = new Enemy(name, health, attack, defense);
        allEnemies.add(enemy);
        
        for (String biomeName : biomeNames) 
        {
          biomeName = biomeName.trim();
          if (biomeName.isEmpty()) continue;
          Biome biome = biomes.get(biomeName);
          if (biome != null) 
          {
            biome.addEnemy(enemy);
          }
        }
      }
    }
  }
  
  
  /**
   * This method takes an item file and parses it to create Item objects
   * @param filename represents the name of the file
   * @param allItems represents a list to add each newly made Item object
   * @throws IOException if file is not valid
   */
  public static void loadItems(String filename, List<Item> allItems) throws IOException 
  {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
    {
      String line;
      while ((line = br.readLine()) != null) 
      {
        if (line.trim().isEmpty()) continue;
        
        String[] parts = line.split(",");
        if (parts.length < 3) continue; //Invalid line
        
        String name = parts[0].trim();
        
        //Determine if it's a special item (multiple values/types) or regular
        if (parts.length == 3) 
        {
          //Regular item: Name, Value, Type
          int value = Integer.parseInt(parts[1].trim());
          String type = parts[2].trim();
          Item item = new Item(name, value, type);
          
          allItems.add(item);
        } 
        else if (parts.length == 5) 
        {
          //Special item: Name, Value1, Value2, Value3, Type1|Type2|Type3
          int value1 = Integer.parseInt(parts[1].trim());
          int value2 = Integer.parseInt(parts[2].trim());
          int value3 = Integer.parseInt(parts[3].trim());
          String[] types = parts[4].split("\\|");
          
          //Create special item and store multiple values/types
          int[] valueArr = new int[]{value1, value2, value3};
          Item item = new Item(name, valueArr, types);
          allItems.add(item);
        }
      }
    }
  }
}
