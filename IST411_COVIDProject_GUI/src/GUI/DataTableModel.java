/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author RIVER
 */
public class DataTableModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] rows;
    
    public DataTableModel(String[] c, Object[][] r) {
        this.columnNames = c;
        this.rows = r;
    }
    
    @Override
    public int getRowCount() {
        return this.rows.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows[rowIndex][columnIndex];
    }
    
    @Override
    public String getColumnName(int col){
        return this.columnNames[col];
    }
    
    @Override
    public Class getColumnClass(int column){
        return getValueAt(0,column).getClass();
    }
}
