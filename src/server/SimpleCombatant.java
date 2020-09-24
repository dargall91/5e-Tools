package tracker;

import monster.Monster;
import player.*;
import encounter.MonsterData;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Combatant implements Comparable<Combatant> {
	private boolean reinforcement;
	private String[] hp;
	private String name;
	private int quantity;
	
	Combatant(JSONObject combatanat) {
		initFromJsonArray(combatanats);
	}

	//TODO: initalize, should each monster be it's own comabatantant (get rid of quantity)?
	private void initFromJson(JSONObject combatant) {
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		if (monster)
			return monData.getQuantity();
			
		return 1;
	}

	public boolean isReinforcement() {
		return reinforcement;
	}
	
	public String getHP(int index) {
		if (quantity > 1)
			return hp[index];
		
		return "999";
	}
}
