package come.jk.cn.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import come.jk.cn.R;
import come.jk.cn.base.BaseActivity;
import come.jk.cn.model.entity.LoginRegsterSucessModel;
import come.jk.cn.presenter.UserPresenter;
import come.jk.cn.model.callbackview.UserinfoView;
import come.jk.cn.ui.MainTabActivity;
import come.jk.cn.utils.InputTextHelper;
import come.jk.cn.utils.PhoneUtils;
import come.jk.cn.utils.ToastUtil;

public class LoginActivity extends BaseActivity implements UserinfoView, View.OnClickListener {


    private UserPresenter userPresenter;
    private TextView login_aty_findpass_tv;
    private Button login_aty_loginbtn;
    private LoginRegsterSucessModel loginRegsterSucessModel;
    private EditText login_aty_phone_et;
    private EditText login_aty_pass_et;
    private LinearLayout linearlayout;

    private String login_account;
    private String login_pwd;
    public final static String MOBILE_PHONE_PATTERN = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[0|3|6|7|8]))\\d{8}$";


    private final int pwdMinLength = 6;
    private final int pwdMaxLength = 16;
    private boolean loginByPwd = true;
    private ImageView login_aty_pass_et_clear;
    private boolean isShowPwd = false;
    private ImageView login_aty_phone_et_clear;
    private TextView login_aty_register_tv;
    private CheckBox checkout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenter() {
        userPresenter = new UserPresenter(this);
    }


    @Override
    protected void initView() {
        login_aty_findpass_tv = findViewById(R.id.login_aty_findpass_tv);
        login_aty_loginbtn = findViewById(R.id.login_aty_loginbtn);
        login_aty_phone_et = findViewById(R.id.login_aty_phone_et);
        login_aty_pass_et = findViewById(R.id.login_aty_pass_et);
        linearlayout = findViewById(R.id.linearlayout);

        login_aty_register_tv = findViewById(R.id.login_aty_register_tv);
        login_aty_register_tv.setOnClickListener(this);

        login_aty_pass_et_clear = findViewById(R.id.login_aty_pass_et_clear);
        login_aty_pass_et_clear.setOnClickListener(this);

        login_aty_phone_et_clear = findViewById(R.id.login_aty_phone_et_clear);
        login_aty_phone_et_clear.setOnClickListener(this);

        login_account = login_aty_phone_et.getText().toString().trim();
        login_pwd = login_aty_pass_et.getText().toString().trim();


        checkout = findViewById(R.id.checkbox);
        login_aty_findpass_tv.setOnClickListener(this);
        login_aty_loginbtn.setOnClickListener(this);
//        setupUI(linearlayout);

        new InputTextHelper.Builder(this)
                .setMain(login_aty_loginbtn)
                .addView(login_aty_phone_et)
                .addView(login_aty_pass_et)
                .build();


        checkout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    login_aty_loginbtn.setEnabled(true);
                }else {
                    login_aty_loginbtn.setEnabled(false);

                }
            }
        });


    }

    @Override
    protected void initListener() {
        initClearEditTextListener();

    }

    private void initClearEditTextListener() {

        //标题栏输入号码后 最后面关闭按钮显示
        login_aty_phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login_account = s.toString();
                if (TextUtils.isEmpty(login_account)) {
                    login_aty_phone_et_clear.setVisibility(View.INVISIBLE);
                } else {
                    login_aty_phone_et_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private boolean isInputValid(boolean isShowToast) {

        if (TextUtils.isEmpty(login_account)) {
            if (isShowToast) {
                ToastUtil.showAtCenter("用户名不能为空");
            }
            return false;
        }

        //todo
        //password.length() < 6   //true
        if (TextUtils.isEmpty(login_pwd) || login_pwd.length() <= 6) {
            if (isShowToast) {
                Toast.makeText(this, R.string.rce_login_password_invalid, Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }


    /**
     *      是否手机号
     * @param phoneNumber
     * @return
     */


    //Todo Editext光标消失
//  login_aty_phone_et.setCursorVisible(false);

    /**
     * 软键盘点击外部消失
     *
     * @param view view为最外层布局
     */
    public void setupUI(View view) {
        if (!(view instanceof EditText)) view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
//                hideSoftKeyboard(LoginActivity.this);
                return false;
            }
        });
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void getUserInfoSuccess(LoginRegsterSucessModel loginRegsterSucessModel) {
        this.loginRegsterSucessModel = loginRegsterSucessModel;
        //登陆成功后进入绑定手机号页面
        showLoadingDialog();
        Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
        startActivity(intent);

    }

    @Override
    public void getUserInfoError() {
        dismissDialog();
        ToastUtil.show("登陆失败");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_aty_findpass_tv:
                startActivity(new Intent(LoginActivity.this, ForgetpwdActivity.class));
//                finish();
                break;
            case R.id.login_aty_phone_et_clear:
                login_aty_phone_et.setText("");
                break;

            case R.id.login_aty_pass_et_clear:
                if (isShowPwd) {
                    //用密码判断 password 跟Hide
                    login_aty_pass_et_clear.setImageResource(R.mipmap.yanjing_hui);
                    login_aty_pass_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    login_aty_pass_et.setSelection(login_aty_pass_et.getText().toString().length());
                } else {
                    login_aty_pass_et_clear.setImageResource(R.mipmap.yanjing);
                    login_aty_pass_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_aty_pass_et.setSelection(login_aty_pass_et.getText().toString().length());
                }

                isShowPwd = !isShowPwd;
                break;

            case R.id.login_aty_loginbtn:

//                showLoadingDialog();
                //判断手机号
                if (!PhoneUtils.judgePhoneNums(login_account)) {
                    return;
                }

//                if (isMobileNumber(login_account)){
//                    return;
//                }


                //密码登录
                showLoadingDialog();

                userPresenter.userLoginBypwd(login_account, login_pwd);

                    break;
            case R.id.login_aty_register_tv:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
                default:
                }


        }


    public static boolean isMobileNumber(String phoneNumber) {
        Pattern p = Pattern.compile(MOBILE_PHONE_PATTERN);
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }
}
