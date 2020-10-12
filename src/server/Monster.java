package monster;

import java.io.*;
import java.util.*;
//import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
//import com.google.gson;

public class Monster implements Serializable {
	private final String[] STATS = { "STR", "DEX", "CON", "INT", "WIS", "CHA" };
	private String name, type, alignment, size, speed, languages, senses, ac, hp,
		challenge, legendaryActionCount;
	int proficiency;
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
		//org.json
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("Monsters/" + name + ".json");

			if (in == null)
            		in = new FileInputStream(new File("Monsters/" + name + ".json"));
            		
			JSONObject json = new JSONObject(new JSONTokener(in));
			initFromJson(json);
		} catch (Exception e) {
			System.out.println("Error in Monster(String name): " + e.getMessage());
		}
		
		//TODO: Figure out why is can't find gson, change to gson
		//gson
		/*Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("Monster/" + name + ".json"));
		Type type = new TypeToken<Monster>(){}.getType();
		this = gson.fromJson(json, Monster.class);*/
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
			type = json.getString("type");
			alignment = json.getString("alignment");
			size = json.getString("size");
			ac = json.getString("ac");
			hp = json.getString("hp");
			challenge = json.getString("challenge");
			proficiency = json.getInt("proficiency");
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
			
			legendaryActionCount = json.getString("legendaryActionCount");
			
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
		return proficiency;
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
	
	public String getLegendaryActionCount() {
		return legendaryActionCount;
	}
	
	public int getAbilityScore(String stat) {
		return scores.get(stat).getScore();
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
	
	public void setProficiency(int proficiency) {
		this.proficiency = proficiency;
	}
	
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	
	public void setSenses(String senses) {
		this.senses = senses;
	}
	
	public void setLegendaryActionCount(String count) {
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
			obj.put("type", type);
			obj.put("alignment", alignment);
			obj.put("size", size);
			obj.put("ac", ac);
			obj.put("hp", hp);
			obj.put("challenge", challenge);
			obj.put("proficiency", proficiency);
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
		
		return true;
	}
}