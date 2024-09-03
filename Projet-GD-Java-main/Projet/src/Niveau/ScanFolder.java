package Niveau;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ScanFolder {

	public static List<String> getFolderNamesInFolder(String folderPath) {
	    List<String> folderNames = new ArrayList<>();
	    File folder = new File(folderPath);
	    if (folder.exists() && folder.isDirectory()) {
	        File[] directories = folder.listFiles(File::isDirectory);
	        if (directories != null) {
	            for (File directory : directories) {
	                folderNames.add(directory.getName());
	            }
	        }
	    }
	    return folderNames;
	}

	public static List<String> readJsonFile(String folderPath) {
		List<String> jsonLines = Collections.emptyList();
		try {
			jsonLines = Files.readAllLines(Paths.get(folderPath));
		} catch (IOException e) {
			System.err.println("Error reading JSON file: " + e.getMessage());
		}
		return jsonLines;
	}
	
	public static String extractLines(List<String> jsonLines, int index) {
	    if (index > 0 && index <= jsonLines.size()) {
	        String jsonLine = jsonLines.get(index - 1);
	        int startIndex = jsonLine.indexOf("\"");
	        int endIndex = jsonLine.lastIndexOf("\"");
	        if (startIndex != -1 && endIndex != -1) {
	            jsonLine = jsonLine.substring(startIndex + 1, endIndex);
	        }
	        return jsonLine;
	    }
	    return null;
	}
    public static List<String> getFileNamesWithExtension(String folderPath, String extension) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(extension));
            if (files != null) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;
    }
}