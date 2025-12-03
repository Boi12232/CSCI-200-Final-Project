import java.util.*;

public class BiomeRegion 
{
  private final String name;
  private final Biome biome;
  private final boolean isOutskirt;
  
  public BiomeRegion(String name, Biome biome, boolean isOutskirt) 
  {
    this.name = name;
    this.biome = biome;
    this.isOutskirt = isOutskirt;
  }
  
  public String getName() 
  {
    return name;
  }
  
  public Biome getBiome() 
  {
    return biome;
  }
  
  public boolean isOutskirt() 
  {
    return isOutskirt;
  }
  
  @Override
  public String toString() 
  {
    return name + " (" + biome.getName() + ")";
  }
}
