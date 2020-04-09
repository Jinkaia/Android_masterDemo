package come.jk.cn.presenter;

import come.jk.cn.base.BaseObserver;
import come.jk.cn.base.BasePresenter;
import come.jk.cn.model.entity.LoginRegsterSucessModel;
import come.jk.cn.model.biz.ServiceFactory;
import come.jk.cn.model.callbackview.UserinfoView;


/**
 * Created by Master on 2019/4/13.
 */

public class UserPresenter extends BasePresenter<UserinfoView> {
    public UserPresenter(UserinfoView view) {
        attachView(view);
    }

    /**
     * 密码登陆
     * @param mobile
     * @param password
     */
    public void userLoginBypwd(String mobile,String password){
        addApiSubscribe(ServiceFactory.getUserInfoService().getPhoneLogin(mobile, password), new BaseObserver<LoginRegsterSucessModel>() {
            @Override
            protected void onHandleSuccess(LoginRegsterSucessModel loginRegsterSucessModel) {
                  attachedView.getUserInfoSuccess(loginRegsterSucessModel);
            }

            @Override
            protected void onHandleError(int code, String msg) {
                super.onHandleError(code, msg);
                attachedView.getUserInfoError();
            }
        });
    }
}
