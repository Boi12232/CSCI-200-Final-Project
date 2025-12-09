/**
 * Shamin 
 * Enemy class: creates and contains the data of Enemy object
 * 12/7/2025
 */
public class Enemy 
{
  /**
   * Attributes
   */
    private final String name;
    private int health;
    private int attack;
    private int defense;

    /**
     * Constructor 
     * @param name represents the name of the Enemy 
     * @param health represents the health of the Enemey
     * @param attack represents the attack damage of the Enemy 
     * @param defense represents the Enemy's defense
     */
    public Enemy(String name, int health, int attack, int defense) 
    {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     * This method gets the name of the enemy
     * @return String of the Enemy's name.
     */
    public String getName() 
    { 
      return name; 
    }
    
    /**
     * This method gets the Health of the Enemy
     * @return int of the Enemy's health
     */
    public int getHealth() 
    { 
      return health; 
    }
    
    /**
     * This method gets the Attack of the enemy 
     * @return int of the Enemy's attack damage
     */
    public int getAttack() 
    { 
      return attack; 
    }
    
    /**
     * This method gets the defense of the enemy 
     * @return int of the Enemy's defense
     */
    public int getDefense() 
    {
      return defense; 
    }

    /**
     * This method indicates if the enemy is still alive based on its health
     * @return boolean true if the enemy's health is greater than 0, else false
     */
    public boolean isAlive()
    {
      return health > 0;
    }
    
    /**
     * This method duplicates the enemy to add back into enemy array before getting into combat
     * @return Enemy before combat
     */
    public Enemy duplicate()
    {
      return new Enemy(this.name, this.health, this.attack, this.defense);
    }
    
    /**
     * This method removes the enemy's health based on the damage it's received 
     * @param dmg represents the damage taken by the enemy to be removed from its health
     */
    public void takeDamage(int dmg)
    {
      health -= dmg;
      if (health > 0) health = 0;
    }
    
    /**
     *This method returns the enemy's name
     * @return String of the enemy's name
     */
    @Override
    public String toString() 
    { 
      return name; 
    }
}
