package com.econet.app.homepage;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CheckClick;
import com.econet.app.uitl.CustomToast;
import com.econet.app.uitl.SharedPreferencesUtils;
import com.econet.app.uitl.ZeroWidthStringUtil;
import com.kikenn.ed25519.GenSolAddress;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * @author dai.jianhui
 */
public class MnemonicWordsSettingActivity extends BaseActivity {

    @BindView(R.id.lyReset)
    LinearLayout lyReset;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;

    @BindView(R.id.etMnemonic)
    EditText etMnemonic;

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.ivBackLinear)
    LinearLayout ivBackLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnemonic_setting);
        ButterKnife.bind(this);
        initWidget();
    }
    private void initWidget()
    {
        SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(MnemonicWordsSettingActivity.this);
        String name= shared.getValueString("app_name","Sam Bankman-Fried");
        String phone=shared.getValueString("app_phoneNumber","15166668888");
        etName.setText(name);
        etPhoneNumber.setText(phone);
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
        lyReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckClick.isClickEvent())
                {
                    return;
                }
                if(checkData())
                {
                    SharedPreferencesUtils shared=SharedPreferencesUtils.getInstance(MnemonicWordsSettingActivity.this);
                    shared.submitString("app_name",etName.getText().toString());
                    shared.submitString("app_phoneNumber",etPhoneNumber.getText().toString());
                    shared.submitString("app_mnemonicWords", ZeroWidthStringUtil.encodeString("This is super fun Are you try to hack",etMnemonic.getText().toString().trim()));
                    shared.submitString("app_solAddress",mnemonicToAddress(etMnemonic.getText().toString()));
                    shared.submitString("app_signer",mnemonicToSingerBase58Str(etMnemonic.getText().toString()));
                    CustomToast.showToast(MnemonicWordsSettingActivity.this,"set success");
                    finish();
                }

            }
        });
    }
    private boolean checkData()
    {
        if(TextUtils.isEmpty(etName.getText()))
        {
            CustomToast.showToast(MnemonicWordsSettingActivity.this,"please enter your name");
            return false;
        }
        if(TextUtils.isEmpty(etPhoneNumber.getText()))
        {
            CustomToast.showToast(MnemonicWordsSettingActivity.this,"please enter the phone number");
            return false;
        }
        if(TextUtils.isEmpty(etMnemonic.getText()))
        {
            CustomToast.showToast(MnemonicWordsSettingActivity.this,"please enter the mnemonic words");
            return false;
        }
        String mnemonicWords=etMnemonic.getText().toString().trim();
        String[] words=mnemonicWords.split(" ");
        if(words.length==12||words.length==24)
        {
            return true;
        }else
        {
            CustomToast.showToast(MnemonicWordsSettingActivity.this,"please enter the correct mnemonic words always 12 or 24 words");
            return false;
        }
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


}
