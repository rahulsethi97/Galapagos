package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import proceduralGenerator.Constants;

import org.apache.struts2.ServletActionContext;
public class MapDetails {
	public JSONObject mapData;

	public JSONObject getMapData() {
		return mapData;
	}

	public void setMapData(JSONObject mapData) {
		this.mapData = mapData;
	}
	
	public void populate() {
		JSONParser jsonParser = new JSONParser();
		Object obj = null;
		try {
			obj = jsonParser.parse(new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/map.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapData = (JSONObject)obj;
	}
	
	public String execute() throws Exception {
		populate();
		return "success";
	}
	
}
