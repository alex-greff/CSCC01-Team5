package com.team5.report.charts;

import java.util.ArrayList;
import java.util.List;

import com.team5.report.data.Series;

import org.javatuples.Pair;

public class MockBarChartReport extends BarChartReport {
    @Override
    protected List<Series<Pair<String, Number>>> getPairData() {
        List<Series<Pair<String, Number>>> ret = new ArrayList<>();

        List<Pair<String, Number>> temp = new ArrayList<>();
        temp.add(new Pair<String,Number>("category1", 7));
        temp.add(new Pair<String,Number>("category2", 3));
        temp.add(new Pair<String,Number>("category3", -5));

        ret.add(new Series<>("series1", temp));

        temp = new ArrayList<>();
        temp.add(new Pair<String,Number>("category1", 10));
        temp.add(new Pair<String,Number>("category2", 2));
        temp.add(new Pair<String,Number>("category3", 15));

        ret.add(new Series<>("series2", temp));

        return ret;
    }

    @Override
    protected Pair<String, String> getAxisLabels() {
        return new Pair<String,String>("xAxisLabel", "yAxisLabel");
    }

    @Override
    protected Pair<Double, Double> getDimensions() {
        return new Pair<Double,Double>(500.0, 600.0);
    }

    @Override
    public String getReportDescription() {
        return "My mock bar chart report description";
    }

    @Override
    public String getReportName() {
        return "Mock Bar Chart Report";
    }

    @Override
    protected String getTitle() {
        return "Mock Title";
    }
}