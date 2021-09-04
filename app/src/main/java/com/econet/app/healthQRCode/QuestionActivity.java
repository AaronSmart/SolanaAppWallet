package com.econet.app.healthQRCode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.econet.app.ServerBeans.SaveQuestionBean;
import com.econet.app.ServerBeans.myhealth.MyHealthBean;
import com.econet.app.ServerBeans.save.SaveQuestionReturnBean;
import com.econet.app.beans.JsonBean;
import com.econet.app.beans.QuestionBean;
import com.econet.app.beans.QuestionFormBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.homepage.BaseActivity;
import com.econet.app.listView.CityListActivity;
import com.econet.app.trans.DataAdapter;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CopyCatLog;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.GetJsonDataUtil;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.SharedPreferencesUtils;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.econet.app.solwallet.R;

/**
 * Describe the questionnaire activity
 * @Auhtor dai.jianhui
 *
 */
public class QuestionActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.txName)
    TextView txName;
    @BindView(R.id.txPhoneNumber)
    TextView txPhoneNumber;
    @BindView(R.id.txCurrentCity)
    TextView txCurrentCity;
    @BindView(R.id.txCurrentAddress)
    TextView txCurrentAddress;
    @BindView(R.id.txArrivalDateOfCurrentAddress)
    TextView txArrivalDateOfCurrentAddress;
    @BindView(R.id.txNextKinContactPhoneNumber)
    TextView txNextKinContactPhoneNumber;
    @BindView(R.id.txNextKinName)
    TextView txNextKinName;
    @BindView(R.id.txOtherCityVisited)
    TextView txOtherCityVisited;
    @BindView(R.id.txVisitedCity)
    TextView txVisitedCity;
    @BindView(R.id.txFlight)
    TextView txFlight;
    @BindView(R.id.txFlightNumber)
    TextView txFlightNumber;
    @BindView(R.id.txSeatNumber)
    TextView txSeatNumber;
    @BindView(R.id.txDateOfEntry)
    TextView txDateOfEntry;
    @BindView(R.id.txCityOfEntry)
    TextView txCityOfEntry;
    @BindView(R.id.txDestinationCity)
    TextView txDestinationCity;
    @BindView(R.id.txDestinationAddress)
    TextView txDestinationAddress;
    @BindView(R.id.txContactPatient)
    TextView txContactPatient;
    @BindView(R.id.txContactPatientFluLike)
    TextView txContactPatientFluLike;
    @BindView(R.id.txReportOfConfirmed)
    TextView txReportOfConfirmed;
    @BindView(R.id.txTWoOrMoreFluLike)
    TextView txTWoOrMoreFluLike;
    @BindView(R.id.txBodyTemperatureToday)
    TextView txBodyTemperatureToday;
    @BindView(R.id.txFluSymptoms)
    TextView txFluSymptoms;
    @BindView(R.id.txTestStatus)
    TextView txTestStatus;
    @BindView(R.id.lySure)
    LinearLayout lySure;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.txDateEntryCalendar)
    TextView txDateEntryCalendar;
    @BindView(R.id.txDateEntry)
    TextView txDateEntry;
    @BindView(R.id.txVoyageDateCalendar)
    TextView txVoyageDateCalendar;
    @BindView(R.id.txVoyageDate)
    TextView txVoyageDate;

    @BindView(R.id.lyHealthInfo)
    LinearLayout lyHealthInfo;
    //check data
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.etDestinationAddress)
    EditText etDestinationAddress;
    @BindView(R.id.tvCurrentCity)
    TextView tvCurrentCity;
    @BindView(R.id.lyCurrentCity)
    LinearLayout lyCurrentCity;
    @BindView(R.id.lyVisitedCity)
    LinearLayout lyVisitedCity;
    @BindView(R.id.lyCityOfEntry)
    LinearLayout lyCityOfEntry;
    @BindView(R.id.lyDestinationCity)
    LinearLayout lyDestinationCity;
    @BindView(R.id.etCurrentAddress)
    EditText etCurrentAddress;
    @BindView(R.id.txArrivalDateOfCurrentAddressCalendar)
    TextView txArrivalDateOfCurrentAddressCalendar;
    @BindView(R.id.txArrivalDateOfCurrentAddress2)
    TextView txArrivalDateOfCurrentAddress2;
    @BindView(R.id.etNextKinContactPhoneNumber)
    EditText etNextKinContactPhoneNumber;
    @BindView(R.id.etNextKinName)
    EditText etNextKinName;
    @BindView(R.id.tvVisitedCity)
    TextView tvVisitedCity;
    @BindView(R.id.etFlightNumber)
    EditText etFlightNumber;
    @BindView(R.id.etSeatNumber)
    EditText etSeatNumber;
    @BindView(R.id.tvCityOfEntry)
    TextView tvCityOfEntry;
    @BindView(R.id.tvDestinationCity)
    TextView tvDestinationCity;
    @BindView(R.id.etBodyTemperatureToday)
    EditText etBodyTemperatureToday;
    @BindView(R.id.rbOtherCityVisitedYes)
    RadioButton rbOtherCityVisitedYes;
    @BindView(R.id.rbOtherCityVisitedNo)
    RadioButton rbOtherCityVisitedNo;
    @BindView(R.id.rbContactPatientYes)
    RadioButton rbContactPatientYes;
    @BindView(R.id.rbContactPatientNo)
    RadioButton rbContactPatientNo;
    @BindView(R.id.rbContactPatientDontKnow)
    RadioButton rbContactPatientDontKnow;
    @BindView(R.id.rbContactPatientFluLikeYes)
    RadioButton rbContactPatientFluLikeYes;
    @BindView(R.id.rbContactPatientFluLikeNo)
    RadioButton rbContactPatientFluLikeNo;
    @BindView(R.id.rbReportOfConfirmedYes)
    RadioButton rbReportOfConfirmedYes;
    @BindView(R.id.rbReportOfConfirmedNo)
    RadioButton rbReportOfConfirmedNo;
    @BindView(R.id.rbTwoOrMoreFluLikeYes)
    RadioButton rbTwoOrMoreFluLikeYes;
    @BindView(R.id.rbTwoOrMoreFluLikeNo)
    RadioButton rbTwoOrMoreFluLikeNo;
    @BindView(R.id.testStatus1)
    RadioButton testStatus1;
    @BindView(R.id.testStatus2)
    RadioButton testStatus2;
    @BindView(R.id.testStatus3)
    RadioButton testStatus3;
    @BindView(R.id.testStatus4)
    RadioButton testStatus4;
    @BindView(R.id.checkBoxOne)
    CheckBox checkBoxOne;
    @BindView(R.id.checkBoxTwo)
    CheckBox checkBoxTwo;
    @BindView(R.id.checkBoxThree)
    CheckBox checkBoxThree;
    @BindView(R.id.checkBoxFour)
    CheckBox checkBoxFour;
    @BindView(R.id.checkBoxFive)
    CheckBox checkBoxFive;
    @BindView(R.id.checkBoxSix)
    CheckBox checkBoxSix;
    @BindView(R.id.checkBoxSeven)
    CheckBox checkBoxSeven;
    String uploadJson;
    boolean hiddenTravelFlag= false;
    String currentCityUpload="";
    String visitedCityUpload="";
    String cityOfEntryUplaod="";
    String destinationCityUpload="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        initQuestion();
        initUserBasicInfo();
        initJsonData();
        initWidget();
        lySure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData())
                {
                    QuestionFormBean formBean= getData();
                    SaveQuestionBean saveQuestionBean=DataAdapter.localToServer(formBean);
                    uploadJson= DataAdapter.toJson(saveQuestionBean);
                    HashMap<String,String> headers=new HashMap<>();
                    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(QuestionActivity.this);
                    headers.put(Constant.Auth.USER_TOKEN,shared.getValueString(Constant.Auth.USER_TOKEN,"no_user_token"));
                    headers.put(Constant.Auth.USER_ID,shared.getValueString(Constant.Auth.USER_ID,"no_user_id"));
                    showLoadingDialog();
                    HttpUtil.getDataFromServerUsePost(Constant.Url.SAVE_USER_QUESTION,uploadJson,headers,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            hideLoadingDialog();
                            CustomToast.showToast(QuestionActivity.this, "access network failed");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            hideLoadingDialog();
                            SaveQuestionReturnBean saveQuestionReturnBean=null;
                            try{
                                saveQuestionReturnBean= JSON.parseObject(response.body().string(), SaveQuestionReturnBean.class);
                            }catch(Exception e)
                            {
                                CustomToast.showToast(QuestionActivity.this,"parse data failed");
                            }
                            if(saveQuestionReturnBean!=null)
                            {
                                if(saveQuestionReturnBean.getResultcode()==200)
                                {
                                    CustomToast.showToast(QuestionActivity.this,"upload success");
                                    HashMap<String,String> params=getData2();
                                    HashMap<String,String>headers=new HashMap<>();
                                    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(QuestionActivity.this);
                                    //this is the flag to identify
                                    shared.submitString(Constant.User.IS_FIRST_FILL_QUESTIONNAIRE,"false");
                                    headers.put(Constant.Auth.USER_TOKEN,shared.getValueString(Constant.Auth.USER_TOKEN,"no_user_token"));
                                    headers.put(Constant.Auth.USER_ID,shared.getValueString(Constant.Auth.USER_ID,"no_user_id"));
                                    HttpUtil.getDataFromServerUseGet(Constant.Url.USER_INFO_DETAIL,params,headers,new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            CustomToast.showToast(QuestionActivity.this, "access network failed");
                                        }
                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            MyHealthBean myHealthBean = null;
                                            try {
                                                myHealthBean = JSON.parseObject(response.body().string(), MyHealthBean.class);
                                            } catch (Exception e) {
                                                CustomToast.showToast(QuestionActivity.this, "parse data failed");
                                            }
                                            if(myHealthBean!=null)
                                            {
                                                if(myHealthBean.getResultcode()==200)
                                                {
                                                    //save the basic info to local
                                                    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(QuestionActivity.this);
                                                    shared.submitString(Constant.Basic.BASIC_NAME,etName.getText().toString().trim());
                                                    shared.submitString(Constant.Basic.BASIC_PHONE,etPhoneNumber.getText().toString().trim());
                                                    shared.submitString(Constant.Basic.BASIC_CITY,tvCurrentCity.getText().toString().trim());
                                                    shared.submitString(Constant.Basic.BASIC_CITY_UPLOAD,currentCityUpload);
                                                    shared.submitString(Constant.Basic.BASIC_ADDRESS,etCurrentAddress.getText().toString().trim());
                                                    shared.submitString(Constant.Basic.BASIC_ARRIVAL_DATE,txArrivalDateOfCurrentAddress2.getText().toString().trim());
                                                    shared.submitString(Constant.Basic.BASIC_KIN_PHONE,etNextKinContactPhoneNumber.getText().toString().trim());
                                                    shared.submitString(Constant.Basic.BASIC_KIN_NAME,etNextKinName.getText().toString().trim());

                                                    if(myHealthBean.getResult()!=null)
                                                    {
                                                        String healthStatus=myHealthBean.getResult().getHealthstatus();
                                                        String healthStatusTime=myHealthBean.getResult().getHealthstatustime();
                                                        //update local
                                                        SharedPreferencesUtils sharedUpdate=SharedPreferencesUtils.getInstance(QuestionActivity.this);
                                                        sharedUpdate.submitString(Constant.Auth.HEALTH_STATUS,healthStatus);
                                                        sharedUpdate.submitString(Constant.Auth.HEALTH_STATUS_TIME,healthStatusTime);
                                                        Intent i = new Intent(QuestionActivity.this, QrCreateActivity.class);
                                                        if("Red".equals(healthStatus))
                                                        {
                                                            i.putExtra("mark", 10);
                                                        }else if("Yellow".equals(healthStatus))
                                                        {
                                                            i.putExtra("mark", 5);
                                                        }else
                                                        {
                                                            i.putExtra("mark", 4);
                                                        }
                                                        i.putExtra("healthStatus",healthStatus);
                                                        i.putExtra("healthStatusTime",healthStatusTime);
                                                        startActivityForResult(i,222);
                                                    }
                                                }else
                                                {
                                                    CustomToast.showToast(QuestionActivity.this, myHealthBean.getResultmsg());
                                                }
                                            }
                                        }
                                    });
                                }// end inner request
                                else
                                {
                                    CustomToast.showToast(QuestionActivity.this,saveQuestionReturnBean.getResultmsg());
                                }
                            }
                        }
                    });
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }

        });
        txArrivalDateOfCurrentAddressCalendar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                showDate(txArrivalDateOfCurrentAddress2,"Arrival Date",QuestionActivity.this);
            }
        });
        txDateEntryCalendar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                showDate(txDateEntry,"Date of entry",QuestionActivity.this);
            }
        });
        txVoyageDateCalendar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                showDate(txVoyageDate,"Flight/voyage",QuestionActivity.this);
            }
        });
        rbOtherCityVisitedNo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                lyHealthInfo.setVisibility(View.GONE);
                hiddenTravelFlag=true;
            }
        });
        rbOtherCityVisitedYes.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                lyHealthInfo.setVisibility(View.VISIBLE);
                hiddenTravelFlag=false;
            }
        });
        etPhoneNumber.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etBodyTemperatureToday.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etBodyTemperatureToday.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    /**
     *  used the add the red star
     */
    void initWidget()
    {
        lyCurrentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                showProvincePickerViewByPage(tvCurrentCity,1);
                //the pm don't wanna use the wheel component
                //showProvincePickerView(tvCurrentCity,1);
            }
        });
        lyVisitedCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                showProvincePickerViewByPage(tvVisitedCity,2);
            }
        });
        lyCityOfEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                showProvincePickerViewByPage(tvCityOfEntry,3);
            }
        });
        lyDestinationCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                showProvincePickerViewByPage(tvDestinationCity,4);
            }
        });
    }
    void initUserBasicInfo()
    {
        SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(QuestionActivity.this);
        etName.setText(shared.getValueString(Constant.Basic.BASIC_NAME,""));
        etPhoneNumber.setText(shared.getValueString(Constant.Basic.BASIC_PHONE,""));
        tvCurrentCity.setText(shared.getValueString(Constant.Basic.BASIC_CITY,""));
        currentCityUpload = shared.getValueString(Constant.Basic.BASIC_CITY_UPLOAD,"");
        etCurrentAddress.setText(shared.getValueString(Constant.Basic.BASIC_ADDRESS,""));
        txArrivalDateOfCurrentAddress2.setText(shared.getValueString(Constant.Basic.BASIC_ARRIVAL_DATE,""));
        etNextKinContactPhoneNumber.setText(shared.getValueString(Constant.Basic.BASIC_KIN_PHONE,""));
        etNextKinName.setText(shared.getValueString(Constant.Basic.BASIC_KIN_NAME,""));
    }


    void initQuestion()
    {
        tvTitle.setText("FIGHT COVID-19");
        String name="Name<font color='#FF0000'>*</font>";
        txName.setText(Html.fromHtml(name));
        String  phoneNumber="Phone number<font color='#FF0000'>*</font>";
        txPhoneNumber.setText(Html.fromHtml(phoneNumber));
        String  currentCity="Current city<font color='#FF0000'>*</font>";
        txCurrentCity.setText(Html.fromHtml(currentCity));
        String  currentAddress="Current address<font color='#FF0000'>*</font>";
        txCurrentAddress.setText(Html.fromHtml(currentAddress));
        String  arrivalDateOfCurrentAddress="Arrival date of current address<font color='#FF0000'>*</font>";
        txArrivalDateOfCurrentAddress.setText(Html.fromHtml(arrivalDateOfCurrentAddress));
        String  nextKinContactPhoneNumber="Next of kin contact phone number<font color='#FF0000'>*</font>";
        txNextKinContactPhoneNumber.setText(Html.fromHtml(nextKinContactPhoneNumber));
        String  nextKinName="Next of kin name<font color='#FF0000'>*</font>";
        txNextKinName.setText(Html.fromHtml(nextKinName));
        String  otherCityVisited="Other city visited in last 14 days?<font color='#FF0000'>*</font>(No need to report repeatedly)";
        txOtherCityVisited.setText(Html.fromHtml(otherCityVisited));
        String  visitedCity="Visited city?<font color='#FF0000'>*</font>";
        txVisitedCity.setText(Html.fromHtml(visitedCity));
        String  flight="Flight/voyage date<font color='#FF0000'>*</font>";
        txFlight.setText(Html.fromHtml(flight));
        String  flightNumber="Flight/voyage number<font color='#FF0000'>*</font>";
        txFlightNumber.setText(Html.fromHtml(flightNumber));
        String  seatNumber="Seat number<font color='#FF0000'>*</font>";
        txSeatNumber.setText(Html.fromHtml(seatNumber));
        String  dateOfEntry="Date of entry<font color='#FF0000'>*</font>";
        txDateOfEntry.setText(Html.fromHtml(dateOfEntry));
        String  cityOfEntry="City of entry<font color='#FF0000'>*</font>";
        txCityOfEntry.setText(Html.fromHtml(cityOfEntry));
        String  destinationCity="Destination city<font color='#FF0000'>*</font>";
        txDestinationCity.setText(Html.fromHtml(destinationCity));
        String  destinationAddress="Destination address<font color='#FF0000'>*</font>";
        txDestinationAddress.setText(Html.fromHtml(destinationAddress));
        String  contactPatient="Contact with confirmed COVID-19 patient in last 14 days?<font color='#FF0000'>*</font>";
        txContactPatient.setText(Html.fromHtml(contactPatient));
        String  contactPatientFluLike="Contact with person with flu-like (fever/respiratory distress)?<font color='#FF0000'>*</font>";
        txContactPatientFluLike.setText(Html.fromHtml(contactPatientFluLike));
        String  reportOfConfirmed="Report of confirmed COVID-19 case in your neighborhood  today?<font color='#FF0000'>*</font>";
        txReportOfConfirmed.setText(Html.fromHtml(reportOfConfirmed));
        String  twoOrMoreFluLike="2 or more people showing flu-like symptoms at home/work?<font color='#FF0000'>*</font>";
        txTWoOrMoreFluLike.setText(Html.fromHtml(twoOrMoreFluLike));
        String  bodyTemperatureToday="Body temperature today<font color='#FF0000'>*</font>";
        txBodyTemperatureToday.setText(Html.fromHtml(bodyTemperatureToday));
        String  fluSymptoms="Do you have following symptoms?(multiple choice)<font color='#FF0000'>*</font>";
        txFluSymptoms.setText(Html.fromHtml(fluSymptoms));
        String  testStatus=" COVID-19 Test Status<font color='#FF0000'>*</font>";
        txTestStatus.setText(Html.fromHtml(testStatus));
    }
    private boolean checkData()
    {
        if(TextUtils.isEmpty(etName.getText()))
        {
            CustomToast.showToast(QuestionActivity.this,"please enter name");
            return false;
        }
        if(TextUtils.isEmpty(etPhoneNumber.getText()))
        {
            CustomToast.showToast(QuestionActivity.this,"please enter phone number");
            return false;
        }
        //special check for The Republic of Zimbabwe
        if(!"07".equals(etPhoneNumber.getText().toString().substring(0,2)))
        {
            CustomToast.showToast(QuestionActivity.this,"the phone number should begin with \"07\"");
            return false;
        }
        if(etPhoneNumber.getText().toString().length()!=10)
        {
            CustomToast.showToast(QuestionActivity.this,"the phone number length is not equal to 10");
            return false;
        }//end special check
        if(TextUtils.isEmpty(tvCurrentCity.getText()))
        {
            CustomToast.showToast(QuestionActivity.this,"please select current city");
            return false;
        }
        if(TextUtils.isEmpty(etCurrentAddress.getText()))
        {
            CustomToast.showToast(QuestionActivity.this,"please enter current address");
            return false;
        }
        if(TextUtils.isEmpty(txArrivalDateOfCurrentAddress2.getText()))
        {
            CustomToast.showToast(QuestionActivity.this,"please enter arrival date of current address");
            return false;
        }
        if(TextUtils.isEmpty(etNextKinContactPhoneNumber.getText()))
        {
            CustomToast.showToast(QuestionActivity.this,"please enter next of kin contact phone number");
            return false;
        }
        //special check for The Republic of Zimbabwe
        if(!"07".equals(etNextKinContactPhoneNumber.getText().toString().substring(0,2)))
        {
            CustomToast.showToast(QuestionActivity.this,"the kin contact phone number should begin with \"07\"");
            return false;
        }
        if(etNextKinContactPhoneNumber.getText().toString().length()!=10)
        {
            CustomToast.showToast(QuestionActivity.this,"the kin contact phone number length is not equal to 10");
            return false;
        }//end special check
        if(TextUtils.isEmpty(etNextKinName.getText()))
        {
            CustomToast.showToast(QuestionActivity.this,"please enter next of kin name");
            return false;
        }
        if(rbOtherCityVisitedYes.isChecked()==rbOtherCityVisitedNo.isChecked())
        {
            CustomToast.showToast(QuestionActivity.this,"please select the answer of Other city visited in last 14 days");
            return false;
        }
        if(!hiddenTravelFlag)
        {
            if(TextUtils.isEmpty(tvVisitedCity.getText()))
            {
                CustomToast.showToast(QuestionActivity.this,"please select visited city");
                return false;
            }
            if(TextUtils.isEmpty(txVoyageDate.getText()))
            {
                CustomToast.showToast(QuestionActivity.this,"please select flight/voyage date");
                return false;
            }
            if(TextUtils.isEmpty(etFlightNumber.getText()))
            {
                CustomToast.showToast(QuestionActivity.this,"please enter flight/voyage number");
                return false;
            }
            if(TextUtils.isEmpty(etSeatNumber.getText()))
            {
                CustomToast.showToast(QuestionActivity.this,"please enter seat number");
                return false;
            }
            if(TextUtils.isEmpty(txDateEntry.getText()))
            {
                CustomToast.showToast(QuestionActivity.this,"please select date of entry");
                return false;
            }
            if(TextUtils.isEmpty(tvCityOfEntry.getText()))
            {
                CustomToast.showToast(QuestionActivity.this,"please select city of entry");
                return false;
            }
            if(TextUtils.isEmpty(tvDestinationCity.getText()))
            {
                CustomToast.showToast(QuestionActivity.this,"please select destination city");
                return false;
            }
            if(TextUtils.isEmpty(etDestinationAddress.getText()))
            {
                CustomToast.showToast(QuestionActivity.this,"please enter destination address");
                return false;
            }
        }
        if((rbContactPatientYes.isChecked()==rbContactPatientNo.isChecked())&&(rbContactPatientNo.isChecked()==rbContactPatientDontKnow.isChecked()))
        {
            CustomToast.showToast(QuestionActivity.this,"please select answer for Contact with confirmed COVID-19 patient in last 14 days");
            return false;
        }
        if(rbContactPatientFluLikeYes.isChecked()==rbContactPatientFluLikeNo.isChecked())
        {
            CustomToast.showToast(QuestionActivity.this,"please select answer for Contact with person with flu-like(fever/respiratory distress)");
            return false;
        }
        if(rbReportOfConfirmedYes.isChecked()==rbReportOfConfirmedNo.isChecked())
        {
            CustomToast.showToast(QuestionActivity.this,"please select answer for Report of confirmed COVID-19 case in your neighborhood today");
            return false;
        }
        if(rbTwoOrMoreFluLikeYes.isChecked()==rbTwoOrMoreFluLikeNo.isChecked())
        {
            CustomToast.showToast(QuestionActivity.this,"please select answer for 2 or more people showing flu-like symptoms at home/work");
            return false;
        }
        if(TextUtils.isEmpty(etBodyTemperatureToday.getText()))
        {
            CustomToast.showToast(QuestionActivity.this,"please enter body temperature today");
            return false;
        }
        if(getCheckBoxData().equals(""))
        {
            CustomToast.showToast(QuestionActivity.this,"please select following symptoms");
            return false;
        }
        if(testStatus1.isChecked()==testStatus2.isChecked()==testStatus3.isChecked()==testStatus4.isChecked())
        {
            CustomToast.showToast(QuestionActivity.this,"please select answer for COVID-19 Test Status");
            return false;
        }
        return true;
    }

    /**
     *  this is the local  data structure
     * @return
     */
    private QuestionFormBean getData()
    {
        QuestionFormBean form=new QuestionFormBean();
        form.form=new ArrayList<>();
        form.form.add(new QuestionBean("01","0",etName.getText().toString()));
        form.form.add(new QuestionBean("02","0",etPhoneNumber.getText().toString()));
        form.form.add(new QuestionBean("03","0",currentCityUpload));
        form.form.add(new QuestionBean("04","0",etCurrentAddress.getText().toString()));
        form.form.add(new QuestionBean("05","0",txArrivalDateOfCurrentAddress2.getText().toString()));
        form.form.add(new QuestionBean("06","0",etNextKinContactPhoneNumber.getText().toString()));
        form.form.add(new QuestionBean("07","0",etNextKinName.getText().toString()));
        if(rbOtherCityVisitedYes.isChecked())
        {
            form.form.add(new QuestionBean("08","1","A"));
        }
        if(rbOtherCityVisitedNo.isChecked())
        {
            form.form.add(new QuestionBean("08","1","B"));
        }
        if(hiddenTravelFlag)
        {
              //the server side say no need to upload the default value
//            form.form.add(new QuestionBean("09","0","citya||cityb"));
//            form.form.add(new QuestionBean("10","0","2099-12-12"));
//            form.form.add(new QuestionBean("11","0","D6409"));
//            form.form.add(new QuestionBean("12","0","15B"));
//            form.form.add(new QuestionBean("13","0","2099-12-12"));
//            form.form.add(new QuestionBean("14","0","citya||cityb"));
//            form.form.add(new QuestionBean("15","0","citya||cityb"));
//            form.form.add(new QuestionBean("16","0","no address"));
        }else
        {
            form.form.add(new QuestionBean("09","0",visitedCityUpload));
            form.form.add(new QuestionBean("10","0",txVoyageDate.getText().toString()));
            form.form.add(new QuestionBean("11","0",etFlightNumber.getText().toString()));
            form.form.add(new QuestionBean("12","0",etSeatNumber.getText().toString()));
            form.form.add(new QuestionBean("13","0",txDateEntry.getText().toString()));
            form.form.add(new QuestionBean("14","0",cityOfEntryUplaod));
            form.form.add(new QuestionBean("15","0",destinationCityUpload));
            form.form.add(new QuestionBean("16","0",etDestinationAddress.getText().toString()));
        }
        if(rbContactPatientYes.isChecked())
        {
            form.form.add(new QuestionBean("17","1","A"));
        }
        if(rbContactPatientNo.isChecked())
        {
            form.form.add(new QuestionBean("17","1","B"));
        }
        if(rbContactPatientDontKnow.isChecked())
        {
            form.form.add(new QuestionBean("17","1","C"));
        }
        //============================
        if(rbContactPatientFluLikeYes.isChecked())
        {
            form.form.add(new QuestionBean("18","1","A"));
        }
        if(rbContactPatientFluLikeNo.isChecked())
        {
            form.form.add(new QuestionBean("18","1","B"));
        }
        //========================
        if(rbReportOfConfirmedYes.isChecked())
        {
            form.form.add(new QuestionBean("19","1","A"));
        }
        if(rbReportOfConfirmedNo.isChecked())
        {
            form.form.add(new QuestionBean("19","1","B"));
        }
        //==================
        if(rbTwoOrMoreFluLikeYes.isChecked())
        {
            form.form.add(new QuestionBean("20","1","A"));
        }
        if(rbTwoOrMoreFluLikeNo.isChecked())
        {
            form.form.add(new QuestionBean("20","1","B"));
        }
        form.form.add(new QuestionBean("21","0",etBodyTemperatureToday.getText().toString()));
        form.form.add(new QuestionBean("22","2",getCheckBoxData()));
        //========================================
        if(testStatus1.isChecked())
        {
            form.form.add(new QuestionBean("23","1","A"));
        }
        if(testStatus2.isChecked())
        {
            form.form.add(new QuestionBean("23","1","B"));
        }
        if(testStatus3.isChecked())
        {
            form.form.add(new QuestionBean("23","1","C"));
        }
        if(testStatus4.isChecked())
        {
            form.form.add(new QuestionBean("23","1","D"));
        }
        return form;
    }
    private String getCheckBoxData()
    {
        String checkItem="";
        if(checkBoxOne.isChecked())
        {
            checkItem+="A";
        }
        if(checkBoxTwo.isChecked())
        {
            checkItem+="B";
        }
        if(checkBoxThree.isChecked())
        {
            checkItem+="C";
        }
        if(checkBoxFour.isChecked())
        {
            checkItem+="D";
        }
        if(checkBoxFive.isChecked())
        {
            checkItem+="E";
        }
        if(checkBoxSix.isChecked())
        {
            checkItem+="F";
        }
        if(checkBoxSeven.isChecked())
        {
            checkItem+="G";
        }
       return checkItem;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==777)
        {
            currentCityUpload = data.getStringExtra("cityUpload");
            String city=data.getStringExtra("city");
            if(city.length()>27)
            {
                city=city.substring(0,27);
                city=city+"...";
            }
            tvCurrentCity.setText(city);
        }else if(requestCode==778)
        {
            visitedCityUpload= data.getStringExtra("cityUpload");
            String city=data.getStringExtra("city");
            if(city.length()>27)
            {
                city=city.substring(0,27);
                city=city+"...";
            }
            tvVisitedCity.setText(city);
        }else if(requestCode==779)
        {
            cityOfEntryUplaod= data.getStringExtra("cityUpload");
            String city=data.getStringExtra("city");
            if(city.length()>27)
            {
                city=city.substring(0,27);
                city=city+"...";
            }
            tvCityOfEntry.setText(city);
        }
        else if(requestCode==780)
        {
            destinationCityUpload= data.getStringExtra("cityUpload");
            String city=data.getStringExtra("city");
            if(city.length()>27)
            {
                city=city.substring(0,27);
                city=city+"...";
            }
            tvDestinationCity.setText(city);
        }
        else if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            this.finish();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1001){

            if(permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CopyCatLog copycat=new CopyCatLog();
            }
        }
    }

    private HashMap<String,String>getData2()
    {
        HashMap<String,String>params=new HashMap<>();
        return params;
    }
    private void showProvincePickerViewByPage(TextView view ,int flag){

        Intent i = new Intent(getApplicationContext(), CityListActivity.class);
        if(flag==1)
        {
            startActivityForResult(i,777);
        }else if(flag==2)
        {
            startActivityForResult(i,778);
        }
        else if(flag==3)
        {
            startActivityForResult(i,779);
        }
        else if(flag==4)
        {
            startActivityForResult(i,780);
        }
    }


    //the province part
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private void showProvincePickerView(TextView view ,int flag) {// 弹出选择器

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

                String tx = opt1tx + "  " + opt2tx;
                String uploadStr = opt1tx + "||" + opt2tx;
                if (flag == 1) {
                    currentCityUpload = uploadStr;
                } else if (flag == 2) {
                    visitedCityUpload = uploadStr;
                } else if (flag == 3) {
                    cityOfEntryUplaod = uploadStr;
                } else{
                    destinationCityUpload=uploadStr;
                }
                view.setText(tx);
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
