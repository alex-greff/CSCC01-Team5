package com.team5.template;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.team5.utilities.JSONLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import com.team5.template.TemplateLoader;
import java.util.ArrayList;

public class TemplateParser {
	 private TemplateParser() {}
	 
	 public static void inputValuesArray(JSONObject inputobject, Sheet firstSheet, String key, JSONArray array, int row_cur) {
     	int columnNum = CellReference.convertColStringToIndex((String) array.get(0));
     	boolean requiredfield = (boolean) array.get(1);
         Cell inputcell = firstSheet.getRow(row_cur).getCell(columnNum);
         if(inputcell.getCellType() == CellType.STRING) {
         	String inputvalue = inputcell.getStringCellValue();
         	if(inputobject.get(key) == null) {
         		  ArrayList<String> objects = new ArrayList<String>();
         		  objects.add(inputvalue);
         		  inputobject.put(key, objects);
         	   }
         	else {
         		 ArrayList<String> objects = (ArrayList<String>) inputobject.get(key);
         	     objects.add(inputvalue);
         	     inputobject.put(key, objects);
         	}
         }
         else if(inputcell.getCellType() == CellType.NUMERIC) {
         	double inputvalue = inputcell.getNumericCellValue();
         	if(inputobject.get(key) == null) {
         		  ArrayList<Double> objects = new ArrayList<Double>();
         		  objects.add(inputvalue);
         		  inputobject.put(key, objects);
         	   }
         	else {
         		 ArrayList<Double> objects = (ArrayList<Double>) inputobject.get(key);
         	     objects.add(inputvalue);
         	     inputobject.put(key, objects);
         	}
         }
         else if(inputcell.getCellType() == CellType.BLANK) {
          
          	if(inputobject.get(key) == null) {
          		  ArrayList<String> objects = new ArrayList<String>();
          		  objects.add(null);
          		  inputobject.put(key, objects);
          	   }
          	else {
          		 ArrayList<String> objects = (ArrayList<String>) inputobject.get(key);
          	     objects.add(null);
          	     inputobject.put(key, objects);
          	}
          }
         
		
		
		 
	 }
	 @SuppressWarnings("unchecked")
	public static JSONObject parseExcel(String excelFilePath, String parsetemplate) throws IOException, ParseException{
		 FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		 JSONObject jsonObject =  new TemplateLoader("data\\/templates\\/iCare-templates").parseTemplate(parsetemplate);
		 JSONObject parsedObj = new JSONObject();
	     Workbook workbook = new XSSFWorkbook(inputStream);
	     Sheet firstSheet = workbook.getSheetAt(0);
	     int row_cur = 3;
	     
	     //iterate through the row values in excel file
	     while(firstSheet.getRow(row_cur) != null) {
	     //first validate if this row is a valid row (client identity exist or not)
	     
	    Boolean row_validate = true;
	     JSONObject objval = (JSONObject) jsonObject.get("identifier");

     	for(Iterator nestediterator = objval.keySet().iterator(); nestediterator.hasNext();) {
     		String nestedkey = (String) nestediterator.next();
     		JSONArray array = (JSONArray) objval.get(nestedkey);
     		int columnNum = CellReference.convertColStringToIndex((String) array.get(0));
     		Cell inputcell = firstSheet.getRow(row_cur).getCell(columnNum);
     		if(inputcell.getCellType() == CellType.BLANK) {
     			row_validate = false;
     		}
     
     
     	}
     	 // iterate through each key value in the json object
	     for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
	        	
	            String key =  (String) iterator.next();
	            // Check if the key returns an json array or Json Object
	            if (jsonObject.get(key) instanceof JSONArray) {
	            	JSONArray array = (JSONArray) jsonObject.get(key);
	            	if(row_validate) {
	            	inputValuesArray(parsedObj, firstSheet, key, array, row_cur);
	            	}
	            	
	            }
	            else {
	           
	            	JSONObject obj = (JSONObject) jsonObject.get(key);
	            	JSONObject nestedobj = new JSONObject();
	            	if(parsedObj.get(key) != null) {
	            		nestedobj = (JSONObject) parsedObj.get(key);
	            	}
	            	
	            	for(Iterator nestediterator = obj.keySet().iterator(); nestediterator.hasNext();) {
	            		String nestedkey = (String) nestediterator.next();
	            		if (obj.get(nestedkey) instanceof JSONArray) {
	            		JSONArray array = (JSONArray) obj.get(nestedkey);
	            		if(row_validate) {
	            		inputValuesArray(nestedobj, firstSheet, nestedkey, array, row_cur);
	            		}
	            		}
	            	  if(row_validate) {
	            	  parsedObj.put(key, nestedobj);
	            	  }
	            	}
	            	
	            }
	          
	        } 
	     row_cur++;
	     }
	        workbook.close();
	        inputStream.close();
	        
		 return parsedObj;
		 
		 
	 }
     
    public static void main(String[] args) throws IOException, ParseException {
    	
        String excelFilePath = "D:\\team5_project\\Team5\\src\\main\\java\\com\\team5\\template\\client.xlsx";
        String excelFilePath2 = "D:\\team5_project\\Team5\\src\\main\\java\\com\\team5\\template\\enroll.xlsx";

        System.out.println("This is the Parser Test, This parse the excel file and puts it into a Json Object");
        System.out.println("Client Profile");
        System.out.println(TemplateParser.parseExcel(excelFilePath, "client_profile.json"));
        System.out.println("lt_client_enroll");
        System.out.println(TemplateParser.parseExcel(excelFilePath2, "lt_client_enroll.json"));
        
    }
 
}