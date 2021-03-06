package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import proceduralGenerator.Constants;

public class CollectCard {
	public String card_id;
	JSONObject cardDetails;
	String guideMessage;
	
	public String getGuideMessage() {
		return guideMessage;
	}

	public void setGuideMessage(String guideMessage) {
		this.guideMessage = guideMessage;
	}

	public JSONObject getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(JSONObject cardDetails) {
		this.cardDetails = cardDetails;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	
	@SuppressWarnings("unchecked")
	public void addToInventory() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		
		FileReader f = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
		Object obj = jsonParser.parse(f);
		JSONObject data = (JSONObject)obj;
		
		f = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/cards.json");
		JSONObject cardObj = (JSONObject) jsonParser.parse(f);
		
		f.close();
		
		JSONArray inventory = (JSONArray) data.get("inventory");
		JSONObject map = (JSONObject) data.get("map");
		JSONObject cards = (JSONObject) map.get("cards");
		
		inventory.add(card_id);
		
		this.cardDetails = (JSONObject) cardObj.get(this.card_id);
		
		cards.remove(card_id);
		
		map.put("cards" , cards);
		
		data.put("map", map);
		
		data.put("inventory", inventory);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newMapJSONData = gson.toJson(data);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
        file.write(newMapJSONData);
        file.flush();
        file.close();
	}
	
	public void populateGuideMessage() {
		int size = Constants.guideCardMessages.length;
		int randIdx = ThreadLocalRandom.current().nextInt(0, size);
		this.guideMessage = Constants.guideCardMessages[randIdx];
	}
	
	public String execute() throws Exception {
		
		try {
			addToInventory();
			populateGuideMessage();
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return "success";
	}
}
