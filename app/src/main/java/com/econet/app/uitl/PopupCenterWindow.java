
package com.econet.app.uitl;

import java.util.ArrayList;
import java.util.List;
import com.econet.app.solwallet.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * 
 * <P><B>Description: </B>  popwindow  </P>
 
 *                 2016年3月22日 Wade Huang CREATE
 * @author Wade Huang
 * @version 1.0
 */
public class PopupCenterWindow {

    /**
     * 
     */
    private PopupWindow popupWindow;
    /**
     * 列表
     */
    private ListView sortListView;
    /**
     * 适配器
     */
    private PopCenterdapter sortAdapter;
    /**
     * 数据源
     */
    private List<String> sortlist = new ArrayList<String>();

    public PopupCenterWindow(Context context, int width, OnItemClickListener onitem) {
        //sortlist.addAll(Arrays.asList(context.getResources().getStringArray(
        //R.array.circle_edit_1)));
        View view = LayoutInflater.from(context).inflate(R.layout.popup_center, null);
        sortAdapter = new PopCenterdapter(context, sortlist);
        sortListView = (ListView) view.findViewById(R.id.listView);
        sortListView.setAdapter(sortAdapter);
        popupWindow = new PopupWindow(view, width, LayoutParams.WRAP_CONTENT, true);// 这个用于显示popupWindow显示的宽高。
        popupWindow.setBackgroundDrawable(new BitmapDrawable());// 加这个是可以点击pop外的任意区域可以让pop消失
        popupWindow.setFocusable(true);
        sortListView.setOnItemClickListener(onitem);
    }

    public void showPorListener(View v, List<String> list, OnItemClickListener onitme) {
        sortListView.setOnItemClickListener(onitme);
        popupUpData(v, list);
    }

    public void popupUpData(View v, List<String> list) {
        sortlist.clear();
        sortlist.addAll(list);
        sortAdapter.notifyDataSetChanged();
        showPop(v);
    }

    public void popupUpData2(View v, List<String> list) {
        sortlist.clear();
        sortlist.addAll(list);
        sortAdapter.notifyDataSetChanged();
        showPop2(v);
    }

    public void popupUpData(View v) {
        showPopClock(v, 0);
    }

    public void popupUpDataClock(View v, List<String> list, int popXY) {
        sortlist.clear();
        sortlist.addAll(list);
        sortAdapter.notifyDataSetChanged();
        showPopClock(v, popXY);
    }

    public void popupUpNewSport(View v, List<String> list) {
        sortlist.clear();
        sortlist.addAll(list);
        sortAdapter.notifyDataSetChanged();
        showNewSport(v);
    }

    public void showPop(View v) {
        //popupWindow.showAsDropDown(v,0,0);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    public void showPop2(View v) {
        //popupWindow.showAsDropDown(v,0,0);
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    public void showNewSport(View v) {
        popupWindow.showAsDropDown(v, 0, 25);
        //popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        //popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    public void showPopClock(View v, int popXY) {
        //popupWindow.showAtLocation(v, Gravity.RIGHT |Gravity.TOP, 10, popXY);
        popupWindow.showAsDropDown(v, 0, 0);
        //popupWindow.showAtLocation(v, Gravity.RIGHT |Gravity.TOP, 10, 10);
        // popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }


}
