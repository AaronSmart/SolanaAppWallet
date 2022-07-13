package com.econet.app.listviewsolana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.econet.app.ServerBeans.historylist.Recordlist;
import com.econet.app.dictionary.Constant;
import com.econet.app.homepage.BaseActivity;
import com.econet.app.homepage.TransferSplTokenActivity;
import com.econet.app.solanabean.SplToken;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.HttpUtil;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.econet.app.uitl.SwipeRefreshListView;

import java.io.IOException;
import java.util.ArrayList;
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
public class SplTokenListSolScanActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lvLoadMore)
    SwipeRefreshListView lvLoadMore;
    @BindView(R.id.lySOLAmount)
    LinearLayout lySOLAmount;
    @BindView(R.id.tvSolAmount)
    TextView tvSolAmount;
    private SplTokenListAdapter mAdapter;
    private int total;
    private String start;
    private List<Recordlist> mQuestions;
    private final Object lock = new Object();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_list_solscan);
        ButterKnife.bind(this);
         // android.os.networkOnMainThread
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
        initWidget();
        initData();
    }
    private void initWidget()
    {
        tvTitle.setText("Solana SPL Token");
        //the sol amount
        lySOLAmount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Intent intent = new Intent(SplTokenListSolScanActivity.this, TransferSplTokenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mint","");
                bundle.putSerializable("isTransferSol",true);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //request for the sol amount
        searchSolAmount();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initData()
    {
        mQuestions = new ArrayList<>();
        //init the adapter
        mAdapter = new SplTokenListAdapter(this, mQuestions);
        //set the adapter
        lvLoadMore.setAdapter(mAdapter);
        //set the divider
        lvLoadMore.getListView().setDivider(null);
        lvLoadMore.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                Recordlist recordlist=mQuestions.get(position);
                String mint =recordlist.getRecordstatus();
                String splToken= recordlist.getRecordTitle();
                Intent intent = new Intent(SplTokenListSolScanActivity.this, TransferSplTokenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mint", mint);
                bundle.putSerializable("splToken", splToken);
                if(ParseSplTokenUtil.Solana.SOL_MINT.equals(mint))//transfer sol
                {
                    bundle.putSerializable("isTransferSol",true);
                }else {
                    bundle.putSerializable("isTransferSol",false);//transfer token
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lvLoadMore.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Recordlist recordlist=mQuestions.get(position);
                CustomToast.showToast(SplTokenListSolScanActivity.this,"MintCode:"+recordlist.getRecordstatus());
                return false;
            }
        });
        lvLoadMore.setOnHeadRefreshListener(new SwipeRefreshListView.OnHeadRefreshListener()
        {
            @Override
            public void onHeadRefresh()
            {

                SplTokenListSolScanActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        showLoadingDialog();
                    }
                });
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        searchQuestionHistoryList("0");
                        synchronized (lock) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        SplTokenListSolScanActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                hideLoadingDialog();
                            }
                        });
                        Looper.loop();

                    }
                });
                thread1.start();

            }
        });
        lvLoadMore.setOnFooterRefreshListener(new SwipeRefreshListView.OnFooterRefreshListener()
        {
            @Override
            public void onFooterRefresh()
            {
//                if (mQuestions.size() < total)
//                {
//                    start = mQuestions.size() + "";
//                    searchQuestionHistoryList("a",start);
//                }
            }
        });
       lvLoadMore.onHeadRefreshing();
    }

private void searchQuestionHistoryList(String startInner )
{
    HashMap<String,String>headers=new HashMap<>();
    headers.put("Accept","application/json, text/plain, */*");
    headers.put("Sec-Fetch-Mode","cors");
    headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36 Core/1.77.87.400 QQBrowser/10.9.4613.400");
    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(SplTokenListSolScanActivity.this);
    String solAddress=shared.getValueString("app_solAddress","");
    HashMap<String,String>params=new HashMap<>();
    params.put("address",solAddress);
    params.put("price","1");//i dont know what is used for
    HttpUtil.getDataFromServerUseGet(Constant.Solana.solscanNet,params,headers,new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

            SplTokenListSolScanActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    hideLoadingDialog();
                    lvLoadMore.onCompleteAll();
                }
            });
            CustomToast.showToast(SplTokenListSolScanActivity.this, "loading failed");
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            List<SplToken> splTokenList=ParseSplTokenUtil.parseTokensFromSolscanJson(response.body().string());
            List<Recordlist> listRecord =new ArrayList<Recordlist>();
            for(int i=0;i<splTokenList.size();i++)
            {
                SplToken splToken=splTokenList.get(i);
                Recordlist record=new Recordlist();
                record.setRecordTitle(splToken.getTokenName());
                record.setRecorddate(splToken.getTokenAmount());
                record.setRecordstatus(splToken.getTokenMint());
                listRecord.add(record);
            }
            total=listRecord.size();
            final List<Recordlist> listRecord2 =listRecord;
            //解锁
            synchronized (lock) {
                lock.notifyAll();
            }
            SplTokenListSolScanActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        List<Recordlist> list=new ArrayList<>();
                        for(int i=0;i<listRecord2.size();i++)
                        {
                            Recordlist recordlist=listRecord2.get(i);
                            list.add(recordlist);
                        }
                        if (list != null)
                        {
                            if (Integer.valueOf(startInner) == 0)
                            {
                                mQuestions.clear();
                            }
                            mQuestions.addAll(list);
                            if (mQuestions.size() < total)
                            {
                                lvLoadMore.getListView().addFooterView();
                            }
                            else
                            {
                                lvLoadMore.getListView().removeFooterView();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    } catch (Exception ee)
                    {
                        ee.printStackTrace();
                    }
                    lvLoadMore.onCompleteAll();
                }
            });

        }
    });
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777) {
            if (resultCode == RESULT_OK) {
                //重新刷新token列表
                lvLoadMore.onHeadRefreshing();
            }
        }
    }

    private HashMap<String,String> getData()
    {
        HashMap<String,String> params=new HashMap<>();
        String currentPage =(Integer.parseInt(start)+1)/10+"";
        params.put("currentPage",currentPage);
        params.put("pageSize","10");
        return params;
    }

    /**
     * get the sol main account balance
     */
    private void searchSolAmount()
    {
        HashMap<String,String>params=new HashMap<>();
        params.put("userType","Personal");
        HashMap<String,String>headers=new HashMap<>();
        headers.put("Accept","*/*");
        headers.put("Content-Type","application/json");
        String json = JSONObject.toJSONString(params);
        SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(SplTokenListSolScanActivity.this);
        String solAddress=shared.getValueString("app_solAddress","");
        String tokenUrl=ParseSplTokenUtil.getTokenInfoUrl(solAddress, ParseSplTokenUtil.Solana.SOL);
        HttpUtil.getDataFromServerUsePost(Constant.Solana.solMainNet,tokenUrl,headers,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SplTokenListSolScanActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        hideLoadingDialog();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                SplToken splToken = ParseSplTokenUtil.parseJsonForSol(response.body().string());
                SplTokenListSolScanActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        tvSolAmount.setText("SOL Balance: "+splToken.getTokenAmount()+" SOL");
                    }
                });
            }
        });
    }
}
