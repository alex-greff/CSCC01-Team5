package com.team5.report.charts;

import java.util.List;

import com.team5.report.data.Series;

import org.javatuples.Pair;

public abstract class PieChartReport extends ChartReport {

    @Override
    public void generate(String targetPath) {
        
    }

    protected abstract List<Series<Pair<String, Double>>> getData();
}