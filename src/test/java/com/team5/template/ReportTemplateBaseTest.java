package com.team5.template;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.team5.utilities.JSONLoader;

class ReportTemplateBaseTest {

	static ReportTemplateBase RTB;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		RTB = new ReportTemplateBase();
	}

	@Test
	void parseTest() throws FileNotFoundException, IOException, ParseException {
		JSONObject jsonFile = JSONLoader.parseJSONFile(System.getProperty("user.dir") + "/data/templates/iCare-templates/client_profile.json");
		JSONObject RTBjson = RTB.parseTemplate("client_profile.json");
		
		assertEquals(jsonFile, RTBjson);
	}
	
	@Test
	void parseAllTest() {
		fail("Not yet implemented.");
	}

}
