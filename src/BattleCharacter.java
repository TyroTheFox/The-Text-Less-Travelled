
public class BattleCharacter {
	
	PlayerCharacter player;
	Enemy enemy;
	String name;
	int type = 0;
	
	int targetting = 0;
	
	Command command;

	public BattleCharacter(PlayerCharacter p){
		player = p; 
		name = p.name;
		type = 1;
	}
	
	public BattleCharacter(Enemy e){
		enemy = e;
		name = e.name;
		type = 2;
	}
	
	public float returnDex(){		
		if(type == 1){
			return player.dexterity;
		}
			
		if(type == 2){
			return enemy.dexterity;
		}
		
		return 0;
	}
	
	public boolean returnEmpty(){
		if(type == 1){
			return player.energyTankCheck();
		}		
		return false;
	}
	
	public void storeCommand(Command c){
		command = c;
	}
	
	public Command returnCommand(){
		return command;
	}
}
