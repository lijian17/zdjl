package cn.dxs.zdjl.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.List;

import cn.dxs.zdjl.R;

/**
 * 适配器-脚本编辑管理器
 *
 * @author lijian
 * @date 2020-03-24 20:45
 */
public class EditManagerAdapter extends BaseAdapter{

    private Context context;
    private List<JsonObject> jObjs;

    public EditManagerAdapter(Context context, List<JsonObject> jObjs){
        this.context = context;
        this.jObjs = jObjs;
    }

    @Override
    public int getCount() {
        return jObjs == null ? 0 : jObjs.size() - 1;
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
        EditManagerAdapter.ViewHolder viewHolder = null;
        // 1.使用历史缓存的view对象 减少 布局创建的次数
        if (convertView != null && convertView instanceof RelativeLayout) {// 注意这里对TextView的过滤
            viewHolder = (EditManagerAdapter.ViewHolder) convertView.getTag();// 从口袋里面取出记事本
        } else {
            convertView = View.inflate(context, R.layout.item_edit_manager, null);
            // 2.减少子孩子查询的次数，只是在创建子孩子的时候 获取孩子对象的引用
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);// 把记事本放在父亲的口袋里
        }
        // 寻找子孩子的引用比较消耗资源
        setData(viewHolder, position + 1, jObjs.get(position + 1));
        return convertView;
    }


    private void setData(EditManagerAdapter.ViewHolder v, int position, JsonObject jObj) {
        v.tv_type.setText(position + "." + jObj.get("type").getAsString());
    }

    private EditManagerAdapter.ViewHolder getViewHolder(View v) {
        EditManagerAdapter.ViewHolder viewHolder = new EditManagerAdapter.ViewHolder();
        viewHolder.tv_type = (TextView) v.findViewById(R.id.tv_type);
        return viewHolder;
    }

    /**
     * 定义view对象的容器，记事本用来保存孩子控件的引用。
     *
     * @author lijian
     * @date 2020-03-24 21:02:06
     */
    public static class ViewHolder {
        /**
         * 脚本类型
         */
        public TextView tv_type;
    }

    /**
     * 刷新列表数据
     *
     * @param jObjs
     */
    public void notifyDataSetChanged(List<JsonObject> jObjs) {
        this.jObjs = jObjs;
        notifyDataSetChanged();
    }
}
