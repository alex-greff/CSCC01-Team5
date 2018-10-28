package com.team5.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.team5.utilities.*;

import org.json.simple.JSONObject;

public class ReportGenerator {
    private String rootDir = "";

    public ReportGenerator() {
        getConfigs();
    }

    public void generateReport(String reportTemplatePath, ReportData<?> data, String targetPath) 
        throws org.json.simple.parser.ParseException, FileNotFoundException, IOException, NotFileException {
        
        JSONObject reportTemplate = JSONLoader.parseJSONFile(rootDir + "/" + reportTemplatePath);

        initializeReportFile(reportTemplate, targetPath);

        populateReportFile(data, targetPath);
    }

    private void getConfigs() {
        try {
            // Load the needed configs
            JSONObject config = ConfigurationLoader.loadConfiguration("report-template-system");
            rootDir = (String) config.get("root-template-directory");
        } catch (ConfigurationNotFoundException e) {
            // Do nothing
        }
    }

    private void initializeReportFile(JSONObject reportTemplate, String targetPath) throws NotFileException, IOException, SecurityException {
        String templatePath = (String)reportTemplate.get("source-file");
        File templateFile = new File(rootDir + "/" + templatePath);
        File targetFile = new File(rootDir + "/" + targetPath);

        // Checks
        if (!templateFile.exists())
            throw new FileNotFoundException("Template file '" + templatePath + " does not exist.");
        if (templateFile.isDirectory())
            throw new NotFileException("Template file '" + templatePath + " is not a file.");
        if (targetFile.isDirectory())
            throw new NotFileException("Target file '" + targetPath + "' is not a file.");

        // If the file does not exist
        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdirs(); // Make sure its parent director(ies) are created
            targetFile.createNewFile(); // Create the file
        }

        // Copy the template file to the target file
        copyFiles(templateFile, targetFile);
    }

    private void copyFiles(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            // Setup streams and buffer
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            
            // Copy the file over
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            // Close Streams
            is.close();
            os.close();
        }
    }

    private void populateReportFile(ReportData<?> data, String targetPath) {
        // All the POI stuff here
    }
}