package com.remoting.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 14:03
 * @Description:
 */
public class RmiServer {
    public static void main(String[] args) throws Exception {
        //端口号
        int port = 1099;
        //对外发布的url
        String url = "rmi://localhost:1099/demo.zookeeper.remoting.server.HelloServiceImpl";
        //绑定端口
        LocateRegistry.createRegistry(port);
        //绑定url
        Naming.rebind(url, new HelloServiceImpl());
    }
}
