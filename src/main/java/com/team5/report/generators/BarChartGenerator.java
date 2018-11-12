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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public class BarChartGenerator extends Application {
    private static String targetPath, title;
    private static Pair<String, String> axisLabels;
    private static Pair<Double, Double> dimensions;
    private static List<Series<Pair<String, Number>>> data;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(title);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> chart =  new BarChart<>(xAxis,yAxis);
        chart.setTitle(title);

        // System.out.printf("%s\n", axisLabels);
        xAxis.setLabel(axisLabels.getValue0());       
        yAxis.setLabel(axisLabels.getValue1());

        List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();

        for (Series<Pair<String, Number>> currDataSeries : data) {
            XYChart.Series<String, Number> chartSeries = new XYChart.Series<String, Number>();

            chartSeries.setName(currDataSeries.getName());

            for (Pair<String, Number> currDataEntry : currDataSeries.getContent()) {
                XYChart.Data<String, Number> chartDataEntry = new XYChart.Data<String, Number>(currDataEntry.getValue0(), currDataEntry.getValue1());

                chartSeries.getData().add(chartDataEntry);
            }

            seriesList.add(chartSeries);
            // chart.getData().add(chartSeries);
        }
        
        Scene scene  = new Scene(chart, dimensions.getValue0(), dimensions.getValue1());

        chart.getData().addAll(seriesList);
        // chart.getData().addAll(seriesList.get(0), seriesList.get(1));

        // stage.setScene(scene);
        // stage.show();

        saveAsPng(scene, targetPath);

        stage.close();
        System.exit(0);
    }

    protected void saveAsPng(Scene scene, String path) throws IOException {
        WritableImage image = scene.snapshot(null);
        File file = new File(path);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }

    // final static String austria = "Austria";
    // final static String brazil = "Brazil";
    // final static String france = "France";
    // final static String italy = "Italy";
    // final static String usa = "USA";

    // @Override public void start(Stage stage) {
    //     stage.setTitle("Bar Chart Sample");
    //     final CategoryAxis xAxis = new CategoryAxis();
    //     final NumberAxis yAxis = new NumberAxis();
    //     final BarChart<String,Number> bc = 
    //         new BarChart<>(xAxis,yAxis);
    //     bc.setTitle("Country Summary");
    //     xAxis.setLabel("Country");       
    //     yAxis.setLabel("Value");
 
    //     XYChart.Series series1 = new XYChart.Series();
    //     series1.setName("2003");       
    //     series1.getData().add(new XYChart.Data(austria, 25601.34));
    //     series1.getData().add(new XYChart.Data(brazil, 20148.82));
    //     series1.getData().add(new XYChart.Data(france, 10000));
    //     series1.getData().add(new XYChart.Data(italy, 35407.15));
    //     series1.getData().add(new XYChart.Data(usa, 12000));      
        
    //     XYChart.Series series2 = new XYChart.Series();
    //     series2.setName("2004");
    //     series2.getData().add(new XYChart.Data(austria, 57401.85));
    //     series2.getData().add(new XYChart.Data(brazil, 41941.19));
    //     series2.getData().add(new XYChart.Data(france, 45263.37));
    //     series2.getData().add(new XYChart.Data(italy, 117320.16));
    //     series2.getData().add(new XYChart.Data(usa, 14845.27));  
        
    //     XYChart.Series series3 = new XYChart.Series();
    //     series3.setName("2005");
    //     series3.getData().add(new XYChart.Data(austria, 45000.65));
    //     series3.getData().add(new XYChart.Data(brazil, 44835.76));
    //     series3.getData().add(new XYChart.Data(france, 18722.18));
    //     series3.getData().add(new XYChart.Data(italy, 17557.31));
    //     series3.getData().add(new XYChart.Data(usa, 92633.68));  
        
    //     Scene scene  = new Scene(bc,800,600);
    //     bc.getData().addAll(series1, series2, series3);
    //     stage.setScene(scene);
    //     stage.show();
    // }

    public void generate(String targetPath, String title, Pair<String, String> axisLabels, Pair<Double, Double> dimensions, List<Series<Pair<String, Number>>> data) {
        BarChartGenerator.targetPath = targetPath;
        BarChartGenerator.title = title;
        BarChartGenerator.axisLabels = axisLabels;
        BarChartGenerator.dimensions = dimensions;
        BarChartGenerator.data = data;

        // System.out.printf("%s, %s, %s\n", this.title, this.axisLabels.toString(), this.dimensions.toString());

        launch(new String[0]);
    }
}