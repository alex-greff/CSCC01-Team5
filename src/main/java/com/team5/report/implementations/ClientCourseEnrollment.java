package com.team5.report.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team5.database.DatabaseDriver;
import com.team5.database.MongoDriver;
import com.team5.report.charts.BarChartReport;
import com.team5.report.charts.Report;
import com.team5.report.data.Series;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.JSONLoader;

import org.javatuples.Pair;
import org.json.simple.JSONObject;


public class ClientCourseEnrollment extends BarChartReport {
    private Pair<Double, Double> dimensions;
    private String title, report_name, report_description;
    private Pair<String, String> axisLabels;

    private String db_URI, db_name, db_collection;

    public ClientCourseEnrollment() {
        setupConfigs();
    }

    private void setupConfigs() {
        try {
            // Load configs
            String config_location = (String) ConfigurationLoader.loadConfiguration("report-locations").get("client-course-enrollment");
            JSONObject config = JSONLoader.parseJSONFile(config_location);
            
            // Get data
            Double width = Double.parseDouble(config.get("report-width").toString());
            Double height = Double.parseDouble(config.get("report-height").toString());
            dimensions = new Pair<Double,Double>(width, height);
            title = (String) config.get("title");
            report_name = (String) config.get("report-name");
            report_description = (String) config.get("report-description");

            // Get axis labels
            String xAxisLabel = (String) config.get("xAxis-label");
            String yAxisLabel = (String) config.get("yAxis-label");
            axisLabels = new Pair<String,String>(xAxisLabel, yAxisLabel);

            // Get database information
            db_URI = ConfigurationLoader.loadConfiguration("database-URI").get("icare_db_remote").toString();
            db_name = ConfigurationLoader.loadConfiguration("database-names").get("icare-db-name").toString();
            db_collection = ConfigurationLoader.loadConfiguration("database-collections").get("language-training-client-enroll").toString();

        } catch (Exception e) {
            // Warn if an exception happens while setting up
            System.err.println("Warning: configuration setup failed...");
            e.printStackTrace();
        }
    }

    @Override
    protected List<Series<Pair<String, Number>>> getPairData() {
        // Get connection to the database
        DatabaseDriver db = new MongoDriver(db_URI, db_name, db_collection);
        // Get the documents from the employment services collection
        List<JSONObject> documents = db.queryCollection();
        // Close connection to db
        db.closeConnection();

        // Setup return list
        List<Series<Pair<String, Number>>> ret_list = new ArrayList<>();

        Map<String, Integer> course_to_enrolled = new HashMap<>();

        for (JSONObject item : documents) {
            String course = (String) item.get("course-code");

            // If the course was not found then skip
            if (course == null)
                continue;

            // Increment the course counter 
            if (course_to_enrolled.containsKey(course)) {
                int amt = course_to_enrolled.get(course);
                course_to_enrolled.put(course, amt + 1);
            } else {
                course_to_enrolled.put(course, 1);
            }
        }

        List<Pair<String, Number>> points = new ArrayList<>();

        // Construct the points
        for (Map.Entry<String, Integer> entry : course_to_enrolled.entrySet()) {
            points.add(new Pair<String,Number>(entry.getKey(), entry.getValue()));
        }

        // Construct the series
        Series<Pair<String, Number>> client_series = new Series<>("Clients", points);
        ret_list.add(client_series);

        return ret_list;
    }

    @Override
    protected Pair<Double, Double> getDimensions() {
        return dimensions;
    }

    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    protected Pair<String, String> getAxisLabels() {
        return axisLabels;
    }

    @Override
    public String getReportName() {
        return report_name;
    }

    @Override
    public String getReportDescription() {
        return report_description;
    }

    public static void main(String[] args) throws IOException {
        Report report = new ClientCourseEnrollment();

        report.generate("testFiles/reportTests/ClientCourseEnrollmentReport.png");

        System.out.println("Done!");
    }
}