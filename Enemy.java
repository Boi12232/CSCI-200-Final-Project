public class Enemy 
{
    private final String name;
    private int health;
    private int attack;
    private int defense;

    public Enemy(String name, int health, int attack, int defense) 
    {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() 
    { 
      return name; 
    }
    
    public int getHealth() 
    { 
      return health; 
    }
    
    public int getAttack() 
    { 
      return attack; 
    }
    
    public int getDefense() 
    {
      return defense; 
    }

    public boolean isAlive()
    {
      return health > 0;
    }
    
    public void takeDamage(int dmg)
    {
      health -= dmg;
      if (health > 0) health = 0;
    }
    @Override
    public String toString() 
    { 
      return name; 
    }
}
