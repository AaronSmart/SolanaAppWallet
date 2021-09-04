package com.econet.app.homepage;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.econet.app.beans.JsonBean;
import com.econet.app.dictionary.Dictionary;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.GetJsonDataUtil;
import com.econet.app.uitl.PopDialog;
import com.econet.app.uitl.PopupCenterWindow;
import com.econet.app.uitl.Tools;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/***
 * @author dai.jianhui
 */
public class SignUpActivity extends BaseActivity {

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
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvRight)
    TextView tvRight;
    public PopDialog dialog;

    private boolean checkData()
    {
        if(TextUtils.isEmpty(etFirstName.getText().toString()))
        {
            CustomToast.showToast(SignUpActivity.this,"please enter the first name");
            return false;
        }
        if(TextUtils.isEmpty(etLastName.getText().toString()))
        {
            CustomToast.showToast(SignUpActivity.this,"please enter the last name");
            return false;
        }
        if(TextUtils.isEmpty(etIdNumber.getText().toString()))
        {
            CustomToast.showToast(SignUpActivity.this,"please enter the id number");
            return false;
        }
        if(TextUtils.isEmpty(etPhoneNumber.getText().toString()))
        {
            CustomToast.showToast(SignUpActivity.this,"please enter the phone number");
            return false;
        }
        //special check for The Republic of Zimbabwe
        if(!"07".equals(etPhoneNumber.getText().toString().substring(0,2)))
        {
            CustomToast.showToast(SignUpActivity.this,"the phone number should begin with \"07\"");
            return false;
        }
        if(etPhoneNumber.getText().toString().length()!=10)
        {
            CustomToast.showToast(SignUpActivity.this,"the phone number length is not equal to 10");
            return false;
        }//end special check
        if(TextUtils.isEmpty(tvGender2.getText())||tvGender2.getText().equals(" "))
        {
            CustomToast.showToast(SignUpActivity.this,"please select the gender");
            return false;
        }
        if(TextUtils.isEmpty(tvDateOfBirth2.getText())||tvDateOfBirth2.getText().equals(" "))
        {
            CustomToast.showToast(SignUpActivity.this,"please select date of birth");
            return false;
        }
        if(TextUtils.isEmpty(etAddress.getText()))
        {
            CustomToast.showToast(SignUpActivity.this,"please enter the address");
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
        params.put("address",etAddress.getText().toString());
        return params;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup_basic);
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
                if(checkData())
                {
                    HashMap<String,String> params=getData();
                    Intent i = new Intent(getApplicationContext(), SignUpPasswordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("params", params);
                    i.putExtras(bundle);
                    startActivityForResult(i,333);
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
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
            showDate(tvDateOfBirth2,"Date of Birth",SignUpActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                setResult(250);
                this.finish();
            }
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
