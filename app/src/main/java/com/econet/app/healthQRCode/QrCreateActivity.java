package com.econet.app.healthQRCode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.econet.app.homepage.MnemonicWordsSettingActivity;
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer;
import com.github.sumimakito.awesomeqr.RenderResult;
import com.github.sumimakito.awesomeqr.option.RenderOption;
import com.github.sumimakito.awesomeqr.option.color.Color;
import com.econet.app.ServerBeans.myhealth.MyHealthBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.homepage.BaseActivity;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HtmlTagHandler;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.kikenn.ed25519.GenSolAddress;

import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.econet.app.solwallet.R;

/**
 * @author dai.jianhui
 */
public class QrCreateActivity extends BaseActivity {

    ImageView ivBack;

    int mark = 0;
    int total = 0;
    String id;
    private TextView txtMark, txtMessage, txtId, txtName;
    private ImageView imgQr;
    TextView tvTitle,idStatusInfo,healthBadgeName,tvUpdateTime;
    LinearLayout lyDailyEvaluation;

    private String condition;

    String healthStatus="";
    String healthStatusTime="";
    int currentPageRefresh=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);
        imgQr = findViewById(R.id.img_qr);
        ivBack = findViewById(R.id.ivBack);
        tvUpdateTime=findViewById(R.id.tvUpdateTime);
        healthBadgeName=findViewById(R.id.healthBadgeName);
        tvTitle=findViewById(R.id.tvTitle);
        idStatusInfo=findViewById(R.id.idStatusInfo);
        lyDailyEvaluation=findViewById(R.id.lyDailyEvaluation);
        tvTitle.setText("Solana Account");
        SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(QrCreateActivity.this);
        String name=shared.getValueString(Constant.Auth.FIRST_NAME,"My");
        String solAddress = shared.getValueString("app_solAddress","null");
        String lastName=shared.getValueString(Constant.Auth.LAST_NAME,"");
        String updateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //tvUpdateTime.setText();
        tvUpdateTime.setText("Send me SPL token to this address");
        name=name+" "+lastName;
        if(name.length()>8)
        {
            name="My";
        }
        int sdkVersion=22;
        try
        {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        if(sdkVersion > Build.VERSION_CODES.LOLLIPOP){
            if("My".equals(name))
            {
                name =name+" SOL Address";
                healthBadgeName.setText(name);
            }else
            {
                name=name+"<myfont size='38px'></myfont>"+" SOL Address";
                healthBadgeName.setText(Html.fromHtml(name,null, new HtmlTagHandler("myfont")));
            }
        }else {
            if("My".equals(name))
            {
                name =name+" SOL Address";
                healthBadgeName.setText(name);
            }else
            {
                name =name+"'S SOL Address";
                healthBadgeName.setText(name);
            }
        }
        ivBack.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                finish();
            }

        });
        lyDailyEvaluation.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("solana address", solAddress);
                cm.setPrimaryClip(mClipData);
                CustomToast.showToast(QrCreateActivity.this, "copy the sol address to clipboard");
            }
        });
        //currentPageRefresh=getIntent().getIntExtra("currentPageRefresh", 0);
        if(currentPageRefresh==0)
        {
            mark = getIntent().getIntExtra("mark", 0);
            total = getIntent().getIntExtra("total", 0);
            id = getIntent().getStringExtra("id");
            healthStatus=getIntent().getStringExtra("healthStatus");
            healthStatusTime=getIntent().getStringExtra("healthStatusTime");
            showQR(solAddress);
        }
    }

    private HashMap<String,String>getData()
    {
        HashMap<String,String>params=new HashMap<>();
        return params;
    }
    public void showQR(String address)
    {
        try {
            Color color = new Color();
            color.setLight(0xFFFFFFFF);
            if (mark >= 10) {
                color.setDark(android.graphics.Color.parseColor("#F2545B")); // for non-blank spaces
                color.setBackground(0xF2545B);
                condition = "dangerous";
                idStatusInfo.setText(address);
            } else if (mark >= 5) {
                color.setDark(android.graphics.Color.parseColor("#F7B500"));
                color.setBackground(0xF7B500);
                condition = "cure";
                idStatusInfo.setText(address);
                //txtMessage.setText(R.string.yellow_msg);
            } else {
                color.setDark(android.graphics.Color.parseColor("#1CA566"));
                color.setBackground(0x1CA566);
                condition = "safe";
                idStatusInfo.setText(address);
            }
            JSONObject object = new JSONObject();
            object.put("healthStatus", healthStatus);
            object.put("healthStatusTime", healthStatusTime);

            // for the background (will be overriden by background images, if set)
            color.setAuto(false);
            RenderOption renderOption = new RenderOption();
            renderOption.setContent(address); // content to encode
            renderOption.setSize(1000); // size of the final QR code image
            renderOption.setBorderWidth(130); // width of the empty space around the QR code
            //renderOption.setEcl(ErrorCorrectionLevel.M); // (optional) specify an error correction level
            renderOption.setPatternScale(0.8f); // (optional) specify a scale for patterns
            renderOption.setRoundedPatterns(true); // (optional) if true, blocks will be drawn as dots instead
            renderOption.setClearBorder(true); // if set to true, the background will NOT be drawn on the border area
            renderOption.setColor(color); // set a color palette for the QR code
            // renderOption.setBackground();
            RenderResult result = AwesomeQrRenderer.render(renderOption);
            if (result.getBitmap() != null) {
                imgQr.invalidate();
                imgQr.setImageBitmap(result.getBitmap());
                imgQr.refreshDrawableState();
                //imgQr.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                this.finish();
            }
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

}
