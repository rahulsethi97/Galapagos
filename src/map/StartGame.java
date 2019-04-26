package map;

import java.io.File;
import java.io.FileWriter;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import proceduralGenerator.Constants;
import proceduralGenerator.StartProceduralGenerator;

public class StartGame {
	public String execute() throws Exception {
		
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			System.out.println("Session ID: "+ServletActionContext.getRequest().getSession().getId());
			String sessionId = session.getId();
			
			session.setAttribute("GameStarted", "1");
			session.setAttribute("ShowEnd", "0");
			
			JSONObject emptyObj = new JSONObject();
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			String newMapJSONData = gson.toJson(emptyObj);
//			System.out.println(newMapJSONData);
			
			File theDir = new File(Constants.projectRootPath + "/assets/data/" + sessionId);
			
			if (!theDir.exists()) {
			    System.out.println("creating directory: " + theDir.getName());
			    boolean result = false;

			    try{
			        theDir.mkdir();
			        result = true;
			    } 
			    catch(SecurityException se){
			        //handle it
			    }        
			    if(result) {    
			        System.out.println("DIR created");  
			    }
			}
			
			System.out.println(Constants.projectRootPath + "/assets/data");
			FileWriter file1 = new FileWriter(Constants.projectRootPath + "/assets/data/" + sessionId + "/map.json");
			FileWriter file2 = new FileWriter(Constants.projectRootPath + "/assets/data/" + sessionId + "/cards.json");
			FileWriter file3 = new FileWriter(Constants.projectRootPath + "/assets/data/" + sessionId + "/npcs.json");
	       
	        
			file1.write(newMapJSONData);
			file2.write(newMapJSONData);
			file3.write(newMapJSONData);
	        
			file1.flush();
			file2.flush();
			file3.flush();
	        
			file1.close();
			file2.close();
			file3.close();
//			ServletActionContext.getRequest().getSession().getId() = "1";
			StartProceduralGenerator gameEngine = new StartProceduralGenerator(1);
			gameEngine.startGenerator();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
