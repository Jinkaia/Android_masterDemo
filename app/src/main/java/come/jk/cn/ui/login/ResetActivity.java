package come.jk.cn.ui.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import come.jk.cn.R;
import come.jk.cn.base.BaseActivity;
import come.jk.cn.utils.InputTextHelper;

public class ResetActivity extends BaseActivity {


    @BindView(R.id.retrievepass_cancle)
    TextView retrievepassCancle;
    @BindView(R.id.text_forget)
    TextView textForget;
    @BindView(R.id.reset_aty_phone_et)
    EditText resetAtyPhoneEt;
    @BindView(R.id.reset_aty_phone_et_clear)
    ImageView resetAtyPhoneEtClear;
    @BindView(R.id.resetpass_aty_rp_et)
    EditText resetpassAtyRpEt;
    @BindView(R.id.resetpass_aty)
    ImageView resetpassAty;
    @BindView(R.id.reset_aty_finishbtn)
    Button resetAtyFinishbtn;
    private boolean isShowPwd = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        new InputTextHelper.Builder(this)
                .setMain(resetAtyFinishbtn)
                .addView(resetAtyPhoneEt)
                .addView(resetpassAtyRpEt)
                .build();
    }

    @Override
    protected void initListener() {
        initClearEditTextListener();
    }

    private void initClearEditTextListener() {


    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.reset_aty_phone_et_clear, R.id.resetpass_aty, R.id.retrievepass_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reset_aty_phone_et_clear:
                if (isShowPwd) {
                    resetAtyPhoneEtClear.setImageResource(R.mipmap.yanjing_hui);
                    resetAtyPhoneEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    resetAtyPhoneEt.setSelection(resetAtyPhoneEt.getText().toString().length());
                } else {
                    resetAtyPhoneEtClear.setImageResource(R.mipmap.yanjing);
                    resetAtyPhoneEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    resetAtyPhoneEt.setSelection(resetAtyPhoneEt.getText().toString().length());
                }


                break;
            case R.id.resetpass_aty:
                break;
            case R.id.retrievepass_cancle:
                finish();
                break;
        }
    }
}
