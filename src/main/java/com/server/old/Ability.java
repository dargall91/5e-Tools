package com.server.old;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Ability implements Serializable {
	private String name;
	private String description;
	
	public Ability() {
		name = "name";
		description = "description";
	}
	
	/**
	 * Constructs a monster ability from json styled string
	 */
	Ability(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}
	
	/**
	 * Constructs a monster ability from json object
	 */
	Ability(JSONObject jsonObj) {
		try {
			name = jsonObj.getString("name");
			description = jsonObj.getString("description");
		} catch (Exception e) {
			System.out.println("Error in Action(JsonObject): " + e.getMessage());
		}
	}
	
	/**
	 * Gets the name of this ability
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the descripton of this ability
	 */
	public String getDescription() {
		return description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("name", name);
			obj.put("description", description);
		} catch (Exception e) {
			System.out.println("Error in Ability.toJson: " + e.getMessage());
		}
		
		return obj;
	}
}
