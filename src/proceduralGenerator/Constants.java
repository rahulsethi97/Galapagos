package proceduralGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
	public static Map<String , List<String>> coordinates = new HashMap<String , List<String>>();
	
	public static final String[] cardNames = {"The prophet", "King Slayer", "The Wizard" , "The Lord"};
	
	public static final String[] cardTypes = {"Fire", "Water", "Earth", "Electric"};
	
	public static final String[] cardRaces = {"Warrior", "Royalty", "Scholar", "Merchant"};
	
	public static final String projectRootPath = "/home/rsethi3/eclipse-workspace/Galapagos";
	
	public static final String[] guideCardMessages = {	
														"Nice.!! That's a really nice card you got.", 
														"You just collected a very rare card over there.", 
														"You are one step closer to your goal.", 
														"You might want to hold on to that.",
														"Remember you can check inventory anytime."
													 };
}
