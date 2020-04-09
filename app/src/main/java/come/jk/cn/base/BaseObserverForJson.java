package come.jk.cn.base;
import come.jk.cn.utils.JsonUtil;
import come.jk.cn.utils.ToastUtil;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Master on 2018/12/27.
 */

public abstract class BaseObserverForJson extends DisposableObserver<String> {
    @Override
    public void onNext(String json) {
        if (JsonUtil.isSuccess(json)) {
            onHandleSuccess(json);
        } else {
            onHandleError(JsonUtil.getFieldValue(json, "status"), JsonUtil.getFieldValue(json, "msg"));
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
    protected abstract void onHandleSuccess(String json);

    protected void onHandleError(String code, String msg) {
        ToastUtil.showAtCenter(msg);
        switch (code) {
            case "-1":
                //未登录
//                MyAppLication.getContext().startActivity(new Intent(MyAppLication.getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }
}
