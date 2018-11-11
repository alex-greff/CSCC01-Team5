package com.team5.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.utilities.JSONLoader;

public class ParserTest {
	String excelFilePath = "testFiles/SampleFilledTemplates/client_profile.xlsx";
	String excelFilePath2 = "testFiles/reportGeneratorTests/enroll.xlsx";
	String jsonObjectpath1 = "testFiles/JSONTest/client_profile.xlsx";
	
	void test() {
	ArrayList<JSONObject> arrayjson = new ArrayList<JSONObject>();
	try 
	{
		arrayjson = TemplateParser.GetJsonArray(excelFilePath, "client_profile.json", "iCare-template-system");
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ConfigurationNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MissingFieldException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	JSONObject check = arrayjson.get(0);
	}

}
