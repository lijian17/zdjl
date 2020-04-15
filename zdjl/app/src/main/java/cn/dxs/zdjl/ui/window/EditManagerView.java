package cn.dxs.zdjl.ui.window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import cn.dxs.zdjl.GlobalParams;
import cn.dxs.zdjl.R;
import cn.dxs.zdjl.domain.ScriptContent;
import cn.dxs.zdjl.domain.ScriptInfo;
import cn.dxs.zdjl.ui.adapter.EditManagerAdapter;

/**
 * 脚本编辑管理器
 *
 * @author lijian
 * @date 2020-03-30 10:58
 */
public class EditManagerView extends LinearLayout implements View.OnClickListener {

    private static final int HANDLER_DATA = 1000;
    private WindowManager windowManager;
    private WindowManager.LayoutParams mParams;
    private int mStartX;// 当前手指触摸点坐标X
    private int mStartY;// 当前手指触摸点坐标Y

    private View v;
    private TextView mTv_scriptName;
    private Button mBtn_set;
    private Button mBtn_close;
    private ListView mLv_item;
    private Button mBtn_run;
    private Button mBtn_transcribe;
    private Button mBtn_more;
    private PopupWindow mPopupWindow;

    private EditManagerAdapter mAdp_editManager;
    private ScriptInfo scriptInfo;
    private ScriptContent scriptContent;

    private OnClickListener mOnClickListener;

    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_DATA:
                    setAdpData();
                    break;
            }
        };
    };

    public EditManagerView(Context context, WindowManager windowManager) {
        super(context);
        this.windowManager = windowManager;
        init();
    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        v = View.inflate(getContext(), R.layout.dialog_edit_manager, null);
        addView(v);
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

        setWindowParams();
    }

    private void setWindowParams() {
        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        mParams.format = PixelFormat.TRANSPARENT;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.width = GlobalParams.widthPixels * 4 / 9;
        mParams.height = GlobalParams.heightPixels / 4;
        mParams.x = (GlobalParams.widthPixels - mParams.width) / 2;
        mParams.y = 0;
        // mParams.alpha = 0.5f;

        windowManager.addView(this, mParams);
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 手指按下
                touchAction_down(event);
                break;

            case MotionEvent.ACTION_MOVE:// 手指移动
                touchAction_move(event);
                break;

            case MotionEvent.ACTION_UP:// 手指抬起
                touchAction_up();
        }
        return super.onTouchEvent(event);
    }

    private void touchAction_down(MotionEvent event) {
        event.getRawX();
        mStartX = (int) event.getRawX();
        mStartY = (int) event.getRawY();
    }

    private void touchAction_move(MotionEvent event) {
        int newX = (int) event.getRawX();
        int newY = (int) event.getRawY();

        int dx = newX - mStartX;
        int dy = newY - mStartY;

        // 立刻让控件也跟随着手指移动 dx dy。
        mParams.x += dx;
        mParams.y += dy;

        // 超出边界修正
        if (mParams.x < 0) {
            mParams.x = 0;
        }
        if (mParams.y < 0) {
            mParams.y = 0;
        }
        if (mParams.x > (GlobalParams.widthPixels - this.getWidth())) {
            mParams.x = GlobalParams.widthPixels - this.getWidth();
        }
        if (mParams.y > (GlobalParams.heightPixels - this.getHeight())) {
            mParams.y = GlobalParams.heightPixels - this.getHeight();
        }

        windowManager.updateViewLayout(this, mParams);

        // 重复第一步的操作 ，重新初始化手指的开始位置。
        mStartX = (int) event.getRawX();
        mStartY = (int) event.getRawY();
    }

    private void touchAction_up() {
        // L.i(TAG, "手指抬起");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set:
                Toast.makeText(getContext(), "设置", Toast.LENGTH_SHORT).show();
                if (mOnClickListener != null) {
                    mOnClickListener.onEditManagerSetClick();
                }
                break;

            case R.id.btn_close:
                Toast.makeText(getContext(), "关闭", Toast.LENGTH_SHORT).show();
                if (mOnClickListener != null) {
                    mOnClickListener.onEditManagerCloseClick();
                }
                windowManager.removeViewImmediate(this);
                break;

            case R.id.btn_run:
                Toast.makeText(getContext(), "运行", Toast.LENGTH_SHORT).show();
                if (mOnClickListener != null) {
                    mOnClickListener.onEditManagerRunClick();
                }
                break;

            case R.id.btn_transcribe:
                Toast.makeText(getContext(), "录制", Toast.LENGTH_SHORT).show();
                if (mOnClickListener != null) {
                    mOnClickListener.onEditManagerTranscribeClick();
                }
                break;

            case R.id.btn_more:
                Toast.makeText(getContext(), "更多", Toast.LENGTH_SHORT).show();
                showPopupWindow(v);
                break;

            case R.id.btn_clear:
                Toast.makeText(getContext(), "清空", Toast.LENGTH_SHORT).show();
                dismissPopupWindow();
                if (mOnClickListener != null) {
                    mOnClickListener.onEditManagerClearClick();
                }
                break;

            case R.id.btn_save:
                Toast.makeText(getContext(), "保存", Toast.LENGTH_SHORT).show();
                dismissPopupWindow();
                if (mOnClickListener != null) {
                    mOnClickListener.onEditManagerSaveClick();
                }
                break;
        }
    }

    /**
     * 显示一个PopupWindow
     *
     * @param view
     */
    private void showPopupWindow(View view) {
        View v = View.inflate(getContext(), R.layout.item_more, null);
        Button clear = v.findViewById(R.id.btn_clear);
        Button save = v.findViewById(R.id.btn_save);
        clear.setOnClickListener(this);
        save.setOnClickListener(this);

        mPopupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, 200);// 设置浮窗及其大小
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        int[] location = new int[2];
        view.getLocationInWindow(location);// 获得所点击的view的距屏幕x,y的坐标
        mPopupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP, location[0], location[1] - 200);// 设置相对于父窗体位置

        AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f);// 定义渐变动画
        aa.setDuration(200);
        // 定义缩放动画
        ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 0.5f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f);
        sa.setDuration(200);
        AnimationSet set = new AnimationSet(false);// 定义动画数组
        set.addAnimation(aa);
        set.addAnimation(sa);
        v.startAnimation(set);// 播放动画
    }

    /**
     * 关闭PopupWindow
     */
    private void dismissPopupWindow() {
        if (mPopupWindow != null) {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            }
            mPopupWindow = null;
        }
    }

    public void setOnClickButtonListener(OnClickListener l) {
        this.mOnClickListener = l;
    }

    public interface OnClickListener {
        /**
         * 设置按钮（小齿轮）
         */
        public void onEditManagerSetClick();

        /**
         * 关闭按钮
         */
        public void onEditManagerCloseClick();

        /**
         * 运行按钮
         */
        public void onEditManagerRunClick();

        /**
         * 录制按钮
         */
        public void onEditManagerTranscribeClick();

        /**
         * 清空按钮
         */
        public void onEditManagerClearClick();

        /**
         * 保存按钮
         */
        public void onEditManagerSaveClick();
    }
}
