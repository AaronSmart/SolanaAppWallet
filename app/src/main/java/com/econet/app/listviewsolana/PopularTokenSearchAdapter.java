package com.econet.app.listviewsolana;

import android.widget.BaseAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.econet.app.solanaUtil.ToolUtil;
import com.econet.app.solwallet.R;

import com.econet.app.solanabean.mintTokenList.Tokens;

import java.util.List;

public class PopularTokenSearchAdapter extends BaseAdapter {
    /**
     * 上下文
     */
    private Activity context;
    /**
     * 数据源
     */
    private List<Tokens> list;
    /**
     * 布局生成器
     */
    private LayoutInflater layoutinflator;
    /**
     * 下标位置
     */
    private int mPosition = 0;


    public PopularTokenSearchAdapter(Activity context, List<Tokens> list) {
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
            convertView = layoutinflator.inflate(R.layout.list_item_popular_token, null);
            viewholder = new ViewHolder();
            viewholder.tvSymbol = (TextView) convertView.findViewById(R.id.tvSymbol);
            viewholder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewholder.tvMint = (TextView) convertView.findViewById(R.id.tvMint);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        Tokens order = list.get(position);
        viewholder.tvSymbol.setText("Symbol: "+order.getSymbol());
        viewholder.tvName.setText("Describe: "+order.getName());
        viewholder.tvMint.setText("MintCode: "+ToolUtil.getAbbrForMint(order.getAddress()));
        return convertView;
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    class ViewHolder {
        TextView tvSymbol;
        TextView tvName;
        TextView tvMint;
    }
}
