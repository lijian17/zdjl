package cn.dxs.zdjl.ui.window;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.dxs.zdjl.GlobalParams;
import cn.dxs.zdjl.R;

/**
 * 运行模式弹窗
 *
 * @author lijian
 * @date 2020-03-28 10:33
 */
public class RunModeDialog extends Dialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Button mBtn_close;
    private RadioGroup mRg_runMode;
    private TextView mTv_systemAssist;
    private TextView mTv_virtualSpace;
    private TextView mTv_coreService;
    private TextView mTv_root;
    private Button mBtn_ok;

    private OnClickListener mOnClickListener;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public RunModeDialog(@NonNull Context context) {
        super(context, R.style.RunModeDialog);
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        getWindow().setType(WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = GlobalParams.widthPixels;
        p.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(p);
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        View v = View.inflate(getContext(), R.layout.dialog_run_mode, null);
        setContentView(v);
        mBtn_close = v.findViewById(R.id.btn_close);
        mRg_runMode = v.findViewById(R.id.rg_runMode);
        mTv_systemAssist = v.findViewById(R.id.tv_systemAssist);
        mTv_virtualSpace = v.findViewById(R.id.tv_virtualSpace);
        mTv_coreService = v.findViewById(R.id.tv_coreService);
        mTv_root = v.findViewById(R.id.tv_root);
        mBtn_ok = v.findViewById(R.id.btn_ok);

        mBtn_close.setOnClickListener(this);
        mBtn_ok.setOnClickListener(this);
        mRg_runMode.setOnCheckedChangeListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                cancel();
                if (mOnClickListener != null) {
                    mOnClickListener.onRunModeCloseClick();
                }
                break;

            case R.id.btn_ok:
                cancel();
                if (mOnClickListener != null) {
                    mOnClickListener.onRunModeOkClick();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_systemAssist:
                mTv_systemAssist.setVisibility(View.VISIBLE);
                mTv_virtualSpace.setVisibility(View.GONE);
                mTv_coreService.setVisibility(View.GONE);
                mTv_root.setVisibility(View.GONE);
                if (mOnCheckedChangeListener != null) {
                    mOnCheckedChangeListener.onRunModeCheckedChanged(0);
                }
                break;

            case R.id.rb_virtualSpace:
                mTv_systemAssist.setVisibility(View.GONE);
                mTv_virtualSpace.setVisibility(View.VISIBLE);
                mTv_coreService.setVisibility(View.GONE);
                mTv_root.setVisibility(View.GONE);
                if (mOnCheckedChangeListener != null) {
                    mOnCheckedChangeListener.onRunModeCheckedChanged(1);
                }
                break;

            case R.id.rb_coreService:
                mTv_systemAssist.setVisibility(View.GONE);
                mTv_virtualSpace.setVisibility(View.GONE);
                mTv_coreService.setVisibility(View.VISIBLE);
                mTv_root.setVisibility(View.GONE);
                if (mOnCheckedChangeListener != null) {
                    mOnCheckedChangeListener.onRunModeCheckedChanged(2);
                }
                break;

            case R.id.rb_root:
                mTv_systemAssist.setVisibility(View.GONE);
                mTv_virtualSpace.setVisibility(View.GONE);
                mTv_coreService.setVisibility(View.GONE);
                mTv_root.setVisibility(View.VISIBLE);
                if (mOnCheckedChangeListener != null) {
                    mOnCheckedChangeListener.onRunModeCheckedChanged(3);
                }
                break;
        }
    }

    public void setOnClickListener(OnClickListener l) {
        this.mOnClickListener = l;
    }

    public interface OnClickListener {
        /**
         * 关闭按钮
         */
        public void onRunModeCloseClick();

        /**
         * 确认按钮
         */
        public void onRunModeOkClick();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener l) {
        this.mOnCheckedChangeListener = l;
    }

    public interface OnCheckedChangeListener {
        /**
         * 单选按钮被选中
         *
         * @param position 被选中单选按钮的位置
         */
        public void onRunModeCheckedChanged(int position);
    }
}
