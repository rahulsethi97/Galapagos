package map;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import proceduralGenerator.Constants;

public class IntroAndEndStory {
	public String intro;
	public JSONObject end;
	
	public JSONObject getEnd() {
		return end;
	}

	public void setEnd(JSONObject end) {
		this.end = end;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public void populateGuideMessage() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject storyJSON = (JSONObject) jsonParser.parse(new FileReader(Constants.projectRootPath + "/assets/data/story.json"));
		this.intro = (String) storyJSON.get("0");
		this.end = (JSONObject) storyJSON.get("end");
	}
	
	public String execute() throws Exception {
//		System.out.println(this.npcCardId);
		try {
			populateGuideMessage();
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return "success";
	}
}
