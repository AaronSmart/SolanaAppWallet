package com.econet.app.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.econet.app.listviewsolana.PopularTokenSearchActivity;
import com.econet.app.listviewsolana.SplTokenListActivity;
import com.econet.app.solwallet.R;
import com.econet.app.listviewsolana.ParseSplTokenUtil;
import com.econet.app.uitl.CustomToast;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author dai.jianhui
 */
public class AddSplTokenActivity extends BaseActivity {
    
    @BindView(R.id.lyReset)
    LinearLayout lyReset;
    @BindView(R.id.etTokenName)
    EditText etTokenName;
    @BindView(R.id.etTokenMint)
    EditText etTokenMint;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivBackLinear)
    LinearLayout ivBackLinear;

    @BindView(R.id.ivTokenSearch)
    ImageView ivTokenSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_spl_token);
        ButterKnife.bind(this);
        setTitle("Add Token");
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
        ivTokenSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AddSplTokenActivity.this, PopularTokenSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tokenName",etTokenName.getText().toString().trim());
                intent.putExtras(bundle);
                startActivityForResult(intent,1000);
            }
        });
        ivBackLinear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        lyReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkData())
                {
                    String tokenName=etTokenName.getText().toString().trim();
                    String tokenMint=etTokenMint.getText().toString().trim();
                    ParseSplTokenUtil.putToken(tokenMint,tokenName);
                    ParseSplTokenUtil.addSpltoken(tokenMint);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
    private boolean checkData()
    {
        if(TextUtils.isEmpty(etTokenName.getText()))
        {
            CustomToast.showToast(AddSplTokenActivity.this,"please enter the token name");
            return false;
        }
        if(TextUtils.isEmpty(etTokenMint.getText()))
        {
            CustomToast.showToast(AddSplTokenActivity.this,"please enter the token mint");
            return false;
        }
        if(etTokenMint.getText().toString().trim().length()!=44)
        {
            CustomToast.showToast(AddSplTokenActivity.this,"The token mint length is not equal to 44");
            return false;
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                String symbol = data.getStringExtra("tokenSymbol");
                String mint = data.getStringExtra("tokenMint");
                etTokenMint.setText(mint);
                etTokenName.setText(symbol);
            }
        }
    }
}
