import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.transform.Templates;

public class Combat {

	private Parser parser;
	private Player player;
	private ArrayList<PlayerCharacter> playerParty;
	private ArrayList<Enemy> enemyParty;
	
	private static ArrayList<BattleCharacter> turnOrder;
	
	private static BattleCharacter temp;
	
	PlayerCharacter tempP;
	static Enemy tempE;
	
	boolean attack = false;
	
	boolean end = false;
	
	public static WidthLimitedOutputStream display;
	
	int currentTurn = 0;
	
	public Combat(){
		player = Logic.playerOne;
		playerParty = player.party;
		enemyParty = Logic.currentEnemyTeam;
		
		display = Logic.display;
		
		turnOrder = new ArrayList<BattleCharacter>();
		addParties();
	}
	
	public void turnLoop(){
		currentTurn++;
		display.println();
		display.println("Turn " + currentTurn);
		
		for(BattleCharacter b : turnOrder) {
			temp = b;
			if(b.type == 2){
				if(!b.enemy.frontRow){
					display.println();
					display.print("/" + b.targetting + "//// " + b.enemy.name + " // ");
					display.print("Lvl: " + b.enemy.level + " // ");
					display.print("HP: " + b.enemy.HP + " // ");
				}
				
	        }
		}
		
		display.println();
		System.out.println("-                                                     Back Row");
		display.println("--------------------------------------------------------------");
		System.out.println("-                                                    Front Row");
				
	    for(BattleCharacter b : turnOrder) {
			temp = b;
			if(b.type == 2){		
				if(b.enemy.frontRow){
					display.println();
					display.print("/" + b.targetting + "//// " + b.enemy.name + " // ");
					display.print("Lvl: " + b.enemy.level + " // ");
					display.print("HP: " + b.enemy.HP + " // ");
				}
	        }
		}
		display.println("||||||||||| Enemy Party");
		display.println("-----------------------");
		display.println();
		display.println("-----------------------");
		display.println("||||||||||| Player Party");
		for(BattleCharacter b : turnOrder) {
			temp = b;
			if(b.type == 1){
				if(b.player.frontRow){
					display.print("///// " + b.player.name + " // ");
					display.print("Lvl: " + b.player.level + " // ");
					display.print("HP: " + b.player.HP + " // ");
					display.println();
					getCommand(b);
				}
	        }
		}
		
		System.out.println("-                                                    Front Row");
		display.println("--------------------------------------------------------------");
		System.out.println("-                                                     Back Row");
		
		for(BattleCharacter b : turnOrder) {
			temp = b;
			if(b.type == 1){
				if(!b.player.frontRow){
					display.print("///// " + b.player.name + " // ");
					display.print("Lvl: " + b.player.level + " // ");
					display.print("HP: " + b.player.HP + " // ");
					display.println();
					getCommand(b);
				}
	        }
		}
		
		takeTurn();
	}
	
	public void takeTurn(){
		  for(BattleCharacter b : turnOrder) {
			  temp = b;
			  if(b.type == 1){
			      String commandWord = b.returnCommand().getCommandWord();
			        
		        // The Help Command - Prints the game's help messages.
		          if (commandWord.equals("attack")){
					  attack(b.returnCommand());
				  }
		          if (commandWord.equals("cast")){
					  magicAttack(b.returnCommand());
				  }
		          if (commandWord.equals("shoot")){
					  gunAttack(b.returnCommand());
				  }
			  }
			  if(b.type == 2){
		            BattleCharacter[] list = new BattleCharacter[10];
		            int count = 0;
		    		for(BattleCharacter bc : turnOrder) {
		    			if(bc.type == 1){
		    				list[count] = bc;
		    				count++;
		    	        }
		    		}
		    		
		    		int rand = 0;
		    		int max = count, min = 0;
		    		
		    		int range = Math.abs(max - min);     
		    		rand = (int) ((Math.random() * range) + (min <= max ? min : max));
		    		
		    		tempP = list[rand].player;
				  
		    		float damage = temp.enemy.attackCalc(tempP.fortitude, 50);
				  	display.println();
				  	display.println(temp.name + " attacked " + tempP.name + " for " + damage);
				  	tempP.reduceHP(damage);
				  	if(b.enemy.critFlag){
				  		display.println("-!CRITICAL HIT!-");
				  	}
			  }
		  }
		  
		  enterConfirmPause();
		  
		  winCheck();
		
		  if(!end){
		  sortTurnOrder();
		  turnLoop();
		  }
		  
		  if(end){
			  return;
		  }
	}
	
