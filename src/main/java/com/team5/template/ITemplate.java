package com.team5.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface ITemplate {
    public JSONObject parseTemplate(String fileName) throws FileNotFoundException, IOException, ParseException;

    public ArrayList<JSONObject> parseAllTemplates() throws NotDirectoryException;
}