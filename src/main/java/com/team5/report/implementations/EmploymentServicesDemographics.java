package com.team5.report.implementations;

import java.util.ArrayList;
import java.util.List;

import com.team5.database.DatabaseDriver;
import com.team5.database.MongoDriver;
import com.team5.report.charts.PieChartReport;
import com.team5.report.charts.Report;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.utilities.JSONLoader;

import org.apache.commons.io.FilenameUtils;
import org.javatuples.Pair;
import org.json.simple.JSONObject;

public class EmploymentServicesDemographics extends PieChartReport {
    private Pair<Double, Double> dimensions;
    private String title, report_name, report_description;

    private String db_URI, db_name, db_collection;

    public EmploymentServicesDemographics() {
        setup();
    }

    private void setup() {
        try {
            String config_location = (String) ConfigurationLoader.loadConfiguration("report-locations").get("employment-services-demographics");
            JSONObject config = JSONLoader.parseJSONFile(config_location);
            
            Double width = Double.parseDouble(config.get("report-width").toString());
            Double height = Double.parseDouble(config.get("report-height").toString());
            dimensions = new Pair<Double,Double>(width, height);
            // dimensions = new Pair<Double,Double>(800.0, 800.0);
            title = (String) config.get("title");
            report_name = (String) config.get("report-name");
            report_description = (String) config.get("report-description");

            db_URI = ConfigurationLoader.loadConfiguration("database-URI").get("icare_db_remote").toString();
            db_name = ConfigurationLoader.loadConfiguration("database-names").get("icare-db-name").toString();
            db_collection = ConfigurationLoader.loadConfiguration("database-collections").get("employment-services").toString();
            

        } catch (Exception e) {
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

        for (JSONObject jobj : documents) {
            

        }

        
        return data;
    }

    public void test() {
        getPieChartData();
    }

    public static void main(String[] args) {
        EmploymentServicesDemographics r = new EmploymentServicesDemographics();

        r.test();
    }
}