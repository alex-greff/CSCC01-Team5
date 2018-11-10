package com.team5.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.report.data.ReportData;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReportExporterTest {
    String empty_RD_title = "Empty RowData";
    String empty_RD_rowAxisLabel = "Empty RowData row axis label";
    String empty_RD_colAxisLabel = "Empty RowData col axis label";
    ReportData<Integer> empty_RD;

    String many_RD_title = "Many RowData";
    String many_RD_rowAxisLabel = "Many RowData row axis label";
    String many_RD_colAxisLabel = "Many RowData col axis label";
    ReportData<Integer> many_RD;
    Integer[][] many_RD_data = {
        {0, 5, 3},
        {-7, 9, 10},
        {2, 3, 2},
        {5, 0, 0},
    };

    final String REPORT_TEMPLATE = "BasicBarGraph.json";
    final String REPORT_GENERATOR_TESTS_ROOT = "testFiles/reportGeneratorTests";
    final String targetPath1 = REPORT_GENERATOR_TESTS_ROOT + "/excelFile1Populated.xlsm";
    final String targetPath2 = REPORT_GENERATOR_TESTS_ROOT + "/excelFile2Populated.xlsm";

    ReportExporter RG;

    @BeforeEach
    void setup() throws ConfigurationNotFoundException {
        RG = new ReportExporter();

        empty_RD = new ReportData<>(empty_RD_title, empty_RD_rowAxisLabel, empty_RD_colAxisLabel);

        many_RD = new ReportData<>(many_RD_title, many_RD_rowAxisLabel, many_RD_colAxisLabel);
        for (int x = 0; x < many_RD_data[0].length; x++) {
            many_RD.addColumn("Col " + (x+1));
        }
        for (int y = 0; y < many_RD_data.length; y++) {
            many_RD.addRow("Row " + (y+1));
        }

        for (int row = 0; row < many_RD_data.length; row++) {
            for (int col = 0; col < many_RD_data[0].length; col++) {
                int data = many_RD_data[row][col];
                many_RD.setCell(row, col, data);
            }
        }
    }

    @Test
    @DisplayName("Test generating a report with data")
    void testGenerateReportWithData() throws ParseException, IOException, InvalidFormatException {
        RG.exportReport(REPORT_TEMPLATE, targetPath1, many_RD);

        FileInputStream targetFile_is = new FileInputStream(new File(targetPath1));
        Workbook workbook = WorkbookFactory.create(targetFile_is);
        Sheet sheet = workbook.getSheet("Data");

        Row row1 = sheet.getRow(0);
        assertEquals("Many RowData", row1.getCell(0).getStringCellValue());
        assertEquals("Many RowData row axis label", row1.getCell(1).getStringCellValue());
        assertEquals("Many RowData col axis label", row1.getCell(2).getStringCellValue());

        Row row2 = sheet.getRow(1);
        assertEquals("Col 1", row2.getCell(1).getStringCellValue());
        assertEquals("Col 2", row2.getCell(2).getStringCellValue());
        assertEquals("Col 3", row2.getCell(3).getStringCellValue());

        Row row3 = sheet.getRow(2);
        assertEquals("Row 1", row3.getCell(0).getStringCellValue());
        assertEquals(0, row3.getCell(1).getNumericCellValue());
        assertEquals(5, row3.getCell(2).getNumericCellValue());
        assertEquals(3, row3.getCell(3).getNumericCellValue());

        targetFile_is.close();
    }

    @Test
    @DisplayName("Test generating a report with empty data object")
    void testGenerateReportWitEmptyData() throws ParseException, IOException, InvalidFormatException {
        RG.exportReport(REPORT_TEMPLATE, targetPath2, empty_RD);

        FileInputStream targetFile_is = new FileInputStream(new File(targetPath2));
        Workbook workbook = WorkbookFactory.create(targetFile_is);
        Sheet sheet = workbook.getSheet("Data");

        Row row1 = sheet.getRow(0);
        assertEquals("Empty RowData", row1.getCell(0).getStringCellValue());
        assertEquals("Empty RowData row axis label", row1.getCell(1).getStringCellValue());
        assertEquals("Empty RowData col axis label", row1.getCell(2).getStringCellValue());

        Row row2 = sheet.getRow(1);
        assertNull(row2);

        targetFile_is.close();
    }

    @Test
    @DisplayName("Test generate report with non-existent template file")
    void testGenerateReportWithNonExistentTemplateFile() throws ParseException, IOException, InvalidFormatException {
        assertThrows(FileNotFoundException.class,
            ()->{
                RG.exportReport("someTemplateThatDoesNotExist.json", targetPath1, many_RD);
            }
        );
    }
}