package come.jk.cn.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import come.jk.cn.R;
import come.jk.cn.app.MyAppLication;

public class MyDailog extends Dialog {

    private static TextView userFinish;

    public MyDailog(Context context) {
        super(context);
    }


    public void getDidlog(String temp) {

        Dialog mDialog = new Dialog(MyAppLication.getContext(), R.style.MyDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = DisplayUtils.getScreenWidth(MyAppLication.getContext());

        dialogWindow.setWindowAnimations(R.style.bottomEnterAnim);
        LayoutInflater inflater = (LayoutInflater) MyAppLication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.standard_dialog, null);
        //填充布局
        mDialog.setContentView(view, lp);
        mDialog.show();

        TextView userContent = view.findViewById(R.id.tv_usercontent);
        userContent.setText(temp);
        userFinish = view.findViewById(R.id.tv_userfinish);
        userFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDailog.this.dismiss();
            }
        });


    }


}
