package com.econet.app.uitl;

import com.econet.app.homepage.BaseActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author dai.jianhui basic network util use https
 */
public class HttpUtil  {

    private static X509TrustManager trustManager;
    private static OkHttpClient okHttpClient;
    static
    {
        try {
            trustManager = trustManagerForCertificates(MyApplication.getContext().getAssets().open("keystore.crt"));//new BaseActivity().getAssetManager().open("keystore.crt")
            okHttpClient = new OkHttpClient.Builder().sslSocketFactory(createSSLSocketFactory(), trustManager)
                    .hostnameVerifier((hostname, sslSession) -> {
                        return true;
                        //验证主机名
//                        if("www.test.com".equals(hostname))
//                        {
//                            return true;
//                        }
//                        else
//                        {
//                            HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();
//                            return verifier.verify(hostname,sslSession);
//                        }
                    }).build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getDataFromServerUseGet(String url, HashMap<String,String> paramsMap, HashMap<String,String>headers,Callback callback)
    {
        String parameter="?";
        for(Map.Entry<String, String> entry: paramsMap.entrySet())
        {
            parameter=parameter+entry.getKey()+"="+entry.getValue()+"&";
        }
        String innerUrl=url+parameter.substring(0,parameter.length()-1);
        Headers header=Headers.of(headers);
        Request request = new Request.Builder().url(innerUrl).headers(header).get().build();
        okHttpClient.newCall(request).enqueue(callback);

    }
    public static void getDataFromServerUsePost(String url, String json,HashMap<String,String> headers, Callback callback)
    {
        MediaType raw = MediaType.parse("application/json; charset=utf-8");
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
    private static X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException
    {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        char[] password = "password".toCharArray(); // 这里可以使用任意密码
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }
        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager))
        {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

}
