package com.team5.report.charts;

import com.team5.report.charts.NumberedChartReport;

import org.javatuples.Pair;

public abstract class ScatterChartReport extends NumberedChartReport {

    protected ScatterChartReport(String title, Pair<Double, Double> dimensions, String xAxisLabel, String yAxisLabel) {
        super(title, dimensions, xAxisLabel, yAxisLabel);
    }
}