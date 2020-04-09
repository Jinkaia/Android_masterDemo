package come.jk.cn.model.biz;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import come.jk.cn.BuildConfig;
import come.jk.cn.R;
import come.jk.cn.app.MyAppLication;
import come.jk.cn.config.Urils;
import come.jk.cn.model.interceptor.ParamsInterceptor;

import come.jk.cn.model.server.UserInfoServer;
import come.jk.cn.utils.APKVersionCodeUtils;
import come.jk.cn.utils.LogUtils;
import come.jk.cn.utils.NetUtil;
import come.jk.cn.utils.SPUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Master on 2018/12/27.
 */

public class ServiceFactory {
    private volatile static Retrofit requestRetrofit;
    public static final String CACHE_NAME = "come.jk.cn";



    //正式服
//    public static final String BASE_URL_RELEASE = "http://www.xiangduyizhan-app.com/";
//测试服
//    public static final String BASE_URL_RELEASE = "http://app-luqu.ibangoo.com";
    public static final String BASE_URL_RELEASE= Urils.Root_url;

    //默认链接超时时间
    private static final int DEFAULT_TIMEOUT = 30;
    //默认服务器响应时间
    private static final int DEFAULT_READ_TIMEOUT = 30;

    private ServiceFactory() {


    }



    public static ParamsInterceptor getBaseParamsInterceptor() {
        return new ParamsInterceptor.Builder()
                .addHeaderParam("oCode", "350010")
                .addHeaderParam("appType", "univstarUnion")
                .addHeaderParam("cid", "fe0ca0b429041ea547f09226c7c5df45")
                .addHeaderParam("did", "ae7a2b56b3398323bb5aa894849e31c5")
                .build();
    }

    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }

    public static OkHttpClient getDefaultClient() {
//         FIXME: 2017/1/22 这里其实只针对GET进行cache，应该自定义缓存
//        File httpCacheDirectory = new File(SystemState.getExternalPath(PandaApp.getAppContext(), "PandaCache"), "HttpCache");
        File cacheFile = new File(MyAppLication.context.getExternalCacheDir(), CACHE_NAME);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (!NetUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader(CACHE_NAME)// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader(CACHE_NAME)
                            .build();
                }
                return response;
            }
        };


        return new OkHttpClient.Builder()
                .addInterceptor(getBaseParamsInterceptor())
                .addNetworkInterceptor(getLoggingInterceptor())
                .cache(cache)
//              .addInterceptor(cacheInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }


    private static Retrofit getDefaultRetrofit() {
        if (requestRetrofit == null) {
            synchronized (ServiceFactory.class) {
                if (requestRetrofit == null) {
                    requestRetrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(BASE_URL_RELEASE)
                            .client(getDefaultClient())
                            .build();
                }
            }
        }
        return requestRetrofit;
    }

    //处理线程调度的变换

    public static ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return ((Observable) upstream)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    //Todo rxAndroid
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };


    public static UserInfoServer getUserInfoService() {
        return getDefaultRetrofit().create(UserInfoServer.class);
    }
//
//    public static OtherService getOtherService() {
//        return getDefaultRetrofit().create(OtherService.class);
//    }
//
//    public static AuthService getAuthService(){
//        return getDefaultRetrofit().create(AuthService.class);
//    }
//
//    public static BacklogService getBacklogService(){
//        return getDefaultRetrofit().create(BacklogService.class);
//    }
//
//    public static AddressService getAddressService(){
//        return getDefaultRetrofit().create(AddressService.class);
//    }
//
//    public static TopicService getTopicService(){
//        return getDefaultRetrofit().create(TopicService.class);
//    }
//
//    public static CircleService getCircleService(){
//        return getDefaultRetrofit().create(CircleService.class);
//    }
//
//    public static CommentService getCommentService(){
//        return getDefaultRetrofit().create(CommentService.class);
//    }
//
//    public static ZanService getZanService() {
//        return getDefaultRetrofit().create(ZanService.class);
//    }
//
//    public static QueationService getQueationService() {
//        return getDefaultRetrofit().create(QueationService.class);
//    }
//
//    public static CourseService getCourseService() {
//        return getDefaultRetrofit().create(CourseService.class);
//    }
//
//    public static GuestsService getGuestsService() {
//        return getDefaultRetrofit().create(GuestsService.class);
//    }
//
//    public static RecommendService getRecommendService() {
//        return getDefaultRetrofit().create(RecommendService.class);
//    }
//
//    public static QuestionOrderService getQuestionOrderService() {
//        return getDefaultRetrofit().create(QuestionOrderService.class);
//    }
//    public static SmallClassService getSmallClassService() {
//        return getDefaultRetrofit().create(SmallClassService.class);
//    }

}
