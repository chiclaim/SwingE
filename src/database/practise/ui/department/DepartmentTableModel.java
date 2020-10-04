package database.practise.ui.department;

import database.practise.bean.Department;
import database.practise.ui.callback.TableModelCallback;

import javax.swing.table.AbstractTableModel;
import java.sql.Date;
import java.util.List;

public class DepartmentTableModel extends AbstractTableModel {


    private String[] columns = {"编号", "部门名称"};

    private List<Department> list;

    private TableModelCallback<Department> callback;

    public DepartmentTableModel(List<Department> list, TableModelCallback<Department> callback) {
        this.list = list;
        this.callback = callback;
    }


    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Department employee = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return employee.getId() == 0 ? "" : employee.getId();
            case 1:
                return employee.getName();
        }
        return "";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        Department employee = list.get(rowIndex);
        System.out.println(aValue + "rowIndex = " + rowIndex + " columnIndex = " + columnIndex);
        switch (columnIndex) {
            case 0:
                employee.setId(Integer.parseInt(aValue.toString()));
                callback.onUpdate(employee);
                break;
            case 1:
                employee.setName(String.valueOf(aValue));
                callback.onUpdate(employee);
                break;
        }
    }


}
