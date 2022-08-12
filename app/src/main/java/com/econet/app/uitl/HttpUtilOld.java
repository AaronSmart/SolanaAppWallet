package com.econet.app.uitl;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtilOld {

    public static void getDataFromServerUseGet(String url, HashMap<String,String> paramsMap, HashMap<String,String>headers,Callback callback)
    {
//        new Thread() {
//            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                String parameter="?";
                for(Map.Entry<String, String> entry: paramsMap.entrySet())
                {
                    parameter=parameter+entry.getKey()+"="+entry.getValue()+"&";
                }
                String innerUrl=url+parameter.substring(0,parameter.length()-1);
                Headers header=Headers.of(headers);
                Request request = new Request.Builder().url(innerUrl).headers(header).get().build();
                okHttpClient.newCall(request).enqueue(callback);
//            }
//        }.start();

    }
    public static void getDataFromServerUsePost(String url, String json,HashMap<String,String> headers, Callback callback)
    {
        MediaType raw = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(raw, json);
        Headers header=Headers.of(headers);
        Request request = new Request.Builder().url(url).headers(header).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void getDataFromServer(String url, HashMap<String,String> paramsMap,Callback callback)
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder=new FormBody.Builder();
        for(Map.Entry<String, String> entry: paramsMap.entrySet())
        {
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody requestBody =builder.build();
        Request request = new Request.Builder().url(url).addHeader("x-auth-token","ssss").addHeader("x-auth-id","aaa").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void getDataFromServerRaw(String url, String json,Callback callback)
    {
        MediaType raw = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(raw, json);
        Request request = new Request.Builder().url(url).addHeader("x-auth-token","ssss").addHeader("x-auth-id","aaa").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
