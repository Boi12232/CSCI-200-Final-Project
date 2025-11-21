import java.util.*;

public class Location {
  //Attributes
  private String name;
  private int encounterChance;
  private int fleeInfluence;
  private String description;
  private List<String> enemies = new ArrayList<>();
  private List<String> items = new ArrayList<>();
  
  /**
   * Constructor for the Location
   * @param name
   * @param encounterChance
   * @param fleeInfluence
   * @param description
   */
  public Location(String name, int encounterChance, int fleeInfluence, String description) 
  {
    this.name = name;
    this.encounterChance = encounterChance;
    this.fleeInfluence = fleeInfluence;
    this.description = description;
  }
  
  /**
   * Name getter
   * @return the name
   */
  public String getName() 
  { 
    return name; 
  }
  /**
   * Encounter chance getter
   * @return encounter chance of location
   */
  public int getEncounterChance() 
  { 
    return encounterChance; 
  }
  /**
   * Flee influence getter
   * @return the flee influence of location
   */
  public int getFleeInfluence() 
  { 
    return fleeInfluence; 
  }
  /**
   * Description getter
   * @return the location's description
   */
  public String getDescription() 
  { 
    return description; 
  }
  /**
   * Enemies getter
   * @return a list of the location's enemies
   */
  public List<String> getEnemies() 
  { 
    return enemies; 
  }
  /**
   * Items getter
   * @return a list of the location's items
   */
  public List<String> getItems() 
  { 
    return items; 
  }
  
  /**
   * Equals method
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Location)) return false;
    return name.equals(((Location)o).name);
  }
  /**
   * Hash code
   */
  @Override
  public int hashCode() {
    return name.hashCode();
  }
  /**
   * To string
   */
  @Override
  public String toString() {
    return name;
  }
}
