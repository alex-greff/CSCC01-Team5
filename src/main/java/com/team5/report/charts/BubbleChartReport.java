package com.team5.report.charts;

import com.team5.report.generators.BubbleChartGenerator;

/**
 * The abstract class for the bubble chart report type.
 */
public abstract class BubbleChartReport extends TripletChartReport<Number, Number, Number> {
    @Override
    public void generate(String targetPath) {
        // Generate the report with the associated generator
        BubbleChartGenerator generator = new BubbleChartGenerator();
        generator.generate(targetPath, getTitle(), getAxisLabels(), getDimensions(), getTripletData());
    }
}