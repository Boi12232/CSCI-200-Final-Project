/**
 * Shamin
 * Biome class: creates and contains the data of Biome object
 * 12/7/2025 
 * 
 */
import java.util.*;

public class Biome 
{
  /**
   * Attributes
   */
  protected final String name;
  private final int encounterChance;
  private final int fleeInfluence;
  private final List<BiomeRegion> regions = new ArrayList<>();
  private List<Enemy> enemies = new ArrayList<>();
  //private List<Item> items = new ArrayList<>();
  private BiomeRegion outskirtRegion;
  
  /**
   * Constructor 
   * @param name represents the biome name
   * @param encounterChance represents the chance the user encounters an enemy while in this biome
   * @param fleeInfluence represents the opportunity for the user to flee from an enemy while in this biome
   */
  public Biome(String name, int encounterChance, int fleeInfluence) 
  {
    this.name = name;
    this.encounterChance = encounterChance;
    this.fleeInfluence = fleeInfluence;
  }
  
  /**
   * This method gets the name of the Biome 
   * @return String of the Biome's name
   */
  public String getName() 
  {
    return name;
  }
  
  /**
   * This method gets the encounterChance of the Biome 
   * @return int of the encounterChance of the biome
   */
  public int getEncounterChance() 
  {
    return encounterChance;
  }
  
  /**
   * This method gets the fleeInfluence of the Biome 
   * @return int of the fleeInfluence of the biome
   */
  public int getFleeInfluence() 
  {
    return fleeInfluence;
  }

  /**
   * This method returns a list of enemies in this biome 
   * @return List of the enemies in this Biome
   */
  public List<Enemy> getEnemies()
  {
    return enemies;
  }

//  public List<Item> getItems()
//  {
//    return items;
//  }
  
  /**
   * This method adds regions that are within this Biome 
   * @param region represents the region we want to add into list regions
   */
  public void addRegion(BiomeRegion region) 
  {
    regions.add(region);
    if (region.isOutskirt()) {
      this.outskirtRegion = region;
    }
  }
  
  /**
   * This method adds an enemy that is in this biome
   * @param e represents the enemy we want to add into list enemies
   */
  public void addEnemy(Enemy e)
  {
    enemies.add(e);
  }
  
  /**
   * This method returns a list of regions apart of this biome 
   * @return List of the regions apart of this biome 
   */
  public List<BiomeRegion> getRegions() 
  {
    return regions;
  }
  
  /**
   * This method returns the outskirt region in this Biome (Theres only one)
   * @return BiomeRegion that represents the outskirts of this Biome
   */
  public BiomeRegion getOutskirtRegion() 
  {
    return outskirtRegion;
  }
  
  /**
   * This method returns the name of this biome 
   * @return String of this Biome name
   */
  @Override
  public String toString() 
  {
    return name;
  }
}
