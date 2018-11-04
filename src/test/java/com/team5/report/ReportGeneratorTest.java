package com.team5.report;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
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

    final String REPORT_GENERATOR_TESTS_ROOT = "testFiles/reportGeneratorTests";
    final String targetPath1 = REPORT_GENERATOR_TESTS_ROOT + "/excelFile1Populated.xlsm";

    ReportGenerator RG;

    @Before
    void setupOnce() {
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

    @BeforeEach
    void setupPerTest() {
        RG = new ReportGenerator();
    }

    @Test
    @DisplayName("Test generating a report with data")
    void testGenerateReportWithData() throws ParseException, IOException, InvalidFormatException{
        RG.generateReport("BasicBarGraph.json", targetPath1, many_RD);
    }

    @Test
    @DisplayName("")
    void test() {
    }

    public static void main(String[] args) throws ParseException, IOException, InvalidFormatException {
        ReportGeneratorTest rgt = new ReportGeneratorTest();
        rgt.setupOnce();
        rgt.setupPerTest();
        rgt.testGenerateReportWithData();

        System.out.println("Done!");
    }
}