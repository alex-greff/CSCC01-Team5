package com.team5.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class JSONLoader {
  // Hide constructor
  private JSONLoader() {}
  
  /**
   * 
   * @param file The json file
   * @return
   * @throws FileNotFoundException Thrown if the json file is not found. 
   * @throws IOException 
   * @throws ParseException
   */
  public static JSONObject parseJSONFile(File JSON_File) throws FileNotFoundException, IOException, ParseException {
    JSONParser parser = new JSONParser();
    JSONObject j_obj = (JSONObject) parser.parse(new FileReader(JSON_File));
    return j_obj;
  }
  
  public static JSONObject parseJSONFile(String filePath) throws FileNotFoundException, IOException, ParseException {
    return parseJSONFile(new File(filePath));
  }
  
  public static ArrayList<JSONObject> parseAllJSONFiles(File directory, boolean recursive) {
    ArrayList<JSONObject> j_objs = new ArrayList<JSONObject>();
    for (File subFile : directory.listFiles()) {
      if (subFile.isDirectory() && recursive) {
        j_objs.addAll(parseAllJSONFiles(subFile, true));
      }
      
      if (subFile.isFile()) {
        try {
          j_objs.add(parseJSONFile(subFile));
        } catch (IOException | ParseException e) { /* Skip file if error occurred */ } 
      }
    }
    
    return j_objs;
  }
  
  public static ArrayList<JSONObject> parseAllJSONFiles(String directoryPath, boolean recursive) {
    return parseAllJSONFiles(new File(directoryPath), recursive);
  }
}
