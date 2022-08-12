
package com.econet.app.uitl;

import java.util.List;

import com.econet.app.solwallet.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author dai.jianhui
 */
public class PopCenterdapter extends BaseAdapter {

    /**
     * 上下文
     */
    private Context context;
    /**
     * 数据源
     */
    private List<String> list;
    /**
     * 布局生成器
     */
    private LayoutInflater layoutinflator;

    public PopCenterdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        layoutinflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = layoutinflator.inflate(R.layout.pop_center_list_item, null);
            viewholder = new ViewHolder();
            viewholder.tvGroupTitle = (TextView) convertView.findViewById(R.id.tvGroupTitle);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        String order = list.get(position);
        viewholder.tvGroupTitle.setText(order);
        return convertView;
    }

    class ViewHolder {

        TextView tvGroupTitle;
        ImageView ivHead;
        View viewLine;
    }
}
