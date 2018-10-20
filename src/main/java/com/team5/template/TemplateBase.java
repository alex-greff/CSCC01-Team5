package com.team5.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import com.team5.utilities.JSONLoader;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public abstract class TemplateBase implements ITemplate {
  private String rootDirPath;

  protected TemplateBase(String rootDirPath) {
    this.rootDirPath = rootDirPath;
  }

  public JSONObject parseTemplate(String filePath) throws FileNotFoundException, IOException, ParseException  {
    return JSONLoader.parseJSONFile(rootDirPath + "/" + filePath);
  }

  public ArrayList<JSONObject> parseAllTemplates() throws NotDirectoryException {
    return JSONLoader.parseAllJSONFiles(rootDirPath, true);
  }

}
