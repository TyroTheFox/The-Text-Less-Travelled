import java.io.OutputStream;
import java.util.Stack;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


/**
 * 	The Game Class
 * 
 * 	This is a very silly game created from "The World of Zuul" game.
 * 	It's as advanced as it can be currently.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Kieran Clare
 * @version 1.14 (March 2012)
 */

class Game 
{	

	Logic logic;
    /**
     * The constructor that creates the game and initialises its internal map.
     */
    public Game() 
    {
    	logic = new Logic();
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
    	
        Logic.printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = Logic.navParser.getCommand();
            finished = Logic.processCommand(command);
        }
        Logic.display.println("Thank you for playing.  Good bye.");
    }


}
