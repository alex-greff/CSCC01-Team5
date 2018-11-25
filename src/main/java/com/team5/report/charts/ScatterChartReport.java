package com.team5.report.charts;

import com.team5.report.generators.ScatterChartGenerator;

/**
 * The abstract class for scatter chart type reports.
 */
public abstract class ScatterChartReport extends MixedChartReport {
    @Override
    public void generate(String targetPath) {
        // Generate the report with the associated generator
        ScatterChartGenerator generator = new ScatterChartGenerator();
        generator.generate(targetPath, getTitle(), getAxisLabels(), getDimensions(), getPairData());
    }
}