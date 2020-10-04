package database.practise.ui.employee;

import database.practise.bean.Department;
import database.practise.bean.Employee;
import database.practise.bean.StaffLevel;
import database.practise.presenter.employee.EmployeePresenter;
import database.practise.presenter.employee.IEmployeeContract;
import database.practise.ui.base.BaseManagerTab;
import database.practise.ui.base.IBaseManagerContract;
import database.practise.ui.callback.TableModelCallback;
import database.practise.utils.DialogUtils;
import database.practise.utils.StringUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 员工管理界面
 */
public class EmployeeManagerTab extends BaseManagerTab<Employee>
        implements IEmployeeContract.View, TableModelCallback<Employee> {


    private JTextField nameField, nicknameField, genderField, birthdayField,
            departmentField, staffLevelField;

    @Override
    protected void resetInput() {
        super.resetInput();
        nameField.setText(null);
        nicknameField.setText(null);
        genderField.setText(null);
        birthdayField.setText(null);
        departmentField.setText(null);
        staffLevelField.setText(null);
    }

    @Override
    public AbstractTableModel createTableModel(List<Employee> dataList) {
        return new EmployeeTableModel(dataList, this);
    }

    @Override
    public IBaseManagerContract.Presenter<Employee> createPresenter() {
        return new EmployeePresenter(this);
    }

    @Override
    public Employee buildBeanByField() {
        String name = nameField.getText();
        String nickname = nicknameField.getText();
        String gender = genderField.getText();
        String birthday = birthdayField.getText();
        String depart = departmentField.getText();
        String staff = staffLevelField.getText();

        Employee employee = new Employee();
        if (!StringUtils.isEmpty(name)) {
            employee.setName(name);
        }
        if (!StringUtils.isEmpty(nickname)) {
            employee.setNickname(nickname);
        }
        employee.setGender(gender);
        if (!StringUtils.isEmpty(depart)) {
            employee.setDepartment(new Department(0, depart));
        }
        if (!StringUtils.isEmpty(staff)) {
            employee.setStaffLevel(new StaffLevel(0, staff));
        }
        if (!StringUtils.isEmpty(birthday)) {
            try {
                Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(birthday).getTime());
                employee.setBirthday(date);
            } catch (ParseException e) {
                DialogUtils.showPlainMessage(EmployeeManagerTab.this, "出生日期格式不合法 eg.2020-10-04");
                throw new IllegalArgumentException();
            }
        }
        return employee;
    }

    @Override
    public void addViews(JPanel panel) {
        JPanel p = new JPanel(new GridLayout(2, 3));
        buildLabelField(p, "姓名: ", nameField = new JTextField(10));
        buildLabelField(p, "花名: ", nicknameField = new JTextField(10));
        buildLabelField(p, "性别: ", genderField = new JTextField(10));
        buildLabelField(p, "生日: ", birthdayField = new JTextField(10));
        buildLabelField(p, "部门: ", departmentField = new JTextField(10));
        buildLabelField(p, "职级: ", staffLevelField = new JTextField(10));
        panel.add(p);
    }

    @Override
    public void initTable(JTable table) {
        super.initTable(table);
        // 文字居中展示
        columnTextCenter(table);
        // 男、女选项
        setUpGenderColumn(table.getColumnModel().getColumn(4));
    }


    private void setUpGenderColumn(TableColumn sportColumn) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("男");
        comboBox.addItem("女");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setToolTipText("change gender");
        sportColumn.setCellRenderer(renderer);
    }
}