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
        String blocksStartLocation = (String) dataLocations.get("blocks-start");
        String cellsTitlesStartLocation = (String) dataLocations.get("cells-start");

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

        // TODO: add all the data to the sheet

        targetFile_is.close();
        FileOutputStream targetFile_os = new FileOutputStream(ROOT_DIR + targetPath);
        workbook.write(targetFile_os);
        targetFile_os.close();
    }

    private void addToCell(Sheet sheet, String location, Object content) {
        int[] num_location = convertCellCoordsNumberedArray(location);
        int rowNum = num_location[0], colNum = num_location[1];

        Row curr_row = (sheet.getRow(rowNum) != null) ? sheet.getRow(rowNum) : sheet.createRow(rowNum);

        Cell curr_cell = (curr_row.getCell(colNum) != null) ? curr_row.getCell(colNum) : curr_row.createCell(colNum);

        if (content instanceof String)
            curr_cell.setCellValue((String) content);
        if (content instanceof Number)
            curr_cell.setCellValue((Double) content);
    }

    private static int[] convertCellCoordsNumberedArray(String cellCoordsStr) {
        // Split the number and letter parts apart
        String number_part = "";
        String letter_part = "";
        for (char c : cellCoordsStr.toCharArray()) {
            if (Character.isDigit(c))
                number_part += c;
            if (Character.isLetter(c)) {
                letter_part += c;
            }
        }

        // Compute the cell number of the number part
        int number_part_val = Integer.parseInt(number_part);

        // Compute the cell number of the letter part
        int exp = 0;
        int letter_part_val = 0;
        for (int i = letter_part.length()-1; i >= 0; i--) {
            char c = letter_part.charAt(i);
            int letter_rank = convertUpperCaseLetterAlphabetRanking(c);
            letter_part_val += letter_rank * (int)Math.pow(26, exp);
            exp++;
        }

        // Create and return the output array
        int[] ret = {number_part_val, letter_part_val}; // (Row, Column)
        return ret;
    }

    private static int convertUpperCaseLetterAlphabetRanking(char c) {
        int val_c = (int)c;

        int subtractor_upper = 64; //for upper case
        if(val_c <= 90 & val_c >= 65)
            return val_c-subtractor_upper;

        return 0;
    }

    public static void main(String[] args) {
        //System.out.println(convertUpperCaseLetterAlphabetRanking('Z'));
        int[] ret = convertCellCoordsNumberedArray("A80");

        System.out.println(ret[0] + ", " + ret[1]);
    }
}