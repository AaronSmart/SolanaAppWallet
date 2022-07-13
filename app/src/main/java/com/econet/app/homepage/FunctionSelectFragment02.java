
package com.econet.app.homepage;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.econet.app.showQRCode.QrCreateActivity;
import com.econet.app.listviewsolana.SplTokenListSolScanActivity;
import com.econet.app.solwallet.R;
import com.econet.app.listviewsolana.SplTokenListActivity;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.SharedPreferencesUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author dai.jianhui
 */
public class FunctionSelectFragment02 extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.lyOlapay)
    LinearLayout lyOlapay;
    @BindView(R.id.lyQRAddress)
    LinearLayout lyQRAddress;
    @BindView(R.id.lySollet)
    LinearLayout lySollet;
    @BindView(R.id.lyPhantom)
    LinearLayout lyPhantom;
    @BindView(R.id.lyRaydium)
    LinearLayout lyRaydium;
    @BindView(R.id.lyBeach)
    LinearLayout lyBeach;
    @BindView(R.id.lyZklink)
    LinearLayout lyZklink;
    @BindView(R.id.lyStep)
    LinearLayout lyStep;
    @BindView(R.id.lySaber)
    LinearLayout lySaber;
    @BindView(R.id.lyScan)
    LinearLayout lyScan;
    @BindView(R.id.lyAicoin)
    LinearLayout lyAicoin;
    @BindView(R.id.tv_dqqw)
    MarqueTextView mTextViewDqqw;

    HashMap<String, Integer> g = CustomerMenu.getMenu();

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mod001_activity_home_page2, container, false);
        unbinder=ButterKnife.bind(this,rootView);
        mTextViewDqqw.setText("Solana,the advanced blockchain with scalability and Web-Scale. Thousands of senior engineers are trying so hard to build an ecosystem that matches decentralized、 permissionless、borderless、trustless、bankless、powerful、efficient and low-cost for people around the world. Solana is the God Chain which is a magic leap for DeFi mileStone.Token:ULA  total supply: 200 million, All earnings are used to buy back the tokens and make project move forward.The project continues to be optimized and upgraded for long-term.This project aim to serve the people all over the world,Thank you for your support!");
        initWidget();
        lyOlapay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
//                HashMap<String,String>params=new HashMap<>();
//                params.put("userType","Personal");
//                HashMap<String,String>headers=new HashMap<>();
//                headers.put("Accept","*/*");
//                headers.put("Content-Type","application/json");
//                String json = JSONObject.toJSONString(params);
//                HttpUtil.getDataFromServerUsePost(Constant.Solana.solMainNet,Constant.Solana.getTokenAccountsByOwner,headers,new Callback(){
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        //hideLoadingDialog();
//                        CustomToast.showToast(getActivity(),"access network failed");
//                    }
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                       String result= response.body().string();
//                       CustomToast.showToast(getActivity(),result);
//                    }
//                });
                SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(getActivity());
                String solAddress = shared.getValueString("app_solAddress","null");
                if(solAddress.equals("null"))
                {
                    CustomToast.showToast(getActivity(),"import the mnemonic words first");
                    return;
                }
                Intent intent = new Intent(getActivity(), SplTokenListActivity.class);
                startActivity(intent);
            }
        });
        lyQRAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(getActivity());
                String solAddress = shared.getValueString("app_solAddress","null");
                if(solAddress.equals("null"))
                {
                    CustomToast.showToast(getActivity(),"import the mnemonic words first");
                    return;
                }
                Intent intent = new Intent(getActivity(), QrCreateActivity.class);
                startActivity(intent);
            }
        });
        lySollet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
//                Uri uri = Uri.parse("https://www.sollet.io/");
//                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//                startActivityForResult(intent,111);
//                Intent intent = new Intent(getActivity(),WebViewActivity.class);
//                startActivity(intent);
                SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(getActivity());
                String solAddress = shared.getValueString("app_solAddress","null");
                if(solAddress.equals("null"))
                {
                    CustomToast.showToast(getActivity(),"import the mnemonic words first");
                    return;
                }
                Intent intent = new Intent(getActivity(), SplTokenListSolScanActivity.class);
                startActivity(intent);
            }
        });
        lyPhantom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Uri uri = Uri.parse("https://phantom.app/");

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,112);
            }
        });
        lyRaydium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Uri uri = Uri.parse("https://raydium.io/swap/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,113);
//                Intent intent = new Intent(getActivity(),WebViewActivity.class);
//                startActivity(intent);
            }
        });
        lyBeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(getActivity());
                String solAddress = shared.getValueString("app_solAddress","null");
                Uri uri = Uri.parse("https://solanabeach.io/address/"+solAddress);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,114);
            }
        });
        lyScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Uri uri = Uri.parse("https://solscan.io/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,117);
            }
        });
        lyZklink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Uri uri = Uri.parse("https://zk.link/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,115);
            }
        });
        lyStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Uri uri = Uri.parse("https://app.step.finance/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,116);
            }
        });
        lySaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Uri uri = Uri.parse("https://app.saber.so/#/");

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,118);
            }
        });
        lyAicoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Uri uri = Uri.parse("https://www.aicoin.cn/?long_lives_aicoin=%22live%22");

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,119);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    void initWidget() {

    }

    public static FunctionSelectFragment02 newInstance(int index) {
    	FunctionSelectFragment02 f = new FunctionSelectFragment02();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
}
