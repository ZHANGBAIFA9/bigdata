package com.myflume.sink;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

import java.util.Map;

/**
 * Create By BF On 2020/5/27
 */
public class MySink extends AbstractSink implements Configurable {
    private static final String DefaultWord = "hello world" ;
    private String word = "word" ;
    private String w ;

    @Override
    public void configure(Context context) {
        w = context.getString(word , DefaultWord) ;
    }

    @Override
    public Status process() throws EventDeliveryException {
        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event = null;
        try {
            transaction.begin();
            //从通道中获取数据
            event = channel.take();

            if (event != null) {
                System.out.println("配置数据\t" + w);
                System.out.println("真实数据\t" + new String(event.getBody()));
                System.out.println("---------------------------");
            } else {
                // No event found, request back-off semantics from the sink runner
                result = Status.BACKOFF;
            }
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new EventDeliveryException("Failed to log event: " + event, ex);
        } finally {
            transaction.close();
        }
        return result;
    }
}
