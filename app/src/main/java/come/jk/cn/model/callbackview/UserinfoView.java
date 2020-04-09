package come.jk.cn.model.callbackview;

import come.jk.cn.model.entity.LoginRegsterSucessModel;

/**
 * Created by Master on 2019/4/13.
 */

public interface UserinfoView {
    void getUserInfoSuccess(LoginRegsterSucessModel loginRegsterSucessModel);
    void getUserInfoError();
}
