package com.remoting.client;

import com.remoting.common.HelloService;

import java.rmi.Naming;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 14:02
 * @Description:
 */
public class RmiClient {
    public static void main(String[] args) throws Exception {
        //服务的url
        String url = "rmi://localhost:1099/demo.zookeeper.remoting.server.HelloServiceImpl";
        //发现服务，并将提供的服务创建对应的类的对象
        HelloService helloService = (HelloService) Naming.lookup(url);
        String result = helloService.sayHello("Tom");
        System.out.println(result);
    }
}
