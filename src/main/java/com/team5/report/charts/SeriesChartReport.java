package com.team5.report.charts;

import org.javatuples.Pair;

/**
 * The abstract class the report types that utilize series.
 */
public abstract class SeriesChartReport<X, Y> extends ChartReport {
    /**
     * Gets the axis lables of the chart.
     * 
     * @return Returns the axis lables of the chart. Formatted (X-Axis Label, Y-Axis Label).
     */
    protected abstract Pair<String, String> getAxisLabels();
}