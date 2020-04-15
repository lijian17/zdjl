package cn.dxs.zdjl.ui.window;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import cn.dxs.zdjl.GlobalParams;
import cn.dxs.zdjl.R;
import cn.dxs.zdjl.domain.ScriptContent;
import cn.dxs.zdjl.domain.ScriptInfo;
import cn.dxs.zdjl.ui.adapter.EditManagerAdapter;

/**
 * 脚本编辑管理器
 *
 * @author lijian
 * @date 2020-03-29 9:15
 */
public class EditManagerDialog extends Dialog implements View.OnClickListener {

    private static final int HANDLER_DATA = 1000;
    private WindowManager windowManager;

    private View v;
    private TextView mTv_scriptName;
    private Button mBtn_set;
    private Button mBtn_close;
    private ListView mLv_item;
    private Button mBtn_run;
    private Button mBtn_transcribe;
    private Button mBtn_more;

    private EditManagerAdapter mAdp_editManager;
    private ScriptInfo scriptInfo;
    private ScriptContent scriptContent;

    private WindowManager.LayoutParams mParams;

    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_DATA:
                    setAdpData();
                    break;
            }
        };
    };

    public EditManagerDialog(@NonNull Context context) {
        super(context, R.style.RunModeDialog);
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);

        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        p.format = PixelFormat.TRANSPARENT;
        p.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        p.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        p.width = GlobalParams.widthPixels * 4 / 9;
        p.height = GlobalParams.heightPixels / 4;
        getWindow().setAttributes(p);
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        v = View.inflate(getContext(), R.layout.dialog_edit_manager, null);
        setContentView(v);
        mTv_scriptName = v.findViewById(R.id.tv_scriptName);
        mBtn_set = v.findViewById(R.id.btn_set);
        mBtn_close = v.findViewById(R.id.btn_close);
        mLv_item = v.findViewById(R.id.lv_item);
        mBtn_run = v.findViewById(R.id.btn_run);
        mBtn_transcribe = v.findViewById(R.id.btn_transcribe);
        mBtn_more = v.findViewById(R.id.btn_more);

        mBtn_set.setOnClickListener(this);
        mBtn_close.setOnClickListener(this);
        mBtn_run.setOnClickListener(this);
        mBtn_transcribe.setOnClickListener(this);
        mBtn_more.setOnClickListener(this);
    }

    private void initData() {
        scriptInfo = GlobalParams.scriptInfo;
        mTv_scriptName.setText(scriptInfo.getName());
        fillData();
    }

    private void fillData() {
        new Thread() {
            @Override
            public void run() {
                scriptContent = new ScriptContent(scriptInfo.getPath());
                mHandler.sendEmptyMessage(HANDLER_DATA);
            }
        }.start();
    }

    /**
     * 給适配器设置数据
     */
    private void setAdpData() {
        if (mAdp_editManager == null) {
            mAdp_editManager = new EditManagerAdapter(getContext(), scriptContent.getjObjs());
            mLv_item.setAdapter(mAdp_editManager);
        } else {
            mAdp_editManager.notifyDataSetChanged(scriptContent.getjObjs());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set:
                Toast.makeText(getContext(), "设置", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_close:
                Toast.makeText(getContext(), "关闭", Toast.LENGTH_SHORT).show();
                cancel();
                break;

            case R.id.btn_run:
                Toast.makeText(getContext(), "运行", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_transcribe:
                Toast.makeText(getContext(), "录制", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_more:
                Toast.makeText(getContext(), "更多", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
