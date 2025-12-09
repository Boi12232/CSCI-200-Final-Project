/**
 * Shamin 
 * Player class: creates and contains the methods of the Player Object. The player data.
 * 12/7/2025
 */
import java.util.*;

public class Player 
{
  /**
   * Attributes
   */
  private String name;
  private int maxHP;
  private int currentHP;
  private int attack;
  private int defense;
  private Inventory inventory;
  
  
  /**
   * Constructor 
   * @param name represents the user's name;
   * @param hp represents the starting health of the user
   * @param attack represents the starting attack damage of the user
   * @param defense represents the defense value of the user
   */
  public Player(String name, int hp, int attack, int defense) 
  {
    this.name = name;
    this.maxHP = hp;
    this.currentHP = hp;
    this.attack = attack;
    this.defense = defense;
    this.inventory = new Inventory();
  }
  
  /**
   * This method returns the user's name 
   * @return String of the user's name
   */
  public String getName() 
  { 
    return name; 
  }
  
  /**
   * This method returns the user's name 
   * @return String of the user's name
   */
  public int getHP() 
  { 
    return currentHP; 
    
  }
  
  /**
   * This method returns the user's attack damage 
   * @return int of the user's attack damage
   */
  public int getAttack() 
  { 
    return attack;
  }
  
  /**
   * This method returns the user's defense  
   * @return int of the user's defense
   */
  public int getDefense() 
  { 
    return defense; 
  }
  
  /**
   * This method increases the user's HP 
   * @param val represents the amount the user's health will increase by
   */
  public void addHP(int val) 
  {
    currentHP += val;
    if (currentHP > maxHP) currentHP = maxHP;
  }
  
    /**
   * This method increases the user's attack damage
   * @param val represents the amount the user's attack will increase by
   */
  public void addATK(int val)
  {
    attack += val;
  }
  
    /**
   * This method increases the user's defense
   * @param val represents the amount the user's defense will increase by
   */
  public void addDEF(int val)
  {
    defense += val;
  }
  
  
  public void takeDamage(int dmg) 
  {
    currentHP -= dmg;
    if (currentHP < 0) currentHP = 0;
  }
  
  /**
   * This method returns a boolean on if the user is alive or not
   * @return boolean true if the user's health is greater than 0; else false.
   */
  public boolean isAlive() 
  {
    return currentHP > 0;
  }
  
  /**
   *This method gets the player's inventory
   * @return Inventory 
   */
  public Inventory getInventory() 
  {
    return inventory;
  }
  
}
