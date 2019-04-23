package battleEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleBanter {
	Card c1;
	Card c2;
		
	public BattleBanter(Card c1, Card c2){
		this.c1 = c1;
		this.c2 = c2;
	}
	
	String getDominatingParameter(){
	    // Logic to find the dominating parameter
	    boolean found = false;
	
	    if(!found && c1.getSpeed() > c2.getSpeed()){
	        found = true;
	        return "speed";
	    }
	    if(!found && c1.getOffense() > c2.getOffense()){
	        found = true;
	        return "offense";
	    }
	    if(!found && c1.getDefense() > c2.getDefense()){
	        found = true;
	        return "defense";
	    }
	    if(!found && c1.getHP() > c2.getHP()){
	        found = true;
	        return "Hp";
	    }
	    return null;
	}
	
	public String[] retBanter()
	{
		String domParam = getDominatingParameter();
		System.out.println(domParam);
		String input1 = "What made you think you could have defeated my" + domParam + "?. Well! Giving you a tough fight was in itself a victory. You loser! Think anything to make yourself feel better, however,the facts won't change.";
		String[] str1 = input1.split("\\.");
		
		String input2= "This fight was an insult to me as my victory was so obvious with my" + domParam + ". I may have lost this time, however, wait for the day when I crush your ego. I will wait for the day only to defeat you again.";
		String[] str2 = input2.split("\\.");
		
		String input3 = "Only a foolish can think that he can survive my attack of" + domParam + ". Well! I know your weaknesses now, There is always a silver lining in the cloud. Hahahaha ! I like your confidence.";
		String[] str3 = input3.split("\\.");
		
		String input4 = "I expected a good fight , but you made my victory so easy. Well! then why not have another battle? I will definately defeat your" + domParam + "this time. I don't waste time on morons.";
		String[] str4 = input4.split("\\.");
		
		String input5 = "You pulled up a good fight , didn't expect you to survive even for a minute in front of my " + domParam + ". I wish I had a little more time, then, your defeat was sure. Hahahahaha ! Kid, don't challengeg the GOD of this game.";
		String[] str5 = input5.split("\\.");
		
		String input6 = "Everybody here knows about my strength of " + domParam + ", it was impossible for you to beat me. One day I will crush your over confidence. Let time decide it, you kid.";
		String[] str6 = input6.split("\\.");
		
		String input7 = "You made a big mistake by challenging the lord of "+ domParam +". Fighting with a novice is not my type. I really like your attitude.";
		String[] str7 = input7.split("\\.");
		
		List<String[]>  mylist = new ArrayList<String[]>(); 
		
		mylist.add(str1); 
		mylist.add(str2); 
		mylist.add(str3); 
		mylist.add(str4); 
		mylist.add(str5); 
		mylist.add(str6); 
		mylist.add(str7); 
		
		for(String[] model : mylist) {
			System.out.println(java.util.Arrays.toString(model));
        }
		
		Collections.shuffle(mylist);
		return mylist.get(0);      
	}
}
