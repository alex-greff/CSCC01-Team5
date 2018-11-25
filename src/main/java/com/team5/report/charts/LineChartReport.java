package com.team5.report.charts;

import com.team5.report.generators.LineChartGenerator;

/**
 * The abstract class for line chart type reports.
 */
public abstract class LineChartReport extends PairChartReport<String, Number> {
    @Override
    public void generate(String targetPath) {
        // Generate the report with the associated generator
        LineChartGenerator generator = new LineChartGenerator();
        generator.generate(targetPath, getTitle(), getAxisLabels(), getDimensions(), getPairData());
    }
}