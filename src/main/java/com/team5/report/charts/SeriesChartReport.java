package com.team5.report.charts;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public abstract class SeriesChartReport<X, Y> extends ChartReport {

    private String xAxisLabel;
    private String yAxisLabel;

    protected SeriesChartReport(String title, Pair<Double, Double> dimensions, String xAxisLabel, String yAxisLabel) {
        super(title, dimensions);
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
    }

    // protected abstract List<Series<Tuple>> getData();

    @Override
    protected void setupStage(Stage stage) {
        super.setupStage(stage);

        getXAxis().setLabel(this.xAxisLabel);
        getYAxis().setLabel(this.yAxisLabel);

    }

    protected abstract Axis<X> getXAxis();
    protected abstract Axis<Y> getYAxis();

    @Override
    protected Scene makeChart(Stage stage) {
        setupStage(stage);

        Scene scene = new Scene(getChart(), getDimensions().getValue0(), getDimensions().getValue1());

        return scene;
    }

    @Override
    protected Chart getChart() {
        return getXYChart();
    }

    protected abstract XYChart<X,Y> getXYChart();

    protected String xAxisLabel() {
        return this.xAxisLabel;
    }
    
    protected String yAxisLabel() {
        return this.yAxisLabel;
    }

    public class Series<A> {
        private String name;
        private List<A> content;

        public Series(Series<A> other) {
            this.name = other.name;

            this.content = new ArrayList<>(other.content);
        }

        public Series(String name, List<A> content) {
            this.name = name;
            this.content = content;
        }

        public Series(String name) {
            this(name, new ArrayList<A>());
        }

        public String getName() {
            return this.name;
        }

        public List<A> getContent() {
            return new ArrayList<>(this.content);
        }

        public void addItem(A element) {
            this.content.add(element);
        }

        public void addItem(int index, A element) {
            this.content.add(index, element);
        }
    }
}