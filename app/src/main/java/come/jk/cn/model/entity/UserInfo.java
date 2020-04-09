package come.jk.cn.model.entity;

/**
 * Created by jinkai on 2018/4/19.
 */

public class UserInfo {
    /**
     * code : 0
     * data : {}
     * message : string
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private String mobilePrefix;
        private int flowerNum;
        private String teacherName;
        private int isVisible;

        public int getIsVisible() {
            return isVisible;
        }

        public void setIsVisible(int isVisible) {
            this.isVisible = isVisible;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public DataBean(String mobilePrefix, int flowerNum) {
            this.mobilePrefix = mobilePrefix;
            this.flowerNum = flowerNum;
        }

        public int getFlowerNum() {
            return flowerNum;
        }

        public void setFlowerNum(int flowerNum) {
            this.flowerNum = flowerNum;
        }



        public String getMobilePrefix() {
            return mobilePrefix;
        }

        public void setMobilePrefix(String mobilePrefix) {
            this.mobilePrefix = mobilePrefix;
        }
    }
}
