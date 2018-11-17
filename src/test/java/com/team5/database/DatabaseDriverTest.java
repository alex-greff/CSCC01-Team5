package com.team5.database;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.bson.Document;

import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.Test;

//@RunWith(MockitoJUnitRunner.class)
public class DatabaseDriverTest {
	
	@Mock
	DatabaseDriver emptyDb;
	
	@Mock
	DatabaseDriver oneObjectDb;
	
	@Mock
	DatabaseDriver db;
	
	@BeforeAll
	public void setUpBeforeClass() {
		emptyDb = Mockito.mock(MongoDriver.class);
		oneObjectDb = Mockito.mock(MongoDriver.class);
		db = Mockito.mock(MongoDriver.class);
	}

	@Test
	public void testQueryEmptyDatabase() {
		
	}
	
	@Test
	public void testQueryCollectionWithOneData() {
		
	}
	
	@Test
	public void testQueryCollectionWithMultipleData() {
		
	}
	
}
