package com.team5.report.implementations;

import java.util.List;

import com.team5.database.DatabaseDriver;
import com.team5.database.MongoDriver;
import com.team5.report.charts.PieChartReport;
import com.team5.utilities.ConfigurationLoader;
import com.team5.utilities.ConfigurationNotFoundException;
import com.team5.utilities.JSONLoader;

import org.apache.commons.io.FilenameUtils;
import org.javatuples.Pair;
import org.json.simple.JSONObject;

public class EmploymentServicesDemographics extends PieChartReport {
    private Pair<Double, Double> dimensions;
    private String title, report_name, report_description;

    private String database_URI;

    public EmploymentServicesDemographics() throws ConfigurationNotFoundException {
        setup();
    }

    private void setup() {
        try {
            String config_location = (String) ConfigurationLoader.loadConfiguration("report-locations").get("employment-services-demographics");
            JSONObject config = JSONLoader.parseJSONFile(config_location);
            
            dimensions = new Pair<Double,Double>((Double) config.get("report-width"), (Double) config.get("report-height"));
            title = (String) config.get("title");
            report_name = (String) config.get("report-name");
            report_description = (String) config.get("report-description");

            database_URI = ConfigurationLoader.loadConfiguration("database-URI").get("icare_db_remote").toString();

        } catch (Exception e) {
            System.err.println("Warning: configuration setup failed...");
            e.printStackTrace();
        }
    }

    @Override
    protected Pair<Double, Double> getDimensions() {
        return dimensions;
    }

    @Override
    public String getReportName() {
        return report_name;
    }

    @Override
    public String getReportDescription() {
        return report_description;
    }



    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    protected List<Pair<String, Double>> getPieChartData() {
        DatabaseDriver db = new MongoDriver(ConfigurationLoader.loadConfiguration("database-URI").get("test_db_remote").toString(), "test_db", FilenameUtils.removeExtension(selectedTemplateType));

        return null;
    }
}