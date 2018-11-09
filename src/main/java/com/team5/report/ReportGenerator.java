package com.team5.report;

import java.io.IOException;

import com.team5.utilities.ConfigurationNotFoundException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.parser.ParseException;

public abstract class ReportGenerator {
    private String reportTemplateJSONPath;

    protected ReportGenerator(String reportTemplateJSONPath) {
        this.reportTemplateJSONPath = reportTemplateJSONPath;
    }

    public void GenerateReport(String targetPath) throws ConfigurationNotFoundException, ParseException, IOException, InvalidFormatException {
        ReportExporter reportExporter = new ReportExporter();

        ReportData<?> data = populateReportData();

        reportExporter.exportReport(reportTemplateJSONPath, targetPath, data);
    }

    protected abstract ReportData<?> populateReportData();
}