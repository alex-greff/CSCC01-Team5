package com.team5.report.implementations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team5.database.DatabaseDriver;
import com.team5.database.MongoDriver;
import com.team5.report.charts.BarChartReport;
import com.team5.report.charts.PieChartReport;
import com.team5.report.charts.Report;
import com.team5.report.data.Series;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.DateUtils;
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
            db_collection = ConfigurationLoader.loadConfiguration("database-collections").get("nars").toString();

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

        int totalItems = 0;
        Map<String, Integer> age_to_amount = new HashMap<>();
        age_to_amount.put("20-24 years", 0);
        age_to_amount.put("25-29 years", 0);
        age_to_amount.put("30-34 years", 0);
        age_to_amount.put("< 20 years", 0);
        age_to_amount.put("> 34 years", 0);

        // Iterate through each document
        for (JSONObject doc : documents) {
        	// TODO Go through the document to get the require fields
        }

        // TODO Add to data list
        
        // Return the computed data
        return data;
	}

	public static void main(String[] args) {
        Report r = new ClientPopulationSummary();

        r.generate("testFiles/reportTests/ServiceAccessTrend.png");
    }
}