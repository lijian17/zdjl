package cn.dxs.zdjl;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @author lijian
 * @date 2020-03-25 10:36
 */
public class App extends Application {

    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getContext(){
        return instance;
    }

    /**
     * 获取包信息
     *
     * @return
     */
    public static PackageInfo getPackageInfo(){
        PackageManager packageManager = instance.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(instance.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 获取版本name
     *
     * @return
     */
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    /**
     * 获取版本code
     *
     * @return
     */
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }
}
