package database.practise.presenter.employee;

import database.practise.base.BaseView;
import database.practise.bean.Employee;

import java.util.List;


public interface IEmployeeContract {

    interface Presenter {
        void getList(Employee employee);

        void updateEmployee(Employee employee);

        void deleteEmployee(Employee employee);
    }


    interface View extends BaseView {
        void loadAllSuccess(List<Employee> list);

        void deleteEmployeeSuccess(Employee employee);

    }

}
