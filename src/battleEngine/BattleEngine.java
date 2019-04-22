package battleEngine;

public class BattleEngine {
	
	//Card1 is user card and Card2 is NPC card
	Card Card1;
	Card Card2;
	
	public BattleEngine(Card c1, Card c2){
		Card1=c1;
		Card2=c2;
	}
	
	public String retWinner(){
		
		//set races
		String r1 = Card1.getRace();
		String r2 = Card2.getRace();
		
		//set types
		String t1 = Card1.getType();
		String t2 = Card2.getType();
		
		//set offenses
		float o1 = Card1.getOffense();
		float o2 = Card2.getOffense();
		
		//set defenses
		float d1 = Card1.getDefense();
		float d2 = Card2.getDefense();
		
		//set speeds
		float sp1 = Card1.getSpeed();
		float sp2 = Card2.getSpeed();
		
		// set decrementing counters
		float hp1 = Card1.getHP();
		float hp2 = Card2.getHP();
		
		//give Race multipliers
		if(r1=="Warrior" || r1=="Royalty"){
			hp1=hp1*1.2f;
		}
		if(r2=="Warrior" || r2=="Royalty"){
			hp2=hp2*1.2f;
		}
		
		if(r1=="Scholar" || r1=="Merchant"){
			sp1=sp1*1.2f;
		}
		if(r2=="Scholar" || r2=="Merchant"){
			sp2=sp2*1.2f;
		}
		
		//give Type advantages to offense
		if(t1=="Fire" & t2=="Earth"){
			o1=o1*1.2f;
		}
		if(t1=="Earth" & t2=="Electric"){
			o1=o1*1.2f;
		}
		if(t1=="Electric" & t2=="Water"){
			o1=o1*1.2f;
		}
		if(t1=="Water" & t2=="Fire"){
			o1=o1*1.2f;
		}
		
		if(t2=="Fire" & t1=="Earth"){
			o2=o2*1.2f;
		}
		if(t2=="Earth" & t1=="Electric"){
			o2=o2*1.2f;
		}
		if(t2=="Electric" & t1=="Water"){
			o2=o2*1.2f;
		}
		if(t2=="Water" & t1=="Fire"){
			o2=o2*1.2f;
		}
		
		// calculate effective offenses
		float eO1=o1-d2;
		if (eO1<0){
			return "Card2";
		}
				
		float eO2=o2-d1;
		if (eO2<0){
			return "Card1";
		}
		
		//provide speed advantages
		float speedRatio=sp1/sp2;
		
		if(speedRatio<1){
			hp2=hp2/speedRatio;
		} else {
			hp1=hp1*speedRatio;
		}
		
		while(hp1>0 & hp2>0){
			hp1=hp1-eO2;
			hp2=hp2-eO1;
		}
		
		if (hp1<=0){
			return "Card2";
		} else {
			return "Card1";
		}
		
		
	}
}
