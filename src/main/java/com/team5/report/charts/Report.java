    package com.team5.report.charts;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public abstract class Report extends Application {

    protected String targetPath;


    public void generate(String targetPath) throws IOException {
        launch(new String[0]);
        this.targetPath = targetPath;
    }


    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = makeChart(stage);
        saveAsPng(scene, this.targetPath);
    }

    protected abstract Scene makeChart(Stage stage);

    protected void saveAsPng(Scene scene, String path) throws IOException {
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }


    
    // @Override
    // public void start(Stage stage) {
    //     // Scene scene = new Scene(new Group());
    //     // stage.setTitle("Imported Fruits");
    //     // stage.setWidth(500);
    //     // stage.setHeight(500);
 
    //     // ObservableList<PieChart.Data> pieChartData =
    //     //         FXCollections.observableArrayList(
    //     //         new PieChart.Data("Grapefruit", 13),
    //     //         new PieChart.Data("Oranges", 25),
    //     //         new PieChart.Data("Plums", 10),
    //     //         new PieChart.Data("Pears", 22),
    //     //         new PieChart.Data("Apples", 30));
    //     // final PieChart chart = new PieChart(pieChartData);
    //     // chart.setTitle("Imported Fruits");

    //     // ((Group) scene.getRoot()).getChildren().add(chart);
    //     // stage.setScene(scene);
    //     // stage.show();



    //     stage.setTitle("Line Chart Sample");
    //     //defining the axes
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


    //     // Saving code

    //     saveAsPng(scene, "testFiles/reportGenerator/chart.png"); // Does the same thing
    //     stage.setScene(scene);
    //     saveAsPng(scene, "testFiles/reportGenerator/chart1.png"); // Does the same thing

    //     // stage.show();
    //     System.out.println("After show");

    //     // Exit code
    //     Platform.exit();
    //     System.exit(0);
    // }
 
    // public static void main(String[] args) {
    //     // launch(args);
    //     // Report r = new Report();
    //     // r.generate("");
    // }
}