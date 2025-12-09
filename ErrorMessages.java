/**
 * Shamin
 * ErrorMessages class: creates and contains the data and methods of ErrorMessages object
 * 12/7/2025
 * 
 */
import java.util.*;
import java.io.*;

public class ErrorMessages
{ 
  
  /**
   * This method takes a file of invalid inputs and adds them into an array
   * @return List of String representing an array containing each invalid responses after parsing the file.
   * @throws IOException if file is invalid
   */
  public static List<String> loadMessages() throws IOException
  {
    List<String> errorMessages = new ArrayList<String>();
    BufferedReader reader = new BufferedReader(new FileReader("invalidinputmessages.txt"));
    String line;
    
    while((line = reader.readLine()) != null)
    {
      errorMessages.add(line.trim());
    }
    reader.close();
    return errorMessages;
  }
  
  /**
   * This method gets a random message within the array 
   * @param messages represents the array with all the potential responses
   * @return String of the randomized response from the array.
   */
  public static String getRandomMessage(List<String> messages)
  {
    Random random = new Random();
    return messages.get(random.nextInt(messages.size()));
  }
}
