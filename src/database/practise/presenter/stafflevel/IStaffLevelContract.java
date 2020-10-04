package database.practise.presenter.stafflevel;

import database.practise.bean.StaffLevel;
import database.practise.ui.base.IBaseManagerContract;


public interface IStaffLevelContract {

    interface Presenter extends IBaseManagerContract.Presenter<StaffLevel> {
    }


    interface View extends IBaseManagerContract.View<StaffLevel> {

    }

}
