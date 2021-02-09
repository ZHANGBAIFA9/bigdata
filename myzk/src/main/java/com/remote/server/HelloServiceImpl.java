package com.remote.server;

import com.remote.common.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 13:15
 * @Description:
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    protected HelloServiceImpl() throws RemoteException{}
    @Override
    public String sayHello(String name) throws RemoteException {
        System.out.println("服务器端：name = "+name);
        //String.format("hello %s",name);
        return "hello "+name;
    }
}
