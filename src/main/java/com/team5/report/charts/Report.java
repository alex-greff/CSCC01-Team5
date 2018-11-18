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

    /**
     * Gets the name of the report.
     * 
     * @return Returns a string with the report's name.
     */
    public abstract String getReportName();

    /**
     * Gets a short description of the report.
     * 
     * @return Returns a string with the description.
     */
    public abstract String getReportDescription();
}