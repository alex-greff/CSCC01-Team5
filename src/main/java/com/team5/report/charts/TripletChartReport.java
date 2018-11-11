package com.team5.report.charts;

import java.util.List;

import com.team5.report.data.Series;

import org.javatuples.Triplet;

public abstract class TripletChartReport<X, Y, Z> extends SeriesChartReport<X, Y> {
    protected abstract List<Series<Triplet<X, Y, Z>>> getTripletData();
}