package Global;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BlockAccessor {
	private static final String JSON_FILE_PATH = "blocks/block_properties.json";
	private static JsonObject jsonObject; // Store the JSON object once it's read

	public static List<String> readBlockProperties() {
		try {
			List<String> lines = Files.readAllLines(Paths.get(JSON_FILE_PATH));
			StringBuilder jsonContent = new StringBuilder();
			for (String line : lines) {
				jsonContent.append(line);
			}
			String jsonString = jsonContent.toString();

			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(jsonString); // Parse JSON string to JsonElement

			if (jsonElement.isJsonObject()) {
				jsonObject = jsonElement.getAsJsonObject(); // Convert JsonElement to JsonObject
			}

			if (Static.getDebugPower() >= 1) {
				System.out.println("BlockAccessor ; JSON file read successfully.");
			}
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error reading JSON file.");
			return null;
		}
	}

	public static String[][] getRenderingOrder(int category, int index) {
		try {
			String key = category + "," + index;

			if (jsonObject != null && jsonObject.has(key)) {
				JsonObject blockObject = jsonObject.getAsJsonObject(key);

				JsonArray orderTypeArray = blockObject.getAsJsonArray("key1");
				JsonArray orderListArray = blockObject.getAsJsonArray("key2");

				// Constructing the result array
				String[][] result = new String[2][];

				// Constructing the orderType array
				String[] orderType = new String[orderTypeArray.size()];
				for (int i = 0; i < orderTypeArray.size(); i++) {
					orderType[i] = orderTypeArray.get(i).getAsString();
				}
				result[0] = orderType;

				// Constructing the orderList array
				String[] orderList = new String[orderListArray.size()];
				for (int i = 0; i < orderListArray.size(); i++) {
					orderList[i] = orderListArray.get(i).getAsString();
				}
				result[1] = orderList;

				return result;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
