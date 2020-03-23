package cn.dxs.zdjl;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

/**
 * 无障碍服务
 */
public class MyAccessibilityService extends AccessibilityService {

    private int create_num, connect_num;
    public static MainFunctions mainFunctions;

    /**
     * 服务创建时调用，初始化一些数据
     */
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            create_num = 0;
            connect_num = 0;
            create_num++;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务连接时，也就是第一次打开时调用，这里我们可以初始化常量和标签等
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        if (++connect_num != create_num) {
            throw new RuntimeException("无障碍服务出现异常");
        }
        mainFunctions = new MainFunctions(this);
        mainFunctions.onServiceConnected();
        if (MyAccessibilityServiceNoGesture.mainFunctions != null) {
            MyAccessibilityServiceNoGesture.mainFunctions.handler.sendEmptyMessage(0x04);
        }
    }

    /**
     * 监测到内容节点时调用
     * @param event
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        mainFunctions.onAccessibilityEvent(event);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return mainFunctions.onKeyEvent(event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mainFunctions.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mainFunctions.onUnbind(intent);
        mainFunctions = null;
        return super.onUnbind(intent);
    }

    /**
     * 终止accessibility service时调用
     */
    @Override
    public void onInterrupt() {
    }
}
