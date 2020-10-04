package database.practise.ui.department;

import database.practise.bean.Department;
import database.practise.presenter.department.DepartmentPresenter;
import database.practise.presenter.department.IDepartmentContract;
import database.practise.ui.base.BaseManagerTab;
import database.practise.ui.base.IBaseManagerContract;
import database.practise.ui.callback.TableModelCallback;
import database.practise.utils.StringUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * 部门管理界面
 */
public class DepartmentManagerTab extends BaseManagerTab<Department>
        implements IDepartmentContract.View, TableModelCallback<Department> {


    private JTextField nameField;

    @Override
    protected void resetInput() {
        super.resetInput();
        nameField.setText(null);
    }

    @Override
    public AbstractTableModel createTableModel(List<Department> dataList) {
        return new DepartmentTableModel(dataList, this);
    }

    @Override
    public IBaseManagerContract.Presenter<Department> createPresenter() {
        return new DepartmentPresenter(this);
    }

    @Override
    public Department buildBeanByField() {
        String name = nameField.getText();

        Department department = new Department();
        if (!StringUtils.isEmpty(name)) {
            department.setName(name);
        }
        return department;
    }

    @Override
    public void addViews(JPanel panel) {
        JPanel p = new JPanel(new GridLayout(1, 21));
        buildLabelField(p, "部门名称: ", nameField = new JTextField(10));
        panel.add(p);
    }

    @Override
    public void initTable(JTable table) {
        super.initTable(table);
        columnTextCenter(table);
    }
}