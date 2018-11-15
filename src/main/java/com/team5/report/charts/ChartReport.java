package com.team5.report.charts;

import org.javatuples.Pair;

/**
 * The abstract class for chart-based reports.
 */
public abstract class ChartReport extends Report {
    /**
     * Gets the title of the report.
     * 
     * @return Returns the title of the report.
     */
    protected abstract String getTitle();

    /**
     * Gets the dimentions of the report chart.
     * 
     * @return Returns the dimensions of the report chart. Formatted as (width, height).
     */
    protected abstract Pair<Double, Double> getDimensions();
}