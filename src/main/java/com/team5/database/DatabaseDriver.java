package com.team5.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;

/**
 * Database driver class that deals with modifying database.
 */
public class DatabaseDriver {
	
	private final static String URI = "mongodb://<dbuser>:<dbpassword>@ds031088.mlab.com:31088/icare_db"; // Our database URI

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
		this.uri = new MongoClientURI(uri);
		this.client = new MongoClient(this.uri); // Connect to client
		this.database = this.client.getDatabase(database); // Get the database
		this.collection = this.database.getCollection(collection); // Get the collection
	}
	
	public void insert() {
		// Get cursor
		FindIterable<Document> records = collection.find();
		MongoCursor<Document> cursor = records.iterator();
		
	}
	
	public void select() {
		// Get cursor
		FindIterable<Document> records = collection.find();
		MongoCursor<Document> cursor = records.iterator();
		
		while (cursor.hasNext()) {
			Document doc = cursor.next();
		    String firstName = doc.getString("name");
		    int lastName = doc.getInteger("age");
		    System.out.println("First Name: " + firstName);
		    System.out.println("Last Name: " + lastName);
		}
	}
	
	public void update() {
		// Get cursor
		FindIterable<Document> records = collection.find();
		MongoCursor<Document> cursor = records.iterator();
	}
	
	public static void main(String[] args) {
		DatabaseDriver db = new DatabaseDriver("mongodb://Mohammed:1234@localhost:27017/javatest", "javatest", "customer");
		db.select();
	}
}
