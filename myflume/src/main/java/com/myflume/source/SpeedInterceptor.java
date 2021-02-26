package com.myflume.source;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.logging.Logger;

/**
 * @Auther: ZHANGBAIFA
 * @Date: 2020/9/14 09:57
 * @Description: flume 源二次开发限速设置
 */
public class SpeedInterceptor implements Interceptor {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(SpeedInterceptor.class);
    //设置单位
    private static long KB = 1024 ;
    //指定最后一次发送时间
    private long lastEventSentTick = System.nanoTime() ;
    //指定发送数据长度
    private long pastSentLength = 0L;
    //指定发送数据最大值
    private long max ;
    //1秒钟=1000000000L纳秒
    private long timeCostPerCheck = 1000000000L;
    //设置标记位
    private boolean flag = true ;
    //统计数量
    private int num = 0 ;

    public SpeedInterceptor(long limitRate){
        this.max = (limitRate * KB);
    }

    @Override
    public void initialize() {
        // no-operation
    }
    @Override
    public void close() {
        // no-operation
    }
    @Override
    public Event intercept(Event event) {
        this.num +=1 ;
        if(this.pastSentLength > this.max) {
            //获取纳秒
            long nowTick = System.nanoTime();
            //倍数
            long multiple = this.pastSentLength / this.max;
            //缺失时间=所需时间-实际时间
            long missedTime = multiple * this.timeCostPerCheck - (nowTick - this.lastEventSentTick);
            if(missedTime > 0L) {
                try {
/*                    System.out.printf("Limit source send rate, headerLength:%d,pastSentLength:%d,lastEventSentTick:%d,sleepTime:%d, num:%d\n",
                            headerSize, pastSentLength, lastEventSentTick, missedTime / 1000000, num);*/
//                    logger.info("missedTime:="+missedTime);
                    Thread.sleep(missedTime / 1000000L,(int)(missedTime % 1000000L));
                } catch(InterruptedException e){
//发生异常时抛出,flume会自行处理这个可以异常
                    throw new RuntimeException(e);
                }
            }
            this.num = 0;
            this.pastSentLength = 0L;
            this.lastEventSentTick = (nowTick + (missedTime > 0L ? missedTime : 0L));
        }
        this.pastSentLength += event.getBody().length;
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event : list) {
            intercept(event);
        }
        return list;
    }

    public static class Builder implements Interceptor.Builder {
        private long limitRate;
        @Override
        public Interceptor build() {
            return new SpeedInterceptor(this.limitRate);
        }
        @Override
        public void configure(Context context) {
            this.limitRate = context.getLong(Constants.LIMIT_RATE, Long.valueOf(Constants.DEFAULT_RATE));
            //this.headerSize = context.getLong(Constants.HEADER_SIZE, Long.valueOf(Constants.DEFAULT_SIZE));
        }

        public static class Constants {
            public static long DEFAULT_RATE = 500L;
            //public static long DEFAULT_SIZE = 16L;
            public static String LIMIT_RATE = "limitRate";
        }
    }
}
