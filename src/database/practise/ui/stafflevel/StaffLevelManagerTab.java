package database.practise.ui.stafflevel;

import database.practise.bean.StaffLevel;
import database.practise.presenter.stafflevel.IStaffLevelContract;
import database.practise.presenter.stafflevel.StaffLevelPresenter;
import database.practise.ui.base.BaseManagerTab;
import database.practise.ui.base.IBaseManagerContract;
import database.practise.ui.callback.TableModelCallback;
import database.practise.utils.StringUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * 职级管理界面
 */
public class StaffLevelManagerTab extends BaseManagerTab<StaffLevel>
        implements IStaffLevelContract.View, TableModelCallback<StaffLevel> {


    private JTextField nameField, typeField, levelField;

    @Override
    protected void resetInput() {
        super.resetInput();
        nameField.setText(null);
        typeField.setText(null);
        levelField.setText(null);
    }

    @Override
    public AbstractTableModel createTableModel(List<StaffLevel> dataList) {
        return new StaffLevelTableModel(dataList, this);
    }

    @Override
    public IBaseManagerContract.Presenter<StaffLevel> createPresenter() {
        return new StaffLevelPresenter(this);
    }

    @Override
    public StaffLevel buildBeanByField() {
        String name = nameField.getText();
        String type = typeField.getText();
        String level = levelField.getText();

        StaffLevel staffLevel = new StaffLevel();
        if (!StringUtils.isEmpty(name)) {
            staffLevel.setName(name);
        }
        short s_level = 0;
        try {
            s_level = Short.parseShort(level);
        } catch (Exception ignored) {
        }
        staffLevel.setLevel(s_level);

        if (!StringUtils.isEmpty(type)) {
            staffLevel.setType(type);
        }
        return staffLevel;
    }

    @Override
    public void addViews(JPanel panel) {
        JPanel p = new JPanel(new GridLayout(1, 1));
        buildLabelField(p, "职级名称: ", nameField = new JTextField(10));
        buildLabelField(p, "职级类型: ", typeField = new JTextField(10));
        buildLabelField(p, "级别: ", levelField = new JTextField(10));
        panel.add(p);
    }

    @Override
    public void initTable(JTable table) {
        super.initTable(table);
        columnTextCenter(table);
    }
}