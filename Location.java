import java.util.*;

public class Location{
  //Attributes
  private String name;
  private int encounterChance;
  private int fleeInfluence;
  private String description;
  protected List<Enemy> enemies = new ArrayList<>();
  private List<String> items = new ArrayList<>();
  
  /**
   * Constructor for the Location
   * @param name represents the name of the location
   * @param encounterChance represents an int for the chances of encountering an enemy
   * @param fleeInfluence is used to find the possibility for fleeing from this particular enemy
   * @param description represents the description of the enemy
   */
  public Location(String name, int encounterChance, int fleeInfluence, String description) 
  {
    this.name = name;
    this.encounterChance = encounterChance;
    this.fleeInfluence = fleeInfluence;
    this.description = description;
  }
  
  /**
   * Constructor for creating a shelter location
   * @param name represents the shelter name
   */
  public Location(String name){
    this.name = name;
    this.encounterChance = 0;
    this.fleeInfluence = 0;
    this.description = "Shelter";
    
    
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
  public List<Enemy> getEnemies() 
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
  public boolean equals(Object o) 
  {
    if (!(o instanceof Location)) return false;
    return name.equals(((Location)o).name);
  }
  /**
   * Hash code
   */
  @Override
  public int hashCode() 
  {
    return name.hashCode();
  }
  /**
   * To string
   */
  @Override
  public String toString() 
  {
    return name;
  }
  
//  @Override
//  public int compareTo(T otherLocation){
//    Location other = (Location)otherLocation;
//    if(this.name ==  other.getName()){
//    return 0;
//    }
//    else{
//    return 1;
//    }
//  }
  
}
