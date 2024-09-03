package Personnage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class IconsAccessor {

	private static final String[] JSON_FILE_PATHS = { "icons/cube.json", "icons/ship.json", "icons/ball.json",
			"icons/wave.json", "icons/ufo.json", "icons/robot.json", "icons/spider.json", "icons/swingcopter.json" };

	private static final String ID_FILE_PATH = "icons/player.json";

	public static int getIconId(int TypeID) {

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();

		try (FileReader reader = new FileReader(ID_FILE_PATH)) {
			JsonObject jsonObject = parser.parse(reader).getAsJsonObject();

			JsonObject typeObject = jsonObject.getAsJsonObject(String.valueOf(TypeID));

			if (typeObject != null) {
				int id = typeObject.get("ID").getAsInt();

				return id;
			} else {
				System.err.println("Type ID " + TypeID + " not found in the JSON file.");
				return -1;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String[][] getRenderingOrder(int param1, int param2) {
		// Determine file path based on param1
		String filePath = JSON_FILE_PATHS[param1 - 1];

		try (FileReader reader = new FileReader(filePath)) {
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(reader).getAsJsonObject();

			// Retrieve part1 and part2 arrays using param2 as key
			JsonObject partObjects = jsonObject.getAsJsonObject(String.valueOf(param2));
			if (partObjects != null) {

				JsonArray part1Array = partObjects.getAsJsonArray("part1");

				JsonArray part2Array = partObjects.getAsJsonArray("part2");

				// Convert JsonArray to String array
				String[] part1 = new String[part1Array.size()];
				String[] part2 = new String[part2Array.size()];

				for (int i = 0; i < part1Array.size(); i++) {
					part1[i] = part1Array.get(i).getAsString();
				}

				for (int i = 0; i < part2Array.size(); i++) {
					part2[i] = part2Array.get(i).getAsString();
				}

				return new String[][] { part1, part2 };
			} else {
				System.err.println("Key " + param2 + " not found in the JSON file.");
				return null; // Or handle error accordingly
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null; // Or handle error accordingly
		}
	}

}
