package com.econet.app.homepage;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.TextUtils;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.econet.app.solwallet.R;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author dai.jianhui
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    private  Dialog progressDialog;


    public void showLoadingDialog(String msgStr) {
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.dialog_common);
            progressDialog.setCancelable(false);//点击空白不消失
            progressDialog.setContentView(R.layout.dialog_loading_progress);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView msg = (TextView) progressDialog.findViewById(R.id.tvMessage);
            msg.setText("Loading...");
        }
        if (!TextUtils.isEmpty(msgStr)) {
            TextView msg = (TextView) progressDialog.findViewById(R.id.tvMessage);
            msg.setText(msgStr);
        } else {
            TextView msg = (TextView) progressDialog.findViewById(R.id.tvMessage);
            msg.setText("Loading...");
        }
        progressDialog.show();
    }
    public void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.dialog_common);
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.dialog_loading_progress);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView msg = (TextView) progressDialog.findViewById(R.id.tvMessage);
            msg.setText("Loading...");
        }
            TextView msg = (TextView) progressDialog.findViewById(R.id.tvMessage);
            msg.setText("Loading...");
        progressDialog.show();
    }
    public void hideLoadingDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    protected void showDate(TextView tv, String title, Context context)
    {
        if(title==null)
        {
            title = "";
        }
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .setContentTextSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText(title)//标题文字
                //.setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setLabel("","","","H","m","s")
                //.isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }
    public void setTitle(String titleText) {
        TextView title =  findViewById(R.id.tvTitle);
        if (title != null) {
            title.setText(titleText);
        }
    }
    public ImageView showImageRightButton(int drawable, View.OnClickListener listener) {
        findViewById(R.id.llRight).setVisibility(View.VISIBLE);
        findViewById(R.id.btnRight).setVisibility(View.GONE);
        findViewById(R.id.tvRight).setVisibility(View.GONE);
        ImageView imageView = (ImageView) findViewById(R.id.ivRight);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(drawable);
        if (listener != null) {
            findViewById(R.id.llRight).setOnClickListener(listener);
        }
        return imageView;
    }

    public Button showRightButton(int drawable, String str, View.OnClickListener listener) {
        findViewById(R.id.llRight).setVisibility(View.VISIBLE);
        findViewById(R.id.ivRight).setVisibility(View.GONE);
        findViewById(R.id.tvRight).setVisibility(View.GONE);
        Button btnRight = (Button) findViewById(R.id.btnRight);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText(str);
        btnRight.setBackgroundResource(drawable);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setOnClickListener(listener);
        return btnRight;
    }
    public void setRightText(String str, View.OnClickListener listener) {
        findViewById(R.id.llRight).setVisibility(View.VISIBLE);
        findViewById(R.id.ivRight).setVisibility(View.GONE);
        findViewById(R.id.btnRight).setVisibility(View.GONE);
        TextView tvRight = (TextView) findViewById(R.id.tvRight);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(str);
        tvRight.setOnClickListener(listener);
    }

    public AssetManager getAssetManager()
    {
        AssetManager assetManager = getAssets();
        return assetManager;
    }

}
