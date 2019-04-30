package proceduralGenerator;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import battleEngine.BattleEngine;
import battleEngine.Card;

public class GenerateCards {
	public int currentLevel;
	JSONObject cardObject;
	
    int getRandomHP() {
		return ThreadLocalRandom.current().nextInt(10, 101);	
    }
    
    int getRandomSpeed() {
		return ThreadLocalRandom.current().nextInt(1, 6);	
    }
    
    int getRandomOffense() {
		return ThreadLocalRandom.current().nextInt(10, 16);	
    }
    
    int getRandomDefense() {
		return ThreadLocalRandom.current().nextInt(1, 6);	
    }
    
    String getRandomType() {
    	int idx = ThreadLocalRandom.current().nextInt(0, 4);
		return Constants.cardTypes[idx];	
    }
    
    String getRandomRace() {
    	int idx = ThreadLocalRandom.current().nextInt(0, 4);
		return Constants.cardRaces[idx];	
    }
    
    String getRandomName() {
    	int idx = ThreadLocalRandom.current().nextInt(0, 4);
		return Constants.cardNames[idx];	
    }
	
	@SuppressWarnings("unchecked")
	void populateCards() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader cardFile = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/cards.json");
		JSONObject cardJSON = (JSONObject)jsonParser.parse(cardFile);
		cardFile.close();
		List<Card> cardList = new ArrayList<Card>();
		for(int cardId = 1 ; cardId <= 40; cardId++) {
			JSONObject cardObj = new JSONObject();
			
			if((cardId%4) == 0){
				Card c = null;
				
				int start = cardId - 4;
				boolean flag = true;
				
				while(flag) {
					c = new Card(getRandomHP(), getRandomSpeed(), getRandomOffense(), getRandomDefense(), getRandomType(), getRandomRace());
					for(int i = start ; i < (start + 3) ; i++) {
						BattleEngine bEng = new BattleEngine(cardList.get(i), c);
						if(bEng.retWinner() == "Card1") {
							flag = false;
						}
					}
				}
				
				cardObj.put("Name", getRandomName());
				cardObj.put("HP", String.valueOf(c.getHP()));
				cardObj.put("Speed", String.valueOf(c.getSpeed()));
				cardObj.put("Offense", String.valueOf(c.getOffense()));
				cardObj.put("Defense", String.valueOf(c.getDefense()));
				cardObj.put("Type", String.valueOf(c.getType()));
				cardObj.put("Race", String.valueOf(c.getRace()));
				cardList.add(c);
			}else {
				Card c = new Card(getRandomHP(), getRandomSpeed(), getRandomOffense(), getRandomDefense(), getRandomType(), getRandomRace());
				cardObj.put("Name", getRandomName());
				cardObj.put("HP", String.valueOf(c.getHP()));
				cardObj.put("Speed", String.valueOf(c.getSpeed()));
				cardObj.put("Offense", String.valueOf(c.getOffense()));
				cardObj.put("Defense", String.valueOf(c.getDefense()));
				cardObj.put("Type", String.valueOf(c.getType()));
				cardObj.put("Race", String.valueOf(c.getRace()));
				cardList.add(c);
			}
			cardJSON.put(String.valueOf(cardId), cardObj);
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newCardJSONData = gson.toJson(cardJSON);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/cards.json");
        file.write(newCardJSONData);
        file.flush();
        file.close();
	}
}
