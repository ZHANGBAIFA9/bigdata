create table if not exists person(
id int comment 'person标识' ,
name string comment '姓名' ,
likes array<string> comment '喜好',
address map<string,string> comment '住址' 
)comment '人员基本信息表'
row format delimited 
fields terminated by ","
collection items terminated by "-"
map keys terminated by ":"
lines terminated by "\n" ;

-- 创建外部表
create external table if not exists person2(
id int comment 'person标识' ,
name string comment '姓名' ,
likes array<string> comment '喜好',
address map<string,string> comment '住址' 
)comment '人员基本信息表2'
row format delimited
fields terminated by ','
collection items terminated by '-'
map keys terminated by ':'
location '/usr/';

-- 创建单分区表
create table if not exists day_table(
id int , 
content string
) comment '单分区表'
partitioned by (dt string) ;

-- 创建双分区表
create table if not exists day_table2(
id int ,
content string 
)comment '双分区表'
partitioned by (dt string , hours string) ;

-- 创建人员基本信息分区表
create table if not exists person5(
id int comment 'person标识' ,
name string comment '姓名' ,
likes array<string> comment '喜好',
address map<string,string> comment '住址' 
)comment '人员基本信息分区表'
partitioned by(age int)
row format delimited 
fields terminated by ','
collection items terminated by '-'
map keys terminated by ':'
lines terminated by "\n" ;


