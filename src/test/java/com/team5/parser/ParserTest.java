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
	String client_profile = "testFiles/ParseTestFiles/client_profile.xlsx";
	String community_connections = "testFiles/ParseTestFiles/community_connections.xlsx";
	String lt_enrollcorrect = "testFiles/ParseTestFiles/enroll.xlsx";
	String client_profileMissing = "testFiles/ParseTestFiles/client_profileMissing.xlsx";
	String community_connectionsmissing = "testFiles/ParseTestFiles/community_connectionsMISSING.xlsx";
	String lt_enroll = "testFiles/ParseTestFiles/lt_client_enrollment.xlsx";
	String client_profileString = "{\"phone-number\":\"902-628-1285\",\"identifier\":{\"category\":1,\"value\":12345678},\"address\":{\"postal-code\":\"M6G3A4\",\"province\":\"Ontario\",\"city\":\"Toronto\",\"street-number\":1256,\"unit-suit-apt\":205,\"steet-name\":\"College\",\"street-type\":\"Abbey\"},\"display-name\":\"Client Profile\",\"dob\":\"1978-05-20\",\"language-of-preference\":\"English\",\"future-research-or-consultation-consent\":\"Yes\",\"email\":\"hnestor@cathcrosscultural.org\"}";
	String lt_enrollString = "{\"identifier\":{\"category\":1,\"value\":\"dsds\"},\"service-postal-code\":\"M6G4A3\",\"date-of-first-class\":\"2018-05-20\",\"display-name\":\"Language Training - Client Enroll\",\"processing-details\":1,\"dob\":\"1978-05-20\",\"support-services\":{\"support-services-received\":\"Yes\",\"interpretation\":{\"interpretation-received\":\"Yes\",\"language-2\":\"English\",\"language-1\":\"English\"},\"provisions-for-disabilities\":\"Yes\",\"translation\":{\"language-2\":\"English\",\"language-1\":\"English\",\"translation-received\":\"Yes\"},\"children-care\":{\"children\":[{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"},{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"},{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"},{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"},{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"}],\"care-for-newcomer-children\":\"Yes\"},\"crisis-counselling\":\"Yes\",\"transportation\":\"Yes\"},\"update-record-ID\":10387104,\"language-of-preference\":\"English\",\"reason-for-update\":\"Amend record\",\"course-code\":\"L-CCSNAR18008\"}";
    String community_connectionString = "{\"identifier\":{\"category\":1,\"value\":12345678},\"essential-skills-and-aptitudes-training\":{\"computer-skills\":\"Yes\",\"interpersonal-skills-and-workplace-culture\":\"Yes\",\"life-skills\":\"Yes\",\"document-use\":\"Yes\",\"leadership-training\":\"Yes\",\"numeracy\":\"Yes\",\"essential-skills-and-aptitudes-training-received-as-part-of-service\":\"Yes\"},\"processing-details\":1,\"language-of-service\":\"English\",\"reason-for-update\":\"Amend record\",\"referred-by\":\"library\",\"activity-which-client-received-services\":\"Community-based group events and activities\",\"institution-or-organziation-type\":\"Settlement service provider\",\"service-postal-code\":\"M6G3A4\",\"display-name\":\"Community Connections\",\"dob\":\"1978-05-20\",\"services-received\":{\"service-received\":\"Community-based group events and activities: Group session (e.g. conversation circles)\",\"type-of-service\":\"Conversation circle\",\"reason-for-leading-service\":\"Client felt the service was not meeting current needs\",\"start-date\":\"2018-05-20\",\"main-topic-or-focus-of-service-received\":\"Access to local community services\",\"end-date\":\"2018-05-20\",\"directed-at-specific-target-group\":\"Yes\",\"projected-end-date\":\"2018-05-20\",\"volunteers-from-host-community-participated-in-activity\":\"Yes\",\"target-group\":{\"refugees\":\"Yes\",\"seniors\":\"Yes\",\"familties\":\"Yes\",\"other-imparements\":\"Yes\",\"official-language-minorities\":\"Yes\",\"youth\":\"Yes\",\"bind-or-partially-sighted\":\"Yes\",\"clients-with-international-training-in-a-regulated-profession\":\"Yes\",\"gender-specific\":\"Yes\",\"deaf-or-hard-of-hearing\":\"Yes\",\"LGBTQ\":\"Yes\",\"children\":\"Yes\",\"ethnic-or-cultural-or-linguistic-group\":\"Yes\",\"clients-with-international-training-in-a-regulated-trade\":\"Yes\"},\"status-of-service\":\"Service ended early (i.e. client ended participation)\",\"number-of-unique-participants\":\"Less than 10 people\",\"type-of-event-attended\":\"visits pertaining to culture or history\"},\"support-services\":{\"support-services-received\":\"Yes\",\"interpretation\":{\"interpretation-received\":\"Yes\",\"language-2\":\"English\",\"language-1\":\"English\"},\"provisions-for-disabilities\":\"Yes\",\"translation\":{\"language-2\":\"English\",\"language-1\":\"English\",\"translation-received\":\"Yes\"},\"length-of-service\":{\"hours\":1,\"minutes\":50},\"children-care\":{\"children\":[{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"},{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"},{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"},{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"},{\"type-of-care\":\"Short term\",\"age\":\"Infant (6-18 months)\"}],\"care-for-newcomer-children\":\"Yes\"},\"crisis-counselling\":\"Yes\",\"transportation\":\"Yes\"},\"update-record-ID\":10387104,\"language-of-preference\":\"English\"}";
	JSONParser parser = new JSONParser(); // Create JSON parser instance
	
	// Parse the given JSON file
	@Test
	@DisplayName("Missing Client profile")
	void client_profile_missing() {
		assertThrows(MissingFieldException.class, () -> {
			TemplateParser.GetJsonArray(client_profileMissing, "client_profile.json", "iCare-template-system");
		}, "exception was thrown");
		
	}

	@Test
	@DisplayName("Missing Community Connection profile")
	void community_connections_missing() {
		assertThrows(MissingFieldException.class, () -> {
			TemplateParser.GetJsonArray(community_connectionsmissing, "community_connections.json", "iCare-template-system");
		}, "exception was thrown");

	}

	@Test
	@DisplayName("ltEnroll missing value but one")
	void ltenroll_missing() {
		assertThrows(MissingFieldException.class, () -> {
			TemplateParser.GetJsonArray(lt_enroll, "lt_client_enroll.json", "iCare-template-system");
		}, "exception was thrown");
	}
	
	@Test
	@DisplayName("Checks if JSON client profile is Equal")
	void client_equal() throws FileNotFoundException, IOException, ParseException, ConfigurationNotFoundException, MissingFieldException {
		ArrayList<JSONObject> clientjson = TemplateParser.GetJsonArray(client_profile, "client_profile.json", "iCare-template-system");
		assertEquals(client_profileString,clientjson.get(0).toString());
	}
	
	@Test
	@DisplayName("Checks if JSON lt_enroll is Equal")
	void lt_enroll_equal() throws FileNotFoundException, IOException, ParseException, ConfigurationNotFoundException, MissingFieldException {
		ArrayList<JSONObject> clientjson = TemplateParser.GetJsonArray(lt_enrollcorrect, "lt_client_enroll.json", "iCare-template-system");
		assertEquals(lt_enrollString ,clientjson.get(0).toString());
	}
	
	@Test
	@DisplayName("Checks if JSON community_connections is Equal")
	void community_connections() throws FileNotFoundException, IOException, ParseException, ConfigurationNotFoundException, MissingFieldException {
		ArrayList<JSONObject> clientjson = TemplateParser.GetJsonArray(community_connections, "community_connections.json", "iCare-template-system");
		assertEquals(community_connectionString ,clientjson.get(0).toString());
	}
	

}
