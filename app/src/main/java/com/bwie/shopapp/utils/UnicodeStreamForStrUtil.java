package com.bwie.shopapp.utils;

/**
 * 作者：张玉轲
 * 时间：2017/10/10
 */

public class UnicodeStreamForStrUtil {
    //字节流转换工具类

    public static String getStr(String dataStr){
        int index = 0;

        StringBuffer buffer = new StringBuffer();



        int li_len = dataStr.length();

        while (index < li_len) {

            if (index >= li_len - 1  || !"\\u".equals(dataStr.substring(index, index + 2))) {
                buffer.append(dataStr.charAt(index));
                index++;
                continue;
            }
            String charStr = "";
            charStr = dataStr.substring(index + 2, index + 6);
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(letter);
            index += 6;

        }

        return buffer.toString();

    }
}
