package com.team5.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import com.team5.report.data.ReportData;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.utilities.JSONLoader;
import com.team5.utilities.SpreadsheetHelpers;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Exports reports based of the report template system.
 */
public class ReportExporter {
    /**
     * The root directory for the templates.
     */
    private String TEMPLATE_ROOT_DIR = "";

    /**
     * Constructs a ReportExporter object.
     * 
     * @throws ConfigurationNotFoundException Thrown the configuration for the report system is missing.
     */
    public ReportExporter() throws ConfigurationNotFoundException {
        getConfigs();
    }

    /**
     * Export a report from the given report template and data object.
     * 
     * @param reportTemplateJSONPath The report template JSON file.
     * @param targetPath The target path of the report file.
     * @param data The data for the report.
     * @throws ParseException Thrown if a parse exception occurs while parsing the template JSON.
     * @throws IOException Occurs if an IOException occurs (most commonly a non-existent file).
     * @throws InvalidFormatException Thrown if an invalid format is encountered in a report excel file.
     */
    public void exportReport(String reportTemplateJSONPath, String targetPath, ReportData<?> data) 
        throws ParseException, IOException, InvalidFormatException {
        
        // Get the report template JSON
        JSONObject reportTemplateJSON = JSONLoader.parseJSONFile(TEMPLATE_ROOT_DIR + "/" + reportTemplateJSONPath);

        // Initialize the report file
        initializeReportFile(reportTemplateJSON, targetPath);

        // Populate the report file
        populateReportFile(reportTemplateJSON, data, targetPath);
    }

    /**
     * Gets the configurations for the report generator.
     */
    private void getConfigs() throws ConfigurationNotFoundException {
        // Load the needed configs
        JSONObject config = ConfigurationLoader.loadConfiguration("report-template-system");
        TEMPLATE_ROOT_DIR = (String) config.get("root-template-directory"); // Usually "data/templates/report-templates"
    }

    /**
     * Initializes the report file by creating and/or copying the template report file into the target file.
     * 
     * @param reportTemplate The report template.
     * @param targetPath The target path of the report file.
     * @throws IOException Thrown if an IO exception occurs.
     */
    private void initializeReportFile(JSONObject reportTemplate, String targetPath) throws IOException {
        String correspondingTemplatePath = (String)reportTemplate.get("source-file");

        String templateFilePath = TEMPLATE_ROOT_DIR + "/" + correspondingTemplatePath;
        String targetFilePath = targetPath;

        File templateFile = new File(templateFilePath);
        File targetFile = new File(targetFilePath);

        // Copy the files
        FileUtils.copyFile(templateFile, targetFile, false);
    }

    
    /**
     * Populates the report file with the given data.
     * 
     * @param reportTemplate The report template.
     * @param data The data object.
     * @param targetPath The report file path.
     * @throws FileNotFoundException Thrown if the report file is not found.
     * @throws IOException Thrown if an IO exception occurs.
     * @throws InvalidFormatException Thrown if an invalid format is encountered in a report excel file.
     */
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
        FileInputStream targetFile_is = new FileInputStream(new File(targetPath));
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
        rowTitleStartCoords = new int[] { rowTitleStartCoords[0] - 1, rowTitleStartCoords[1] - 1 }; // Convert to zero-based counting

        col_num = rowTitleStartCoords[1];
        for (int i = 0; i < data.getRowSize(); i++) {
            // Get the current row name
            String row_name = data.getRowName(i);

            // Get the coords of the cell in the worksheet
            row_num = rowTitleStartCoords[0] + i;
            int[] coords = { row_num, col_num };
            
            // Add the row name to the cell in the worksheet
            addToCell(sheet, coords, row_name);
        }

        // Add column title names
        int[] colTitleStartCoords = SpreadsheetHelpers.convertCellCoordsNumberedArray(blocksTitlesStartLocation);
        colTitleStartCoords = new int[] { colTitleStartCoords[0] - 1, colTitleStartCoords[1] - 1 }; // Convert to zero-based counting

        row_num = colTitleStartCoords[0];
        for (int i = 0; i < data.getColumnSize(); i++) {
            // Get the current cell name
            String col_name = data.getColumnName(i);

            // Get the coords of the cell in the worksheet
            col_num = colTitleStartCoords[1] + i;
            int[] coords = { row_num, col_num };
            
            // Add the cell name to the cell in the worksheet
            addToCell(sheet, coords, col_name);
        }

        // Add all the data
        int[] dataStartCoords = SpreadsheetHelpers.convertCellCoordsNumberedArray(cellsStartLocation);
        dataStartCoords = new int[] { dataStartCoords[0] - 1, dataStartCoords[1] - 1 }; // Convert to zero-based counting

        for (int x = 0; x < data.getColumnSize(); x++) {
            for (int y = 0; y < data.getRowSize(); y++) {
                // Get the content stored in the ReportData object
                Object cellContent = data.getCell(y, x);

                // Get the coordinates of the target sheet value
                row_num = dataStartCoords[0] + y;
                col_num = dataStartCoords[1] + x;
                int[] coords = { row_num, col_num };

                // Add the content to the cell in the worksheet
                addToCell(sheet, coords, cellContent);
            }
        }

        // Clean up and close file and workbook streams
        targetFile_is.close();
        FileOutputStream targetFile_os = new FileOutputStream(targetPath);
        workbook.write(targetFile_os);
        targetFile_os.close();
    }

    /**
     * Adds the given content to the cell in the given location of the given sheet.
     * 
     * @param sheet The sheet.
     * @param location The string location (e.g. 'A10' for column A and row 10... careful it's one-based).
     * @param content The content to the put in the cell.
     */
    private void addToCell(Sheet sheet, String location, Object content) {
        // Get the coordinates in numerical form
        int[] num_location = SpreadsheetHelpers.convertCellCoordsNumberedArray(location);
        num_location = new int[] { num_location[0] - 1, num_location[1] - 1 }; // Convert to zero-based counting

        // Add the content to the cell
        addToCell(sheet, num_location, content);
    }

    /**
     * Adds the given content to the cell in the given location of the given sheet.
     * 
     * @param sheet The sheet.
     * @param location The integer pair location (e.g. [0, 5] -> The 0th row and the 5th column... careful it's zero-based).
     * @param content The content to be put in the cell.
     */
    private void addToCell(Sheet sheet, int[] location, Object content) {
        // Get the coordinates
        int rowNum = location[0], colNum = location[1];
    
        // Get the current row and current cell
        Row curr_row = (sheet.getRow(rowNum) != null) ? sheet.getRow(rowNum) : sheet.createRow(rowNum);
        Cell curr_cell = (curr_row.getCell(colNum) != null) ? curr_row.getCell(colNum) : curr_row.createCell(colNum);

        // Put the content in the cell

        // This is terrible but there is not much I can do due to the nature of the setCellValue method
        if (content instanceof String) // String-based content
            curr_cell.setCellValue((String) content);
        else if (content instanceof Integer) 
            curr_cell.setCellValue((Integer) content);
        else if (content instanceof Double)
            curr_cell.setCellValue((Double) content);
        else if (content instanceof Boolean)
            curr_cell.setCellValue((Boolean) content);
        else if (content instanceof Date)
            curr_cell.setCellValue((Date) content);
        else if (content instanceof Calendar)
            curr_cell.setCellValue((Calendar) content);
        else if (content instanceof RichTextString)
            curr_cell.setCellValue((RichTextString) content);
    }
}