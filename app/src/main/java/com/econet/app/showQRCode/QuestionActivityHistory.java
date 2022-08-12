package com.econet.app.showQRCode;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import com.econet.app.beans.QuestionBean;
import com.econet.app.beans.QuestionFormBean;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.econet.app.solwallet.R;

/**
 * @Auhtor dai.jianhui
 *
 */
public class QuestionActivityHistory extends AppCompatActivity {

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
    TextView etName;
    @BindView(R.id.etPhoneNumber)
    TextView etPhoneNumber;
    @BindView(R.id.etCurrentCity)
    TextView etCurrentCity;
    @BindView(R.id.etCurrentAddress)
    TextView etCurrentAddress;
    @BindView(R.id.txArrivalDateOfCurrentAddressCalendar)
    TextView txArrivalDateOfCurrentAddressCalendar;
    @BindView(R.id.txArrivalDateOfCurrentAddress2)
    TextView txArrivalDateOfCurrentAddress2;
    @BindView(R.id.etNextKinContactPhoneNumber)
    TextView etNextKinContactPhoneNumber;
    @BindView(R.id.etNextKinName)
    TextView etNextKinName;
    @BindView(R.id.etVisitedCity)
    TextView etVisitedCity;
    @BindView(R.id.etFlightNumber)
    TextView etFlightNumber;
    @BindView(R.id.etSeatNumber)
    TextView etSeatNumber;
    @BindView(R.id.etCityOfEntry)
    TextView etCityOfEntry;
    @BindView(R.id.etDestinationCity)
    TextView etDestinationCity;
    @BindView(R.id.etDestinationAddress)
    TextView etDestinationAddress;
    @BindView(R.id.etBodyTemperatureToday)
    TextView etBodyTemperatureToday;
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
    QuestionFormBean form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_history);
        ButterKnife.bind(this);
        form = (QuestionFormBean) getIntent().getSerializableExtra("form");
        initWidget();
        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }

        });
        setData(form);
    }

    void initWidget()
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
        String  otherCityVisited="Other city visited in last 14 days?<font color='#FF0000'>*</font>";
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

    private void setData(QuestionFormBean form)
    {
        List<QuestionBean> list=form.form;
        for(int i=0;i<list.size();i++)
        {
            QuestionBean questionBean=list.get(i);
            switch(questionBean.getQuestionId())
            {
                case "01":
                    etName.setText(questionBean.getAnswer());
                    break;
                case "02":
                    etPhoneNumber.setText(questionBean.getAnswer());
                    break;
                case "03":
                    etCurrentCity.setText(questionBean.getAnswer().replace("||"," "));
                    break;
                case "04":
                    etCurrentAddress.setText(questionBean.getAnswer());
                    break;
                case "05":
                    txArrivalDateOfCurrentAddress2.setText(questionBean.getAnswer());
                    break;
                case "06":
                    etNextKinContactPhoneNumber.setText(questionBean.getAnswer());
                    break;
                case "07":
                    etNextKinName.setText(questionBean.getAnswer());
                    break;
                case "08":
                    if("A".equals(questionBean.getAnswer()))
                    {
                        rbOtherCityVisitedYes.setChecked(true);
                        lyHealthInfo.setVisibility(View.VISIBLE);
                        rbOtherCityVisitedNo.setClickable(false);

                    }else
                    {
                        rbOtherCityVisitedNo.setChecked(true);
                        lyHealthInfo.setVisibility(View.GONE);
                        rbOtherCityVisitedYes.setClickable(false);
                    }
                    break;
                case "09":
                    etVisitedCity.setText(questionBean.getAnswer().replace("||"," "));
                    break;
                case "10":
                    txVoyageDate.setText(questionBean.getAnswer());
                    break;
                case "11":
                    etFlightNumber.setText(questionBean.getAnswer());
                    break;
                case "12":
                    etSeatNumber.setText(questionBean.getAnswer());
                    break;
                case "13":
                    txDateEntry.setText(questionBean.getAnswer());
                    break;
                case "14":
                    etCityOfEntry.setText(questionBean.getAnswer().replace("||"," "));
                    break;
                case "15":
                    etDestinationCity.setText(questionBean.getAnswer().replace("||"," "));
                    break;
                case "16":
                    etDestinationAddress.setText(questionBean.getAnswer());
                    break;
                case "17":
                    if("A".equals(questionBean.getAnswer()))
                    {
                        rbContactPatientYes.setChecked(true);
                        rbContactPatientNo.setClickable(false);
                        rbContactPatientDontKnow.setClickable(false);
                    }else if("B".equals(questionBean.getAnswer()))
                    {
                        rbContactPatientYes.setClickable(false);
                        rbContactPatientNo.setChecked(true);
                        rbContactPatientDontKnow.setClickable(false);
                    }else if("C".equals(questionBean.getAnswer()))
                    {
                        rbContactPatientYes.setClickable(false);
                        rbContactPatientNo.setClickable(false);
                        rbContactPatientDontKnow.setChecked(true);
                    }
                    break;
                case "18":
                    if("A".equals(questionBean.getAnswer()))
                    {
                        rbContactPatientFluLikeYes.setChecked(true);
                        rbContactPatientFluLikeNo.setClickable(false);
                    }else if("B".equals(questionBean.getAnswer()))
                    {
                        rbContactPatientFluLikeYes.setClickable(false);
                        rbContactPatientFluLikeNo.setChecked(true);
                    }
                    break;
                case "19":
                    if("A".equals(questionBean.getAnswer()))
                    {
                        rbReportOfConfirmedYes.setChecked(true);
                        rbReportOfConfirmedNo.setClickable(false);
                    }else if("B".equals(questionBean.getAnswer()))
                    {
                        rbReportOfConfirmedYes.setClickable(false);
                        rbReportOfConfirmedNo.setChecked(true);
                    }
                    break;
                case "20":
                    if("A".equals(questionBean.getAnswer()))
                    {
                        rbTwoOrMoreFluLikeYes.setChecked(true);
                        rbTwoOrMoreFluLikeNo.setClickable(false);
                    }else if("B".equals(questionBean.getAnswer()))
                    {
                        rbTwoOrMoreFluLikeYes.setClickable(false);
                        rbTwoOrMoreFluLikeNo.setChecked(true);
                    }
                    break;
                case "21":
                    etBodyTemperatureToday.setText(questionBean.getAnswer());
                    break;
                case "22":
                    if(questionBean.getAnswer().contains("A"))
                    {
                        checkBoxOne.setChecked(true);
                        checkBoxOne.setClickable(false);
                        checkBoxTwo.setClickable(false);
                        checkBoxThree.setClickable(false);
                        checkBoxFour.setClickable(false);
                        checkBoxFive.setClickable(false);
                        checkBoxSix.setClickable(false);
                        checkBoxSeven.setClickable(false);
                    }
                    if(questionBean.getAnswer().contains("B"))
                    {
                        checkBoxOne.setClickable(false);
                        checkBoxTwo.setChecked(true);
                        checkBoxTwo.setClickable(false);
                        checkBoxThree.setClickable(false);
                        checkBoxFour.setClickable(false);
                        checkBoxFive.setClickable(false);
                        checkBoxSix.setClickable(false);
                        checkBoxSeven.setClickable(false);
                    }
                    if(questionBean.getAnswer().contains("C"))
                    {
                        checkBoxOne.setClickable(false);
                        checkBoxTwo.setClickable(false);
                        checkBoxThree.setChecked(true);
                        checkBoxThree.setClickable(false);
                        checkBoxFour.setClickable(false);
                        checkBoxFive.setClickable(false);
                        checkBoxSix.setClickable(false);
                        checkBoxSeven.setClickable(false);
                    }
                    if(questionBean.getAnswer().contains("D"))
                    {
                        checkBoxOne.setClickable(false);
                        checkBoxTwo.setClickable(false);
                        checkBoxThree.setClickable(false);
                        checkBoxFour.setChecked(true);
                        checkBoxFour.setClickable(false);
                        checkBoxFive.setClickable(false);
                        checkBoxSix.setClickable(false);
                        checkBoxSeven.setClickable(false);
                    }
                    if(questionBean.getAnswer().contains("E"))
                    {
                        checkBoxOne.setClickable(false);
                        checkBoxTwo.setClickable(false);
                        checkBoxThree.setClickable(false);
                        checkBoxFour.setClickable(false);
                        checkBoxFive.setChecked(true);
                        checkBoxFive.setClickable(false);
                        checkBoxSix.setClickable(false);
                        checkBoxSeven.setClickable(false);
                    }
                    if(questionBean.getAnswer().contains("F"))
                    {
                        checkBoxOne.setClickable(false);
                        checkBoxTwo.setClickable(false);
                        checkBoxThree.setClickable(false);
                        checkBoxFour.setClickable(false);
                        checkBoxFive.setClickable(false);
                        checkBoxSix.setChecked(true);
                        checkBoxSix.setClickable(false);
                        checkBoxSeven.setClickable(false);
                    }
                    if(questionBean.getAnswer().contains("G"))
                    {
                        checkBoxOne.setClickable(false);
                        checkBoxTwo.setClickable(false);
                        checkBoxThree.setClickable(false);
                        checkBoxFour.setClickable(false);
                        checkBoxFive.setClickable(false);
                        checkBoxSix.setClickable(false);
                        checkBoxSeven.setChecked(true);
                        checkBoxSeven.setClickable(false);
                    }
                    break;
                case "23":
                    if("A".equals(questionBean.getAnswer()))
                    {
                        testStatus1.setChecked(true);
                        testStatus2.setChecked(false);
                        testStatus3.setChecked(false);
                        testStatus4.setChecked(false);
                        testStatus2.setClickable(false);
                        testStatus3.setClickable(false);
                        testStatus4.setClickable(false);
                    }
                    if("B".equals(questionBean.getAnswer()))
                    {
                        testStatus1.setChecked(false);
                        testStatus1.setClickable(false);
                        testStatus2.setChecked(true);
                        testStatus3.setChecked(false);
                        testStatus4.setChecked(false);
                        testStatus3.setClickable(false);
                        testStatus4.setClickable(false);
                    }
                    if("C".equals(questionBean.getAnswer()))
                    {
                        testStatus1.setChecked(false);
                        testStatus2.setChecked(false);
                        testStatus1.setClickable(false);
                        testStatus2.setClickable(false);
                        testStatus3.setChecked(true);
                        testStatus4.setChecked(false);
                        testStatus4.setClickable(false);
                    }
                    if("D".equals(questionBean.getAnswer()))
                    {
                        testStatus1.setChecked(false);
                        testStatus2.setChecked(false);
                        testStatus3.setChecked(false);
                        testStatus1.setClickable(false);
                        testStatus2.setClickable(false);
                        testStatus3.setClickable(false);
                        testStatus4.setChecked(true);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
