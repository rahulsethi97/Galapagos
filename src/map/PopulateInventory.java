package map;

import org.apache.struts2.ServletActionContext;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import proceduralGenerator.Constants;

public class PopulateInventory {
	public JSONObject inventoryData;
	public JSONObject cardDetails;
	public String opponentCard;
	public String level;
	public String npcBattling;

	public String getNpcBattling() {
		return npcBattling;
	}

	public void setNpcBattling(String npcBattling) {
		this.npcBattling = npcBattling;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOpponentCard() {
		return opponentCard;
	}

	public void setOpponentCard(String opponentCard) {
		this.opponentCard = opponentCard;
	}

	public JSONObject getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(JSONObject cardDetails) {
		this.cardDetails = cardDetails;
	}

	public JSONObject getInventoryData() {
		return inventoryData;
	}

	public void setInventoryData(JSONObject inventoryData) {
		this.inventoryData = inventoryData;
	}

	@SuppressWarnings("unchecked")
	public void populate() {
		JSONParser jsonParser = new JSONParser();
		JSONObject obj1 = null;
		JSONObject obj2 = null;
		JSONObject obj3 = null;
		try {
			FileReader f = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
			obj1 = (JSONObject)jsonParser.parse(f);
			f.close();
			
			f = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/cards.json");
			obj2 = (JSONObject)jsonParser.parse(f);
			f.close();
			
			f = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/npcs.json");
			obj3 = (JSONObject)jsonParser.parse(f);
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.level = (String) obj1.get("level");
		this.npcBattling = (String) obj1.get("npc_battling");
		JSONArray inventory = (JSONArray) obj1.get("inventory");
		inventoryData = new JSONObject();
		for (int i = 0; i < inventory.size(); i++) {
			String cardId = inventory.get(i).toString();			
			inventoryData.put(cardId, obj2.get(cardId));
		}
		cardDetails = obj2;
		opponentCard = (String) obj3.get(obj1.get("npc_battling"));
	}
	
	public String execute() throws Exception {
		populate();
		return "success";
	}
}
