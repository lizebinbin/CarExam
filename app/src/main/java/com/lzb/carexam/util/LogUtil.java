package com.lzb.carexam.util;

import android.util.Log;

import com.lzb.carexam.base.Config;

/**
 * Created by MooreLi on 2016/8/12.
 */
public class LogUtil {
    private static String TAG = "com.lzb.carexam";

    public static void i(String msg) {
        if (Config.isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (Config.isDebug) {
            Log.v(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (Config.isDebug) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (Config.isDebug) {
            Log.e(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (Config.isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (Config.isDebug) {
            Log.v(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (Config.isDebug) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (Config.isDebug) {
            Log.e(TAG, msg);
        }
    }
}
