package database.practise.base;

import database.practise.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLSyntaxErrorException;

public class BasePanel extends JPanel implements BaseView {

    @Override
    public void showMessageDialog(String msg) {
        DialogUtils.showPlainMessage(this, msg);
    }

    @Override
    public void showMessageDialog(Exception e) {
        showMessageDialog(parseException(e));
    }

    @Override
    public String parseException(Exception e) {
        Throwable cause = e;
        if (e.getCause() != null) cause = e.getCause();
        if (cause instanceof SQLSyntaxErrorException) {
            return "SQL 语法错误 ~";
        }
        return e.getClass().getName();
    }

}
