package com.team5.report.charts.implementations;

import com.team5.report.charts.ChartReport;

import org.javatuples.Pair;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public abstract class PieChartReport extends ChartReport {
    protected PieChartReport(String title, Pair<Double, Double> dimensions) {
        super(title, dimensions);
    }

    @Override
    protected void setupStage(Stage stage) {
        super.setupStage(stage);

        Pair<Double, Double> dimensions = this.getDimensions();

        stage.setWidth(dimensions.getValue0());
        stage.setHeight(dimensions.getValue1());
    }

    @Override
    protected Chart getChart() {
        return new PieChart(getData()); 
    }

    protected abstract ObservableList<PieChart.Data> getData();

    protected Scene makeChart(Stage stage) {
        Chart chart = getChart();
        chart.setTitle(getTitle());

        Scene scene = new Scene(new Group());

        ((Group) scene.getRoot()).getChildren().add(chart);

        return scene;
    }
}