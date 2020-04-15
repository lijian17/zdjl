package cn.dxs.zdjl.ui.window;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Path;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import cn.dxs.zdjl.GlobalParams;
import cn.dxs.zdjl.domain.ScriptContent;
import cn.dxs.zdjl.domain.script.ScriptDes;
import cn.dxs.zdjl.utils.L;
import cn.dxs.zdjl.utils.ScriptUtil;

/**
 * 弹窗主控制器
 *
 * @author lijian
 * @date 2020-03-27 20:45
 */
public class MainFunctions implements RunModeDialog.OnClickListener, RunModeDialog.OnCheckedChangeListener, EditManagerView.OnClickListener {

    private AccessibilityService service;
    private String packageName;// 程序包名
    private AudioManager audioManager;// 音频管理器
    private Vibrator vibrator;// 振动管理器
    private SharedPreferences sharedPreferences;// sp
    private WindowManager windowManager;// 窗体管理器
    private DevicePolicyManager devicePolicyManager;// 设备策略管理器
    private PackageManager packageManager;// 程序包管理器
    private AccessibilityServiceInfo asi;// 辅助功能服务信息
    private ScheduledExecutorService executorService;// 线程池
    public Handler handler;
    private RunModeDialog runModeDialog;// 运行模式弹窗

    public MainFunctions(AccessibilityService service) {
        this.service = service;
    }

