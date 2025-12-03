public class Item 
{
    private final String name;
    private final int[] values;
    private final String[] types;

    //Regular item constructor
    public Item(String name, int value, String type) 
    {
        this.name = name;
        this.values = new int[] {value};
        this.types = new String[] {type};
    }

    //Special item constructor
    public Item(String name, int[] values, String[] types) 
    {
        this.name = name;
        this.values = values;
        this.types = types;
    }

    public String getName() 
    { 
      return name; 
    }
    
    public int[] getValues() 
    { 
      return values; 
    }
    
    public String[] getTypes() 
    { 
      return types; 
    }
    
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
    
    @Override
    public String toString() 
    { 
      return name; 
    }
}
