package com.team5.report.charts;

import org.javatuples.Pair;

public abstract class ChartReport extends Report {
    protected abstract String getTitle();
    protected abstract Pair<Double, Double> getDimensions();
}