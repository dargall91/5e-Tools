package tracker;

import monster.Monster;
import player.*;
import encounter.MonsterData;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SimpleCombatant implements Comparable<SimpleCombatant> {
	private boolean reinforcement;
	private String name;
	private int initiative, weight;
	
	public SimpleCombatant(JSONObject combatant) {
		initFromJson(combatant);
	}

	//TODO: initalize, should each monster be it's own comabatantant (get rid of quantity)?
	private void initFromJson(JSONObject combatant) {
		try {
			name = combatant.getString("name");
			reinforcement = combatant.getBoolean("reinforcement");
			initiative = combatant.getInt("initiative");
			weight = combatant.getInt("weight");
		} catch (Exception e) {
			System.out.println("Error in SimpleCombatant(JSONObject): " + e.getMessage());
		}
	}

	public String getName() {
		return name;
	}

	public boolean isReinforcement() {
		return reinforcement;
	}

	private int getWeight() {
		return weight;
	}

	public int compareTo(SimpleCombatant c) {
		if (weight > c.getWeight())
			return 1;

		if (weight < c.getWeight())
			return -1;

		return 0;
	}
}
