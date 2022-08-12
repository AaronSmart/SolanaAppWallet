package com.econet.app.listView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.econet.app.dictionary.Constant;
import com.econet.app.solwallet.R;
import com.econet.app.homepage.BaseActivity;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.SwipeRefreshListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dai.jianhui
 */
public class CityListActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.lvLoadMore)
    SwipeRefreshListView lvLoadMore;
    private CityListAdapter mAdapter;
    private List<String> mCtities;
    private String area="";
    private String areaUpload="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_city);
        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initWidget();
        initData();

    }
    private void initWidget()
    {
        tvTitle.setText("city select");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("city", area);
                intent.putExtra("cityUpload", areaUpload);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void initData()
    {
        mCtities = Constant.City.getCity();
        //init the adapter
        mAdapter = new CityListAdapter(this, mCtities);
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
                Intent i = new Intent(getApplicationContext(), AreaListActivity.class);
                i.putExtra("city",mCtities.get(position) );
                startActivityForResult(i,99);
                
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
             area= (String) data.getSerializableExtra("area");
             areaUpload= (String) data.getSerializableExtra("areaUpload");
            Intent intent = new Intent();
            intent.putExtra("city", area);
            intent.putExtra("cityUpload", areaUpload);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("city", area);
        intent.putExtra("cityUpload", areaUpload);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
