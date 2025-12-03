import java.util.*;

public class Inventory 
{
  private final List<Item> items = new ArrayList<>();
  private final Scanner input = new Scanner(System.in);
  
  /** 
   * Add item to inventory
   */
  public void addItem(Item item) 
  {
    items.add(item);
    System.out.println("You obtained: " + item.getName());
  }
  
  /** 
   * Show inventory & allow usage
   */
  public void showInventory(Player player) 
  {
    
    if (items.isEmpty()) 
    {
      System.out.println("\nYour inventory is empty.");
      return;
    }
    
    System.out.println("\n=== Inventory ===");
    for (int i = 0; i < items.size(); i++) 
    {
      Item item = items.get(i);
      
      System.out.print((i + 1) + ". " + item.getName() + " → ");
      
      // Print multi-value stat boosts
      String[] types = item.getTypes();
      int[] values = item.getValues();
      
      for (int j = 0; j < types.length; j++) 
      {
        System.out.print("+" + values[j] + " " + types[j]);
        if (j < types.length - 1) System.out.print(", ");
      }
      System.out.println();
    }
    
    System.out.println("0. Cancel");
    System.out.print("\nChoose an item to use: ");
    
    String choiceIn = input.nextLine().trim();
    int choice;
    
    try 
    {
      choice = Integer.parseInt(choiceIn);
    } 
    catch (NumberFormatException ex) 
    {
      System.out.println("Invalid choice.");
      return;
    }
    
    //Cancel
    if (choice == 0) 
    {
      System.out.println("Exited inventory.\n");
      return;
    }
    
    //Valid pick
    if (choice > 0 && choice <= items.size()) 
    {
      
      Item selected = items.remove(choice - 1);
      
      System.out.println("\nUsing: " + selected.getName());
      selected.useItem(player);  //Calls Item system
      
      System.out.println("\nUpdated Stats:");
      System.out.println("HP: " + player.getHP());
      System.out.println("ATK: " + player.getAttack());
      System.out.println("DEF: " + player.getDefense());
      
      return;
    }
    
    System.out.println("Invalid selection.");
  }
}
