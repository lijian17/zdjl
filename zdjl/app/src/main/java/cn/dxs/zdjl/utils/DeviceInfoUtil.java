package cn.dxs.zdjl.utils;

import android.util.DisplayMetrics;

import cn.dxs.zdjl.App;

/**
 * 设备信息工具类
 *
 * @author lijian
 * @date 2020-03-26 22:20
 */
public class DeviceInfoUtil {

    private static int screenWidth;
    private static int screenHeight;

    public static int getScreenWidth() {
        if (screenWidth <= 0) {
            getScreen();
        }
        return screenWidth;
    }

    public static int getScreenHeight() {
        if (screenHeight <= 0) {
            getScreen();
        }
        return screenHeight;
    }

    private static void getScreen(){
        DisplayMetrics dm = App.getContext().getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }
}
