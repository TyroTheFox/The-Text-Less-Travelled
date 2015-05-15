import java.util.ArrayList;

/**
 * The Command Words Class
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 * 
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * @author Kieran Clare
 * @version 1.14 (March 2012)
 */

class CommandWords
{
	private int charWidth = 65;
	// Creates an instance of the WidthLimitedOutputStream
	public static WidthLimitedOutputStream display;
//    // a constant array that holds all valid command words
//    private static final String validCommands[] = {
//        "go", "quit", "help", "look", "examine", "back", "systemtest", "switch", "inventory", "drop", "take", "zuul", "describe", "talk", "approach", "stop", 
//        "attack", "battle", "cast", "shoot"
//    };

    private static ArrayList<String> validCommands;
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords(ArrayList<String> commandList)
    {
    	display = new WidthLimitedOutputStream(System.out, charWidth);
        validCommands = commandList;
    }

    /**
     * Check whether a given String is a valid command word. 
     * Return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
    	for(String s : validCommands) {
    		if(s.equals(aString)){
    			return true;
    		}
    	}
    	return false;
    	
//        for(int i = 0; i < validCommands.length; i++) {
//            if(validCommands[i].equals(aString))
//                return true;
//        }
//        // if we get here, the string was not found in the commands
//        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() 
    {
    	String returnString = "Commands - ";
    	for(String s : validCommands) {
    		returnString += s + " ~ ";
    	}
    	display.print(returnString);
    	display.println();
//        for(int i = 0; i < validCommands.length; i++) {
//            System.out.print(validCommands[i] + "  ");
//        }
    }
}
