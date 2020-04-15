package cn.dxs.zdjl.base;


import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author lijian
 * @date 2020-03-24 14:51
 */
public abstract class BaseActivity extends AppCompatActivity {


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            parserMessage(msg);
        };
    };

    /**
     * 处理Handler Message
     *
     * @param msg 消息包
     */
    protected void parserMessage(Message msg) {
        if (msg == null) {
            showToast("message is null");
            return;
        }
    }

    /**
     * 发送handler消息
     *
     * @param what
     * @param obj
     */
    public void sendMsg(int what, Object obj) {
        Message _msg = mHandler.obtainMessage();
        _msg.what = what;
        _msg.obj = obj;
        mHandler.sendMessage(_msg);
    }
}
