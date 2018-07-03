package com.xz.framework.utils.string;

import com.xz.framework.common.Constants;

import java.util.Arrays;
import java.util.Map;

/**
 * <pre>
 * String工具类
 * </pre>
 *
 * @author jack
 */
public class StringUtil {


    public static String getUpperStart(String field) {
        String fields[] = field.split("_");
        if(fields.length<=1)
            return  field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
        else {
            String smallStart ="";
            smallStart = smallStart + getUpperStart(fields[0]);
            for(int i=1;i<fields.length;i++)
            {
                smallStart = smallStart + getUpperStart(fields[i]);
            }
            return smallStart;
        }
    }
    public static String getLowerStart(String field) {
        String fields[] = field.split("_");
        if(fields.length<=1)
            return  field.substring(0, 1).toLowerCase() + field.substring(1, field.length());
        else {
            String smallStart ="";
            smallStart = smallStart + getLowerStart(fields[0]);
            for(int i=1;i<fields.length;i++)
            {
                smallStart = smallStart + getUpperStart(fields[i]);
            }
            return smallStart;
        }
    }

    public static String getGetMethod(String field) {
        String getMethod = "get" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
        return getMethod;
    }



    public static String getSetMethod(String field) {
        String getMethod = "set" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
        return getMethod;
    }

    public static boolean isEmpty(String field) {
        if (field == null || field.equals(""))
            return true;
        else
            return false;
    }

    public static boolean isNotEmpty(String field) {
        return !isEmpty(field);
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

    public static String urlToHttps(String backgroundUrl) {
        if(StringUtil.isEmpty(backgroundUrl)){
            return "";
        }
        if(!backgroundUrl.startsWith("http")){
            backgroundUrl = Constants.SCHEME + backgroundUrl;
        }
        return backgroundUrl;
    }

}
