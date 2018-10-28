package com.team5.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.utilities.JSONLoader;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * The base template system class.
 */
public class TemplateBase implements ITemplate {

  private String rootDirPath; // The root directory
  protected static String config = "iCare-template-system"; // Name of the config to load
  protected static String configItem = "root-template-directory"; // Name of the config item to load from config
  
  /**
   * Gets the root directory path from the config file for use with constructor
   * 
   * @return rootDirPath the directory path to the iCare template files
   * @throws ConfigurationNotFoundException if config file is not found
   */
  private static String getRootDirPath() throws ConfigurationNotFoundException {
		JSONObject jsonConfig = ConfigurationLoader.loadConfiguration(config);
		String rootDirPath = (String) jsonConfig.get(configItem);
		return rootDirPath;
  }
  
  /**
   * Default constructor class uses iCare template directory
   * @throws ConfigurationNotFoundException raises if config item not found
   */
  protected TemplateBase() throws ConfigurationNotFoundException {
		this(getRootDirPath());
  }

  /**
   * Constructs the template object.
   * 
   * @param rootDirPath The root directory.
   */
  protected TemplateBase(String rootDirPath) {
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
