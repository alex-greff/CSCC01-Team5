package com.team5.report.implementations;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.team5.database.DatabaseDriver;
import com.team5.database.MongoDriver;
import com.team5.report.charts.LineChartReport;
import com.team5.report.charts.Report;
import com.team5.report.data.Series;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.JSONLoader;

import org.javatuples.Pair;
import org.json.simple.JSONObject;


public class IncompleteCoursesOverTime extends LineChartReport {
    private Pair<Double, Double> dimensions;
    private String title, report_name, report_description;
    private Pair<String, String> axisLabels;

    private String db_URI, db_name, db_collection;

    private final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public IncompleteCoursesOverTime() {
        setupConfigs();
    }

    private void setupConfigs() {
        try {
            // Load configs
            String config_location = (String) ConfigurationLoader.loadConfiguration("report-locations").get("incomplete-courses-over-time");
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
            db_collection = ConfigurationLoader.loadConfiguration("database-collections").get("language-training-client-exit").toString();

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

        // Format: (Course) : (Month : Incomplete Number)
        Map<String, Map<String, Integer>> course_to_month_incompetions = new HashMap<>();


        // Iterate through each document
        for (JSONObject item : documents) {
            String training_status = item.get("client's-training-status").toString();

            // If the exit status is not the exiting the course before completion then move on
            if (!training_status.equals("Exited the course before completing"))
                continue;

            String course = item.get("course-code").toString();

            // Format: Month (ordered) : Incomplete Number
            Map<String, Integer> month_to_incompletions = course_to_month_incompetions.get(course);

            // If the map has not been initialized yet then inialize it
            if (month_to_incompletions == null) {
                // Used a linked hash map so that the order of the months stays the same
                month_to_incompletions = new LinkedHashMap<>();

                // Inject the months into the hash map
                for (String month : MONTHS) {
                    month_to_incompletions.put(month, 0);
                }
            }

            // Get the course exit month
            String exit_date_string = item.get("date-client-exited-course").toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date exit_date = null;
            try {
                exit_date = format.parse(exit_date_string);
            } catch (ParseException e) {
                // Skip if an error occurs
                continue;
            }
            SimpleDateFormat month_date = new SimpleDateFormat("MMM", Locale.ENGLISH);
            String month_name = month_date.format(exit_date);

            // Increment the counter for the given month
            month_to_incompletions.put(month_name, month_to_incompletions.get(month_name) + 1);

            // Intsert the sorted hash map back into the course to month map
            course_to_month_incompetions.put(course, month_to_incompletions);
        }

        // Construct the data
        for (Map.Entry<String, Map<String, Integer>> entry : course_to_month_incompetions.entrySet()) {
            List<Pair<String, Number>> points = new ArrayList<>();

            String course = entry.getKey();

            // Construct the data points
            for (String month : entry.getValue().keySet()) {
                Integer amount = entry.getValue().get(month);

                points.add(new Pair<String, Number>(month, (Number) amount));
            }

            // Construct the current series
            Series<Pair<String, Number>> curr_series = new Series<>(course, points);
            
            // Add to the return list
            ret_list.add(curr_series);
        }

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