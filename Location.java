import java.util.*;

public class Location 
{
  private String name;
  private int encounterChance;
  private int fleeInfluence;
  private String description;
  private Biome biome;
  
  public Location(String name, int encounterChance, int fleeInfluence, String description, Biome biome) 
  {
    this.name = name;
    this.encounterChance = encounterChance;
    this.fleeInfluence = fleeInfluence;
    this.description = description;
    this.biome = biome;
  }
  
   /**
   * Constructor for creating a shelter location
   * @param name represents the shelter name
   */
  public Location(String name, Biome currentBiome){
    this.name = name;
    this.encounterChance = 0;
    this.fleeInfluence = 0;
    this.description = "Shelter";
    this.biome = currentBiome;
    
    
  }
  
  public String getName()
  {
    return name;
  }
  
  public Biome getBiome() 
  { 
    return biome; 
  }
  
  public int getEncounterChance() {
    return encounterChance;
  }
  
  @Override
  public boolean equals(Object o) 
  {
    if (!(o instanceof Location)) return false;
    return name.equals(((Location)o).name);
  }
  
  @Override
  public int hashCode() 
  { 
    return name.hashCode(); 
  }
  
  @Override
  public String toString() 
  { 
    return name; 
  }
}
