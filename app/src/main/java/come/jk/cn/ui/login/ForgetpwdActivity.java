package come.jk.cn.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import come.jk.cn.R;
import come.jk.cn.base.BaseActivity;
import come.jk.cn.utils.InputTextHelper;
import come.jk.cn.utils.ToastUtil;

public class ForgetpwdActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.forgrt_art_phone)
    EditText forgrtArtPhone;
    @BindView(R.id.forget_aty_phone_et_clear)
    ImageView forgetAtyPhoneEtClear;

    @BindView(R.id.edi_code)
    EditText ediCode;
    @BindView(R.id.forget_aty_code_et_clear)
    ImageView forgetAtyCodeEtClear;
    @BindView(R.id.forget_aty_btn)
    Button forgetAtyBtn;
    private TextView retrievepass_cancle;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpwd;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        retrievepass_cancle = findViewById(R.id.retrievepass_cancle);
        retrievepass_cancle.setOnClickListener(this);
        forgetAtyBtn.setOnClickListener(this);
        forgetAtyPhoneEtClear.setOnClickListener(this);
        forgetAtyCodeEtClear.setOnClickListener(this);

        new InputTextHelper.Builder(this)
                .setMain(forgetAtyBtn)
                .addView(forgrtArtPhone)
                .addView(ediCode)
                .build();

    }

    @Override
    protected void initListener() {
        initClearEditTextListener();
    }

    private void initClearEditTextListener() {

        forgrtArtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String forgrt_Phone = s.toString().trim();
                if (TextUtils.isEmpty(forgrt_Phone)) {
                    forgetAtyPhoneEtClear.setVisibility(View.INVISIBLE);
                } else {
                    forgetAtyPhoneEtClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ediCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String edi_code = s.toString().trim();
                if (TextUtils.isEmpty(edi_code)) {
                    forgetAtyCodeEtClear.setVisibility(View.INVISIBLE);
                } else {
                    forgetAtyCodeEtClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retrievepass_cancle:
                this.finish();

                break;
            case R.id.forget_aty_btn:
                if (forgrtArtPhone.getText().toString().trim() != null && ediCode.getText().toString().trim() != null) {
                    Intent intent = new Intent(ForgetpwdActivity.this, ResetActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.show("请输入相关信息");
                }

                break;
            case R.id.forget_aty_phone_et_clear:
                forgrtArtPhone.setText("");
                break;
            case R.id.forget_aty_code_et_clear:
                ediCode.setText("");
                break;
            default:
                break;
        }
    }


}
