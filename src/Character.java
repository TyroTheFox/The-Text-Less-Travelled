import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
/**
 * A class to create players for the game.
 * 
 * @author Kieran Clare
 * @version 1.0
 */
public class Character
{

    private String charName;
    private String charDescription;
    private HashMap<String, Dialogue> dialogueOptions; //stores character location.

    /**
     * Constructor for objects of class Player
     */
    public Character(String name, String description)
    {
        charName = name;
        charDescription = description;
        dialogueOptions = new HashMap<String, Dialogue>();
    }

    /**
     * Returns the players name.
     */
    public String getCharacterName(){
        return charName;
    }
    
    public String getCharacterDescription()
    {
    	return charName + ": " + charDescription;
    }
    
    /**
     * Define a dialogue Option for this character.
     * @param direction
     * @param neighbor
     */
    public void setOption(String optionString, Dialogue dialogue) 
    {
        dialogueOptions.put(optionString, dialogue);
    }
    
    public String getOptionString()
    {
        String returnString = "Speech Options:";
        Set keys = dialogueOptions.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }
    
    public Dialogue getOption(String option) 
    {
        return (Dialogue)dialogueOptions.get(option);
    }
    
}
