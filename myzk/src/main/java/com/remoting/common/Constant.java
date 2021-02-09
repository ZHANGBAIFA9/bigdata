package com.remoting.common;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 14:03
 * @Description:
 */
public interface Constant {

    String ZK_CONNECTION_STRING = "192.168.76.102:2181,192.168.76.103:2181,192.168.76.104:2181";
    int ZK_SESSION_TIMEOUT = 5000;
    String ZK_REGISTRY_PATH = "/registy";
    String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
}
