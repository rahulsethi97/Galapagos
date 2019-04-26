package map;


import org.apache.struts2.ServletActionContext;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import battleEngine.BattleBanter;
import battleEngine.BattleEngine;
import battleEngine.Card;
import proceduralGenerator.Constants;
import proceduralGenerator.StartProceduralGenerator;

public class StartBattle {
	public String npc_card_id;
	public String player_card_id;
	public String npc_id;
	public String result;
	public int level;
	public String lostConversation[];
	public String lesson;
	
	

	public String getLesson() {
		return lesson;
	}

	public void setLesson(String lesson) {
		this.lesson = lesson;
	}

	public String[] getLostConversation() {
		return lostConversation;
	}

	public void setLostConversation(String lostConversation[]) {
		this.lostConversation = lostConversation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNpc_card_id() {
		return npc_card_id;
	}

	public void setNpc_card_id(String npc_card_id) {
		this.npc_card_id = npc_card_id;
	}

	public String getPlayer_card_id() {
		return player_card_id;
	}

	public void setPlayer_card_id(String player_card_id) {
		this.player_card_id = player_card_id;
	}

	@SuppressWarnings("unchecked")
	public void removeNPCFromJSON() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json"));
		JSONObject data = (JSONObject)obj;
		
		npc_id = (String) data.get("npc_battling");
		
		JSONObject map = (JSONObject) data.get("map");
		JSONObject npcs = (JSONObject) map.get("npcs");
		
		level = Integer.parseInt((String) data.get("level"));
		
		npcs.remove(npc_id);
		
		map.put("npcs" , npcs);
		
		data.put("map", map);
		data.put("npc_battling", "-1");
		data.put("show_story", "1");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newMapJSONData = gson.toJson(data);
//		System.out.println(newMapJSONData);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
        file.write(newMapJSONData);
        file.flush();
        file.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public void removeCardFromMap() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json"));
		JSONObject data = (JSONObject)obj;
		
		JSONArray inventory = (JSONArray) data.get("inventory");
		
		JSONArray list = new JSONArray();     
		int len = inventory.size();
		if (inventory != null) { 
		   for (int i=0;i<len;i++){ 
			   if(!(inventory.get(i).toString().equals(player_card_id))) {
				   list.add(inventory.get(i));
			   }
		   } 
		}
		
		data.put("inventory", list);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newMapJSONData = gson.toJson(data);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
        file.write(newMapJSONData);
        file.flush();
        file.close();
		
	}
	
	
	public String getWinner() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/cards.json"));
		JSONObject data = (JSONObject)obj;
		
		JSONObject playerCardObj = (JSONObject) data.get(this.player_card_id);
		JSONObject npcCardObj = (JSONObject) data.get(this.npc_card_id);
		
		Card playerCard = new Card(	Integer.parseInt((String)playerCardObj.get("HP")) , 
									Integer.parseInt((String)playerCardObj.get("Speed")) , 
									Integer.parseInt((String)playerCardObj.get("Offense")) , 
									Integer.parseInt((String)playerCardObj.get("Defense")) , 
									(String)playerCardObj.get("Type") , 
									(String)playerCardObj.get("Race")
								  );
		
		Card npcCard = new Card(Integer.parseInt((String)npcCardObj.get("HP")) , 
								Integer.parseInt((String)npcCardObj.get("Speed")) , 
								Integer.parseInt((String)npcCardObj.get("Offense")) , 
								Integer.parseInt((String)npcCardObj.get("Defense")) , 
								(String)npcCardObj.get("Type") , 
								(String)npcCardObj.get("Race")
							  );
		
		BattleEngine battle = new BattleEngine(playerCard, npcCard);
		String winner = battle.retWinner();
		System.out.println(battle.battleText() + " sadksaokd ");
		this.lesson = battle.battleText();
		
		
		if(winner.equals("Card2")) {
			HttpSession session = ServletActionContext.getRequest().getSession();
			System.out.println("Session ID: "+ServletActionContext.getRequest().getSession().getId());
			String sessionId = session.getId();
			
			session.removeAttribute("GameStarted");
			BattleBanter bb = new BattleBanter(npcCard, playerCard);
			this.lostConversation = bb.retBanter();
		}
		
		return winner;
	}
	
	public void startBattle() throws FileNotFoundException, IOException, ParseException {
		removeNPCFromJSON();
		removeCardFromMap();
		this.result = getWinner();
		if(result == "Card1") {
			if(level == 1) {
				HttpSession session = ServletActionContext.getRequest().getSession();
				System.out.println("Session ID: "+ServletActionContext.getRequest().getSession().getId());
				String sessionId = session.getId();
				
				session.setAttribute("ShowEnd", "1");
			}
			StartProceduralGenerator gameEngine = new StartProceduralGenerator(level+1);
			gameEngine.startGenerator();
		}
	}
	
	public String execute() throws Exception {
//		System.out.println(npc_card_id + " VS " + player_card_id);
		try {
			startBattle();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
