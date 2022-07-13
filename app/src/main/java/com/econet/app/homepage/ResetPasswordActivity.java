package com.econet.app.homepage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.econet.app.ServerBeans.resetpassword.ResetPasswordBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.dictionary.Dictionary;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.Base64;
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
public class ResetPasswordActivity extends BaseActivity {

    @BindView(R.id.lyDocumentType)
    LinearLayout lyDocumentType;
    PopupCenterWindow mListPopup;
    @BindView(R.id.tvDocumentType)
    TextView tvDocumentType;
    @BindView(R.id.lyReset)
    LinearLayout lyReset;
    @BindView(R.id.etIdNumber)
    EditText etIdNumber;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivBackLinear)
    LinearLayout ivBackLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgot_password);
        ButterKnife.bind(this);
        initWidget();
    }
    private void initWidget()
    {
        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        ivBackLinear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(ResetPasswordActivity.this);
        etIdNumber.setText(shared.getValueString(Constant.User.LOGIN_ID,""));
        tvDocumentType.setText(Dictionary.getIdTypeLocal(shared.getValueString(Constant.User.LOGIN_TYPE,"ID Card")));
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
        lyReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData())
                {
                    HashMap<String,String>params=getData();
                    HashMap<String,String>headers=new HashMap<>();
                    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(ResetPasswordActivity.this);
                    headers.put(Constant.Auth.USER_TOKEN,shared.getValueString(Constant.Auth.USER_TOKEN,"no_user_token"));
                    headers.put(Constant.Auth.USER_ID,shared.getValueString(Constant.Auth.USER_ID,"no_user_id"));
                    String json = JSONObject.toJSONString(params);
                    showLoadingDialog();
                    HttpUtil.getDataFromServerUsePost(Constant.Url.USER_RESET_PASSWORD,json,headers,new Callback(){
                        @Override
                        public void onFailure(Call call, IOException e) {
                            hideLoadingDialog();
                            CustomToast.showToast(ResetPasswordActivity.this,"access network failed");
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            hideLoadingDialog();
                            ResetPasswordBean resetPasswordBean=null;
                            try{
                                resetPasswordBean = JSON.parseObject(response.body().string(), ResetPasswordBean.class);
                            }catch (Exception e)
                            {
                                CustomToast.showToast(ResetPasswordActivity.this,"parse data failed");
                            }
                            if(resetPasswordBean!=null)
                            {
                                if(resetPasswordBean.getResultcode()==200)
                                {
                                    SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(ResetPasswordActivity.this);
                                    shared.submitString(Constant.User.LOGIN_PASSWORD,Base64.encodeBytes(etPassword.getText().toString().trim().getBytes()));
                                    CustomToast.showToast(ResetPasswordActivity.this,"reset password success,please exit app and re-login");
                                }else
                                {
                                    CustomToast.showToast(ResetPasswordActivity.this,resetPasswordBean.getResultmsg());
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    private boolean checkData()
    {
        if(TextUtils.isEmpty(etIdNumber.getText()))
        {
            CustomToast.showToast(ResetPasswordActivity.this,"please enter the id number");
            return false;
        }
        if(TextUtils.isEmpty(etPassword.getText()))
        {
            CustomToast.showToast(ResetPasswordActivity.this,"please enter the password");
            return false;
        }
        if(TextUtils.isEmpty(etConfirmPassword.getText()))
        {
            CustomToast.showToast(ResetPasswordActivity.this,"please enter the confirm password");
            return false;
        }
        if(!(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())))
        {
            CustomToast.showToast(ResetPasswordActivity.this,"password and confirm password not the same");
            return false;
        }
        return true;
    }
    private HashMap<String,String>getData()
    {
        HashMap<String,String>params=new HashMap<>();
        params.put("certType",Dictionary.getIdType(tvDocumentType.getText().toString()));
        params.put("certId",etIdNumber.getText().toString());
        params.put("password", Base64.encodeBytes(etPassword.getText().toString().trim().getBytes()));
        return  params;
    }


}
