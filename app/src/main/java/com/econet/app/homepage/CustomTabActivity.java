package com.econet.app.homepage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.econet.app.solwallet.R;

/**
 * @author dai.jianhui
 */

public class CustomTabActivity extends AppCompatActivity implements CustomerTabView.OnTabCheckListener{
    private CustomerTabView mCustomerTabView;
    private Fragment []mFragmensts;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.BaseTheme);
        setContentView(R.layout.mod001_home_page_tab_select);
        mFragmensts = getFragments();//初始化几个页面
        initView();
    }

    private void initView() {
        mCustomerTabView = (CustomerTabView) findViewById(R.id.custom_tab_container);
        CustomerTabView.Tab tabHome = new CustomerTabView.Tab().setText("Home")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.home_tab01)
                .setPressedIcon(R.mipmap.home_tab01_press);
        mCustomerTabView.addTab(tabHome);
//        CustomerTabView.Tab tabDis = new CustomerTabView.Tab().setText("待办事项")
//                .setColor(getResources().getColor(android.R.color.darker_gray))
//                .setCheckedColor(getResources().getColor(android.R.color.black))
//                .setNormalIcon(R.mipmap.home_tab02)
//                .setPressedIcon(R.mipmap.home_tab02_press);
//        mCustomerTabView.addTab(tabDis);
//        CustomerTabView.Tab tabAttention = new CustomerTabView.Tab().setText("勤务管理")
//                .setColor(getResources().getColor(android.R.color.darker_gray))
//                .setCheckedColor(getResources().getColor(android.R.color.black))
//                .setNormalIcon(R.mipmap.home_tab03)
//                .setPressedIcon(R.mipmap.home_tab03_press);
//        mCustomerTabView.addTab(tabAttention);
        CustomerTabView.Tab tabProfile = new CustomerTabView.Tab().setText("My")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.home_tab04)
                .setPressedIcon(R.mipmap.home_tab04_press);
        mCustomerTabView.addTab(tabProfile);
       //设置监听
        mCustomerTabView.setOnTabCheckListener(this);
       // 默认选中tab
        mCustomerTabView.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(View v, int position) {
        onTabItemSelected(position);
    }

    private void onTabItemSelected(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
            	fragment = mFragmensts[0];
                break;
//            case 1:
//            	CustomToast.showToast(this, "待办事项正在开发中");
//            	fragment = mFragmensts[1];
//                break;

            case 1:
            	fragment = mFragmensts[2];
                break;
            case 2:
                fragment = mFragmensts[3];
                break;
        }
        if(fragment!=null) {
            FragmentManager fragManager = getFragmentManager();
            fragManager.beginTransaction().replace(R.id.home_container, fragment).commit();
        	//getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }
    public static Fragment[] getFragments(){
        Fragment fragments[] = new Fragment[4];
        fragments[0] = FunctionSelectFragment02.newInstance(1);
        fragments[1] = FunctionSelectFragment03.newInstance(2);
        fragments[2] = FunctionSelectFragment03.newInstance(2);
        fragments[3] = FunctionSelectFragment03.newInstance(2);
        return fragments;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
