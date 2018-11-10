package com.team5.report.charts.implementations;

import com.team5.report.charts.PairChartReport;
import com.team5.report.charts.TripletChartReport;

import org.javatuples.Pair;

import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;

public abstract class BubbleChartReport extends TripletChartReport<Number, Number, Number> {
    private NumberAxis xAxis, yAxis;

    protected BubbleChartReport(String title, Pair<Double, Double> dimensions, String xAxisLabel, String yAxisLabel) {
        super(title, dimensions, xAxisLabel, yAxisLabel);
    }

    @Override
    protected Axis<Number> getXAxis() {
        return xAxis;
    }

    @Override
    protected Axis<Number> getYAxis() {
        return yAxis;
    }

    protected void setXAxisBounds(double lowerBound, double upperBound, double tickUnit) {
        this.xAxis.setLowerBound(lowerBound);
        this.xAxis.setUpperBound(upperBound);
        this.xAxis.setTickUnit(tickUnit);
    }

    protected void setYAxisBounds(double lowerBound, double upperBound, double tickUnit) {
        this.yAxis.setLowerBound(lowerBound);
        this.yAxis.setUpperBound(upperBound);
        this.yAxis.setTickUnit(tickUnit);
    }
}