package com.team5.report.charts;

import com.team5.report.charts.NumberedChartReport;

import org.javatuples.Pair;

public abstract class LineChartReport extends NumberedChartReport {

    protected LineChartReport(String title, Pair<Double, Double> dimensions, String xAxisLabel, String yAxisLabel) {
        super(title, dimensions, xAxisLabel, yAxisLabel);
    }
}