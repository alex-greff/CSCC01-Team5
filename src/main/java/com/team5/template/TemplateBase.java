package com.team5.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class TemplateBase {
  private File rootFolder;

  public TemplateBase(String rootFolderPath) {
    this.rootFolder = new File(rootFolderPath);
  }

  private String fileExtension(File file) {
    String fileName = file.getName();
    int i = fileName.lastIndexOf('.');
    String extension = (i > 0) ? fileName.substring(i + 1) : "";
    return extension;
  }

  protected ArrayList<JSONObject> parseAllTemplateFiles()
      throws FileNotFoundException, IOException, ParseException {
    
    ArrayList<JSONObject> return_arr = new ArrayList<JSONObject>();

    for (File subFile : rootFolder.listFiles()) {
      boolean isFile = subFile.isFile();
      boolean isJSON = fileExtension(subFile).toLowerCase().equals("json");

      if (isFile && isJSON) {
        JSONParser parser = new JSONParser();
        JSONObject j_obj = (JSONObject) parser.parse(new FileReader(subFile));
        return_arr.add(j_obj);
      }
    }

    return return_arr;
  }

}
