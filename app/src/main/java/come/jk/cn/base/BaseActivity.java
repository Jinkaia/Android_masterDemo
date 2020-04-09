package come.jk.cn.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import come.jk.cn.R;
import come.jk.cn.app.AppManager;
import come.jk.cn.app.MyAppLication;
import come.jk.cn.utils.ViewUtil;
import come.jk.cn.utils.XPermissionUtils;

public abstract class BaseActivity extends AppCompatActivity {

    private BaseFragment lastFragment;
    private AlertDialog loadingDialog;
    private View mEmptyView;
    private LayoutInflater inflater;
    private RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        MyAppLication.context = this;
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        rootView = new RelativeLayout(this);
        rootView.setPadding(0, 0, 0, 0);
        initView();
        initPresenter();

        initListener();
        loadData();
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorblue));
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (startActivitySelfCheck(intent)) {
            hideSoftKeyboard();
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode);
        }
    }

    private String mStartActivityTag;
    private long mStartActivityTime;
    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent          用于跳转的 Intent 对象
     * @return                检查通过返回true, 检查不通过返回false
     */
    protected boolean startActivitySelfCheck(Intent intent) {
        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) {
            // 显式跳转
            tag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) {
            // 隐式跳转
            tag = intent.getAction();
        } else {
            // 其他方式
            return true;
        }

        if (tag.equals(mStartActivityTag) && mStartActivityTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            return false;
        }

        // 记录启动标记和时间
        mStartActivityTag = tag;
        mStartActivityTime = SystemClock.uptimeMillis();
        return result;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initPresenter();

    /**
     * 初始化控件，执行findviewByid()操作
     */
    protected abstract void initView();

    /**
     * 统一处理监听事件
     */
    protected abstract void initListener();

    protected abstract void loadData();

    public void changFragment(int containerId, Class<? extends BaseFragment> fragmentClass, Bundle bundle, boolean isReplace, boolean isBack) {
        //第一步 获取Fragment管理器
        FragmentManager manager = getSupportFragmentManager();
        //第二步 获取Fragment类名 下面会用到类名做Tag
        String fragmentName = fragmentClass.getSimpleName();
        //第三步 开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        //第四步 通过tag值 找fragment
        BaseFragment currentFragment = (BaseFragment) manager.findFragmentByTag(fragmentName);
        //第五步 如果fragment为null 通过java动态代理创建对应的fragment对象
        if (currentFragment == null) {
            try {
                //java动态代理 返回fragment对象 调用该类的无参构造方法
                currentFragment = fragmentClass.newInstance();
                transaction.add(containerId, currentFragment, fragmentName);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (isReplace) {
            transaction.replace(containerId, currentFragment, fragmentName);
        } else {
            if (lastFragment != null) {
                transaction.hide(lastFragment);
                transaction.show(currentFragment);
            }
        }

        if (bundle != null) {
            currentFragment.setParams(bundle);
        }
        if (isBack) {
            transaction.addToBackStack(fragmentName);
        }

        lastFragment = currentFragment;
        transaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MyAppLication.context = this;
    }


    /**
     * 显示加载等待Dialog
     */
    public void showLoadingDialog() {
        showLoadingDialog("加载中...");
    }

    public void showLoadingDialog(String loadingStr) {
        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        loadingDialog = builder.create();
        loadingDialog.show();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.view_loading_dialog, null);
        TextView tipTextview = (TextView) view.findViewById(R.id.tipTextView);
        if (TextUtils.isEmpty(loadingStr)) {
            tipTextview.setVisibility(View.GONE);
        } else {
            tipTextview.setVisibility(View.VISIBLE);
            tipTextview.setText(loadingStr);
        }
        loadingDialog.setContentView(view);
        loadingDialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = loadingDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        android.view.WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.height = getWindowManager().getDefaultDisplay().getWidth() * 1 / 3;
        layoutParams.width = getWindowManager().getDefaultDisplay().getWidth() * 1 / 3;
        dialogWindow.setAttributes(layoutParams);
        loadingDialog.show();
    }

    public void dismissDialog() {
        if (loadingDialog != null) {
            try {
                loadingDialog.dismiss();
                loadingDialog = null;
            } catch (Exception e) {
            }
        }
    }

    /**
     * 空页的相关处理，根据不同的项目增添相关内容
     */
    public static final int EmptyType_NETWORK = 2003;
    public static final int EmptyType_SEARCH = 2005;
    public static final int EmptyType_YIGOU = 2004;
    public static final int EmptyType_ZHUANJIA = 2008;
    public static final int EmptyType_MSG = 2007;

    public void initEmptyView() {
        mEmptyView = inflater.inflate(R.layout.layout_empty, null);
    }

    public void showEmptyView(int type) {
        if (rootView.getChildCount() > 0) {
            if (mEmptyView == rootView.getChildAt(0)) {
                return;
            }
        }
        rootView.removeAllViews();

        if (mEmptyView == null) {
            initEmptyView();
        }
        ImageView emptyImg = mEmptyView.findViewById(R.id.empty_img);
        TextView emptyText = mEmptyView.findViewById(R.id.empty_text);
        switch (type) {
            case EmptyType_NETWORK:
//                emptyImg.setImageResource(R.drawable.empty_wifi);
                emptyText.setText("网络异常，请重新尝试");
                break;
            case EmptyType_YIGOU:
//                emptyImg.setImageResource(R.drawable.empty_yigou);
                emptyText.setText("这里一片荒土啥都没有");
                break;
            case EmptyType_SEARCH:
//                emptyImg.setImageResource(R.drawable.empty_sousuo);
                emptyText.setText("什么都没有搜索到哦...");
                break;
            case EmptyType_ZHUANJIA:
//                emptyImg.setImageResource(R.drawable.empty_zhuanjia);
                emptyText.setText("快去把喜欢的专家添加进来");
                break;
            case EmptyType_MSG:
//                emptyImg.setImageResource(R.drawable.empty_wuxiaoxi);
                emptyText.setText("你还没有收到任何消息");

                break;
        }

        ViewUtil.removeSelfFromParent(mEmptyView);
        rootView.addView(mEmptyView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

//    onRequestPermissionsResult

    /**
     * 权限管理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        XPermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyAppLication.context = this;
        AppManager.getAppManager().finishActivity(this);
    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.open_enter_horizontal, R.anim.close_exit_horizontal);
//    }
}

