package cn.dxs.zdjl.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.dxs.zdjl.App;
import cn.dxs.zdjl.R;

/**
 * 全局Toast
 *
 * @author lijian
 * @date 2020-03-25 10:35
 */
public class AppToast extends Toast {

    private static AppToast instance = null;
    private View layout;
    private TextView text;

    private AppToast() {
        super(App.getContext());
        init();
    }

    public static AppToast getInstance() {
        if (instance == null) {
            instance = new AppToast();
        }
        return instance;
    }

    private void init() {
        LayoutInflater inflate = (LayoutInflater) App.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflate.inflate(R.layout.toast, null);
        text = (TextView) layout.findViewById(R.id.message);
        this.setView(layout);
        // 第一个参数：设置toast在屏幕中显示的位置。我现在的设置是居中靠顶
        // 第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
        // 第三个参数：同的第二个参数道理一样
        // 如果你设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
        this.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 200);
        // 屏幕居中显示，X轴和Y轴偏移量都是0
        // this.setGravity(Gravity.CENTER, 0, 0);
    }

    public void show(String msg) {
        text.setText(msg);
        this.setDuration(Toast.LENGTH_LONG);
        this.show();
    }

    public void show(int msg) {
        text.setText(msg);
        this.setDuration(Toast.LENGTH_LONG);
        this.show();
    }
}
