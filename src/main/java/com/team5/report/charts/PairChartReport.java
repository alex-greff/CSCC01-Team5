package com.team5.report.charts;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public abstract class PairChartReport<X, Y> extends SeriesChartReport<X, Y> {
    @Override
    protected void setupStage(Stage stage) {
        super.setupStage(stage);
    }

    @Override
    protected XYChart<X,Y> getXYChart() {
        XYChart<X, Y> chart = getXYChart();
        List<Series<Pair<X,Y>>> data = getPairData();

        List<XYChart.Series<X,Y>> seriesList = new ArrayList<>();

        for (Series<Pair<X,Y>> currDataSeries : data) {
            XYChart.Series<X,Y> chartSeries = new XYChart.Series<X,Y>();

            chartSeries.setName(currDataSeries.getName());

            for (Pair<X,Y> currDataEntry : currDataSeries.getContent()) {
                XYChart.Data<X,Y> chartDataEntry = new XYChart.Data<X,Y>(currDataEntry.getValue0(), currDataEntry.getValue1());

                chartSeries.getData().add(chartDataEntry);
            }

            seriesList.add(chartSeries);
        }
        
        chart.getData().addAll(seriesList);

        return chart;
    }

    protected abstract List<Series<Pair<X,Y>>> getPairData();
}