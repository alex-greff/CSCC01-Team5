package com.team5.report.charts.implementations;

import com.team5.report.charts.NumberedChartReport;

import org.javatuples.Pair;

public abstract class AreaChartReport extends NumberedChartReport {

    protected AreaChartReport(String title, Pair<Double, Double> dimensions, String xAxisLabel, String yAxisLabel) {
        super(title, dimensions, xAxisLabel, yAxisLabel);
    }
}