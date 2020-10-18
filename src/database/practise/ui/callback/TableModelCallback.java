package database.practise.ui.callback;

import database.practise.ui.base.BaseView;

public interface TableModelCallback<T> extends BaseView{

    void onUpdate(T t);

}
