package com.myjava.net.udp;

import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Description:
 */
public class DatagramSocketReceiver {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(4444);

        //文件输出流
        FileWriter fw = new FileWriter("E:\\tmp\\logs\\out.txt");
        //
        byte[] bys = new byte[1024 * 64];
        DatagramPacket packet = new DatagramPacket(bys, bys.length);

        //
        while(true){
            socket.receive(packet);
            //解析数据
            byte[] data = packet.getData();
            int len = packet.getLength();
            //还原成字符串
            String s = new String(data,0,len);
            if(s.equals("886")){
                break;
            }else{
                fw.write(s);
                fw.write("\r\n");
            }
        }
        //
        socket.close();
        fw.close();
    }


}
