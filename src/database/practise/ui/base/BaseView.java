package database.practise.ui.base;

public interface BaseView {

    void showMessageDialog(Exception e);

    void showMessageDialog(String msg);

    String parseException(Exception e);
}
