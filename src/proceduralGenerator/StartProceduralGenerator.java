package proceduralGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StartProceduralGenerator {
	public int level;

	public StartProceduralGenerator(int level) {
		this.level = level;
	}
	
	@SuppressWarnings("unchecked")
	void populateCoordinates() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		
//		System.out.println(System.getProperty("user.dir"));
		
		JSONObject coordinate = (JSONObject)jsonParser.parse(new FileReader(Constants.projectRootPath + "/WebContent/assets/data/coordinates.json"));
		
		coordinate.keySet().forEach(keyStr ->
	    {
	    	String key = (String) keyStr;
	        JSONObject obj = (JSONObject) coordinate.get(keyStr);
	        Constants.coordinates.put(key , new ArrayList<String>());
	        
	        Constants.coordinates.get(key).add((String)obj.get("X"));
	        Constants.coordinates.get(key).add((String)obj.get("Y"));
	        Constants.coordinates.get(key).add((String)obj.get("new_x"));
	        Constants.coordinates.get(key).add((String)obj.get("new_y"));
	    });
		
//		for (String key : Constants.coordinates.keySet())
//		{
//		   System.out.println(key + "->" + Arrays.toString(Constants.coordinates.get(key).toArray()));
//		}
		
	}
	
	public void startGenerator() throws FileNotFoundException, IOException, ParseException {
		if(level == 1) {
			populateCoordinates();
			GenerateCards cardGen = new GenerateCards();
			GenerateNPCs npcGen = new GenerateNPCs();
			cardGen.populateCards();
			npcGen.populateNPCs();
		}
		PopulateMap popMap = new PopulateMap(this.level);
		popMap.populateMap();
	}
	
}
