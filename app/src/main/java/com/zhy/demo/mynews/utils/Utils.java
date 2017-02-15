package com.zhy.demo.mynews.utils;

/**
 * Created Time: 2017/2/15.
 * Author:  zhy
 * 功能：String、
 */

public class Utils {

    /**
     * 获取字符串的字节长度
     *
     * @param begin
     * @return
     */
    public static int getStringLength(String begin) {

        int length = 0;
        if (begin!=null){
            for (int i = 0; i < begin.length(); i++) {
                if (0 <= begin.charAt(i) && begin.charAt(i) <= 127) {
                    length += 1;
                } else {
                    length += 2;
                }
            }
        }

        return length;
    }

    /**
     * 数字字符串 3.000返回3
     *
     * @param begin  不能为null
     * @return
     */
    public static String getString(String begin) {
        String result = begin;

        if (begin.contains(".")) {
            int xx = -1;
            for (int i = begin.length() - 1; i >= 0; i--) {
                if ('.' == (begin.charAt(i))) {
                    xx = i;
                    break;
                }
                if ('0' == (begin.charAt(i))) {
                    xx = i;
                } else {
                    break;
                }
            }
            if (xx != -1) {
                result = begin.substring(0, xx);
            }
        }
        return result;
    }
}
