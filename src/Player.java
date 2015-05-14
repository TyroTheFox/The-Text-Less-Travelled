import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * The Player Class 
 * Creates the Playable Characters the player controls.
 * 
 * @author Kieran Clare
 * @version 1.14
 */

public class Player
{
	// The Character's Name.
    private String playerName;
    // The Character's list of previously visited rooms.
    public Stack<Room> roomHistory;
    // The start position of this Character.
    public Room startPosition;
    // The Inventory of this Character.
    private HashMap inventory;

    ArrayList<PlayerCharacter> party;
    /**
     * Constructor for objects of the class, Player.
     * @param name
     * @param start
     */
    public Player(String name, Room start)
    {
        playerName = name;
        roomHistory = new Stack<Room>();
        start = startPosition;
        inventory = new HashMap<String, Item>();
        
        party = new ArrayList<PlayerCharacter>();
        
    }
    
    public void addToParty(PlayerCharacter p){
    	party.add(p);
    }
    
    public PlayerCharacter getPartyMember(int i){
    	return party.get(i);
    }
    
    /**
     * Writes the player character's current location to this Hash Map.
     * @param currentRoom
     */
    public void writeRoomHistory(Room currentRoom){
    	roomHistory.push(currentRoom);
    }
    
    /**
     * Returns the player characer's current location from the Hash Map.
     * @return
     */
    public Room readRoomHistory(){
    	return roomHistory.pop();
    }

    /**
     * Returns the player character's name.
     * @return
     */
    public String getPlayerName(){
        return playerName;
    }
    
    /**
     * Puts an item in the player character's inventory.
     * @param name
     * @param item
     */
    public void take(String name, Item item){
        inventory.put(name, item);
    }
    
    /**
     * Removes an item from the player character's inventory.
     * @param name
     */
    public void drop(String name){
        inventory.remove(name);
    }
    
    /**
     * Returns the contents of the Inventory in the form of a string variable.
     * @return
     */
    public String getInventory(){
        String returnString = "";
        Set inventoryKeys = inventory.keySet();
        for(Iterator iter = inventoryKeys.iterator(); iter.hasNext();){
            returnString += iter.next() + " ";
        }
        return returnString;
    }
    
    /**
     * Allows the function to return values from the Inventory Hash Map.
     * @param name
     * @return
     */
    public Item fetch(String name){
        return (Item) inventory.get(name);
    }
}
