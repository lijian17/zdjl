package cn.dxs.zdjl.utils;

import android.util.Log;

/**
 * 日志工具类
 * @author lijian
 * @date 2020-03-23 19:32
 */
public class L {
    private static final boolean flag = true;
    private static final String LOG_PREFIX = "自动精灵--->";// 日志前缀

    public static void i(String tag, String msg) {
        if (flag)
            Log.i(tag, LOG_PREFIX + msg);
    }

    public static void d(String tag, String msg) {
        if (flag)
            Log.d(tag, LOG_PREFIX + msg);
    }

    public static void e(String tag, String msg) {
        if (flag)
            Log.e(tag, LOG_PREFIX + msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (flag)
            Log.e(tag, LOG_PREFIX + msg, tr);
    }

    public static void v(String tag, String msg) {
        if (flag)
            Log.v(tag, LOG_PREFIX + msg);
    }

    public static void m(String tag, String msg) {
        if (flag)
            Log.e(tag, LOG_PREFIX + msg);
    }

    public static void w(String tag, String msg) {
        if (flag)
            Log.w(tag, LOG_PREFIX + msg);
    }
}
