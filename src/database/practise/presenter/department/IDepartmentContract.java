package database.practise.presenter.department;

import database.practise.bean.Department;
import database.practise.bean.Employee;
import database.practise.ui.base.IBaseManagerContract;


public interface IDepartmentContract {

    interface Presenter extends IBaseManagerContract.Presenter<Department> {
    }


    interface View extends IBaseManagerContract.View<Department> {

    }

}
