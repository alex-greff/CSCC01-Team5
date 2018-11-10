package com.team5.report.data;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The data container for the reports.
 * 
 * @param <T> The generic type of the data stored.
 */
public class ReportData<T> implements Iterable<ReportData<T>.Series>{
    /**
     * A class representing a series (row) on the table.
     */
    public class Series implements Iterable<ReportData<T>.Block>{
        /**
         * The name of the series.
         */
        private String name;
        /**
         * The list of blocks (columns) and the contents of the cell in the series.
         */
        private ArrayList<Block> blocks = new ArrayList<>();

        @Override
        public Iterator<ReportData<T>.Block> iterator() {
            Iterator<ReportData<T>.Block> it = new Iterator<ReportData<T>.Block>() {
                ArrayList<ReportData<T>.Block> arr = new ArrayList<>(blocks);

                @Override
                public boolean hasNext() {
                    return arr.size() > 0;
                }

                @Override 
                public ReportData<T>.Block next() {
                    return arr.remove(0);
                }
            };
            return it;
        }


        /**
         * Constructs the series with the given name.
         * 
         * @param name The name of the series.
         */
        public Series (String name) {
            this.name = name;
        }

        /**
         * Sets the name of the series to a new name.
         * 
         * @param newName The new name.
         */
        public void setName(String newName) {
            this.name = newName;
        }

        /**
         * Gets the name of the series.
         * 
         * @return Returns the name of the series.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Inserts a block at the end of this series.
         * 
         * @param newBlock The new block.
         */
        public void addBlock(Block newBlock) {
            this.blocks.add(newBlock);
        }

