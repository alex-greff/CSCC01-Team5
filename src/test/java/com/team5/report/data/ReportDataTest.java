package com.team5.report.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReportDataTest {
    String empty_RD_title = "Empty RowData";
    String empty_RD_rowAxisLabel = "Empty RowData row axis label";
    String empty_RD_colAxisLabel = "Empty RowData col axis label";
    ReportData<Integer> empty_RD;

    String one_RD_title = "One by One RowData";
    String one_RD_rowAxisLabel = "One by One RowData row axis label";
    String one_RD_colAxisLabel = "One by One RowData col axis label";
    ReportData<Integer> one_RD;
    Integer[][] one_RD_data = {
        {5}
    };

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

    @BeforeEach
    void setup() {
        empty_RD = new ReportData<>(empty_RD_title, empty_RD_rowAxisLabel, empty_RD_colAxisLabel);

        one_RD = new ReportData<>(one_RD_title, one_RD_rowAxisLabel, one_RD_colAxisLabel);
        one_RD.addRow("Row 1");
        one_RD.addColumn("Col 1");
        one_RD.setCell(0, 0, one_RD_data[0][0]);

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

    @Test
    @DisplayName("Test get title")
    void testGetTitle() {
        assertEquals(empty_RD_title, empty_RD.getTitle());
    }

    @Test
    @DisplayName("Test set title")
    void testSetTitle() {
        String newTitle = "new title";
        empty_RD.setTitle(newTitle);
        assertEquals(newTitle, empty_RD.getTitle());
    }

    @Test
    @DisplayName("Test get row axis label")
    void testGetRowAxisLabel() {
        assertEquals(one_RD_rowAxisLabel, one_RD.getRowAxisLabel());
    }
    
    @Test
    @DisplayName("Test set row axis label")
    void testSetRowAxisLabel() {
        String newRowAxisLabel = "new row axis label";

        one_RD.setRowAxisLabel(newRowAxisLabel);
        assertEquals(newRowAxisLabel, one_RD.getRowAxisLabel());
    }

    @Test
    @DisplayName("Test get column axis label")
    void testGetColAxisLabel() {
        assertEquals(one_RD_colAxisLabel, one_RD.getColumnAxisLabel());
    }

    @Test
    @DisplayName("Test set column axis label")
    void testSetColAxisLabel() {
        String newColAxisLabel = "new col axis label";

        one_RD.setColumnAxisLabel(newColAxisLabel);
        assertEquals(newColAxisLabel, one_RD.getColumnAxisLabel());
    }

    @Test
    @DisplayName("Get dimensions of empty report data table")
    void testGetDimensionsEmpty() {
        assertEquals(0, empty_RD.getRowSize());
        assertEquals(0, empty_RD.getColumnSize());
    }

    @Test
    @DisplayName("Get dimensions of non empty report data table")
    void testGetDimensionsNonEmpty() {
        assertEquals(4, many_RD.getRowSize());
        assertEquals(3, many_RD.getColumnSize());
    }

    @Test
    @DisplayName("Get existent row name")
    void testGetExistentRowName() {
        assertEquals("Row 2", many_RD.getRowName(1));
    }

    @DisplayName("Get out of bounds row name with positive index")
    void testGetOutOfBoundsRowNamePosIndex() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.getRowName(5);        
            }
        );
    }

    @Test
    @DisplayName("Get out of bounds row name with negative index")
    void testGetOutOfBoundsRowNameNegIndex() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.getRowName(-1);        
            }
        );
    }

    @Test
    @DisplayName("Get out of bounds row name with empty report data")
    void testGetOutOfBoundsRowNameEmpty() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                empty_RD.getRowName(0);
            }
        );
    }

    @Test
    @DisplayName("Get existent column name")
    void testGetExistentColName() {
        assertEquals("Col 3", many_RD.getColumnName(2));
    }

    @DisplayName("Get out of bounds column name with positive index")
    void testGetOutOfBoundsColNamePosIndex() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.getColumnName(6);
            }
        );
    }

    @Test
    @DisplayName("Get out of bounds column name with negative index")
    void testGetOutOfBoundsColNameNegIndex() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.getColumnName(-1);        
            }
        );
    }

    @Test
    @DisplayName("Get out of bounds column name with empty report data")
    void testGetOutOfBoundsColNameEmpty() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                empty_RD.getColumnName(0);
            }
        );
    }


    @Test
    @DisplayName("Set row name")
    void testSetRowName() {
        String newName = "New row name";
        one_RD.setRowName(0, newName);

        assertEquals(newName, one_RD.getRowName(0));
    }

    @Test
    @DisplayName("Set row name of non-existent row")
    void testSetRowNameNonExistent() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.setRowName(4, "something");
            }
        );
    }

    @Test
    @DisplayName("Set column name")
    void testSetColName() {
        String newName = "New col name";
        one_RD.setColumnName(0, newName);

        assertEquals(newName, one_RD.getColumnName(0));
    }

    @Test
    @DisplayName("Set column name of non-existent column")
    void testSetColNameNonExistent() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.setColumnName(-1, "something");
            }
        );
    }


    @Test
    @DisplayName("Test get cell")
    void testGetCell() {
        assertEquals(3, many_RD.getCell(2, 1));
    }

    @Test
    @DisplayName("Test get cell out of bounds")
    void testGetCellOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.getCell(5, 0);
            }
        );
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.getCell(0, 4);
            }
        );

        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.getCell(-1, 4);
            }
        );
    }

    @Test
    @DisplayName("test set cell")
    void testSetCell() {
        int newVal = 1000;
        many_RD.setCell(1, 2, newVal);
        assertEquals(newVal, many_RD.getCell(1, 2));
    }

    @Test
    @DisplayName("test set cell out of bounds")
    void testSetCellOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.setCell(5, 0, 17);
            }
        );
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.setCell(0, 4, 17);
            }
        );

        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.setCell(-1, 4, 17);
            }
        );
    }

    @Test
    @DisplayName("Test clear cell")
    void testClearCell() {
        many_RD.clearCell(0, 0);
        assertNull(many_RD.getCell(0, 0));
    }

    @Test
    @DisplayName("Test clear cell out of bounds")
    void testClearCellOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.clearCell(5, 0);
            }
        );
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.clearCell(0, 4);
            }
        );

        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.clearCell(-1, 4);
            }
        );
    }

    @Test
    @DisplayName("Test remove row")
    void testRemoveRow() {
        many_RD.removeRow(0);

        // Should be
        // {-7, 9, 10},
        // {2, 3, 2},
        // {5, 0, 0},
        assertEquals(-7, many_RD.getCell(0, 0));
        assertEquals(9, many_RD.getCell(0, 1));
        assertEquals(10, many_RD.getCell(0, 2));

        assertEquals(2, many_RD.getCell(1, 0));
        assertEquals(3, many_RD.getCell(1, 1));
        assertEquals(2, many_RD.getCell(1, 2));

        assertEquals(5, many_RD.getCell(2, 0));
        assertEquals(0, many_RD.getCell(2, 1));
        assertEquals(0, many_RD.getCell(2, 2));

        assertEquals(3, many_RD.getRowSize());
    }

    @Test
    @DisplayName("Test remove row out of bounds")
    void testRemoveRowOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.removeRow(4);
            }
        );
    }

    @Test
    @DisplayName("Test remove column")
    void testRemoveCol() {
        many_RD.removeColumn(0);

        // Should be
        // {5, 3},
        // {9, 10},
        // {3, 2},
        // {0, 0},
        assertEquals(5, many_RD.getCell(0, 0));
        assertEquals(3, many_RD.getCell(0, 1));

        assertEquals(9, many_RD.getCell(1, 0));
        assertEquals(10, many_RD.getCell(1, 1));

        assertEquals(3, many_RD.getCell(2, 0));
        assertEquals(2, many_RD.getCell(2, 1));

        assertEquals(0, many_RD.getCell(3, 0));
        assertEquals(0, many_RD.getCell(3, 1));


        assertEquals(2, many_RD.getColumnSize());
    }

    @Test
    @DisplayName("Test remove row out of bounds")
    void testRemoveColOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class,
            ()->{
                many_RD.removeColumn(3);
            }
        );
    }

    public static void main(String[] args) {
        ReportDataTest t = new ReportDataTest();

        t.setup();

        String str = t.many_RD.toString();
        System.out.println(str);

        int x = t.many_RD.getRowSize();
    }
}