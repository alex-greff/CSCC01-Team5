package com.team5.report.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import com.team5.report.data.Series;

import org.javatuples.Pair;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * The generator for the bar chart report.
 */
public class ScatterChartGenerator extends Generator {
    // Data storage
    private static String targetPath, title;
    private static Pair<String, String> axisLabels;
    private static Pair<Double, Double> dimensions;
    private static List<Series<Pair<String, Number>>> data;

    @Override
    public void displayChart(Stage stage) {
        stage.setTitle(title); // Set the title of the chart
        // Setup the axises and the chart
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final ScatterChart<String,Number> chart =  new ScatterChart<>(xAxis,yAxis);

        // Disable animations
        xAxis.setAnimated(false); 
        yAxis.setAnimated(false);

        chart.setTitle(title); // Set the title of the chart

        // Anchor the legend to the right
        chart.setLegendSide(Side.RIGHT);

        // Set the lables of the axises
        xAxis.setLabel(axisLabels.getValue0());       
        yAxis.setLabel(axisLabels.getValue1());

        // Initialize the series list container
        List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();

        // Populate the series list container with the generated information of the report
        for (Series<Pair<String, Number>> currDataSeries : data) {
            XYChart.Series<String, Number> chartSeries = new XYChart.Series<String, Number>();

            chartSeries.setName(currDataSeries.getName()); // Set the series name

            // Get and add each data entry
            for (Pair<String, Number> currDataEntry : currDataSeries.getContent()) {
                XYChart.Data<String, Number> chartDataEntry = new XYChart.Data<String, Number>(currDataEntry.getValue0(), currDataEntry.getValue1());
                
                chartSeries.getData().add(chartDataEntry);
            }

            seriesList.add(chartSeries); // Add the series to series list
        }
        
        // Initialize the scene
        Scene scene  = new Scene(chart, dimensions.getValue0(), dimensions.getValue1());

        // Add the data to the chart
        chart.getData().addAll(seriesList);

        // Display the scene
        stage.setScene(scene);
        stage.show();

        // Setup the save hook
        super.setupSaveHook(stage, scene, targetPath);
    }

    /**
     * Generates the report with the given information.
     * 
     * @param targetPath The save path of the report.
     * @param title The title of the report.
     * @param axisLabels The axis lables of the report.
     * @param dimensions The dimensions of the report.
     * @param data The data of the report.
     */
    public void generate(String targetPath, String title, Pair<String, String> axisLabels, Pair<Double, Double> dimensions, List<Series<Pair<String, Number>>> data) {
        // Setup the chart data
        ScatterChartGenerator.targetPath = targetPath;
        ScatterChartGenerator.title = title;
        ScatterChartGenerator.axisLabels = axisLabels;
        ScatterChartGenerator.dimensions = dimensions;
        ScatterChartGenerator.data = data;

        // Generate the chart
        super.generate();
    }

    @Override
    protected void launchSelf() {
        launch(new String[0]);
    }
}