
package com.econet.app.uitl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * 
 * <P><B>Description: </B> 工具类  </P>
 
 *                 2016年7月19日 Wade Huang CREATE
 * @author Wade Huang
 * @version 1.0
 */
public class Tools {
    /**
     * TAG
     */
    private final static String TAG = "Tools";

    /**
     * 
     * 获取屏幕属性
     
     *                 2016年10月10日 Json Lai CREATE
     * @author Json Lai
     * 
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 
     * TODO 缓存图片路径
     
     *                 2016年6月27日 Wade Huang CREATE
     * @author Wade Huang
     * 
     * @param context
     * @return
     */
    public static String getStorageDirCache(Context context) {
        File file = null;
        String packageName = context.getPackageName();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // SD卡不存在
            file = new File("data/data/" + packageName + "/evecom_mpas_cache");
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {//SD卡存在
            String exPath = SharedPreferencesUtils.getInstance(context).getValueString(
                    SharedPreferencesUtils.externalStorage, Environment.getExternalStorageDirectory().getPath());
            file = new File(exPath + "/" + packageName + "/evecom_mpas_cache");
            if (!file.exists()) {
                if (!file.mkdirs()) {// 创建文件失败
                    ArrayList<String> list = getSDCardPathEx(); // 获取SD卡路径列表
                    for (int i = 0; i < list.size(); i++) {
                        exPath = getSDCardPathEx().get(i);
                        file = new File(exPath + "/evecom_mpas_cache");

                        if (file.exists()) {
                            //                            AppConfig.putString(AppContext.context,
                            //                                    AppConfig.externalStorage, exPath);
                            SharedPreferencesUtils.getInstance(context)
                                    .submitString(SharedPreferencesUtils.externalStorage, exPath);
                            break;
                        }
                        if (file.mkdirs()) {
                            //                            AppConfig.putString(AppContext.context,
                            //                                    AppConfig.externalStorage, exPath);
                            SharedPreferencesUtils.getInstance(context)
                                    .submitString(SharedPreferencesUtils.externalStorage, exPath);
                            break;
                        }
                    }

                }
            }
        }
        return file.getAbsolutePath();
    }

    public static String getStorageDir(Context context) {
        File file = null;
        String packageName = context.getPackageName();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // SD卡不存在
            file = new File("data/data/" + packageName + "/evecom_mpas_files");
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {//SD卡存在
            String exPath = SharedPreferencesUtils.getInstance(context).getValueString(
                    SharedPreferencesUtils.externalStorage, Environment.getExternalStorageDirectory().getPath());
            file = new File(exPath + "/" + packageName + "/evecom_mpas_files");
            if (!file.exists()) {
                if (!file.mkdirs()) {// 创建文件失败
                    ArrayList<String> list = getSDCardPathEx(); // 获取SD卡路径列表
                    for (int i = 0; i < list.size(); i++) {
                        exPath = getSDCardPathEx().get(i);
                        file = new File(exPath + "/evecom_mpas_files");

                        if (file.exists()) {
                            //                            AppConfig.putString(AppContext.context,
                            //                                    AppConfig.externalStorage, exPath);
                            SharedPreferencesUtils.getInstance(context)
                                    .submitString(SharedPreferencesUtils.externalStorage, exPath);
                            break;
                        }
                        if (file.mkdirs()) {
                            //                            AppConfig.putString(AppContext.context,
                            //                                    AppConfig.externalStorage, exPath);
                            SharedPreferencesUtils.getInstance(context)
                                    .submitString(SharedPreferencesUtils.externalStorage, exPath);
                            break;
                        }
                    }

                }
            }
        }
        return file.getAbsolutePath();
    }

    public static ArrayList<String> getSDCardPathEx() {

        ArrayList<String> list = new ArrayList<String>();

        // String mount = new String();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;

                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        list.add(columns[1]);
                        // mount = mount.concat("*" + columns[1] + "\n");
                    }
                } else if (line.contains("fuse")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        // mount = mount.concat(columns[1] + "\n");
                        list.add(columns[1]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            //            e.printStackTrace();
        } catch (IOException e) {
            //            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取某个控件的高度
     */
    public static int getViewHigh(final View view) {
        ViewTreeObserver vto2 = view.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return view.getHeight();
    }

}
