import java.util.*;

public class Biome 
{
  private final String name;
  private final int encounterChance;
  private final int fleeInfluence;
  private final List<BiomeRegion> regions = new ArrayList<>();
  private List<Enemy> enemies = new ArrayList<>();
  private List<Item> items = new ArrayList<>();
  private BiomeRegion outskirtRegion;
  
  public Biome(String name, int encounterChance, int fleeInfluence) 
  {
    this.name = name;
    this.encounterChance = encounterChance;
    this.fleeInfluence = fleeInfluence;
  }
  
  public String getName() 
  {
    return name;
  }
  
  public int getEncounterChance() 
  {
    return encounterChance;
  }
  
  public int getFleeInfluence() 
  {
    return fleeInfluence;
  }
  
  public List<Enemy> getEnemies()
  {
    return enemies;
  }
  
  public List<Item> getItems()
  {
    return items;
  }
  
  public void addRegion(BiomeRegion region) 
  {
    regions.add(region);
    if (region.isOutskirt()) {
      this.outskirtRegion = region;
    }
  }
  
  public void addEnemy(Enemy e)
  {
    enemies.add(e);
  }
  
  public List<BiomeRegion> getRegions() 
  {
    return regions;
  }
  
  public BiomeRegion getOutskirtRegion() 
  {
    return outskirtRegion;
  }
  
  @Override
  public String toString() 
  {
    return name;
  }
}
