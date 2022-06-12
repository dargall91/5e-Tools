package encounter;

import java.io.Serializable;
import org.json.JSONObject;

/**
 * MonsterData is the metadata for a Monster in an Encounter.
 * i.e., it is is how many instances of the monster are part
 * of the encounter, their initiative roll, reinforcement
 * status, etc
 */

public class MonsterData implements Serializable {
	private String monster;
	private int quantity, xp, initiative;
	private boolean minion, reinforcement, invisible;
	
	/**
	 * Initializes a MonsterData object from JSON
	 * 
	 * @param json The JSON object that contains the data
	 */
	public MonsterData(JSONObject json) {
		try {
			monster = json.getString("monster");
			quantity = json.getInt("quantity");
			xp = json.getInt("xp");
			minion = json.getBoolean("minion");
			reinforcement = json.getBoolean("reinforcement");
			
			//invisible is a new json key that did not previously exist,
			//to prevent exceptions from being thrown when reading old
			//json files it must now be checked for
			if (json.has("invisible")) {
				invisible = json.getBoolean("invisible");
			} else {
				invisible = false;
			}
			
			initiative = json.getInt("initiative");
		} catch (Exception e) {
			System.out.println("Error in MonsterData(json) " + e.getMessage());
		}
	}
	
	/**
	 * Initializes a MonsterData object from just a 
	 * monster name and XP value
	 * 
	 * @param monster The name of the monster
	 * @param xp the monster's XP value
	 */
	public MonsterData(String monster, int xp) {
		this.monster = monster;
		this.xp = xp;
		quantity = 1;
		minion = false;
		reinforcement = false;
		invisible = false;
		initiative = 0;
	}
	
	public String getMonster() {
		return monster;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getXP() {
		return xp;
	}
	
	public int getInitiative() {
		return initiative;
	}
	
	public boolean isMinion() {
		return minion;
	}
	
	public boolean isReinforcement() {
		return reinforcement;
	}

	public boolean isInvisible() {
		return invisible;
	}
	
	public void setMonster(String monster) {
		this.monster = monster;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setXP(int xp) {
		this.xp = xp;
	}
	
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}
	
	public void setMinion(boolean minion) {
		this.minion = minion;
	}
	
	public void setReinforcement(boolean reinforcement) {
		this.reinforcement = reinforcement;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}
	
	public String toString() {
		return "monster: " + monster + " quantity: " + quantity;
	}
	
	/**
	 * Serializes this MonsterData object into
	 * a JSON object
	 *
	 * @return the serialized JSON object
	 */
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("monster", monster);
			obj.put("quantity", quantity);
			obj.put("xp", xp);
			obj.put("minion", minion);
			obj.put("reinforcement", reinforcement);
			obj.put("invisible", invisible);
			obj.put("initiative", initiative);
		} catch (Exception e) {
			System.out.println("Error in MonsterData.toJson: " + e.getMessage());
		}
		
		return obj;
	}
}

