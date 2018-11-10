package com.team5.database;

import java.util.List;

import org.bson.conversions.Bson;
import org.json.simple.JSONObject;

/**
 * Database Driver interface that all database drivers must implement.
 *
 */
public interface DatabaseDriver {
	
	/**
	 * Accesses a collection in the database. (NOTE: if collection doesn't exist
	 * and you specify it, then inserting will create a new collection with the name
	 * specified).
	 * @param collection The collection to be accessed.
	 */
	public void useCollection(String collection);
	
	/**
	 * Inserts JSON file object into the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insert(JSONObject jsonObject);
	
	/**
	 * Inserts a list of JSON file objects into the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertMany(List<JSONObject> jsonObjects);
	
	/**
	 * Retrieves all documents from the current collection.
	 * 
	 * @return A list of JSONObjects.
	 */
	public List<JSONObject> queryCollection();
	
	/**
	 * Retrieves all documents from the current collection that match the inputted query.
	 * 
	 * @param query The query to the database.
	 * @return Returns a list of JSONObjects.
	 */
	public List<JSONObject> queryCollection(Bson query);
	
	/**
	 * Closes the connection to the database.
	 */
	public void closeConnection();

}
