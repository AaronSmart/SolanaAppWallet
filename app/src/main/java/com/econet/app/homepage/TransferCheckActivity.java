package com.econet.app.homepage;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.econet.app.dictionary.Constant;
import com.econet.app.listviewsolana.ParseSplTokenUtil;
import com.econet.app.solanabean.getSignatureStatus.Result;
import com.econet.app.solanabean.getSignatureStatus.SignatureStatus;
import com.econet.app.solanabean.getSignatureStatus.Value;
import com.econet.app.solanabean.getSignatureStatusFromSolscan.SignatureStatusFromSolscan;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author dai.jianhui
 */
public class TransferCheckActivity extends BaseActivity {

    @BindView(R.id.lyReset)
    LinearLayout lyReset;

    @BindView(R.id.lyNew)
    LinearLayout lyNew;

    @BindView(R.id.tvTransferSignature)
    TextView tvTransferSignature;

    @BindView(R.id.tvSignatureStatus)
    TextView tvSignatureStatus;

    @BindView(R.id.tvStatusLogo)
    TextView tvStatusLogo;
    @BindView(R.id.tvStatusLogo2)
    TextView tvStatusLogo2;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    int retryTimes=0;
    String signature ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_check);
        ButterKnife.bind(this);
        signature = (String) getIntent().getSerializableExtra("signature");
        tvTransferSignature.setText(signature);
        setTitle("Transfer result");
        initWidget();
    }
    private void initWidget() {
        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        lyReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                //getSignatureStatus(true);
                getSignatureStatusFromSolscan(true);
            }
        });
        lyNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                ClipboardManager cm = (ClipboardManager) TransferCheckActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("signature", signature);
                cm.setPrimaryClip(mClipData);
                CustomToast.showToast(TransferCheckActivity.this, "already copy the mnemonic words to clipboard");
            }
        });
//        new Thread(new Runnable() {
//            @Override public void run() {
//
//                Timer timer=new Timer();
//                TimerTask task=new TimerTask() {
//                    public void run() {
//                        if(tvSignatureStatus.getText().toString().contains("confirmed")||tvSignatureStatus.getText().toString().contains("finalized"))
//                        {
//                            TLogService.logi("CopyCopy","copycat","=================enter the ssss======");
//                        }else
//                        {
//                            if(retryTimes<3)
//                            {
//                                //  Looper.prepare();
//                                //getSignatureStatusFromSolscan(false);
//                                TLogService.logi("CopyCopy","copycat","===============================");
//                                TLogService.logi("CopyCopy","copycat","=================copycat stest"+retryTimes);
//                                //  Looper.loop();
//                            }
//                        }
//                    }
//                };
//                timer.schedule(task, 1000,5000);
//
//            }
//        }).start();

