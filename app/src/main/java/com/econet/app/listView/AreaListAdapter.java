package com.econet.app.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.econet.app.solwallet.R;

import java.util.List;

/**
 * @author dai.jianhui
 */
public class AreaListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater mInflater;

    public AreaListAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_area, null);
            viewHolder = new ViewHolder();
            viewHolder.tvCity = (TextView) convertView.findViewById(R.id.tvCity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String city = mList.get(position);
        viewHolder.tvCity.setText(city);
        return convertView;
    }


    public class ViewHolder {
        TextView tvCity;
    }

}
