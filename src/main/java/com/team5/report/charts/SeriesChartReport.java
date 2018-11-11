package com.team5.report.charts;

import java.util.ArrayList;
import java.util.List;

import com.team5.report.generators.SeriesChartGenerator;

import org.javatuples.Pair;

import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public abstract class SeriesChartReport<X, Y> extends ChartReport {
    protected abstract String getXAxisLabel();
    protected abstract String getYAxisLabel();

    protected abstract Axis<X> getXAxis();
    protected abstract Axis<Y> getYAxis();

    @Override
    protected void setupStage(Stage stage) {
        super.setupStage(stage);

        getXAxis().setLabel(this.getXAxisLabel());
        getYAxis().setLabel(this.getYAxisLabel());

    }

    // @Override
    // protected void makeChart(Stage stage, Scene scene) {
    //     setupStage(stage);

    //     // Scene scene = new Scene(getChart(), getDimensions().getValue0(), getDimensions().getValue1());

    //     scene.setRoot(getChart());
    //     // scene.wid

    //     // return scene;
    // }

    @Override
    public void generate(String targetPath) {
        System.out.println("Ready to generate!");

        SeriesChartGenerator generator = new SeriesChartGenerator();

        Double width = getDimensions().getValue0();
        Double height = getDimensions().getValue1();

        generator.generate(getChart(), width, height, targetPath);
    }

    @Override
    protected Chart getChart() {
        return getXYChart();
    }

    protected abstract XYChart<X,Y> getXYChart();

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