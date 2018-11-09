package com.team5.template;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.utilities.JSONLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import com.team5.template.TemplateLoader;
import java.util.ArrayList;


/**
 * This is reponsible for parsing through the .xlsx file
 *
 */
public class TemplateParser {
	 //constructor class
	 private TemplateParser() {}
	 
	 /**
	 * This class is responsible for validating if we put the data of the current row into the json object
	 * 
	 * @param jsonObject This is the input json object we want to put into our array
	 * @param row_cur this is the current row we are on
	 * @param firstSheet this is the .xslx sheet
	 * @return row_validate this is the validation if we put the data into json object
	 */
	public static boolean validateRow(JSONObject jsonObject, int row_cur, Sheet firstSheet ) {
		    Boolean row_validate = true;
		    JSONObject objval = (JSONObject) jsonObject.get("identifier");

	     	for(Iterator nestediterator = objval.keySet().iterator(); nestediterator.hasNext();) {
	     		String nestedkey = (String) nestediterator.next();
	     		JSONArray array = (JSONArray) objval.get(nestedkey);
	     		int columnNum = CellReference.convertColStringToIndex((String) array.get(0));
	     		Cell inputcell = firstSheet.getRow(row_cur).getCell(columnNum);
	     		if(inputcell.getCellType() == Cell.CELL_TYPE_BLANK) {
	     			row_validate = false;
	     		}
	     
	     
	     	}
			return row_validate;
	 }

	 /**
	 * This is for getting the default template for the .xlsx file 
	 * 
	 * @param parsetemplate this is the template name
	 * @param configName config name for the configuration loader
	 * @return
	 * @throws ParseException
	 * @throws ConfigurationNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static JSONObject getTemplateObject(String parsetemplate, String configName) throws ParseException, ConfigurationNotFoundException, FileNotFoundException, IOException {
		String config = (String) ConfigurationLoader.loadConfiguration(configName).get("root-template-directory");
		JSONObject jsonObject =  new TemplateLoader(config).parseTemplate(parsetemplate);
		return jsonObject;
	 }

	
	
	
	/**
	 * @param stringInput
	 * @return
	 */
	public static boolean checkInt(String stringInput) {
		try {
			Integer.parseInt(stringInput);
			return true;
		} catch(Exception e) {
			return false; 
		}
		
	}
	
	/**
	 * @param stringInput
	 * @return
	 */
	public static boolean checkDouble(String stringInput) {
		try {
			Double.parseDouble(stringInput);
			return true;
		} catch(Exception e) {
			return false; 
		}
		
	}
	
	/**
	 * @param stringInput
	 */
	public static void inputNumber(String stringInput, String key, JSONObject inputobject) {
	
		if(checkInt(stringInput)) {
			int value = Integer.parseInt(stringInput);
			inputobject.put(key, value);
		}
		else if(checkDouble(stringInput)) {
			double value = Double.parseDouble(stringInput);
			inputobject.put(key, value);
		}else {
			inputobject.put(key, stringInput);
		}
	}
	 /**
		 * This inputs values into the json object to put into the array 
		 * 
		 * @param inputobject this is the empty json object
		 * @param firstSheet this is the .xlsx file sheet
		 * @param key this is the key from the default template
		 * @param array this is the value we get from the default template 
		 * @param row_cur this is the current row we are on
		 * @param row_validate this is the validation if we put it into the object
		 */
		public static void inputValues(JSONObject inputobject, Sheet firstSheet, String key, JSONArray array, int row_cur, boolean row_validate) {
			 int columnNum = CellReference.convertColStringToIndex((String) array.get(0));
		     boolean requiredfield = (boolean) array.get(1);
		     Cell inputcell = firstSheet.getRow(row_cur).getCell(columnNum);
		     if(row_validate) {
		    	 if(inputcell.getCellType() == Cell.CELL_TYPE_STRING) {
		         		String inputvalue = inputcell.getStringCellValue();
		         		inputNumber(inputvalue, key, inputobject);
		    	 }
		    	 else {
		    		 inputobject.put(key, null);
		    	 }
		     }
		 }
	
