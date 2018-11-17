package com.team5.database;

import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

//import org.bson.Document;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseDriverTest {
	
	@Mock
	static DatabaseDriver emptyDb;
	
	@Mock
	static DatabaseDriver oneObjectDb;
	
	@Mock
	static DatabaseDriver db;
	
	@BeforeAll
	static void setUpBeforeClass() {
		emptyDb = mock(MongoDriver.class);
		when(emptyDb.queryCollection()).thenReturn(new ArrayList<JSONObject>()); // Set the return for empt
		oneObjectDb = mock(MongoDriver.class);
		db = mock(MongoDriver.class);
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
	void testQueryCollectionWithOneObject() {
		
	}
	
	@Test
	@DisplayName("Test database with multiple objects.")
	void testQueryCollectionWithMultipleObjects() {
		
	}
	
}
