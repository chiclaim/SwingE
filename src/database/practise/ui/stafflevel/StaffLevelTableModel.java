package database.practise.ui.stafflevel;

import database.practise.bean.StaffLevel;
import database.practise.ui.callback.TableModelCallback;
import database.practise.utils.DialogUtils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StaffLevelTableModel extends AbstractTableModel {


    private String[] columns = {"编号", "职级名称", "职级类型", "级别"};

    private List<StaffLevel> list;

    private TableModelCallback<StaffLevel> callback;

    public StaffLevelTableModel(List<StaffLevel> list, TableModelCallback<StaffLevel> callback) {
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
        StaffLevel staffLevel = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return staffLevel.getId() == 0 ? "" : staffLevel.getId();
            case 1:
                return staffLevel.getName();
            case 2:
                return staffLevel.getType();
            case 3:
                return staffLevel.getLevel();
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
        StaffLevel staffLevel = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                staffLevel.setId(Integer.parseInt(aValue.toString()));
                callback.onUpdate(staffLevel);
                break;
            case 1:
                staffLevel.setName(String.valueOf(aValue));
                callback.onUpdate(staffLevel);
                break;
            case 2:
                staffLevel.setType(String.valueOf(aValue));
                callback.onUpdate(staffLevel);
                break;
            case 3:
                short level = 0;
                try {
                    level = Short.parseShort(aValue.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                staffLevel.setLevel(level);
                callback.onUpdate(staffLevel);
                break;
        }
    }


}
