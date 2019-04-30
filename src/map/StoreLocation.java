package map;


import org.apache.struts2.ServletActionContext;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import proceduralGenerator.Constants;

public class StoreLocation {
	public int X;
	public int Y;
	
	
	
	public int getX() {
		return X;
	}



	public void setX(int x) {
		X = x;
	}



	public int getY() {
		return Y;
	}



	public void setY(int y) {
		Y = y;
	}



	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		JSONParser jsonParser = new JSONParser();
		
		FileReader f = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
		Object obj = jsonParser.parse(f);
		f.close();
		
		JSONObject data = (JSONObject)obj;
		
		JSONObject currentLoc = (JSONObject) data.get("currentpos");
		
		
		currentLoc.put("X", Integer.toString(X));
		currentLoc.put("Y", Integer.toString(Y));
		
		data.put("currentpos", currentLoc);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newMapJSONData = gson.toJson(data);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json");
        file.write(newMapJSONData);
        file.flush();
        file.close();
		
		return "success";
	}
}
