package com.econet.app.listView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.econet.app.ServerBeans.historydetail.HistoryDetailBean;
import com.econet.app.ServerBeans.historylist.HistoryListBean;
import com.econet.app.ServerBeans.historylist.Recordlist;
import com.econet.app.beans.QuestionFormBean;
import com.econet.app.dictionary.Constant;
import com.econet.app.showQRCode.QuestionActivityHistory;
import com.econet.app.solwallet.R;
import com.econet.app.homepage.BaseActivity;
import com.econet.app.trans.DataAdapter;
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
public class QuestionListHistoryActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lvLoadMore)
    SwipeRefreshListView lvLoadMore;
    private QuestionListAdapter mAdapter;
    private int total;
    private String start;
    private List<Recordlist> mQuestions;


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
        initWidget();
        initData();
    }
    private void initWidget()
    {
        tvTitle.setText("Report Detail");
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
        mAdapter = new QuestionListAdapter(this, mQuestions);
        //set the adapter
        lvLoadMore.setAdapter(mAdapter);
        //set the divider
        lvLoadMore.getListView().setDivider(null);
        lvLoadMore.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Recordlist recordlist=mQuestions.get(position);
                String recordId = recordlist.getRecordid().split(";")[0];
                HashMap<String,String>params=new HashMap<>();
                params.put("recordId",recordId);
                HashMap<String,String>headers=new HashMap<>();
                SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(QuestionListHistoryActivity.this);
                headers.put(Constant.Auth.USER_TOKEN,shared.getValueString(Constant.Auth.USER_TOKEN,"no_user_token"));
                headers.put(Constant.Auth.USER_ID,shared.getValueString(Constant.Auth.USER_ID,"no_user_id"));
                showLoadingDialog();
                HttpUtil.getDataFromServerUseGet(Constant.Url.QUERY_QUESTION_DETAIL,params,headers,new Callback(){
                    @Override
                    public void onFailure(Call call, IOException e) {
                        hideLoadingDialog();
                        CustomToast.showToast(QuestionListHistoryActivity.this,"access network failed");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        hideLoadingDialog();
                        HistoryDetailBean historyDetailBean=null;
                        try{
                            historyDetailBean = JSON.parseObject(response.body().string(), HistoryDetailBean.class);
                        }catch (Exception e)
                        {
                            CustomToast.showToast(QuestionListHistoryActivity.this,"parse data failed");
                        }
                        if(historyDetailBean!=null)
                        {
                            if(historyDetailBean.getResultcode()==200)
                            {
                                QuestionFormBean form= DataAdapter.serverToLocalHistoryDetail(historyDetailBean);
                                Intent intent = new Intent(getApplicationContext(), QuestionActivityHistory.class);
                                //QuestionFormBean form = MockData.getMockQuestionFormBean();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("form", form);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }else {
                                CustomToast.showToast(QuestionListHistoryActivity.this,historyDetailBean.getResultmsg());
                            }

                        }
                    }
                });
            }
        });
        lvLoadMore.setOnHeadRefreshListener(new SwipeRefreshListView.OnHeadRefreshListener()
        {
            @Override
            public void onHeadRefresh()
            {
                start = "0";
                searchQuestionHistoryList();
            }
        });
        lvLoadMore.setOnFooterRefreshListener(new SwipeRefreshListView.OnFooterRefreshListener()
        {
            @Override
            public void onFooterRefresh()
            {
                if (mQuestions.size() < total)
                {
                    start = mQuestions.size() + "";
                    searchQuestionHistoryList();
                }
            }
        });
       lvLoadMore.onHeadRefreshing();
    }

private void searchQuestionHistoryList()
{
    HashMap<String,String> params=getData();
    HashMap<String,String>headers=new HashMap<>();
    SharedPreferencesUtils shared =SharedPreferencesUtils.getInstance(QuestionListHistoryActivity.this);
    headers.put(Constant.Auth.USER_TOKEN,shared.getValueString(Constant.Auth.USER_TOKEN,"no_user_token"));
    headers.put(Constant.Auth.USER_ID,shared.getValueString(Constant.Auth.USER_ID,"no_user_id"));
    showLoadingDialog();
    HttpUtil.getDataFromServerUseGet(Constant.Url.HISTORY_QUESTION_LIST_QUERY,params,headers,new Callback(){

        @Override
        public void onFailure(Call call, IOException e) {
            hideLoadingDialog();
            lvLoadMore.onCompleteAll();
            CustomToast.showToast(QuestionListHistoryActivity.this, "loading failed");
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            hideLoadingDialog();
            List<Recordlist> listRecord =null;
            HistoryListBean historyListBean=null;
            try {
                historyListBean=JSON.parseObject(response.body().string(), HistoryListBean.class);//don't put this on runOnUiThread
            }catch (Exception e)
            {
                CustomToast.showToast(QuestionListHistoryActivity.this,e.toString());
            }
            if(historyListBean!=null)
            {
                if(historyListBean.getResultcode()==200)
                {
                    listRecord=historyListBean.getResult().getRecordlist();
                    total=Integer.parseInt(historyListBean.getResult().getPage().getTotalsize());
                }else
                {
                    CustomToast.showToast(QuestionListHistoryActivity.this,historyListBean.getResultmsg());
                }
            }
            final List<Recordlist> listRecord2 =listRecord;
            QuestionListHistoryActivity.this.runOnUiThread(new Runnable() {
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
                            if (Integer.valueOf(start) == 0)
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

    private HashMap<String,String> getData()
    {
        HashMap<String,String> params=new HashMap<>();
        String currentPage =(Integer.parseInt(start)+1)/10+"";
        params.put("currentPage",currentPage);
        params.put("pageSize","10");
        return params;
    }

}
