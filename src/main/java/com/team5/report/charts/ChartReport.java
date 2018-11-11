package com.team5.report.charts;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public abstract class ChartReport extends Report {
    protected abstract String getTitle();
    protected abstract Pair<Double, Double> getDimensions();

    protected void setupStage(Stage stage) {
        stage.setTitle(this.getTitle());

        // chart.setTitle(this.title);
        getChart().setTitle(this.getTitle());
    } 

    protected abstract Chart getChart();
}