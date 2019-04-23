package proceduralGenerator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.util.Pair;

public class PopulateMap {
	JSONObject data;
	int level;
	public PopulateMap(int level) {
		data = new JSONObject();
		this.level = level;
	}
	
	boolean isCoordinateAvailable(String X , String Y) throws FileNotFoundException, IOException, ParseException {
		
		
		JSONObject mapData = (JSONObject) this.data.get("map");
		
		JSONObject npcs = (JSONObject) mapData.get("npcs");
		JSONObject cardsInMap = (JSONObject) mapData.get("cards");
		
		for (Object key : npcs.keySet()) {
			if(npcs.get(key) instanceof JSONObject) {
//				System.out.println(npcs.get(key));
				JSONObject npc = (JSONObject) npcs.get(key);
				if(npc.get("X").equals(X) || npc.get("Y").equals(Y)) {
					return false;
				}
			}
		}
		
		for (Object key : cardsInMap.keySet()) {
			if(cardsInMap.get(key) instanceof JSONObject) {
				JSONObject card = (JSONObject) cardsInMap.get((String) key);
				if(card.get("X").equals(X) || card.get("Y").equals(Y)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	String getAvailableCoordinate() throws FileNotFoundException, IOException, ParseException {
		int size = Constants.coordinates.size();
		
		while(true) {
			int randIdx = ThreadLocalRandom.current().nextInt(1, size+1);
			if(isCoordinateAvailable(Constants.coordinates.get(String.valueOf(randIdx)).get(0) , Constants.coordinates.get(String.valueOf(randIdx)).get(1))) {
				return String.valueOf(randIdx);
			}
		}	
	}
	
	@SuppressWarnings("unchecked")
	void populateLevel() {
		this.data.put("level", String.valueOf(level));
	}
	
	@SuppressWarnings("unchecked")
	void populateNPCBattling() {
		this.data.put("npc_battling", "-1");
	}
	
	void populateData() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		if(level == 1) {
			JSONObject mapFrame = (JSONObject)jsonParser.parse(new FileReader(Constants.projectRootPath + "/WebContent/assets/data/mapFrame.json"));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			String mapData = gson.toJson(mapFrame);
//			System.out.println(mapData);
			
			FileWriter file = new FileWriter(Constants.projectRootPath + "/WebContent/assets/data/1/map.json");
	        file.write(mapData);
	        file.flush();
	        file.close();
		}
		this.data = (JSONObject)jsonParser.parse(new FileReader(Constants.projectRootPath + "/WebContent/assets/data/1/map.json"));
	}

	@SuppressWarnings("unchecked")
	void populateNPC() throws FileNotFoundException, IOException, ParseException {
		
		
		JSONObject map = (JSONObject) this.data.get("map");
		JSONObject npcs = (JSONObject) map.get("npcs");
		
		String coordinateIdx = getAvailableCoordinate();
		
		JSONObject newNPC = new JSONObject();
		
		newNPC.put("X", Constants.coordinates.get(coordinateIdx).get(0));
		newNPC.put("Y", Constants.coordinates.get(coordinateIdx).get(1));
		newNPC.put("new_x", Constants.coordinates.get(coordinateIdx).get(2));
		newNPC.put("new_y", Constants.coordinates.get(coordinateIdx).get(3));
		
		npcs.put(String.valueOf(level), newNPC);
		map.put("npcs", npcs);
		
		this.data.put("map", map);
	}
	
	@SuppressWarnings("unchecked")
	void populateCards() throws FileNotFoundException, IOException, ParseException {
		
		JSONObject map = (JSONObject) this.data.get("map");
		JSONObject cards = (JSONObject) map.get("cards");
		
		int cardId = 4*(level-1) + 1;
		int endCardId = cardId + 2;
		for( ; cardId <= endCardId ; cardId++){
			String coordinateIdx = getAvailableCoordinate();
			JSONObject card = new JSONObject();
			
			card.put("X", Constants.coordinates.get(coordinateIdx).get(0));
			card.put("Y", Constants.coordinates.get(coordinateIdx).get(1));
			cards.put(String.valueOf(cardId), card);
		}
				
		map.put("cards", cards);
		
		this.data.put("map", map);
	}
	
	void pushToMap() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String mapData = gson.toJson(this.data);
//		System.out.println(mapData);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/WebContent/assets/data/1/map.json");
        file.write(mapData);
        file.flush();
        file.close();
	}
	
	public void populateMap() throws FileNotFoundException, IOException, ParseException {
		populateData();
		populateLevel();
		populateNPCBattling();
		populateNPC();
		populateCards();
		pushToMap();
	}
}
