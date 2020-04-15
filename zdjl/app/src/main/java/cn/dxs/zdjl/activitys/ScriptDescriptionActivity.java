package cn.dxs.zdjl.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import cn.dxs.zdjl.GlobalParams;
import cn.dxs.zdjl.R;
import cn.dxs.zdjl.base.BaseActivity;
import cn.dxs.zdjl.domain.ScriptContent;
import cn.dxs.zdjl.domain.ScriptInfo;
import cn.dxs.zdjl.domain.script.ScriptDes;
import cn.dxs.zdjl.service.ZdjlAccessibilityService;
import cn.dxs.zdjl.utils.AppToast;
import cn.dxs.zdjl.utils.ScreenShotHelper;

/**
 * 脚本描述页面
 *
 * @author lijian
 * @date 2020-03-25 13:45
 */
public class ScriptDescriptionActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_MEDIA_PROJECTION = 0;

    private TextView mTv_scriptName;
    private TextView mTv_scriptSize;
    private TextView mTv_createTime;
    private TextView mTv_lastModifiedTime;
    private TextView mTv_scriptPreview;
    private TextView mTv_scriptApplyRatio;
    private TextView mTv_scriptDescription;
    private Button mBtn_edit;
    private Button mBtn_run;
    private Button mBtn_more;
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_description);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        mTv_scriptName = findViewById(R.id.tv_scriptName);
        mTv_scriptSize = findViewById(R.id.tv_scriptSize);
        mTv_createTime = findViewById(R.id.tv_createTime);
        mTv_lastModifiedTime = findViewById(R.id.tv_lastModifiedTime);
        mTv_scriptPreview = findViewById(R.id.tv_scriptPreview);
        mTv_scriptApplyRatio = findViewById(R.id.tv_scriptApplyRatio);
        mTv_scriptDescription = findViewById(R.id.tv_scriptDescription);
        mBtn_edit = findViewById(R.id.btn_edit);
        mBtn_run = findViewById(R.id.btn_run);
        mBtn_more = findViewById(R.id.btn_more);
        mBtn_edit.setOnClickListener(this);
        mBtn_run.setOnClickListener(this);
        mBtn_more.setOnClickListener(this);
        mImageView = findViewById(R.id.image);
    }

    private void initData() {
        Intent intent = getIntent();
        ScriptInfo scriptInfo = intent.getParcelableExtra("scriptInfo");
        ScriptContent scriptContent = new ScriptContent(scriptInfo.getPath());
        ScriptDes scriptDes = scriptContent.getScriptDes();
        GlobalParams.scriptInfo = scriptInfo;
        GlobalParams.scriptContent = scriptContent;

        mTv_scriptName.setText(scriptInfo.getName());
        mTv_scriptSize.setText(scriptInfo.getSize());
        mTv_createTime.setText(scriptInfo.getCreateTime());
        mTv_lastModifiedTime.setText(scriptInfo.getLastModifiedTime());
        mTv_scriptPreview.setText("共" + scriptDes.getCount() + "个动作");

        String scriptRatio = scriptDes.getScreenWidth() + "x" + scriptDes.getScreenHeight() + "@" + scriptDes.getScreenDpi();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        GlobalParams.widthPixels = dm.widthPixels;
        GlobalParams.heightPixels = dm.heightPixels;
        GlobalParams.density = dm.density;
        GlobalParams.densityDpi = dm.densityDpi;
        GlobalParams.scaledDensity = dm.scaledDensity;
        GlobalParams.xdpi = dm.xdpi;
        GlobalParams.ydpi = dm.ydpi;
        String currentMobile = "（当前分辨率：" + dm.widthPixels + "x" + dm.heightPixels + "@" + dm.density + "）";
        mTv_scriptApplyRatio.setText(scriptRatio + currentMobile);

        mTv_scriptDescription.setText(scriptDes.getDescription());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit:
                AppToast.getInstance().show("编辑");
                editClick();
                break;

            case R.id.btn_run:
                AppToast.getInstance().show("运行");
                break;

            case R.id.btn_more:
                AppToast.getInstance().show("更多");
                break;
        }
    }

    private void editClick() {
        try2StartScreenShot();
    }

    /**
     * 申请屏幕录取权限
     */
    private void try2StartScreenShot() {
        if (GlobalParams.screenCaptureResultCode == RESULT_OK && GlobalParams.screenCaptureData != null) {
            startZdjlAccessibilityService();
        } else {
            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode == RESULT_OK && data != null) {
                GlobalParams.screenCaptureResultCode = resultCode;
                GlobalParams.screenCaptureData = data;
                screenShot();
                startZdjlAccessibilityService();
            }
        }
    }

    private void screenShot() {
        ScreenShotHelper screenShotHelper = new ScreenShotHelper(this, GlobalParams.screenCaptureResultCode, GlobalParams.screenCaptureData, new ScreenShotHelper.OnScreenShotListener() {
            @Override
            public void onFinish(Bitmap bitmap) {
                mImageView.setImageBitmap(bitmap);
            }
        });
        screenShotHelper.startScreenShot();
    }

    private void startZdjlAccessibilityService() {
        if (ZdjlAccessibilityService.mainFunctions == null) {
            Intent intent_abs = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent_abs.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent_abs);
            Toast.makeText(this, "请打开其中一个无障碍服务", Toast.LENGTH_SHORT).show();
        } else {
            ZdjlAccessibilityService.mainFunctions.handler.sendEmptyMessage(1000);
        }
    }

}
