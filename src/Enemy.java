
public class Enemy{

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
			
	public Enemy(String name, float startingLevel, CharacterClass cClass, 
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
		
		critRatio = dexterity/15;
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
	
	public float attackCalc(float eFortitude, float baseAttackPower){
		float rand = 0;
		float max = 0.85f, min = 1, maxCrit = 0, minCrit = 100;
		
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
		float max = 0.85f, min = 1, maxCrit = 0, minCrit = 100;
		
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
		float max = 0.85f, min = 1, maxCrit = 0, minCrit = 100;
		
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
	
}
