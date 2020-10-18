package database.practise.ui.employee;

import database.practise.bean.Employee;
import database.practise.ui.callback.TableModelCallback;

import javax.swing.table.AbstractTableModel;
import java.sql.Date;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {


    private String[] columns = {"编号", "姓名", "花名", "出生年月", "性别", "部门", "职级"};

    private List<Employee> list;

    private TableModelCallback<Employee> callback;

    public EmployeeTableModel(List<Employee> list, TableModelCallback<Employee> callback) {
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
                // return employee.getBirthday() 默认的 render 不支持修改 Date，先使用 String，否则双击生日这一列不能编辑。
                if (employee.getBirthday() == null) return "";
                return employee.getBirthday().toString();
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
        switch (columnIndex) {
            case 0:
                employee.setId(Integer.parseInt(aValue.toString()));
                callback.onUpdate(employee);
                break;
            case 1:
                if (aValue.equals(employee.getName())) return;
                employee.setName(String.valueOf(aValue));
                callback.onUpdate(employee);
                break;
            case 2:
                if (aValue.equals(employee.getNickname())) return;
                employee.setNickname(String.valueOf(aValue));
                callback.onUpdate(employee);
                break;
            case 3:
                if (employee.getBirthday() != null &&
                        employee.getBirthday().toString().equals(aValue.toString())) return;
                Date newDate;
                if (aValue.toString().trim().length() == 0) {
                    newDate = null;
                } else {
                    try {
                        newDate = Date.valueOf(aValue.toString());
                    } catch (Exception e) {
                        //e.printStackTrace();
                        callback.showMessageDialog("日期不合法");
                        return;
                    }
                }
                employee.setBirthday(newDate);
                callback.onUpdate(employee);
                break;
            case 4:
                if (aValue.equals(employee.getGender())) return;
                employee.setGender(String.valueOf(aValue));
                callback.onUpdate(employee);
                break;
            case 5:
                if (aValue.equals(employee.getDepartment().getName())) return;
                employee.getDepartment().setName(String.valueOf(aValue));
                callback.onUpdate(employee);
                break;
            case 6:
                if (aValue.equals(employee.getStaffLevel().getName())) return;
                employee.getStaffLevel().setName(String.valueOf(aValue));
                callback.onUpdate(employee);
                break;
        }
    }


}
