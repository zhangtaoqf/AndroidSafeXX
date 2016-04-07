package com.zt.jiamishouji.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/3/2 0002.
 */
public class MySharePreferenceUtils {
    private SharedPreferences sharedPreferences;

    public MySharePreferenceUtils(String fileName,Context context) {
        sharedPreferences = context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }
    //获取一个数据
    public String getParamter(String key)
    {
        String s = sharedPreferences.getString(key, "");
        return s;
    }
    //保存一个值
    public void saveParamter(String key,String value)
    {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        SharedPreferences.Editor editor = edit.putString(key, value);
        editor.commit();
    }

}
