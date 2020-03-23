package cn.dxs.zdjl.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijian
 * @date 2020-03-21 15:46
 */
public class SmartInstallAPKAccessibilityService extends AccessibilityService {

    private static final String TAG = "日志--->[SmartInstallAPKAccessibilityService]";
    private Map<Integer, Boolean> handleMap = new HashMap<>();


    /**
     * 当服务连接成功时
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i(TAG, "服务连接成功");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "窗口事件的包名" + event.getPackageName().toString());
        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo != null) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
                    eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                if (handleMap.get(event.getWindowId()) == null) {
                    boolean handled = iterateNodesAndHandle(nodeInfo);
                    if (handled) {
                        handleMap.put(event.getWindowId(), true);
                    }
                }
            }
        }
    }

    /**
     * 终止服务时调用
     */
    @Override
    public void onInterrupt() {

    }

    //遍历节点，模拟点击安装按钮
    private boolean iterateNodesAndHandle(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo != null) {
            int childCount = nodeInfo.getChildCount();
            if ("android.widget.Button".equals(nodeInfo.getClassName())) {
                String nodeCotent = nodeInfo.getText().toString();
                Log.d(TAG, "文本内容：" + nodeCotent);
                if ("安装".equals(nodeCotent)
//                        || "完成".equals(nodeCotent)
                        || "确定".equals(nodeCotent)
                        || "打开".equals(nodeCotent)) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true;
                }
            }
            //遇到ScrollView的时候模拟滑动一下
            else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
                if (iterateNodesAndHandle(childNodeInfo)) {
                    return true;
                }
            }
        }
        return false;
    }
}
