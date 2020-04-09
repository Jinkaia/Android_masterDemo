package come.jk.cn.model.entity;

/**
 * Created by Master on 2019/4/13.
 */

public class LoginRegsterSucessModel {

    /**
     * nickname : 靳凯
     * mobile : 15296716766public static class DataBean {
     * photo : http://qiniu.5roo.com/1a229ee579f541c998899279c050af81.jpg
     * id : 485
     * token : eyJhbGciOiJIUzUxMiJ9.eyJyYW5kb21LZXkiOiJnODYzeGciLCJzdWIiOiI0ODUiLCJleHAiOjE1MjU3NzQ4ODUsImlhdCI6MTUyNTE3MDA4NX0.t62LvHXk90j9zzdfa-zo79bPu1vERAQUNTY5_fKnbk502CvBNUxRuXyYMh8XpsYB18DUt-LtUYcszecY3x44mA
     */

    private String nickname;
    private String mobile;
    private String photo;
    private int id;
    private String token;
    private int userType;
    private String mobilePrefix;
    private int flowerNum;
    private String teacherName;
    private int isVisible;

    public String getMobilePrefix() {
        return mobilePrefix;
    }

    public void setMobilePrefix(String mobilePrefix) {
        this.mobilePrefix = mobilePrefix;
    }

    public int getFlowerNum() {
        return flowerNum;
    }

    public void setFlowerNum(int flowerNum) {
        this.flowerNum = flowerNum;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
