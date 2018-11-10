package com.team5.report;

import java.io.IOException;

import com.team5.report.data.ReportData;
import com.team5.utilities.ConfigurationNotFoundException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.parser.ParseException;

/**
 * The abstract class for the report generator.
 */
public abstract class ReportGenerator {
    /**
     * The relative file path to the JSON template of the current report.
     */
    private String reportTemplateJSONPath;

    /**
     * Gets the report template JSON file path.
     * 
     * @return Returns the report template JSON file path.
     */
    protected String getReportTemplateJSONPath() {
        return reportTemplateJSONPath;
    }

    /**
     * Protected constructor for descendant classes to use for configuration
     * 
     * @param reportTemplateJSONPath The report template JSON file path.
     */
    protected ReportGenerator(String reportTemplateJSONPath) {
        this.reportTemplateJSONPath = reportTemplateJSONPath;
    }

    /**
     * Generates the report at the specified location.
     * 
     * @param targetPath The save location of the report file.
     * @throws ConfigurationNotFoundException Thrown if a configuration exception occurs.
     * @throws ParseException Thrown if a parse error occurs while parsing the JSON template.
     * @throws IOException Thrown if an IO expection occurs.
     * @throws InvalidFormatException
     */
    public void GenerateReport(String targetPath) throws ConfigurationNotFoundException, ParseException, IOException, InvalidFormatException {
        // Instantiate a new report exporter instance
        ReportExporter reportExporter = new ReportExporter();

        // Populate the data matrix
        ReportData<?> data = populateReportData();

        // Report the report with the generated data
        reportExporter.exportReport(reportTemplateJSONPath, targetPath, data);
    }

    /**
     * Populates a report object with data specific to the implemented report type.
     * 
     * @return Returns a ReportData object populated with data.
     */
    protected abstract ReportData<?> populateReportData();
}