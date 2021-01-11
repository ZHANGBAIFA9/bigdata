## Shuffle及优化

#### 1、shuffle是什么？从什么地方开始到什么地方结束？

​	**map方法之后，reduce方法之前，混洗的过程，shuffle是MapReduce的心脏，属于不断被优化和改进的代码库的一部分。**

​	Shuffle阶段是指从Map的输出开始，包括系统执行排序以及传送Map输出到Reduce作为输入的过程。Sort阶段是指对Map端输出的Key进行排序的过程。不同的Map可能输出相同的Key，相同的Key必须发送到同一个Reduce端处理。Shuffle阶段可以分为Map端的Shuffle和Reduce端的Shuffle。![1609823305499](/C:/Users/ZHANGBAIFA/AppData/Roaming/Typora/typora-user-images/1609823305499.png)

#### 2、Map段shuffle

​	map端的shuffle包括环形内存缓冲区执行溢出写，partition，sort，combiner，生成溢出写文件，合并

​	![1609823966521](C:\Users\ZHANGBAIFA\AppData\Roaming\Typora\typora-user-images\1609823966521.png)

​	Map端会处理输入数据并产生中间结果，这个中间结果会写到本地磁盘，**而不是HDFS**。**map函数开始产生输出时并非简单地将它输出到磁盘**。因为频繁的磁盘操作会导致性能严重下降。它的处理过程更复杂，数据首先写到内存中的一个缓冲区，并做一些预排序，以提升效率。

每个map任务都有一个环形内存缓冲区，用于存储任务的输出（默认大小100MB，mapreduce.task.io.sort.mb调整），被缓冲的K-V对记录已经被序列化，但没有排序。而且每个K-V对都附带一些额外的审计信息。
一旦缓冲内容达到**阈值**（mapreduce.map.io.sort.spill.percent,默认0.80，或者80%），就会创建一个溢出写文件，**同时开启一个后台线程把数据溢出写（spill）到本地磁盘中**。溢出写过程按轮询方式将缓冲区中的内容写到mapreduce.cluster.local.dir属性指定的目录中。

**在写磁盘之前，线程首先根据数据最终要传递到的Reducer把数据划分成相应的分区（partition）**，输出key会经过Partitioner分组或者分桶选择不同的reduce。默认的情况下Partitioner会对map输出的key进行hash取模，比如有6个ReduceTask，它就是模6，如果key的hash值为0，就选择第0个ReduceTask（为1，选Reduce Task1）。这样不同的map对相同单词key，它的hash值取模是一样的，所以会交给同一个reduce来处理。目的是将记录划分到不同的Reducer上去，以期望能够达到负载均衡，以后的Reducer就会根据partition来读取自己对应的数据。

**然后在每个分区中，后台线程将数据按Key进行排序（排序方式为快速排序）**。接着运行combiner在本地节点内存中将每个Map任务输出的中间结果按键做本地聚合(如果设置了的话)，可以减少传递给Reducer的数据量。可以通过setCombinerClass()方法来指定一个作业的combiner。当溢出写文件生成数至少为3时(mapreduce.map.combine.minspills属性设置)，combiner函数就会在它排序后的输出文件写到磁盘之前运行。

在写磁盘过程中，另外的20%内存可以继续写入数据，两种操作互不干扰，但如果在此期间缓冲区被填满，那么map就会阻塞写入内存的操作，让写入磁盘操作完成后再执行写入内存。
在map任务写完其最后一个输出记录之后，可能产生多个spill文件，**在每个Map任务完成前，溢出写文件被合并成一个索引文件和数据文件（多路归并排序）（Sort阶段）**。一次最多合并多少流由io.sort.factor控制，默认为10。至此，Map端的shuffle过程就结束了。

溢出写文件归并完毕后，Map将删除所有的临时溢出写文件，并告知NodeManager任务已完成，只要其中一个MapTask完成，ReduceTask就开始复制它的输出（Copy阶段分区输出文件通过http的方式提供给reducer）

#### 3、











​	