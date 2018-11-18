package com.team5.report.charts;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.team5.report.data.Series;

import org.apache.commons.io.FileUtils;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BarChartReportTest {
    final String ROOT = "testFiles/reportTests/BarChartTest";

    final String testReport = ROOT + "/testBarChart.png";

    // @Test
    // @DisplayName("test generate")
    // void testGenerate() throws IOException {
    //     // Empty the directory contents
    //     FileUtils.cleanDirectory(new File(ROOT));

    //     Report r = new MockBarChartReport();

    //     r.generate(testReport);

    //     assertTrue(new File(testReport).isFile());
    // }

    

    @Test
    @DisplayName("test")
    void test() {
        
    }
}