package com.team5.report.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team5.database.DatabaseDriver;
import com.team5.report.charts.BarChartReport;
import com.team5.report.charts.Report;
import com.team5.report.charts.SeriesChartReport;
import com.team5.utilities.JSONLoader;

import org.javatuples.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class ClientCourseEnrollmentReport extends BarChartReport {
    private String title, xAxisLabel, yAxisLabel;
    private Pair<Double, Double> dimensions;

    public ClientCourseEnrollmentReport() {
        setupConfigs();
    }

    private void setupConfigs() {
        try {
            // TODO: dont hardcode this shit
            JSONObject config = JSONLoader.parseJSONFile("data/reports/ClientCourseEnrollmentReport.json");
            title = (String) config.get("title");
            xAxisLabel = (String) config.get("xAxisLabel");
            yAxisLabel = (String) config.get("yAxisLabel");
            // TODO: add dimensions to config
            dimensions = new Pair<Double,Double>(800.0, 600.0);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<SeriesChartReport<String, Number>.Series<Pair<String, Number>>> getPairData() {
        // TODO: dont hardcode this shit
        DatabaseDriver db = new DatabaseDriver("mongodb://mo:ProficiousF18@ds031088.mlab.com:31088/icare_db", "icare_db", "lt_client_enrollment");

        List<JSONObject> queriedItems = db.queryDatabase();
        
        System.out.println(queriedItems); // TODO: remove

        List<Series<Pair<String, Number>>> ret_list = new ArrayList<>();

        Map<String, Integer> course_to_enrolled = new HashMap<>();

        for (JSONObject item : queriedItems) {
            String course = (String) item.get("course-code");

            if (course_to_enrolled.containsKey(course)) {
                int amt = course_to_enrolled.get(course);
                course_to_enrolled.put(course, amt + 1);
            } else {
                course_to_enrolled.put(course, 1);
            }
        }

        List<Pair<String, Number>> points = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : course_to_enrolled.entrySet()) {
            points.add(new Pair<String,Number>(entry.getKey(), entry.getValue()));
        }

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
    protected String getXAxisLabel() {
        return xAxisLabel;
    }

    @Override
    protected String getYAxisLabel() {
        return yAxisLabel;
    }

    public static void main(String[] args) throws IOException {
        Report report = new ClientCourseEnrollmentReport();

        report.generate("testFiles/reportTests/ClientCourseEnrollmentReport.png");

        System.out.println("Done!");
    }

    // @Override
    // protected Chart getChart() {
    //     // defining the axes
    //     final NumberAxis xAxis = new NumberAxis();
    //     final NumberAxis yAxis = new NumberAxis();
    //     xAxis.setLabel("Number of Month");
    //     xAxis.setLabel("Number of Month");
    //     //creating the chart
    //     LineChart<Number, Number> lineChart =
    //             new LineChart<Number, Number>(xAxis, yAxis);
    //     lineChart.setTitle("Stock Monitoring, 2010");
    //     //defining a series
    //     XYChart.Series series = new XYChart.Series();
    //     series.setName("My portfolio");
    //     //populating the series with data
    //     series.getData().add(new XYChart.Data(1, 23));
    //     series.getData().add(new XYChart.Data(2, 14));
    //     series.getData().add(new XYChart.Data(3, 15));
    //     series.getData().add(new XYChart.Data(4, 24));
    //     series.getData().add(new XYChart.Data(5, 34));
    //     Scene scene = new Scene(lineChart, 800, 600);
    //     lineChart.setAnimated(false);
    //     lineChart.getData().add(series);

    //     return lineChart;
    // }
}