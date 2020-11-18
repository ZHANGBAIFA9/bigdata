package com.myjava.net.udp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Description:
 */
public class Receiver {
    public static void main(String[] args) throws Exception {
        DatagramSocket sock = new DatagramSocket(5555);
        //
        BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\tmp\\logs\\out2.txt"));
        //
        byte[] bys = new byte[1024 * 64];
        DatagramPacket packet = new DatagramPacket(bys, bys.length);
        while(true){
            //
            sock.receive(packet);
            byte[] data = packet.getData();
            int len = packet.getLength();
            //
            String s = new String(data,0,len);
            if(s.equals("886")){
                break;
            }else{
                bw.write(s);
                bw.newLine();
                bw.flush();
            }
        }
        //
        sock.close();
        bw.close();
    }

}
