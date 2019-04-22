package map;

import proceduralGenerator.*;

public class StartGame {
	public String execute() throws Exception {
		
		try {
			StartProceduralGenerator gameEngine = new StartProceduralGenerator(1);
			gameEngine.startGenerator();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
