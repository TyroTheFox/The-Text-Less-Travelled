
public class EnergyTank {
	
	int energyLevel = 50;
	int maxEnergyLevel = 50;
	int rechargeRate = 2;
	boolean empty = false;
	boolean full = true;
	public static WidthLimitedOutputStream display;
	int charWidth = 52;
	
	String tank, energy;
	
	public EnergyTank() {
		tank = "[";
		energy = "";
		display = new WidthLimitedOutputStream(System.out, charWidth);
	//	System.out.println("Energy Tank Created");
	}

	public void render(){
		display.print(tank);
		System.out.println(energyLevel);
	}
	
	public void update(){
		
		tank = "[";
		energy = "";
		for(int i = 0; i*10 <= energyLevel; i++){	
			energy += "|";
		}
		tank += energy + "] " + energyLevel;
		
		if(energyLevel <=0){
			empty = true;
		}
		
		if(energyLevel > 0){
			empty = false;
		}
		
		if(energyLevel >= maxEnergyLevel){
			full = true;
		}
		else{
			full = false;
		}
	}

}
