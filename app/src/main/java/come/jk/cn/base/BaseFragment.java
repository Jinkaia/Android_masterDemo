package come.jk.cn.base;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import come.jk.cn.R;
import come.jk.cn.app.MyAppLication;
import come.jk.cn.utils.ViewUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {
    protected Bundle params;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        return inflater.inflate(getlayoutId(), container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        init();
//        loadData();
//    }
//    protected abstract int getlayoutId();
//    //统一管理数据的初始化
//    protected abstract void init();
//
//    /**
//     * 统一做数据的初始化
//     */
//    protected abstract void loadData();
//
//    /**
//     *  当fragment隐藏、显示时该方法调用
//     *  hidden 当fragment被隐藏时为true
//     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
            onHide();
        else
            onShow();
    }

    protected abstract void onHide();
    protected abstract void onShow();
//
    public Bundle getParams() {
        return params;
    }
    public void setParams(Bundle params) {
        this.params = params;
    }
//
//
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    //todo
    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    private LayoutInflater inflater;
    private RelativeLayout rootView;
    private View mContentView;
    protected LinearLayout mLoadView = null;

    private AlertDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        if (rootView == null) {
            rootView = new RelativeLayout(this.getActivity());
            mContentView = initLayout();
            rootView.addView(mContentView);
            showContentView();
            this.initView();
            this.initPresenter();
        }
        ViewUtil.removeSelfFromParent(rootView);
        //TODO
        StatusBarUtil.setColor(getActivity(),getResources().getColor(R.color.colorblue));
        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    /**
     * 显示内容View
     */
    public void showContentView() {
        if (rootView.getChildCount() > 0) {
            if (mContentView == rootView.getChildAt(0)) {
                return;
            }
        }
        rootView.removeAllViews();
        ViewUtil.removeSelfFromParent(mContentView);
        rootView.addView(mContentView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 初始化加载View
     */
    public void initLoadView() {
        mLoadView = (LinearLayout) inflater.inflate(R.layout.view_loading_dialog, null);
        mLoadView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 显示加载View
     */
    public void showLoadView() {
        if (rootView.getChildCount() > 0) {
            if (mLoadView == rootView.getChildAt(0)) {
                return;
            }
        }

//        rootView.removeAllViews();
        if (mLoadView == null) {
            initLoadView();
        }
        ViewUtil.removeSelfFromParent(mLoadView);
        rootView.addView(mLoadView);
    }

    /**
     * 显示空页面
     */
    public void showEmptyView(View view){
        if (rootView.getChildCount() > 0) {
            if (view == rootView.getChildAt(0)) {
                return;
            }
        }

        rootView.removeAllViews();
        ViewUtil.removeSelfFromParent(view);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.addView(view);
    }

    /**
     * 显示空View
     */
    private View mEmptyView;
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



    /**
     * 显示加载等待Dialog
     */
    public void showLoadingDialog(FragmentActivity activity) {
        showLoadingDialog(activity,"加载中...");
    }

    /**
     * 显示加载等待Dialog
     *
     * @param loadingStr
     */
    public void showLoadingDialog(FragmentActivity activity,String loadingStr) {
        dismissDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialog);
        loadingDialog = builder.create();
        loadingDialog.show();
        LayoutInflater inflater = LayoutInflater.from(MyAppLication.getContext());
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
        layoutParams.height = activity.getWindowManager().getDefaultDisplay().getWidth() * 1 / 3;
        layoutParams.width = activity.getWindowManager().getDefaultDisplay().getWidth() * 1 / 3;
        dialogWindow.setAttributes(layoutParams);
        loadingDialog.show();

    }

    /**
     * 隐藏加载等待Dialog
     */
    public void dismissDialog() {
        if (loadingDialog != null) {
            try {
                loadingDialog.dismiss();
                loadingDialog = null;

            } catch (Exception e) {
            }
        }
    }


    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    //获取布局文件
    public abstract View initLayout();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView();

}
