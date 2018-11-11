package com.team5.report.charts;

import com.team5.report.charts.ChartReport;

import org.javatuples.Pair;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public abstract class PieChartReport extends ChartReport {

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

    // @Override
    // protected void makeChart(Stage stage, Scene scene) {
    //     Chart chart = getChart();
    //     chart.setTitle(getTitle());

    //     // Scene scene = new Scene(new Group());
    //     scene.setRoot(new Group());

    //     ((Group) scene.getRoot()).getChildren().add(chart);

    //     // return scene;
    // }
}