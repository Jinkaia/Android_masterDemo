package come.jk.cn.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import come.jk.cn.app.MyAppLication;

/**
 * Created by Master on 2018/12/27.
 */

public class ToastUtil {
    private static Toast sToast;

    public static void show(final String text) {
        MyAppLication.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = Toast.makeText(MyAppLication.getContext(), text, Toast.LENGTH_SHORT);
                    View view = sToast.getView();
//                    view.setBackgroundResource(R.drawable.shape_corner_black_4);
                    view.setPadding(30,10,30,10);
                }
                sToast.setText(text);
                sToast.show();
            }
        });
    }

    public static void showAtCenter(final String text) {
        MyAppLication.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = Toast.makeText(MyAppLication.getContext(), text, Toast.LENGTH_SHORT);
                    sToast.setGravity(Gravity.CENTER, 0, 0);
                    View view = sToast.getView();
//                    view.setBackgroundResource(R.drawable.shape_corner_black_4);
                    view.setPadding(30,10,30,10);
                }
                sToast.setText(text);
                sToast.show();
            }
        });

    }
}
