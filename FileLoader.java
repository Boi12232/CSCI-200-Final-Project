import java.io.*;
import java.util.*;

public class FileLoader 
{
  
  public static class WorldData 
  {
    public final Map<String, Biome> biomes = new HashMap<>();
    public final Map<String, BiomeRegion> regions = new HashMap<>();
    public final Map<String, List<String>> pendingConnections = new HashMap<>();
    public final List<Enemy> enemies = new ArrayList<>();
    public final List<Item> items = new ArrayList<>();
  }
  
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
            data.pendingConnections.put(from, Arrays.asList(tos));
            break;
        }
      }
    }
    return data;
  }
  
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
  
  public static void loadItems(String filename, Map<String, Biome> biomes, List<Item> allItems) throws IOException 
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
        
        //Regular item: Name, Value, Type
        int value = Integer.parseInt(parts[1].trim());
        String type = parts[2].trim();
        Item item = new Item(name, value, type);
        allItems.add(item); 
      } 
    }
  }
}
