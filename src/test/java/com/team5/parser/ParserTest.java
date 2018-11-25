package com.team5.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.utilities.JSONLoader;
import com.team5.template.TemplateLoader;

public class ParserTest {
	String excelFilePath = "testFiles/ParseTestFiles/client_profile.xlsx";
	String excelFilePath3 = "testFiles/ParseTestFiles/client_profileMissing.xlsx";
	String excelFilePath4 = "testFiles/ParseTestFiles/community_connectionsMISSING.xlsx";
	String excelFilePath2 = "testFiles/ParseTestFiles/lt_client_enrollment.xlsx";
	String jsonObjectpath1 = "testFiles/JSONTestFiles/client_profile.json";
	JSONParser parser = new JSONParser(); // Create JSON parser instance
	// Parse the given JSON file
	@Test
	@DisplayName("Missing Client profile")
	void client_profile_missing() {
		assertThrows(MissingFieldException.class, () -> {
			TemplateParser.GetJsonArray(excelFilePath3, "client_profile.json", "iCare-template-system");
		}, "exception was thrown");
		
	}

	@Test
	@DisplayName("Missing Community Connection profile")
	void community_connections_missing() {
		assertThrows(MissingFieldException.class, () -> {
			TemplateParser.GetJsonArray(excelFilePath4, "community_connections.json", "iCare-template-system");
		}, "exception was thrown");

	}

	@Test
	@DisplayName("ltEnroll missing value but one")
	void ltenroll_missing() {
		assertThrows(MissingFieldException.class, () -> {
			TemplateParser.GetJsonArray(excelFilePath2, "lt_client_enroll.json", "iCare-template-system");
		}, "exception was thrown");
	}

}
