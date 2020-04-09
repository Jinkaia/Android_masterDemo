package come.jk.cn.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jinkai on 2018/3/23.
 */

public class MyAppLication extends Application {
    //全局的上下文
    public static Context context;
    private static MyAppLication instance;
    private static Handler mainHandler;

    public boolean isIMConnectSuccess = false;
    private Unbinder bind;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        instance = this;
        mainHandler = new Handler();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          //  closeAndroidPDialog();
        }

    }
    /**
     * 适配P
     */
    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//

    public boolean isIMConnectSuccess() {
        return isIMConnectSuccess;
    }

    public void setIMConnectSuccess(boolean IMConnectSuccess) {
        isIMConnectSuccess = IMConnectSuccess;
    }


    public static MyAppLication getInstance() {
        return instance;
    }

    /**
     * 公共context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取UI线程
     *
     * @return
     */
    public static Handler getMainHandler() {
        return mainHandler;
    }


}
