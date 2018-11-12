package com.team5.report.data;

import java.util.ArrayList;
import java.util.List;

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