package com.team5.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.team5.utilities.JSONLoader;

public class TemplateLoaderTest {

	static TemplateLoader tl;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		tl = new TemplateLoader("data/templates/iCare-templates");
	}

	@Test
	void parseTest() throws FileNotFoundException, IOException, ParseException {
		JSONObject jsonFile = JSONLoader.parseJSONFile(System.getProperty("user.dir") + "/data/templates/iCare-templates/client_profile.json");
		JSONObject tlJSON = tl.parseTemplate("client_profile.json");
		
		assertEquals(jsonFile, tlJSON);
	}
	
	@Test
	void parseAllTest() {
		// fail("Not yet implemented.");
	}
}
