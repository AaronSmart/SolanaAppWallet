
package com.econet.app.uitl;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author dai.jianhui
 */
public class SharedPreferencesUtils {


    public static String externalStorage = "ExternalStorage";
    private static SharedPreferences mySharePreference;
    private static SharedPreferences.Editor mySharepreEditor;
    private static SharedPreferencesUtils mySharePreferenceUtil;

    public static synchronized SharedPreferencesUtils getInstance(Context context) {
        if (mySharePreferenceUtil == null) {
            mySharePreferenceUtil = new SharedPreferencesUtils();
            mySharePreference = context.getSharedPreferences("mpas", Context.MODE_PRIVATE);
            mySharepreEditor = mySharePreference.edit();
        }
        return mySharePreferenceUtil;
    }
    public void  clear()
    {
        mySharePreference.edit().clear().commit();
    }

    private SharedPreferencesUtils()
    {

    }

    public void submitString(String key, String values) {
        mySharepreEditor.putString(key, values);
        mySharepreEditor.commit();
    }
    public String getValueString(String key, String defaultValue) {

        return mySharePreference.getString(key, defaultValue);
    }



    public void submitLong(String key, long value) {
        mySharepreEditor.putLong(key, value);
        mySharepreEditor.commit();
    }

    public long getLong(String key) {
        return mySharePreference.getLong(key, -1);
    }

    public void submitBoolean(String key, boolean isChecked) {
        mySharepreEditor.putBoolean(key, isChecked);
        mySharepreEditor.commit();
    }

    public boolean getValueBoolean(String key, boolean defaultValue) {
        return mySharePreference.getBoolean(key, defaultValue);
    }
    
    public void submitInt(String key, int param) {
        mySharepreEditor.putInt(key, param);
        mySharepreEditor.commit();
    }
    
    public int getValueInt(String key) {
        return mySharePreference.getInt(key, 0);
    }

    public int getValueIntDefault1(String key) {
        return mySharePreference.getInt(key, 1);
    }
   
}
