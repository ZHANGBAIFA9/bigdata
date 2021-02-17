# MapReduce简介

### 1、map 与reduce

​	map端主要做映射，reduce端做聚合，线性依赖关系,先执行完map,再执行reduce
​	源语：相同的key为一组，这一组数据调用一次reduce方法，方法内迭代计算这一组数据。

### 2、MapReduce全过程

​	**官方**：系统执行排序、将map输出作为输入传给reducer的过程称为Shuffle	。

![1613536600417](C:\Users\ZHANGBAIFA\AppData\Roaming\Typora\typora-user-images\1613536600417.png)





























​	



