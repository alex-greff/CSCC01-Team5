package com.team5.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.utilities.JSONLoader;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Mongo driver class that deals with modifying database.
 */
public class MongoDriver implements DatabaseDriver {
	private MongoClientURI uri;
	private MongoClient client;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	/**
	 * Constructs the database driver.
	 * @param client The database client for connection.
	 * @param database The name of the database to get from the client.
	 */
	public MongoDriver(String uri, String database, String collection) {
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
	@Override
	public void useCollection(String collection) {
		this.collection = database.getCollection(collection);
	}
	
	/**
	 * Inserts JSON file object into the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	@Override
	public void insert(JSONObject jsonObject) {
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON file objects into the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	@Override
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
	 * Retrieves all documents from the current collection.
	 * 
	 * @return A list of JSONObjects.
	 */
	@Override
	public List<JSONObject> queryCollection() {
		// Query the database with no filter
		return queryCollection(new Document());
	}

	/**
	 * Retrieves all documents from the current collection that match the inputted query.
	 * 
	 * @param query The query to the database.
	 * @return Returns a list of JSONObjects.
	 */
	@Override
	public List<JSONObject> queryCollection(Bson query) {
		List<JSONObject> queried_docs = new ArrayList<>();

		// Find all documents matching the query
		FindIterable<Document> findIterable = collection.find(query);

		// Initialize a JSON parser instance
		JSONParser parser = new JSONParser();

		// Iterate through each filtered document
		for (Document doc : findIterable) {
			try {
				// Parse to JSON
				JSONObject doc_obj = (JSONObject) parser.parse(doc.toJson());
				// Add to the queried document JSON list
				queried_docs.add(doc_obj);

			} catch (ParseException e) {
				// If an error occurs parsing then just print the stack trace and ignore it
				e.printStackTrace();
			}
		}

		// Return the queried docs
		return queried_docs;
	}
	
	/**
	 * Closes the connection to the database.
	 */
	@Override
	public void closeConnection() {
		client.close(); // close the connection
	}

	/**
	 * Deconstructor
	 */
	@Override
	public void finalize() {
		closeConnection(); // Close the connection
	}
	
	// Demo
	public static void main(String[] args) {
		System.out.println("The DatabaseDriver is the driver that connects, and allows us to populate our database.");
		System.out.println("Initializing a new DatabaseDriver with the specified fields will connect us to the database.");
		MongoDriver db = null;
		try {
			db = new MongoDriver(ConfigurationLoader.loadConfiguration("database-URI").get("test_db_remote").toString(),
													"test_db", "client_profile");
		} catch (ConfigurationNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("We are now connected to the database test_db on the cloud server.");
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
		
		System.out.println("Now we will get the objects that we inserted into the database. Printing objects...\n");
		
		List<JSONObject> data = db.queryCollection();
		for (JSONObject obj : data) {
			obj.remove("_id"); // Removes the _id field
			System.out.println(obj.toJSONString());
		}
		
		System.out.println("\nNow closing connection.");
		db.closeConnection();
		System.out.println("Connection closed.");
		
	}

}