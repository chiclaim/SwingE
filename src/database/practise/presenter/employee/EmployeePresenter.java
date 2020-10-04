package database.practise.presenter.employee;

import database.practise.bean.Employee;
import database.practise.dao.EmployeeDaoImpl;
import database.practise.dao.IDao;

import javax.swing.*;
import java.util.List;

public class EmployeePresenter implements IEmployeeContract.Presenter {

    private IEmployeeContract.View view;

    private IDao<Employee> employeeIDao;

    public EmployeePresenter(IEmployeeContract.View view) {
        this.view = view;
        employeeIDao = new EmployeeDaoImpl();
    }

    @Override
    public void getList(Employee employee) {
        new SwingWorker<Void, Employee>() {
            @Override
            protected Void doInBackground() {
                List<Employee> list = employeeIDao.query(employee);
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

    @Override
    public void updateEmployee(Employee employee) {
        new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() {
                int row = employeeIDao.update(employee);
                publish(row);
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                super.process(chunks);
            }
        }.execute();

    }

    @Override
    public void deleteEmployee(Employee employee) {
        new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() {
                int row = employeeIDao.remove(employee.getId());
                publish(row);
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                super.process(chunks);
                view.deleteEmployeeSuccess(employee);
            }
        }.execute();

    }
}
