public class CharacterClass {
	
	String name;
	
	float modSTR = 0;
	float modDEX = 0;
	float modCON = 0;
	float modINT = 0;
	float modWIS = 0;
	float modCHA = 0;
	
	float modFort = 0;
	float modMark = 0;
	float modMach = 0;
	float modMag = 0;
	float modMagFort = 0;
	
	public CharacterClass(String name,
			float sMod, float dMod, float cMod, float iMod, float wMod, float chMod
			,float fortMod, float markMod, float machMod, float magMod, float magFortMod){
		
		this.name = name;
		
		modSTR = sMod;
		modDEX = dMod;
		modCON = cMod;
		modINT = iMod;
		modWIS = wMod;
		modCHA = chMod;
		
		modFort = fortMod;
		modMark = markMod;
		modMach = machMod;
		modMag = magMod;
		modMagFort = magFortMod;
	}

}
