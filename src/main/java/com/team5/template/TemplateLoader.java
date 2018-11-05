package com.team5.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import com.team5.utilities.JSONLoader;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * The base template system class.
 */
public class TemplateLoader implements ITemplate {
  /**
   * The root directory.
   */
  private String rootDirPath;

  /**
   * Constructs the template object.
   * 
   * @param rootDirPath The root directory.
   */
  public TemplateLoader(String rootDirPath) {
    this.rootDirPath = rootDirPath;
  }

  /**
   * Parses the template located at the given file location.
   * 
   * @param fileName The file location.
   * @return Returns a JSON Object of the parsed file.
   * @throws FileNotFoundException Thrown if a file is not found.
   * @throws IOException Thrown if an I/O exception occurs.
   * @throws ParseException Thrown if an exception occurs during the JSON parsing.
   */
  public JSONObject parseTemplate(String filePath) throws FileNotFoundException, IOException, ParseException  {
    return JSONLoader.parseJSONFile(rootDirPath + "/" + filePath);
  }

  /**
   * Parses all templates in the template system.
   * 
   * @return Returns an array list of the parsed files as JSON Objects.
   * @throws NotDirectoryException Thrown if the root file is not a directory.
   */
  public ArrayList<JSONObject> parseAllTemplates() throws NotDirectoryException {
    return JSONLoader.parseAllJSONFiles(rootDirPath, true);
  }
}

