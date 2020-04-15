package cn.dxs.zdjl;

import android.content.Intent;

import cn.dxs.zdjl.domain.ScriptContent;
import cn.dxs.zdjl.domain.ScriptInfo;

/**
 * 全局参数
 *
 * @author lijian
 * @date 2020-03-28 21:59
 */
public class GlobalParams {

    public static int widthPixels;// 手机屏幕宽度像素
    public static int heightPixels;// 手机屏幕高度像素
    public static float density;// 手机屏幕密度
    public static int densityDpi;
    public static float scaledDensity;
    public static float xdpi;
    public static float ydpi;

    public static int screenCaptureResultCode;
    public static Intent screenCaptureData;

    public static ScriptInfo scriptInfo;// 脚本信息
    public static ScriptContent scriptContent;// 脚本内容
}
