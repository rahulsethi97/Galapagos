package proceduralGenerator;


import org.apache.struts2.ServletActionContext;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GenerateNPCs {
	@SuppressWarnings("unchecked")
	void populateNPCs() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		
		FileReader f = new FileReader(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/npcs.json");
		JSONObject npcJSON = (JSONObject)jsonParser.parse(f);
		f.close();
		
		int cardId = 4;
		
		for(int npcId = 1 ; npcId <= 10; npcId++) {
			npcJSON.put(String.valueOf(npcId), String.valueOf(cardId));
			cardId = cardId + 4;
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newNPCJSONData = gson.toJson(npcJSON);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/assets/data/" + ServletActionContext.getRequest().getSession().getId() + "/npcs.json");
        file.write(newNPCJSONData);
        file.flush();
        file.close();
	}
}
