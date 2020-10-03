package database.practise.ui;

import database.practise.bean.Employee;
import database.practise.presenter.employee.EmployeePresenter;
import database.practise.presenter.employee.IEmployeeContract;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerTab extends JPanel implements IEmployeeContract.View, EmployeeTableModelCallback {

    private List<Employee> dataList = new ArrayList<>();
    private EmployeeTableModel employeeTableModel;
    private IEmployeeContract.Presenter presenter;

    public EmployeeManagerTab() {
        super(new GridLayout(1, 0));

        presenter = new EmployeePresenter(this);
        employeeTableModel = new EmployeeTableModel(dataList, this);

        JTable table = new JTable(employeeTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        // 文字居中展示
        columnTextCenter(table);
        // 男、女选项
        setUpGenderColumn(table, table.getColumnModel().getColumn(4));

        add(scrollPane);

        presenter.loadAll();
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
        dataList.addAll(list);
        employeeTableModel.fireTableDataChanged();
    }

    @Override
    public void updateEmployee(Employee employee) {
        presenter.updateEmployee(employee);
    }
}