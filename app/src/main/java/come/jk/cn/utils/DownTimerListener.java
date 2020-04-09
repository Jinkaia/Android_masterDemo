package come.jk.cn.utils;

/**
 * Created by Master on 2019/1/14.
 */

public interface DownTimerListener {
    /**
     * [倒计时每秒方法]<BR>
     * @param millisUntilFinished
     */
    void onTick(long millisUntilFinished);

    /**
     * [倒计时完成方法]<BR>
     */
    void onFinish();

}


