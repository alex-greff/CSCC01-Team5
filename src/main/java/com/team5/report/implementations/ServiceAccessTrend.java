package com.team5.report.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team5.database.DatabaseDriver;
import com.team5.database.MongoDriver;
import com.team5.report.charts.Report;
import com.team5.report.charts.BarChartReport;
import com.team5.report.data.Series;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.JSONLoader;
import org.javatuples.Pair;
import org.json.simple.JSONObject;

public class ServiceAccessTrend extends BarChartReport {
    private Pair<Double, Double> dimensions;
    private String title, report_name, report_description, xAxisLabel, yAxisLabel;

    private String db_URI, db_name, db_collection;

    /**
     * Constructs a new employment services demographics report object.
     */
    public ServiceAccessTrend() {
        setup();
    }

    /**
     * Sets up the report.
     */
    private void setup() {
        try {
            // Load configs
            String config_location = (String) ConfigurationLoader.loadConfiguration("report-locations").get("service-access-trend");
            JSONObject config = JSONLoader.parseJSONFile(config_location);
            
            // Get data
            Double width = Double.parseDouble(config.get("report-width").toString());
            Double height = Double.parseDouble(config.get("report-height").toString());
            dimensions = new Pair<Double,Double>(width, height);
            title = (String) config.get("title");
            xAxisLabel = (String) config.get("xAxisLabel");
            yAxisLabel = (String) config.get("yAxisLabel");
            report_name = (String) config.get("report-name");
            report_description = (String) config.get("report-description");

            // Get database information
            db_URI = ConfigurationLoader.loadConfiguration("database-URI").get("icare_db_remote").toString();
            db_name = ConfigurationLoader.loadConfiguration("database-names").get("icare-db-name").toString();
            db_collection = ConfigurationLoader.loadConfiguration("database-collections").get("info-and-orientation").toString();

        } catch (Exception e) {
            // Warn if an exception happens while setting up
            System.err.println("Warning: configuration setup failed...");
            e.printStackTrace();
        }
    }

    @Override
    protected Pair<Double, Double> getDimensions() {
        return dimensions;
    }

    @Override
    public String getReportName() {
        return report_name;
    }

    @Override
    public String getReportDescription() {
        return report_description;
    }


    @Override
    protected String getTitle() {
        return title;
    }
    
    @Override
	protected Pair<String, String> getAxisLabels() {
		return new Pair<String, String>(xAxisLabel, yAxisLabel);
	}
    
    @SuppressWarnings("unchecked")
	@Override
	protected List<Series<Pair<String, Number>>> getPairData() {
    	// Get connection to the database
        DatabaseDriver db = new MongoDriver(db_URI, db_name, db_collection);
        // Get the documents from the employment services collection
        List<JSONObject> documents = db.queryCollection();
        // Close connection to db
        db.closeConnection();

        // Initialize data list
        List<Series<Pair<String, Number>>> data = new ArrayList<>();
        
        // Initialize List of target groups
        List<String> targetNames = new ArrayList<>();
        
        // Look for target groups in services-received
        JSONObject targetGroups = (JSONObject) documents.get(0).get("services-received");
        targetGroups = (JSONObject) targetGroups.get("target-group");
        targetNames.addAll(targetGroups.keySet()); // Add the names to the list
        
        // Initialize data pairings
        Map<String, Map<String, Integer>> seriesToData = new HashMap<>();
        
        // Represent each series of the chart
        for (String targetName : targetNames) {
        	Map<String, Integer> serviceToAmount = new HashMap<>();
        	
        	// Initialize the x and y value pairs of the chart
        	serviceToAmount.put("education", 0);
        	serviceToAmount.put("housing", 0);
        	serviceToAmount.put("health", 0);
        	serviceToAmount.put("transportation", 0);
        	serviceToAmount.put("employment-and-income", 0);
        	
        	// Map each target name to the pairings
        	seriesToData.put(targetName, serviceToAmount);
        }

        // Iterate through each document
        for (JSONObject doc : documents) {
        	// Get the value for each target group field
        	if (doc.containsKey("services-received")) {
	        	doc = (JSONObject) doc.get("services-received");
	        	JSONObject targets = (JSONObject) doc.get("target-group");
	        	
	        	// Go through each target group and increment their value if they are using the service
	        	for (String target : targetNames) {
	        	
		        	String targetValue = targets.get(target).toString();
		        	
		        	// Check if value is required value
		        	if (targetValue.equalsIgnoreCase("yes")) {
		        		Map<String, Integer> targetData = seriesToData.get(target);
		        		// Go through all the services
		        		for (String service : targetData.keySet()) {
			        		String serviceField = doc.get(service).toString(); // Get the service field
			        		
			        		// Increment the field if the target group has accessed the service
			        		if (serviceField.equalsIgnoreCase("yes")) {
			        			int newValue = targetData.get(service) + 1; // Increment
			        			targetData.replace(service, newValue);
			        		}
		        		}
		        	}
	        	}
        	}
        }

        // Plot the value for each service based on the target group
        for (String targetName : targetNames) {
        	Series<Pair<String, Number>> targetSeries = new Series<>(targetName, new ArrayList<Pair<String, Number>>());
        
        	Map<String, Integer> targetData = seriesToData.get(targetName);
        	
		    // Get the x-value (service) and y-value (number of clients) from each mapping pair
		    for (Map.Entry<String, Integer> entry : targetData.entrySet()) {
		    	String service = entry.getKey();
		        Integer amount = new Integer(entry.getValue());
		
		        targetSeries.addItem(new Pair<String, Number>(service, amount)); // Plot for the target group in the chart
		    }

	        data.add(targetSeries); // Add to the data list
        }
        
        // Return the computed data
        return data;
	}

	public static void main(String[] args) {
        Report r = new ServiceAccessTrend();

        r.generate("testFiles/reportTests/ServiceAccessTrend.png");
    }
}