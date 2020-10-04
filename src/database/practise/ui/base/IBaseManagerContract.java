package database.practise.ui.base;

import java.util.List;


public interface IBaseManagerContract {

    interface Presenter<T> {
        void getList(T t);

        void update(T t);

        void delete(T t);

        void add(T t);
    }


    interface View<T> extends BaseView {
        void getListSuccess(List<T> list);
        void deleteSuccess(T t);
        void addSuccess();

    }

}
