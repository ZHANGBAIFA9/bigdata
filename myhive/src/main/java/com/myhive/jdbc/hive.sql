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

-- 数据加载
load data local inpath '/root/data/person.txt' into table person5 ;



-- 创建基站表
create table if not exists jizhan(
record_time string comment '通话时间',
imei int comment '基站编号',
cell string comment '手机编号',
ph_num int comment '',
call_num int comment '',
drop_num int comment '掉话的秒数',
duration int comment '通话持续总秒数',
drop_rate double comment '',
net_type string comment '',
erl int  comment ''
)comment '基站基础信息表'
row format delimited fields terminated by ',' lines terminated by "\n";


-- 基站统计结果表
create table if not exists jizhan_result(
imei string comment "基站编号",
drop_num int comment "掉话总时长",
duration int comment "通话总时长",
drop_rate double comment "掉话率 drop_num除以 duration"
)comment '基站统计结果表';

-- 数据拉取
load data local inpath '/root/data/imei.csv' into table jizhan ;

-- 数据统计
insert into jizhan_result
select 
imei,
sum(drop_num) sdrop,
sum(duration ) sdura,
sum(drop_num)/sum(duration) drop_rate
from jizhan
group by imei
order by drop_rate desc limit 20;
-- 数据统计
from jizhan
insert overwrite table jizhan_result
select imei,sum(drop_num) sdrop,sum(duration ) sdura,
sum(drop_num)/sum(duration) drop_rate
group by imei
order by drop_rate desc;


-- word count 
create external table if not exists wc (
line string comment '单行数据'
)comment '数据外部表' location '/usr';

-- word count 结果表
create table if not exists wc_result(
word string comment '单词',
num int comment '数量'
)comment 'word count 结果表'
row format delimited fields terminated by ',' lines terminated by '\n' ;

-- 数据抽取
insert overwrite table wc_result
select 
ww.word as word,
count(1) as num
from 
(select explode(split(line , ' ')) word from wc)ww
group by ww.word ;


