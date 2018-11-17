package com.team5.database;

import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseDriverTest {
	
	@Mock
	DatabaseDriver emptyDb;
	
	@Mock
	DatabaseDriver oneObjectDb;
	
	@Mock
	DatabaseDriver db;
	
	@BeforeAll
	public void setUpBeforeClass() {
		emptyDb = mock(MongoDriver.class);
		oneObjectDb = mock(MongoDriver.class);
		db = mock(MongoDriver.class);
	}

	@Test
	public void testQueryEmptyDatabase() {
		List<JSONObject> objects = new ArrayList<>();
		
		List<JSONObject> dbObjects = emptyDb.queryCollection();
		
		assertEquals(objects, dbObjects);
	}
	
	@Test
	public void testQueryCollectionWithOneData() {
		
	}
	
	@Test
	public void testQueryCollectionWithMultipleData() {
		
	}
	
}
