package database.practise.presenter.stafflevel;

import database.practise.bean.StaffLevel;
import database.practise.dao.StaffLevelDaoImpl;
import database.practise.dao.IDao;
import javax.swing.*;
import java.util.List;

public class StaffLevelPresenter implements IStaffLevelContract.Presenter {

    private IStaffLevelContract.View view;

    private IDao<StaffLevel> staffLevelDao;

    public StaffLevelPresenter(IStaffLevelContract.View view) {
        this.view = view;
        staffLevelDao = new StaffLevelDaoImpl();
    }

    @Override
    public void getList(StaffLevel department) {
        new SwingWorker<List<StaffLevel>, Void>() {
            @Override
            protected List<StaffLevel> doInBackground() throws Exception {
                return staffLevelDao.query(department);
            }

            @Override
            protected void done() {
                super.done();
                if (view == null) return;
                try {
                    List<StaffLevel> list = get();
                    view.getListSuccess(list);
                } catch (Exception e) {
                    e.printStackTrace();
                    view.showMessageDialog(e);
                }

            }

        }.execute();

    }

    @Override
    public void update(StaffLevel department) {
        new SwingWorker<Integer, Integer>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return staffLevelDao.update(department);
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
    public void delete(StaffLevel department) {
        new SwingWorker<Integer, Integer>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return staffLevelDao.remove(department.getId());

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
    public void add(StaffLevel department) {
        new SwingWorker<Integer, Integer>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return staffLevelDao.add(department);
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
