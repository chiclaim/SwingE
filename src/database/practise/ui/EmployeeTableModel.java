package database.practise.ui;

import database.practise.bean.Employee;

import javax.swing.table.AbstractTableModel;
import java.sql.Date;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {


    private String[] columns = {"编号", "姓名", "花名", "出生年月", "性别", "部门", "职级"};

    private List<Employee> list;

    private EmployeeTableModelCallback callback;

    public EmployeeTableModel(List<Employee> list, EmployeeTableModelCallback callback) {
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
        Employee employee = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return employee.getId() == 0 ? "" : employee.getId();
            case 1:
                return employee.getName();
            case 2:
                return employee.getNickname();
            case 3:
                return employee.getBirthday();
            case 4:
                return employee.getGender();
            case 5:
                if (employee.getDepartment() == null) return "";
                return employee.getDepartment().getName();
            case 6:
                if (employee.getStaffLevel() == null) return "";
                return employee.getStaffLevel().getName();
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
        Employee employee = list.get(rowIndex);
        System.out.println(aValue + "rowIndex = " + rowIndex + " columnIndex = " + columnIndex);
        switch (columnIndex) {
            case 0:
                employee.setId(Integer.parseInt(aValue.toString()));
                callback.updateEmployee(employee);
                break;
            case 1:
                employee.setName(String.valueOf(aValue));
                callback.updateEmployee(employee);
                break;
            case 2:
                employee.setNickname(String.valueOf(aValue));
                callback.updateEmployee(employee);
                break;
            case 3:
                employee.setBirthday((Date) aValue);
                callback.updateEmployee(employee);
                break;
            case 4:
                employee.setGender(String.valueOf(aValue));
                callback.updateEmployee(employee);
                break;
            case 5:
                employee.getDepartment().setName(String.valueOf(aValue));
                callback.updateEmployee(employee);
                break;
            case 6:
                employee.getStaffLevel().setName(String.valueOf(aValue));
                callback.updateEmployee(employee);
                break;
        }
    }


}
