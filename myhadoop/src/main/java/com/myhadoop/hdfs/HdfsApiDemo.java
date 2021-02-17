package com.myhadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/15 11:31
 * @Description:
 */
public class HdfsApiDemo {
    //配置文件初始到 Configuration类对象中
    private Configuration configuration ;
    //创建分布式文件管理系统对象FileSystem
    private FileSystem fileSystem ;

    /**
     * 实例化Configuration类和FileSystem类对象
     */
    @Before
    public void init() throws IOException {
        configuration = new Configuration(true) ;
        fileSystem = FileSystem.get(configuration);
    }

    /**
     * 关闭fileSystem对象
     */
    @After
    public void close() throws IOException {
        if(fileSystem != null){
            fileSystem.close();
        }
    }
    /**获取指定目录下的自己文件或文件夹的状态（并不是所有“后代”）
     */
    @Test
    public void getFileStatus(){
        try {
            FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));
            for(FileStatus fileStatus:fileStatuses){
                //System.out.println("path:"+fileStatus.getPath()+",Length:"+fileStatus.getLen()+",BlockSize:"+fileStatus.getBlockSize());
                System.out.println(fileStatus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**获取指定目录下的所有后代的文件或文件夹信息
     */
    public void getFileStatusDG(String path){
        try {
            FileStatus[] fileStatuses = fileSystem.listStatus(new Path(path));
            for(FileStatus fileStatus:fileStatuses){
                //System.out.println("path:"+fileStatus.getPath()+",Length:"+fileStatus.getLen()+",BlockSize:"+fileStatus.getBlockSize());
                if(fileStatus.isDirectory()){
                    System.out.println(fileStatus);
                    getFileStatusDG("/"+fileStatus.getPath().getName());
                }else{
                    System.out.println(fileStatus);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建目录会出现permission denied 用户权限不够，此时在执行时vm options 中添加 -DHADOOP_USER_NAME=root
     */
    @Test
    public void testGetFileStatusDG(){
        getFileStatusDG("/");
    }
    @Test
    public void mkdirs(){
        try {
            boolean flag = fileSystem.mkdirs(new Path("/a/b/c"));
            System.out.println(flag?"目录创建成功!":"目录创建失败");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**删除
     * boolean delete(Path f, boolean recursive)
     *   f:被删除文件或目录的Path对象
     *   recursive：false 被删除为目录时，只能删除空目录。如果删除非空目录则抛出异常，并删除失败
     *         true：被删除的为目录时，无论是否有子级，则都能够删除成功！
     */
    @Test
    public void delete(){
        boolean flag = false;
        try {
            flag = fileSystem.delete(new Path("/a"), true);
            System.out.println(flag?"删除成功!":"删除失败");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 权限不够，同上
     */
    @Test
    public void uploadFile1() throws IOException {
        //调用fileSystem的create(new Path("全路径名称"))
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/wordcount.txt"));
        //创建本地文件的输入流对象
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\hh.txt"));
        //定义缓存数组
        byte[] datas = new byte[1024];
        int len = -1;
        while((len = fileInputStream.read(datas))!= -1){
            fsDataOutputStream.write(datas,0,len);
        }
        //关闭流
        fileInputStream.close();
        fsDataOutputStream.close();
    }
    @Test
    public void uploadFile2() throws IOException {
        //调用fileSystem的create(new Path("全路径名称"))
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/wordcount.txt"));
        //创建本地文件的输入流对象
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\hh.txt"));

        IOUtils.copyBytes(fileInputStream,fsDataOutputStream,1024,true);
    }

    /**
     * 文件下载测试
     */
    @Test
    public void downLoad() throws IOException {
        FSDataInputStream fsDataInputStream = fileSystem.open(new Path("/wordcount.txt"));
        FileOutputStream fos = new FileOutputStream(new File("D:/wordcount.txt"));
        IOUtils.copyBytes(fsDataInputStream,fos,1024,true);
    }
    //获取文件块信息
    @Test
    public void getFileStatusLocation() throws IOException {
        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/wordcount.txt"));
        BlockLocation[] fileBlockLocations = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        for(BlockLocation fileBlockLocation:fileBlockLocations){
            System.out.println(fileBlockLocation);
        }
    }

}
