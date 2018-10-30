package com.team5.template;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
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
 

public class TemplateParser {
	 private TemplateParser() {}
	 
	 public static JSONObject parseExcel(String excelFilePath, String parsetemplate) throws IOException, ParseException{
		 FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		 JSONObject jsonObject =  new TemplateLoader("data\\/templates\\/iCare-templates").parseTemplate(parsetemplate);
		 JSONObject parsedObj = new JSONObject();
	     Workbook workbook = new XSSFWorkbook(inputStream);
	     Sheet firstSheet = workbook.getSheetAt(0);
	     
	     // iterate through each key value in the json object
	     for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
	        	
	            String key =  (String) iterator.next();
	            // Check if the key returns an json array or Json Object
	            if (jsonObject.get(key) instanceof JSONArray) {
	    
	            	JSONArray array = (JSONArray) jsonObject.get(key);
	            	int columnNum = CellReference.convertColStringToIndex((String) array.get(0));
	            	parsedObj.put(key, firstSheet.getRow(3).getCell(columnNum));

	            }
	            else {
	           
	            	JSONObject obj = (JSONObject) jsonObject.get(key);
	            	JSONObject nestedobj = new JSONObject();
	            	
	            	
	            	for(Iterator nestediterator = obj.keySet().iterator(); nestediterator.hasNext();) {
	            		String key2 = (String) nestediterator.next();
	            		if (obj.get(key2) instanceof JSONArray) {
	            		JSONArray array = (JSONArray) obj.get(key2);
	            		
	            		int columnNum = CellReference.convertColStringToIndex((String) array.get(0));
	            		nestedobj.put(key2, firstSheet.getRow(3).getCell(columnNum));

	            		}
	            	  parsedObj.put(key, nestedobj);
	            	}
	            	
	            }
	           
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