package com.team5.report.generators;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.team5.report.data.Series;

import org.javatuples.Pair;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

/**
 * The generator for the bar chart report.
 */
public class ScatterChartGenerator extends Application {
    // Data storage
    private static String targetPath, title;
    private static Pair<String, String> axisLabels;
    private static Pair<Double, Double> dimensions;
    private static List<Series<Pair<Number, Number>>> data;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(title); // Set the title of the chart
        // Setup the axises and the chart
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final ScatterChart<Number,Number> chart =  new ScatterChart<>(xAxis,yAxis);

        // Disable animations
        xAxis.setAnimated(false); 
        yAxis.setAnimated(false);

        chart.setTitle(title); // Set the title of the chart

        // Set the lables of the axises
        xAxis.setLabel(axisLabels.getValue0());       
        yAxis.setLabel(axisLabels.getValue1());

        // Initialize the series list container
        List<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();

        // Populate the series list container with the generated information of the report
        for (Series<Pair<Number, Number>> currDataSeries : data) {
            XYChart.Series<Number, Number> chartSeries = new XYChart.Series<Number, Number>();

            chartSeries.setName(currDataSeries.getName()); // Set the series name

            // Get and add each data entry
            for (Pair<Number, Number> currDataEntry : currDataSeries.getContent()) {
                XYChart.Data<Number, Number> chartDataEntry = new XYChart.Data<Number, Number>(currDataEntry.getValue0(), currDataEntry.getValue1());
                
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
    public void generate(String targetPath, String title, Pair<String, String> axisLabels, Pair<Double, Double> dimensions, List<Series<Pair<Number, Number>>> data) {
        // Setup the chart data
        ScatterChartGenerator.targetPath = targetPath;
        ScatterChartGenerator.title = title;
        ScatterChartGenerator.axisLabels = axisLabels;
        ScatterChartGenerator.dimensions = dimensions;
        ScatterChartGenerator.data = data;

        // Generate and save the chart
        launch(new String[0]);
    }
}