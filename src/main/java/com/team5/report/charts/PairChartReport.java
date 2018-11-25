package com.team5.report.charts;

import com.team5.report.data.Series;

import java.util.List;

import org.javatuples.Pair;

/**
 * The abstract class for the chart report type with 2 data relationships.
 * 
 * @param <X> The type of the first data relationship.
 * @param <Y> The type of the second data relationship.
 */
public abstract class PairChartReport<X, Y> extends SeriesChartReport<X, Y> {
    /**
     * Gets the data for the pair report.
     * 
     * @return Returns the pair report data.
     */
    protected abstract List<Series<Pair<X, Y>>> getPairData();
}