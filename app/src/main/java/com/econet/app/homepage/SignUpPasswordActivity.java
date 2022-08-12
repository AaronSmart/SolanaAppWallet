package com.econet.app.homepage;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.econet.app.ServerBeans.register.UserRegisterBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.dictionary.Dictionary;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.Base64;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.PopDialog;
import com.econet.app.uitl.SharedPreferencesUtils;


import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/***
 * @author dai.jianhui
 */
public class SignUpPasswordActivity extends BaseActivity {

    @BindView(R.id.lySignUp)
    LinearLayout lySignUp;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.checkBoxOne)
    CheckBox checkBoxOne;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    public PopDialog dialog;

    HashMap<String,String>params;

    private boolean checkData()
    {
        if(TextUtils.isEmpty(etPassword.getText()))
        {
            CustomToast.showToast(SignUpPasswordActivity.this,"please enter the password");
            return false;
        }
        if(TextUtils.isEmpty(etConfirmPassword.getText()))
        {
            CustomToast.showToast(SignUpPasswordActivity.this,"please enter the confirm password");
            return false;
        }
        if(!checkBoxOne.isChecked())
        {
            CustomToast.showToast(SignUpPasswordActivity.this,"please check the informed consent");
            return false;
        }
        if(!(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())))
        {
            CustomToast.showToast(SignUpPasswordActivity.this,"password and confirm password not the same");
            return false;
        }
        return true;
    }
    private HashMap<String,String> getData()
    {
        params.put("password", Base64.encodeBytes(etPassword.getText().toString().trim().getBytes()));
        return params;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup_password);
        ButterKnife.bind(this);
        params= (HashMap<String, String>) getIntent().getSerializableExtra("params");
        initWidget();

    }
    private void initWidget()
    {
        setTitle("sign up");
        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        checkBoxOne.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (dialog == null) {
                dialog = new PopDialog(SignUpPasswordActivity.this, "I declare personally that I guarantee that the above-mentioned contents are true and accurate. If the information is false, I am willing to bear the corresponding legal responsibility. I know that the appeal information is very important for generating a health code. Once submitted, whether it is filled wrong or not, it is unchangeable, and the relevant responsibility and consequences are at my own risk.", "I agree", "Don't agree", "My informed consent",
                        new PopDialog.XClickListener() {

                            @Override
                            public void btnSure() {
                                checkBoxOne.setChecked(true);
                            }
                            @Override
                            public void btnCancel() {
                                checkBoxOne.setChecked(false);
                            }
                        });
            }
            dialog.show();
        }
       });
        lySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData())
                {
                    HashMap<String,String>params=getData();
                    String json = JSONObject.toJSONString(params);
                    HashMap<String,String>headers=new HashMap<>();
                    headers.put("aaa","bbb");
                    showLoadingDialog();
                    HttpUtil.getDataFromServerUsePost(Constant.Url.USER_SIGN_UP,json,headers,new Callback(){
                        @Override
                        public void onFailure(Call call, IOException e) {
                            hideLoadingDialog();
                            CustomToast.showToast(SignUpPasswordActivity.this,"access network failed");
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            hideLoadingDialog();
                            UserRegisterBean user=null;
                            try{
                                 user = JSON.parseObject(response.body().string(), UserRegisterBean.class);// jsonString转为java对象
                            }catch (Exception e)
                            {
                                CustomToast.showToast(SignUpPasswordActivity.this,"parse data failed");
                            }
                            if(user!=null) {
                                if (user.getResultcode() == 200) {
                                    String authId = user.getResult().getAuthid();
                                    String authToken = user.getResult().getAuthtoken();
                                    SharedPreferencesUtils shared= SharedPreferencesUtils.getInstance(SignUpPasswordActivity.this);
                                    shared.submitString(Constant.Auth.USER_ID, authId);
                                    shared.submitString(Constant.Auth.USER_ID, authToken);
                                    //add user info in order to auto login
                                    shared.submitString(Constant.User.LOGIN_TYPE,Dictionary.getIdType(params.get("certType")));
                                    shared.submitString(Constant.User.LOGIN_PASSWORD,Base64.encodeBytes(etPassword.getText().toString().trim().getBytes()));
                                    shared.submitString(Constant.User.LOGIN_ID,params.get("certId"));
                                    CustomToast.showToast(SignUpPasswordActivity.this, "register success");
                                    setResult(RESULT_OK);
                                    finish();
                                } else
                                {
                                    CustomToast.showToast(SignUpPasswordActivity.this, user.getResultmsg());
                                }
                            }
                        }
                    });
                }
            }
        });
    }

}
