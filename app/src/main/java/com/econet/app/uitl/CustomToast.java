/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.econet.app.uitl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 
 * <P><B>Description: </B> 消息提示工具类   </P>
 
 *                 2016年10月10日 Json Lai CREATE
 * @author Json Lai
 * @version 1.0
 */
public class CustomToast {

    /**
     * 提示类
     */
    private static Toast mToast;
    /**
     * 上下文环境
     */
    private static Context context;
    /**
     * 文本
     */
    private static String text;
    /**
     * 
     */
    private static Handler mhandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (text != null && !TextUtils.isEmpty(text.trim()) && !"null".equals(text)) {
                if (null != mToast) {
                    mToast.setText(text);
                } else {
                    mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                }
                mToast.show();
            }

        }
    };
    /**
     * 
     */
    private static Runnable r = new Runnable() {
        public void run() {
            if (null != mToast) {
                mToast.cancel();
            }
        };
    };

    /**
     * 
     * 提示消息
     
     *                 2016年10月10日 Json Lai CREATE
     * @author Json Lai
     * 
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        CustomToast.context = context;
        mhandler.removeCallbacks(r);
        CustomToast.text = text;
        mhandler.sendEmptyMessage(0);
        mhandler.postDelayed(r, 3000);
    }

    /**
     * 
     * TODO 添加描述
     
     *                 2016年10月10日 Json Lai CREATE
     * @author Json Lai
     * 
     * @param context
     * @param strId
     */
    public static void showToast(Context context, int strId) {
        showToast(context, context.getString(strId));
    }
}
