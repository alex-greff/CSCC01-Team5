package com.team5.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Database driver class that deals with modifying database.
 */
public class DatabaseDriver {

	MongoClient client;
	MongoDatabase database;
	
	/**
	 * Constructs the database driver.
	 * @param client The database client for connection.
	 * @param database The name of the database to get from the client.
	 */
	public DatabaseDriver(MongoClient client, String database) {
		this.client = client; // Connect to client
		this.database = client.getDatabase(database); // Get the database
	}
	
	public void insert() {
		
	}
	
	public void select() {
		
	}
	
	public void update() {
		
	}
	
	public static void main(String[] args) {
		DatabaseDriver db;
	}
}