        /**
         * Sets the contents of the cell at the given index.
         * 
         * @param index The index of the wanted cell.
         * @param data The data to be inserted into the cell.
         * @throws IndexOutOfBoundsException Thrown if the index beyond the bounds of the series.
         */
        public void setCell(int index, T data) throws IndexOutOfBoundsException {
            // Upper bound check
            if (index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds. Add more blocks to expand the matrix.");
            // Lower bound check
            if (index < 0)
                throw new IndexOutOfBoundsException("Index '" + index + "' can not be negative.");
            
            this.blocks.get(index).setContent(data);;
        }

        /**
         * Gets the cell contents at the given index.
         * 
         * @param index The index.
         * @return Returns the content of the cell.
         */
        public T getCell(int index) throws IndexOutOfBoundsException {
            // Upper bound check
            if (index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds. Add more blocks to expand the matrix.");
            // Lower bound check
            if (index < 0)
                throw new IndexOutOfBoundsException("Index '" + index + "' can not be negative.");
            
            return this.blocks.get(index).getContent();
        }

        /**
         * Sets the block at the given index to a new block.
         * 
         * @param index The index.
         * @param block The new block.
         * @throws IndexOutOfBoundsException Thrown if the index is out of bounds.
         */
        public void setBlock(int index, Block block) throws IndexOutOfBoundsException {
            if (index < 0 || index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds");

            this.blocks.set(index, block);
        }

        /**
         * Sets the block name at the given index.
         * 
         * @param index The index.
         * @param name The new name of the block.
         * @throws IndexOutOfBoundsException Thrown if the index is out of bounds.
         */
        public void setBlockName(int index, String name) throws IndexOutOfBoundsException {
            if (index < 0 || index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds");

            this.blocks.get(index).setName(name);
        }

        /**
         * Removes the block at the given index. Will also remove the cells associated with that block and cascade everything.
         * 
         * @param index The index.
         * @throws IndexOutOfBoundsException Thrown if the index is out of bounds.
         */
        public void removeBlock(int index) throws IndexOutOfBoundsException {
            if (index < 0 || index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index '" + index + "'' is out of bounds");
            
            this.blocks.remove(index);
        }

        /**
         * Clears the contents of the cell at the given index (i.e. set its value to null).
         * 
         * @param index The index
         * @throws IndexOutOfBoundsException Thrown if the index is out of bounds.
         */
        public void clearCell(int index) throws IndexOutOfBoundsException {
            if (index < 0 || index >= this.blocks.size())
                throw new IndexOutOfBoundsException("Index " + index + "'' is out of bounds");
            
            this.blocks.get(index).setContent(null);
        }

        /**
         * The string representation of the series.
         */
        public String toString() {
            return this.getName() + ": " + this.blocks.toString();
        }
    }

    /**
     * A class reprsenting a block (column) on the table.
     */
    public class Block {
        /**
         * The name of the block.
         */
        private String name;

        /**
         * The content of the block
         */
        private T content;

        /**
         * Constructs the block with the given name.
         * 
         * @param name The name of the block.
         */
        public Block(String name, T content) {
            this.name = name;
        }

        /**
         * Sets the name of the block to a new name.
         * 
         * @param newName The new name.
         */
        public void setName(String newName) {
            this.name = newName;
        }

        /**
         * Gets the name of the block.
         * 
         * @return Returns the name of the block.
         */
        public String getName() {
            return this.name;
        }


        /**
         * Sets the content of the block.
         * 
         * @param newContent The new content.
         */
        public void setContent(T newContent) {
            this.content = newContent;
        }

        /**
         * Gets the content of the block.
         * 
         * @return Returns the content of the block.
         */
        public T getContent() {
            return this.content;
        }
        

        /**
         * The string representation of the block.
         */
        public String toString() {
            return String.format("%s = %s", this.getName(), this.getContent().toString());
        }
    }

    @Override
    public Iterator<ReportData<T>.Series> iterator() {
        Iterator<ReportData<T>.Series> it = new Iterator<ReportData<T>.Series>() {
            ArrayList<ReportData<T>.Series> arr = new ArrayList<>(series);

            @Override
            public boolean hasNext() {
                return arr.size() > 0;
            }

            @Override
            public ReportData<T>.Series next() {
                return arr.remove(0);
            }
        };
        return it;
    }


    /**
     * Store the title, row axis label and column axis label of the table.
     */
    private String title, rowAxisLabel, columnAxisLabel;
    /**
     * Store a list of all the blocks in the table.
     */
    private ArrayList<Block> blocks = new ArrayList<>();
    /**
     * Store a list of all the series in the table.
     */
    private ArrayList<Series> series = new ArrayList<>();

    /**
     * Construct the report data table.
     * 
     * @param title The title of the table.
     * @param rowAxisLabel The label of the row axis.
     * @param columnAxisLabel The label of the column axis.
     */
    public ReportData(String title, String rowAxisLabel, String columnAxisLabel) {
        this.title = title;
        this.rowAxisLabel = rowAxisLabel;
        this.columnAxisLabel = columnAxisLabel;
    }

    /**
     * Sets the title of the table.
     * 
     * @param newTitle The new title.
     */
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    /**
     * Sets the row axis label of the table.
     * 
     * @param newRowAxisLabel The new row axis label.
     */
    public void setRowAxisLabel(String newRowAxisLabel) {
        this.rowAxisLabel = newRowAxisLabel;
    }

    /**
     * Sets the column axis label of the table.
     * 
     * @param newColumnAxisLabel The new column axis label.
     */
    public void setColumnAxisLabel(String newColumnAxisLabel) {
        this.columnAxisLabel = newColumnAxisLabel;
    }

    /**
     * Gets the title of the table.
     * 
     * @return Returns the title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the row axis label of the table.
     * 
     * @return Returns the row axis label.
     */
    public String getRowAxisLabel() {
        return this.rowAxisLabel;
    }

    /**
     * Gets the column axis label of the table.
     * 
     * @return Returns the column axis label.
     */
    public String getColumnAxisLabel() {
        return this.columnAxisLabel;
    }

    /**
     * Gets the row size of the table.
     * 
     * @return Returns the size of the rows.
     */
    public int getRowSize() {
        return this.series.size();
    }

    /**
     * Gets the column size of the table.
     * 
     * @return Returns the size of the columns.
     */
    public int getColumnSize() {
        return this.blocks.size();
    }

    /**
     * Adds a new row to the table.
     * 
     * @param rowName The name of the row.
     */
    public void addRow(String rowName) {
        Series newSeries = new Series(rowName);

        for (Block block : this.blocks) {
            newSeries.addBlock(block);
        }

        this.series.add(newSeries);
    }

    /**
     * Adds a new column to the table.
     * 
     * @param columnName The name of the column.
     */
    public void addColumn(String columnName) {
        Block newBlock = new Block(columnName, null);
        this.blocks.add(newBlock);

        for (Series currSeries : this.series){
            currSeries.addBlock(newBlock);
        }
    }

    /**
     * Gets the name of the row at the given index of the table.
     * 
     * @param index The index.
     * @return Returns the name of the row.
     * @throws IndexOutOfBoundsException Thrown of the index is out of bounds.
     */
    public String getRowName(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.series.size())
            throw new IndexOutOfBoundsException("Index " + index + "' out of row range.");
        
        return this.series.get(index).getName();
    }

    /**
     * Gets the name of the column at the given index of the table.
     * 
     * @param index The index.
     * @return Returns the name of the column.
     * @throws IndexOutOfBoundsException Thrown of the index is out of bounds.
     */
    public String getColumnName(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.blocks.size())
            throw new IndexOutOfBoundsException("Index " + index + "' out of column range.");

        return this.blocks.get(index).getName();
    }

    /**
     * Sets the name of the row at the given index in the table.
     * 
     * @param index The index.
     * @param newName The new name of the row.
     * @throws IndexOutOfBoundsException Thrown of the index is out of bounds.
     */
    public void setRowName(int index, String newName) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.series.size())
            throw new IndexOutOfBoundsException("Index " + index + "' out of row range.");
        
        this.series.get(index).setName(newName);
    }

