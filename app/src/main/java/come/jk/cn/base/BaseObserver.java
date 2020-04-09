package come.jk.cn.base;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import come.jk.cn.utils.NetWorkUtil;
import come.jk.cn.utils.ToastUtil;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Master on 2019/4/13.
 */

public abstract class BaseObserver<T> extends DisposableObserver<BaseEntity<T>> {
    private static final String TAG = "BaseObserver";
    private Context mContext;


    @Override
    public void onNext(BaseEntity<T> value) {
        if (value.isSuccess()) {
            T t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getCode(), value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        String message = "";
        Log.i("===", "===okhttp--Throwable===" + e.toString());
        if (!NetWorkUtil.isNetworkAvailable()) {
            message = "请检查您的网络";
        } else {
            if (e instanceof ConnectException) {
                message = "网络不佳";
            } else if (e instanceof SocketException || e instanceof SocketTimeoutException) {
                message = "连接超时";
            } else {
                message = "好像出问题了";
            }
        }
        onHandleError(-1001, message);

    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        onHandleComplete();
    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleComplete() {

    }

    protected void onHandleError(int code, String msg) {
        ToastUtil.show(msg);
        switch (code) {
            case -1:
                //未登录
//                MyApplication.getContext().startActivity(new Intent(MyApplication.getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;


        }
    }
}
