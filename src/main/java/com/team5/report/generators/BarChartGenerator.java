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
public class BarChartGenerator extends Application {
    // Data storage
    private static String targetPath, title;
    private static Pair<String, String> axisLabels;
    private static Pair<Double, Double> dimensions;
    private static List<Series<Pair<String, Number>>> data;

    private static boolean firstTimeInstantiated = false;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        mainStage = stage;

        firstTimeInstantiated = true;

        displayChart(stage);
    }

    private static void displayChart() {
        System.out.println("Got here");

        // Label secondLabel = new Label("I'm a Label on new Window");
 
        // StackPane secondaryLayout = new StackPane();
        // secondaryLayout.getChildren().add(secondLabel);

        // Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // // New window (Stage)
        // Stage newWindow = new Stage();
        // newWindow.setTitle("Second Stage");
        // newWindow.setScene(secondScene);
        

        // newWindow.show();

        // Stage stage = new Stage();

        displayChart(mainStage);
    }

    private static void displayChart(Stage stage) {
        System.out.println("Got here");

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

        chart.setAnimated(false);
        yAxis.setAnimated(false);
        xAxis.setAnimated(false);
        chart.setStyle("-fx-open-tab-animation: NONE; -fx-close-tab-animation: NONE;");

        // System.out.println("Some shit");
        System.out.println(chart.getStyle());

        // scene.getStylesheets().add("chartStyles.css");

        // style = chart.getStyle();

        

        // Display the scene
        stage.setScene(scene);
        stage.show();
        
        // Save the scene in the target path
        stage.setOnCloseRequest(e -> {
			try {
				saveAsPng(scene, targetPath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
            }
            
            Platform.exit();
            System.exit(0);
		});

        // Save the scene in the target path
        // saveAsPng(scene, targetPath);

        // stage.close();
        // System.exit(0);
    }

    // @Override
    // public void stop() {
    //     System.out.println("Stage is closing");
    // }

    /**
     * Saves the generated report to the given target location.
     * 
     * @param scene The report.
     * @param path The save location.
     * @throws IOException Thrown if an IOException occurs.
     */
    protected static void saveAsPng(Scene scene, String path) throws IOException, InterruptedException {
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
    public void generate(String targetPath, String title, Pair<String, String> axisLabels, Pair<Double, Double> dimensions, List<Series<Pair<String, Number>>> data) {
        // Setup the chart data
        BarChartGenerator.targetPath = targetPath;
        BarChartGenerator.title = title;
        BarChartGenerator.axisLabels = axisLabels;
        BarChartGenerator.dimensions = dimensions;
        BarChartGenerator.data = data;

        // Generate and save the chart
        //if (firstTimeInstantiated == false) {
            launch(new String[0]);
        //} else {
            // System.out.println("Trying to run");

            // Platform.runLater(() -> {
            //     BarChartGenerator.displayChart();
            // });

            // Platform.runLater(new Runnable(){

            //     @Override
            //     public void run() {
            //         BarChartGenerator.displayChart(BarChartGenerator.instantiated_stage);
            //     }
            // });
        // }
    }
}