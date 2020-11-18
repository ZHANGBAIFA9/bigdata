package com.myjava.net.tcp;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Description:
 */
public class TCPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket socket = new ServerSocket(5555);
        Socket accept = socket.accept();

        InputStream is = accept.getInputStream();
        byte[] bys = new byte[1024];
        int len = is.read(bys);
        System.out.println(new String(bys,0,len));
        socket.close();
        is.close();
    }
}
