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

public class ShowStory {
	public String show_story;
	public String level;
	public String title;
	public String story;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getShow_story() {
		return show_story;
	}

	public void setShow_story(String show_story) {
		this.show_story = show_story;
	}
	
	public void populateStory() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject storiesJSON = (JSONObject)jsonParser.parse(new FileReader(Constants.projectRootPath + "/WebContent/assets/data/story.json"));
		
		JSONObject storyJSON = (JSONObject) storiesJSON.get(level);
		
		this.title = (String) storyJSON.get("title");
		this.story = (String) storyJSON.get("story");
	}
	
	@SuppressWarnings("unchecked")
	public void populate() throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject obj1 = null;
		
		try {
			obj1 = (JSONObject)jsonParser.parse(new FileReader(Constants.projectRootPath + "/WebContent/assets/data/1/map.json"));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.show_story = (String) obj1.get("show_story");
		
		populateStory();
		
//		System.out.println("Here1");
//		System.out.println(this.show_story);
		if(this.show_story.equals("1")) {
//			System.out.println("Here2");
			obj1.put("show_story", "0");
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String newMapJSONData = gson.toJson(obj1);
		
		FileWriter file = new FileWriter(Constants.projectRootPath + "/WebContent/assets/data/1/map.json");
        file.write(newMapJSONData);
        file.flush();
        file.close();
	}
	
	public String execute() throws Exception {
		populate();
		return "success";
	}
}
