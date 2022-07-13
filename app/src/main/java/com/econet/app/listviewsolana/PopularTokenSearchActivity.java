package com.econet.app.listviewsolana;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.econet.app.homepage.AddSplTokenActivity;
import com.econet.app.homepage.BaseActivity;
import com.econet.app.solanabean.mintTokenList.Tokens;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CopyCatLog;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.PressMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author popular token search
 */
public class PopularTokenSearchActivity extends BaseActivity {


    @BindView(R.id.listView)
    PressMoreListView listView;

    @BindView(R.id.tvSearch)
    TextView tvSearch;

    @BindView(R.id.etSearchKey)
    EditText etSearchKey;

    @BindView(R.id.lyNoData)
    LinearLayout lyNoData;

    @BindView(R.id.lyContent)
    LinearLayout lyContent;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    private PopularTokenSearchAdapter mTokenPopularAdapter = null;

    //数据源
    List<Tokens> mList = new ArrayList<Tokens>();

    private int mPageSize = 20;
    private int mCount = 0;

    //the flag is used to diverse the source from whether is from AddSplTokenActivity
    String fromAddTokenFlag="0";


    //传进来的模糊参数，暂时没用
    String condition="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_token_search);
        ButterKnife.bind(PopularTokenSearchActivity.this);
        condition = (String) getIntent().getSerializableExtra("mint");
        fromAddTokenFlag = (String) getIntent().getSerializableExtra("fromAddToken");
        setTitle("Popular Tokens");
        initWidget();
    }
    private void initWidget()
    {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTokenPopularAdapter = new PopularTokenSearchAdapter(PopularTokenSearchActivity.this, mList);
        listView.setAdapter(mTokenPopularAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Tokens token = mList.get(position);
                if("1".equals(fromAddTokenFlag)) {
                  Intent intent = new Intent(PopularTokenSearchActivity.this, AddSplTokenActivity.class);
                  Bundle bundle = new Bundle();
                  bundle.putSerializable("tokenSymbol",token.getSymbol().trim());
                  bundle.putSerializable("tokenMint",token.getAddress().trim());
                  intent.putExtras(bundle);
                  setResult(RESULT_OK,intent);
                  finish();
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });
        etSearchKey.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                searchPopularToken();
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }
            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
        listView.setOnRefreshLoadingMoreListener(new PressMoreListView.OnRefreshLoadingMoreListener() {
            @Override
            public void onLoadMore() {
              //  loadData();
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etSearchKey.getText().toString().trim())) {
                    searchPopularToken();
                } else {
                    CustomToast.showToast(PopularTokenSearchActivity.this, "please input key words");
                }
            }
        });
        searchPopularToken();

    }
    public void searchPopularToken() {
        mCount = 0;
        mList.clear();
        loadData();
    }
    public void loadData() {
        showLoadingDialog();
        if (mList.size() == 0) {
            mCount = 1000;
        }
        mList.addAll(TokenMintUtil.searchTokens(etSearchKey.getText().toString().trim()));
        if (mList.size() == 0) {
            lyNoData.setVisibility(View.VISIBLE);
        } else {
            lyNoData.setVisibility(View.GONE);
        }
        listView.removeFooterView();
//        if (mList.size() >= mCount) {
//            listView.removeFooterView();
//        } else {
//            listView.addFooterView();
//        }
        hideLoadingDialog();
        mTokenPopularAdapter.notifyDataSetChanged();
    }

    public void onLoadMore() {
        loadData();
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
    }
    @Override
    public void onBackPressed() {
        setResult(999);
        super.onBackPressed();
    }
}
