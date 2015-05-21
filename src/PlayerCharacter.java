import java.util.ArrayList;

public class PlayerCharacter {

	//Modifiable Aspects\\
		//Name
		String name;
		//Level
		float level = 0;
		float HP = 0;
		
		//Strength
		//A Measure of Physical Prowess. Used mainly in Physical Attacking
		float STR = 0;
		
		//Dexterity
		//Dexterity measures agility, reflexes, and balance. Excellent for Ranged Weapon Users (Like Guns)
		float DEX = 0;
		
		//Constitution
		//Constitution represents your character's health and stamina.
		float CON = 0;
		
		//Intelligence
		//Intelligence determines how well your character learns and reasons. 
		//This ability is important for magical characters because it affects their spellcasting ability in many ways. 
		float INT = 0;
		
		//Wisdom
		//Wisdom describes a character's willpower, common sense, awareness, and intuition.
		float WIS = 0;
		
		//Charisma
		//Charisma measures a character's personality, personal magnetism, ability to lead, and appearance. Mainly used for diversions and tricks in combat.
		float CHA = 0;
		
		//Player Modifiers
			//Strength
			float strMod = 0;
			//Dexterity
			float dexMod = 0;
			//Constitution
			float conMod = 0;
			//Intelligence
			float intMod = 0;
			//Wisdom
			float wisMod = 0;
			//Charisma
			float chaMod = 0;
			//Fortitude - Describes a character's ability to withstand attack 
			float forMod = 0;
			//Marksmanship - Describes a character's ability to fire a weapon accurately
			float markMod = 0;
			//Machine Skill - Describes a character's ability to operate machinery effectively.
			float machMod = 0;
			//Magical Skill - Describes a character's ability to perform magic well
			float magMod = 0;
			//Magical Fortitude - Describes a character's ability to withstand magical attacks
			float magFortMod = 0;
			
			float maxHP = 0;
			
			float strength = 0;
			
			float dexterity = 0;
			
			float constitution = 0;
			
			float intelligence = 0;
			
			float wisdom = 0;
			
			float fortitude = 0;
			
			float marksmanship = 0;
			
			float machineSkill = 0;
			
			float magicalSkill = 0;
			
			float windMagicSkill = 0;
			float fireMagicSkill = 0;

			float magicalFortitude = 0;
		
			boolean critFlag = false;
			float critRatio = 0;
			
			float type = 1;
			float crit = 1;
			float other = 1;
			
			CharacterClass cClass;
			Boolean frontRow = false;	
			boolean moving = false;
			
			float accuracy = 1;
			boolean defending = false;
			
			  int maxTank = 2;
			  public ArrayList<EnergyTank> energyReserves;
			  public EnergyTank temp;
			  int currentTank;
			  
			  int rechargeTime = 5;
			  int recharge = 0;
			  boolean recharged = true;
			  boolean recharging = false;
			
	public PlayerCharacter(String name, float startingLevel, CharacterClass cClass, 
			float str, float dex, float con, float inte, float wis, float cha){
		this.name = name;
		
		STR = str;
		DEX = dex;
		CON = con;
		INT = inte;
		WIS = wis;
		CHA = cha;
		
		level = startingLevel;
		
		this.cClass = cClass;
		
		//Strength
		strMod = cClass.modSTR;
		//Dexterity
		dexMod = cClass.modDEX;
		//Constitution
		conMod = cClass.modCON;
		//Intelligence
		intMod = cClass.modINT;
		//Wisdom
		wisMod = cClass.modWIS;
		//Charisma
		chaMod = cClass.modCHA;
		//Fortitude - Describes a character's ability to withstand attack 
		forMod = cClass.modFort;
		//Marksmanship - Describes a character's ability to fire a weapon accurately
		markMod = cClass.modMark;
		//Machine Skill - Describes a character's ability to operate machinery effectively.
		machMod = cClass.modMach;
		//Magical Skill - Describes a character's ability to perform magic well
		magMod = cClass.modMag;
		//Magical Fortitude - Describes a character's ability to withstand magical attacks
		magFortMod = cClass.modMagFort;
		
		maxHP = (CON * 20 + conMod) + level/25 + 10 + level;
		
		HP = maxHP;
		
		strength = ((STR * 20 + strMod) * level / 50 + 5);
		dexterity = ((DEX * 20 + dexMod) * level / 50 + 5);
		constitution = ((CON * 20 + conMod) * level / 50 + 5);
		intelligence = ((INT * 20 + intMod) * level / 50 + 5);
		wisdom = ((WIS * 20 + wisMod) * level / 50 + 5);
		
		fortitude = (((STR + CON) * 10 + forMod) * level / 50 + 5);
		marksmanship = (((DEX + WIS) * 10 + markMod) * level / 50 + 5);
		machineSkill = (((DEX + INT) * 10 + machMod) * level / 50 + 5);
		magicalSkill = (((INT + WIS) * 10 + magMod) * level / 50 + 5);
		magicalFortitude = (((INT + CON) * 10 + magFortMod) * level / 50 + 5);
		
		critRatio = dexterity/15 + 5;
		
		energyReserves = new ArrayList<EnergyTank>();
		  
		  for(int e = 0; e < maxTank; e++){
			  energyReserves.add(new EnergyTank());
		  }
		  
		  currentTank = energyReserves.size() - 1;
	}
	
