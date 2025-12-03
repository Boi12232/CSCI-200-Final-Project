import java.util.*;

public class Player 
{
  private String name;
  private int maxHP;
  private int currentHP;
  private int attack;
  private int defense;
  private List<Item> inventory;
  
  public Player(String name, int hp, int attack, int defense) 
  {
    this.name = name;
    this.maxHP = hp;
    this.currentHP = hp;
    this.attack = attack;
    this.defense = defense;
    this.inventory = new ArrayList<>();
  }
  
  public String getName() 
  { 
    return name; 
  }
  
  public int getHP() 
  { 
    return currentHP; 
    
  }
  
  public int getAttack() 
  { 
    return attack;
  }
  
  public int getDefense() 
  { 
    return defense; 
  }
  
  public void addHP(int val) 
  {
    currentHP += val;
    if (currentHP > maxHP) currentHP = maxHP;
  }
  
  public void addATK(int val)
  {
    attack += val;
  }
  
  public void addDEF(int val)
  {
    defense += val;
  }
  
  public void takeDamage(int dmg) 
  {
    currentHP -= dmg;
    if (currentHP < 0) currentHP = 0;
  }
  
  public boolean isAlive() 
  {
    return currentHP > 0;
  }
  
  public void addItem(Item item) 
  {
    inventory.add(item);
  }
  
  public List<Item> getInventory() 
  {
    return inventory;
  }
}
