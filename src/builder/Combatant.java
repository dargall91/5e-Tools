package tracker;

import monster.Monster;
import player.*;
import encounter.MonsterData;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Combatant implements Comparable<Combatant> {
	private boolean monster;
	private boolean reinforcement;
	private int initiative, bonus;
	private int weight; //used to break absolute ties (both initiative and bonus are ==), higher weight == higher initiative
	PlayerCharacter pc;
	MonsterData monData;
	private boolean complete; //used to track if tiebreaker has finished executing
	private String[] ac, hp;
	private int breaker;
	
	Combatant(PlayerCharacter pc, int initiative) {
		this.pc = pc;
		monster = false;
		reinforcement = false;
		this.initiative = initiative;
		breaker = 0;
		complete = false;
		weight = 0;
		ac = new String[1];
		ac[0] = Integer.toString(pc.getAC());
	}
	
	Combatant(MonsterData monData, Monster mon) {
		this.monData = monData;
		monster = true;
		reinforcement = monData.isReinforcement();
		bonus = mon.getInitiativeBonus();
		initiative = monData.getInitiative() + bonus;
		breaker = 0;
		complete = false;
		ac = new String[monData.getQuantity()];
		hp = new String[monData.getQuantity()];
		
		for (int i = 0; i < monData.getQuantity(); i++)
			setAC(i, mon.getAC());
			
		for (int i = 0; i < monData.getQuantity(); i++)
			setHP(i, mon.getHP());
			
		weight = 0;
	}
	
	public String getName() {
		if (monster)
			return monData.getMonster();
		
		return pc.getName();
	}
	
	public int getInitiative() {
		return initiative;
	}
	
	public int getBonus() {
		if (monster)
			return bonus;
			
		return pc.getBonus();
	}
	
	public int getQuantity() {
		if (monster)
			return monData.getQuantity();
			
		return 1;
	}
	
	public int getBreaker() {
		return breaker;
	}
	
	public boolean isMonster() {
		return monster;
	}
	
	public boolean isReinforcement() {
		return reinforcement;
	}
	
	public String getAC(int index) {
		if (monster)
			return ac[index];
			
		return ac[0];
	}
	
	public String getHP(int index) {
		if (monster)
			return hp[index];
		
		return "0";
	}
	
	private boolean complete() {
		return complete;
	}
	
	public void setPlayerCharacter(PlayerCharacter pc) {
		this.pc = pc;
	}
	
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}
	
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	public void setMonster(boolean monster) {
		this.monster = monster;
	}
	
	public void setReinforcement(boolean reinforcement) {
		this.reinforcement = reinforcement;
	}
	
	public void setAC(int index, String ac) {
		if (monster)
			this.ac[index] = ac;
		
		else
			this.ac[0] = ac;
	}
	
	public void setHP(int index, String hp) {
		this.hp[index] = hp;
	}
	
	public void increaseWeight() {
		weight++;
	}
	
	public int getWeight() {
		return weight;
	}
	
	private void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public void setBreaker(int breaker) {
		this.breaker = breaker;
	}
	
	private class TieBreaker {
		TieBreaker(Combatant c) {
			final Integer[] list = new Integer[21];
		
			for (int i = 0; i < 21; i++)
				list[i] = i;

			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel.setMinimumSize(new Dimension(300, 100));
			panel.setMaximumSize(new Dimension(300, 100));
			panel.setPreferredSize(new Dimension(300, 100));
			
			JPanel labels = new JPanel();
			labels.setLayout(new BoxLayout(labels, BoxLayout.X_AXIS));
			labels.setAlignmentX(Component.CENTER_ALIGNMENT);
			JLabel name = new JLabel(getName());
			name.setMinimumSize(new Dimension(150, 30));
			name.setMaximumSize(new Dimension(150, 30));
			name.setPreferredSize(new Dimension(150, 30));
			JLabel cName = new JLabel(c.getName());
			cName.setMinimumSize(new Dimension(150, 30));
			cName.setMaximumSize(new Dimension(150, 30));
			cName.setPreferredSize(new Dimension(150, 30));
			
			JPanel boxes = new JPanel();
			boxes.setLayout(new BoxLayout(boxes, BoxLayout.X_AXIS));
			boxes.setAlignmentX(Component.CENTER_ALIGNMENT);
			JComboBox weightBox = new JComboBox(list);
			weightBox.setMinimumSize(new Dimension(50, 20));
			weightBox.setMaximumSize(new Dimension(50, 20));
			weightBox.setPreferredSize(new Dimension(50, 20));
			JComboBox cWeightBox = new JComboBox(list);
			cWeightBox.setMinimumSize(new Dimension(50, 20));
			cWeightBox.setMaximumSize(new Dimension(50, 20));
			cWeightBox.setPreferredSize(new Dimension(50, 20));
			
			weightBox.setSelectedIndex(getBreaker());
			cWeightBox.setSelectedIndex(c.getBreaker());
			
			labels.add(name);
			labels.add(Box.createRigidArea(new Dimension(50, 0)));
			labels.add(cName);
			
			boxes.add(weightBox);
			boxes.add(Box.createRigidArea(new Dimension(125, 0)));
			boxes.add(cWeightBox);
			
			panel.add(labels);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			panel.add(boxes);
			panel.add(Box.createRigidArea(new Dimension(0, 5)));
			
			Object[] options = {"OK"};
			int result = JOptionPane.showOptionDialog(null, panel, "Tie Breaker", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			
			if (result == JOptionPane.OK_OPTION) {
				setComplete(true);// = true;
				setBreaker((Integer) weightBox.getSelectedItem());
				c.setBreaker((Integer) cWeightBox.getSelectedItem());
			}
		}
	}
	
	//weighs this combatant against another to determine turn order
	public void weigh(Combatant c) {
		setComplete(false);
		
		//use total initiaitve to determine turn order
		if (getInitiative() > c.getInitiative()) {
			increaseWeight();
			return;
		}
			
		if (getInitiative() < c.getInitiative()) {
			c.increaseWeight();
			return;
		}
		
		//in the event of an initiative tie, priority goes to combatant with higher initiative bonus
		if (getBonus() > c.getBonus()) {
			increaseWeight();
			return;
		}
			
		if (getBonus() < c.getBonus()) {
			c.increaseWeight();
			return;
		}
		
		//handle ties, then compare weights
		if (getBreaker() == 0 || c.getBreaker() == 0 || getBreaker() == c.getBreaker()) {
			TieBreaker tieBreaker = new TieBreaker(c);
		}
		
		else
			setComplete(true);
		
		while(!complete());
		
		//ensure 0 values weren't entered, also ensure not tied again
		if (getBreaker() == 0 || c.getBreaker() == 0 || getBreaker() == c.getBreaker()) {
			System.out.println("here");
			weigh(c);
			return;
		}

		if (getBreaker() > c.getBreaker()) {
			increaseWeight();
			return;
		}
			
		if (getBreaker() < c.getBreaker())
			c.increaseWeight();
	}
	
	public int compareTo(Combatant c) {
		if (weight > c.getWeight())
			return 1;
		
		if (weight < c.getWeight())
			return -1;
		
		return 0;
	}
}
