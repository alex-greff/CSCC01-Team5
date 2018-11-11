package com.team5.report.implementations;

import java.util.List;

import com.team5.report.charts.BarChartReport;
import com.team5.report.charts.SeriesChartReport;

import org.javatuples.Pair;

import javafx.scene.chart.Axis;

public class ClientCourseEnrollmentReport extends BarChartReport {
    private String title, xAxisLabel, yAxisLabel;
    private Pair<Double, Double> dimensions;

    public ClientCourseEnrollmentReport() {
        setupConfigs();
    }

    private void setupConfigs() {

    }

    @Override
    protected List<SeriesChartReport<String, Number>.Series<Pair<String, Number>>> getPairData() {
        

        return null;
    }

    @Override
    protected Pair<Double, Double> getDimensions() {
        return dimensions;
    }

    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    protected String getXAxisLabel() {
        return xAxisLabel;
    }

    @Override
    protected String getYAxisLabel() {
        return yAxisLabel;
    }
}