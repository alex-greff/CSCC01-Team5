package com.team5.report.charts;

import com.team5.report.charts.PairChartReport;

import org.javatuples.Pair;

import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public abstract class BarChartReport extends PairChartReport<String, Number> {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    @Override
    protected Axis<String> getXAxis() {
        return xAxis;
    }

    @Override
    protected Axis<Number> getYAxis() {
        return yAxis;
    }

    @Override
    protected XYChart<String, Number> instantiateXYChart() {
        return new BarChart<>(getXAxis(), getYAxis());
    }

    protected void setYAxisBounds(double lowerBound, double upperBound, double tickUnit) {
        this.yAxis.setLowerBound(lowerBound);
        this.yAxis.setUpperBound(upperBound);
        this.yAxis.setTickUnit(tickUnit);
    }
}