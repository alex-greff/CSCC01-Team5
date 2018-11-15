package com.team5.report.charts;

/**
 * The abstract base class for reports
 */
public abstract class Report {
    /**
     * Generates the report file in the given path location.
     * 
     * @param targetPath The target save location.
     */
    public abstract void generate(String targetPath);
}