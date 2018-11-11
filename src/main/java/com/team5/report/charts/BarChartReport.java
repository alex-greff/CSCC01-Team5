package com.team5.report.charts;

import com.team5.report.charts.PairChartReport;

import org.javatuples.Pair;

import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public abstract class BarChartReport extends PairChartReport<String, Number> {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    protected BarChartReport(String title, Pair<Double, Double> dimensions, String xAxisLabel, String yAxisLabel) {
        super(title, dimensions, xAxisLabel, yAxisLabel);
    }

    @Override
    protected Axis<String> getXAxis() {
        return xAxis;
    }

    @Override
    protected Axis<Number> getYAxis() {
        return yAxis;
    }

    protected void setYAxisBounds(double lowerBound, double upperBound, double tickUnit) {
        this.yAxis.setLowerBound(lowerBound);
        this.yAxis.setUpperBound(upperBound);
        this.yAxis.setTickUnit(tickUnit);
    }
}