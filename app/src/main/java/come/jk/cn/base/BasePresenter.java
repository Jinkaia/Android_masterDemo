package come.jk.cn.base;

import come.jk.cn.model.biz.ServiceFactory;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

/**
 * Created by Master on 2018/12/27.
 */

public class BasePresenter<V> {
    protected V attachedView;
    private CompositeDisposable mDisposables;


    public void attachView(V view) {
        attachedView = view;
    }

    public void detachView(V view) {
        attachedView = null;
        clearAllDisposable();
    }

    protected void clearAllDisposable() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    /**
     * 处理返回值为Bean对象接口数据
     * @param observable
     * @param observer
     * @param <T>
     */
    protected <T extends BaseEntity> void addApiSubscribe(Observable<T> observable, DisposableObserver<T> observer) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        observable.compose(ServiceFactory.schedulersTransformer).subscribe(observer);

        mDisposables.add(observer);
    }

    /**
     * 处理返回值json接口数据
     *
     * @param observable
     * @param observer
     */
    protected String addApiSubscribeForJson(Observable<ResponseBody> observable, DisposableObserver<String> observer) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        observable.map(new Function<ResponseBody, String>() {
            @Override
            public String apply(ResponseBody responseBody) throws Exception {
                String string = responseBody.string();
                return string;
            }
        }).compose(ServiceFactory.schedulersTransformer).subscribe(observer);

        mDisposables.add(observer);
        return "";
    }
}