	public void addParties(){
	  for(PlayerCharacter p : playerParty) {
		  turnOrder.add(new BattleCharacter(p));
	  }
	  
	  int target = 0;
	  
	  for(Enemy e : enemyParty) {
		  target++;
		  BattleCharacter t = new BattleCharacter(e);
		  t.targetting = target;
		  turnOrder.add(t);
	  }
	  sortTurnOrder();

	}
	
	public void winCheck(){
		int enemyCount = 0;
		int playerCount = 0;
		ArrayList<BattleCharacter> temp = new ArrayList<BattleCharacter>();
		boolean remove = false;
		
		for(BattleCharacter b : turnOrder) {
			if(b.type == 1){
				if(b.player.HP <= 0){
					temp.add(b);
					remove = true;
				}
				else{
					playerCount++;
				}
			}
			if(b.type == 2){
				if(b.enemy.HP <= 0){
					temp.add(b);
					remove = true;
				}
				else{
					enemyCount++;
				}
			}
		}
		
		if(remove){
			turnOrder.removeAll(temp);			
		}
		
		if(enemyCount > 0 && playerCount > 0){
			return;
		}
		
		if(enemyCount > 0 && playerCount == 0){
			display.println("Player Lost - Game Over");
			enterConfirmPause();
			end = true;
			return;
		}
		
		if(enemyCount == 0 && playerCount > 0){
			display.println("Player Wins!");
			enterConfirmPause();
			end = true;
			return;
		}
	}
	
	public void sortTurnOrder(){
		Collections.sort(turnOrder, new Comparator<BattleCharacter>(){
			@Override
			public int compare(BattleCharacter c1, BattleCharacter c2) {				
		         if(c1.returnDex() == c2.returnDex())
		             return 0;
		         return c1.returnDex() < c2.returnDex() ? -1 : 1;
			}
		});
	}
	
	public void getCommand(BattleCharacter b){
        boolean finished = false;
        while (! finished) {        	
            Command command = Logic.parser.getCommand();
            finished = processCommand(command, b);
        }
	}
	
	public void enterConfirmPause(){
        boolean finished = false;
		display.println();
		display.println("Push Enter to Continue");
        while (! finished) {        	
            Command command = Logic.parser.getCommand();
            finished = true;
        }
	}
	
//    public boolean processPause(Command command) 
//    {
//        boolean wantToQuit = false;
//
//            return true;
//    }
	
	   /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     * @param command
     * @return
     */
    public boolean processCommand(Command command, BattleCharacter b) 
    {
        boolean wantToQuit = false;
        
        // Checks whether the user has given a command work that doesn't match any of the words programmed into the system.
        if(command.isUnknown()) {
            Logic.display.println("I'm pretty sure " + b.name + " can't do that...");
            return false;
        }
        
        // Collects the command word typed into the game from the Parser.
        String commandWord = command.getCommandWord();
        
        // The Help Command - Prints the game's help messages.
        if (commandWord.equals("attack") || commandWord.equals("cast") || commandWord.equals("shoot")){
        	if(command.hasSecondWord()){
	        	int temp = Integer.parseInt(command.getSecondWord());
	        	if(temp - 1 < enemyParty.size()){
		            b.storeCommand(command);
		            return true;
	            }
	        	else{
	        		display.println("There aren't that many of them. Try again.");
	        		return false;
	        	}
        	}
        	else{
        		b.storeCommand(command);
	            return true;
        	}
        }
        else{
        	Logic.display.println("I don't know what you mean...");
        	return false;
        }
    }
    
    public static void attack(Command command){
    	
        BattleCharacter[] list = new BattleCharacter[10];
        int count = 0;
        
		for(BattleCharacter b : turnOrder) {
			if(b.type == 2){
				list[count] = b;
				count++;
	        }
		}
    	
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go. So we just swing at something randomly.
        	display.println();
            display.println("All right! We'll just swing at anything randomly.");
    		
    		int rand = 0;
    		int max = count, min = 0;
    		
    		int range = Math.abs(max - min);     
    		rand = (int) ((Math.random() * range) + (min <= max ? min : max));
    		
    		tempE = list[rand].enemy;
    		
        }
        
