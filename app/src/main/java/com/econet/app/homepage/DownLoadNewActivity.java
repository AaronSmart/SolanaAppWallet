package com.econet.app.homepage;

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

import com.econet.app.dictionary.Constant;
import com.econet.app.listviewsolana.ParseSplTokenUtil;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HtmlTagHandler;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer;
import com.github.sumimakito.awesomeqr.RenderResult;
import com.github.sumimakito.awesomeqr.option.RenderOption;
import com.github.sumimakito.awesomeqr.option.color.Color;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @author dai.jianhui
 */
public class DownLoadNewActivity extends BaseActivity {

    ImageView ivBack;

    int mark = 0;
    int total = 0;
    String id;
    private TextView txtMark, txtMessage, txtId, txtName;
    private ImageView imgQr;
    TextView tvTitle,idStatusInfo,healthBadgeName,tvUpdateTime;
    LinearLayout lyDailyEvaluation;
    LinearLayout lySolanaMerchantPay;

    private String condition;

    String healthStatus="";
    String healthStatusTime="";
    int currentPageRefresh=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_new);
        imgQr = findViewById(R.id.img_qr);
        ivBack = findViewById(R.id.ivBack);
        tvUpdateTime=findViewById(R.id.tvUpdateTime);
        healthBadgeName=findViewById(R.id.healthBadgeName);
        tvTitle=findViewById(R.id.tvTitle);
        idStatusInfo=findViewById(R.id.idStatusInfo);
        lyDailyEvaluation=findViewById(R.id.lyDailyEvaluation);
        lySolanaMerchantPay=findViewById(R.id.lySolanaMerchantPay);
        tvTitle.setText("Check Version");
        SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(DownLoadNewActivity.this);
        String solAddress = "share this download QR code to the other people";
        //tvUpdateTime.setText();
        tvUpdateTime.setText("Scan the QR code to download new version");
        healthBadgeName.setText("www.kikenn.com");
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
                CustomToast.showToast(DownLoadNewActivity.this, "copy the sol address to clipboard");
            }
        });
        lySolanaMerchantPay.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                //todo call the qr scan method to implements the solana standard pay protocol
                Intent mIntent =new Intent(DownLoadNewActivity.this, CaptureActivity.class);
                startActivityForResult(mIntent,1000);
                //CustomToast.showToast(QrCreateActivity.this, "this is solana merchant pay");
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
            renderOption.setContent("http://www.kikenn.com/app/solanawallet.apk"); // content to encode
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
            if (requestCode == 1000) {
              if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                //get the solana pay param
                HashMap<String,String> payMap= ParseSplTokenUtil.getMerchantPayMap(contents);
                if(payMap.size()==0)
                {
                    CustomToast.showToast(DownLoadNewActivity.this, "the QR code is not solana pay standard");
                }else
                {
                    //todo according to the solana pay protocol do some adopt
                    String  mint =payMap.get("spl-token");
                    String  solanaAccount =payMap.get("solanaAccount");
                    String  amount=payMap.get("amount");
                    String label =payMap.get("label");
                    Intent intent = new Intent(DownLoadNewActivity.this, TransferSplTokenMerchantActivity.class);
                    Bundle bundle = new Bundle();

                    if(mint==null | "".equals(mint))
                    {
                        bundle.putSerializable("isTransferSol",true);//transfer sol
                        bundle.putSerializable("mint", mint);
                        bundle.putSerializable("tokenName","");
                    }else
                    {
                        bundle.putSerializable("isTransferSol",false);//transfer token
                        bundle.putSerializable("mint", mint);
                        bundle.putSerializable("tokenName",ParseSplTokenUtil.getTokenName(mint));
                    }
                    bundle.putSerializable("solanaAccount", solanaAccount);
                    bundle.putSerializable("amount",amount);
                    bundle.putSerializable("label",label);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
             }
           }
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

}
