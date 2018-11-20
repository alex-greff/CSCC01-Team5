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
import com.team5.report.charts.PieChartReport;
import com.team5.report.charts.Report;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.DateUtils;
import com.team5.utilities.JSONLoader;

import org.javatuples.Pair;
import org.json.simple.JSONObject;

public class EmploymentServicesDemographics extends PieChartReport {
    private Pair<Double, Double> dimensions;
    private String title, report_name, report_description;

    private String db_URI, db_name, db_collection;

    /**
     * Constructs a new employment services demographics report object.
     */
    public EmploymentServicesDemographics() {
        setup();
    }

    /**
     * Sets up the report.
     */
    private void setup() {
        try {
            // Load configs
            String config_location = (String) ConfigurationLoader.loadConfiguration("report-locations").get("employment-services-demographics");
            JSONObject config = JSONLoader.parseJSONFile(config_location);
            
            // Get data
            Double width = Double.parseDouble(config.get("report-width").toString());
            Double height = Double.parseDouble(config.get("report-height").toString());
            dimensions = new Pair<Double,Double>(width, height);
            title = (String) config.get("title");
            report_name = (String) config.get("report-name");
            report_description = (String) config.get("report-description");

            // Get database information
            db_URI = ConfigurationLoader.loadConfiguration("database-URI").get("icare_db_remote").toString();
            db_name = ConfigurationLoader.loadConfiguration("database-names").get("icare-db-name").toString();
            db_collection = ConfigurationLoader.loadConfiguration("database-collections").get("employment-services").toString();

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
    protected List<Pair<String, Double>> getPieChartData() {
        // Get connection to the database
        DatabaseDriver db = new MongoDriver(db_URI, db_name, db_collection);
        // Get the documents from the employment services collection
        List<JSONObject> documents = db.queryCollection();
        // Close connection to db
        db.closeConnection();

        // Initialize data list
        List<Pair<String, Double>> data = new ArrayList<>();

        int totalItems = 0;
        Map<String, Integer> age_to_amount = new HashMap<>();
        age_to_amount.put("20-24 years", 0);
        age_to_amount.put("25-29 years", 0);
        age_to_amount.put("30-34 years", 0);
        age_to_amount.put("< 20 years", 0);
        age_to_amount.put("> 34 years", 0);

        // Iterate through each document
        for (JSONObject doc : documents) {
            String dob_str = doc.get("dob").toString(); // Get the dob of the enty

            Date now = new Date();

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date dob = format.parse(dob_str);

                int year_diff = DateUtils.getDiffYears(dob, now);

                if (year_diff < 20) 
                    age_to_amount.put("< 20 years", age_to_amount.get("< 20 years") + 1);
                else if (year_diff >= 20 && year_diff <= 24) 
                    age_to_amount.put("20-24 years", age_to_amount.get("20-24 years") + 1);
                else if (year_diff >= 25 && year_diff <= 29) 
                    age_to_amount.put("25-29 years", age_to_amount.get("25-29 years") + 1);
                else if (year_diff >= 30 && year_diff <= 34) 
                    age_to_amount.put("30-34 years", age_to_amount.get("30-34 years") + 1);
                else 
                    age_to_amount.put("> 34 years", age_to_amount.get("> 34 years") + 1);

                totalItems++;

            } catch (ParseException e) {
                continue; // Skip if parse exception occurs
            }
        }

        // Convert the tallied data into percents and add it to the data list
        for (Map.Entry<String, Integer> entry : age_to_amount.entrySet()) {
            String category = entry.getKey();
            int tally = entry.getValue();

            double percent = ((double) tally / (double) totalItems) * 100.0;

            if(percent > 0.0)
                data.add(new Pair<String,Double>(category, percent));
        }
        
        // Return the computed data
        return data;
    }

    public static void main(String[] args) {
        Report r = new EmploymentServicesDemographics();

        r.generate("testFiles/reportTests/EmploymentServicesDemographics.png");
    }
}