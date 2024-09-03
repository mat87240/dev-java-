package Niveau;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Global.Static;

import java.io.*;

public class Save {

    public static void writeToFile(String levelName, Block[] blockList) {
    	Static.setDoSave(false);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File levelFolder = new File("level/" + levelName);
        if (!levelFolder.exists()) {
            levelFolder.mkdir();
        }
        File file = new File(levelFolder, "level.json");

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                JsonElement existingData = gson.fromJson(reader, JsonElement.class);
                if (existingData.isJsonObject()) {
                    jsonObject = existingData.getAsJsonObject();
                } else if (existingData.isJsonArray()) {
                    jsonArray = existingData.getAsJsonArray();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Block block : blockList) {
            JsonObject blockJson = new JsonObject();
            blockJson.addProperty("x", block.getX());
            blockJson.addProperty("y", block.getY());
            blockJson.addProperty("width", block.getWidth());
            blockJson.addProperty("height", block.getHeight());
            blockJson.addProperty("index catégorie", block.getIndexCat());
            blockJson.addProperty("index block", block.getIndexBlock());
            blockJson.addProperty("scale", block.getScale());

            jsonArray.add(blockJson);
        }

        jsonObject.add("blocks", jsonArray);

        try (Writer writer = new FileWriter(file)) {
            gson.toJson(jsonObject, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Debug
        if (Static.getDebugPower()>=1) {
        	System.out.println("Saving the level : "+levelName);
        }
    }
}
