package com.team5.report.generators;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.javatuples.Pair;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

/**
 * The generator for the bar chart report.
 */
public class PieChartGenerator extends Application {
    // Data storage
    private static String targetPath, title;
    private static Pair<Double, Double> dimensions;
    private static List<Pair<String, Double>> data;

    @Override
    public void start(Stage stage) throws Exception {
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

        // Save the scene in the target path
        saveAsPng(scene, targetPath);

        // stage.close();
        // System.exit(0);
    }

    /**
     * Saves the generated report to the given target location.
     * 
     * @param scene The report.
     * @param path The save location.
     * @throws IOException Thrown if an IOException occurs.
     */
    protected void saveAsPng(Scene scene, String path) throws IOException {
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
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

        // Generate and save the chart
        launch(new String[0]);
    }
}