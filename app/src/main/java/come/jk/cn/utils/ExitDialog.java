package come.jk.cn.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import come.jk.cn.R;


public class ExitDialog extends Dialog {
    private Context mContext;
    private TextView mConfirm;
    private TextView mCancel;

    public ExitDialog(Context context) {
        super(context, R.style.ExitDialog);
        mContext = context;
    }

    public ExitDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_exit);
        //设置为我们的布局
        this.setCanceledOnTouchOutside(false);
        //设置为点击对话框之外的区域对话框不消失
        mConfirm =  findViewById(R.id.tv_usercontent);
        mCancel =  findViewById(R.id.tv_userfinish);
        //设置事件
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExitDialog.this.dismiss();
            }
        });

    }
}