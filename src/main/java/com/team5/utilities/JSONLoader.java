package com.team5.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * A loader class for JSON files.
 */
public final class JSONLoader {
  // Hide constructor
  private JSONLoader() {}
  
  /**
   * Parses a JSON file into a JSONObject.
   * 
   * @param file The JSON file.
   * @return Returns the JSONObject.
   * @throws FileNotFoundException Thrown if the JSON file is not found. 
   * @throws IOException Thrown if an IO exception occurs.
   * @throws ParseException Thrown if a parsing error occurs while parsing the json file.
   */
  public static JSONObject parseJSONFile(File JSON_File) throws FileNotFoundException, IOException, ParseException {
    JSONParser parser = new JSONParser(); // Create JSON parser instance
    JSONObject j_obj = (JSONObject) parser.parse(new FileReader(JSON_File)); // Parse the given JSON file
    return j_obj; // Return the JSON object
  }
  
  /**
   * Parses a JSON file into a JSONObject.
   * 
   * @param filePath The string path to the JSON file.
   * @return Returns the JSONObject.
   * @throws FileNotFoundException Thrown if the json file is not found. 
   * @throws IOException Thrown if an IO exception occurs.
   * @throws ParseException Thrown if a parsing error occurs while parsing the json file.
   */
  public static JSONObject parseJSONFile(String filePath) throws FileNotFoundException, IOException, ParseException {
    return parseJSONFile(new File(filePath));
  }
  
  /**
   * Gets an parses all the JSON objects in a directory.
   * 
   * @param directory The root directory.
   * @param recursive Recursively search through each sub directory.
   * @return Returns a list of JSON Objects
   * @throws NotDirectoryException Thrown the given file is not a directory.
   */
  public static ArrayList<JSONObject> parseAllJSONFiles(File directory, boolean recursive) throws NotDirectoryException {
    // Check for non-directory file and throw error if found
    if (!directory.isDirectory()) 
      throw new NotDirectoryException("The given file is not a directory");

    ArrayList<JSONObject> j_objs = new ArrayList<JSONObject>(); // Initialize return array

    // Iterate through each file in the directory
    for (File subFile : directory.listFiles()) {
      // If a directory is found and we're searching recursively then call the function again and add the JSON objects found
      if (subFile.isDirectory() && recursive) {
        j_objs.addAll(parseAllJSONFiles(subFile, true));
      }
      
      if (subFile.isFile()) {
        try {
          j_objs.add(parseJSONFile(subFile));
        } catch (IOException | ParseException e) { /* Skip file if error occurred */ } 
      }
    }
    return j_objs; // Return list of JSON objects
  }
  
  /**
   * * Gets an parses all the JSON objects in a directory.
   * 
   * @param directoryPath The path to the root directory.
   * @param recursive Recursively search through each sub directory.
   * @return Returns a list of JSON Objects
   * @throws NotDirectoryException Thrown the given file is not a directory.
   */
  public static ArrayList<JSONObject> parseAllJSONFiles(String directoryPath, boolean recursive) throws NotDirectoryException {
    return parseAllJSONFiles(new File(directoryPath), recursive);
  }
}
