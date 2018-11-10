package com.team5.report.charts;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public abstract class ChartReport extends Report {

    // protected XYChartReport(XYChart<X, Y> chartReference) {
    //     this.chartReference = chartReference;
    // }

    // protected Scene makeChart(Stage stage) {
    //     List<Pair<X,Y>> data = getData();

    //     for (Pair<X,Y> series : data) {

    //     }

    //     return null;
    // }

    private String title;
    private Pair<Double, Double> dimensions;

    protected ChartReport(String title, Pair<Double, Double> dimensions) {
        this.title = title;
        this.dimensions = dimensions;
    }

    protected void setupStage(Stage stage) {
        stage.setTitle(this.title);

        // chart.setTitle(this.title);
        getChart().setTitle(this.title);
    } 

    protected abstract Chart getChart();

    protected String getTitle() {
        return this.title;
    }

    protected Pair<Double, Double> getDimensions() {
        return new Pair<Double, Double>(this.dimensions.getValue0(), this.dimensions.getValue1());
    }
}