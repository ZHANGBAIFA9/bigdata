package com.myjava.net.udp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Description:
 */
public class Sender {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("E:\\tmp\\logs\\out.txt"));
        //
        DatagramSocket sock = new DatagramSocket();
        //
        String line = null;
        while((line = br.readLine()) != null){
            byte[] bys = line.getBytes();
            //
            DatagramPacket packet = new DatagramPacket(bys, bys.length, InetAddress.getByName("localhost"), 5555);
            //
            sock.send(packet);
        }
        //给接收端一个结束标记
        byte[] bys = "886".getBytes();
        //
        DatagramPacket packet = new DatagramPacket(bys, bys.length, InetAddress.getByName("localhost"), 5555);
        //
        sock.send(packet);
        //
        sock.close();
    }

}
