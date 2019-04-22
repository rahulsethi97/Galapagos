package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import proceduralGenerator.Constants;

public class PopulateBattle {
	public String npc_id;
	public String new_x;
	public String new_y;
	
	

	public String getNew_x() {
		return new_x;
	}

	public void setNew_x(String new_x) {
		this.new_x = new_x;
	}

	public String getNew_y() {
		return new_y;
	}

	public void setNew_y(String new_y) {
		this.new_y = new_y;
	}

	public String getNpc_id() {
		return npc_id;
	}

	public void setNpc_id(String npc_id) {
		this.npc_id = npc_id;
	}
	
	@SuppressWarnings("unchecked")
	public void populateBattle() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(new FileReader(Constants.projectRootPath + "/WebContent/assets/data/1/map.json"));
		JSONObject data = (JSONObject)obj;
		
		JSONObject map = (JSONObject) data.get("map");
		JSONObject npcs = (JSONObject) map.get("npcs");
		
		
		JSONObject npcBattling = (JSONObject) npcs.get(this.npc_id);
		
//		System.out.println(this.npc_id);
//		System.out.println(npcBattling);
		JSONObject currentpos = (JSONObject) data.get("currentpos");
		
		this.new_x = (String) npcBattling.get("new_x");
		this.new_y = (String) npcBattling.get("new_y");
		
		currentpos.put("X" , this.new_x);
		currentpos.put("Y" , this.new_y);
		
		data.put("currentpos" , currentpos);
		
		data.put("npc_battling", npc_id);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newMapJSONData = gson.toJson(data);
//		System.out.println(newMapJSONData);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/WebContent/assets/data/1/map.json");
        file.write(newMapJSONData);
        file.flush();
        file.close();
	}
	
	public String execute() throws Exception {
//		System.out.println(npc_id);
		try {
			populateBattle();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
