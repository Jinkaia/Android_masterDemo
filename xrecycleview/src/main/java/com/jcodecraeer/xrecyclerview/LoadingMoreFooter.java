package com.jcodecraeer.xrecyclerview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;

public class LoadingMoreFooter extends RelativeLayout {

    private SimpleViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;
    private Context context;
    private ImageView mLoadingImageView;
    private AnimationDrawable animationDrawable;
    private View loadingView;
    private View loadingDescView;

    public LoadingMoreFooter(Context context) {
		super(context);
        this.context = context;
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
        this.context = context;
		initView();
	}

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    public void initView(){
        setGravity(Gravity.CENTER);
//        setLayoutParams(new RecyclerView.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        progressCon = new SimpleViewSwitcher(getContext());
//        progressCon.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
//        progressView.setIndicatorColor(0xffB5B5B5);
//        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
//        progressCon.setView(progressView);
//         addView(progressCon);


        setLayoutParams(new RecyclerView.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        loadingView = View.inflate(context, R.layout.footer_loading, null);
        mLoadingImageView = loadingView.findViewById(R.id.iv_loadingview);
        animationDrawable = (AnimationDrawable) mLoadingImageView.getDrawable();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingView.setLayoutParams(layoutParams);
        addView(loadingView);


//        mText = new TextView(getContext());
//        mText.setBackgroundColor(getContext().getResources().getColor(R.color.white));
//        mText.setPadding(8,8,8,8);
//        mText.setTextColor(Color.parseColor("#696969"));
//        mText.setText("正在加载...");
        loadingHint = (String)getContext().getText(R.string.listview_loading);
        noMoreHint = (String)getContext().getText(R.string.nomore_loading);
        loadingDoneHint = (String)getContext().getText(R.string.loading_done);
//        LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////        layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

//        mText.setLayoutParams(layoutParams2);

        loadingDescView = View.inflate(context, R.layout.footer_nomore, null);
        mText = loadingDescView.findViewById(R.id.tv_nomore);
        addView(loadingDescView);

    }

    public void setProgressStyle(int style) {
        if(style == ProgressStyle.SysProgress){
            progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        }else{
            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }
    }

    /**
     * 从父亲布局中移除自己
     *
     * @param v
     */
    public static void removeSelfFromParent(View v) {
        ViewParent parent = v.getParent();
        if (parent != null) {
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(v);
            }
        }
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
//                progressCon.setVisibility(View.VISIBLE);
//                mText.setText(loadingHint);
                loadingView.setVisibility(VISIBLE);
                animationDrawable.start();
                loadingDescView.setVisibility(GONE);
                this.setVisibility(View.VISIBLE);
                    break;
            case STATE_COMPLETE:
//                mText.setText(loadingDoneHint);
                animationDrawable.stop();
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                loadingDescView.setVisibility(VISIBLE);
                mText.setText(noMoreHint);
//                progressCon.setVisibility(View.GONE);
                loadingView.setVisibility(GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }


}
