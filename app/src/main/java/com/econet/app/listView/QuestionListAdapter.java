package com.econet.app.listView;

import com.econet.app.ServerBeans.historylist.Recordlist;
import com.econet.app.solwallet.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author dai.jianhui
 */
public class QuestionListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Recordlist> mList;
    private LayoutInflater mInflater;

    public QuestionListAdapter(Context context, List<Recordlist> list) {
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
            convertView = mInflater.inflate(R.layout.list_item_question, null);
            viewHolder = new ViewHolder();
            viewHolder.tvLeaveTime = (TextView) convertView.findViewById(R.id.tvLeaveTime);
            viewHolder.tvLeavePerson = (TextView) convertView.findViewById(R.id.tvLeavePerson);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Recordlist recordlist = mList.get(position);
       // String time=new SimpleDateFormat("yyyyy-MM-dd hh:mm:ss").format(recordlist.getRecorddate());
        viewHolder.tvLeaveTime.setText(recordlist.getRecorddate());
        viewHolder.tvLeavePerson.setText(recordlist.getRecordTitle());
        return convertView;
    }

    public class ViewHolder {
        TextView tvLeaveSupplyState;
        TextView tvLeaveTime;
        TextView tvLeavePerson;
    }

}
