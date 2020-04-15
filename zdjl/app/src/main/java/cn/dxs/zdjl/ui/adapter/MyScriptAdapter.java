package cn.dxs.zdjl.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.dxs.zdjl.R;
import cn.dxs.zdjl.domain.ScriptInfo;
import cn.dxs.zdjl.utils.AppToast;

/**
 * 适配器-我的脚本
 *
 * @author lijian
 * @date 2020-03-24 20:45
 */
public class MyScriptAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<ScriptInfo> scriptInfos;

    public MyScriptAdapter(Context context, List<ScriptInfo> scriptInfos){
        this.context = context;
        this.scriptInfos = scriptInfos;
    }

    @Override
    public int getCount() {
        return scriptInfos == null ? 0 : scriptInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyScriptAdapter.ViewHolder viewHolder = null;
        // 1.使用历史缓存的view对象 减少 布局创建的次数
        if (convertView != null && convertView instanceof RelativeLayout) {// 注意这里对TextView的过滤
            viewHolder = (MyScriptAdapter.ViewHolder) convertView.getTag();// 从口袋里面取出记事本
        } else {
            convertView = View.inflate(context, R.layout.item_myscript, null);
            // 2.减少子孩子查询的次数，只是在创建子孩子的时候 获取孩子对象的引用
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);// 把记事本放在父亲的口袋里
        }
        // 寻找子孩子的引用比较消耗资源
        setData(viewHolder, scriptInfos.get(position));
        return convertView;
    }


    private void setData(MyScriptAdapter.ViewHolder v, ScriptInfo info) {
        v.tv_scriptName.setText(info.getName());
        v.tv_scriptSize.setText(info.getSize());
        v.tv_createTime.setText(info.getCreateTime());
        v.btn_run.setOnClickListener(this);
        v.btn_run.setTag(info);
    }

    private MyScriptAdapter.ViewHolder getViewHolder(View v) {
        MyScriptAdapter.ViewHolder viewHolder = new MyScriptAdapter.ViewHolder();
        viewHolder.tv_scriptName = (TextView) v.findViewById(R.id.tv_scriptName);
        viewHolder.tv_scriptSize = (TextView) v.findViewById(R.id.tv_scriptSize);
        viewHolder.btn_run = (Button) v.findViewById(R.id.btn_run);
        viewHolder.tv_createTime = (TextView) v.findViewById(R.id.tv_createTime);
        return viewHolder;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_run:
                ScriptInfo info = (ScriptInfo) view.getTag();
                AppToast.getInstance().show(info.getName());
                break;
        }
    }

    /**
     * 定义view对象的容器，记事本用来保存孩子控件的引用。
     *
     * @author lijian
     * @date 2020-03-24 21:02:06
     */
    public static class ViewHolder {
        /**
         * 脚本名称
         */
        public TextView tv_scriptName;
        /**
         * 脚本文件大小
         */
        public TextView tv_scriptSize;
        /**
         * 运行按钮
         */
        public Button btn_run;
        /**
         * 脚本创建时间
         */
        public TextView tv_createTime;
    }

    /**
     * 刷新列表数据
     *
     * @param scriptInfos
     */
    public void notifyDataSetChanged(List<ScriptInfo> scriptInfos) {
        this.scriptInfos = scriptInfos;
        notifyDataSetChanged();
    }
}
