import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Logic {
	// Initialises instances of all the classes that are necessary for functions.
		// Creates an instance of the Parser class in order to make commands to the game.
	    public static Parser navParser;
	    
	    // Creates instances of each playable character.
	    static Player playerOne;
	    
	    // Creates instances of each class thats needed for the games functions. Each tells the game which player character
	    // is being used and where that player currently is.
	    private static Room currentRoom;
	    private static Player currentPlayer;
	    public static Character currentCharacter;
	    
	    // A boolean switch that controls whether the game enters test mode.
	    // When in test mode, the normal function for writing to the Console is disabled. 
	    private static boolean debug_mode;
	    // All new player characters are registered to this Hash Map to them be written to the console.
	    private static HashMap<String, Player> playerList;
		// Used by the Output Stream: Sets the length of each sentence before a new line is made.
		private int charWidth = 65;
		// Creates an instance of the WidthLimitedOutputStream
		public static WidthLimitedOutputStream display;
		static Character mrtest;

		static Character monkey;

		static Character owl;

		static Character nextCharacter;

		static Character characterBeingTalkedTo;
	    Dialogue test, hello, cheese, help, helloowl, hellowmonkey, day, thisplace, food;
	    
	    PlayerCharacter testCharacter, testCharacter2;
	    Enemy testEnemy, testEnemy2;
	    
	    Toivo toivo;
	    
	    static ArrayList<Enemy> currentEnemyTeam;
	        
        // Rooms
    	// Each room is declared as a member of the Room Class.
        Room outside, hallway, office, safe, magic, basement, sweet, pizza, attic;
        
      // a constant array that holds all valid command words
      private static String[] navCommands = {
          "go", "quit", "help", "look", "examine", "back", "systemtest", "inventory", "drop", "take", "zuul", "describe", "talk", "approach", "stop", "battle",
          "status"
      };
      
	    
	public Logic(){
		
		ArrayList<String> temp = new ArrayList<String>();
		for(int i = 0; i < navCommands.length; i++){
			temp.add(navCommands[i]);
		}
		
        navParser = new Parser(temp);
        debug_mode = false;
        playerList = new HashMap<String, Player>();
		// By default, use standard output
		setOutputStream (System.out, charWidth);
		currentCharacter = new Character(null, null);
		
		createRooms();
		createItems();
		setUpPlayer();
		createNPCs();
	}
	
	   /** 
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // Creates all instances of the rooms.
        // Uses the format: room = new Room("description_of_the_room");
        outside = new Room("outside the main entrance of the building. You look around and shiver in your snuggee in the winter air. Perhaps you should enter now?");
        hallway = new Room("in the hallway of the building. It's long, narrow and has been hardly cleaned in thirty years. The doors break off in all directions.");
        office = new Room("in the leader's office where he keeps his special things like his special pencil called 'Mr Mallows'. If the leader is not feeling well, Mr Tanky has been known to take over from him. Otherwise, this place is a tiny, dingy box. There's a safe in the wall though.");
        safe = new Room("at the safe. With a wish, a flick and a very large stick, you break in only to find that it contains very, very little");
        magic = new Room("in the realm of the Owls who rule over this land with a horrible and cleaving wing. The land expands beyond you for miles and miles of rolling hills and trees. The sun has only just risen behind these monuments to the Earth, dazzelling your eyes as a quiet 'hoo-hoo'ing is carried on the breaze.");
        basement = new Room("in the basement of the building and it resembles the hovel of a dangerous psycopath. This one belongs to Sgt. Fuzzyboots who has just slinked back your leg. In the dark, he begins to hiss at you, making the bell on his collar tinkle with each slash of his claws.");
        sweet = new Room("in a place of madness and sweet, sweet delight that only a blank, white room with a light bulb hanging from the ceiling can give. For some reason, the room is spacious, just uninteresting.");
        attic = new Room("in the attic. Here, five hundred chimpanzees on five hundred typewriters tap-tap-tap away to create scritps for Hollyoaks. In order to keep warm, they burn the Shakespeare they accidentally write in the downy gloom.");
        
        // Room Exits
        // Creates all the exits for each room.
        // Uses the format: room.setExit("direction", room_the_exit_leads_to);
        outside.setExit("north", hallway);
        
        hallway.setExit("south", outside);
        hallway.setExit("office", office);
        hallway.setExit("west", magic);
        hallway.setExit("north", sweet);

        office.setExit("safe", safe);
        office.setExit("down", basement);
        office.setExit("hallway", hallway);
        
        safe.setExit("office", office);
        
        basement.setExit("up", office);
        
        magic.setExit("hallway", hallway);
        
        sweet.setExit("up", attic);
        sweet.setExit("hallway", hallway);
        
        attic.setExit("down", sweet);
    }
    
    private void createItems(){
    	// Items
        // Each item is declared as a member of the Item Class. 
        Item pencil, cup, rubber, korn, money, brew, doughnut, daff, maff, laff, smaff, gaff, staff, hamster, scrap, sword;
        
        // Creates all instances of all the items.
        // Uses the format: item = new Item("item_description", "item_description", item_weight);
        pencil = new Item("Pencil", "A small pencil, called 'Mr Mallows' with two googly eyes that stare into you. They judge you, they weigh your heart against the weight of the world and they pity you for failing to meet the standards of existance. They also seem to give a gold star for effort though.", 1);
        cup = new Item("Coffee Cup", "The cup isn't especially remarkable except that is smells of booze. The text on the side says 'World's Best Boss (If you discount Barry)'. Barry must be one hell of a guy.", 2);
        rubber = new Item("Rubber", "A dirty little device for making pencil invisible. Made from the mucus of trolls.", 3);
        korn = new Item("KorN CD", "A secret stash of Korn. Because when you feel like your staring into a deep, cavoernous void, its reasuring to know that there's someone else worse off than you.", 2);
        money = new Item("Money", "£1.71 in very dusty pennies. Wow! These guys must have been a crack unit in The Church of Neeson to have been given such bountiful funds. It's normally a bar of Dairy Milk and a copy of Metro.", 1);
        brew = new Item("Iron Brew", "Sweet, sweet liquid of orange delight and delectation. It's the fuel for my engine and my soul. Nothing but Neeson's fizzy ambrosia.", 3);
        doughnut = new Item("Doughnut", "A half-eaten doughnut. It's covered in fluff and dust as well as sprinkles. Seems a shame to waste it. Anyway, dust puts hairs on your chest.", 2);
        daff = new Item("Daffodils", "A bunch of Daffodils. They're bright and yellow.", 3);
        maff = new Item("Maffodils", "A bunch of weird-smelling flowers that resemble the face of Ussain Bolt.", 3);
        laff = new Item("Laffodils", "A bunch of flowers that occationally blerts out that everyone gets marchmellows if they Vote Concervative.", 3);
        smaff = new Item("Smaffodils", "A bunch of flowers that smell faintly of Chocolate Spread.", 3);
        gaff = new Item("Gaffodils", "A bunch of flowers that squeek if I say the word 'Farfignoogan'", 3);
        staff = new Item("Staffodils", "A bunch of flowers that spit seeds if it doesn't like you.", 3);
        hamster = new Item("Warrior Hamster", "A small hamster that's carrying a sword, a shield and has half of his face covered in blue paint. It keeps shouting for something but the squeeky voice is too funny to listen too properly.", 4);
        scrap = new Item("Scrap From Burn Pile", "A scrap of the burn pile in the attic. You could only manage to take one off the top. It reads: 'Sherril: I'm leaving you Barry and there's nothing this spaceship can do about it! Barry: Well, my ghost will have to see about that! Sherril: No Barry! We can't! It's forbidden and I must go to Fargon Five! Barry: I know my love but this is the summer of our discontent, worn opon ones sleave while your daughter makes the beast with two backs. Sherril: Oh that I might be a glove apon that hand that I might stay on Earth rather than mine for Donkey Sweat.' Terrifying.", 1);
        sword = new Item("Sword of Zuul", "The Sword of Zuul. Your Task is complete. Speek it's name and all shall end.", 6);
        
        // Item Locations and Weights.
        // Creates the location of each item in relation of the Room they are expected to appear in as well as the weight of each one.
        // Uses the format: room.setItem("item_name", item);
        // 					room.setItemWeight(item_weight, item);
        office.setItem("pencil", pencil);
        office.setItem("coffeecup", cup);
        office.setItem("rubber", rubber);
        office.setItem("korncd", korn);
        
        safe.setItem("money", money);
        safe.setItem("ironbrew", brew);
        safe.setItem("doughnut", doughnut);
        
        magic.setItem("daffodils", daff);
        magic.setItem("maffodils", maff);
        magic.setItem("laffodils", laff);
        magic.setItem("smaffodils", smaff);
        magic.setItem("gaffodils", gaff);
        magic.setItem("staffodils", staff);
        magic.setItem("warriorhamster", hamster);
        magic.setItem("sword", sword);
        
        attic.setItem("scrap", scrap);
                
    }
    
    public void setUpPlayer(){
    	// Player Characters
        // Creates new instances of each playable character.
        // Uses the format: player = new Player("character_name", start_location);
        playerOne = new Player("Brian", outside);
    	
    	// Sets which character the game will begin the game as and where that character starts off in the game world.
        currentPlayer = playerOne;
        currentRoom = outside;
        
        // Player Character starting positions.
        // Writes the current room to each character's room history.
        playerOne.writeRoomHistory(currentRoom);
        
        toivo = new Toivo();
        
        testCharacter = new PlayerCharacter("Test", 100, 
        		new CharacterClass("Mage", -5, 0, -5, 10, 10, 0, -10, 0, 0, 10, 10)
        		,10, 18, 10, 10, 10, 10);
        testCharacter2 = new PlayerCharacter("Test 2", 100, 
        		new CharacterClass("Warrior", 15, -5, 5, -10, -10, 5, 5, -10, -10, 0, -10)
        		,16, 12, 2, 20, 23, 50);
        
        playerOne.addToParty(toivo);
    }
    
    public void createNPCs(){

        nextCharacter = new Character("error", "error");
        
        // NPC Characters

        //Mr Test - Used for testing this little bit of the program.
        test = new Dialogue("test", "I hope to Inari and Celestia that this bloody thing works.");
        hello = new Dialogue("hello", "Hi. I am Mr Test and I was created by Tyro The Fox to demonstate the currently really limited dialogue system. I'll work but it's very awkward to implement at the moment.");
        cheese = new Dialogue("cheese", "Cheeeeeeeeeese! The Limited Width what's it makes sure everything is laid out nicely.");
        help = new Dialogue("help", "If you know any way of getting the dialogue system in this working better than it is at the moment then please, please, please could you help out if you looking at this little tech demo thing");
        helloowl = new Dialogue("hello", "YOU DARE TALK TO ME PUNY MORTAL OF CHIMP DECENT?! I RUFFLE MY FEATHERS IN YOUR DIRECTION! I SHOULD SNATCH YOU UP LIKE A FIELD MOUSE! LEAVE NOW!");
        hellowmonkey = new Dialogue("hello", "Hello there luuuuuve! How yeh doin'? Eh? Eh? I need a fag break soon...");
        day = new Dialogue("day", "I've had an awful day to day, mate! I bashed out three verses of Sonet 130 before three acts of The Tempest! I've not had this bad a time of it since I started writing about a time travelling cop that went back to the 1980's in a coma or something. They took the script and my Cillit Bang away from me...Bastards...");
        thisplace = new Dialogue("thisplace", "This is where the magic happens! Awwww, yeah! Hollyoaks hasn't been quite the same thanks to us.");
        food = new Dialogue("food", "We tend to eat the mould from underneath the older scripts. They just grow faster than anythin' I ever did see!");
        
        
        mrtest = new Character("Mr Test", "Just come guy I'm using to make sure this all works.");
        owl = new Character("An Owl", "A huge, menacing bird that seems to be watching you carefully. Maybe it thinks your a shrew?");
        monkey = new Character("A Monkey Typist", "This moss-covered, smelly and dirty animal is happily hitting keys like a maniac while he smokes endlessly.");
        
        outside.setCharacter("mrtest", mrtest);
        mrtest.setOption("test", test);
        mrtest.setOption("hello", hello);
        mrtest.setOption("cheese", cheese);
        mrtest.setOption("help", help);
        
        attic.setCharacter("monkey", monkey);
        monkey.setOption("hello", hellowmonkey);
        monkey.setOption("day", day);
        monkey.setOption("thisplace", thisplace);
        monkey.setOption("food", food);
        
        magic.setCharacter("owl", owl);
        owl.setOption("hello", helloowl);
        
        testEnemy = new Enemy("Test Enemy", 100, 
        		new CharacterClass("Warrior", 15, -5, 5, -10, -10, 5, 5, -10, -10, 0, -10)
        		,1, 6, 20, 1, 1, 1);
        testEnemy2 = new Enemy("Test Enemy 2", 100, 
        		new CharacterClass("Warrior", 15, -5, 5, -10, -10, 5, 5, -10, -10, 0, -10)
        		,1, 2, 20, 2, 2, 2);
        
        currentEnemyTeam = new ArrayList<Enemy>();
        
        currentEnemyTeam.add(testEnemy);
        currentEnemyTeam.add(testEnemy2);
        
    }
	
    /**
     * Instead of a print line method, this method handles all printing to the console by being sent strings to then be printed.
     * The WidthLimitedOutputStream will then handle formatting the console output automatically. 
     * The Dependent on whether debug mode is on or off.
     * Sets the string to be output to the Console and the length of each sentence displayed. 
	 * @param out
	 * @param width
	 */
	public void setOutputStream(OutputStream out, int width)
	{
		if (!debug_mode){
		display = new WidthLimitedOutputStream(out, width);
		}
	}
    
    /**
     * Print out the opening message for the player.
     */
    public static void printWelcome()
    {
    	// The players to the list of playable characters.
    	playerList.put("Brian", playerOne);
    	
    	// Opening message is written here.
        display.println(" ");
        display.println("Welcome to Zuul");
        display.println(" ");
        display.println("Neeson bless you");
        display.println(" ");
        display.println("Your mission, if you choose to except it, is to find the sword of Zuul in the Neesonite's HQ in Swindon.");
        display.println(" ");
        display.println("A little background: The Church of Neeson devotes its time to the worship and activities that further the causes of Liam Neeson; Saviour of the Wasteland saviour of the first High Priestess. The followers of the Church of Neeson (termed 'Neesonites') are famous for their charity work that tends to revolve around retiriving kidnap victims by punching all perpetrators in the face. All Neesonites can disarm you in thirty different ways. Five can be done with toothpicks.");
        display.println(" ");
        display.println(currentRoom.getLongDescription());
        display.println(currentRoom.getItemList());
    	
        // The various methods that gather the important information related to room or item information are added to the Console.
        String returnString = "Party: ";
//        Set<String> playerKeys = playerList.keySet();
//        for(Iterator<String> iter = playerKeys.iterator(); iter.hasNext();){
//           returnString += iter.next() + " | ";
//        }
        for(PlayerCharacter p : playerOne.party) {
        	returnString += p.name + " ";
        }
        display.println(returnString);
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     * @param command
     * @return
     */
    public static boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        
        // Checks whether the user has given a command work that doesn't match any of the words programmed into the system.
        if(command.isUnknown()) {
            display.println("I don't know what you mean...");
            return false;
        }
        
        // Collects the command word typed into the game from the Parser.
        String commandWord = command.getCommandWord();
        
        // The Help Command - Prints the game's help messages.
        if (commandWord.equals("help")){
            printHelp();}
        // The Go Command - Controls the direction the player's character moves in.
        else if (commandWord.equals("go")){
            goRoom(command);}
        // The Quit Command - Stops the program. 
        else if (commandWord.equals("quit")){
            wantToQuit = quit(command);}
        // The Look Command - Displays the room information again if you missed it.
        else if (commandWord.equals("look")){
        	lookRoom();}
        // The Examine Command - Displays information about an item of choice in the room.
        else if (commandWord.equals("examine")) {
            examineItem(command);
        }
        // The Back Command - Makes the player's character backtrack a room.
        else if (commandWord.equals("back")){
        	back();
        }
        // The System Test Command - Tests the back command. Mainly for people working on adding new commands to the game.
        else if (commandWord.equals("systemtest")){
            TestGame TestGame;
            TestGame = new TestGame();
            debug_mode = true;
            TestGame.test();
            debug_mode = false;
        }
        // The Switch Command - Swaps the current player character with another of their choice.
        else if (commandWord.equals("switch")){
        	swapCharacterControl(command);
        }
        // The Inventory Command - Shows everything in that character's Inventory.
        else if (commandWord.equals("inventory")){
        	getInventory();
        }
        // The Take Command - Picks up an item of choice in the room and places it in the inventory of the currently used character.
        else if (commandWord.equals("take")){
        	takeItem(command);
        }
        // The Drop Command - Drops an item back into the rooom that character is currently standing in from the character's inventory.
        else if (commandWord.equals("drop")){
        	dropItem(command);
        }
        // The Zuul Command - If the player has picked up the Sword of Zuul, then they shall recite lines from the famous Neesonite scripture, ready to take on the monsters of the Church.
        else if (commandWord.equals("zuul")){
        	zuul();
        }
        else if (commandWord.equals("describe")){
        	lookCharacter(command);
        }
        else if (commandWord.equals("approach")){
        	approachCharacter(command);
        }
        else if (commandWord.equals("talk")){
        	talkToCharacter(command);
        }
        else if (commandWord.equals("stop")){
        	stopTalkingToCharacter();
        }
        else if (commandWord.equals("battle")){
        	Combat c = new Combat();
        	c.turnLoop();
        	
        	if(c.end){
        		lookRoom();
        	}
        }
        else if (commandWord.equals("status")){
        	display.print("Status");
        }
        return wantToQuit;
    }
    
    /**
     * Used by the Test Game class - It allows the Test Game class to process a command using the same classes and methods as the Game class.
     * @param command
     * @return
     */
    public boolean processTestCommand(Command command) 
    {
    	return processCommand(command);
    }
    
    /**
     * Used by the Test Game class - The Test Game class uses this method to signal a back command.
     */
    public void testBack(){
    	back();
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Hopefully, some useful words and a list of the 
     * command words available.
     */
    private static void printHelp() 
    {
        display.println("You are lost. You are alone. You wander");
        display.println(" ");
        display.println("Your command words are:");
        navParser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param command
     */
    private static void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go. So we ask the player to type the command in properly.
            display.println("Go where exactly?");
            return;
        }
        
        // Collects the second word in the commands from the Parser class.
        String direction = command.getSecondWord();

        // Try to leave current room. If there isn't an exit, we inform the player that this is so.
        Room nextDirection = currentRoom.getExit(direction);
        if (nextDirection == null) {
            display.println("There is no door!");
        }
        else {
        	// Adds the room the player has moved from to the player character's room history for use by the back command.
			currentPlayer.writeRoomHistory(currentRoom);
			// THis changes the current room to the room the player is moving into.
            currentRoom = nextDirection;
            // Prints the new locations description.
            display.println(currentRoom.getLongDescription());
            display.println(currentRoom.getItemList());
            cancelTalkingToCharacter();
        }
    }
    
    /**
     * A back command method to allow the player to retrace their steps.
     */
    private static void back() 
    {
        // Try to leave current room.
    	Room lastRoom = currentPlayer.readRoomHistory();
    	
    	// Checks that there is a place to actually backtrack to.
        if(lastRoom == null){
        	display.println("You can't go back any further!");
        }
        // Checks for the last room in the Player character's history and makes the character move back to it.
        else{
            Room nextRoom = null;
            nextRoom = lastRoom;
    		// Changes the current room to the room the player moves into.
            currentRoom = nextRoom;
            // Then displays that rooms description.
            display.println(currentRoom.getLongDescription());
            display.println(currentRoom.getItemList());
            cancelTalkingToCharacter();
        }
    }     
    
    /**
     * Looks at one of the items (that the player chooses with a command) and displays the item's description.
     * @param command
     */
    private static void examineItem(Command command)
    {
    	Item nextItem = null;
        if(!command.hasSecondWord()) {
            // If there is no second word, we don't know what we're looking at so we ask the player to type things in properly.
            display.println("Look at what?");
            return;
        }

        // Get the name of the item that the player has requested.
        String item = command.getSecondWord();

        // Display the item's description to the console.
        if (currentRoom.getItemData(item) != null){
        	nextItem = currentRoom.getItemData(item);
        }
        else if (currentPlayer.fetch(item) != null){
        	nextItem = currentPlayer.fetch(item);
        }
        
        display.println(currentRoom.getFullDescription(nextItem));
    }
    
    /**
     * A command to look at an item in the room
     */
    private static void lookCharacter(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            display.println("Look at who, exactly?");
            return;
        }

        // Check the item being looked at
        String characterName = command.getSecondWord();

        // Return the item description
        display.println(currentRoom.getCharacterDescription(characterName));
    }
    
    /**
     * A method to allow the player to approach an NPC and lock them into a conversation. All dialogue options will now be in relation to this character.
     * @param command
     */
    private static void approachCharacter(Command command)
    {
        String commandWord = command.getSecondWord(); 

    	if(!command.hasSecondWord()){
    		display.println("You need to specify who your talking to or your just talking to yourself.");
    	}
    	else if(commandWord.equals("mrtest")){
    		nextCharacter = mrtest;
    	}
    	else if(commandWord.equals("monkey")){
    		nextCharacter = monkey;
    	}
    	else if(commandWord.equals("owl")){
    		nextCharacter = owl;
    	}
    	else{
    		display.println("They're not here.");
    	}
    	
    	currentCharacter = nextCharacter;
    	display.println("Approached: " + nextCharacter.getCharacterName());
    	display.println(currentCharacter.getOptionString());
    }
    
    /**
     * A method to allow conversations between the player and the NPCs in the game.
     * @param command
     */
    private static void talkToCharacter(Command command)
    {
    	String speechOption = command.getSecondWord();
    	
    	Dialogue talkCommand;
    	talkCommand = new Dialogue(null, null);
    	characterBeingTalkedTo = new Character(null, null);
    	
    	characterBeingTalkedTo = currentCharacter;
    	if(command.hasSecondWord()){
    	talkCommand = currentCharacter.getOption(speechOption);
    	}
    	else{
    		display.println("You need to specify what to say or your mouth will be just hanging open.");
    	}
    	
   		display.println(" ~" + characterBeingTalkedTo.getCharacterName() + "~ ");
    	display.println(talkCommand.getCharacterSpeech());
    	display.println(characterBeingTalkedTo.getOptionString());
    	
    }
    
    private static void stopTalkingToCharacter()
    {
    	characterBeingTalkedTo = null;
    	display.println("You'll talk to them later.");
    	lookRoom();
    }
    
    private static void cancelTalkingToCharacter(){
    	characterBeingTalkedTo = null;
    }
    
    
    /**
     * Exchanges the character the player is currently controlling with one the player wants to use.
     * @param command
     */
    private static void swapCharacterControl(Command command){  
    	
    	// Gets the player's choice of character.
        String playerAvatar = command.getSecondWord();
        
        // The temporary store for the room the character being swapped is in.
		Room temp;
		temp = new Room(null);
    	if (command.equals(null)){
    		// The player has typed things in wrong, so the system tells the player that it hasn't any idea what the player is on about.
    		display.println("Who we swapping to?");
    	}
    	// The current character's location is saved before being swapped over for the other character.
		currentPlayer.writeRoomHistory(currentRoom);
    	
		// A method for swapping to Brian. It swaps all the details for the currently used character for the newly changed character.
    		currentPlayer = playerOne;
    		temp = playerOne.readRoomHistory();
    		currentRoom = temp;
    		display.println(" ~ " + currentPlayer.getPlayerName() + " ~ ");
    		lookRoom();

    }
    
    /**
     * Displays the players current inventory.
     */
    private static void getInventory(){
            display.println(currentPlayer.getInventory());
    }

    /**
     * Takes an item that is in the room the player is in.
     * @param command
     */
    private static void takeItem(Command command){
    	// A variable to check whether there is anything that can be picked up in the room.
        boolean itemPresent = false;
        
        // Gets the list of items that the Room class generates for the current room. 
        //String itemList = currentRoom.getItemString();
        
        
        
        // Gets the second command word the player typed in from the Parser.
        String name = command.getSecondWord();
        Item item = currentRoom.getItemData(name);
        // A temporary store for the item being manipulated.
        
        // The method below likes having things in a lower case form. This converts the string to a lower case form.
        name = name.toLowerCase();
        
        // Checks to see if the item asked for is in this room. If not, the game will ask the player to type everything properly.
        if (name != null){
            itemPresent = true;
        }
        // If the item is in the room, it's placed in the inventory of the currently used character then it's name is removed from the room.
        if(itemPresent == true){
            currentPlayer.take(name, item);
            currentRoom.removeItemData(name);
            display.println(currentRoom.getLongDescription());
            display.println(currentRoom.getItemList());
        }
        else{
            display.println("Nope! There doesn't seem to be any of that here...");
        }
    }

    /**
     * Drops an item currently held by the player.
     * @param command
     */
    private static void dropItem(Command command){
    	// A variable to check whether there is anything that can be picked up in the room.
        boolean itemPresent = false;
        // The Inventory string where the contents of the Inventory is stored as a string.
        String inventory = "";
        // Gets the second command word from the Parser.
        String name = command.getSecondWord();
        // A temporary store for the item being manipulated.        
        Item item = null;

        // The method below likes having things in a lower case form. This converts the string to a lower case form.        
        name = name.toLowerCase();
        
        // Goes and collects the item from the Inventory (if it is in the Inventory) then places it into the room then wipes the item from the Inventory list.
        if (currentPlayer.getInventory().contains(name) == true){
                item = currentPlayer.fetch(name);
                currentRoom.setItem(name,item);
                currentPlayer.drop(name);
                display.println(currentRoom.getLongDescription());
                display.println(currentRoom.getItemList());
            }
        }
    
    
    /**
     * A command to reprint the room description to the console.
     */
    private static void lookRoom()
    {
       display.println(currentRoom.getLongDescription());
       display.println(currentRoom.getItemList());
    }
    
    /**
     * Gives a win condition for the silly plot for no reason, that's dependent on whether the player has found the Sword of Zuul and stored it in their inventory.
     */
    private static void zuul()
    {
    	Command quit;
    	quit = new Command("quit", null);
    	String playerName = currentPlayer.getPlayerName();
    	
    	if (currentPlayer.getInventory().contains("sword") == true){
    		display.println(playerName + " holds the Sword of Zuul aloft, heart pumping ever so much faster now that this momentous occation has happened. It's been done, the Neesonites will never end their appresiation. It's well known. The words come naturally to " + playerName + ".");
    		display.println(" ");
    		display.println("'Monster, I don't know who you are. I don't know what you want. If you are looking for a ransom for the high pristess. I can tell you I don't have money. But what I do have are a vary particular set of skills; skills I have acquired over a very long career. Skills that make me a nightmare for people like you. If you let the priestess go now, that'll be the end of it. I will not look for you, I will not pursue you. But if you don't, I will look for you, I will find you, and I will kill you.'");
    		display.println(" ");
    		display.println("The declaration to the heavens spoken, " + playerName + " sheathes the sword and walks out to battle the monster for the High Priestess of the Neesonites; The Huge Monsterous Salmon Jelly of Mild Peril.");
    		quit(quit);
    	}
    	else{
    		display.println("You can't do that without the sword.");
    	}
    }
    
    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     * @param command
     * @return
     */
    private static boolean quit(Command command) 
    {	
    	// If the player has typed in one more word than the game needs, then the system will ask the player to type the command in properly.
        if(command.hasSecondWord()) {
            display.println("You can quit the game if you'd like.");
            return false;
        }
        else
        // This signals that the player wants to stop playing.
            return true;
    }
}
