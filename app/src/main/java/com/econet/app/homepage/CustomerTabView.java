package com.econet.app.homepage;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.econet.app.solwallet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe 自定义TabView,如果使用TabLayout需要使用Android-support-design开发包
 * @author dai.jianhui
 *
 */
public class CustomerTabView extends LinearLayout implements View.OnClickListener{
    private List<View> mTabViews;//保存TabView 
    private List<Tab> mTabs;// 保存Tab
    private OnTabCheckListener mOnTabCheckListener;

    public void setOnTabCheckListener(OnTabCheckListener onTabCheckListener) {
        mOnTabCheckListener = onTabCheckListener;
    }

    public CustomerTabView(Context context) {
        super(context);
        init();
    }

    public CustomerTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomerTabView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomerTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.WHITE);
        mTabViews = new ArrayList<>();
        mTabs = new ArrayList<>();

    }

    public void addTab(Tab tab){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.mod001_home_tab_item_layout,null);
        TextView textView = (TextView) view.findViewById(R.id.custom_tab_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.custom_tab_icon);
        imageView.setImageResource(tab.mIconNormalResId);
        textView.setText(tab.mText);
        textView.setTextColor(tab.mNormalColor);

        view.setTag(mTabViews.size());
        view.setOnClickListener(this);

        mTabViews.add(view);
        mTabs.add(tab);

        addView(view);

    }

    public void setCurrentItem(int position){
        if(position>=mTabs.size() || position<0){
            position = 0;
        }
         mTabViews.get(position).performClick();
        updateState(position);

    }

    private void updateState(int position){
        for(int i= 0;i<mTabViews.size();i++){
            View view = mTabViews.get(i);
            TextView textView = (TextView) view.findViewById(R.id.custom_tab_text);
            ImageView imageView = (ImageView) view.findViewById(R.id.custom_tab_icon);
            if(i == position){
                imageView.setImageResource(mTabs.get(i).mIconPressedResId);
                textView.setTextColor(mTabs.get(i).mSelectColor);
            }else{
                imageView.setImageResource(mTabs.get(i).mIconNormalResId);
                textView.setTextColor(mTabs.get(i).mNormalColor);
            }
        }
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if(mOnTabCheckListener!=null){
            mOnTabCheckListener.onTabSelected(v, position);
        }

        updateState(position);
    }

    public interface  OnTabCheckListener{
        public void onTabSelected(View v, int position);
    }


    public static class Tab{
        private int mIconNormalResId;
        private int mIconPressedResId;
        private int mNormalColor;
        private int mSelectColor;
        private String mText;


        public Tab setText(String text){
            mText = text;
            return this;
        }

        public Tab setNormalIcon(int res){
            mIconNormalResId = res;
            return this;
        }

        public Tab setPressedIcon(int res){
            mIconPressedResId = res;
            return this;
        }

        public Tab setColor(int color){
            mNormalColor = color;
            return this;
        }

        public Tab setCheckedColor(int color){
            mSelectColor = color;
            return this;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mTabViews!=null){
            mTabViews.clear();
        }
        if(mTabs!=null){
            mTabs.clear();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
       // 调整每个Tab的大小
        for(int i=0;i<mTabViews.size();i++){
            View view = mTabViews.get(i);
            int width = getResources().getDisplayMetrics().widthPixels / (mTabs.size());
            LayoutParams params = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
        }

    }
}