import java.util.*;

public class BiomeRegion 
{
  private final String name;
  protected final Biome biome;
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
  
  
  /**
   * ------------------------------------------------------------------------------------------------------------------------------------------------------
   * CODE USED FROM SOURCE START:
   * 
   * hashCode method taken from and equals method referenced from
   * https://www.digitalocean.com/community/tutorials/java-equals-hashcode
   * 
   * 
   * 
   * 
   * -----------------------------------------------------------------------------------------------------------------------------------------------------
   */
  
  
  @Override
  public int hashCode(){
    final int prime = 31;
    int result = 1;
    result = prime * result + biome.name.hashCode();
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;

  }
  
  @Override
  public boolean equals(Object biome){
    if(this == biome){
      return true;
    }
    if(biome == null){
      return false;
    }
    if(getClass() != biome.getClass()){
      return false;
    }
    
    BiomeRegion otherBiome = (BiomeRegion) biome;
    
    if(this.name==otherBiome.name){
      if(this.biome==otherBiome.biome){
        if(this.isOutskirt == otherBiome.isOutskirt){
        return true;
        }
        else
        {
        return false;
        }
      
      }
      else{
      return false;
      }
    }
    else{
      return false;
    }
  
  
  }
  /**
   * ------------------------------------------------------------------------------------------------------------------------------------------------------
   * CODE USED/REFERENCED FROM SOURCE END:
   * 
   * hashCode method taken from and equals method referenced from
   * https://www.digitalocean.com/community/tutorials/java-equals-hashcode
   * 
   * 
   * 
   * 
   * -----------------------------------------------------------------------------------------------------------------------------------------------------
   */
  
  
}
