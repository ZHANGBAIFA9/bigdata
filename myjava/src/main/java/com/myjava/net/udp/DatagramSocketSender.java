package com.myjava.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Description:
 */
public class DatagramSocketSender {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        DatagramSocket sock = new DatagramSocket();
        //
        while(true){
            System.out.print("input a string:");
            String line = sc.nextLine();
            byte[] bys = line.getBytes();
            DatagramPacket packet = new DatagramPacket(bys, bys.length, InetAddress.getByName("localhost"), 4444);
            sock.send(packet);
            if(line.equals("886")){
                break;
            }
        }
        //
        sock.close();
    }
}
