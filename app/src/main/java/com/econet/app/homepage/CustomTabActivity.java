package com.econet.app.homepage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//import com.alibaba.ha.adapter.AliHaAdapter;
//import com.alibaba.ha.adapter.AliHaConfig;
//import com.alibaba.ha.adapter.Plugin;
//import com.alibaba.ha.adapter.service.tlog.TLogLevel;
//import com.alibaba.ha.adapter.service.tlog.TLogService;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.SharedPreferencesHelper;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author dai.jianhui
 */

public class CustomTabActivity extends AppCompatActivity implements CustomerTabView.OnTabCheckListener{
    private CustomerTabView mCustomerTabView;
    private Fragment []mFragmensts;
    SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.BaseTheme);
        setContentView(R.layout.mod001_home_page_tab_select);
        mFragmensts = getFragments();//初始化几个页面
        initView();
        //友盟相关统计
        sharedPreferencesHelper=new SharedPreferencesHelper(this,"umeng");
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //友盟预初始化
        UMConfigure.preInit(getApplicationContext(),"617dfcfde0f9bb492b457de1","Umeng");
        //友盟正式初始化
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(getApplicationContext(), "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "669c30a9584623e70e8cd01b0381dcb4");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

//        AliHaConfig config = new AliHaConfig();
//        config.appKey = "333727597"; //配置项：appkey
//        config.appVersion = "1.6"; //配置项：应用的版本号
//        config.appSecret = "710b9bad31834594902a81bc4f583df9"; //配置项：appsecret
//        config.channel = "mqc_test"; //配置项：应用的渠道号标记，自定义
//        config.userNick = "copycat"; //配置项：用户的昵称
//        config.application = this.getApplication(); //配置项：应用指针
//        config.context = getApplicationContext(); //配置项：应用上下文
//        config.isAliyunos = false; //配置项：是否为yunos
//        config.rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdaeWy93lgZam4fKrzOHu2FUcYra8265Jag7nB6vdvHZA0T732DsQJkfj/NhkMKa8E7q5nrZrVdF6imdNdYT2fKZ0kYGv2z6NakjTvPlr9W3zexGsmr3dlMDFOCKwB7YQayqM1Sr6GnRWAsqP9QR2twxBiNJK190otHY+4luXTYwIDAQAB"; //配置项：tlog公钥
//        AliHaAdapter.getInstance().addPlugin(Plugin.tlog);
//        AliHaAdapter.getInstance().openDebug(true);
//        AliHaAdapter.getInstance().start(config);
//        TLogService.updateLogLevel(TLogLevel.INFO); //配置项：控制台可拉取的日志级别
    }

    private void initView() {
        mCustomerTabView = (CustomerTabView) findViewById(R.id.custom_tab_container);
        CustomerTabView.Tab tabHome = new CustomerTabView.Tab().setText("Home")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.home_tab01)
                .setPressedIcon(R.mipmap.home_tab01_press);
        mCustomerTabView.addTab(tabHome);
        CustomerTabView.Tab tabDis = new CustomerTabView.Tab().setText("Meta")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.home_tab02_press)
                .setPressedIcon(R.mipmap.home_tab02_press);
        mCustomerTabView.addTab(tabDis);
//        CustomerTabView.Tab tabAttention = new CustomerTabView.Tab().setText("ETH")
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
            case 1:
            	fragment = mFragmensts[1];
                break;

            case 2:
            	fragment = mFragmensts[3];
                break;
            case 3:
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
        fragments[1] = FunctionSelectFragment04.newInstance(2);
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
