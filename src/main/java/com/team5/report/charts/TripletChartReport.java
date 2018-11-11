package com.team5.report.charts;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public abstract class TripletChartReport<X, Y, Z> extends SeriesChartReport<X, Y> {
    @Override
    protected XYChart<X,Y> getXYChart() {
        XYChart<X, Y> chart = instantiateXYChart();
        List<Series<Triplet<X, Y, Z>>> data = getTripletData();

        List<XYChart.Series<X,Y>> seriesList = new ArrayList<>();

        for (Series<Triplet<X, Y, Z>> currDataSeries : data) {
            XYChart.Series<X,Y> chartSeries = new XYChart.Series<X,Y>();

            chartSeries.setName(currDataSeries.getName());

            for (Triplet<X, Y, Z> currDataEntry : currDataSeries.getContent()) {
                XYChart.Data<X,Y> chartDataEntry = new XYChart.Data<X,Y>(currDataEntry.getValue0(), currDataEntry.getValue1(), currDataEntry.getValue2());

                chartSeries.getData().add(chartDataEntry);
            }

            seriesList.add(chartSeries);
        }
        
        chart.getData().addAll(seriesList);

        return chart;
    }

    protected abstract List<Series<Triplet<X, Y, Z>>> getTripletData();

    protected abstract XYChart<X, Y> instantiateXYChart();
}