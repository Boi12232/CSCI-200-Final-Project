/**
 * Shamin
 * Item class: creates and contains the methods of the Item Object.
 * 12/7/2025
 */
public class Item 
{
  /**
   * Attributes
   */
    private final String name;
    private final int[] values;
    private final String[] types;

    /**
     * Regular item constructor
     * @param name represents the item name
     * @param value represents the value the item will increase user attributes by
     * @param type represents if the item increases the user's Health, Attack, or Defense
     */
    public Item(String name, int value, String type) 
    {
        this.name = name;
        this.values = new int[] {value};
        this.types = new String[] {type};
    }

    
    /**
     * Special Item Constructor
     * @param name represents the item's name
     * @param values represents an array of the values the item will increase user attributes by
     * @param types represents an array of the user's Health, Attack, or Defense
     */
    public Item(String name, int[] values, String[] types) 
    {
        this.name = name;
        this.values = values;
        this.types = types;
    }

    /**
     * Gets name of the item 
     * @return String of the name of the item
     */
    public String getName() 
    { 
      return name; 
    }
    
     /**
     * Gets value of the item 
     * @return int[] of the value(s) of the item
     */
    public int[] getValues() 
    { 
      return values; 
    }
    
     /**
     * Gets types of the item 
     * @return String[] of the type(s) of the item
     */
    public String[] getTypes() 
    { 
      return types; 
    }
    
    
    /**
     * This method uses the item on the player based on its type and attribues and updates either the user's health, attack, defense, or all.
     * @param player represents the player stats.
     */
    public void useItem(Player player)
    {
      for (int i = 0; i < types.length; i++) 
      {
        String type = types[i];
        int value = values[i];
        
        switch (type.toLowerCase()) 
        {
          case "heal":
          case "hp":
            player.addHP(value);
            System.out.println(player.getName() + " healed " + value + " HP!");
            break;
            
          case "attack":
            player.addATK(value);
            System.out.println(player.getName() + " gained +" + value + " ATK!");
            break;
            
          case "defense":
          case "def":
            player.addDEF(value);
            System.out.println(player.getName() + " gained +" + value + " DEF!");
            break;
            
          case "status":
            System.out.println(player.getName() + " is now affected by " + value);
            break;
            
          default:
            System.out.println("Unknown item effect type: " + type);
        }
      }
    }
    
    
    
    /**
     * Returns the item's name
     * @return String of the item's name.
     */
    @Override
    public String toString() 
    { 
      return name; 
    }
}
