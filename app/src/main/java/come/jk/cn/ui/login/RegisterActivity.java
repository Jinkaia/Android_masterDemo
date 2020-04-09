package come.jk.cn.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import come.jk.cn.model.callbackview.UserinfoView;
import come.jk.cn.model.entity.LoginRegsterSucessModel;
import come.jk.cn.presenter.UserPresenter;
import come.jk.cn.utils.InputTextHelper;
import come.jk.cn.utils.PhoneUtils;
import come.jk.cn.utils.ToastUtil;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, UserinfoView {


    @BindView(R.id.edi_quhao)
    EditText ediQuhao;
    @BindView(R.id.view)
    TextView view;
    private TextView retrievepass_cancle;
    private Button btn_aty_register;
    private UserPresenter userPresenter;
    private EditText regis_name;
    private ImageView regis_aty_name_et_clear;
    private EditText regis_phone;
    private EditText edi_code;
    private EditText register_aty_pass_et;
    private ImageView regis_aty_phone_et_clear;
    private ImageView regis_aty_code_et_clear;
    private ImageView regis_aty_pass_et_clear;
    private boolean isShowPwd = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initPresenter() {
        userPresenter = new UserPresenter(this);
    }

    @Override
    protected void initView() {
        retrievepass_cancle = findViewById(R.id.retrievepass_cancle);
        retrievepass_cancle.setOnClickListener(this);

        regis_aty_name_et_clear = findViewById(R.id.regis_aty_name_et_clear);
        regis_aty_name_et_clear.setOnClickListener(this);

        regis_aty_phone_et_clear = findViewById(R.id.regis_aty_phone_et_clear);
        regis_aty_phone_et_clear.setOnClickListener(this);

        regis_aty_code_et_clear = findViewById(R.id.regis_aty_code_et_clear);
        regis_aty_code_et_clear.setOnClickListener(this);

        regis_aty_pass_et_clear = findViewById(R.id.regis_aty_pass_et_clear);
        regis_aty_pass_et_clear.setOnClickListener(this);

        regis_name = findViewById(R.id.regis_name);
        regis_phone = findViewById(R.id.regis_phone);
        edi_code = findViewById(R.id.edi_code);
        register_aty_pass_et = findViewById(R.id.register_aty_pass_et);

        btn_aty_register = findViewById(R.id.btn_aty_register);
        btn_aty_register.setOnClickListener(this);

        new InputTextHelper.Builder(this)
                .setMain(btn_aty_register)
                .addView(regis_name)
                .addView(regis_phone)
                .addView(edi_code)
                .addView(register_aty_pass_et)
                .build();

    }

    @Override
    protected void initListener() {
        initClearEditTextListener();
    }

    private void initClearEditTextListener() {
        regis_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String rgs_Name = s.toString();
                if (TextUtils.isEmpty(rgs_Name)) {
                    regis_aty_name_et_clear.setVisibility(View.INVISIBLE);
                } else {
                    regis_aty_name_et_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        regis_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String rgs_phone = s.toString();
                if (TextUtils.isEmpty(rgs_phone)) {
                    regis_aty_phone_et_clear.setVisibility(View.INVISIBLE);
                } else {
                    regis_aty_phone_et_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        regis_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view.setBackgroundColor(Color.parseColor("#368AE8"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#939393"));
                }
            }
        });

        ediQuhao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //获取焦点
                if (hasFocus) {
                    view.setBackgroundColor(Color.parseColor("#368AE8"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#939393"));
                }
            }
        });

        ediQuhao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String edi_code = s.toString().trim();
                if (TextUtils.isEmpty(edi_code)) {
                    regis_aty_code_et_clear.setVisibility(View.INVISIBLE);
                } else {
                    regis_aty_code_et_clear.setVisibility(View.VISIBLE);
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
                finish();
                break;

            case R.id.regis_aty_name_et_clear:
                regis_name.setText("");
                break;
            case R.id.regis_aty_phone_et_clear:
                regis_phone.setText("");
                break;
            case R.id.regis_aty_code_et_clear:
                edi_code.setText("");
                break;

            case R.id.regis_aty_pass_et_clear:
                if (isShowPwd) {
                    regis_aty_pass_et_clear.setImageResource(R.mipmap.yanjing_hui);
                    register_aty_pass_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    register_aty_pass_et.setSelection(register_aty_pass_et.getText().toString().length());
                } else {
                    regis_aty_pass_et_clear.setImageResource(R.mipmap.yanjing);
                    register_aty_pass_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    register_aty_pass_et.setSelection(register_aty_pass_et.getText().toString().length());
                }
                isShowPwd = !isShowPwd;


                break;
            case R.id.btn_aty_register:

                String rgs_Name = regis_name.getText().toString().trim();
                if (TextUtils.isEmpty(rgs_Name)) {
                    ToastUtil.show("请输入用户名");
                }

                String rgs_Phone = regis_phone.getText().toString().trim();
                if (TextUtils.isEmpty(rgs_Phone)) {
                    ToastUtil.show("请输入账号");
                }
                if (!PhoneUtils.judgePhoneNums(rgs_Phone)) {
                    return;
                }
                String code_Text = edi_code.getText().toString().trim();
                if (TextUtils.isEmpty(code_Text)) {
                    ToastUtil.show("请输入验证码");
                }

                if (code_Text.length() < 4 || code_Text.length() > 6) {
                    ToastUtil.show("请输入4至6位验证码");
                }

                String pwd_Text = register_aty_pass_et.getText().toString().trim();
                if (TextUtils.isEmpty(pwd_Text)) {
                    ToastUtil.show("请输入密码");
                }

                if (pwd_Text.length() < 6) {
                    ToastUtil.show("请输入六位数以上密码");
                }

                showLoadingDialog();
//                userPresenter.userRegister(rgs_Phone, code_Text);

                break;

        }
    }


    @Override
    public void getUserInfoSuccess(LoginRegsterSucessModel loginRegsterSucessModel) {
        dismissDialog();
    }

    @Override
    public void getUserInfoError() {
        dismissDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.edi_code, R.id.edi_quhao})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.edi_code:
                break;
            case R.id.edi_quhao:

                break;
            default:
                break;
        }
    }


}
