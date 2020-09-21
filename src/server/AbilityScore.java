package monster;

import java.io.Serializable;
import org.json.JSONObject;

public class AbilityScore implements Serializable {
	private int score;
	boolean proficient;
	private String stat;
	
	public AbilityScore() {
		score = 10;
		proficient = false;
	}
	
	/**
	 * Constructs a monster ability score from json styled string
	 */
	AbilityScore(String jsonString) {
		this(new JSONObject(jsonString));
	}
	
	/**
	 * Constructs a monster ability from json object
	 */
	AbilityScore(JSONObject jsonObj) {
		try {
			stat = jsonObj.getString("stat");
			score = jsonObj.getInt("score");
			proficient = jsonObj.getBoolean("proficient");
		} catch (Exception e) {
			System.out.println("Error in Action(JsonObject): " + e.getMessage());
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean getProficient() {
		return proficient;
	}
	
	public void setStat(String stat) {
		this.stat = stat;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setProficient(boolean proficient) {
		this.proficient = proficient;
	}
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("stat", stat);
			obj.put("score", score);
			obj.put("proficient", proficient);
		} catch (Exception e) {
			System.out.println("Error in Ability.toJson: " + e.getMessage());
		}
		
		return obj;
	}
}
