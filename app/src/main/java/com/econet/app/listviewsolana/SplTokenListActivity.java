package com.econet.app.listviewsolana;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.econet.app.ServerBeans.historylist.Recordlist;
import com.econet.app.dictionary.Constant;
import com.econet.app.homepage.TransferSplTokenActivity;
import com.econet.app.solwallet.R;
import com.econet.app.homepage.AddSplTokenActivity;
import com.econet.app.homepage.BaseActivity;
import com.econet.app.solanabean.SplToken;
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
public class SplTokenListActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lvLoadMore)
    SwipeRefreshListView lvLoadMore;
    private SplTokenListAdapter mAdapter;
    private int total;
    private String start;
    private List<Recordlist> mQuestions;
    private final Object lock = new Object();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list_history);
        ButterKnife.bind(this);
         // android.os.networkOnMainThread
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
        setRightText("add", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SplTokenListActivity.this, AddSplTokenActivity.class);
                startActivityForResult(i,777);
            }
        });
        initWidget();
        initData();
    }
    private void initWidget()
    {
        tvTitle.setText("Solana SPL Token");
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
                Intent intent = new Intent(SplTokenListActivity.this, TransferSplTokenActivity.class);
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
                CustomToast.showToast(SplTokenListActivity.this,"MintCode:"+recordlist.getRecordstatus());
                return false;
            }
        });
        lvLoadMore.setOnHeadRefreshListener(new SwipeRefreshListView.OnHeadRefreshListener()
        {
            @Override
            public void onHeadRefresh()
            {

                SplTokenListActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        showLoadingDialog();
                    }
                });
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        ArrayList<String> splTokenList= ParseSplTokenUtil.getDefaultSplTokenInfo();
                        int size=splTokenList.size();
                        for(int i=0;i<size;i++)
                        {
                            searchQuestionHistoryList(splTokenList.get(i),i+"");
                            synchronized (lock) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        SplTokenListActivity.this.runOnUiThread(new Runnable() {
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

private void searchQuestionHistoryList(String mint,String startInner )
{
    HashMap<String,String>params=new HashMap<>();
    params.put("userType","Personal");
    HashMap<String,String>headers=new HashMap<>();
    headers.put("Accept","*/*");
    headers.put("Content-Type","application/json");
    String json = JSONObject.toJSONString(params);
    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(SplTokenListActivity.this);
    String solAddress=shared.getValueString("app_solAddress","");
    String tokenUrl=ParseSplTokenUtil.getTokenInfoUrl(solAddress,mint);
    HttpUtil.getDataFromServerUsePost(Constant.Solana.solMainNet,tokenUrl,headers,new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

            SplTokenListActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    hideLoadingDialog();
                    lvLoadMore.onCompleteAll();
                }
            });
            synchronized (lock) {
                lock.notifyAll();
            }
            CustomToast.showToast(SplTokenListActivity.this, "loading failed");
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            SplToken splToken;
            if(ParseSplTokenUtil.Solana.SOL.equals(mint))
            {
                splToken=ParseSplTokenUtil.parseJsonForSol(response.body().string());
            }else
            {
                splToken=ParseSplTokenUtil.parseJson(response.body().string());
            }
            //判断是否是空的
            if(splToken.getTokenName().equals("***"))
            {
                splToken.setTokenName(ParseSplTokenUtil.getTokenName(mint));
                splToken.setTokenAmount("0");
                splToken.setTokenMint(mint);
            }
            List<Recordlist> listRecord =new ArrayList<Recordlist>();
            Recordlist record=new Recordlist();
            record.setRecordTitle(splToken.getTokenName());
            record.setRecorddate(splToken.getTokenAmount());
            record.setRecordstatus(splToken.getTokenMint());
            listRecord.add(record);
            total=6;//dead write here
            final List<Recordlist> listRecord2 =listRecord;
            //解锁
            synchronized (lock) {
                lock.notifyAll();
            }
            SplTokenListActivity.this.runOnUiThread(new Runnable() {
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
                            //according to requirement,change the id for show
                            //mQuestions.addAll(QuestionListAdapterTransUtil.transRecordWithOrder(list,mQuestions.size()));
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

}
