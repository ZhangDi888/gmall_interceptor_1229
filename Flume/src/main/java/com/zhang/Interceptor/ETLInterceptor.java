package com.zhang.Interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import sun.rmi.runtime.Log;

import javax.xml.stream.events.StartDocument;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class ETLInterceptor implements Interceptor {
    //整体思路：先获取文件，把文件转成string格式，然后根据start-event判断分到哪个channel
    //对两个类型的日志进行清洗
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //获取文件
        byte[] body = event.getBody();
        //把文件转成string,然后转成utf8格式
        String logs = new String(body, Charset.forName("utf8"));
        //判断一下，如果是start就进去start清洗，如果是event，同理
        if(logs.contains("start")){
            if(LogUntil.validateStart(logs)){
                return event;
            }
        } else {
            if (LogUntil.validateEvent(logs)){
                return event;
            }
        }
        return null;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        //对集合进行遍历，用迭代器，因为迭代器可以移除空对象
        Iterator<Event> iterator = list.iterator();
        while (iterator.hasNext()){
            //获取iterator
            Event next = iterator.next();
            if(intercept(next) == null){
                iterator.remove();
            }
        }
        return list;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new ETLInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
