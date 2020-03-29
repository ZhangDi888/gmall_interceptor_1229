package com.zhang.Interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

public class TypeInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        //获取event
        byte[] body = event.getBody();
        //转换成string，并且格式转成utf8
        String logs = new String(body, Charset.forName("utf8"));
        //判断类型。然后分组
        if(logs.contains("start")){
            event.getHeaders().put("topic","topic_start");
        } else {
            event.getHeaders().put("topic","topic_event");
        }
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event : list) {
            intercept(event);
        }
        return list;
    }

    @Override
    public void close() {

    }
    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new TypeInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
