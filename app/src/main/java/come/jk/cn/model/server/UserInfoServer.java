package come.jk.cn.model.server;

import come.jk.cn.base.BaseEntity;
import come.jk.cn.model.entity.LoginRegsterSucessModel;
import come.jk.cn.model.entity.UserInfo;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Master on 2019/4/13.
 */

public interface UserInfoServer {

    /**
     * 密码登陆
     *
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("v1/m/user/login/mobile")
    Observable<BaseEntity<LoginRegsterSucessModel>> getPhoneLogin(@Field("mobile") String mobile, @Field("password") String password);

    /**
     * 账号注册
     *
     * @param mobile
     * @param authCode
     * @return
     */
    @FormUrlEncoded
    @POST("v1/m/user/register/mobile")
    Observable<BaseEntity<LoginRegsterSucessModel>> getPhoneRegis(@Field("mobile") String mobile, @Field("mobileValidCode") String authCode);

}
