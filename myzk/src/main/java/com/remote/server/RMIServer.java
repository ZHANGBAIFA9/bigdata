package com.remote.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 13:15
 * @Description:
 */
public class RMIServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        int port = 1099;
        String url = "rmi://localhost:1099/com.remote.server.HelloServiceImpl";
        //创建注册服务
        LocateRegistry.createRegistry(port);
        //发布服务器
        Naming.rebind(url,new HelloServiceImpl());
        System.out.println("服务器端启动了。。。");
    }
}
