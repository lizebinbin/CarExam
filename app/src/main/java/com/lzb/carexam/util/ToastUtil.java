package com.lzb.carexam.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by MooreLi on 2016/8/12.
 */
public class ToastUtil {
    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, String msg, int millSec) {
        Toast.makeText(context, msg, millSec).show();
    }

}
