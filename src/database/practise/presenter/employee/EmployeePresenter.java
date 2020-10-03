package database.practise.presenter.employee;

import database.practise.bean.Employee;
import database.practise.dao.EmployeeDaoImpl;
import database.practise.dao.IDao;

import javax.swing.*;
import java.util.List;

public class EmployeePresenter implements IEmployeeContract.Presenter {

    private IEmployeeContract.View view;

    public EmployeePresenter(IEmployeeContract.View view) {
        this.view = view;
    }

    @Override
    public void loadAll() {
        new SwingWorker<Void, Employee>() {
            @Override
            protected Void doInBackground() {
                IDao<Employee> employeeIDao = new EmployeeDaoImpl();
                List<Employee> list = employeeIDao.getAll();
                for (Employee employee : list) {
                    publish(employee);
                }
                return null;
            }

            @Override
            protected void process(List<Employee> chunks) {
                super.process(chunks);
                if (view == null) return;
                view.loadAllSuccess(chunks);
            }

        }.execute();

    }
}
