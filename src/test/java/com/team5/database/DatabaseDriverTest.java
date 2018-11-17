package com.team5.database;

import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseDriverTest {
	
	@Mock
	static DatabaseDriver emptyDb;
	
	@Mock
	static DatabaseDriver oneObjectDb;
	
	@Mock
	static DatabaseDriver db;
	
	@BeforeAll
	static void setUpBeforeClass() throws ParseException {
		List<JSONObject> emptyJsonList =  new ArrayList<>();
		List<JSONObject> oneObjectJsonList =  new ArrayList<>();
		List<JSONObject> multipleObjectJsonList =  new ArrayList<>();
		
		emptyDb = mock(MongoDriver.class);
		when(emptyDb.queryCollection()).thenReturn(emptyJsonList); // Set the return for emptyDb
		
		Document doc = new Document();
		doc.append("name", "james");
		oneObjectJsonList.add((JSONObject) new JSONParser().parse(doc.toJson())); // query now has one object
		oneObjectDb = mock(MongoDriver.class);
		when(oneObjectDb.queryCollection()).thenReturn(oneObjectJsonList);// Set the return for oneObjectDb
		
		Document doc2 = new Document();
		doc2.append("name", "james");
		doc2.append("age", "24");
		multipleObjectJsonList.add((JSONObject) new JSONParser().parse(doc2.toJson())); // query now has two objects
		db = mock(MongoDriver.class);
		when(db.queryCollection()).thenReturn(multipleObjectJsonList);
	}

	@Test
	@DisplayName("Test empty database.")
	void testQueryEmptyDatabaseCollection() {
		List<JSONObject> objects = new ArrayList<>();
		
		List<JSONObject> dbObjects = emptyDb.queryCollection();
		
		assertEquals(objects, dbObjects);
	}
	
	@Test
	@DisplayName("Test data base with one object.")
	void testQueryCollectionWithOneObject() throws ParseException {
		List<JSONObject> objects = new ArrayList<>();
		
		Document doc = new Document();
		doc.append("name", "james");
		objects.add((JSONObject) new JSONParser().parse(doc.toJson()));
		
		List<JSONObject> dbObjects = oneObjectDb.queryCollection();
		
		assertEquals(objects, dbObjects);
	}
	
	@Test
	@DisplayName("Test database with multiple objects.")
	void testQueryCollectionWithMultipleObjects() throws ParseException {
		List<JSONObject> objects = new ArrayList<>();
		
		Document doc = new Document();
		doc.append("name", "james");
		doc.append("age", "24");
		objects.add((JSONObject) new JSONParser().parse(doc.toJson()));
		
		List<JSONObject> dbObjects = db.queryCollection();
		
		assertEquals(objects, dbObjects);
	}
}
