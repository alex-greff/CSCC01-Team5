package com.team5.report.generators;

import java.util.List;

import org.javatuples.Pair;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

/**
 * The generator for the bar chart report.
 */
public class PieChartGenerator extends Generator {
    // Data storage
    private static String targetPath, title;
    private static Pair<Double, Double> dimensions;
    private static List<Pair<String, Double>> data;

    @Override
    public void displayChart(Stage stage) {
        stage.setTitle(title); // Set the title of the chart

        // Setup the dimensions of the chart
        stage.setWidth(dimensions.getValue0());
        stage.setHeight(dimensions.getValue1());;

        // Initialize the scene
        Scene scene = new Scene(new Group());

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Populate the pie chart data with the inputted data
        for (Pair<String, Double> item : data) {
            pieChartData.add(new PieChart.Data(item.getValue0(), item.getValue1()));
        }

        // Instantiate the pie chart
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(title); // Set the title of the chart 

        // Set the scene to display the pie chart
        ((Group) scene.getRoot()).getChildren().add(chart);

        // Show the scene
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
    public void generate(String targetPath, String title, Pair<Double, Double> dimensions, List<Pair<String, Double>> data) {
        // Setup the chart data
        PieChartGenerator.targetPath = targetPath;
        PieChartGenerator.title = title;
        PieChartGenerator.dimensions = dimensions;
        PieChartGenerator.data = data;

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