/**
 * Shamin and Mai Ke 
 * BiomeRegion class: creates and contains the data of BiomeRegion object
 * 12/7/2025
 */
import java.util.*;

public class BiomeRegion 
{
  //Attributes
  private final String name;
  protected final Biome biome;
  private final boolean isOutskirt;
  
  /**
   * Constructor 
   * @param name represents the Region's name
   * @param biome represents the Region's corresponding biome
   * @param isOutskirt represents if the region is an outskirt
   */
  public BiomeRegion(String name, Biome biome, boolean isOutskirt) 
  {
    this.name = name;
    this.biome = biome;
    this.isOutskirt = isOutskirt;
  }
  
  /**
   * This method gets the name of the Region 
   * @return String of the Region's name
   */
  public String getName() 
  {
    return name;
  }
  
  /**
   * This method gets this region's biome
   * @return Biome representing the Region's connected Biome.
   */
  public Biome getBiome() 
  {
    return biome;
  }
  
    /**
   * This method determines if the region is an outskirt or not
   * @return boolean true if this region is an outskirt; else false.
   */
  public boolean isOutskirt() 
  {
    return isOutskirt;
  }
  
  /**
   * This method returns the name of the region and its cooresponding biome
   * @return String of this region and its Biome
   */
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
  
  
  /**
   * This is the hashCode for the BiomeRegion
   * taken from https://www.digitalocean.com/community/tutorials/java-equals-hashcode
   * @return int of the hashCode
   */
  @Override
  public int hashCode(){
    final int prime = 31;
    int result = 1;
    result = prime * result + biome.name.hashCode();
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;

  }
  
  /**
   * The equals method determines if two BiomeRegions are equal
   * Code referenced by https://www.digitalocean.com/community/tutorials/java-equals-hashcode
   * @return boolean true if its equal by biome, class, name , else false
   */
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
