package com.team5.report.charts;

import java.util.List;

import com.team5.report.data.Series;

import org.javatuples.Triplet;

/**
 * The abstract class for the chart report type with 3 data relationships.
 * 
 * @param <X> The type of the first data relationship.
 * @param <Y> The type of the second data relationship.
 * @param <Z> The type of the third data relationship.
 */
public abstract class TripletChartReport<X, Y, Z> extends SeriesChartReport<X, Y> {
    /**
     * Gets the data for the triplet report.
     * 
     * @return Returns the data of the triplet report.
     */
    protected abstract List<Series<Triplet<X, Y, Z>>> getTripletData();
}