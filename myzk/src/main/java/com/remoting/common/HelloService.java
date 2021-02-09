package com.remoting.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 14:03
 * @Description:
 */
public interface HelloService extends Remote {
    String sayHello(String name) throws RemoteException;
}

