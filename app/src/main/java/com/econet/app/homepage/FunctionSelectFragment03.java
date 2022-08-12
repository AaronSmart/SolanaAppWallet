
package com.econet.app.homepage;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.econet.app.ServerBeans.myhealth.MyHealthBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.showQRCode.QrCreateActivity;
import com.econet.app.showQRCode.QuestionActivity;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.econet.app.uitl.ZeroWidthStringUtil;

import org.p2p.solanaj.utils.SolanaTransferUtil;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author dai.jianhui
 */
public class FunctionSelectFragment03 extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.lyLogout)
    LinearLayout lyLogout;
    @BindView(R.id.lyHealth)
    LinearLayout lyHealth;
    @BindView(R.id.lyPassword)
    LinearLayout lyPassword;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.lyDonate)
    LinearLayout lyDonate;
    @BindView(R.id.lyReadme)
    LinearLayout lyReadme;
    @BindView(R.id.lyWallet)
    LinearLayout lyWallet;
    @BindView(R.id.lyCopyToClipboard)
    LinearLayout lyCopyToClipboard;
    @BindView(R.id.lyClearClipboard)
    LinearLayout lyClearClipboard;
    @BindView(R.id.lyAirdrop)
    LinearLayout lyAirdrop;
    // this button is used to test some function
    @BindView(R.id.lyTest)
    LinearLayout lyTest;
    @BindView(R.id.lyDownload)
    LinearLayout lyDownload;


    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mod001_activity_home_page4, container, false);
        unbinder=ButterKnife.bind(this,rootView);
        initWidget();
        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    void initWidget() {
        SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(getActivity());
        String phone=shared.getValueString("app_phoneNumber","150******23");
        String firstName=shared.getValueString("app_name","Jack Ma");
        String lastName=shared.getValueString("app_lastName","");
        String hiddenPhone="";
        if(phone.length()<10)
        {
            hiddenPhone=phone;
        }else
        {
            hiddenPhone=phone.substring(0,3)+"******"+phone.substring(7,10);
        }
        tvPhone.setHint(hiddenPhone);
        userName.setText(firstName+" "+lastName);

        lyLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                getActivity().setResult(getActivity().RESULT_OK);
                getActivity().finish();
//                Intent i = new Intent(getActivity(), LoginActivity.class);
//                i.setAction(Intent.ACTION_MAIN);
//                i.addCategory(Intent.CATEGORY_LAUNCHER);
//                startActivityForResult(i,999);
            }
        });
        lyReadme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Intent i = new Intent(getActivity(), ReadmeActivity.class);
                startActivityForResult(i,999);
            }
        });
        lyDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Intent i = new Intent(getActivity(), DownLoadNewActivity.class);
                startActivity(i);
            }
        });
        lyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Intent i = new Intent(getActivity(), MnemonicWordsSettingActivity.class);
                startActivityForResult(i,999);
            }
        });
        lyAirdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Intent i = new Intent(getActivity(), AirdropActivity.class);
                startActivityForResult(i,999);
            }
        });
        lyTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                new Thread(networkTask).start();

            }
        });
        lyCopyToClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                SharedPreferencesUtils sharedOne=SharedPreferencesUtils.getInstance(getActivity());
                String mnemonic=sharedOne.getValueString("app_mnemonicWords","");
                mnemonic= ZeroWidthStringUtil.decodeString(mnemonic);
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("mnemonic", mnemonic);
                cm.setPrimaryClip(mClipData);
                CustomToast.showToast(getActivity(), "already copy the mnemonic words to clipboard");

            }
        });
        lyClearClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("mnemonic", "");
                cm.setPrimaryClip(mClipData);
                CustomToast.showToast(getActivity(), "already clear the mnemonic words in clipboard");
            }
        });

        lyDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("solana address", "4z8D2vPNuvt8gK22T8mvnpEPhSmiTpn9HZBF4Jw9YUq6");
                cm.setPrimaryClip(mClipData);
                CustomToast.showToast(getActivity(), "already copy the donate sol address to clipboard");

            }
        });
        lyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ResetPasswordActivity.class);
                startActivity(i);

            }
        });
        lyHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils sharedOne=SharedPreferencesUtils.getInstance(getActivity());
                String flag=sharedOne.getValueString(Constant.User.IS_FIRST_FILL_QUESTIONNAIRE,"true");
                String healthStatus=shared.getValueString(Constant.Auth.HEALTH_STATUS,"");
               // if("".equals(healthStatus))
                if(false)
                {
                    Intent i = new Intent(getActivity(), QuestionActivity.class);
                    startActivity(i);
                }
                else
                {
                    HashMap<String,String> params=getData();
                    HashMap<String,String>headers=new HashMap<>();
                    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(getActivity());
                    headers.put(Constant.Auth.USER_TOKEN,shared.getValueString(Constant.Auth.USER_TOKEN,"no_user_token"));
                    headers.put(Constant.Auth.USER_ID,shared.getValueString(Constant.Auth.USER_ID,"no_user_id"));
                    //showLoadingDialog();
                    HttpUtil.getDataFromServerUseGet(Constant.Url.USER_INFO_DETAIL,params,headers,new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // hideLoadingDialog();
                            CustomToast.showToast(getActivity(), "access network failed");
                            Intent i = new Intent(getActivity(), QrCreateActivity.class);
                            SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(getActivity());
                            String healthStatus=shared.getValueString(Constant.Auth.HEALTH_STATUS,"Green");
                            String healthStatusTime=shared.getValueString(Constant.Auth.HEALTH_STATUS_TIME,"2020-12-12");
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
                            i.putExtra("mark", 4);
                            i.putExtra("healthStatus",healthStatus);
                            i.putExtra("healthStatusTime",healthStatusTime);
                            startActivity(i);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //hideLoadingDialog();
                            MyHealthBean myHealthBean = null;
                            try {
                                myHealthBean = JSON.parseObject(response.body().string(), MyHealthBean.class);
                            } catch (Exception e) {
                                CustomToast.showToast(getActivity(), "parse data failed");
                            }
                            if(myHealthBean!=null)
                            {
                                if(myHealthBean.getResultcode()==200)
                                {
                                    if(myHealthBean.getResult()!=null)
                                    {
                                        String healthStatus=myHealthBean.getResult().getHealthstatus();
                                        String healthStatusTime=myHealthBean.getResult().getHealthstatustime();
                                        Intent i = new Intent(getActivity(), QrCreateActivity.class);
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
                                        startActivity(i);
                                    }
                                }else
                                {
                                    CustomToast.showToast(getActivity(), myHealthBean.getResultmsg());
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public static FunctionSelectFragment03 newInstance(int index) {
    	FunctionSelectFragment03 f = new FunctionSelectFragment03();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    private HashMap<String,String>getData()
    {
        HashMap<String,String>params=new HashMap<>();
        //SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(getActivity());
        //params.put("idNo",shared.getValueString(Constant.User.LOGIN_ID,""));
        return params;
    }

    @Override
      public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == getActivity().RESULT_OK) {
                getActivity().setResult(getActivity().RESULT_OK);
                getActivity().finish();
            }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            CustomToast.showToast(getActivity(),"copycat"+ val);

        }
    };

    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(getActivity());
            String signer=shared.getValueString("app_signer","");
            final String[] resultSignature = {""};
            try
            {
                //resultSignature[0] = SolanaTransferUtil.createMemo("hello nice",signer);
                resultSignature[0] = SolanaTransferUtil.createAnchor("hello nice",signer);
            }catch (Exception e)
            {
                resultSignature[0]=e.toString();
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", resultSignature[0]);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

}