    public int compareDexTo(PlayerCharacter p)
    {
         return (int) (dexterity - p.dexterity);
    }
    
    public int compareDexTo(Enemy e)
    {
         return (int) (dexterity - e.dexterity);
    }
    
    public void reduceHP(float x){
    	if(HP - x >= 0){
    		HP -= x;
    	}
    	if(HP - x < 0){
    		HP = 0;
    	}
    }
    
	public float accuracyCalc(float baseAccuracy, float eEvasion){
		float result = 0;
		
		result = baseAccuracy * (accuracy/eEvasion);
		
		return result;
	}
	
	public float attackCalc(float eFortitude, float baseAttackPower){
		float rand = 0;
		float min = 0.85f, max = 1, minCrit = 0, maxCrit = 100;
		
		float range = Math.abs(max - min);     
		rand = (float) ((Math.random() * range) + (min <= max ? min : max));
		
		float rangeCrit = Math.abs(maxCrit - minCrit);     
		float tempCrit = (float) ((Math.random() * rangeCrit) + (min <= max ? min : max));
		
		if(tempCrit <= critRatio){
			crit = 2;
			critFlag = true;
		}
		else{
			crit = 1;
			critFlag = false;
		}
		
		float modifier = type * crit * other * rand;
		float damage = (((2*level+1)/250) * (strength/eFortitude) * (baseAttackPower) + 2) * modifier;
		
		return damage;
	}
	
	public float mAttackCalc(float eFortitude, float baseAttackPower){
		float rand = 0;
		float min = 0.85f, max = 1, minCrit = 0, maxCrit = 100;
		
		float range = Math.abs(max - min);     
		rand = (float) ((Math.random() * range) + (min <= max ? min : max));
		
		float rangeCrit = Math.abs(maxCrit - minCrit);     
		float tempCrit = (float) ((Math.random() * rangeCrit) + (min <= max ? min : max));
		
		if(tempCrit <= critRatio){
			crit = 2;
			critFlag = true;
		}
		else{
			crit = 1;
			critFlag = false;
		}
		
		float modifier = type * crit * other * rand;
		float damage = (((2*level+1)/250) * (magicalSkill/eFortitude) * (baseAttackPower) + 2) * modifier;
		
		return damage;
	}
	
	public float gAttackCalc(float eFortitude, float baseAttackPower){
		float rand = 0;
		float min = 0.85f, max = 1, minCrit = 0, maxCrit = 100;
		
		float range = Math.abs(max - min);     
		rand = (float) ((Math.random() * range) + (min <= max ? min : max));
		
		float rangeCrit = Math.abs(maxCrit - minCrit);     
		float tempCrit = (float) ((Math.random() * rangeCrit) + (min <= max ? min : max));
		
		float marksBonus = marksmanship/15;
		tempCrit -= marksBonus;
		
		if(tempCrit <= critRatio){
			crit = 2;
			critFlag = true;
		}
		else{
			crit = 1;
			critFlag = false;
		}
		
		float modifier = type * crit * other * rand;
		float damage = (((2*level+1)/250) * (marksmanship/eFortitude) * (baseAttackPower) + 2) * modifier;
		
		return damage;
	}
	
	public float calcFlatExperience(float eLevel){
		
		float a = 1.5f;
		float b = 50;
		float l = eLevel;
		
		float exp = (a * b * l)/7;
		
		return exp;
	}
	
	public float calcScaledExperience(float eLevel){
		
		float a = 1.5f;
		float b = 50;
		float l = eLevel;
		float lp = level;
		
		double x = (2*l)+10;
		double y = l + lp + 10;
		
		float exp = (float) (((a*b*l)/5)*((Math.pow(x, 2.5))/Math.pow(y, 2.5)) + 1);
		
		return exp;
	}
	
	public float calcErraticNextLevel(float pLevel){
		float exp = 0;
		
		if(pLevel <= 50){
			exp = (float) ((Math.pow(pLevel, 3)) * (100 - pLevel))/50;
		}
		if(pLevel > 50 || pLevel <= 68){
			exp = (float) ((Math.pow(pLevel, 3)) * (150 - pLevel))/100;
		}
		if(pLevel > 68 || pLevel <= 98){
			exp = (float) ((Math.pow(pLevel, 3)) * ((1911 - (pLevel * 10))*3))/500;
		}
		if(pLevel > 98 || pLevel <= 100){
			exp = (float) ((Math.pow(pLevel, 3)) * (160 - pLevel))/100;
		}
		
		return exp;
	}
	
	public float calcFastNextLevel(float pLevel){
		float exp = 0;
		
		exp = (float) ((Math.pow(pLevel, 3)) * (4))/5;
		
		return exp;
	}
	
