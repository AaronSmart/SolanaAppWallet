package com.econet.app.homepage;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.econet.app.ServerBeans.login.LoginBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.econet.app.uitl.SignCheckUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author dai.jianhui
 */
public class LancherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancher);
        ButterKnife.bind(LancherActivity.this);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(R.color.lancher));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initWidget();
        signCheck();
        killRoot();
        SharedPreferencesUtils shared= SharedPreferencesUtils.getInstance(LancherActivity.this);
        if(!"null".equals(shared.getValueString(Constant.Auth.USER_TOKEN,"null")))
        {
            Timer timer=new Timer();
            TimerTask task=new TimerTask(){
                public void run()
                {
                    Looper.prepare();
                    HashMap<String,String>params=new HashMap<>();
                    SharedPreferencesUtils shared= SharedPreferencesUtils.getInstance(LancherActivity.this);
                    params.put("idType",shared.getValueString(Constant.User.LOGIN_TYPE,"no-data"));
                    params.put("userType","Personal");
                    params.put("idNo",shared.getValueString(Constant.User.LOGIN_ID,"no-data"));
                    params.put("password", shared.getValueString(Constant.User.LOGIN_PASSWORD,"no-data"));
                    HashMap<String,String>headers=new HashMap<>();
                    headers.put("Accept","*/*");
                    headers.put("Content-Type","application/json");
                    String json = JSONObject.toJSONString(params);
                   // showLoadingDialog();
                    HttpUtil.getDataFromServerUsePost(Constant.Url.USER_LOG_IN,json,headers,new Callback(){
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //hideLoadingDialog();
                            CustomToast.showToast(LancherActivity.this,"access network failed");
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //hideLoadingDialog();
                            LoginBean loginBean=null;
                            try{
                                loginBean = JSON.parseObject(response.body().string(), LoginBean.class);
                            }catch (Exception e)
                            {
                                CustomToast.showToast(LancherActivity.this,"parse data failed");
                            }
                            if(loginBean!=null)
                            {
                                if(loginBean.getResultcode()==200)
                                {
                                    String firstName=loginBean.getResult().getUserinfo().getFirstname();
                                    String lastName=loginBean.getResult().getUserinfo().getLastname();
                                    String healthStatus=loginBean.getResult().getUserinfo().getHealthstatus();
                                    String healthStatusTime=loginBean.getResult().getUserinfo().getHealthstatustime();
                                    String userToken=loginBean.getResult().getAuthtoken();
                                    String userId=loginBean.getResult().getAuthid();
                                    String userPhone=loginBean.getResult().getUserinfo().getPhone();
                                    SharedPreferencesUtils shared= SharedPreferencesUtils.getInstance(LancherActivity.this);
                                    shared.submitString(Constant.Auth.USER_ID,userId);
                                    shared.submitString(Constant.Auth.USER_TOKEN,userToken);
                                    shared.submitString(Constant.Auth.FIRST_NAME,firstName);
                                    shared.submitString(Constant.Auth.LAST_NAME,lastName);
                                    shared.submitString(Constant.Auth.HEALTH_STATUS,healthStatus);
                                    shared.submitString(Constant.Auth.HEALTH_STATUS_TIME,healthStatusTime);
                                    shared.submitString(Constant.Auth.USER_PHONE,userPhone);
                                    Intent i = new Intent(getApplicationContext(), CustomTabActivity.class);
                                    startActivityForResult(i, 1001);
                                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                                }else
                                {
                                    //CustomToast.showToast(LoginActivity.this,loginBean.getResultmsg());
                                }
                            }
                            // CustomToast.showToast(LoginActivity.this,response.body().string());
                        }
                    });
                    Looper.loop();
                }
            };
            timer.schedule(task, 2000);
        }
        else
        {
            Timer timer=new Timer();
            TimerTask task=new TimerTask(){
                public void run() {
                    Intent i = new Intent(LancherActivity.this, LoginActivity.class);
                    startActivityForResult(i,1002);
                }
            };
            timer.schedule(task, 1500);
        }
    }
    private void initWidget()
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                this.finish();
            }
        }
        if (requestCode == 1003) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                this.finish();
            }
        }
        else
        {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                this.finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
    private void signCheck() {
        SignCheckUtil signCheckUtil = new SignCheckUtil(getApplicationContext(),
                "72:0F:DF:13:5A:1E:16:BA:16:B8:05:F4:4C:54:C9:E4:A6:BF:01:4C");
        if (!signCheckUtil.check()) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    private void killRoot()
    {
        if(checkRootPathSU())
        {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    private  static boolean checkRootPathSU()
    {
        File f=null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/","/vendor/bin/"};
        try
        {
            for (int i = 0; i < kSuSearchPaths.length; i++)
            {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    return true;
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
         return false;
    }
}
