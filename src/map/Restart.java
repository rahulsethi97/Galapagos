package map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class Restart {
	public String execute() throws Exception {		
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			System.out.println("Session ID: "+ServletActionContext.getRequest().getSession().getId());
			
			if(session.getAttribute("GameStarted") != null) {
				session.removeAttribute("GameStarted");
			}
			
			session.setAttribute("ShowEnd", "0");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
