package com.team5.report.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team5.database.DatabaseDriver;
import com.team5.report.charts.BarChartReport;
import com.team5.report.charts.Report;
import com.team5.report.data.Series;
import com.team5.utilities.JSONLoader;

import org.javatuples.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


public class ClientCourseEnrollmentReport extends BarChartReport {
    private String title;
    private Pair<Double, Double> dimensions;
    private Pair<String, String> axisLabels;

    public ClientCourseEnrollmentReport() {
        setupConfigs();
    }

    private void setupConfigs() {
        // try {
        //     // TODO: dont hardcode this shit
        //     JSONObject config = JSONLoader.parseJSONFile("data/reports/ClientCourseEnrollmentReport.json");
        //     title = (String) config.get("title");

        //     axisLabels = new Pair<String,String>((String) config.get("xAxisLabel"), (String) config.get("yAxisLabel"));

        //     // TODO: add dimensions to config
        //     dimensions = new Pair<Double,Double>(800.0, 600.0);

        // } catch (IOException | ParseException e) {
        //     e.printStackTrace();
        // }

        title = "Some title";
        axisLabels = new Pair<String,String>("My X axis label", "My Y axis label");
        dimensions = new Pair<Double,Double>(800.0, 800.0);
    }

    // @Override
    // protected List<Series<Pair<String, Number>>> getPairData() {
    //     // TODO: dont hardcode this shit
    //     DatabaseDriver db = new DatabaseDriver("mongodb://mo:ProficiousF18@ds031088.mlab.com:31088/icare_db", "icare_db", "lt_client_enrollment");

    //     List<JSONObject> queriedItems = db.queryDatabase();
        
    //     System.out.println(queriedItems); // TODO: remove

    //     List<Series<Pair<String, Number>>> ret_list = new ArrayList<>();

    //     Map<String, Integer> course_to_enrolled = new HashMap<>();

    //     for (JSONObject item : queriedItems) {
    //         String course = (String) item.get("course-code");

    //         if (course_to_enrolled.containsKey(course)) {
    //             int amt = course_to_enrolled.get(course);
    //             course_to_enrolled.put(course, amt + 1);
    //         } else {
    //             course_to_enrolled.put(course, 1);
    //         }
    //     }

    //     List<Pair<String, Number>> points = new ArrayList<>();

    //     for (Map.Entry<String, Integer> entry : course_to_enrolled.entrySet()) {
    //         points.add(new Pair<String,Number>(entry.getKey(), entry.getValue()));
    //     }

    //     Series<Pair<String, Number>> client_series = new Series<>("Clients", points);
    //     ret_list.add(client_series);

    //     return ret_list;
    // }

    // Testing method TODO: remove
    @Override
    protected List<Series<Pair<String, Number>>> getPairData() {
        List<Series<Pair<String, Number>>> ret = new ArrayList<>();

        List<Pair<String, Number>> temp = new ArrayList<>();
        temp.add(new Pair<String,Number>("category1", 7));
        temp.add(new Pair<String,Number>("category2", 3));
        temp.add(new Pair<String,Number>("category3", -5));

        ret.add(new Series<>("series1", temp));

        temp = new ArrayList<>();
        temp.add(new Pair<String,Number>("category1", 10));
        temp.add(new Pair<String,Number>("category2", 2));
        temp.add(new Pair<String,Number>("category3", 15));

        ret.add(new Series<>("series2", temp));

        return ret;
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
        return "TODO: some name here";
    }

    @Override
    public String getReportDescription() {
        return "TODO: some report description here";
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