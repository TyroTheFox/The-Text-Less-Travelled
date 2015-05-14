import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The Room Class
 * A room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Kieran Clare
 * @version 1.14 (March 2012)
 */

class Room 
{
	// The Room's description.
    private String description;
    // The Room's exits.
    private HashMap exits;
    // The Room's items contained within.
    private HashMap items;
    private HashMap<String, Character> characters; //stores character location.
    
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     * @param description
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap();
        items = new HashMap();
        characters = new HashMap<String, Character>();
    }

    /**
     * Define an exit from this room.
     * @param direction
     * @param neighbor
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Defines an item in the room.
     * @param type
     * @param item
     */
    public void setItem (String type, Item item) 
    {
        items.put(type, item);

    }

	public void setCharacter(String name, Character character){
		characters.put(name, character);	
	}
    
    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     * @return
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of this room, in the form:
     *     You are in the kitchen. Exits: north west You can see there is: hat | 3kg
     * @return
     */
    public String getLongDescription()
    {
        System.out.println(getExitString());
        return "You are " + description + ".\n" + getCharacterString();
    }
    
    public String getItemList()
    {
    	return getItemString();
    }
    
    /**
     * Return a string to get the description of items in the room.
     */
    public String getCharacterDescription(String characterName)
    {
        Character character = characters.get(characterName);
        
        if (character == null) {
            return "No-one here called that.";
        }
        else{
            return character.getCharacterDescription();
        }
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set keys = exits.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " | " + iter.next();
        return returnString;
    }
    
    /**
     * Return a string describing the room's items, for example:
     * "You can see there is: hat | 3kg"
     * @return
     */
    public String getItemString(){
        String returnString = "Items: ";
        String itemName;
        Item currentItem;
        
        Set<String> itemKeys = items.keySet();
        for(Iterator<String> iter = itemKeys.iterator(); iter.hasNext();){
        	itemName = (String) iter.next();
        	currentItem = (Item) items.get(itemName);
        	returnString += currentItem.name() + ", " + currentItem.weight() + "Kg | ";
        }
        
        return returnString;
    }
    
    private String getCharacterString()
    {
        String returnString = "People you can see:";
        Set keys = characters.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction
     * @return
     */
    public Room getExit(String direction) 
    {
        return (Room)exits.get(direction);
    }
    
    /**
     * Allows the function to return values from the item Hash Map.
     * @param name
     * @return
     */
    public Item getItemData(String name){
        return (Item) items.get(name);
    }
    
    /**
     * Return a string to get the description of items in the room.
     * @param nextItem
     * @return
     */
    public String getFullDescription(Item nextItem)
    {
    	String returnString = " ";
        
        if (nextItem != null){
        returnString = (String) nextItem.fullDescription();
        }
        else{
        	// If there isn't an item of that name, then the game will report that fact.
        	return "Nope. None! Nadda! Zippo!";
        }
        return returnString;

    }
    
    /**
     * Allows items to be removed from a rooms hashmap.
     * @param name
     */
    public void removeItemData(String name){
        items.remove(name);
    }
    
    /**
     * Allows items to be removed from a rooms hashmap.
     * @param name
     */
    public void removeCharacterData(String name){
        characters.remove(name);
    }
    
}