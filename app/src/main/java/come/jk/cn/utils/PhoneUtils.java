package come.jk.cn.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import come.jk.cn.app.MyAppLication;

/**
 * Created by Master on 2019/4/17.
 */

public class PhoneUtils {
    /**
     * 获取常用手机信息
     *
     * @param context
     * @return
     */
//    public static Map<String, String> getAppInfo(Context context) {
//        Map<String, String> header = new HashMap<String, String>();
//        //获取设备ID
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceId = tm.getDeviceId();
//
//        //获取手机操作系统版本号
//        String appBuild = Build.VERSION.RELEASE;
//
//        //获取手机品牌
//        String brand = Build.BRAND;
//
//        //获取应用版本号
//        PackageInfo pi = null;
//        PackageManager pm = context.getPackageManager();
//        try {
//            pi = pm.getPackageInfo(context.getPackageName(),
//                    PackageManager.GET_CONFIGURATIONS);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String versionCode = String.valueOf(pi.versionCode);
//
//        //获取应用版本名称
//        String versionName = pi.versionName;
//
//        //将各个信息加进集合
//        header.put("deviceId", deviceId);
//        header.put("appVersion", versionName);
//        header.put("appBuild", versionCode);
//        header.put("os", "android");
//        header.put("osVersion", appBuild);
//        header.put("brand", brand);
//
//        //将数据存储起来
//        SPUtils.put(MyAppLication.context,"deviceId", deviceId);
//        SPUtils.put(MyAppLication.context,"appVersion", versionName);
//        SpUtil.putString("appBuild", versionCode);
//        SpUtil.putString("os", "android");
//        SpUtil.putString("osVersion", appBuild);
//        SpUtil.putString("brand", brand);
//        SpUtil.putBoolean("has_header", true);
//
//        return header;
//    }

    /**
     * 判断手机号格式
     *
     * @param phoneNums 手机号
     * @return true 表示手机号格式正确   false 表示手机号格式错误
     */
    public static boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        return false;
    }

    /**
     * 验证手机号的位数
     *
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            ToastUtil.show("请输入手机号码");
            return false;
        } else {
            if (str.length() != length) {
                ToastUtil.show("手机号码格式不正确");
            }
            return str.length() == length ? true : false;

        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、
        // 4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums)) {
            ToastUtil.show("请输入手机号码");
            return false;
        } else {
            if (!mobileNums.matches(telRegex)) {
                ToastUtil.show("手机号码无效");
            }
            return mobileNums.matches(telRegex);
        }
    }

    /**
     * 检测验证码
     *
     * @param code
     * @return
     */
    public static boolean checkCode(String code) {
        if (TextUtils.isEmpty(code)) {
            ToastUtil.show("请输入验证码");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检测密码输入格式
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        String str = "^[\\@A-Za-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,100}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    public static String getUniqueId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        String encode = Md5Util.encode(id);
        return encode;
    }

    public static boolean checkEmail(String emailText) {
        String e = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern compile = Pattern.compile(e);
        Matcher matcher = compile.matcher(emailText);
        boolean matches = matcher.matches();
        return matches;
    }
}
