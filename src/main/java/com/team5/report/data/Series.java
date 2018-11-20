package com.team5.report.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a series in a chart.
 * @param <A> The type of the contents of the series.
 */
public class Series<A> {
    private String name;
    private List<A> content;

    /**
     * Constructs the series from another series.
     * 
     * @param other The other series.
     */
    public Series(Series<A> other) {
        this.name = other.name;

        this.content = new ArrayList<>(other.content);
    }

    /**
     * Constructs a series with initialized content.
     * 
     * @param name The name of the series.
     * @param content The content to be put in the series.
     */
    public Series(String name, List<A> content) {
        this.name = name;
        this.content = content;
    }

    /**
     * Constructs an empty series.
     * 
     * @param name The name of the series.
     */
    public Series(String name) {
        this(name, new ArrayList<A>());
    }

    /**
     * Gets the name of the series.
     * 
     * @return Returns the name string.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the contents of the series.
     * 
     * @return Returns a list of the contents.
     */
    public List<A> getContent() {
        return new ArrayList<>(this.content);
    }

    /**
     * Adds an element to the series.
     * 
     * @param element The element.
     */
    public void addItem(A element) {
        this.content.add(element);
    }

    /**
     * Insert an element at a given index into the series.
     * 
     * @param index The insersion index.
     * @param element The element.
     */
    public void addItem(int index, A element) {
        this.content.add(index, element);
    }
}