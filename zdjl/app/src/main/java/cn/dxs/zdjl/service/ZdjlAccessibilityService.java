package cn.dxs.zdjl.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import cn.dxs.zdjl.ui.window.MainFunctions;
import cn.dxs.zdjl.utils.L;

/**
 * 自动精灵服务
 *
 * @author lijian
 * @date 2020-03-27 14:29
 */
public class ZdjlAccessibilityService extends AccessibilityService {

    private int create_num;
    private int connect_num;
    public static MainFunctions mainFunctions;

    @Override
    public void onCreate() {
        L.i("Tag", "onCreate");
        super.onCreate();
        create_num = 0;
        connect_num = 0;
        create_num++;
    }

    /**
     * 当启动服务的时候就会被调用(非必须重写)
     */
    @Override
    protected void onServiceConnected() {
        L.i("Tag", "onServiceConnected");
        super.onServiceConnected();
        if (++connect_num != create_num) {
            throw new RuntimeException("无障碍服务出现异常");
        }
        mainFunctions = new MainFunctions(this);
        mainFunctions.onServiceConnected();
    }

    /**
     * 监听窗口变化的回调
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        L.i("Tag", "onAccessibilityEvent");
        int eventType = event.getEventType();
        //根据事件回调类型进行处理
        mainFunctions.onAccessibilityEvent(event);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        L.i("Tag", "onKeyEvent");
        return mainFunctions.onKeyEvent(event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        L.i("Tag", "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
        mainFunctions.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        L.i("Tag", "onUnbind");
        mainFunctions.onUnbind(intent);
        mainFunctions = null;
        return super.onUnbind(intent);
    }

    /**
     * 中断服务的回调
     */
    @Override
    public void onInterrupt() {
        L.i("Tag", "onInterrupt");

    }
}
