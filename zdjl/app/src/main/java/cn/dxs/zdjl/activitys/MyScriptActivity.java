package cn.dxs.zdjl.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.dxs.zdjl.R;
import cn.dxs.zdjl.base.BaseActivity;
import cn.dxs.zdjl.domain.ScriptInfo;
import cn.dxs.zdjl.ui.adapter.MyScriptAdapter;
import cn.dxs.zdjl.utils.L;

/**
 * 我的脚本页面
 *
 * @author lijian
 * @date 2020-03-24 16:06
 */
public class MyScriptActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MyScriptActivity.class.getSimpleName();

    private static final int HANDLER_DATA = 1000;

    private static String SCRIPT_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "自动精灵" + File.separator;
    private DecimalFormat df = new DecimalFormat("0.00");//格式化小数

    private LinearLayout mLl_loading;
    private ListView mLv_myscript;

    private List<ScriptInfo> scriptInfos;

    private MyScriptAdapter mAdp_myScript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_script);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        mLl_loading = findViewById(R.id.ll_myscript_loading);
        mLv_myscript = findViewById(R.id.lv_myscript_myscript);
    }

    private void initData() {
        mLv_myscript.setOnItemClickListener(this);
        setPermissions();
        fillData();
    }

    private void setPermissions() {
        // 读写手机存储权限
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            Toast.makeText(this, "请授予读写手机存储权限，并设置允许后台运行", Toast.LENGTH_SHORT).show();
        }

        // 请求悬浮窗权限
        if (!Settings.canDrawOverlays(this)) {
            Intent intent_dol = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            intent_dol.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent_dol, PackageManager.MATCH_ALL);
            if (resolveInfo != null) {
                startActivity(intent_dol);
            } else {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            Toast.makeText(this, "请授予应用悬浮窗权限，并设置允许后台运行", Toast.LENGTH_SHORT).show();
        }

        // 省电优化白名单
        if (!((PowerManager) getSystemService(POWER_SERVICE)).isIgnoringBatteryOptimizations(getPackageName())) {
            Intent intent_ibo = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:" + getPackageName()));
            intent_ibo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent_ibo, PackageManager.MATCH_ALL);
            if (resolveInfo != null) {
                startActivity(intent_ibo);
            }
            Toast.makeText(this, "请授予应用省电白名单权限", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 刷新界面
     */
    private void fillData() {
        mLl_loading.setVisibility(View.VISIBLE);
        new Thread() {
            public void run() {
                // 所有的应用程序信息
                File dir = new File(SCRIPT_DIRECTORY);
                File[] children = dir.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".zjs");
                    }
                });
                if (children == null) {
                    L.i(TAG, "脚本目录不存在或他不是一个目录");
                } else {
                    scriptInfos = new ArrayList<>();
                    for (int i = 0, len = children.length; i < len; i++) {
                        ScriptInfo scriptInfo = new ScriptInfo();
                        scriptInfo.setName(children[i].getName());
                        scriptInfo.setSize(showFileSize(children[i].length()));
                        scriptInfo.setCreateTime(getCreateTime(children[i].getAbsolutePath()));
                        scriptInfo.setLastModifiedTime(getLastModifiedTime(children[i].lastModified()));
                        scriptInfo.setPath(children[i].getAbsolutePath());
                        L.i(TAG, scriptInfo.toString());
                        scriptInfos.add(scriptInfo);
                    }
                }
                mHandler.sendEmptyMessage(HANDLER_DATA);
            }
        }.start();
    }

    @Override
    protected void parserMessage(Message msg) {
        super.parserMessage(msg);
        switch (msg.what) {
            case HANDLER_DATA:
                setAdpData();
                break;
        }
    }

    /**
     * 給适配器设置数据
     */
    private void setAdpData() {
        mLl_loading.setVisibility(View.INVISIBLE);
        if (mAdp_myScript == null) {
            mAdp_myScript = new MyScriptAdapter(this, scriptInfos);
            mLv_myscript.setAdapter(mAdp_myScript);
        } else {
            mAdp_myScript.notifyDataSetChanged(scriptInfos);
        }
    }

    /****
     * 计算文件大小
     *
     * @param length
     * @return
     */
    public String showFileSize(Long length) {
        if (length >= 1048576) {
            return df.format((float) length / 1048576) + "MB";
        } else if (length >= 1024) {
            return df.format((float) length / 1024) + "KB";
        } else if (length < 1024) {
            return length + "B";
        } else {
            return "0KB";
        }
    }

    /**
     * 文件创建时间（格式：2020-03-24 17:28:38）
     *
     * @param filePath
     * @return
     */
    public String getCreateTime(String filePath) {
        String strTime = null;
        File file = new File(filePath);
        strTime = getLastModifiedTime(file.lastModified());
        return strTime;
    }

    /**
     * 文件最后修改时间（格式：2020-03-24 17:28:38）
     *
     * @param time
     * @return
     */
    public String getLastModifiedTime(long time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        return formatter.format(cal.getTime());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ScriptDescriptionActivity.class);
        intent.putExtra("scriptInfo", scriptInfos.get(position));
        startActivity(intent);
    }
}
