package com.econet.app.homepage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.econet.app.dictionary.Constant;
import com.econet.app.dictionary.Dictionary;
import com.econet.app.listviewsolana.ParseSplTokenUtil;
import com.econet.app.listviewsolana.TokenMintUtil;
import com.econet.app.solanabean.getAccountInfo.AccountInfoBean;
import com.econet.app.solanabean.getAccountInfo.Value;
import com.econet.app.solanabean.getFees.FeeBean;
import com.econet.app.solanabean.getFees.FeeCalculator;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.Base64;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CopyCatLog;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.PopupCenterWindow;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.econet.app.uitl.Tools;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.p2p.solanaj.rpc.RpcException;
import org.p2p.solanaj.utils.AssociatedAccountUtil;
import org.p2p.solanaj.utils.SolanaTransferUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author dai.jianhui
 */
public class TransferSplTokenMerchantActivity extends BaseActivity {
    @BindView(R.id.lyLogin)
    LinearLayout lyLogin;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.lyDocumentType)
    LinearLayout lyDocumentType;
    PopupCenterWindow mListPopup;//列表形式弹出框
    @BindView(R.id.tvDocumentType)
    TextView tvDocumentType;

    @BindView(R.id.tvGetFees)
    TextView tvGetFees;

    //用来实现SOL 还是SPL提示
    @BindView(R.id.tvLabel)
    TextView tvLabel;

    @BindView(R.id.etAccountAddress)
    EditText etAccountAddress;

    @BindView(R.id.etAmount)
    EditText etAmount;

    @BindView(R.id.ivTokenSearch)
    ImageView ivTokenSearch;

    boolean  isTransferSOL = false;
    String mintAddress="";
    String splToken="";
    String decimal="";
    //to send to the destination
    String solanaAccount="";
    String amount="";
    String label="";

    boolean isDonate=false;
    String  donateSolAddress="4z8D2vPNuvt8gK22T8mvnpEPhSmiTpn9HZBF4Jw9YUq6";
    String  donateSolAmount="0.0005";

    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(TransferSplTokenMerchantActivity.this);
    String solAddress=shared.getValueString("app_solAddress","");
    String signer=shared.getValueString("app_signer","");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_token_merchant);
        ButterKnife.bind(TransferSplTokenMerchantActivity.this);
        setTitle("Transfer");
        mintAddress = (String) getIntent().getSerializableExtra("mint");
        isTransferSOL = (boolean) getIntent().getSerializableExtra("isTransferSol");
        splToken=(String) getIntent().getSerializableExtra("tokenName");
        solanaAccount=(String) getIntent().getSerializableExtra("solanaAccount");
        amount=(String) getIntent().getSerializableExtra("amount");
        label=(String) getIntent().getSerializableExtra("label");
        if(isTransferSOL) {
            tvLabel.setText("Transfer SOL");
        }else {
            tvLabel.setText("Transfer SPL TOKEN:("+splToken+")");
        }
        decimal= TokenMintUtil.getMintDecimal(mintAddress);
        //set the data from scan
        etAccountAddress.setText(solanaAccount);
        etAmount.setText(amount);
        //todo more info like label and memo to show to the user
        initWidget();
    }
    private void initWidget()
    {
        //getFees
        getFees();
        ivTokenSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent mIntent =new Intent(TransferSplTokenMerchantActivity.this, CaptureActivity.class);
                startActivityForResult(mIntent,1000);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        lyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                if(checkData())
                {
                    HashMap<String,String>headers=new HashMap<>();
                    headers.put("Accept","*/*");
                    headers.put("Content-Type","application/json");
                    String json = ParseSplTokenUtil.getAccountInfo(etAccountAddress.getText().toString().trim());
                    showLoadingDialog("Trading...");
                    HttpUtil.getDataFromServerUsePost(Constant.Solana.solMainNet,json,headers,new Callback(){
                        @Override
                        public void onFailure(Call call, IOException e) {
                            hideLoadingDialog();
                            CustomToast.showToast(TransferSplTokenMerchantActivity.this,"access network failed");
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //hideLoadingDialog();
                            AccountInfoBean accountInfoBean=null;
                            try{
                                accountInfoBean = JSON.parseObject(response.body().string(), AccountInfoBean.class);
                            }catch (Exception e)
                            {
                                hideLoadingDialog();
                                CustomToast.showToast(TransferSplTokenMerchantActivity.this,"parse data failed");
                            }
                            Looper.prepare();
                            if(accountInfoBean!=null)
                            {
                                if(accountInfoBean.getResult()!=null)
                                {
                                    Value value =accountInfoBean.getResult().getValue();
                                    if(value!=null)
                                    {
                                        String owner=value.getOwner();
                                        if(isTransferSOL)
                                        {
                                            if(ParseSplTokenUtil.ContractProgram.SYSTEM_PROGRAM.equals(owner))
                                            {
                                                //转sol
                                                String toAddress=etAccountAddress.getText().toString().trim();
                                                String amount=etAmount.getText().toString().trim();
                                                try {
                                                    String resultSignature=SolanaTransferUtil.transferSol(solAddress,toAddress,amount,signer);
                                                    System.out.println(resultSignature+"++++++++++++++++++++++copycat");
                                                    if(resultSignature.length()==88||resultSignature.length()==87)
                                                    {
                                                        hideLoadingDialog();
                                                        CustomToast.showToast(TransferSplTokenMerchantActivity.this,"transfer sol success");
                                                        if(isDonate){SolanaTransferUtil.transferSol(solAddress,donateSolAddress,donateSolAmount,signer);}
                                                    }else
                                                    {
                                                        hideLoadingDialog();
                                                        CustomToast.showToast(TransferSplTokenMerchantActivity.this,resultSignature);
                                                    }
                                                } catch (RpcException e) {
                                                    hideLoadingDialog();
                                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,e.toString());
                                                    e.printStackTrace();
                                                }
                                            }else
                                            {
                                                CustomToast.showToast(TransferSplTokenMerchantActivity.this,"the destination account is not sol main account");
                                            }
                                        }
                                        else //转账token
                                        {
                                            if(ParseSplTokenUtil.ContractProgram.TOKEN_PROGRAM.equals(owner))  //转账token-->输入地址是token地址
                                            {
                                                //转token
                                                String toAddress=etAccountAddress.getText().toString().trim();
                                                String amount=etAmount.getText().toString().trim();
                                                String mySplTokenAddress= AssociatedAccountUtil.createAssociatedAccountAddress(solAddress,mintAddress);
                                                try {
                                                    //showLoadingDialog();
                                                    String resultSignature=SolanaTransferUtil.transferSplToken(mySplTokenAddress,toAddress,decimal,amount,signer);
                                                    if(resultSignature.length()==88||resultSignature.length()==87)
                                                    {
                                                        hideLoadingDialog();
                                                        CustomToast.showToast(TransferSplTokenMerchantActivity.this,"transfer token success");
                                                        if(isDonate){SolanaTransferUtil.transferSol(solAddress,donateSolAddress,donateSolAmount,signer);}
                                                    }else
                                                    {
                                                        hideLoadingDialog();
                                                        CustomToast.showToast(TransferSplTokenMerchantActivity.this,resultSignature);
                                                    }
                                                } catch (RpcException e) {
                                                    hideLoadingDialog();
                                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,e.toString());
                                                }
                                            }else if(ParseSplTokenUtil.ContractProgram.SYSTEM_PROGRAM.equals(owner))  //转账token-->输入地址是主地址
                                            {
                                                String destinationSolAddress=etAccountAddress.getText().toString().trim();
                                                String amount=etAmount.getText().toString().trim();
                                                String destinationAssociatedTokenAddress= AssociatedAccountUtil.createAssociatedAccountAddress(destinationSolAddress,mintAddress);
                                                String sourceTokenAddress= AssociatedAccountUtil.createAssociatedAccountAddress(solAddress,mintAddress);
                                                //二次请求判断是否关联地址存在
                                                checkAssociatedAccountAndTransfer(destinationAssociatedTokenAddress,sourceTokenAddress, destinationAssociatedTokenAddress, decimal, amount, signer,destinationSolAddress);
                                            }
                                        }
                                    }else  //value is null means the account is not exist
                                    {
                                        if(isTransferSOL)
                                        {
                                            //TODO
                                            //show the dialog to info the user that destination account sol balance is zero,still continue
                                            String toAddress=etAccountAddress.getText().toString().trim();
                                            String amount=etAmount.getText().toString().trim();
                                            try {
                                                String resultSignature=SolanaTransferUtil.transferSol(solAddress,toAddress,amount,signer);
                                                if(resultSignature.length()==88||resultSignature.length()==87)
                                                {
                                                    hideLoadingDialog();
                                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,"transfer sol success");
                                                    if(isDonate){SolanaTransferUtil.transferSol(solAddress,donateSolAddress,donateSolAmount,signer);}
                                                }else
                                                {
                                                    hideLoadingDialog();
                                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,resultSignature);
                                                }
                                            } catch (RpcException e) {
                                                hideLoadingDialog();
                                                CustomToast.showToast(TransferSplTokenMerchantActivity.this,e.toString());
                                            }
                                        }
                                        else// a little dangerous, we don't know what is the destination account type
                                        {
                                            //create associated account and transfer
                                            String toAddress=etAccountAddress.getText().toString().trim();
                                            String amount=etAmount.getText().toString().trim();
                                            String address= AssociatedAccountUtil.createAssociatedAccountAddress(toAddress,mintAddress);
                                            String mySplTokenAddress= AssociatedAccountUtil.createAssociatedAccountAddress(solAddress,mintAddress);
                                            try {
                                                //showLoadingDialog();
                                                String resultSignature=SolanaTransferUtil.createAssociatedAccount(address,toAddress,mintAddress,signer);
                                                if(resultSignature.length()==88||resultSignature.length()==87)
                                                {
                                                    //睡眠15秒，等待关联账户被确认
                                                    try {
                                                        Thread.sleep(20000);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                    String resultTransferSignature=SolanaTransferUtil.transferSplToken(mySplTokenAddress,address,decimal,amount,signer);
                                                    if(resultTransferSignature.length()==88||resultTransferSignature.length()==87)
                                                    {
                                                        hideLoadingDialog();
                                                        CustomToast.showToast(TransferSplTokenMerchantActivity.this,"transfer success");
                                                        if(isDonate){SolanaTransferUtil.transferSol(solAddress,donateSolAddress,donateSolAmount,signer);}
                                                    }else
                                                    {
                                                        hideLoadingDialog();
                                                        CustomToast.showToast(TransferSplTokenMerchantActivity.this,resultTransferSignature);
                                                    }
                                                }else {
                                                    hideLoadingDialog();
                                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,resultSignature);
                                                }
                                            } catch (RpcException e) {
                                                hideLoadingDialog();
                                                CustomToast.showToast(TransferSplTokenMerchantActivity.this,e.toString());
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    hideLoadingDialog();
                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,"check the destination account is exist");
                                }
                            }
                            Looper.loop();
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
        tvGetFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFees();
            }
        });
    }
    private HashMap<String,String> getData()
    {
        HashMap<String,String>params=new HashMap<>();
        params.put("idType",Dictionary.getIdType(tvDocumentType.getText().toString()));
        params.put("userType","Personal");
        params.put("idNo",etAccountAddress.getText().toString());
        params.put("password", Base64.encodeBytes(etAmount.getText().toString().trim().getBytes()));
        return params;
    }

    //basic check
    private boolean checkData()
    {
//        if(TextUtils.isEmpty(tvDocumentType.getText().toString()))
//        {
//            CustomToast.showToast(TransferSplTokenActivity.this,"please select the document type");
//            return false;
//        }
        if(TextUtils.isEmpty(etAccountAddress.getText().toString()))
        {
            CustomToast.showToast(TransferSplTokenMerchantActivity.this,"please enter the account address");
            return false;
        }
        if(TextUtils.isEmpty(etAmount.getText().toString()))
        {
            CustomToast.showToast(TransferSplTokenMerchantActivity.this,"please enter the amount");
            return false;
        }
//        if(!TextUtils.isEmpty(etAmount.getText().toString()))
//        {
//            //regular express check the input
//        }
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
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                etAccountAddress.setText(contents);
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
        SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(TransferSplTokenMerchantActivity.this);
        params.put("idType",shared.getValueString(Constant.User.LOGIN_TYPE,""));
        params.put("userType","Personal");
        params.put("idNo", shared.getValueString(Constant.User.LOGIN_ID,""));
        params.put("password", shared.getValueString(Constant.User.LOGIN_PASSWORD,""));
        return params;
    }

    public void checkAssociatedAccountAndTransfer(String account,String sourceTokenAddress,String destinationAssociatedTokenAddress,String decimal,String amount,String signer,String destinationSolAddress)
    {
        System.out.println("========enter getaccountInfo===========copycat");
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Content-Type","application/json");
        String json = ParseSplTokenUtil.getAccountInfo(account);
        //showLoadingDialog();
        HttpUtil.getDataFromServerUsePost(Constant.Solana.solMainNet,json,headers,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
                CustomToast.showToast(TransferSplTokenMerchantActivity.this, "access network failed");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AccountInfoBean accountInfoBean = null;
                try {
                    accountInfoBean = JSON.parseObject(response.body().string(), AccountInfoBean.class);
                } catch (Exception e) {
                    hideLoadingDialog();
                    CustomToast.showToast(TransferSplTokenMerchantActivity.this, "parse data failed");
                }
                if (accountInfoBean != null) {
                    if (accountInfoBean.getResult() != null) {
                        Value value = accountInfoBean.getResult().getValue();
                        if (value != null) { //转账token-->输入地址是主地址-->当关联地址已经存在
                            try {
                                //showLoadingDialog();
                                String resultTransferSignature=SolanaTransferUtil.transferSplToken(sourceTokenAddress,destinationAssociatedTokenAddress,decimal,amount,signer);
                                if(resultTransferSignature.length()==88||resultTransferSignature.length()==87)
                                {
                                    hideLoadingDialog();
                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,"transfer token success");
                                    if(isDonate){SolanaTransferUtil.transferSol(solAddress,donateSolAddress,donateSolAmount,signer);}
                                }else {
                                    hideLoadingDialog();
                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,resultTransferSignature);
                                }
                            } catch (RpcException e) {
                                hideLoadingDialog();
                                CustomToast.showToast(TransferSplTokenMerchantActivity.this,e.toString());
                            }
                        }else  //转账token-->输入地址是主地址-->当关联地址不存在
                        {
                            try {
                                //showLoadingDialog();
                                String resultSignature=SolanaTransferUtil.createAssociatedAccount(destinationAssociatedTokenAddress,destinationSolAddress,mintAddress,signer);
                                if(resultSignature.length()==88||resultSignature.length()==87)
                                {
                                    //睡眠15秒,等待被创建的关联账户被确认,立即调用会出现如下错误
                                    //org.p2p.solanaj.rpc.RpcException: Transaction simulation failed: Error processing Instruction 0: invalid account data for instruction
                                    try {
                                        Thread.sleep(20000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    String resultTransferSignature=SolanaTransferUtil.transferSplToken(sourceTokenAddress,destinationAssociatedTokenAddress,decimal,amount,signer);
                                    if(resultTransferSignature.length()==88||resultTransferSignature.length()==87)
                                    {
                                        hideLoadingDialog();
                                        CustomToast.showToast(TransferSplTokenMerchantActivity.this,"transfer token success");
                                    }else
                                    {
                                        hideLoadingDialog();
                                        CustomToast.showToast(TransferSplTokenMerchantActivity.this,resultTransferSignature);
                                        if(isDonate){SolanaTransferUtil.transferSol(solAddress,donateSolAddress,donateSolAmount,signer);}
                                    }
                                }else
                                {
                                    hideLoadingDialog();
                                    CustomToast.showToast(TransferSplTokenMerchantActivity.this,resultSignature);
                                }
                            } catch (RpcException e) {
                                hideLoadingDialog();
                                CustomToast.showToast(TransferSplTokenMerchantActivity.this,e.toString());
                            }
                        }
                    }
                }

            }
        });
    }

    private void getFees()
    {
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Content-Type","application/json");
        String json = ParseSplTokenUtil.getFees();
        HttpUtil.getDataFromServerUsePost(Constant.Solana.solMainNet,json,headers,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CustomToast.showToast(TransferSplTokenMerchantActivity.this, "get fee failed");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FeeBean feeBean = null;
                try {
                    feeBean = JSON.parseObject(response.body().string(), FeeBean.class);
                } catch (Exception e) {
                    CustomToast.showToast(TransferSplTokenMerchantActivity.this, "parse fee Bean failed");
                }
                Looper.prepare();
                FeeBean finalFeeBean = feeBean;
                TransferSplTokenMerchantActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        if(finalFeeBean !=null)
                        {
                            if(finalFeeBean.getResult()!=null)
                            {
                                com.econet.app.solanabean.getFees.Value value =finalFeeBean.getResult().getValue();
                                if(value!=null)
                                {
                                    FeeCalculator calculator= value.getFeeCalculator();
                                    if(calculator!=null)
                                    {
                                      int fee=calculator.getLamportsPerSignature();
                                      double feeForSol= (double)fee/1000000000L;
                                      DecimalFormat df=new DecimalFormat("#.########");
                                      tvGetFees.setText("fee ≈ "+df.format(feeForSol)+"SOL");
                                    }
                                }
                            }
                        }else
                        {
                            //set the default fees
                            tvGetFees.setText("fee ≈ 0.000005SOL");
                        }
                    }
                });
                Looper.loop();
            }
        });
    }
}
