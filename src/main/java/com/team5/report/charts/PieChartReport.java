package com.team5.report.charts;

import java.util.List;

import com.team5.report.generators.PieChartGenerator;

import org.javatuples.Pair;

/**
 * The abstract class for pie chart type reports.
 */
public abstract class PieChartReport extends ChartReport {

    @Override
    public void generate(String targetPath) {
        // Generate the report with the associated generator
        PieChartGenerator generator = new PieChartGenerator();
        generator.generate(targetPath, getTitle(), getDimensions(), getPieChartData());
    }

    /**
     * Gets the data for the pie chart.
     * 
     * @return Returns the data of the pie chart.
     */
    protected abstract List<Pair<String, Double>> getPieChartData();
}