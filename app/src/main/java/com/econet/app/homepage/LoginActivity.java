package com.econet.app.homepage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.econet.app.ServerBeans.login.LoginBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.dictionary.Dictionary;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.Base64;
import com.econet.app.uitl.CopyCatLog;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.PopupCenterWindow;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.econet.app.uitl.Tools;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author dai.jianhui
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.lyLogin)
    LinearLayout lyLogin;
    @BindView(R.id.lyDocumentType)
    LinearLayout lyDocumentType;
    PopupCenterWindow mListPopup;//列表形式弹出框
    @BindView(R.id.tvDocumentType)
    TextView tvDocumentType;
    @BindView(R.id.lySignUp)
    LinearLayout lySignUp;

    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;

    @BindView(R.id.etIdNumber)
    EditText etIdNumber;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        ButterKnife.bind(LoginActivity.this);
        initWidget();
    }
    private void initWidget()
    {
        lyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//
//                    int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//                    if(writePermission != PackageManager.PERMISSION_GRANTED){
//                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
//                    }
//                    else
//                    {
//                        CopyCatLog copycat=new CopyCatLog();
//                        copycat.addLogString(getExternalCacheDir().toString()+"\n"+ Environment.getExternalStorageDirectory().getPath());
//                    }
//                }
                if(checkData())
                {
                    HashMap<String,String>params=getData();
                    HashMap<String,String>headers=new HashMap<>();
                    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(LoginActivity.this);
                    //headers.put(Constant.Auth.USER_TOKEN,shared.getValueString(Constant.Auth.USER_TOKEN,"no_user_token"));
                    //headers.put(Constant.Auth.USER_ID,shared.getValueString(Constant.Auth.USER_ID,"no_user_id"));
                    headers.put("Accept","*/*");
                    headers.put("Content-Type","application/json");
                    headers.put("User-Agent","PostmanRuntime/7.15.0");
                    String json = JSONObject.toJSONString(params);
                    showLoadingDialog();
                    HttpUtil.getDataFromServerUsePost(Constant.Url.USER_LOG_IN,json,headers,new Callback(){
                        @Override
                        public void onFailure(Call call, IOException e) {
                            hideLoadingDialog();
                            CustomToast.showToast(LoginActivity.this,"access network failed");
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            hideLoadingDialog();
                            LoginBean loginBean=null;
                            try{
                                loginBean = JSON.parseObject(response.body().string(), LoginBean.class);
                            }catch (Exception e)
                            {
                                CustomToast.showToast(LoginActivity.this,"parse data failed");
                            }
                            if(loginBean!=null)
                            {
                                if(loginBean.getResultcode()==200)
                                {
                                    String firstName=loginBean.getResult().getUserinfo().getFirstname();
                                    String lastName=loginBean.getResult().getUserinfo().getLastname();
                                    String healthStatus=loginBean.getResult().getUserinfo().getHealthstatus();
                                    String userToken=loginBean.getResult().getAuthtoken();
                                    String userId=loginBean.getResult().getAuthid();
                                    String userPhone=loginBean.getResult().getUserinfo().getPhone();
                                    SharedPreferencesUtils shared= SharedPreferencesUtils.getInstance(LoginActivity.this);
                                    shared.submitString(Constant.Auth.USER_ID,userId);
                                    shared.submitString(Constant.Auth.USER_TOKEN,userToken);
                                    shared.submitString(Constant.Auth.FIRST_NAME,firstName);
                                    shared.submitString(Constant.Auth.LAST_NAME,lastName);
                                    shared.submitString(Constant.Auth.HEALTH_STATUS,healthStatus);
                                    shared.submitString(Constant.Auth.USER_PHONE,userPhone);
                                    shared.submitString(Constant.Basic.BASIC_NAME,firstName+" "+lastName);
                                    shared.submitString(Constant.Basic.BASIC_PHONE,userPhone);
                                    //add user info
                                    shared.submitString(Constant.User.LOGIN_TYPE,Dictionary.getIdType(tvDocumentType.getText().toString()));
                                    shared.submitString(Constant.User.LOGIN_PASSWORD,Base64.encodeBytes(etPassword.getText().toString().trim().getBytes()));
                                    shared.submitString(Constant.User.LOGIN_ID,etIdNumber.getText().toString());
                                    Intent i = new Intent(getApplicationContext(), CustomTabActivity.class);
                                    startActivityForResult(i, 1001);
                                }else
                                {
                                    CustomToast.showToast(LoginActivity.this,loginBean.getResultmsg());
                                }
                            }
                        }
                    });
                }
            }
        });
        lyDocumentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPopup.popupUpData(lyDocumentType, Dictionary.getDocumentType());
            }
        });
        mListPopup = new PopupCenterWindow(this, (int) (0.8 * Tools.getDisplayMetrics(this).widthPixels),
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        mListPopup.dismiss();
                        tvDocumentType.setText(""+Dictionary.getDocumentType().get(position));

                    }
                });
        lySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(i,346);
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(i);
            }
        });
    }
    private HashMap<String,String> getData()
    {
        HashMap<String,String>params=new HashMap<>();
        params.put("idType",Dictionary.getIdType(tvDocumentType.getText().toString()));
        params.put("userType","Personal");
        params.put("idNo",etIdNumber.getText().toString());
        params.put("password", Base64.encodeBytes(etPassword.getText().toString().trim().getBytes()));
        return params;
    }
    private boolean checkData()
    {
        if(TextUtils.isEmpty(tvDocumentType.getText().toString()))
        {
            CustomToast.showToast(LoginActivity.this,"please select the document type");
            return false;
        }
        if(TextUtils.isEmpty(etIdNumber.getText().toString()))
        {
            CustomToast.showToast(LoginActivity.this,"please enter the id number");
            return false;
        }
        if(TextUtils.isEmpty(etPassword.getText().toString()))
        {
            CustomToast.showToast(LoginActivity.this,"please enter the password");
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1001){
            if(permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CopyCatLog copycat=new CopyCatLog();
                copycat.addLogString(getExternalCacheDir().toString()+"\n"+ Environment.getExternalStorageDirectory().getPath());
            }
        }
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
        if(resultCode==250)//use this code to login
        {
            HashMap<String,String>params=getData2();
            HashMap<String,String>headers=new HashMap<>();
            SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(LoginActivity.this);
            //headers.put(Constant.Auth.USER_TOKEN,shared.getValueString(Constant.Auth.USER_TOKEN,"no_user_token"));
            //headers.put(Constant.Auth.USER_ID,shared.getValueString(Constant.Auth.USER_ID,"no_user_id"));
            headers.put("Accept","*/*");
            headers.put("Content-Type","application/json");
            headers.put("User-Agent","PostmanRuntime/7.15.0");
            String json = JSONObject.toJSONString(params);
            showLoadingDialog();
            HttpUtil.getDataFromServerUsePost(Constant.Url.USER_LOG_IN,json,headers,new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    hideLoadingDialog();
                    CustomToast.showToast(LoginActivity.this,"access network failed");
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    hideLoadingDialog();
                    LoginBean loginBean=null;
                    try{
                        loginBean = JSON.parseObject(response.body().string(), LoginBean.class);
                    }catch (Exception e)
                    {
                        CustomToast.showToast(LoginActivity.this,"parse data failed");
                    }
                    if(loginBean!=null)
                    {
                        if(loginBean.getResultcode()==200)
                        {
                            String firstName=loginBean.getResult().getUserinfo().getFirstname();
                            String lastName=loginBean.getResult().getUserinfo().getLastname();
                            String healthStatus=loginBean.getResult().getUserinfo().getHealthstatus();
                            String userToken=loginBean.getResult().getAuthtoken();
                            String userId=loginBean.getResult().getAuthid();
                            String userPhone=loginBean.getResult().getUserinfo().getPhone();
                            SharedPreferencesUtils shared= SharedPreferencesUtils.getInstance(LoginActivity.this);
                            shared.submitString(Constant.Auth.USER_ID,userId);
                            shared.submitString(Constant.Auth.USER_TOKEN,userToken);
                            shared.submitString(Constant.Auth.FIRST_NAME,firstName);
                            shared.submitString(Constant.Auth.LAST_NAME,lastName);
                            shared.submitString(Constant.Auth.HEALTH_STATUS,healthStatus);
                            shared.submitString(Constant.Auth.USER_PHONE,userPhone);
                            //add basic name
                            shared.submitString(Constant.Basic.BASIC_NAME,firstName+" "+lastName);
                            shared.submitString(Constant.Basic.BASIC_PHONE,userPhone);
                            Intent i = new Intent(getApplicationContext(), CustomTabActivity.class);
                            startActivityForResult(i, 1001);
                        }else
                        {
                            CustomToast.showToast(LoginActivity.this,loginBean.getResultmsg());
                        }
                    }
                }
            });
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
    private HashMap<String,String> getData2()
    {
        HashMap<String,String>params=new HashMap<>();
        SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(LoginActivity.this);
        params.put("idType",shared.getValueString(Constant.User.LOGIN_TYPE,""));
        params.put("userType","Personal");
        params.put("idNo", shared.getValueString(Constant.User.LOGIN_ID,""));
        params.put("password", shared.getValueString(Constant.User.LOGIN_PASSWORD,""));
        return params;
    }
}
