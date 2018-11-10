package com.team5.report;

import javafx.scene.chart.XYChart;

public abstract class XYChartReport<X, Y> extends Report {
    XYChart<X, Y> chartReference;

    protected XYChartReport(XYChart<X, Y> chartReference) {
        this.chartReference = chartReference;
    }

}