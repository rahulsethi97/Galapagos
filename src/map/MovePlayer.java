package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import proceduralGenerator.Constants;

import org.apache.struts2.ServletActionContext;

public class MovePlayer {
	public String npc_id;
	public String new_x;
	public String new_y;
	public String isInventoryEmpty;

	public String getIsInventoryEmpty() {
		return isInventoryEmpty;
	}

	public void setIsInventoryEmpty(String isInventoryEmpty) {
		this.isInventoryEmpty = isInventoryEmpty;
	}

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
		FileReader f = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
		Object obj = jsonParser.parse(f);
		f.close();
		JSONObject data = (JSONObject)obj;
		
		JSONObject map = (JSONObject) data.get("map");
		JSONObject npcs = (JSONObject) map.get("npcs");
		
		JSONArray inventory = (JSONArray) data.get("inventory");
		
		if(inventory.size() == 0) {
			this.isInventoryEmpty = "1";
		}else {
			this.isInventoryEmpty = "0";
		}
		
		JSONObject npcBattling = (JSONObject) npcs.get(this.npc_id);
		
		JSONObject currentpos = (JSONObject) data.get("currentpos");
		
		this.new_x = (String) npcBattling.get("new_x");
		this.new_y = (String) npcBattling.get("new_y");
		
		currentpos.put("X" , this.new_x);
		currentpos.put("Y" , this.new_y);
		
		data.put("currentpos" , currentpos);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newMapJSONData = gson.toJson(data);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
        file.write(newMapJSONData);
        file.flush();
        file.close();
	}
	
	public String execute() throws Exception {
		try {
			populateBattle();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
