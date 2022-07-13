package com.econet.app.homepage;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.econet.app.dictionary.Constant;
import com.econet.app.listviewsolana.ParseSplTokenUtil;
import com.econet.app.solanabean.getAccountInfo.AccountInfoBean;
import com.econet.app.solanabean.getAccountInfo.Value;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.econet.app.uitl.ZeroWidthStringUtil;
import com.kikenn.ed25519.GenSolAddress;

import org.p2p.solanaj.rpc.RpcException;
import org.p2p.solanaj.utils.AssociatedAccountUtil;
import org.p2p.solanaj.utils.SolanaTransferUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author dai.jianhui
 */
public class AirdropActivity extends BaseActivity {

    @BindView(R.id.lyStart)
    LinearLayout lyStart;

    @BindView(R.id.lyLoad)
    LinearLayout lyLoad;

    @BindView(R.id.lyTransfer)
    LinearLayout lyTransfer;

    @BindView(R.id.etBlockId)
    EditText etBlockId;

    @BindView(R.id.etTotalCount)
    EditText etTotalCount;

    @BindView(R.id.etSingerCount)
    EditText etSingerCount;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.ivBackLinear)
    LinearLayout ivBackLinear;

    List<String> singerListForAirdrop=new ArrayList<>();
    Set<String> singerSetForAirdrop=new HashSet<>();

    Object lock =new Object();

    String airdropTokenMint="EwHqbMUMX33JjWAhxSg9vsX3miWqncsgpnAbqn9nhJwZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airdrop_setting);
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
        lyStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                String blockid=etBlockId.getText().toString().trim();
                getBlockTranscationCount(blockid);

            }
        });
        lyLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                singerListForAirdrop.clear();
                singerSetForAirdrop.clear();
                int total=Integer.parseInt(etTotalCount.getText().toString().trim());
                for(int i=0; i<total; i++)
                {
                    getBlockTranscactionList(etBlockId.getText().toString().trim(),i+"");
                    i=i+20;
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getBlockTranscactionList(etBlockId.getText().toString().trim(),0+"");
            }
        });
        lyTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(AirdropActivity.this);
                String solAddress=shared.getValueString("app_solAddress","");
                String signer=shared.getValueString("app_signer","");
                final int[] failTimes = {0};
                for(String airdropSolAddress:singerSetForAirdrop)
                {
                    try {
                        String mySplTokenAddress= AssociatedAccountUtil.createAssociatedAccountAddress(solAddress,airdropTokenMint);
                        String toSplTokenAddress= AssociatedAccountUtil.createAssociatedAccountAddress(airdropSolAddress,airdropTokenMint);
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        Thread t = new Thread(new Runnable(){
//                            public void run(){
//                                Looper.prepare();
//                                checkAssociatedAccountAndTransfer(toSplTokenAddress,mySplTokenAddress,toSplTokenAddress,"9","2", signer,airdropSolAddress);
//                                Looper.loop();
//                            }
//                        });
//                        t.start();
                        checkAssociatedAccountAndTransfer(toSplTokenAddress,mySplTokenAddress,toSplTokenAddress,"9","2", signer,airdropSolAddress);
                    } catch (UnsupportedEncodingException  e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String mnemonicToAddress(String mnemonicWords)
    {
        String temp=mnemonicWords.trim();
        String[] words=temp.split(" ");
        List<String> wordList=new ArrayList<String>();
        for(String item:words)
        {
            wordList.add(item);
        }
        String solAddress = GenSolAddress.genSolAddress(wordList);
        return solAddress;
    }
    private String mnemonicToSingerBase58Str(String mnemonicWords)
    {
        String temp=mnemonicWords.trim();
        String[] words=temp.split(" ");
        List<String> wordList=new ArrayList<String>();
        for(String item:words)
        {
            wordList.add(item);
        }
        String solAddress = GenSolAddress.genSignerString(wordList);
        return solAddress;
    }

    private void getBlockTranscationCount(String blockId)
    {
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Content-Type","application/json");
        HashMap<String,String>params=new HashMap<>();
        params.put("block",blockId);
        showLoadingDialog();
        HttpUtil.getDataFromServerUseGet("https://api.solscan.io/block",params,headers,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AirdropActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        CustomToast.showToast(AirdropActivity.this,"fail to access the network");
                        hideLoadingDialog();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
               int total = ParseSplTokenUtil.getBlockTransactionTotalCountForAirdrop(response.body().string());
                AirdropActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        etTotalCount.setText(total+"");
                        hideLoadingDialog();
                    }
                });
            }
        });
    }

    private void getBlockTranscactionList(String blockId,String offset)
    {
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Content-Type","application/json");
        HashMap<String,String>params=new HashMap<>();
        params.put("block",blockId);
        params.put("offset",offset);
        params.put("size","20");
        showLoadingDialog();
        HttpUtil.getDataFromServerUseGet("https://api.solscan.io/block/txs",params,headers,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                AirdropActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        CustomToast.showToast(AirdropActivity.this,"fail to access the network");
                        hideLoadingDialog();
                    }
                });
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<String> singerList = ParseSplTokenUtil.getBlockTransactionSingerListForAirdrop(response.body().string());
                AirdropActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        singerListForAirdrop.addAll(singerList);
                        singerSetForAirdrop.addAll(singerList);
                        etSingerCount.setText(singerListForAirdrop.size()+" "+singerSetForAirdrop.size());
                        hideLoadingDialog();
                    }
                });
                synchronized (lock) {
                    lock.notifyAll();
                }
            }
        });
    }

    public void checkAssociatedAccountAndTransfer(String account,String sourceTokenAddress,String destinationAssociatedTokenAddress,String decimal,String amount,String signer,String destinationSolAddress)
    {
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Content-Type","application/json");
        String json = ParseSplTokenUtil.getAccountInfo(account);
        showLoadingDialog();
        HttpUtil.getDataFromServerUsePost(Constant.Solana.solMainNet,json,headers,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                hideLoadingDialog();
                CustomToast.showToast(AirdropActivity.this, "access network failed");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AccountInfoBean accountInfoBean = null;
                try {
                    accountInfoBean = JSON.parseObject(response.body().string(), AccountInfoBean.class);
                } catch (Exception e) {
                    hideLoadingDialog();
                    CustomToast.showToast(AirdropActivity.this, "parse data failed");
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
                                    CustomToast.showToast(AirdropActivity.this,"transfer token success");
                                }else {
                                    hideLoadingDialog();
                                    CustomToast.showToast(AirdropActivity.this,resultTransferSignature);
                                }
                            } catch (RpcException e) {
                                hideLoadingDialog();
                                CustomToast.showToast(AirdropActivity.this,e.toString());
                            }
                        }else  //转账token-->输入地址是主地址-->当关联地址不存在
                        {
                            try {
                                //showLoadingDialog();
                                String resultSignature=SolanaTransferUtil.createAssociatedAccount(destinationAssociatedTokenAddress,destinationSolAddress,airdropTokenMint,signer);
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
                                        CustomToast.showToast(AirdropActivity.this,"transfer token success");
                                    }else
                                    {
                                        hideLoadingDialog();
                                        CustomToast.showToast(AirdropActivity.this,resultTransferSignature);
                                    }
                                }else
                                {
                                    hideLoadingDialog();
                                    CustomToast.showToast(AirdropActivity.this,resultSignature);
                                }
                            } catch (RpcException e) {
                                hideLoadingDialog();
                                CustomToast.showToast(AirdropActivity.this,e.toString());
                            }
                        }
                    }
                }

            }
        });
    }


}
