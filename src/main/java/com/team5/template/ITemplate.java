package com.team5.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Interface for templates.
 */
public interface ITemplate {
    /**
     * Parses the template located at the given file location.
     * 
     * @param fileName The file location.
     * @return Returns a JSON Object of the parsed file.
     * @throws FileNotFoundException Thrown if a file is not found.
     * @throws IOException Thrown if an I/O exception occurs.
     * @throws ParseException Thrown if an exception occurs during the JSON parsing.
     */
    public JSONObject parseTemplate(String fileName) throws FileNotFoundException, IOException, ParseException;

    /**
     * Parses all templates in the template system.
     * 
     * @return Returns an array list of the parsed files as JSON Objects.
     * @throws NotDirectoryException Thrown if the root file is not a directory.
     */
    public ArrayList<JSONObject> parseAllTemplates() throws NotDirectoryException;
}