package ru.ekipogh.sud;

import javax.swing.table.AbstractTableModel;

/**
 * Created by dedov_d on 10.09.2015.
 */
class CheckTableModel extends AbstractTableModel {

    Object rowData[][] = {};
    String[] columnNames = {"A", "Name"};

    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Object getValueAt(int row, int column) {
        return rowData[row][column];
    }

    public Class getColumnClass(int column) {
        return (getValueAt(0, column).getClass());
    }

    public void setValueAt(Object value, int row, int column) {
        rowData[row][column] = value;
    }

    public boolean isCellEditable(int row, int column) {
        return (column != 1);
    }

    public void addRow(boolean availability, String name) {
        Object newRowData[][] = new Object[rowData.length + 1][2];
        for (int i = 0; i < rowData.length; i++)
            newRowData[i] = rowData[i];
        newRowData[rowData.length] = new Object[]{availability, name};
        rowData = newRowData;
    }
}