        if(command.hasSecondWord()){
        	
        	int temp = Integer.parseInt(command.getSecondWord());
        	
    		for(BattleCharacter b : turnOrder) {
    			if(b.type == 2){
    				if(b.targetting == temp){
    					tempE = b.enemy;
    				}
    	        }
    		}
        }
    	
        float hitChance = temp.player.accuracyCalc(100, 1);
        
		float rand = 0;
		float max = 100, min = 0;
		
		float range = Math.abs(max - min);     
		rand = (float) ((Math.random() * range) + (min <= max ? min : max));
		
		if(rand <= hitChance){        
	    	float damage = temp.player.attackCalc(tempE.fortitude, 50);
			display.println();
	    	display.println(temp.player.name + " attacked " + tempE.name + " for " + damage);
	    	tempE.reduceHP(damage);
		  	if(temp.player.critFlag){
		  		display.println("-!CRITICAL HIT!-");
		  	}
		}
		else{
			display.println(temp.player.name + " missed.");
		}
    }
    
    public static void magicAttack(Command command){
    	
        BattleCharacter[] list = new BattleCharacter[10];
        int count = 0;
        
		for(BattleCharacter b : turnOrder) {
			if(b.type == 2){
				list[count] = b;
				count++;
	        }
		}
    	
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go. So we just swing at something randomly.
        	display.println();
            display.println("All right! We'll just swing at anything randomly.");
    		
    		int rand = 0;
    		int max = count, min = 0;
    		
    		int range = Math.abs(max - min);     
    		rand = (int) ((Math.random() * range) + (min <= max ? min : max));
    		
    		tempE = list[rand].enemy;
    		
        }
        
        if(command.hasSecondWord()){
        	
        	int temp = Integer.parseInt(command.getSecondWord());
        	
    		for(BattleCharacter b : turnOrder) {
    			if(b.type == 2){
    				if(b.targetting == temp){
    					tempE = b.enemy;
    				}
    	        }
    		}
        }
    	
        float hitChance = temp.player.accuracyCalc(100, 1);
        
		float rand = 0;
		float max = 100, min = 0;
		
		float range = Math.abs(max - min);     
		rand = (float) ((Math.random() * range) + (min <= max ? min : max));
		
		if(rand <= hitChance){ 
	    	float damage = temp.player.mAttackCalc(tempE.fortitude, 50);
			display.println();
	    	display.println(temp.player.name + " attacked " + tempE.name + " for " + damage);
	    	tempE.reduceHP(damage);
		  	if(temp.player.critFlag){
		  		display.println("-!CRITICAL HIT!-");
		  	}
		}
		else{
			display.println(temp.player.name + " missed.");
		}
    }
    
    public static void gunAttack(Command command){
    	
        BattleCharacter[] list = new BattleCharacter[10];
        int count = 0;
        
		for(BattleCharacter b : turnOrder) {
			if(b.type == 2){
				list[count] = b;
				count++;
	        }
		}
    	
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go. So we just swing at something randomly.
        	display.println();
            display.println("All right! We'll just swing at anything randomly.");
    		
    		int rand = 0;
    		int max = count, min = 0;
    		
    		int range = Math.abs(max - min);     
    		rand = (int) ((Math.random() * range) + (min <= max ? min : max));
    		
    		tempE = list[rand].enemy;
    		
        }
        
        if(command.hasSecondWord()){
        	
        	int temp = Integer.parseInt(command.getSecondWord());
        	
    		for(BattleCharacter b : turnOrder) {
    			if(b.type == 2){
    				if(b.targetting == temp){
    					tempE = b.enemy;
    				}
    	        }
    		}
        }
    	
        float hitChance = temp.player.accuracyCalc(100, 1);
        
		float rand = 0;
		float max = 100, min = 0;
		
		float range = Math.abs(max - min);     
		rand = (float) ((Math.random() * range) + (min <= max ? min : max));
		
		if(rand <= hitChance){  
	    	float damage = temp.player.gAttackCalc(tempE.fortitude, 50);
			display.println();
	    	display.println(temp.player.name + " attacked " + tempE.name + " for " + damage);
	    	tempE.reduceHP(damage);
		  	if(temp.player.critFlag){
		  		display.println("-!CRITICAL HIT!-");
		  	}
		}
		else{
			display.println(temp.player.name + " missed.");
		}
    }
	
}
