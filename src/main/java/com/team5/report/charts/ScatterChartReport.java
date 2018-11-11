package com.team5.report.charts;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public abstract class ScatterChartReport extends NumberedChartReport {
    @Override
    protected XYChart<Number, Number> instantiateXYChart() {
        return new LineChart<>(getXAxis(), getYAxis());
    }
}