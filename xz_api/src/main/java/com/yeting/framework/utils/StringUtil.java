package com.yeting.framework.utils;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class StringUtil {

    /**
     * 将2017-09-29 09:45:10格式的拼接为2017年10月26日13时12分格式的，并合并返回
     * @param str1
     * @param str2
     * @return
     */
    public static String getSqlLikeByFull(String str1, String str2) {
/*        String str1 = "2017年10月26日13时12分";
        String str2 = "2017年10月26日15时22分";*/
        str1 = DateUtil.stringDateToDateFormat(str1);
        str2 = DateUtil.stringDateToDateFormat(str2);
        String str3 = str1 + "至" + str2.substring(10, str2.length());
        return str3;
    }

    public static String getSqlLikeByFull(String column) {
        return "%" + column + "%";
    }

    public static String getSqlLikeByPrefix(String column) {
        return column + "%";
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static int strLength(String str) {
        return str.length();
    }

    public static boolean isOnlyNumAndChar(String str){
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for(int i=0 ; i<str.length() ; i++){ //循环遍历字符串
            if(Character.isDigit(str.charAt(i))){     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if(Character.isLetter(str.charAt(i))){   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        if(isDigit==true && isLetter==true)
            return false;
        else
            return true;
    }

    public static String randomCheckCode() {
        String str = "0123456789";
        // 声明返回值
        String temp = "";
        // 验证码
        // 1-9，a-z A-Z ctrl+shift+X
        System.out.println("字符串的长度:" + str.length()); // 62
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            // 随机获取 0-61 数字 4次 charAt(num);
            int num = random.nextInt(str.length());
            char c1 = str.charAt(num); // 索引从0开始 到61
            temp += c1;
        }
        return temp;
    }

    public static String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

    public static  String getParamString(String key,Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            if(key.equalsIgnoreCase(e.getKey()))
            {
                String[] value = e.getValue();
                if (value != null && value.length == 1) {
                    sb.append(value[0]).append("\t");
                } else {
                    sb.append(Arrays.toString(value)).append("\t");
                }
                break;
            }
        }
        return sb.toString();
    }

    public static String Base64ToStr(String name){
        if(isEmpty(name)){
            return "";
        }
        String decodeNickname = "";
        try {
            decodeNickname = new String (Base64.getDecoder().decode(name.getBytes()),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeNickname;
    }

    public static String StrToBase64(String name){
        if(isEmpty(name)){
            return "";
        }
        String encodeNickname = "";
        try {
            encodeNickname = new String (Base64.getEncoder().encode(name.getBytes()),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeNickname;
    }

    public static Long StrToLong(String string){
        Long l = 0L;
        try {
            l = Long.valueOf(string);
        }catch (Exception e){
            return 0L;
        }
        return l;
    }

}
