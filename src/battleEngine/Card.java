package battleEngine;

public class Card {
	// @value between 10 & 100
	private int HP;
	
	// @value between 1 & 5
	private int Speed;
	
	// @value between 10 & 15
	private int Offense;
	
	// @value between 1 & 5
	private int Defense;
	
	// Fire, Water, Earth, Electric
	private String Type;
	
	// Warrior, Royalty, Scholar, Merchant
	private String Race;
	/**
	 * @return the hP
	 */
	public int getHP() {
		return HP;
	}
	/**
	 * @param hP the hP to set
	 */
	public void setHP(int hP) {
		HP = hP;
	}
	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return Speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		Speed = speed;
	}
	/**
	 * @return the offense
	 */
	public int getOffense() {
		return Offense;
	}
	public Card(int hP, int speed, int offense, int defense, String type, String race) {
		super();
		HP = hP;
		Speed = speed;
		Offense = offense;
		Defense = defense;
		Type = type;
		Race = race;
	}
	
	/**
	 * @param offense the offense to set
	 */
	public void setOffense(int offense) {
		Offense = offense;
	}
	/**
	 * @return the defense
	 */
	public int getDefense() {
		return Defense;
	}
	/**
	 * @param defense the defense to set
	 */
	public void setDefense(int defense) {
		Defense = defense;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		Type = type;
	}
	/**
	 * @return the race
	 */
	public String getRace() {
		return Race;
	}
	/**
	 * @param race the race to set
	 */
	public void setRace(String race) {
		Race = race;
	}
	

	

}
