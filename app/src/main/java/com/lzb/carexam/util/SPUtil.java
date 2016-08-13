package com.lzb.carexam.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MooreLi on 2016/8/13.
 */
public class SPUtil {
    private static String SHAREDPREFERENCE_NAME = "carExam";

    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    public static void putIntValue(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    public static int getIntValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }
}
