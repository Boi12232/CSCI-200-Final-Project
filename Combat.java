/**
 * Author: Shamin Fazal
 * Credit: Colton Hern for original Combat class (CSCI 160 Project Spring 2025)
 */

import java.util.*;

public class Combat {
  private final Player player;
  private final Enemy enemy;
  private final BiomeRegion region;
  private final Inventory inventory;
  private final Scanner input = new Scanner(System.in);
  private final Random rnd = new Random();
  
  public Combat(Player player, Enemy enemy, BiomeRegion region, Inventory inventory) 
  {
    this.player = player;
    this.enemy = enemy;
    this.region = region;
    this.inventory = inventory;
  }
  
  /**
   * Starts a battle between the player and the enemy.
   * @return the player's current HP after the battle.
   */
  public int startBattle() 
  {
    System.out.println("Battle starts!\n" + player.getName() + " vs " + enemy.getName());
    
    while (player.isAlive() && enemy.isAlive())
    {
      System.out.println("\nChoose action: \n1. Attack\n2. Flee\n3. Inventory");
      String action = input.nextLine();
      boolean playerAttacked = false;
      
      switch (action) 
      {
        case "1":
          playerAttack();
          playerAttacked = true;
          break;
        case "2":
          if (flee()) 
          return player.getHP();
          else
            break;
        case "3":
          System.out.println("\nAccessing inventory...");
          inventory.showInventory(player);
          continue;
        default:
          System.out.println("Invalid option.");
          continue;
      }
      
      // Enemy performs a turn
      if (enemy.isAlive())
      {
        enemyAttack();
      }
      
      //Win/Loss Check
      if (!player.isAlive())
      {
        System.out.println(player.getName() + " has been defeated.");
      }
      else if (!enemy.isAlive())
      {
        System.out.println(enemy.getName() + " has been defeated.");
      }
    }
    return player.getHP();
  }
  
  
  private void playerAttack()
  {
    int dmgToEnemy;
    if (criticalChance())
    {
      dmgToEnemy = player.getAttack() * 2;
      enemy.takeDamage(dmgToEnemy);
    }
    else
    {
      dmgToEnemy = defenseCalc(player.getAttack(), enemy.getDefense());
      enemy.takeDamage(dmgToEnemy);
    }
    System.out.println("\n" + player.getName() + " deals " + dmgToEnemy + " damage to " + enemy.getName());
  }
  
  private void enemyAttack()
  {
    int dmgToPlayer = defenseCalc(enemy.getAttack(), player.getDefense());
    player.takeDamage(dmgToPlayer);
    System.out.println("\n" + enemy.getName() + " deals " + dmgToPlayer + " damage to " + player.getName());
  }
  
  private boolean criticalChance()
  {
    boolean crit = false;
    int D20 = rnd.nextInt(20) + 1;
    if (D20 == 20) crit = true;
    return crit;
  }
  
  private boolean flee() 
  {
    int fleeRoll = rnd.nextInt(10) + 1;
    int chanceToFail = region.getBiome().getFleeInfluence();
    
    if (fleeRoll < (10 - chanceToFail)) {
      System.out.println("\n" + player.getName() + " successfully fled!");
      return true;
    } 
    else 
    {
      System.out.println("\n" + player.getName() + " failed to flee!");
      return false;
    }
  }
  
  /**
   * --- Method borrowed from 160 ---
   * Calculates the percentage of attack decreased with a cap of 80% to make it playable
   * @param damage is the player's damage
   * @param defense is the player's defense
   * @return Math.max(0, damage - damageReduce) is how much remain damage is being dealt
   */
  private int defenseCalc(int damage, int defense) {
    double percent = Math.min(defense / 100.0, 0.8);
    int damageReduce = (int) (damage * percent);
    return Math.max(0, damage - damageReduce);
  }
}
