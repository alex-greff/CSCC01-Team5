package com.team5.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;

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
	// constructor class
	public TemplateParser() {
	}

	private static int row_cur;
	private static boolean row_validate;
	private static JSONObject missingvalues = new JSONObject();

	/**
	 * This class is responsible for validating if we put the data of the current
	 * row into the json object
	 * 
	 * @param jsonObject This is the input json object we want to put into our array
	 * @param row_cur    this is the current row we are on
	 * @param firstSheet this is the .xslx sheet
	 * @return row_validate this is the validation if we put the data into json
	 *         object
	 */

	private static boolean validateRow(JSONObject jsonObject, int row_cur, Sheet firstSheet) {
		ArrayList<String> missingval = new ArrayList<String>();
		Boolean validate = true;
		JSONObject objval = (JSONObject) jsonObject.get("identifier");

		for (Iterator nestediterator = objval.keySet().iterator(); nestediterator.hasNext();) {
			String nestedkey = (String) nestediterator.next();
			JSONArray array = (JSONArray) objval.get(nestedkey);
			int columnNum = CellReference.convertColStringToIndex((String) array.get(0));
			Cell inputcell = firstSheet.getRow(row_cur).getCell(columnNum);

			if (inputcell.getCellType() == Cell.CELL_TYPE_BLANK) {
				missingval.add((String) array.get(0));
				validate = false;
			}

		}

		return validate;
	}

	/**
	 * This is for getting the default template for the .xlsx file
	 * 
	 * @param parsetemplate this is the template name
	 * @param configName    config name for the configuration loader
	 * @return
	 * @throws ParseException
	 * @throws ConfigurationNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static JSONObject getTemplateObject(String parsetemplate, String configName)
			throws ParseException, ConfigurationNotFoundException, FileNotFoundException, IOException {
		String config = (String) ConfigurationLoader.loadConfiguration(configName).get("root-template-directory");
		JSONObject jsonObject = new TemplateLoader(config).parseTemplate(parsetemplate);
		return jsonObject;
	}

	/**
	 * This method is for checking if the cell value is a integer
	 * 
	 * @param stringInput the cell input
	 * @return
	 */
	private static boolean checkInt(String stringInput) {
		try {
			Integer.parseInt(stringInput);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * This method is for checking if the cell value is a double
	 * 
	 * @param stringInput the cell input
	 * @return
	 */
	private static boolean checkDouble(String stringInput) {
		try {
			Double.parseDouble(stringInput);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * This method is to input the value into the json object
	 * 
	 * @param stringInput the cell input
	 */
	private static void inputToJSONObject(String stringInput, String key, JSONObject inputobject) {

		if (checkInt(stringInput)) {
			int value = Integer.parseInt(stringInput);
			inputobject.put(key, value);
		} else if (checkDouble(stringInput)) {
			double value = Double.parseDouble(stringInput);
			inputobject.put(key, value);
		} else {
			inputobject.put(key, stringInput);
		}
	}

	/**
	 * This method is to check the add the missing value to the missing value array
	 * 
	 * 
	 * @param key   this is the key value from default template
	 * @param array this the value mapped to the key first value is coordinate and
	 *              second value is boolean
	 */

	private static void addMissingVal(String key, JSONArray array) {
		boolean requiredfield = (boolean) array.get(1);
		String rowString = Integer.toString(row_cur);
		// checks if the row is a required row and if the cell value is a required field
		if (row_validate && requiredfield) {
			//
			if (missingvalues.get(key) == null) {
				ArrayList<String> missingKeyArray = new ArrayList<String>();
				missingKeyArray.add(array.get(0) + rowString);
				missingvalues.put(key, missingKeyArray);
			} else {
				ArrayList<String> missingKeyArray = (ArrayList<String>) missingvalues.get(key);
				missingKeyArray.add(array.get(0) + rowString);
				missingvalues.put(key, missingKeyArray);
			}

		}

	}

	/**
	 * This inputs values into the json object to put into the array
	 * 
	 * @param inputobject  this is the empty json object
	 * @param firstSheet   this is the .xlsx file sheet
	 * @param key          this is the key from the default template
	 * @param array        this is the value we get from the default template
	 * @param row_cur      this is the current row we are on
	 * @param row_validate this is the validation if we put it into the object
	 */
	private static void inputValues(JSONObject inputobject, Sheet firstSheet, String key, JSONArray array) {
		int columnNum = CellReference.convertColStringToIndex((String) array.get(0));
		// gets the cell value from the row number and column number
		Cell inputcell = firstSheet.getRow(row_cur).getCell(columnNum);

		if (row_validate) {
			if (inputcell.getCellType() == Cell.CELL_TYPE_STRING) {
				String inputvalue = inputcell.getStringCellValue();
				inputToJSONObject(inputvalue, key, inputobject);
			} else {
				// if it is null add it to missing val if it is a required field
				addMissingVal(key, array);
				inputobject.put(key, null);
			}
		}
	}

	/**
	 * This gets the json objects from the sheet using the jsonarray in the default
	 * template
	 * 
	 * @param obj        this is the Template JSONObject
	 * @param firstSheet this is the sheet on the workbook
	 * @return An arraylist of jsonObjects
	 */
	private static ArrayList<JSONObject> getJsonArrayObjects(JSONArray obj, Sheet firstSheet) throws FileNotFoundException {
		ArrayList<JSONObject> jsonobjects = new ArrayList<JSONObject>();

		for (int i = 0; i < obj.size(); i++) {
			JSONObject inputobject = (JSONObject) obj.get(i);
			JSONObject newObject = new JSONObject();
			newObject = recursiveInputJson(firstSheet, newObject, inputobject);
			jsonobjects.add(newObject);
		}
		return jsonobjects;

	}

	/**
	 * This inputs values into the json object if the value of the key of the
	 * template object is a json array
	 * 
	 * @param nestedobj  This is the JSONObject we want to input information in
	 * @param firstSheet this is the sheet from the workbook
	 * @param key        this is the key value in the template JSONObject
	 * @param array      this is the array value we get from the key in the
	 *                   JSONObject
	 * @throws FileNotFoundException 
	 */
	private static void inputValuesArray(JSONObject inputobject, Sheet firstSheet, String key, JSONArray array) throws FileNotFoundException {
		// checks if the array consist of JSONObject or consist of the coordinate and
		// boolean
		if (array.get(0) instanceof JSONObject) {
			ArrayList<JSONObject> arrayofObjects = getJsonArrayObjects(array, firstSheet);
			inputobject.put(key, arrayofObjects);
		} else {
			inputValues(inputobject, firstSheet, key, array);
		}
	}

	/**
	 * This input the data into the JSONObjects recursively
	 * 
	 * @param firstSheet   this is the sheet from the workbook
	 * @param inputobj     this is the JSONObject we want to input data into
	 * @param templateobj  this is the template JSONObject
	 * @param row_validate this validate if the row is a row with a client id
	 * @param row_cur      this is the current row we are parsing through
	 * @return this return the JSONObject with all the keys filled
	 * @throws FileNotFoundException 
	 */
	private static JSONObject recursiveInputJson(Sheet firstSheet, JSONObject inputobj, JSONObject templateobj) throws FileNotFoundException {
		// iterate through all the keys in the template object
		for (Iterator nestediterator = templateobj.keySet().iterator(); nestediterator.hasNext();) {
			// this get the key value for the template object
			String nestedkey = (String) nestediterator.next();
			// checks if the value of the key in the template obj is a JSONArray
			// if it is we input the jsonobject with array values
			if (templateobj.get(nestedkey) instanceof JSONArray) {
				JSONArray array = (JSONArray) templateobj.get(nestedkey);
				inputValuesArray(inputobj, firstSheet, nestedkey, array);
			} else if (templateobj.get(nestedkey) instanceof String) {
				inputobj.put(nestedkey, templateobj.get(nestedkey));
				// if the value in the template object is a JSONObject we recursive go through
				// the function again until we find an array value
			} else {
				JSONObject nestedObj = (JSONObject) templateobj.get(nestedkey);
				JSONObject newobj = new JSONObject();
				newobj = recursiveInputJson(firstSheet, newobj, nestedObj);
				inputobj.put(nestedkey, newobj);
			}
		}
		return inputobj;

	}

	/**
	 * Parses a .xlsx file into an array of JSON objects
	 * 
	 * @param excelFilePath the file path to the .xlsx file
	 * @param parsetemplate this is the default template of the file they want to
	 *                      parse
	 * @param configName    this is the config name for loading configuration
	 *                      settings
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws ConfigurationNotFoundException
	 */
	private static ArrayList<JSONObject> getArrayJsonObject(String excelFilePath, String parsetemplate,
			String configName) throws IOException, ParseException, ConfigurationNotFoundException, FileNotFoundException {

		missingvalues = new JSONObject();
	
		//gets the sheet from the workbook
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		JSONObject jsonObject = getTemplateObject(parsetemplate, configName);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		ArrayList<JSONObject> jsonobjects = new ArrayList<JSONObject>();
		row_cur = 3;
		//iterate through the row in the workbook sheet and adds the jsonobjects to the
		//array of jsonobjects
		while (firstSheet.getRow(row_cur) != null) {
			JSONObject parsedObj = new JSONObject();
			row_validate = validateRow(jsonObject, row_cur, firstSheet);
			parsedObj = recursiveInputJson(firstSheet, parsedObj, jsonObject);
			if (row_validate) {
				jsonobjects.add(parsedObj);
			}
			row_cur++;
		}

		inputStream.close();
		return jsonobjects;
	}

	/**
	 * Gets a container of an jsonarray of jsonobjects containing data on excel and
	 * a jsonarray of missing required data on excel
	 * 
	 * @param excelFilePath
	 * @param parsetemplate
	 * @param configName
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws ConfigurationNotFoundException
	 * @throws MissingFieldException
	 */
	public static ArrayList<JSONObject> GetJsonArray(String excelFilePath, String parsetemplate, String configName)
			throws IOException, ParseException, ConfigurationNotFoundException, MissingFieldException {
		//gets the missing values and array of filled JSONObjects
		ArrayList<JSONObject> jsonobjects = getArrayJsonObject(excelFilePath, parsetemplate, configName);
		ArrayList<JSONObject> missingValuesArray = new ArrayList<JSONObject>();
		missingValuesArray.add(missingvalues);
		JSONObject missingarray = missingValuesArray.get(0);
		if (missingarray.size() > 0) {
			throw new MissingFieldException(missingValuesArray);
		}

		return jsonobjects;

	}

	public static void main(String[] args)
			throws IOException, ParseException, ConfigurationNotFoundException, MissingFieldException {

		String excelFilePath = "testFiles/reportGeneratorTests/client.xlsx";
		String excelFilePath2 = "testFiles/reportGeneratorTests/enroll.xlsx";

		System.out.println("This is the Parser Test, This parse the excel file and puts it into a Json Object");
		System.out.println("Client Profile");
		System.out.println(TemplateParser.GetJsonArray(excelFilePath, "client_profile.json", "iCare-template-system"));
		System.out.println("lt_client_enroll");
		System.out
				.println(TemplateParser.GetJsonArray(excelFilePath2, "lt_client_enroll.json", "iCare-template-system"));

	}

}