//        for(int i=0;i<3;i++)
//        {
//           if(tvSignatureStatus.getText().toString().contains("confirmed")||tvSignatureStatus.getText().toString().contains("finalized"))
//            {
//                TLogService.logi("CopyCopy","copycat","=================enter the ssss======");
//            }else{
//               TLogService.logi("CopyCopy","copycat","=================enter the ttt======");
//               getSignatureStatusFromSolscan(false);
//           }
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
//        scheduledThreadPool.scheduleAtFixedRate(()->{
//            if(tvSignatureStatus.getText().toString().contains("confirmed")||tvSignatureStatus.getText().toString().contains("finalized"))
//            {
//                TLogService.logi("CopyCopy","copycat","=================enter the ssss======");
//
//            }else
//            {
//                if(retryTimes<3)
//                {
//                    Looper.prepare();
//                    getSignatureStatusFromSolscan(false);
//                    TLogService.logi("CopyCopy","copycat","=============xx==================");
//                    TLogService.logi("CopyCopy","copycat","=================copycat test"+retryTimes);
//                    Looper.loop();
//                }
//            }
//        }
//       ,1, 3, TimeUnit.SECONDS);
        try {
                Thread.sleep(4000);
                 getSignatureStatusFromSolscan(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
    private synchronized void getSignatureStatus(boolean byHand)
    {
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Content-Type","application/json");
        String json = ParseSplTokenUtil.getSignatureStatus(signature);
        if(byHand==false)
        {
            retryTimes++;
        }
        showLoadingDialog("pending...");
        HttpUtil.getDataFromServerUsePost(Constant.Solana.solMainNet,json,headers,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CustomToast.showToast(TransferCheckActivity.this, "get signature status failed");
                hideLoadingDialog();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                hideLoadingDialog();
                SignatureStatus signatureStatus = null;
                try {
                    signatureStatus = JSON.parseObject(response.body().string(), SignatureStatus.class);
                } catch (Exception e) {
                    CustomToast.showToast(TransferCheckActivity.this, "parse signature status bean failed");
                }
                if(signatureStatus!=null)
                {
                    Result result =signatureStatus.getResult();
                    if(result!=null)
                    {
                        List<Value> value= result.getValue();
                        if(value!=null)
                        {
                            // value
                            if(value.get(0)!=null)
                            {
                                String confirmationStatus=value.get(0).getConfirmationStatus();
                                if("confirmed".equalsIgnoreCase(confirmationStatus)||"finalized".equalsIgnoreCase(confirmationStatus))
                                {
                                    Looper.prepare();
                                    TransferCheckActivity.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            tvSignatureStatus.setText("transfer status "+confirmationStatus);
                                            tvStatusLogo.setVisibility(1);
                                            tvStatusLogo2.setVisibility(0);
                                            CustomToast.showToast(TransferCheckActivity.this, "transfer succeed");
                                        }
                                    });
                                    Looper.loop();
                                }else // if the signature status is not confirmed, we need loop required
                                {
                                    Looper.prepare();
                                    TransferCheckActivity.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            tvSignatureStatus.setText("transfer status "+confirmationStatus);
                                            tvStatusLogo.setVisibility(0);
                                            tvStatusLogo2.setVisibility(1);
                                            CustomToast.showToast(TransferCheckActivity.this, "update succeed");
                                        }
                                    });
                                    Looper.loop();
                                    //this is not work not tested
                                    if(byHand==false)// when handle the recheck button,does not need try 3 times
                                    {
                                        if(retryTimes>3)
                                        {
                                            hideLoadingDialog();
                                            CustomToast.showToast(TransferCheckActivity.this, "time out,please check by hand");
                                        }else
                                        {
                                            getSignatureStatus(false);
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        });
    }
    //use the sol scan to check the signature
    private synchronized int getSignatureStatusFromSolscan(boolean byHand){
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","application/json, text/plain, */*");
        headers.put("Sec-Fetch-Mode","cors");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36 Core/1.77.87.400 QQBrowser/10.9.4613.400");
        HashMap<String,String>params=new HashMap<>();
        params.put("tx",signature);
        if(byHand==false)
        {
            retryTimes++;
        }
        showLoadingDialog("pending...");
        HttpUtil.getDataFromServerUseGet(Constant.Solana.solscanNetTx,params,headers,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                CustomToast.showToast(TransferCheckActivity.this, "get signature status failed");
                hideLoadingDialog();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                hideLoadingDialog();
                SignatureStatusFromSolscan signatureStatus = null;
                try {
                    signatureStatus = JSON.parseObject(response.body().string(), SignatureStatusFromSolscan.class);
                } catch (Exception e) {
                    CustomToast.showToast(TransferCheckActivity.this, "parse signature status bean failed");
                }
                if(signatureStatus!=null)
                {
                    String confirmationStatus=signatureStatus.getTxStatus();
                    if("confirmed".equalsIgnoreCase(confirmationStatus)||"finalized".equalsIgnoreCase(confirmationStatus))
                    {
                        Looper.prepare();
                        TransferCheckActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                tvSignatureStatus.setText("transfer status "+confirmationStatus);
                                tvStatusLogo.setVisibility(View.VISIBLE);
                                tvStatusLogo2.setVisibility(View.GONE);
                                CustomToast.showToast(TransferCheckActivity.this, "transfer succeed");
                            }
                        });
                        Looper.loop();
                    }else // if the signature status is not confirmed, we need loop required
                    {
                        Looper.prepare();
                        TransferCheckActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                tvSignatureStatus.setText("transfer status "+confirmationStatus);
                                tvStatusLogo.setVisibility(View.GONE);
                                tvStatusLogo2.setVisibility(View.VISIBLE);
                                CustomToast.showToast(TransferCheckActivity.this, "update succeed");
                            }
                        });
//                        if(byHand==false)// when handle the recheck button,does not need try 3 times
//                        {
//                            if(retryTimes>3)
//                            {
//                                hideLoadingDialog();
//                                CustomToast.showToast(TransferCheckActivity.this, "time out,please check by hand");
//                            }else
//                            {
//                                getSignatureStatusFromSolscan(false);
//                            }
//                        }
                        Looper.loop();
                    }
                }
            }
        });
        return 0;
    }

    private void getSignatureStatusFromSolscan2(boolean byHand){
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","application/json, text/plain, */*");
        headers.put("Sec-Fetch-Mode","cors");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36 Core/1.77.87.400 QQBrowser/10.9.4613.400");
        HashMap<String,String>params=new HashMap<>();
        params.put("tx",signature);
        if(byHand==false)
        {
            retryTimes++;
        }
        showLoadingDialog("pending...");
        HttpUtil.getDataFromServerUseGet(Constant.Solana.getSolscanNetTxMock,params,headers,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                CustomToast.showToast(TransferCheckActivity.this, "get signature status failed");
                hideLoadingDialog();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                hideLoadingDialog();
                SignatureStatusFromSolscan signatureStatus = null;
                try {
                    signatureStatus = JSON.parseObject(response.body().string(), SignatureStatusFromSolscan.class);
                } catch (Exception e) {
                    CustomToast.showToast(TransferCheckActivity.this, "parse signature status bean failed");
                }
                if(signatureStatus!=null)
                {
                    String confirmationStatus=signatureStatus.getTxStatus();
                    if("confirmed".equalsIgnoreCase(confirmationStatus)||"finalized".equalsIgnoreCase(confirmationStatus))
                    {
                        Looper.prepare();
                        TransferCheckActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                tvSignatureStatus.setText("transfer status "+confirmationStatus);
                                tvStatusLogo.setVisibility(View.VISIBLE);
                                tvStatusLogo2.setVisibility(View.GONE);
                                CustomToast.showToast(TransferCheckActivity.this, "transfer succeed");
                            }
                        });
                        Looper.loop();
                    }else // if the signature status is not confirmed, we need loop required
                    {
                        Looper.prepare();
//                        TransferCheckActivity.this.runOnUiThread(new Runnable() {
//                            public void run() {
//                                tvSignatureStatus.setText("transfer status "+confirmationStatus);
//                                tvStatusLogo.setVisibility(View.GONE);
//                                tvStatusLogo2.setVisibility(View.VISIBLE);
//                                CustomToast.showToast(TransferCheckActivity.this, "update succeed");
//                            }
//                        });
//                        if(byHand==false)// when handle the recheck button,does not need try 3 times
//                        {
//                            if(retryTimes>3)
//                            {
//                                hideLoadingDialog();
//                                CustomToast.showToast(TransferCheckActivity.this, "time out,please check by hand");
//                            }else
//                            {
//                                getSignatureStatusFromSolscan(false);
//                            }
//                        }
                        Message  message=new Message();
                        message.what=MSG_LOAD_FAILED;
                        mHandler.sendMessage(message);
                        Looper.loop();
                    }
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_FAILED:
                    tvSignatureStatus.setText("transfer status "+"xx");
                    tvStatusLogo.setVisibility(View.GONE);
                    tvStatusLogo2.setVisibility(View.VISIBLE);
                    CustomToast.showToast(TransferCheckActivity.this, "update succeed");
                    if(retryTimes>3)
                    {
                        hideLoadingDialog();
                        CustomToast.showToast(TransferCheckActivity.this, "time out,please check by hand");
                    }else
                    {
                        //getSignatureStatusFromSolscan(false);
                    }
                    break;
            }
        }
    };
    private static final int MSG_LOAD_FAILED = 0x0003;
}
