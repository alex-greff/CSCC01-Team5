package com.team5.report.charts;

import com.team5.report.generators.BarChartGenerator;

public abstract class BarChartReport extends PairChartReport<String, Number> {

    @Override
    public void generate(String targetPath) {
        BarChartGenerator generator = new BarChartGenerator();

        generator.generate(targetPath, getTitle(), getAxisLabels(), getDimensions(), getPairData());
    }
}