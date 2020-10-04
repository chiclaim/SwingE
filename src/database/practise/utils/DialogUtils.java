package database.practise.utils;

import javax.swing.*;
import java.awt.*;

public class DialogUtils {
    private DialogUtils(){}

    public static void showPlainMessage(Component c,String message){
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(c);
        JOptionPane.showMessageDialog(topFrame,
                message,
                "提示",
                JOptionPane.PLAIN_MESSAGE);
    }
}
