package com.team5.database;

import com.team5.utilities.JSONLoader;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Database driver class that deals with modifying database.
 */
public class DatabaseDriver {
	private MongoClientURI uri;
	private MongoClient client;
	private MongoDatabase database;
	
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
		} catch (MongoClientException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the connection to the database.
	 */
	public void closeConnection() {
		client.close(); // close the connection
	}
	
	/**
	 * Inserts JSON object into the client_profile collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertClientProfile(JSONObject jsonObject) {
		MongoCollection<Document> collection = this.database.getCollection("client_profile");
		
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON objects into the client_profile collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertClientProfiles(List<JSONObject> jsonObjects) {
		MongoCollection<Document> collection = this.database.getCollection("client_profile");

		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/**
	 * Inserts JSON object into the commuinity_collections collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertCommunityCollection(JSONObject jsonObject) {
		MongoCollection<Document> collection = this.database.getCollection("community_collections");
		
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON objects into the community_collections collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertCommunityCollections(List<JSONObject> jsonObjects) {
		MongoCollection<Document> collection = this.database.getCollection("community_collections");

		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/**
	 * Inserts JSON object into the employment collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertEmployment(JSONObject jsonObject) {
		MongoCollection<Document> collection = this.database.getCollection("employment");
		
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON objects into the employment collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertEmployments(List<JSONObject> jsonObjects) {
		MongoCollection<Document> collection = this.database.getCollection("employment");

		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/**
	 * Inserts JSON object into the info_and_orientation collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertInfoAndOrientation(JSONObject jsonObject) {
		MongoCollection<Document> collection = this.database.getCollection("info_and_orientation");
		
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON objects into the info_and_orientation collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertInfoAndOrientations(List<JSONObject> jsonObjects) {
		MongoCollection<Document> collection = this.database.getCollection("info_and_orientation");

		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/**
	 * Inserts JSON object into the lt_client_enrollment collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertLtClientEnrollment(JSONObject jsonObject) {
		MongoCollection<Document> collection = this.database.getCollection("lt_client_enrollment");
		
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON objects into the lt_client_enrollment collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertLtClientEnrollments(List<JSONObject> jsonObjects) {
		MongoCollection<Document> collection = this.database.getCollection("lt_client_enrollment");

		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/**
	 * Inserts JSON object into the lt_client_exit collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertLtClientExit(JSONObject jsonObject) {
		MongoCollection<Document> collection = this.database.getCollection("lt_client_exit");
		
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON objects into the lt_client_exit collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertLtClientExits(List<JSONObject> jsonObjects) {
		MongoCollection<Document> collection = this.database.getCollection("lt_client_exit");

		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/**
	 * Inserts JSON object into the nars collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertNar(JSONObject jsonObject) {
		MongoCollection<Document> collection = this.database.getCollection("nars");
		
		// Parse JSONObject
		Document doc = Document.parse(jsonObject.toJSONString());
		
		collection.insertOne(doc); // Add to database collection
	}
	
	/**
	 * Inserts a list of JSON objects into the nars collection of the database.
	 * @param jsonObject The JSON object to be added to the database collection.
	 */
	public void insertNars(List<JSONObject> jsonObjects) {
		MongoCollection<Document> collection = this.database.getCollection("nars");

		// Go through list of JSONObjects and parse each one
		List<Document> docs = new ArrayList<>();
		for (JSONObject object : jsonObjects) {
			Document doc = Document.parse(object.toJSONString());
			docs.add(doc);
		}
		
		collection.insertMany(docs); // Add the list of documents to database collection
	}
	
	/**
	 * Gets the document specified from client_profile collection.
	 * @param key The key.
	 * @param value The search value for the key
	 * @return The selected document.
	 */
	public Document getClientProfile(String key, String value) {
		MongoCollection<Document> collection = this.database.getCollection("client_profile");
		Document doc = null;
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find(Filters.eq(key, value)).iterator();
		
		if (cursor.hasNext()) {
			doc = cursor.next();
		}
		
		return doc;
		
	}
	
	/**
	 * Gets all documents from the client_profile collection
	 * @return The documents.
	 */
	public List<Document> getClientProfiles() {
		MongoCollection<Document> collection = this.database.getCollection("client_profile");
		List<Document> docs = new ArrayList<>();
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find().iterator();
		
		while (cursor.hasNext()) {
			docs.add(cursor.next());
		}
		
		return docs;
		
	}
	
	/**
	 * Gets the document specified from community_collections collection.
	 * @param key The key.
	 * @param value The search value for the key
	 * @return The selected document.
	 */
	public Document getCommunityCollection(String key, String value) {
		MongoCollection<Document> collection = this.database.getCollection("community_collections");
		Document doc = null;
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find(Filters.eq(key, value)).iterator();
		
		if (cursor.hasNext()) {
			doc = cursor.next();
		}
		
		return doc;
		
	}
	
	/**
	 * Gets all documents from the client_profile collection
	 * @return The documents.
	 */
	public List<Document> getCommunityCollections() {
		MongoCollection<Document> collection = this.database.getCollection("client_profile");
		List<Document> docs = new ArrayList<>();
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find().iterator();
		
		while (cursor.hasNext()) {
			docs.add(cursor.next());
		}
		
		return docs;
		
	}
	
	/**
	 * Gets the document specified from employment collection.
	 * @param key The key.
	 * @param value The search value for the key
	 * @return The selected document.
	 */
	public Document getEmployment(String key, String value) {
		MongoCollection<Document> collection = this.database.getCollection("employment");
		Document doc = null;
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find(Filters.eq(key, value)).iterator();
		
		if (cursor.hasNext()) {
			doc = cursor.next();
		}
		
		return doc;
		
	}
	
	/**
	 * Gets all documents from the employment collection
	 * @return The documents.
	 */
	public List<Document> getEmployments() {
		MongoCollection<Document> collection = this.database.getCollection("employment");
		List<Document> docs = new ArrayList<>();
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find().iterator();
		
		while (cursor.hasNext()) {
			docs.add(cursor.next());
		}
		
		return docs;
		
	}
	
	/**
	 * Gets the document specified from info_and_orientation collection.
	 * @param key The key.
	 * @param value The search value for the key
	 * @return The selected document.
	 */
	public Document getInfoAndOrientation(String key, String value) {
		MongoCollection<Document> collection = this.database.getCollection("info_and_orientation");
		Document doc = null;
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find(Filters.eq(key, value)).iterator();
		
		if (cursor.hasNext()) {
			doc = cursor.next();
		}
		
		return doc;
		
	}
	
	/**
	 * Gets all documents from the info_and_orientation collection
	 * @return The documents.
	 */
	public List<Document> getInfoAndOrientations() {
		MongoCollection<Document> collection = this.database.getCollection("info_and_orientation");
		List<Document> docs = new ArrayList<>();
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find().iterator();
		
		while (cursor.hasNext()) {
			docs.add(cursor.next());
		}
		
		return docs;
		
	}
	
	/**
	 * Gets the document specified from lt_client_enrollment collection.
	 * @param key The key.
	 * @param value The search value for the key
	 * @return The selected document.
	 */
	public Document getLtClientEnrollment(String key, String value) {
		MongoCollection<Document> collection = this.database.getCollection("lt_client_enrollment");
		Document doc = null;
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find(Filters.eq(key, value)).iterator();
		
		if (cursor.hasNext()) {
			doc = cursor.next();
		}
		
		return doc;
		
	}
	
	/**
	 * Gets all documents from the lt_client_enrollment collection
	 * @return The documents.
	 */
	public List<Document> getLtClientEnrollments() {
		MongoCollection<Document> collection = this.database.getCollection("lt_client_enrollment");
		List<Document> docs = new ArrayList<>();
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find().iterator();
		
		while (cursor.hasNext()) {
			docs.add(cursor.next());
		}
		
		return docs;
		
	}
	
	/**
	 * Gets the document specified from lt_client_exit collection.
	 * @param key The key.
	 * @param value The search value for the key
	 * @return The selected document.
	 */
	public Document getLtClientExit(String key, String value) {
		MongoCollection<Document> collection = this.database.getCollection("lt_client_exit");
		Document doc = null;
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find(Filters.eq(key, value)).iterator();
		
		if (cursor.hasNext()) {
			doc = cursor.next();
		}
		
		return doc;
		
	}
	
	/**
	 * Gets all documents from the lt_client_exit collection
	 * @return The documents.
	 */
	public List<Document> getLtClientExits() {
		MongoCollection<Document> collection = this.database.getCollection("lt_client_exit");
		List<Document> docs = new ArrayList<>();
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find().iterator();
		
		while (cursor.hasNext()) {
			docs.add(cursor.next());
		}
		
		return docs;
		
	}
	
	/**
	 * Gets the document specified from nars collection.
	 * @param key The key.
	 * @param value The search value for the key
	 * @return The selected document.
	 */
	public Document getNar(String key, String value) {
		MongoCollection<Document> collection = this.database.getCollection("nars");
		Document doc = null;
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find(Filters.eq(key, value)).iterator();
		
		if (cursor.hasNext()) {
			doc = cursor.next();
		}
		
		return doc;
		
	}
	
	/**
	 * Gets all documents from the nars collection
	 * @return The documents.
	 */
	public List<Document> getNars() {
		MongoCollection<Document> collection = this.database.getCollection("nars");
		List<Document> docs = new ArrayList<>();
		
		// Find the cursor, based on the search filters
		MongoCursor<Document> cursor = collection.find().iterator();
		
		while (cursor.hasNext()) {
			docs.add(cursor.next());
		}
		
		return docs;
		
	}
	
	// Demo
	public static void main(String[] args) {
		System.out.println("The DatabaseDriver is the driver that connects, and allows us to populate our database.");
		System.out.println("Initializing a new DatabaseDriver with the specified fields will connect us to the database.");
		DatabaseDriver db = new DatabaseDriver("mongodb://mo:ProficiousF18@ds031088.mlab.com:31088/icare_db",
												"icare_db", "client_profile");
		System.out.println("We are now connected to the database icare_db on the cloud server.");
		System.out.println("We are now going to insert two JSONObjects into the database collections, \'client_profile\'.");
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
		db.insertClientProfiles(ob);
		db.insertCommunityCollections(ob);
		db.insertEmployments(ob);
		db.insertInfoAndOrientations(ob);
		db.insertLtClientEnrollments(ob);
		db.insertLtClientExits(ob);
		db.insertNars(ob);
		
		System.out.println("JSON objects inserted.");
		
		System.out.println("\nNow getting inserted objects from one database collection.");
		
		System.out.println("\nlt_client_enrollment:\n");
		
		for (Document doc : db.getLtClientEnrollments()) {
			System.out.println(doc.toJson());
		}
		
		System.out.println("\nNow closing connection.");
		db.closeConnection();
		System.out.println("Connection closed.");
		
	}

}
