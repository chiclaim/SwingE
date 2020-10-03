package database.practise.presenter.employee;

import database.practise.bean.Employee;

import java.util.List;


public interface IEmployeeContract {

    interface Presenter {
        void loadAll();

        void updateEmployee(Employee employee);
    }


    interface View {
        void loadAllSuccess(List<Employee> list);

    }

}
