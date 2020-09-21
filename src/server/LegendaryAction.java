package monster;

import java.io.Serializable;
import org.json.JSONObject;

public class LegendaryAction implements Serializable {
	String name, description;
	
	public LegendaryAction() {
		name = "name";
		description = "description";
	}
	
	LegendaryAction(JSONObject jsonObj) {
		try {
			name = jsonObj.getString("name");
			description = jsonObj.getString("description");
		} catch (Exception e) {
			System.out.println("Error in LegendaryAction(JsonObject): " + e.getMessage());
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("name", name);
			obj.put("description", description);
			
			System.out.println(obj.toString());
		} catch (Exception e) {
			System.out.println("Error in LegendaryAction.toJson: " + e.getMessage());
		}
		
		return obj;
	}
}
