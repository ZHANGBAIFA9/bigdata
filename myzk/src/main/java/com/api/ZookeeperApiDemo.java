package com.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/9 11:53
 * @Description:
 */
public class ZookeeperApiDemo {
    //session的实效时间
    private static final int SESSION_TIMEOUT = 30000;
    //创建Logger对象，按照文件log4j.properties中指定的格式输出日志到控制台
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperApiDemo.class);
    //zookeeper连接对象
    private ZooKeeper zooKeeper;
    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            LOGGER.info("process:"+event.getType());
        }
    };

    @Before
    public void connect() throws IOException {
        zooKeeper = new ZooKeeper("192.168.76.102:2181,192.168.76.103:2181,192.168.76.104:2181",
                SESSION_TIMEOUT,watcher);
    }
    @After
    public void close(){
        if(zooKeeper!=null){
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**创建节点：
     *CreateMode：
     * PERSISTENT 普通持久节点
     * PERSISTENT_SEQUENTIAL:顺序持久节点
     * EPHEMERAL ：普通临时
     * EPHEMERAL_SEQUENTIAL：顺序临时节点
     * Access Control List: 访问控制列表
     * https://baike.baidu.com/item/ACL/362453?fr=aladdin
     * OPEN_ACL_UNSAFE: ANYONE CAN VISIT
     */
    @Test
    public void createNode(){
        String result = null;
        try {
            result = zooKeeper.create("/zk001",//节点的全路径
                    "zk001-data".getBytes(),//节点中的数据->字节数据
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,//指定访问控制列表
                    CreateMode.EPHEMERAL //指定创建节点的类型
            );
            Thread.sleep(30000);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("create node success,result={}",result);
    }
    @Test
    public void deleteNode(){
        try {
            zooKeeper.delete("/zk06/test-0000000008",-1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Assert.fail();
        }
    }
    @Test
    public void getNodeData(){
        String result = null;
        try {
            byte[] data = zooKeeper.getData("/zk01", null, null);
            result = new String(data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Assert.fail();
        }
        LOGGER.info("getNodeData={}",result);
    }

    /**查看日志看从哪一个zk服务器上读取的数据，然后在xshell通过命令zkServer.sh stop关闭对应的zk服务器
     * 然后在查看日志发现读取数据是从另外一个zk节点读取的数据。
     * @throws InterruptedException
     */
    @Test
    public void getNodeDataChangeNode() throws InterruptedException {
        String result = null;
        try {
            byte[] data = zooKeeper.getData("/zk01", null, null);
            result = new String(data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Assert.fail();
        }
        LOGGER.info("getNodeData==1=={}",result);
        Thread.sleep(30000);
        try {
            byte[] data = zooKeeper.getData("/zk01", null, null);
            result = new String(data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Assert.fail();
        }
        LOGGER.info("getNodeData===2={}",result);
    }
    @Test
    public void testGetDataWather(){
        String result = "";
        try {
            //在读取数据时添加一个监听事件
            byte[] data = zooKeeper.getData("/zk01", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    LOGGER.info("testGetDataWather watch type:{}", event.getType());
                    System.out.println("watcher ok");
                    testGetDataWather();
                }
            }, null);
            result = new String(data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            Assert.fail();
        }
        LOGGER.info("result = {}",result);
        //
        try {
            System.out.println(".....set1.....");
            zooKeeper.setData("/zk01","updateed01".getBytes(),-1);
            System.out.println(".....set2.....");
            zooKeeper.setData("/zk01","updateed02".getBytes(),-1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=======over=============");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**watch:true:表示使用ZooKeeper连接对象上添加的监听事件
     * watch:false:不使用zooKeeper连接对象上添加的监听事件
     */
    @Test
    public void isExistWatcher1(){
        Stat stat = null;
        try {
            stat = zooKeeper.exists("/zk01/node01", false);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        //如果stat不为null，继续往后执行
        Assert.assertNotNull(stat);
        try {
            zooKeeper.delete("/zk01/node01",-1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 注册了自定义的监听对象，走自定义的。
     */
    @Test
    public void isExistWatcher2(){
        Stat stat = null;
        try {
            stat = zooKeeper.exists("/zk02/node02", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    LOGGER.info("isExistWatcher2  wather type:{}",event.getType());
                }
            });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        //如果stat不为null，继续往后执行
        Assert.assertNotNull(stat);
        try {
            zooKeeper.setData("/zk02/node02","isExistWatcher2_edited".getBytes(),-1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        try {
            zooKeeper.delete("/zk02/node02",-1);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
    @Test
    public void getChilds(){
        try {
            List<String> children = zooKeeper.getChildren("/zk06", true);
            for(String node:children){
                LOGGER.info("================{}",node);
                //  /zk06/test-0000000001
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }
}
