package database.practise.presenter.employee;

import database.practise.bean.Employee;

import java.util.List;


public interface IEmployeeContract {

    interface Presenter {
        void loadAll();
    }



    interface View {
        void loadAllSuccess(List<Employee> list);
    }

}
