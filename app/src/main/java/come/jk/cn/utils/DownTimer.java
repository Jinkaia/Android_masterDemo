package come.jk.cn.utils;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by Master on 2019/1/14.
 */

/**
 * 倒计时相关类
 */
public class DownTimer {
    private final String TAG = DownTimer.class.getSimpleName();
    private CountDownTimer mCountDownTimer;
    private DownTimerListener listener;
    private boolean isDwoning = false;


    /**
     * [开始倒计时功能]<BR>
     * [倒计为time长的时间，时间间隔为每秒]
     *
     * @param time
     */
    public void startDown(long time) {
        startDown(time, 1000);
    }

    /**
     * [倒计为time长的时间，时间间隔为mills]
     *
     * @param time
     * @param mills
     */
    public void startDown(long time, long mills) {
        mCountDownTimer = new CountDownTimer(time, mills) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (listener != null) {
                    listener.onTick(millisUntilFinished);
                } else {
                    Log.e(TAG, "DownTimerListener 监听不能为空");
                }
                isDwoning = true;
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onFinish();
                } else {
                    Log.e(TAG, "DownTimerListener 监听不能为空");
                }
                if (mCountDownTimer != null) mCountDownTimer.cancel();
                isDwoning = false;
            }

        }.start();
    }

    /**
     * [停止倒计时功能]<BR>
     */
    public void stopDown() {
        if (mCountDownTimer != null) mCountDownTimer.cancel();
    }

    /**
     * [设置倒计时监听]<BR>
     *
     * @param listener
     */
    public void setListener(DownTimerListener listener) {
        this.listener = listener;
    }

    public boolean isDwoning() {
        return isDwoning;
    }

    public void setDwoning(boolean dwoning) {
        isDwoning = dwoning;
    }

}
