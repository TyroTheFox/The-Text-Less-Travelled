import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

public class Dialogue
{
    private static HashMap<Dialogue, String> speech;
    private String characterSpeech;
    private String option;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "in a kitchen" or "in an open court 
     * yard".
     */
	public Dialogue(String option, String characterSpeech) {
		this.option = option;
    	this.characterSpeech = characterSpeech;
        speech = new HashMap<Dialogue, String>();
	}

	public static void setSpeech(Dialogue option, String characterSpeech) 
    {
        speech.put(option, characterSpeech);
    }
    
    public String getDialogueOption()
    {
    	return option;
    }
    
    public String getCharacterSpeech()
    {
    	return characterSpeech;
    }
}

