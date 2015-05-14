/**
 * The Item Class
 * All the items in the room.
 * 
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * Creates items to then be placed in the game later via the room class.
 * 
 * @author Kieran Clare
 * @version 1.14 (March 2012)
 */

public class Item {
	
	// The Item's Name.
    private String name;
    // The Item's description.
    private String description;
    // The weight of the Item.
    private int weight;
    
    /**
     * Creates an instance of an item.
     * @param name
     * @param description
     * @param weight
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }
    
    /**
     * Returns the name of the item.
     * @return
     */
    public String name(){
    	return name;
    }
    
    /**
     * Returns the description of the item.
     * @return
     */
    public String description(){
    	
    	return description;
    }
    
    /**
     * Returns the entire Description of the item. For some reason, this string comes out backwards.
     * @return
     */
    public String fullDescription(){
    	return name() + ": " + description();
    }
    
    /**
     * The weight of the item.
     * @return
     */
    public int weight(){
    	
    	return weight;
    }
    
}
