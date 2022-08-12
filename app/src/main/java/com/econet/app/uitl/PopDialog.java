
package com.econet.app.uitl;

import com.econet.app.solwallet.R;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class PopDialog {

    /**
     * 标题
     */
    private TextView tvTitle;
    /**
     * 内容
     */
    private TextView tvContent;
    /**
     * 确定 取消
     */
    private TextView tvSure, tvCancel;
    /**
     * 监听
     */
    private XClickListener iclickListener;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 对话框
     */
    private Dialog dialog;

    public PopDialog(Context context, String content, String strCertain, String strCancel, String titleString,
            XClickListener iclickListener) {
        this.context = context;
        this.iclickListener = iclickListener;
        init(context, content, strCertain, strCancel, titleString);
    }

    public PopDialog(Context context, String content, String strCertain, String titleString,
            XClickListener iclickListener) {
        this.context = context;
        this.iclickListener = iclickListener;
        init(context, content, strCertain, null, titleString);
    }

    public PopDialog(Context context, XClickListener iclickListener) {
        this.context = context;
        this.iclickListener = iclickListener;
        init(context);
    }

    /**
     * 
     * 显示
     
     *                 2016年10月10日 Json Lai CREATE
     * @author Json Lai
     *
     */
    public void show() {
        dialog.show();
    }

    public Dialog getDialog() {
        return dialog;
    }

    /**
     * 
     * TODO 版本更新的时候
     
     * @author Wade Huang
     * 
     */
    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_pop_version_dialog, null);
        tvContent = (TextView) contentView.findViewById(R.id.tvContent);
        tvSure = (TextView) contentView.findViewById(R.id.tvSure);
        tvCancel = (TextView) contentView.findViewById(R.id.tvCancel);
        //        tvContent.setText(content);
        //        tvSure.setText(strCertain);
        //        if (strCancel == null || strCancel.length() == 0) {
        //            tvCancel.setVisibility(View.GONE);
        //        } else {
        //            tvCancel.setVisibility(View.VISIBLE);
        //            tvCancel.setText(strCancel);
        //        }
        tvCancel.setOnClickListener(clickListener);
        tvSure.setOnClickListener(clickListener);
        dialog = new Dialog(context, R.style.dialog_common);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(contentView);
    }

    /**
     * 
     * 初始化
     
     *                 2016年10月10日 Json Lai CREATE
     * @author Json Lai
     * 
     * @param context
     * @param content
     * @param strCertain
     * @param strCancel
     * @param titleString
     */
    private void init(Context context, String content, String strCertain, String strCancel, String titleString) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_pop_dialog, null);
        tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
        tvContent = (TextView) contentView.findViewById(R.id.tvContent);
        tvSure = (TextView) contentView.findViewById(R.id.tvSure);
        tvCancel = (TextView) contentView.findViewById(R.id.tvCancel);
        tvContent.setText(content);
        tvSure.setText(strCertain);
        if (titleString == null || titleString.length() == 0) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(titleString);
        }
        if (strCancel == null || strCancel.length() == 0) {
            tvCancel.setVisibility(View.GONE);
            tvSure.setBackgroundResource(R.drawable.bg_shape_bottom);
        } else {
            tvSure.setBackgroundResource(R.drawable.bg_shape_left);
            tvCancel.setVisibility(View.VISIBLE);
            tvCancel.setText(strCancel);
        }
        tvCancel.setOnClickListener(clickListener);
        tvSure.setOnClickListener(clickListener);
        dialog = new Dialog(context, R.style.MyDialogStyle2);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 
     */
    private boolean canOutSideClose = true;

    /**
     * 
     * TODO 添加描述
     
     *                 2016年10月10日 Json Lai CREATE
     * @author Json Lai
     * 
     * @param canOutSideClose
     */
    public void setOutsideTouchable(boolean canOutSideClose) {
        this.canOutSideClose = canOutSideClose;
    }

    /**
     * 
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvSure:
                    dialog.cancel();
                    if (iclickListener != null) {
                        iclickListener.btnSure();
                    }
                    break;
                case R.id.tvCancel:
                    dialog.cancel();
                    if (iclickListener != null) {
                        iclickListener.btnCancel();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 
     * <P><B>Description: </B> 监听   </P>
     
     *                 2016年10月10日 Json Lai CREATE
     * @author Json Lai
     * @version 1.0
     */
    public interface XClickListener {
        /**
         * 
         * 确定
         
         *                 2016年10月10日 Json Lai CREATE
         * @author Json Lai
         *
         */
        void btnSure();

        /**
         * 
         * 取消
         
         *                 2016年10月10日 Json Lai CREATE
         * @author Json Lai
         *
         */
        void btnCancel();
    }

    /**
     * 
     * 设置内容
     
     *                 2016年10月10日 Json Lai CREATE
     * @author Json Lai
     * 
     * @param content
     */
    public void setcontent(String content) {
        tvContent.setText(content);
    }
}
