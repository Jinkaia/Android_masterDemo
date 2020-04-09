package come.jk.cn.utils.imageload;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import come.jk.cn.R;
import come.jk.cn.app.MyAppLication;
import come.jk.cn.utils.ToastUtil;


/**
 * Created by sunfusheng on 16/4/6.
 */
public class ImageManager {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    //头像
    private static RequestOptions circleHeaderOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.weixin)
            .error(R.mipmap.weixin)
            .priority(Priority.HIGH)
            .transform(new GlideCircleTransform(MyAppLication.context))
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    //一般
    private static RequestOptions defaltOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.weixin)
            .error(R.mipmap.weixin)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    private static RequestOptions normalOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.weixin)
            .error(R.mipmap.weixin)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    //原图的毛玻璃、高斯模糊效果
    private static RequestOptions blurOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.weixin)
            .error(R.mipmap.weixin)
            .priority(Priority.HIGH)
            .bitmapTransform(new BlurTransformation(25))
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    ////原图处理成圆角，如果是四周都是圆角则是RoundedCornersTransformation.CornerType.ALL
    private static RequestOptions cornersOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.weixin)
            .error(R.mipmap.weixin)
            .priority(Priority.HIGH)
            .bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL))
            .diskCacheStrategy(DiskCacheStrategy.ALL);



    // 将资源ID转为Uri
    public static Uri resourceIdToUri(int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + MyAppLication.getContext().getPackageName() + FOREWARD_SLASH + resourceId);
    }

    /**
     * 加载开屏页
     */
    public static void loadLauncher(ImageView imageView, String url) {
        Glide.with(MyAppLication.getContext())
                .load(url)
                .apply(defaltOptions)
                .into(imageView);
    }

    // 加载网络图片
    public static void loadUrlImage(ImageView imageView, String url) {
        Glide.with(MyAppLication.getContext())
                .load(url)
                .apply(normalOptions)
                .into(imageView);
    }

    // 加载头像
    public static void loadUrlHead(ImageView imageView, String url) {
        Glide.with(MyAppLication.getContext())
                .load(url)
                .apply(circleHeaderOptions)
                .into(imageView);
    }


    // 加载banner
    public static void loadUrlBanner(ImageView imageView, String url) {
        Glide.with(MyAppLication.getContext())
                .load(url)
                .apply(defaltOptions)
                .into(imageView);
    }

    // 加载drawable图片
    public static void loadResImage(ImageView imageView, int resId) {
        Glide.with(MyAppLication.getContext())
                .load(resourceIdToUri(resId))
                .into(imageView);
    }

    // 加载本地图片
    public static void loadLocalImage(ImageView imageView, String path) {
        File file = new File(path);
        Glide.with(MyAppLication.getContext())
                .load(file)
                .into(imageView);
    }

    // 加载网络圆型图片
    public static void loadCircleImage(ImageView imageView, String url) {
        Glide.with(MyAppLication.getContext())
                .load(url)
                .apply(circleHeaderOptions)
                .into(imageView);
    }

    // 加载drawable圆型图片
    public static void loadCircleResImage(ImageView imageView, int resId) {
        Glide.with(MyAppLication.getContext())
                .load(resourceIdToUri(resId))
                .apply(circleHeaderOptions)
                .into(imageView);
    }

    // 加载网络图片
    public static void loadCornersUrlImage(ImageView imageView, String url) {
        Glide.with(MyAppLication.getContext())
                .load(url)
                .apply(cornersOptions)
                .into(imageView);
    }

    /**
     * 保存图片到本地
     */
    public static void saveImageFile(Context context, String imageUrl) {
        Glide.with(context).asBitmap().load(imageUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                saveImageToLocal(resource);
            }
        });
    }

    private static void saveImageToLocal(Bitmap bitmap) {
        File dir = new File(Environment.getExternalStorageDirectory(), "shareread");
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fileName = UUID.randomUUID().toString() + ".jpg";
        File file = new File(dir, fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            MyAppLication.context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            ToastUtil.show("保存图片成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