    public void onServiceConnected() {
        L.i("tag", "服务连接");
        packageName = service.getPackageName();
        audioManager = (AudioManager) service.getSystemService(AccessibilityService.AUDIO_SERVICE);
        vibrator = (Vibrator) service.getSystemService(AccessibilityService.VIBRATOR_SERVICE);
        sharedPreferences = service.getSharedPreferences(packageName, AccessibilityService.MODE_PRIVATE);
        windowManager = (WindowManager) service.getSystemService(AccessibilityService.WINDOW_SERVICE);
        devicePolicyManager = (DevicePolicyManager) service.getSystemService(AccessibilityService.DEVICE_POLICY_SERVICE);
        packageManager = service.getPackageManager();
        asi = service.getServiceInfo();
        executorService = Executors.newSingleThreadScheduledExecutor();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what) {
                    case 1000:
                        mainUI();
                        break;

                    case 1001:
                        break;

                    case 1002:
                        break;

                    case 1003:
                        break;

                    case 1004:
                        break;

                    case 1005:
                        break;
                }
                return true;
            }
        });
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    public boolean onKeyEvent(KeyEvent event) {
        return false;
    }

    public void onConfigurationChanged(Configuration newConfig) {

    }

    public void onUnbind(Intent intent) {

    }

    /**
     * 点击
     *
     * @param x         x坐标
     * @param y         y坐标
     * @param startTime 延迟时间（多久后执行点击）
     * @param duration  动作持续时间
     * @return
     */
    private boolean click(float x, float y, long startTime, long duration) {
        Path path = new Path();
        path.moveTo(x, y);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Toast.makeText(service, "模拟点击x:" + x + " y:" + y + " startTime:" + startTime + " duration:" + duration, Toast.LENGTH_SHORT).show();
            GestureDescription.Builder builder = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, startTime, duration));
            return service.dispatchGesture(builder.build(), new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    L.i("aaa", "onCompleted: 完成..........");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    L.i("bbb", "onCancelled: 取消..........");
                }
            }, null);
        }
        return false;
    }

    /**
     * 滑动
     *
     * @param x1        起始点x坐标
     * @param y1        起始点y坐标
     * @param x2        终点x坐标
     * @param y2        终点y坐标
     * @param startTime 延迟时间（多久后执行点击）
     * @param duration  动作持续时间
     * @return
     */
    private boolean swipe(float x1, float y1, float x2, float y2, long startTime, long duration) {
        Path path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription build = builder.addStroke(new GestureDescription.StrokeDescription(path, startTime, duration)).build();
            service.dispatchGesture(build, new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    L.i("aaa", "onCompleted: 滑动完成..........");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    L.i("bbb", "onCancelled: 滑动取消..........");
                }
            }, null);
        }

        return false;
    }

    /**
     * 点击图片
     */
    private void clickImage() {

    }

    /**
     * 点击文字
     */
    private void clickText() {

    }

    /**
     * 按键
     *
     * @param action AccessibilityService.GLOBAL_ACTION_BACK 返回键 1
     *               AccessibilityService.GLOBAL_ACTION_HOME 主页键 2
     *               菜单键
     *               AccessibilityService.GLOBAL_ACTION_RECENTS 任务键 3
     *               音量+键
     *               音量-键
     *               电源键
     *               回车键
     *               TAB键
     *               空格键
     *               删除键
     *               方向键上
     *               方向键下
     *               方向键左
     *               方向键右
     *               <p>
     *               AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS 打开通知 4
     *               AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS 打开快速设置(暂无效果) 5
     *               AccessibilityService.GLOBAL_ACTION_POWER_DIALOG 打开电源长按对话框的操作(暂无效果) 6
     *               AccessibilityService.GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN 切换停靠当前应用程序窗口的操作(暂无效果) 7
     *               AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN 锁屏(安卓9.0使用28) 8
     *               AccessibilityService.GLOBAL_ACTION_TAKE_SCREENSHOT 截屏(安卓9.0使用28) 9
     */
    private void key(int action) {
        service.performGlobalAction(action);
    }

    /**
     * 打开应用
     */
    private void startApp() {

    }

    /**
     * 输入内容
     */
    private void inputText() {

    }

    /**
     * 运行脚本
     */
    private void runScript() {

    }

    /**
     * 运行多个动作
     */
    private void RunMultipleAction() {

    }

    /**
     * 控制运行
     */
    private void controlRun() {

    }

    /**
     * 系统提示
     */
    private void systemTip() {

    }

    private void mainUI() {
        runModeDialog = new RunModeDialog(service);
        runModeDialog.show();
        runModeDialog.setOnClickListener(this);
        runModeDialog.setOnCheckedChangeListener(this);
    }

    @Override
    public void onRunModeCloseClick() {

    }

    @Override
    public void onRunModeOkClick() {
        EditManagerView editManagerView = new EditManagerView(service, windowManager);
        editManagerView.setOnClickButtonListener(this);
    }

    @Override
    public void onRunModeCheckedChanged(int position) {
        switch (position) {
            case 0:// 系统辅助
                break;

            case 1:// 虚拟空间
                break;

            case 2:// 核心服务
                break;

            case 3:// ROOT
                break;
        }
    }

    @Override
    public void onEditManagerSetClick() {

    }

    @Override
    public void onEditManagerCloseClick() {

    }

    @Override
    public void onEditManagerRunClick() {
        if (GlobalParams.scriptContent == null) {
            return;
        }
        executorsScript(GlobalParams.scriptContent);
    }

    @Override
    public void onEditManagerTranscribeClick() {

    }

    @Override
    public void onEditManagerClearClick() {

    }

    @Override
    public void onEditManagerSaveClick() {

    }

    //---------------------------------------------------------------------------------------------
    //---脚本动作执行
    //---------------------------------------------------------------------------------------------

    /**
     * 执行脚本文件（分析脚本文件的设置参数 重复次数，有动作失败立即暂停）
     *
     * @param scriptContent
     */
    private void executorsScript(ScriptContent scriptContent) {
        ScriptDes scriptDes = scriptContent.getScriptDes();
        int repeatCount = ScriptUtil.getRepeatCount(scriptDes.getRepeatCount());// 重复次数
        for (int i = 0; i < repeatCount; i++) {
            executorsAction(scriptContent);
        }
    }

    /**
     * 执行脚本动作
     *
     * @param scriptContent
     */
    private void executorsAction(ScriptContent scriptContent) {
        List<JsonObject> jObjs = scriptContent.getjObjs();
        // 遍历执行每一个动作
//        for (int i = 0, len = jObjs.size(); i < len; i++) {
//            dispatchExecutorsAction(scriptContent, jObjs.get(i));
//        }
        for (JsonObject jObj : jObjs) {
            dispatchExecutorsAction(scriptContent, jObj);
        }
    }

    /**
     * 分发执行动作
     *
     * @param scriptContent
     * @param jObj
     */
    private void dispatchExecutorsAction(ScriptContent scriptContent, JsonObject jObj) {
        String type = jObj.get("type").getAsString();
        // 重复次数
        int repeatCount = ScriptUtil.getRepeatCount(jObj.get("repeatCount").getAsInt());
        switch (type) {
            case "点击":
                for (int i = 0; i < repeatCount; i++) {
                    dispatchExecutorsActionClick(scriptContent, jObj);
                }
                break;

            case "滑动":
                break;

            case "点击图片":
                break;

            case "点击文字":
                break;

            case "点击颜色":
                break;

            case "单指手势":
                break;

            case "多指手势":
                break;

            case "按键":
                break;

            case "打开应用":
                break;

            case "输入内容":
                break;

            case "运行脚本":
                break;

            case "运行多个动作":
                break;

            case "控制运行":
                break;

            case "系统提示":
                break;
        }
    }

    /**
     * 分发执行动作-点击
     *
     * @param scriptContent
     * @param jObj
     */
    private void dispatchExecutorsActionClick(ScriptContent scriptContent, JsonObject jObj) {
        // 重复次数
//        int repeatCount = getRepeatCount(jObj.get("repeatCount").getAsInt());
//        for (int i = 0; i < repeatCount; i++) {
//
//        }

        // 运行条件
        JsonObject condition = jObj.getAsJsonObject("condition");
        JsonObject scriptCallbacks = jObj.getAsJsonObject("scriptCallbacks");
        if (condition == null) {
            // 运行延时

            // 监听动作
        } else {
            // 条件在等待延迟前检查
            JsonElement checkBeforeDelay = condition.get("checkBeforeDelay");
            if (checkBeforeDelay != null && checkBeforeDelay.getAsBoolean()) {// 先检查条件
                if (!checkCondition(condition)) {// 条件不满足，则中断本动作的执行

                    return;
                } else {

                }
            } else {

            }
        }
        // 检查运行条件前 beforeCondition
        // 运行条件满足后 afterConditionSuc
        // 运行条件失败后 afterConditionFail
        // 动作开始运行前 beforeStartExec
        // 动作运行成功后 afterExecSuc
        // 动作运行失败后 afterExecFail
    }


    //---------------------------------------------------------------------------------------------
    //---脚本动作执行-运行条件
    //---------------------------------------------------------------------------------------------

    private boolean checkCondition(JsonObject condition) {// 18210677336 赵
        // 检查运行条件前
        beforeCondition();

        boolean result = true;
        switch (condition.get("type").getAsString()) {
            case "image":// 出现图片
                break;

            case "textFount":// 出现文字
                break;

            case "colorFound":// 出现颜色
                break;

            case "limitRunTimes":// 限制运行次数
                break;

            case "scriptRunState":// 动作运行状态
                break;

            case "random":// 随机运行
                break;

            case "timeAfter":// 在时间之后
                break;

            case "conditionSet":// 多个条件
                break;
        }

        if (result) {
            // 运行条件满足后
            afterConditionSuc();
        } else {
            // 运行条件失败后
            afterConditionFail();
        }

        return result;
    }

    /**
     * 检查运行条件前
     */
    private void beforeCondition() {

    }

    /**
     * 运行条件满足后
     */
    private void afterConditionSuc() {

    }

    /**
     * 运行条件失败后
     */
    private void afterConditionFail() {

    }

    /**
     * 动作开始运行前
     */
    private void beforeStartExec() {

    }

    /**
     * 动作运行成功后
     */
    private void afterExecSuc() {

    }

    /**
     * 动作运行失败后
     */
    private void afterExecFail() {

    }
}
