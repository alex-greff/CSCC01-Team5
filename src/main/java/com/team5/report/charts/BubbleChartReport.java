package com.team5.report.charts;

import javafx.scene.chart.Axis;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public abstract class BubbleChartReport extends TripletChartReport<Number, Number, Number> {
    private NumberAxis xAxis, yAxis;

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

    @Override
    protected XYChart<Number, Number> instantiateXYChart() {
        return new BubbleChart<>(getXAxis(), getYAxis());
    }
}