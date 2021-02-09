package com.remoting.server;

import com.remoting.common.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 14:03
 * @Description:
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    protected HelloServiceImpl() throws RemoteException {
    }
    @Override
    public String sayHello(String name) throws RemoteException {
        return String.format("Hello %s", name);
    }
}
