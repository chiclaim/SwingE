package database.practise.ui.base;

import database.practise.ui.callback.TableModelCallback;
import database.practise.utils.DialogUtils;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理界面基类
 */
public abstract class BaseManagerTab<T> extends BasePanel
        implements IBaseManagerContract.View<T>, TableModelCallback<T> {


    private List<T> dataList = new ArrayList<>();
    private AbstractTableModel tableModel;
    private IBaseManagerContract.Presenter<T> presenter;
    private JTable table;

    public BaseManagerTab() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        presenter = createPresenter();

        tableModel = createTableModel(dataList);

        table = new JTable(tableModel);
        //table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //table.setColumnSelectionAllowed(true);

        initTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        addBottomButton(this);

        //addBottomInput();

        addViews(this);

        presenter.getList(null);
    }

    public void initTable(JTable table){

    }

    public abstract AbstractTableModel createTableModel(List<T> dataList);

    public abstract IBaseManagerContract.Presenter<T> createPresenter();

    public abstract T buildBeanByField();

    public abstract void addViews(JPanel panel);

    protected void resetInput(){

    }

    protected void addBottomButton(JPanel container) {
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
                presenter.add(buildBeanByField());
            }
        });

        // 查询操作
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.getList(buildBeanByField());
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
                    DialogUtils.showPlainMessage(BaseManagerTab.this, "请选中要删除的行");
                    return;
                }
                T t = dataList.get(row);
                presenter.delete(t);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetInput();
            }
        });

        container.add(panel);
    }

    protected void buildLabelField(JPanel p, String label, JTextField textField) {
        JLabel l = new JLabel(label, JLabel.TRAILING);
        p.add(l);
        l.setLabelFor(textField);
        p.add(textField);
    }


    protected void columnTextCenter(JTable table) {

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


    @Override
    public void getListSuccess(List<T> list) {
        dataList.clear();
        if (list != null) dataList.addAll(list);
        tableModel.fireTableDataChanged();
    }

    @Override
    public void onUpdate(T t) {
        presenter.update(t);
    }

    @Override
    public void deleteSuccess(T t) {
        dataList.remove(t);
        tableModel.fireTableDataChanged();
    }

    @Override
    public void addSuccess() {
        presenter.getList(null);
    }
}