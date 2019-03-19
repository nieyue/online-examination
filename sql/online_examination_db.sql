# 数据库 
#创建数据库
DROP DATABASE IF EXISTS online_examination_db;
CREATE DATABASE online_examination_db;

#使用数据库 
use online_examination_db;
#创建角色表
CREATE TABLE role_tb(
role_id int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
name varchar(255) COMMENT '角色名',
duty varchar(255) COMMENT '角色职责',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (role_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='角色表';

#创建账户表
CREATE TABLE account_tb(
account_id int(11) NOT NULL AUTO_INCREMENT COMMENT '账户id',
realname varchar(255) COMMENT '姓名',
sid varchar(255) COMMENT '学号',
sex tinyint(4) DEFAULT 0 COMMENT '性别,默认为0未知，为1男性，为2女性',
nation varchar(255) COMMENT '民族',
identity_cards varchar(255) COMMENT '身份证',
phone varchar(255) COMMENT '联系电话',
password varchar(255) COMMENT '密码',
icon varchar(255) COMMENT '头像',
birth_date datetime COMMENT '出生年月日',
create_date datetime COMMENT '创建时间',
login_date datetime COMMENT '登陆时间',
status tinyint DEFAULT 0 COMMENT '状态，默认0正常，1封禁，2异常',
role_id int(11) COMMENT '角色id外键',
PRIMARY KEY (account_id),
INDEX INDEX_REALNAME (realname) USING BTREE,
INDEX INDEX_ROLEID (role_id) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='账户表';

#创建选择题表
CREATE TABLE choice_tb(
choice_id int(11) NOT NULL AUTO_INCREMENT COMMENT '选择题id',
score decimal(12,2) COMMENT '分数',
question varchar(255) COMMENT '问题',
a varchar(255) COMMENT 'a',
b varchar(255) COMMENT 'b',
c varchar(255) COMMENT 'c',
d varchar(255) COMMENT 'd',
correct tinyint(4) COMMENT '正确的，1,2,3,4分别对应a,b,c,d',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
reading_id int(11) COMMENT '阅读理解id外键',
PRIMARY KEY (choice_id),
INDEX INDEX_READINGID (reading_id) USING BTREE,
INDEX INDEX_CORRECT (correct) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='选择题表';

#创建阅读理解表
CREATE TABLE reading_tb(
reading_id int(11) NOT NULL AUTO_INCREMENT COMMENT '阅读理解id',
content varchar(20000) COMMENT '内容',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (reading_id)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='阅读理解表';

#创建考试成绩表
CREATE TABLE result_tb(
result_id int(11) NOT NULL AUTO_INCREMENT COMMENT '考试成绩id',
score decimal(12,2) COMMENT '分数',
start_date datetime COMMENT '开始时间',
end_date datetime COMMENT '结束时间',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
account_id int(11) COMMENT '账户id外键',
PRIMARY KEY (result_id),
INDEX INDEX_ACCOUNTID (account_id) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='考试成绩表';

#创建选择题成绩表
CREATE TABLE result_choice_tb(
result_choice_id int(11) NOT NULL AUTO_INCREMENT COMMENT '选择题成绩id',
score decimal(12,2) COMMENT '分数',
question varchar(255) COMMENT '问题',
a varchar(255) COMMENT 'a',
b varchar(255) COMMENT 'b',
c varchar(255) COMMENT 'c',
d varchar(255) COMMENT 'd',
correct tinyint(4) COMMENT '正确的，1,2,3,4分别对应a,b,c,d',
target tinyint(4) COMMENT '选择的，1,2,3,4分别对应a,b,c,d',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
result_reading_id int(11) COMMENT '阅读理解成绩id外键',
result_id int(11) COMMENT '考试成绩id外键',
PRIMARY KEY (result_choice_id),
INDEX INDEX_RESULTREADINGID (result_reading_id) USING BTREE,
INDEX INDEX_RESULTID (result_id) USING BTREE,
INDEX INDEX_CORRECT (correct) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='阅读理解成绩表';

#创建阅读理解成绩表
CREATE TABLE result_reading_tb(
result_reading_id int(11) NOT NULL AUTO_INCREMENT COMMENT '阅读理解成绩id',
content varchar(20000) COMMENT '内容',
create_date datetime COMMENT '创建时间',
update_date datetime COMMENT '更新时间',
result_id int(11) COMMENT '考试成绩id外键',
PRIMARY KEY (result_reading_id),
INDEX INDEX_RESULTID (result_id) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='阅读理解成绩表';

#设置初始角色
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date)
VALUES (1000,"超级管理员","超级管理员",now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date)
VALUES (1001,"学生","学生",now());

#设置初始管理员密码MD5加密123456
INSERT IGNORE INTO account_tb (account_id,realname,sid,phone,password,create_date,login_date,role_id)
VALUES (1000,"超级管理员","1000","1000","11874bb6149dd45428da628c9766b252",now(),now(),1000);