package com.team5.report.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SeriesTest {

    @Test
    @DisplayName("test initialize from another series")
    void testInitializeFromAnotherSeries() {
        String name = "mySeries";

        List<String> data = new ArrayList<String>() {{
            add("item1");
            add("item2");
            add("item3");
        }};

        Series<String> other = new Series<>(name, data);

        Series<String> series = new Series<>(other);

        assertEquals(data, series.getContent());
        assertEquals(name, series.getName());
    }

    @Test
    @DisplayName("test initialize with list")
    void testInitializeWithList() {
        String name = "mySeries";

        List<String> data = new ArrayList<String>() {{
            add("item1");
            add("item2");
            add("item3");
        }};

        Series<String> series = new Series<>(name, data);

        assertEquals(data, series.getContent());
        assertEquals(name, series.getName());
    }

    @Test
    @DisplayName("test initialize empty")
    void testInitializeEmpty() {
        String name = "mySeries";

        Series<String> series = new Series<>(name);

        assertEquals(name, series.getName());
    }

    @Test
    @DisplayName("test add item no index")
    void testAddItemNoIndex() {
        String name = "mySeries";
        String newItem = "item4";

        List<String> data = new ArrayList<String>() {{
            add("item1");
            add("item2");
            add("item3");
        }};

        Series<String> series = new Series<>(name, data);
        series.addItem(newItem);

        data.add(newItem);

        assertEquals(data, series.getContent());
    }

    @Test
    @DisplayName("test add item with index")
    void testAddItemWithIndex() {
        String name = "mySeries";
        String newItem = "item4";

        List<String> data = new ArrayList<String>() {{
            add("item1");
            add("item2");
            add("item3");
        }};

        Series<String> series = new Series<>(name, data);
        series.addItem(2, newItem);

        data.add(2, newItem);

        assertEquals(data, series.getContent());
    }

    @Test
    @DisplayName("test add item with out of bounds index")
    void testAddItemOutOfBoundsIndex() {
        String name = "mySeries";
        String newItem = "item4";

        List<String> data = new ArrayList<String>() {{
            add("item1");
            add("item2");
            add("item3");
        }};

        Series<String> series = new Series<>(name, data);

        assertThrows(IndexOutOfBoundsException.class, ()->{
            series.addItem(5, newItem);    
        },  "exception was thrown for out of bounds index");
    }
}