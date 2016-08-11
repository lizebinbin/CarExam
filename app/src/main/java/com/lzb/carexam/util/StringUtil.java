package com.lzb.carexam.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MooreLi on 2016/8/11.
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(String str) {
        return (str != null) && !"".equals(str);
    }

    public static boolean isNumber(String str) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]*$");
            Matcher m = p.matcher(str);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
