package proceduralGenerator;

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
		JSONObject npcJSON = (JSONObject)jsonParser.parse(new FileReader(Constants.projectRootPath + "/WebContent/assets/data/1/npcs.json"));
		
		int cardId = 4;
		
		for(int npcId = 1 ; npcId <= 10; npcId++) {
			npcJSON.put(String.valueOf(npcId), String.valueOf(cardId));
			cardId = cardId + 4;
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newNPCJSONData = gson.toJson(npcJSON);
//		System.out.println(newNPCJSONData);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/WebContent/assets/data/1/npcs.json");
        file.write(newNPCJSONData);
        file.flush();
        file.close();
	}
}
