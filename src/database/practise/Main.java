package database.practise;

import database.practise.ui.EmployeeManagerTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("职工管理系统");

        JPanel panel = new JPanel(new GridLayout());
        addTabs(panel);
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }


    private static void addTabs(JPanel panel) {

        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = null;//createImageIcon("/images/middle.gif");

        //JComponent panel1 = makeTextPanel("员工管理");
        tabbedPane.addTab("员工管理", icon, new EmployeeManagerTab(),
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = makeTextPanel("部门管理");
        tabbedPane.addTab("部门管理", icon, panel2,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = makeTextPanel("职级管理");
        tabbedPane.addTab("职级管理", icon, panel3,
                "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);


        //Add the tabbed pane to this panel.
        panel.add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

    }
}
