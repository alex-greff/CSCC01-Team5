package com.team5.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import com.team5.utilities.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;

public class ReportGenerator {
    private String ROOT_DIR = "";

    public ReportGenerator() {
        getConfigs();
    }

    public void generateReport(String reportTemplatePath, ReportData<?> data, String targetPath) 
        throws org.json.simple.parser.ParseException, FileNotFoundException, IOException, NotFileException, InvalidFormatException {
        
        JSONObject reportTemplate = JSONLoader.parseJSONFile(ROOT_DIR + "/" + reportTemplatePath);

        initializeReportFile(reportTemplate, targetPath);

        populateReportFile(reportTemplate, data, targetPath);
    }

    private void getConfigs() {
        try {
            // Load the needed configs
            JSONObject config = ConfigurationLoader.loadConfiguration("report-template-system");
            ROOT_DIR = (String) config.get("root-template-directory");
        } catch (ConfigurationNotFoundException e) {
            // Do nothing
        }
    }

    private void initializeReportFile(JSONObject reportTemplate, String targetPath) throws NotFileException, IOException, SecurityException {
        String templatePath = (String)reportTemplate.get("source-file");
        File templateFile = new File(ROOT_DIR + "/" + templatePath);
        File targetFile = new File(ROOT_DIR + "/" + targetPath);

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

    private void populateReportFile(JSONObject reportTemplate, ReportData<?> data, String targetPath) throws FileNotFoundException, IOException, InvalidFormatException {
        // Load configs from template
        String dataSheet = (String) reportTemplate.get("data-sheet");
        JSONObject dataLocations = (JSONObject) reportTemplate.get("data-locations");
        String chartTitleLocation = (String) dataLocations.get("chart-title");
        String verticalAxisTitleLocation = (String) dataLocations.get("vertical-axis-title");
        String horizontalAxisTitleLocation = (String) dataLocations.get("horizontal-axis-title");
        String seriesTitlesStartLocation = (String) dataLocations.get("series-start");
        String blocksTitlesStartLocation = (String) dataLocations.get("blocks-start");
        String cellsStartLocation = (String) dataLocations.get("cells-start");

        // Get data sheet
        FileInputStream targetFile_is = new FileInputStream(new File(ROOT_DIR + targetPath));
        Workbook workbook = WorkbookFactory.create(targetFile_is);
        Sheet sheet = workbook.getSheet(dataSheet);
        
        // Clear data sheet
        Iterator<Row> rowIter = sheet.iterator();
        while (rowIter.hasNext()) {
            rowIter.next();
            rowIter.remove();;
        }

        // Add data to worksheet

        // Add chart title
        addToCell(sheet, chartTitleLocation, data.getTitle());
        // Add vertical axis title 
        addToCell(sheet, verticalAxisTitleLocation, data.getRowAxisLabel());
        // Add horizontal axis title
        addToCell(sheet, horizontalAxisTitleLocation, data.getColumnAxisLabel());

        int col_num, row_num;

        // Add row title names
        int[] rowTitleStartCoords = SpreadsheetHelpers.convertCellCoordsNumberedArray(seriesTitlesStartLocation);
        col_num = rowTitleStartCoords[1];

        for (int i = 0; i < data.getRowSize(); i++) {
            row_num = rowTitleStartCoords[0] + i;
            int[] coords = { row_num, col_num };
            String row_name = data.getRowName(i);

            addToCell(sheet, coords, row_name);
        }

        // Add column title names
        int[] colTitleStartCoords = SpreadsheetHelpers.convertCellCoordsNumberedArray(blocksTitlesStartLocation);
        row_num = colTitleStartCoords[0];

        for (int i = 0; i < data.getColumnSize(); i++) {
            col_num = colTitleStartCoords[1] + i;
            int[] coords = { row_num, col_num };
            String col_name = data.getColumnName(i);

            addToCell(sheet, coords, col_name);
        }

        // Add all the data
        int[] dataStartCoords = SpreadsheetHelpers.convertCellCoordsNumberedArray(cellsStartLocation);

        for (int x = 0; x < data.getColumnSize(); x++) {
            for (int y = 0; y < data.getRowSize(); y++) {
                row_num = dataStartCoords[0] + y;
                col_num = dataStartCoords[1] + x;
                int[] coords = { row_num, col_num };

                Object cellContent = data.getCell(row_num, col_num);
                addToCell(sheet, coords, cellContent);
            }
        }

        // Clean up and close file and workbook streams
        targetFile_is.close();
        FileOutputStream targetFile_os = new FileOutputStream(ROOT_DIR + targetPath);
        workbook.write(targetFile_os);
        targetFile_os.close();
    }

    private void addToCell(Sheet sheet, String location, Object content) {
        int[] num_location = SpreadsheetHelpers.convertCellCoordsNumberedArray(location);
        addToCell(sheet, num_location, content);
    }

    private void addToCell(Sheet sheet, int[] location, Object content) {
        int rowNum = location[0], colNum = location[1];

        Row curr_row = (sheet.getRow(rowNum) != null) ? sheet.getRow(rowNum) : sheet.createRow(rowNum);

        Cell curr_cell = (curr_row.getCell(colNum) != null) ? curr_row.getCell(colNum) : curr_row.createCell(colNum);

        if (content instanceof String)
            curr_cell.setCellValue((String) content);
        if (content instanceof Number)
            curr_cell.setCellValue((Double) content);
    }


    public static void main(String[] args) {
        //System.out.println(convertUpperCaseLetterAlphabetRanking('Z'));
        int[] ret = SpreadsheetHelpers.convertCellCoordsNumberedArray("A80");

        System.out.println(ret[0] + ", " + ret[1]);
    }
}