    /**
     * Sets the name of the column at the given index in the table.
     * 
     * @param index The index.
     * @param newName The new name of the column.
     * @throws IndexOutOfBoundsException Thrown of the index is out of bounds.
     */
    public void setColumnName(int index, String newName ) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.blocks.size())
            throw new IndexOutOfBoundsException("Index '" + index + "' out of column range.");

        this.blocks.get(index).setName(newName);
    }

    /**
     * Removes the row at the given index. This includes the cells associated with it as well.
     * 
     * @param index The index.
     * @throws IndexOutOfBoundsException Thrown of the index is out of bounds.
     */
    public void removeRow(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.series.size())
            throw new IndexOutOfBoundsException("Index " + index + "' out of row range.");
        
        this.series.remove(index);
    }

    /**
     * Removes the column at the given index. This includes the cells associated with it as well.
     * 
     * @param index The index.
     * @throws IndexOutOfBoundsException Thrown of the index is out of bounds.
     */
    public void removeColumn(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.blocks.size())
            throw new IndexOutOfBoundsException("Index '" + index + "' out of column range.");
        
        this.blocks.remove(index);

        for (Series currSeries : this.series){
            currSeries.removeBlock(index);
        }
    }

    /**
     * Sets the set at the given row-column coordinate.
     * 
     * @param row The row.
     * @param col The column.
     * @param data The data of the cell.
     * @throws IndexOutOfBoundsException Thrown if the row and/or column is out of bounds.
     */
    public void setCell(int row, int col, T data) throws IndexOutOfBoundsException {
        if (row < 0 || row >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row " + row + "' out of row range.");
        if (col < 0 || col >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row " + col + "' out of column range.");

        Series series = this.series.get(row);
        series.setCell(col, data);
    }

    /**
     * Clears the cell at the given row-column coordinate.
     * 
     * @param row The row.
     * @param col The column.
     * @throws IndexOutOfBoundsException Thrown if the row and/or column is out of bounds.
     */
    public void clearCell(int row, int col) throws IndexOutOfBoundsException {
        if (row < 0 || row >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row '" + row + "' out of row range.");
        if (col < 0 || col >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row '" + col + "' out of column range.");

        Series series = this.series.get(row);
        series.clearCell(col);
    }

    /**
     * Gets the cell contents at the given row-column coordinate.
     * 
     * @param row The row.
     * @param col The index.
     * @throws IndexOutOfBoundsException Thrown if the row and/or column is out of bounds.
     * @return Returns the cell contents.
     */
    public Object getCell(int row, int col)  throws IndexOutOfBoundsException {
        if (row < 0 || row >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row '" + row + "' out of row range.");
        if (col < 0 || col >= this.series.size()) 
            throw new IndexOutOfBoundsException("Row '" + col + "' out of column range.");

        Series series = this.series.get(row);
        return series.getCell(col);
    }

    /**
     * The string representation of the table.
     */
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
}