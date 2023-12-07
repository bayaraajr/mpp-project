package models;

import javax.swing.table.DefaultTableModel;

    public class ReadonlyTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public ReadonlyTableModel(Object[][] data, String[] columns) {
        super(data, columns);
    }
}
