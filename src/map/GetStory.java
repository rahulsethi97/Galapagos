package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import proceduralGenerator.Constants;

public class GetStory {
	public String title;
	public String story;
	public String battle_conversation;
	public String before_battle_init;
	public String after_battle;
	public String after_battle_init;
	public String level;
	
	
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
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
	public String getBattle_conversation() {
		return battle_conversation;
	}
	public void setBattle_conversation(String battle_conversation) {
		this.battle_conversation = battle_conversation;
	}
	public String getBefore_battle_init() {
		return before_battle_init;
	}
	public void setBefore_battle_init(String before_battle_init) {
		this.before_battle_init = before_battle_init;
	}
	public String getAfter_battle() {
		return after_battle;
	}
	public void setAfter_battle(String after_battle) {
		this.after_battle = after_battle;
	}
	public String getAfter_battle_init() {
		return after_battle_init;
	}
	public void setAfter_battle_init(String after_battle_init) {
		this.after_battle_init = after_battle_init;
	}
	
	void populateStory() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader f = new FileReader(Constants.projectRootPath + "/assets/data/story.json");
		JSONObject storiesJSON = (JSONObject)jsonParser.parse(f);
		f.close();
		
		JSONObject storyJSON = (JSONObject) storiesJSON.get(level);
		
		this.title = (String) storyJSON.get("title");
		this.story = (String) storyJSON.get("story");
		this.battle_conversation = (String) storyJSON.get("battle_conversation");
		this.before_battle_init = (String) storyJSON.get("before_battle_init");
		this.after_battle = (String) storyJSON.get("after_battle");
		this.after_battle_init = (String) storyJSON.get("after_battle_init");
	}
	
	public String execute() throws Exception {
		populateStory();
		return "success";
	}
}
