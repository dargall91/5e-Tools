package com.server.old;

import java.io.*;
import java.util.*;
//import java.net.URL;
import com.server.entities.abilityscore.*;
import com.server.entities.monster.ChallengeRating;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

//TODO: remove proficiency var, is now calculated based on CR. Update desktop GUIs to reflect this change
//TODO: update GUIs with display name
//TODO: update GUIs, leg action coutn now an int
public class Monster implements Serializable {
	private final String[] STATS = { "STR", "DEX", "CON", "INT", "WIS", "CHA" };
	private String name, displayName, type, alignment, size, speed, languages, senses, ac, hp,
		challenge;
	int legendaryActionCount;
	Hashtable<String, AbilityScore> scores;
	Hashtable<String, Skill> skills;
	ArrayList<Ability> abilities;
	ArrayList<Action> actions;
	ArrayList<LegendaryAction> legendaryActions;

	/**
	 * Default constructor, creates an empty monster object
	 */
	public Monster() {
		scores = new Hashtable<String, AbilityScore>();
		skills = new Hashtable<String, Skill>();
		abilities = new ArrayList<Ability>();
		actions = new ArrayList<Action>();
		legendaryActions = new ArrayList<LegendaryAction>();
		
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("EmptyData/NewMonster.json");

			if (in == null)
            		in = new FileInputStream(new File("EmptyData/NewMonster.json"));
            		
			JSONObject json = new JSONObject(new JSONTokener(in));
			initFromJson(json);
		} catch (Exception e) {
			System.out.println("Error in Monster(String name): " + e.getMessage());
		}
	 }
	
	/**
	 * Constructs a monster from a json file
	 */
	public Monster(String name) {
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("Monsters/" + name + ".json");

			if (in == null)
            		in = new FileInputStream(new File("Monsters/" + name + ".json"));
            		
			JSONObject json = new JSONObject(new JSONTokener(in));
			initFromJson(json);
		} catch (Exception e) {
			System.out.println("Error in Monster(String name): " + e.getMessage());
		}
	}
	
	/**
	 * Constructs a Monster from a JSONObject
	 */
	public Monster(JSONObject json) {
		initFromJson(json);
	}
	
	private void initFromJson(JSONObject json) {
		scores = new Hashtable<String, AbilityScore>();
		skills = new Hashtable<String, Skill>();
		abilities = new ArrayList<Ability>();
		actions = new ArrayList<Action>();
		legendaryActions = new ArrayList<LegendaryAction>();
		
		try {
			name = json.getString("name");
			displayName = json.getString("displayName");
			type = json.getString("type");
			alignment = json.getString("alignment");
			size = json.getString("size");
			ac = json.getString("ac");
			hp = json.getString("hp");
			challenge = json.getString("challenge");
			speed = json.getString("speed");
			senses = json.getString("senses");
			languages = json.getString("languages");
			
			JSONArray arr = json.getJSONArray("scores");
			int length = arr.length();
			
			for (int i = 0; i < length; i++) {
				JSONObject scoreObj = arr.getJSONObject(i);//AbilityScore score = new AbilityScore(arr.getJSONObject(STATS[i]));
				scores.put(scoreObj.getString("stat"), new AbilityScore(scoreObj));
			}
			
			arr = json.getJSONArray("skills");
			length = arr.length();
			
			for (int i = 0; i < length; i++) {
				JSONObject skillObj = arr.getJSONObject(i);
				skills.put(skillObj.getString("skill"), new Skill(skillObj));
			}
			
			arr = json.getJSONArray("abilities");
			length = arr.length();
			
			for (int i = 0; i < length; i++) {
				Ability ability = new Ability(arr.getJSONObject(i));
				abilities.add(ability);
			}
			
			arr = json.getJSONArray("actions");
			length = arr.length();
			
			for (int i = 0; i < length; i++) {
				Action action = new Action(arr.getJSONObject(i));
				actions.add(action);
			}
			
			legendaryActionCount = json.getInt("legendaryActionCount");
			
			arr = json.getJSONArray("legendaryActions");
			length = arr.length();
			
			for (int i = 0; i < length; i++) {
				LegendaryAction legendaryAction = new LegendaryAction(arr.getJSONObject(i));
				legendaryActions.add(legendaryAction);
			}
		} catch (Exception e) {
			System.out.println("Error in Monster(JSONObject): " + e.getMessage());
		}
	}
	
	//getters
	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public String getType() {
		return type;
	}
	
	public String getAlignment() {
		return alignment;
	}
	
	public String getSize() {
		return size;
	}
	
	public String getSpeed() {
		return speed;
	}

	public String getLanguages() {
		return languages;
	}
	
	public boolean getSkillProficienct(String skill) {
		return skills.get(skill).getProficient();
	}
	
	public boolean getSkillExpertise(String skill) {
		return skills.get(skill).getExpertise();
	}
	
	public String getAC() {
		return ac;
	}
	
	public String getHP() {
		return hp;
	}
	
	public int getProficiency() {
		if (Objects.isNull(challenge))
			return 0;

		switch (challenge) {
			case "-1":
			case "0":
			case "1/8":
			case "1/4":
			case "1/2":
			case "1":
			case "2":
			case "3":
			case "4":
				return 2;
			case "5":
			case "6":
			case "7":
			case "8":
				return 3;
			case "9":
			case "10":
			case "11":
			case "12":
				return 4;
			case "13":
			case "14":
			case "15":
			case "16":
				return 5;
			case "17":
			case "18":
			case "19":
			case "20":
				return 6;
			case "21":
			case "22":
			case "23":
			case "24":
				return 7;
			case "25":
			case "26":
			case "27":
			case "28":
				return 8;
			case "29":
			case "30":
				return 9;
			default:
				return 0;
		}
	}
	
	public String getChallenge() {
		return challenge;
	}
	
	public int getXP() {
		if (Objects.isNull(challenge))
			return 0;

		switch (challenge) {
			case "-1":
				return 0;
			case "0":
				return 10;
			case "1/8":
				return 25;
			case "1/4":
				return 50;
			case "1/2":
				return 100;
			case "1":
				return 200;
			case "2":
				return 450;
			case "3":
				return 700;
			case "4":
				return 1100;
			case "5":
				return 1800;
			case "6":
				return 2300;
			case "7":
				return 2900;
			case "8":
				return 3900;
			case "9":
				return 5000;
			case "10":
				return 5900;
			case "11":
				return 7200;
			case "12":
				return 8400;
			case "13":
				return 10000;
			case "14":
				return 11500;
			case "15":
				return 13000;
			case "16":
				return 15000;
			case "17":
				return 18000;
			case "18":
				return 20000;
			case "19":
				return 22000;
			case "20":
				return 25000;
			case "21":
				return 33000;
			case "22":
				return 41000;
			case "23":
				return 50000;
			case "24":
				return 62000;
			case "25":
				return 75000;
			case "26":
				return 90000;
			case "27":
				return 105000;
			case "28":
				return 120000;
			case "29":
				return 135000;
			case "30":
				return 155000;
			default:
				return 666;
		}
	}
	
	public String getSenses() {
		return senses;
	}
	
	public int getLegendaryActionCount() {
		return legendaryActionCount;
	}
	
	public int getAbilityScore(String stat) {
		return scores.get(stat).getScore();
	}

	public int getAbilityModifier(String stat) {
		return (scores.get(stat).getScore() - 10) / 2;
	}

	public String getSignedAbilityModifier(String stat) {
		int mod = getAbilityModifier(stat);

		if (mod < 0)
			return Integer.toString(mod);

		return "+" + Integer.toString(mod);
	}
	
	public boolean getAbilityProficiency(String stat) {
		return scores.get(stat).getProficient();
	}
	
	public ArrayList<Action> getActions() {
		return actions;
	}
	
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	
	public ArrayList<LegendaryAction> getLegendaryActions() {
		return legendaryActions;
	}
	
	public int getInitiativeBonus() {
		return Math.floorDiv(getAbilityScore("DEX") - 10, 2);
	}
	
	//Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}
	
	public void setSkillProficiency(String skill, boolean proficient) {
		if (!proficient)
			skills.get(skill).setExpertise(proficient);
		
		skills.get(skill).setProficient(proficient);
	}
	
	public void setSkillExpertise(String skill, boolean expertise) {
		if (expertise)
			skills.get(skill).setProficient(expertise);
			
		skills.get(skill).setExpertise(expertise);			
	}
	
	public void setAC(String ac) {
		this.ac = ac;
	}
	
	public void setHP(String hp) {
		this.hp = hp;
	}
	
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	
	public void setSenses(String senses) {
		this.senses = senses;
	}
	
	public void setLegendaryActionCount(int count) {
		legendaryActionCount = count;
	}
	
	public void setAbilityScore(String stat, int score) {
		scores.get(stat).setScore(score);
	}
	
	public void setAbilityProficiency(String stat, boolean proficient) {
		scores.get(stat).setProficient(proficient);
	}
	
	public void setAbilityDescription(String description, int index) {
		abilities.get(index).setDescription(description);
	}
	
	public void setActionDescription(String description, int index) {
		actions.get(index).setDescription(description);
	}
	
	public void setLegendaryDescription(String description, int index) {
		legendaryActions.get(index).setDescription(description);
	}

	public void setLegendaryActionCost(int cost, int index) {
		legendaryActions.get(index).setCost(cost);
	}
	
	//add actions/abilities
	public void addAction(Action action) {
		actions.add(action);
	}
	
	public void addAbility(Ability ability) {
		abilities.add(ability);
	}
	
	public void addLegendaryAction(LegendaryAction action) {
		legendaryActions.add(action);
	}
	
	//delete actions/abilities
	public void deleteAction(int index) {
		actions.remove(index);
	}
	
	public void deleteAbility(int index) {
		abilities.remove(index);
	}
	
	public void deleteLegendaryAction(int index) {
		legendaryActions.remove(index);
	}
	
	//rename actions/abilities
	public void renameAction(String name, int index) {
		actions.get(index).setName(name);
	}
	
	public void renameAbility(String name, int index) {
		abilities.get(index).setName(name);
	}
	
	public void renameLegendaryAction(String name, int index) {
		legendaryActions.get(index).setName(name);
	}
	
	public String toJsonString() {
		String result = "{}";
		
		try {
			result = toJson().toString(4);
		} catch (Exception e) {
			System.out.println("Error in Monster.toJsonString: " + e.getMessage());
		}
		
		return result;
	}
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		JSONArray scoresArr = new JSONArray();
		JSONArray skillsArr = new JSONArray();
		JSONArray abilityArr = new JSONArray();
		JSONArray actionArr = new JSONArray();
		JSONArray legendaryArr = new JSONArray();
		
		try {
			obj.put("name", name);
			obj.put("displayName", displayName);
			obj.put("type", type);
			obj.put("alignment", alignment);
			obj.put("size", size);
			obj.put("ac", ac);
			obj.put("hp", hp);
			obj.put("challenge", challenge);
			obj.put("speed", speed);
			obj.put("senses", senses);
			obj.put("languages", languages);
			
			for (int i = 0; i < STATS.length; i++)
				scoresArr.put(scores.get(STATS[i]).toJson());
				
			obj.put("scores", scoresArr);
			
			Set<String> keys = skills.keySet();
			Iterator<String> itr = keys.iterator();

			while(itr.hasNext()) {
				skillsArr.put(skills.get(itr.next()).toJson());
			}
				
			obj.put("skills", skillsArr);
			
			for (Ability i : abilities)
				abilityArr.put(i.toJson());

			obj.put("abilities", abilityArr);
			
			for (Action i : actions)
				actionArr.put(i.toJson());

			obj.put("actions", actionArr);
			obj.put("legendaryActionCount", legendaryActionCount);
			
			if (legendaryActions.size() > 0)
				for (LegendaryAction i : legendaryActions)
					legendaryArr.put(i.toJson());
				
			obj.put("legendaryActions", legendaryArr);
		} catch (Exception e) {
			System.out.println("Exception in Monster.toJson: " + e.getMessage());
		}

		return obj;
	}
	
	public boolean saveJson() {
		boolean result = false;
		
		try {
			File file = new File("Monsters/" + name + ".json");
			
			if (!file.exists())
				file.createNewFile();
				
			FileWriter out = new FileWriter(file);
			out.write(toJsonString());
			out.flush();
			out.close();
			result = true;
			
			System.out.println("Monster " + name + " save successful.");
		} catch (Exception e) {
			System.out.println("Error in Monster.saveJson: " + e.getMessage());
		}
		
		return result;
	}

	public com.server.entities.monster.Monster toNewMonster() {
		com.server.entities.monster.Monster monster = new com.server.entities.monster.Monster();
		monster.setCampaignId(1);
		monster.setName(name);
		monster.setDisplayName(displayName);
		monster.setLegendaryActionCount(legendaryActionCount);
		monster.setSize(size);
		monster.setType(type);
		monster.setSenses(senses);
		monster.setLanguages(languages);
		monster.setArmorClass(Integer.parseInt(ac));
		monster.setSpeed(speed);
		monster.setHitPoints(Integer.parseInt(hp));
		monster.setAlignment(alignment);

		Strength strength = new Strength();
		strength.setScore(scores.get("STR").getScore());
		strength.setProficient(scores.get("STR").getProficient());
		strength.setAthletics(convertSkill("Athletics"));
		monster.setStrength(strength);

		Dexterity dexterity = new Dexterity();
		dexterity.setScore(scores.get("DEX").getScore());
		dexterity.setProficient(scores.get("DEX").getProficient());
		dexterity.setAcrobatics(convertSkill("Acrobatics"));
		dexterity.setStealth(convertSkill("Stealth"));
		dexterity.setSleightOfHand(convertSkill("Sleight of Hand"));
		monster.setDexterity(dexterity);

		Constitution constitution = new Constitution();
		constitution.setScore(scores.get("CON").getScore());
		constitution.setProficient(scores.get("CON").getProficient());
		monster.setConstitution(constitution);

		Intelligence intelligence = new Intelligence();
		intelligence.setScore(scores.get("INT").getScore());
		intelligence.setProficient(scores.get("INT").getProficient());
		intelligence.setArcana(convertSkill("Arcana"));
		intelligence.setHistory(convertSkill("History"));
		intelligence.setInvestigation(convertSkill("Investigation"));
		intelligence.setNature(convertSkill("Nature"));
		intelligence.setReligion(convertSkill("Religion"));
		monster.setIntelligence(intelligence);

		Wisdom wisdom = new Wisdom();
		wisdom.setScore(scores.get("WIS").getScore());
		wisdom.setProficient(scores.get("WIS").getProficient());
		wisdom.setAnimalHandling(convertSkill("Animal Handling"));
		wisdom.setInsight(convertSkill("Insight"));
		wisdom.setMedicine(convertSkill("Medicine"));
		wisdom.setPerception(convertSkill("Perception"));
		wisdom.setSurvival(convertSkill("Survival"));
		monster.setWisdom(wisdom);

		Charisma charisma = new Charisma();
		charisma.setScore(scores.get("CHA").getScore());
		charisma.setProficient(scores.get("CHA").getProficient());
		charisma.setDeception(convertSkill("Deception"));
		charisma.setIntimidation(convertSkill("Intimidation"));
		charisma.setPerformance(convertSkill("Performance"));
		charisma.setPersuasion(convertSkill("Persuasion"));
		monster.setCharisma(charisma);

		List<com.server.entities.monster.Ability> abilityList = new ArrayList<>();

		for (Ability oldAbility : abilities) {
			com.server.entities.monster.Ability ability = new com.server.entities.monster.Ability();
			ability.setName(oldAbility.getName());
			ability.setDescription(oldAbility.getDescription());
			abilityList.add(ability);
		}

		monster.setAbilities(abilityList);

		List<com.server.entities.monster.Action> actionList = new ArrayList<>();

		for (Action oldAction : actions) {
			com.server.entities.monster.Action action = new com.server.entities.monster.Action();
			action.setName(oldAction.getName());
			action.setDescription(oldAction.getDescription());
			actionList.add(action);
		}

		monster.setActions(actionList);

		List<com.server.entities.monster.LegendaryAction> legendaryActionList = new ArrayList<>();

		for (LegendaryAction oldLegendaryAction : legendaryActions) {
			com.server.entities.monster.LegendaryAction legendaryAction = new com.server.entities.monster.LegendaryAction();
			legendaryAction.setName(oldLegendaryAction.getName());
			legendaryAction.setDescription(oldLegendaryAction.getDescription());
			legendaryAction.setCost(oldLegendaryAction.getCost());
			legendaryActionList.add(legendaryAction);
		}

		monster.setLegendaryActions(legendaryActionList);

		//todo: challenge rating conversion
		ChallengeRating cr = new ChallengeRating();
		cr.setId(convertCrToId());
		monster.setChallengeRating(cr);

		return monster;
	}

	private int convertSkill(String skill) {
		if (skills.get(skill).getExpertise()) {
			return 2;
		} else if (skills.get(skill).getProficient()) {
			return 1;
		} else {
			return 0;
		}
	}

	private int convertCrToId() {
		switch (challenge) {
			case "-1":
				return 1;
			case "0":
				return 2;
			case "1/8":
				return 3;
			case "1/4":
				return 4;
			case "1/2":
				return 5;
			case "1":
				return 6;
			case "2":
				return 7;
			case "3":
				return 8;
			case "4":
				return 9;
			case "5":
				return 10;
			case "6":
				return 11;
			case "7":
				return 12;
			case "8":
				return 13;
			case "9":
				return 14;
			case "10":
				return 15;
			case "11":
				return 16;
			case "12":
				return 17;
			case "13":
				return 18;
			case "14":
				return 19;
			case "15":
				return 20;
			case "16":
				return 21;
			case "17":
				return 22;
			case "18":
				return 23;
			case "19":
				return 24;
			case "20":
				return 25;
			case "21":
				return 26;
			case "22":
				return 27;
			case "23":
				return 28;
			case "24":
				return 29;
			case "25":
				return 30;
			case "26":
				return 31;
			case "27":
				return 32;
			case "28":
				return 33;
			case "29":
				return 34;
			case "30":
				return 35;
			default:
				return 1;
		}
	}
}