	public float calcMediumFastNextLevel(float pLevel){
		float exp = 0;
		
		exp = (float) Math.pow(pLevel, 3);
		
		return exp;
	}
	
	public float calcMediumSlowNextLevel(float pLevel){
		float exp = 0;
		
		exp = (float) (((6/5) * Math.pow(pLevel, 3)) - (15 * Math.pow(pLevel, 2)) + (100 * pLevel) - 140);
		
//		exp = Math.abs(exp);
		
		return exp;
	}
	
	public float calcSlowNextLevel(float pLevel){
		float exp = 0;
		
		exp = (float) ((Math.pow(pLevel, 3)) * 5)/4;
		
		return exp;
	}
	
	public float calcFluctuatingNextLevel(float pLevel){
		float exp = 0;
		
		if(pLevel <= 15){
			exp = (float) ((Math.pow(pLevel, 3)) * ((((1 + pLevel)/3) + 24))/50);
		}
		if(pLevel > 15 || pLevel <= 36){
			exp = (float) ((Math.pow(pLevel, 3)) * ((pLevel + 14))/50);
		}
		if(pLevel > 36 || pLevel <= 100){
			exp = (float) ((Math.pow(pLevel, 3)) * (((pLevel/2) + 32))/50);
		}
		
		return exp;
	}
	
    public boolean energyTankCheck(){
    	boolean empty = false;
    	
		if(currentTank == 0){
    		if(energyReserves.get(currentTank).empty){
    			empty = true;
    			recharging = true;
    			recharged = false;
    			recharge = 0;
    		}
    	}
    	
		return empty;	
    }
    
    public boolean fullTankCheck(){
    	boolean full = false;
    	
    	if(currentTank == maxTank - 1){
    		if(energyReserves.get(currentTank).full){
    			full = true;
    		}
    	}
    	
		return full;	
    }
    
    public boolean spendEnergy(int cost){
    	
    	boolean costAccepted = false;
    	
    	if(energyReserves.get(currentTank).energyLevel - cost > 0){
    		energyReserves.get(currentTank).energyLevel -= cost;
    		costAccepted = true;
    		return costAccepted;
    	}
    	
    	if(energyReserves.get(currentTank).energyLevel - cost <= 0){
    		if(currentTank != 0){
    			int overflow = cost - energyReserves.get(currentTank).energyLevel;
    			energyReserves.get(currentTank).energyLevel = 0;
    			currentTank -= 1;
    			
    			energyReserves.get(currentTank).energyLevel -= overflow;
    			costAccepted = true;
    			return costAccepted;
    		}
    		if(currentTank == 0 && energyReserves.get(currentTank).energyLevel - cost == 0){
    			energyReserves.get(currentTank).energyLevel -= cost;
  			  	costAccepted = true;
  			  	return costAccepted;
    		}
    	}
    	
    	return costAccepted;
    }
    
    public boolean spendCheck(int cost){
    	boolean costAccepted = false;
    	
    	if(energyReserves.get(currentTank).energyLevel - cost > 0){
    		costAccepted = true;
    		return costAccepted;
    	}
    	
    	if(energyReserves.get(currentTank).energyLevel - cost <= 0){
    		if(currentTank != 0){
    			int overflow = cost - energyReserves.get(currentTank).energyLevel;
    			
    			if(energyReserves.get(currentTank - 1).energyLevel - overflow > 0){
    				costAccepted = true;
    			}
    			return costAccepted;
    		}
    		else if(currentTank == 0 && energyReserves.get(currentTank).energyLevel - cost == 0){
  			  	costAccepted = true;
  			  	return costAccepted;
    		}
    	}
    	
    	return costAccepted;
    }
    
    public void addEnergy(int amount){
    	if(energyReserves.get(currentTank).energyLevel + amount < energyReserves.get(currentTank).maxEnergyLevel){
    		energyReserves.get(currentTank).energyLevel += amount;
    	}
    	
    	if(energyReserves.get(currentTank).energyLevel + amount >= energyReserves.get(currentTank).maxEnergyLevel){
    		int overflow = (energyReserves.get(currentTank).energyLevel + amount) - energyReserves.get(currentTank).maxEnergyLevel;
    		energyReserves.get(currentTank).energyLevel = energyReserves.get(currentTank).maxEnergyLevel;
    		
    		if(currentTank != maxTank - 1){
    			currentTank += 1;
    			energyReserves.get(currentTank).energyLevel += overflow;
    		}
    	}
    }
    
	  public void renderEnergyTanks(){
		  for(EnergyTank tank : energyReserves) {
		      tank.render();
		  }
	  }
	  
	  public void updateEnergyTanks(){
		  
		  for(EnergyTank tank : energyReserves) {
		      tank.update();
		  }
		  energyTankCheck();
		  fullTankCheck();
		  
		  if(recharging){
			  recharge++;
			  if(recharge <= rechargeTime){
				  recharging = false;
				  recharged = true;
			  }
		  }
	  }
}