	/**
	 * 
	 * 
	 * @param firstSheet
	 * @param nestedobj
	 * @param obj
	 * @param row_validate
	 * @param row_cur
	 * @return
	 */
	public static JSONObject recursiveInputJson(Sheet firstSheet, JSONObject nestedobj, JSONObject obj, boolean row_validate, int row_cur) {
		
	
    	for(Iterator nestediterator = obj.keySet().iterator(); nestediterator.hasNext();) {
    		
    		String nestedkey = (String) nestediterator.next();
    		if (obj.get(nestedkey) instanceof JSONArray) {
    			JSONArray array = (JSONArray) obj.get(nestedkey);
    			
    			inputValues(nestedobj, firstSheet, nestedkey, array, row_cur, row_validate);
    		
    		}else {
    			JSONObject nestedObj = (JSONObject) obj.get(nestedkey);
    			JSONObject newobj = new JSONObject();
    			newobj = recursiveInputJson(firstSheet, newobj ,  nestedObj,  row_validate,  row_cur);
    			nestedobj.put(nestedkey, newobj);
    		}
    	}
		return nestedobj;
    
	 }

	 /**
	 * Parses a .xlsx file into an array of JSON objects
	 * 
	 * @param excelFilePath the file path to the .xlsx file
	 * @param parsetemplate this is the default template of the file they want to parse
	 * @param configName this is the config name for loading configuration settings
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws ConfigurationNotFoundException
	 */
	public static ArrayList<JSONObject> getJsonObject(String excelFilePath, String parsetemplate, String configName) throws IOException, ParseException, ConfigurationNotFoundException{
		 FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		 JSONObject jsonObject =  getTemplateObject(parsetemplate, configName);
	     Workbook workbook = new XSSFWorkbook(inputStream);
	     Sheet firstSheet = workbook.getSheetAt(0);
	     ArrayList<JSONObject> jsonobjects = new ArrayList<JSONObject>();
	     //ArrayList<String> invalid_values = new ArrayList<String>();
	     //ArrayList<String> missing_values = new ArrayList<String>();
	     
	     int row_cur = 3;
	     
	     //iterate through the row values in excel file
	     while(firstSheet.getRow(row_cur) != null) {
	     //first validate if this row is a valid row (client identity exist or not)
	     JSONObject parsedObj = new JSONObject();
	     boolean row_validate = validateRow(jsonObject, row_cur, firstSheet);
 
     	 // iterate through each key value in the json object
	     for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
	        	
	            String key =  (String) iterator.next();
	            // Check if the key returns an json array or Json Object
	            if (jsonObject.get(key) instanceof JSONArray) {
	            	JSONArray array = (JSONArray) jsonObject.get(key);
	            	inputValues(parsedObj, firstSheet, key, array, row_cur,row_validate);
	            	
	            	
	            }
	            
	            else if(jsonObject.get(key) instanceof String) {
	            	 parsedObj.put(key, parsetemplate);
	            }
	            else {
	           
	            	JSONObject obj =  (JSONObject) jsonObject.get(key);
	            	JSONObject nestedobj = new JSONObject();
	                nestedobj = recursiveInputJson(firstSheet, nestedobj, obj, row_validate, row_cur);
	            	parsedObj.put(key, nestedobj);
	            	
	            	
	            }
	          
	        }
	     if(row_validate) {
	     jsonobjects.add(parsedObj);
	     }
	     row_cur++;
	     }
	     inputStream.close();
	     return jsonobjects;
	 }
	 
	  public static void main(String[] args) throws IOException, ParseException, ConfigurationNotFoundException {
	    	
	        String excelFilePath = "testFiles/reportGeneratorTests/client.xlsx";
	        String excelFilePath2 = "testFiles/reportGeneratorTests/enroll.xlsx";

	        System.out.println("This is the Parser Test, This parse the excel file and puts it into a Json Object");
	        System.out.println("Client Profile");
	        System.out.println(TemplateParser.getJsonObject(excelFilePath, "client_profile.json","iCare-template-system"));
	        System.out.println("lt_client_enroll");
	        System.out.println(TemplateParser.getJsonObject(excelFilePath2, "lt_client_enroll.json","iCare-template-system"));
	        
	    }

}