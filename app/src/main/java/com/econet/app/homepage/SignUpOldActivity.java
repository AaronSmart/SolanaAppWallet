package com.econet.app.homepage;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.econet.app.ServerBeans.register.UserRegisterBean;
import com.econet.app.beans.JsonBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.dictionary.Dictionary;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.Base64;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.GetJsonDataUtil;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.PopDialog;
import com.econet.app.uitl.PopupCenterWindow;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.econet.app.uitl.Tools;

import org.json.JSONArray;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/***
 * PM want to split the sign up activity into two part see SignUPActivity and SignUpPasswordActivity
 * @author dai.jianhui
 */
public class SignUpOldActivity extends BaseActivity {
    @BindView(R.id.lySignUp)
    LinearLayout lySignUp;
    @BindView(R.id.lyDocumentType)
    LinearLayout lyDocumentType;
    PopupCenterWindow mListPopup;//列表形式弹出框
    PopupCenterWindow mListPopupGender;
    @BindView(R.id.tvDocumentType)
    TextView tvDocumentType;
    @BindView(R.id.lyGender)
    LinearLayout lyGender;
    String popType="";
    @BindView(R.id.tvGender2)
    TextView tvGender2;
    @BindView(R.id.tvDateOfBirthCalendar)
    TextView tvDateOfBirthCalendar;
    @BindView(R.id.tvDateOfBirth2)
    TextView tvDateOfBirth2;
    @BindView(R.id.tvRegion2)
    TextView tvRegion2;
    @BindView(R.id.tvRegion1)
    TextView tvRegion1;
    String  tvRegionCopy="";
    @BindView(R.id.lyRegion)
    LinearLayout lyRegion;
    //get form data
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etIdNumber)
    EditText etIdNumber;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.checkBoxOne)
    CheckBox checkBoxOne;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvRight)
    TextView tvRight;
    public PopDialog dialog;

    private boolean checkData()
    {
        if(TextUtils.isEmpty(etFirstName.getText().toString()))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please enter the first name");
            return false;
        }
        if(TextUtils.isEmpty(etLastName.getText().toString()))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please enter the last name");
            return false;
        }
        if(TextUtils.isEmpty(etIdNumber.getText().toString()))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please enter the id number");
            return false;
        }
        if(TextUtils.isEmpty(etPhoneNumber.getText().toString()))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please enter the phone number");
            return false;
        }
        if(TextUtils.isEmpty(tvGender2.getText())||tvGender2.getText().equals(" "))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please select the gender");
            return false;
        }
        if(TextUtils.isEmpty(tvDateOfBirth2.getText())||tvDateOfBirth2.getText().equals(" "))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please select date of birth");
            return false;
        }
        if(TextUtils.isEmpty(etPassword.getText()))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please enter the password");
            return false;
        }
        if(TextUtils.isEmpty(etConfirmPassword.getText()))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please enter the confirm password");
            return false;
        }
        if(TextUtils.isEmpty(etAddress.getText()))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please enter the address");
            return false;
        }
        if(TextUtils.isEmpty(tvRegion1.getText()))
        {
            CustomToast.showToast(SignUpOldActivity.this,"please select the region");
            return false;
        }
        if(!checkBoxOne.isChecked())
        {
            CustomToast.showToast(SignUpOldActivity.this,"please check the informed consent");
            return false;
        }
        if(!(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())))
        {
            CustomToast.showToast(SignUpOldActivity.this,"password and confirm password not the same");
            return false;
        }
        return true;
    }
    private HashMap<String,String> getData()
    {
        HashMap<String,String>params=new HashMap<>();
        params.put("firstName",etFirstName.getText().toString());
        params.put("lastName",etLastName.getText().toString());
        params.put("certType",Dictionary.getIdType(tvDocumentType.getText().toString()));
        params.put("certId",etIdNumber.getText().toString());
        params.put("phone",etPhoneNumber.getText().toString());
        params.put("gender",tvGender2.getText().toString());
        params.put("birth",tvDateOfBirth2.getText().toString());
        params.put("password", Base64.encodeBytes(etPassword.getText().toString().trim().getBytes()));
        params.put("address",etAddress.getText().toString());
        params.put("region",tvRegionCopy);
        return params;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        ButterKnife.bind(this);
        initWidget();
        initJsonData();
    }
    private void initWidget()
    {
        setTitle("sign up");
        setRightText("next",new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
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
                dialog = new PopDialog(SignUpOldActivity.this, "I declare personally that I guarantee that the above-mentioned contents are true and accurate. If the information is false, I am willing to bear the corresponding legal responsibility. I know that the appeal information is very important for generating a health code. Once submitted, whether it is filled wrong or not, it is unchangeable, and the relevant responsibility and consequences are at my own risk.", "I agree", "Don't agree", "My informed consent",
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
                            CustomToast.showToast(SignUpOldActivity.this,"access network failed");
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            hideLoadingDialog();
                            UserRegisterBean user=null;
                            try{
                                 user = JSON.parseObject(response.body().string(), UserRegisterBean.class);// jsonString转为java对象
                            }catch (Exception e)
                            {
                                CustomToast.showToast(SignUpOldActivity.this,"parse data failed");
                            }
                            if(user!=null) {
                                if (user.getResultcode() == 200) {
                                    String authId = user.getResult().getAuthid();
                                    String authToken = user.getResult().getAuthtoken();
                                    SharedPreferencesUtils shared= SharedPreferencesUtils.getInstance(SignUpOldActivity.this);
                                    shared.submitString(Constant.Auth.USER_ID, authId);
                                    shared.submitString(Constant.Auth.USER_ID, authToken);
                                    //add user info in order to auto login
                                    shared.submitString(Constant.User.LOGIN_TYPE,Dictionary.getIdType(tvDocumentType.getText().toString()));
                                    shared.submitString(Constant.User.LOGIN_PASSWORD,Base64.encodeBytes(etPassword.getText().toString().trim().getBytes()));
                                    shared.submitString(Constant.User.LOGIN_ID,etIdNumber.getText().toString());
                                    CustomToast.showToast(SignUpOldActivity.this, "register success");
                                } else
                                {
                                    CustomToast.showToast(SignUpOldActivity.this, user.getResultmsg());
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
                popType="documentType";
                mListPopup.popupUpData(lyDocumentType, Dictionary.getDocumentType());
            }
        });
        lyRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                showProvincePickerView();
            }
        });
        lyGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popType="gender";
                mListPopupGender.popupUpData(lyGender, Dictionary.getGender());
            }
        });
        tvDateOfBirthCalendar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!CheckClick.isClickEvent())
            {
                return;
            }
            showDate(tvDateOfBirth2,"Date of Birth", SignUpOldActivity.this);
        }
    });
        mListPopup = new PopupCenterWindow(this, (int) (0.8 * Tools.getDisplayMetrics(this).widthPixels),
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        mListPopup.dismiss();
                            tvDocumentType.setText(""+Dictionary.getDocumentType().get(position));

                    }
                });
        mListPopupGender = new PopupCenterWindow(this, (int) (0.8 * Tools.getDisplayMetrics(this).widthPixels),
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        mListPopupGender.dismiss();
                        tvGender2.setText(""+Dictionary.getGender().get(position));

                    }
                });
    }
    private void showDate(TextView tv,String title)
    {
        if(title==null)
        {
            title = "";
        }
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .setContentTextSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText(title)//标题文字
                //.setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setLabel("","","","H","m","s")
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    //the province part
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private void showProvincePickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx +"  "+ opt2tx;
                tvRegionCopy=opt1tx +"||"+ opt2tx;
                tvRegion1.setText(tx);
            }
        })
                .setTitleText("Region Select")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .isDialog(true)//是否显示为对话框样式
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
    private void initJsonData() {//解析数据

        String JsonData = new GetJsonDataUtil().getJson(this, "provincejingba.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {
            ArrayList<String> cityList = new ArrayList<>();
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);
                ArrayList<String> city_AreaList = new ArrayList<>();
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);
            }
            options2Items.add(cityList);
            options3Items.add(province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
