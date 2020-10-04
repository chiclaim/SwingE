package database.practise.presenter.department;

import database.practise.bean.Department;
import database.practise.dao.DepartmentDaoImpl;
import database.practise.dao.IDao;

import javax.swing.*;
import java.util.List;

public class DepartmentPresenter implements IDepartmentContract.Presenter {

    private IDepartmentContract.View view;

    private IDao<Department> departmentDao;

    public DepartmentPresenter(IDepartmentContract.View view) {
        this.view = view;
        departmentDao = new DepartmentDaoImpl();
    }

    @Override
    public void getList(Department department) {
        new SwingWorker<List<Department>, Void>() {
            @Override
            protected List<Department> doInBackground() throws Exception {
                return departmentDao.query(department);
            }

            @Override
            protected void done() {
                super.done();
                if (view == null) return;
                try {
                    List<Department> list = get();
                    view.getListSuccess(list);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessageDialog(e);
                }

            }

        }.execute();

    }

    @Override
    public void update(Department department) {
        new SwingWorker<Integer, Integer>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return departmentDao.update(department);
            }

            @Override
            protected void done() {
                super.done();
                try {
                    get();
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessageDialog(e);
                }
            }
        }.execute();
    }

    @Override
    public void delete(Department department) {
        new SwingWorker<Integer, Integer>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return departmentDao.remove(department.getId());

            }

            @Override
            protected void done() {
                super.done();
                try {
                    if (get() > 0) view.deleteSuccess(department);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessageDialog(e);
                }
            }
        }.execute();

    }

    @Override
    public void add(Department department) {
        new SwingWorker<Integer, Integer>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return departmentDao.add(department);
            }

            @Override
            protected void done() {
                super.done();
                try {
                    if (get() > 0) view.addSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessageDialog(e);
                }
            }
        }.execute();

    }
}
