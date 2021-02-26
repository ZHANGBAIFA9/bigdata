package com.myflume.sink;

import org.apache.flume.Channel;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.sink.AbstractSink;

import java.util.Map;

/**
 * 配置过程：
 *  1、参考loggerSink
 *      编写相关代码
 *  2、maven -> package 打包  -> 上传到 -> soft/flume/lib
 *  3、相关配置文件如下：
 *      # Name the components on this agent
 *      a1.sources = r1
 *      a1.sinks = k1
 *      a1.channels = c1
 *
 *      # Describe/configure the source
 *      # tailDir source 优点： 文件进行消费后，更新消费数据索引，以便下次消费的时候继续消费。
 *      a1.sources.r1.type = TAILDIR
 *      a1.sources.r1.filegroups = f1 f2
 *      a1.sources.r1.filegroups.f1 = /home/centos/logs/.*txt
 *      a1.sources.r1.filegroups.f2 = /home/centos/log2/pv.txt
 *
 *      # Describe the sink ------------------
 *      # MySink sink 将消费数据打印在控制台上，用于测试
 *      a1.sinks.k1.type = MySink
 *
 *      # Use a channel which buffers events in memory
 *      a1.channels.c1.type = memory
 *      a1.channels.c1.capacity = 1000000
 *
 *      # Bind the source and sink to the channel
 *      a1.sources.r1.channels = c1
 *      a1.sinks.k1.channel = c1
 */
class TestSink extends AbstractSink {
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
                StringBuffer sb = new StringBuffer();
                Map<String, String> map = event.getHeaders();

                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    sb.append(key+":"+value+",");
                }
                String body = new String(event.getBody());
                String headers = sb.toString();
                String newStr = "";
                if(headers.length() != 0){
                    newStr = headers.substring(0, headers.length() - 1);
                }
                System.out.println("头部=" + newStr + "\t" + "身体=" + body);

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
