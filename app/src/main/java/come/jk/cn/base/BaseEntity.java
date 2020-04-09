package come.jk.cn.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Master on 2018/12/27.
 */

public class BaseEntity<E> {
    //todo 后台返回的数据格式
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String msg;
    @SerializedName("data")
    private E data;

    public boolean isSuccess() {
        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public E getData() {
        return data;
    }
}
