package map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import proceduralGenerator.Constants;



public class ApplicationInitialization implements ServletContextListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Application Down");
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		String currentPath = this.getClass().getClassLoader().getResource("").getPath();
		String folders[] = currentPath.split("/");
		
		String projectPath = "";
		
		int len = folders.length;
		int ctr = 0;
		for(String folder: folders) {
			if(ctr < len-2){
				projectPath += folder + "/";
				ctr++;
			}else {
				break;
			}
		}
		projectPath = projectPath.substring(0, projectPath.length() - 1);
		
		System.out.println(projectPath);
		
		Constants.projectRootPath = projectPath;
		System.out.println("Application Up and Running");
	}

}
