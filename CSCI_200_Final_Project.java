/**
 * Shamin and Mai Ke Lor
 * 12/7/2025
 * The program's driver
 * Comments: Mai ke
 */
import java.util.*;
import java.io.*;

public class CSCI_200_Final_Project
{
  /**
   * The main driver
   * @param args represents the array of arguments 
   * @throws IOException for invalid file
   * @throws InterruptedException for threads
   */
  public static void main(String[] args) throws IOException, InterruptedException
  { 
    RPGGame game = new RPGGame("locations.txt", "enemies.txt", "items.txt");
    //Uncomment next line to see the raw graph and region structure
    //game.debugPrintWorld();
    game.startGame(); //Start the game loop
  }
}
  
