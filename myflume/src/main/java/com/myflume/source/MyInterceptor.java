package com.myflume.source;

import com.google.common.collect.Lists;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import com.google.common.base.Charsets;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.flume.interceptor.RegexFilteringInterceptor.Constants.DEFAULT_REGEX;
import static org.apache.flume.interceptor.RegexFilteringInterceptor.Constants.REGEX;
/**
 * @Auther: ZHANGBAIFA
 * @Date: 2020/9/14 10:12
 * @Description: 自定义拦截器
 */
public class MyInterceptor implements Interceptor {
    private final Pattern regex;
    private MyInterceptor(Pattern regex) {
        this.regex = regex;
    }

    @Override
    public void initialize() {
        // NO-OP...
    }
    @Override
    public void close() {
        // NO-OP...
    }
    @Override
    public Event intercept(Event event) {
        String body = new String(event.getBody(), Charsets.UTF_8);
        //匹配日志信息中以 Parsing events: 为开头关键字,以END 为结尾关键字 的日志信息段
        String pattern= "(Parsing events)(.*)(END)";
        // 创建 Pattern 对象
        Pattern r= Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m= r.matcher(body);
        StringBuffer bodyoutput = new StringBuffer();
        if(m.find())//此处可以用多个正则进行匹配,多条件可以用&& 或者|| 的方式约束连接。
        {
            //多个正则匹配后可以将多个匹配的结果append 到bodyoutput
            bodyoutput=bodyoutput.append(m.group(0));
            event.setBody(bodyoutput.toString().getBytes());
        }else{
            event=null;
        }
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        List<Event> intercepted = Lists.newArrayListWithCapacity(list.size());
        for (Event event : list) {
            Event interceptedEvent = intercept(event);
            if (interceptedEvent != null) {
                intercepted.add(interceptedEvent);
            }
        }
        return intercepted;
    }
    public static class Builder implements Interceptor.Builder {
        private Pattern regex;
        //使用Builder初始化Interceptor
        @Override
        public Interceptor build() {
            return new MyInterceptor(regex);
        }

        @Override
        public void configure(Context context) {
            String regexString = context.getString(REGEX, DEFAULT_REGEX);
            regex = Pattern.compile(regexString);
        }
    }

}
