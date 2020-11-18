网络分层:
OSI 七层模型:参考模型
TCP/IP网络模型:实际应用的模型

![1605621751538](C:\Users\ZHANGBAIFA\AppData\Roaming\Typora\typora-user-images\1605621751538.png)

IP地址的分类:

![1605621776821](C:\Users\ZHANGBAIFA\AppData\Roaming\Typora\typora-user-images\1605621776821.png)

注：网络地址越少,主机位越多,主机位越多,可以拥有的主机数量越多.

一般公司使用的是C类网址.校园网使用的是B类网址.A类网址往往是政府,国家使用.

# InetAddress类:

IP地址的表示形式.

如何获取InetAddress类:

public InetAddress getByName(String name):

通过传递IP地址的字符串表示形式或者是主机名获取IP地址对象.

 

常用方法:

通过IP地址获取主机名:并不是完全能解析成功,如果不成功,则返回IP地址的字符串表示形式.

String getHostName():

通过IP地址获取IP地址的字符串表示形式:

String getHostAddress():

 

# 网络编程三要素之端口:port

端口是用于表示主机上的运行的进程的编号:

范围是:0 - 65535

系统端口范围:0-1024

 

 

# 网络编程三要素之协议:

协议是主机间通信的规则.

常用的两种:

## UDP:无连接的协议.

数据的传输是依赖于数据报包自己决定的.

也就是在发送端发送的数据报包,需要自己封装目的地址和端口号.

数据报包有大小限制:64K.

例:发短信.

 

## TCP:有连接的协议.

需要三次握手过程.效率稍低.但是安全性,准确性高.

例:打电话.

 

DatagramSocket类:

发送和接收都是使用同样的类.

发送和接收的方法都是需要使用到DatagramPacket包做参数.

 

发送端:需要数据报包自己封装目的地IP地址和端口号.

接收端:不需要封装目的地IP地址和端口号,只是用来接收数据.