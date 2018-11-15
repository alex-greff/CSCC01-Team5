package com.team5.report.charts;

import com.team5.report.generators.BarChartGenerator;

/**
 * The abstract class for bar chart type reports.
 */
public abstract class BarChartReport extends PairChartReport<String, Number> {
    @Override
    public void generate(String targetPath) {
        // Generate the report with the associated generator
        BarChartGenerator generator = new BarChartGenerator();
        generator.generate(targetPath, getTitle(), getAxisLabels(), getDimensions(), getPairData());
    }
}