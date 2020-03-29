package com.zhang.Interceptor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import sun.applet.Main;

public class LogUntil {

    public static boolean validateStart(String logs) {
        //判断文件是否为空
        if(StringUtils.isBlank(logs)){
            return false;
        }
        //如果logs文件不以{开始或}结束返回false
        if(!logs.startsWith("{") || !logs.endsWith("}")){
            return false;
        }
        return true;
    }

    public static boolean validateEvent(String logs) {
        if(StringUtils.isBlank(logs)){
            return false;
        }

        //对文件进行切割
        String[] split = logs.split("\\|");
        //判断是不是切成两份
        if(split.length != 2){
            return false;
        }
        String time = split[0];
        String json = split[1];
        //判断下time是不是数字而且是不是13位
        if(time.length() != 13){
            return false;
        }
        if(!NumberUtils.isDigits(time)){
            return false;
        }
        if(!json.startsWith("{") || !json.endsWith("}")){
            return false;
        }
        return true;
    }

}
