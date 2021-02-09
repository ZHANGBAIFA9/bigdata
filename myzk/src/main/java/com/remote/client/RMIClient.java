package com.remote.client;

import com.remote.common.HelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 13:15
 * @Description:
 */
public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String url = "rmi://localhost:1099/com.remote.server.HelloServiceImpl";
        //根据url寻找被注册的服务
        HelloService helloService = (HelloService) Naming.lookup(url);
        //调用服务器端的方法
        String result = helloService.sayHello("rose");
        System.out.println("result:"+result);

    }
}
