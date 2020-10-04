package database.practise.presenter.employee;

import database.practise.bean.Employee;
import database.practise.ui.base.IBaseManagerContract;


public interface IEmployeeContract {

    interface Presenter extends IBaseManagerContract.Presenter<Employee> {
    }


    interface View extends IBaseManagerContract.View<Employee> {

    }

}
