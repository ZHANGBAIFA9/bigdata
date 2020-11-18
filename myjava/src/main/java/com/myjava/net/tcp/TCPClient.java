package com.myjava.net.tcp;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Description:
 */
public class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(InetAddress.getByName("localhost"),5555);
        OutputStream os = socket.getOutputStream();
        os.write("helloworld".getBytes());
        socket.close();
        os.close();
    }
}
