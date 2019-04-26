package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import proceduralGenerator.Constants;

public class GetBattleGuidance {
	public String npcCardId;
	public String guideMessage;
	
	public String getNpcCardId() {
		return npcCardId;
	}

	public void setNpcCardId(String npcCardId) {
		this.npcCardId = npcCardId;
	}

	public String getGuideMessage() {
		return guideMessage;
	}

	public void setGuideMessage(String guideMessage) {
		this.guideMessage = guideMessage;
	}
	
	public void populateGuideMessage() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject cards = (JSONObject) jsonParser.parse(new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/cards.json"));
		JSONObject npcCardDetails = (JSONObject) cards.get(this.npcCardId);
		
		String npcRace = (String) npcCardDetails.get("Race");
		String npcType = (String) npcCardDetails.get("Type");
		int randNum = ThreadLocalRandom.current().nextInt(1, 3);
//		System.out.println(randNum);
		if(randNum == 1) {
			if(npcRace.equals("Warrior")) {
				this.guideMessage = "Remember!! Cards with Race 'Warrior' increases its HP";
			}else if(npcRace.equals("Scholar")) {
				this.guideMessage = "Let me tell you that Cards with Race 'Scholar' has more effective Speed";
			}else if(npcRace.equals("Royalty")) {
				this.guideMessage = "Interesting! The opponent card's race is Royalty. Expect more HP";
			}else if(npcRace.equals("Merchant")) {
				this.guideMessage = "Lookout for enhanced Speed. Merchant cards are rare.";
			}
		}else {
			if(npcType.equals("Fire")) {
				this.guideMessage = "Remember!! Water type cards are effective against Fire type cards.";
			}else if(npcType.equals("Earth")) {
				this.guideMessage = "Try using a Fire type card. They have advantage over Earth type cards.";
			}else if(npcType.equals("Electric")) {
				this.guideMessage = "Interesting! The opponent card's type is Electric. Try using an Earth type card.";
			}else if(npcType.equals("Water")) {
				this.guideMessage = "Electric cards are better than Water cards. Remember it!!";
			}
		}
	}
	
	public String execute() throws Exception {
//		System.out.println(this.npcCardId);
		try {
			populateGuideMessage();
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return "success";
	}
}
