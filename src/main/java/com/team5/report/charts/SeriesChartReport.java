package com.team5.report.charts;

import org.javatuples.Pair;

public abstract class SeriesChartReport<X, Y> extends ChartReport {
    protected abstract Pair<String, String> getAxisLabels();
}