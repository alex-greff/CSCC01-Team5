package com.team5.report;

import java.util.ArrayList;

public class ReportData<T> {
    private class Series {
        private String name;
        private ArrayList<Block> blocks = new ArrayList<>();
        private ArrayList<T> cells = new ArrayList<>();

        public Series (String name) {
            this.name = name;
        }

        public void setName(String newName) {
            this.name = newName;
        }

        public String getName() {
            return this.name;
        }

        public void addBlock(Block newBlock) {
            this.blocks.add(newBlock);
            this.cells.add(null);
        }

        public void setCell(int index, T data) throws IndexOutOfBoundsException {
            // Upper bound check
            if (index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds. Add more blocks to expand the matrix.");
            // Lower bound check
            if (index < 0)
                throw new IndexOutOfBoundsException("Index '" + index + "' can not be negative.");
            
            this.cells.set(index, data);
        }

        public void setBlock(int index, Block block) throws IndexOutOfBoundsException {
            if (index < 0 || index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds");

            this.blocks.set(index, block);
        }

        public void setBlockName(int index, String name) throws IndexOutOfBoundsException {
            if (index < 0 || index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds");

            this.blocks.get(index).setName(name);
        }

        public void removeBlock(int index) throws IndexOutOfBoundsException {
            if (index < 0 || index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds");
            
            this.blocks.remove(index);
            this.cells.remove(index);
        }

        public void clearCell(int index) throws IndexOutOfBoundsException {
            if (index < 0 || index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index " + index + "'' is out of bounds");
            
            this.cells.set(index, null);
        }

        public String toString() {
            return this.getName() + ": " + this.cells.toString();
        }
    }

    private class Block {
        private String name;

        public Block(String name) {
            this.name = name;
        }

        public void setName(String newName) {
            this.name = newName;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.getName();
        }
    }

    private String title, rowAxisLabel, columnAxisLabel;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Series> series = new ArrayList<>();

    public ReportData(String title, String rowAxisLabel, String columnAxisLabel) {
        this.title = title;
        this.rowAxisLabel = rowAxisLabel;
        this.columnAxisLabel = columnAxisLabel;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public void setRowAxisLabel(String newRowAxisLabel) {
        this.rowAxisLabel = newRowAxisLabel;
    }

    public void setColumnAxisLabel(String newColumnAxisLabel) {
        this.columnAxisLabel = newColumnAxisLabel;
    }

    public String getTitle() {
        return this.title;
    }

    public String getRowAxisLabel() {
        return this.rowAxisLabel;
    }

    public String getColumnAxisLabel() {
        return this.columnAxisLabel;
    }

    public int getRowSize() {
        return this.series.size();
    }

    public int getColumnSize() {
        return this.blocks.size();
    }

    public void addRow(String rowName) {
        Series newSeries = new Series(rowName);
        this.series.add(newSeries);
    }

    public void addColumn(String columnName) {
        Block newBlock = new Block(columnName);
        this.blocks.add(newBlock);

        for (Series currSeries : this.series){
            currSeries.addBlock(newBlock);
        }
    }

    public String getRowName(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.series.size())
            throw new IndexOutOfBoundsException("Index " + index + "' out of row range.");
        
        return this.series.get(index).getName();
    }

    public String getColumnName(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.blocks.size())
            throw new IndexOutOfBoundsException("Index " + index + "' out of column range.");

        return this.blocks.get(index).getName();
    }

    public void setRowName(int index, String newName) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.series.size())
            throw new IndexOutOfBoundsException("Index " + index + "' out of row range.");
        
        this.series.get(index).setName(newName);
    }

    public void setColumnName(int index, String newName ) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.blocks.size())
            throw new IndexOutOfBoundsException("Index '" + index + "' out of column range.");

        this.blocks.get(index).setName(newName);
    }

    public void removeRow(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.series.size())
            throw new IndexOutOfBoundsException("Index " + index + "' out of row range.");
        
        this.series.remove(index);
    }

    public void removeColumn(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.blocks.size())
            throw new IndexOutOfBoundsException("Index '" + index + "' out of column range.");
        
        this.blocks.remove(index);

        for (Series currSeries : this.series){
            currSeries.removeBlock(index);
        }
    }

    public void setCell(int row, int col, T data) throws IndexOutOfBoundsException {
        if (row < 0 || row >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row " + row + "' out of row range.");
        if (col < 0 || col >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row " + col + "' out of column range.");

        Series series = this.series.get(row);
        series.setCell(col, data);
    }

    public void clearCell(int row, int col) {
        if (row < 0 || row >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row '" + row + "' out of row range.");
        if (col < 0 || col >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row '" + col + "' out of column range.");

        Series series = this.series.get(row);
        series.clearCell(col);
    }

    public String toString() {
        StringBuilder ret_str = new StringBuilder();

        ret_str.append("Title: " + this.getTitle() + "\n");
        ret_str.append("Row Axis Label: " + this.getRowAxisLabel() + "\n");
        ret_str.append("Column Axis Label: " + this.getColumnAxisLabel() + "\n");

        ret_str.append("\nData:\n");

        ret_str.append("ROW/COL ");

        String blocks_str = blocks.toString();
        blocks_str = blocks_str.substring(1, blocks_str.length()-1);

        ret_str.append(blocks_str + "\n");
        
        for (Series s : this.series) {
            ret_str.append(s.toString() + "\n");
        }

        return ret_str.toString().trim();
    }

    public static void main(String[] args) {
        ReportData<Integer> test = new ReportData<>("title", "vertAxisName", "horizAxisName");
        test.addRow("series1");
        test.addRow("series2");
        test.addColumn("block1");
        test.addColumn("block2");
        test.addColumn("block3");
        test.setCell(0, 0, 4);
        // test.setCell(3, 0, 4);

        System.out.println(test.toString());
    }

    // private Iterator<Iterator<T>> rowIterator() {
    //     Iterator<Iterator<T>> it = new Iterator<Iterator<T>>() {
    //         private int currRow = 0;

    //         @Override
    //         public boolean hasNext() {
    //             return currRow <= series.size(); 
    //         }

    //         @Override
    //         public Iterator<T> next() {



    //             return null;
    //         }
    //     };
    //     return it;
    // }
}