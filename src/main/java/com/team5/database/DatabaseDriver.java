package com.team5.database;

import com.team5.utilities.JSONLoader;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Database driver class that deals with modifying database.
 */
public class DatabaseDriver {
	private MongoClientURI uri;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	/**
	 * Constructs the database driver.
	 * @param client The database client for connection.
	 * @param database The name of the database to get from the client.
	 */
	public DatabaseDriver(String uri, String database, String collection) {
		try {
			this.uri = new MongoClientURI(uri); // URI of the client
			this.client = new MongoClient(this.uri); // Connect to client
			this.database = this.client.getDatabase(database); // Get the database
			this.collection = this.database.getCollection(collection); // Get the collection
		} catch (MongoClientException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Accesses a collection in the database. (NOTE: if collection doesn't exist
	 * and you specify it, then inserting will create a new collection with the name
	 * specified).
	 * @param collection The collection to be accessed.
	 */
	public void useCollection(String collection) {
		this.collection = database.getCollection(collection);
	}
	
	/**
	 * Inserts JSON file object into the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insert(JSONObject jsonObject) {
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON file objects into the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertMany(List<JSONObject> jsonObjects) {
		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/**
	 * Retrieves data from the database.
	 * @return A list of JSONObjects.
	 */
	public List<JSONObject> queryDatabase() {
		List<JSONObject> data = new ArrayList<>(); // List of JSONObjects
		
		MongoCursor<Document> cursor = collection.find().iterator(); // Get the cursor
		
		// Go through the data in the collection
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			JSONParser parser = new JSONParser();
			JSONObject object = null;
			try {
				object = (JSONObject) parser.parse(doc.toJson()); // Convert document to JSONObject;
				object.remove("_id"); // Removes the _id field
				data.add(object);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		
		return data;
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public void closeConnection() {
		client.close(); // close the connection
	}
	
	// Demo
	public static void main(String[] args) {
		System.out.println("The DatabaseDriver is the driver that connects, and allows us to populate our database.");
		System.out.println("Initializing a new DatabaseDriver with the specified fields will connect us to the database.");
		DatabaseDriver db = new DatabaseDriver("mongodb://mo:ProficiousF18@ds031088.mlab.com:31088/icare_db",
												"icare_db", "client_profile");
		System.out.println("We are now connected to the database icare_db on the cloud server.");
		System.out.println("We are now going to insert two JSONObjects into the database collection, \'client_profile\'.");
		JSONObject jsonObject = null;
		List<JSONObject> ob = new ArrayList<>();
		try {
			jsonObject = JSONLoader.parseJSONFile("testFiles/JSONTestFiles/testJSONFile_valid1.json");
			System.out.println(jsonObject.toJSONString());
			ob.add(jsonObject);
			
			jsonObject = JSONLoader.parseJSONFile("testFiles/JSONTestFiles/testJSONFile_valid2.json");
			System.out.println(jsonObject.toJSONString());
			ob.add(jsonObject);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Inserting JSON objects into database...");
		db.insertMany(ob);
		System.out.println("JSON objects inserted.");
		
		System.out.println("\nNow closing connection.");
		db.closeConnection();
		System.out.println("Connection closed.");
		
	}

}
