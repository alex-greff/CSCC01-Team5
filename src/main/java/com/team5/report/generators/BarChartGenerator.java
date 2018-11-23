package com.team5.report.generators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.team5.report.data.Series;

import org.javatuples.Pair;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The generator for the bar chart report.
 */
public class BarChartGenerator extends Generator {
    // Data storage
    private static String targetPath, title;
    private static Pair<String, String> axisLabels;
    private static Pair<Double, Double> dimensions;
    private static List<Series<Pair<String, Number>>> data;

    @Override
    protected void displayChart(Stage stage) {
        stage.setTitle(title); // Set the title of the chart
        // Setup the axises and the chart
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> chart =  new BarChart<>(xAxis,yAxis);
        chart.setTitle(title); // Set the title of the chart

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

        // Display the chart
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
        BarChartGenerator.targetPath = targetPath;
        BarChartGenerator.title = title;
        BarChartGenerator.axisLabels = axisLabels;
        BarChartGenerator.dimensions = dimensions;
        BarChartGenerator.data = data;

        // Launch the chart
        launchChart();
    }

    /**
     * Launches the chart.
     */
    private void launchChart() {
        // If javafx hasn't been run yet
        if (isInitialized == false) {
            // Launch the chart
            launch(new String[0]);
        } else {
            // Display the chart from the javafx thread 
            Platform.runLater(() -> {
                displayChart();
            });
        }
    }
}