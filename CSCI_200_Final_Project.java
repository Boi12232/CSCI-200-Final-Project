import java.util.*;
import java.io.*;

public class CSCI_200_Final_Project
{
  public static void main(String[] args) throws IOException, InterruptedException
  { 
    RPGGame game = new RPGGame("locations.txt", "enemies.txt", "items.txt");
    //Uncomment next line to see the raw graph and region structure
    //game.debugPrintWorld();
    game.startGame(); //Start the game loop
  }
}
  
