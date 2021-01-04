# MAP之HashMap深入理解

### 0、集合框架概览

![1609204542786](E:\workSpace\bigdata\typora-user-images\1609204542786.png)

### 1、HashMap组成

​	Java1.7底层实现**数组+链表**，Java1.8底层实现**数组+链表+红黑树**

##### 	jdk1.7：（头插法）

​		使用一个Entry数组来存储数据，用key的hashcode取模来决定key会被放到数组里的位置，如果hashcode相同，或者hashcode取模后的结果相同，那么这些key会被定位到Entry数组的同一个格子里，这些key会形成一个链表；

​		在hash函数特别差的情况下，比如说所有key的hashcode都相同，这个链表可能会很长，那么put/get操作都可能需要遍历这个链表，也就是最差情况下时间复杂度为O（n）。

##### 	jdk1.8：（尾插法）

​			

