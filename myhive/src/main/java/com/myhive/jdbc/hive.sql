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
