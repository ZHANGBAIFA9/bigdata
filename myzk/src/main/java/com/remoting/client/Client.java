package com.remoting.client;

import com.remoting.common.HelloService;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 14:02
 * @Description:
 */
public class Client {
    public static void main(String[] args) throws Exception {
        ServiceConsumer consumer = new ServiceConsumer();
        while (true) {
            HelloService helloService = consumer.lookup();
            String result = helloService.sayHello("Jack");
            System.out.println(result);
            Thread.sleep(3000);
        }
    }
}
