package com.econet.app.listView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.econet.app.dictionary.Constant;
import com.econet.app.solwallet.R;
import com.econet.app.homepage.BaseActivity;
import com.econet.app.uitl.SwipeRefreshListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dai.jianhui
 */
public class AreaListActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lvLoadMore)
    SwipeRefreshListView lvLoadMore;
    private AreaListAdapter mAdapter;
    private List<String> mCtities;
    String city="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city);
        ButterKnife.bind(this);
        city = getIntent().getStringExtra("city");
        initWidget();
        initData();

    }
    private void initWidget()
    {
        tvTitle.setText("area select");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initData()
    {
        mCtities = Constant.City.getArea(city);
        //init the adapter
        mAdapter = new AreaListAdapter(this, mCtities);
        //set the adapter
        lvLoadMore.setAdapter(mAdapter);
        //set the divider
        lvLoadMore.getListView().setDivider(null);
        lvLoadMore.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String area = city+"  "+mCtities.get(position);
                String areaUpload=city+"||"+mCtities.get(position);
                Intent intent = new Intent();
                intent.putExtra("area", area);
                intent.putExtra("areaUpload",areaUpload);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        lvLoadMore.setOnHeadRefreshListener(new SwipeRefreshListView.OnHeadRefreshListener()
        {
            @Override
            public void onHeadRefresh()
            {
            }
        });
        lvLoadMore.setOnFooterRefreshListener(new SwipeRefreshListView.OnFooterRefreshListener()
        {
            @Override
            public void onFooterRefresh()
            {
            }
        });
       //lvLoadMore.onHeadRefreshing();
    }
}
