package server;

import library.*;
import monster.*;
import encounter.*;
import player.*;
import tracker.ServerCombatScreen;
import java.net.*;
import java.io.*;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class DNDServerSkeleton {
	private DNDLibrary lib;
	ServerCombatScreen scs;

	DNDServerSkeleton(DNDLibrary lib, ServerCombatScreen scs) {
		this.lib = lib;
		this.scs = scs;
	}
	
	public String callMethod(String request) {
		JSONObject result = new JSONObject();
		
		try {
			JSONObject call = new JSONObject(request);
			String library = call.getString("library");
			String method = call.getString("method");
			JSONArray params = null;
			
			if (!call.isNull("params"))
				params = call.getJSONArray("params");
				
			if (library.equals("monster"))
				switch (method) {
					case "get":
						String get = params.getString(0);
						result.put("result", lib.getMonster(get).toJson());
						break;
					
					case "add":
						String add = params.getString(0);
						result.put("result", lib.addMonster(add));
						break;
						
					case "delete":
						String delete = params.getString(0);
						result.put("result", lib.deleteMonster(delete));
						break;
						
					case "change":
						String name = params.getString(0);
						JSONObject monster = params.getJSONObject(1);
						result.put("result", lib.renameMonster(name, new Monster(monster)));
						break;
						
					case "update":
						JSONObject update = params.getJSONObject(0);
						result.put("result", lib.updateMonster(new Monster(update)));
						break;
						
					case "restore":
						String restore = params.getString(0);
						result.put("result", lib.restoreMonster(restore));
						break;
						
					case "list":
						result.put("result", lib.getMonsterList());
						break;
						
					case "save":
						String save = params.getString(0);
						result.put("result", lib.saveMonster(save));
						break;
						
					default:
						result.put("result", "0.0");
						System.out.println("Method " + method + " in monster library not found");
						break;
				}
				
			else if (library.equals("encounter"))
				switch (method) {
					case "get":
						String get = params.getString(0);
						result.put("result", lib.getEncounter(get).toJson());
						break;
					
					case "add":
						String add = params.getString(0);
						result.put("result", lib.addEncounter(add));
						break;
						
					case "delete":
						String delete = params.getString(0);
						result.put("result", lib.deleteEncounter(delete));
						break;
						
					case "change":
						String name = params.getString(0);
						JSONObject encounter = params.getJSONObject(1);
						result.put("result", lib.renameEncounter(name, new Encounter(encounter)));
						break;
						
					case "update":
						JSONObject update = params.getJSONObject(0);
						result.put("result", lib.updateEncounter(new Encounter(update)));
						break;
						
					case "restore":
						String restore = params.getString(0);
						result.put("result", lib.restoreEncounter(restore));
						break;
						
					case "list":
						result.put("result", lib.getEncounterList());
						break;
						
					case "save":
						String save = params.getString(0);
						result.put("result", lib.saveEncounter(save));
						break;

					case "music":
						//TODO: change "Music/" to music.dat filepath
						File file = new File("Music/");
						String[] fileNames = file.list();
						JSONArray music = new JSONArray();

						for (int i = 0; i < fileNames.length; i++)
							music.put(fileNames[i]);

						result.put("result", music);
						break;
						
					default:
						result.put("result", "0.0");
						System.out.println("Method " + method + " in encounter library not found");
						break;
				}
				
			else if (library.equals("pc"))
				switch (method) {
					case "get":
						String get = params.getString(0);
						result.put("result", lib.getPlayerCharacter(get).toJson());
						break;
					
					case "add":
						String add = params.getString(0);
						result.put("result", lib.addPlayerCharacter(add));
						break;
						
					case "delete":
						String delete = params.getString(0);
						result.put("result", lib.deletePlayerCharacter(delete));
						break;
					case "update":
						JSONObject update = params.getJSONObject(0);
						result.put("result", lib.updatePlayerCharacter(new PlayerCharacter(update)));
						break;
					case "list":
						result.put("result", lib.getPlayerCharacterList());
						break;
					default:
						result.put("result", "0.0");
						System.out.println("Method " + method + " in pc library not found");
						break;
				}
				
			else if (library.equals("all")) {
				if (method.equals("save"))
					result.put("result", lib.saveAll());
								
				else {
					result.put("result", "0.0");
					System.out.println("Method " + method + " for all libraries not found.");
				}
			}

			else if (library.equals("combat")) {
				if (method.equals("begin")) {
					scs.start(params.getString(0));
					result.put("result", true);
				}

				else if (method.equals("update")) {
					scs.update(params.getJSONArray(0));
					result.put("result", true);
				}

				else if (method.equals("end")) {
					scs.endEncounter();
					result.put("result", true);
				}

				else {
					result.put("result", "0.0");
					System.out.println("Method " + method + " for combat request not found.");
				}
			}
			
			else {
				result.put("result", "0.0");
				System.out.println("Library " + library + " not found.");
			}
		} catch (Exception e) {
			System.out.println("Error processing request: " + e.getMessage());
		}
		
		return result.toString();
	}
}
