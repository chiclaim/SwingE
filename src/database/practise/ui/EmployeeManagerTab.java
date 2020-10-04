package database.practise.ui;

import database.practise.base.BasePanel;
import database.practise.bean.Department;
import database.practise.bean.Employee;
import database.practise.bean.Staff;
import database.practise.presenter.employee.EmployeePresenter;
import database.practise.presenter.employee.IEmployeeContract;
import database.practise.utils.DialogUtils;
import database.practise.utils.StringUtils;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerTab extends BasePanel implements IEmployeeContract.View, EmployeeTableModelCallback {


    private List<Employee> dataList = new ArrayList<>();
    private EmployeeTableModel employeeTableModel;
    private IEmployeeContract.Presenter presenter;
    private JTable table;

    public EmployeeManagerTab() {
        //super(new GridLayout(1, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        presenter = new EmployeePresenter(this);

        employeeTableModel = new EmployeeTableModel(dataList, this);

        table = new JTable(employeeTableModel);
        //table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //table.setColumnSelectionAllowed(true);

        JScrollPane scrollPane = new JScrollPane(table);

        // 文字居中展示
        columnTextCenter(table);
        // 男、女选项
        setUpGenderColumn(table, table.getColumnModel().getColumn(4));

        add(scrollPane);

        addBottomButton();

        addBottomInput();

        presenter.getList(null);
    }

    private void addBottomButton() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("添加");
        panel.add(addButton);

        JButton selectButton = new JButton("查询");
        panel.add(selectButton);

        JButton deleteButton = new JButton("删除");
        panel.add(deleteButton);

        JButton resetButton = new JButton("重置");
        panel.add(resetButton);

        // 添加操作
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataList.add(new Employee());
                employeeTableModel.fireTableDataChanged();
                table.setRowSelectionInterval(dataList.size() - 1, dataList.size() - 1);


            }
        });

        // 查询操作
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.getList(buildEmployeeByField());
            }
        });

        // 删除操作
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (dataList == null || dataList.isEmpty()) {
                    return;
                }

                if (row == -1) {
                    DialogUtils.showPlainMessage(EmployeeManagerTab.this, "请选中要删除的行");
                    return;
                }
                Employee employee = dataList.get(row);
                presenter.deleteEmployee(employee);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText(null);
                nicknameField.setText(null);
                genderField.setText(null);
                birthdayField.setText(null);
                departmentField.setText(null);
                staffLevelField.setText(null);
            }
        });

        add(panel);
    }

    private JTextField nameField, nicknameField, genderField, birthdayField,
            departmentField, staffLevelField;

    private void addBottomInput() {

        JPanel p = new JPanel(new GridLayout(2, 3));

        buildLabelField(p, "姓名: ", nameField = new JTextField(10));
        buildLabelField(p, "花名: ", nicknameField = new JTextField(10));
        buildLabelField(p, "性别: ", genderField = new JTextField(10));
        buildLabelField(p, "出生日期: ", birthdayField = new JTextField(10));
        buildLabelField(p, "部门: ", departmentField = new JTextField(10));
        buildLabelField(p, "职级: ", staffLevelField = new JTextField(10));

        add(p);
    }

    private Employee buildEmployeeByField() {
        String name = nameField.getText();
        String nickname = nicknameField.getText();
        String gender = genderField.getText();
        String birthday = birthdayField.getText();
        String depart = departmentField.getText();
        String staff = staffLevelField.getText();

        Employee employee = new Employee();
        employee.setName(name);
        employee.setNickname(nickname);
        employee.setGender(gender);
        employee.setDepartment(new Department(0, depart));
        employee.setStaffLevel(new Staff(0, staff));
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

    private void buildLabelField(JPanel p, String label, JTextField textField) {
        JLabel l = new JLabel(label, JLabel.TRAILING);
        p.add(l);
        l.setLabelFor(textField);
        p.add(textField);
    }


    private void columnTextCenter(JTable table) {

        // Column 标题居中
        JTableHeader header = table.getTableHeader();
        DefaultTableCellHeaderRenderer headerRenderer = new DefaultTableCellHeaderRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        header.setDefaultRenderer(headerRenderer);

        // Column 内容居中
        int count = table.getColumnCount();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < count; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public void setUpGenderColumn(JTable table,
                                  TableColumn sportColumn) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("男");
        comboBox.addItem("女");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setToolTipText("change gender");
        sportColumn.setCellRenderer(renderer);
    }

    @Override
    public void loadAllSuccess(List<Employee> list) {
        dataList.clear();
        if (list != null) dataList.addAll(list);
        employeeTableModel.fireTableDataChanged();
    }

    @Override
    public void updateEmployee(Employee employee) {
        presenter.updateEmployee(employee);
    }

    @Override
    public void deleteEmployeeSuccess(Employee employee) {
        dataList.remove(employee);
        employeeTableModel.fireTableDataChanged();
    }
}