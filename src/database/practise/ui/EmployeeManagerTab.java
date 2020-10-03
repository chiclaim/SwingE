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

public class EmployeeManagerTab extends JPanel implements IEmployeeContract.View {

    private List<Employee> dataList = new ArrayList<>();
    private EmployeeTableModel employeeTableModel;
    private IEmployeeContract.Presenter presenter;

    public EmployeeManagerTab() {
        super(new GridLayout(1, 0));

        presenter = new EmployeePresenter(this);
        employeeTableModel = new EmployeeTableModel(dataList);

        JTable table = new JTable(employeeTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);


        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);


        // 文字居中展示
        columnTextCenter(table);

        //Fiddle with the Sport column's cell editors/renderers.
        setUpGenderColumn(table, table.getColumnModel().getColumn(4));

        //Add the scroll pane to this panel.
        add(scrollPane);

        presenter.loadAll();;
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
        //Set up the editor for the sport cells.
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("男");
        comboBox.addItem("女");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setToolTipText("change gender");
        sportColumn.setCellRenderer(renderer);
    }

    @Override
    public void loadAllSuccess(List<Employee> list) {
        dataList.addAll(list);
        employeeTableModel.fireTableDataChanged();
    }

}