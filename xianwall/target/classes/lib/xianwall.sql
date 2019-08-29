/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/8/20 18:09:39                           */
/*==============================================================*/


drop table if exists CardUseRecordInfo;

drop table if exists Orders;

drop table if exists Role;

drop table if exists UserCardInfo;

drop table if exists UserInfo;

drop table if exists UserInfoCheck;

/*==============================================================*/
/* Table: CardUseRecordInfo                                     */
/*==============================================================*/
create table CardUseRecordInfo
(
   ID                   int(10) not null auto_increment,
   CardId               int(10),
   UseTime              datetime,
   primary key (ID)
);

alter table CardUseRecordInfo comment '惠民卡使用记录';

alter table CardUseRecordInfo modify column ID int(10) comment 'ID';

alter table CardUseRecordInfo modify column CardId int(10) comment '卡ID';

alter table CardUseRecordInfo modify column UseTime datetime comment '使用时间';

/*==============================================================*/
/* Table: Orders                                                */
/*==============================================================*/
create table Orders
(
   ID                   int(10) not null auto_increment,
   user_id              int(10),
   card_id              int(10),
   pay_type             varchar(3),
   merchant_no          varchar(15),
   terminal_id          varchar(8),
   terminal_trace       varchar(32),
   terminal_time        varchar(32),
   auth_no              varchar(128),
   total_fee            decimal(12,2),
   order_body           varchar(128),
   return_code          varchar(2),
   return_msg           varchar(128),
   result_code          varchar(2),
   end_time             varchar(32),
   out_trade_no         varchar(32),
   CreatedTime          datetime,
   UpdatedTime          datetime,
   notify_url           varchar(500),
   Addition1            varchar(240),
   Addition2            varchar(240),
   Addition3            varchar(240),
   primary key (ID)
);

alter table Orders comment '订单表';

alter table Orders modify column ID int(10) comment 'ID';

alter table Orders modify column user_id int(10) comment '用户id';

alter table Orders modify column card_id int(10) comment '惠民卡id';

alter table Orders modify column pay_type varchar(3) comment '支付类型"010"';

alter table Orders modify column merchant_no varchar(15) comment '商户号';

alter table Orders modify column terminal_id varchar(8) comment '终端号';

alter table Orders modify column terminal_trace varchar(32) comment '终端交易流水';

alter table Orders modify column terminal_time varchar(32) comment '终端交易时间，格式：yyyyMMddHHmmss';

alter table Orders modify column auth_no varchar(128) comment '授权码';

alter table Orders modify column total_fee decimal(12,2) comment '金额，单位分';

alter table Orders modify column order_body varchar(128) comment '订单描述';

alter table Orders modify column return_code varchar(2) comment '返回码';

alter table Orders modify column return_msg varchar(128) comment '返回信息';

alter table Orders modify column result_code varchar(2) comment '业务结果';

alter table Orders modify column end_time varchar(32) comment '支付完成时间，格式：yyyyMMddHHmmss';

alter table Orders modify column out_trade_no varchar(32) comment '唯一订单号';

alter table Orders modify column CreatedTime datetime comment '创建时间';

alter table Orders modify column UpdatedTime datetime comment '最后修改时间';

alter table Orders modify column notify_url VARCHAR(500) comment '回调地址';

alter table Orders modify column Addition1 varchar(240) comment '备用字段1';

alter table Orders modify column Addition2 varchar(240) comment '备用字段2';

alter table Orders modify column Addition3 varchar(240) comment '备用字段3';

/*==============================================================*/
/* Table: Role                                                  */
/*==============================================================*/
create table Role
(
   ID                   int(10) not null auto_increment,
   Role                 varchar(30),
   Description          varchar(100),
   Remarks              varchar(100),
   CreatedTime          datetime,
   UpdatedTime          datetime,
   primary key (ID)
);

alter table Role comment '角色表';

alter table Role modify column ID int(10) comment 'ID';

alter table Role modify column Role varchar(30) comment '角色名称';

alter table Role modify column Description varchar(100) comment '角色描述';

alter table Role modify column Remarks varchar(100) comment '备注';

alter table Role modify column CreatedTime datetime comment '创建时间';

alter table Role modify column UpdatedTime datetime comment '最后修改时间';

/*==============================================================*/
/* Table: UserCardInfo                                          */
/*==============================================================*/
create table UserCardInfo
(
   ID                   int(10) not null auto_increment,
   CardName             int(10),
   UserId               int(10),
   FirstUseTime         datetime,
   CreatedTime          datetime,
   InvalidTime          datetime,
   Num                  int(10),
   CardType             varchar(10),
   Addition1            varchar(240),
   Addition2            varchar(240),
   Addition3            varchar(240),
   primary key (ID)
);

alter table UserCardInfo comment '用户惠民卡信息';

alter table UserCardInfo modify column ID int(10) comment 'ID';

alter table UserCardInfo modify column CardName int(10) comment '卡名称';

alter table UserCardInfo modify column UserId  int(10) comment '所属人Id';

alter table UserCardInfo modify column FirstUseTime datetime comment '首次使用时间（激活时间）';

alter table UserCardInfo modify column CreatedTime datetime comment '创建时间';

alter table UserCardInfo modify column InvalidTime datetime comment '失效时间';

alter table UserCardInfo modify column Num int(10) comment '累计使用统计';

alter table UserCardInfo modify column CardType varchar(10) comment '惠民卡类型';

alter table UserCardInfo modify column Addition1 varchar(240) comment '备用字段1';

alter table UserCardInfo modify column Addition2 varchar(240) comment '备用字段2';

alter table UserCardInfo modify column Addition3 varchar(240) comment '备用字段3';

/*==============================================================*/
/* Table: UserInfo                                              */
/*==============================================================*/
create table UserInfo
(
   ID int(10) not null auto_increment,
   username             varchar(60),
   realname             varchar(60),
   userage              varchar(15),
   gender               int(10),
   usertel              varchar(15),
   idcard               varchar(20),
   userrole             varchar(30),
   createdtime          datetime,
   updatedtime          datetime,
   openId               varchar(240),
   addition1            varchar(240),
   addition2            varchar(240),
   addition3            varchar(240),
   primary key (ID)
);

alter table UserInfo comment '用户信息表';

alter table UserInfo modify column ID  int(10) comment '用户ID';

alter table UserInfo modify column username varchar(60) comment '登录名';

alter table UserInfo modify column realname varchar(60) comment '用户真实姓名';

alter table UserInfo modify column userage varchar(15) comment '出生年月';

alter table UserInfo modify column gender int(10) comment '性别';

alter table UserInfo modify column usertel varchar(15) comment '联系方式';

alter table UserInfo modify column idcard varchar(20) comment '身份证号';

alter table UserInfo modify column userrole varchar(30) comment '用户角色';

alter table UserInfo modify column createdtime datetime comment '注册时间';

alter table UserInfo modify column updatedtime datetime comment '修改时间';

alter table UserInfo modify column openId datetime comment '微信openId';

alter table UserInfo modify column addition1 varchar(240) comment '备用字段1';

alter table UserInfo modify column addition2 varchar(240) comment '备用字段2';

alter table UserInfo modify column addition3 varchar(240) comment '备用字段3';

/*==============================================================*/
/* Table: UserInfoCheck                                         */
/*==============================================================*/
create table UserInfoCheck
(
   ID                   int(10) not null auto_increment,
   UserId               int(10),
   CreatedTime          datetime,
   IdCardFrontDoc       varchar(500),
   IdCardBackDoc        varchar(500),
   AttachDoc            varchar(500),
   CheckUser            varchar(10),
   CheckTime            datetime,
   AttachType           varchar(10),
   Status               char(1),
   Remarks              varchar(200),
   UpdatedBy            int(10),
   UpdatedTime          datetime,
   Addition1            varchar(240),
   Addition2            varchar(240),
   Addition3            varchar(240),
   primary key (ID)
);

alter table UserInfoCheck comment '用户信息审核表';

alter table UserInfoCheck modify column ID int(10) comment 'ID';

alter table UserInfoCheck modify column UserId int(10) comment '用户id';

alter table UserInfoCheck modify column CreatedTime  datetime comment '提交时间';

alter table UserInfoCheck modify column IdCardFrontDoc varchar(500) comment '省份证正面文件路径';

alter table UserInfoCheck modify column IdCardBackDoc varchar(500) comment '身份证背面文件路径';

alter table UserInfoCheck modify column AttachDoc varchar(500) comment '审核附件文件路径';

alter table UserInfoCheck modify column CheckUser varchar(10) comment '审核人';

alter table UserInfoCheck modify column CheckTime datetime comment '审核时间';

alter table UserInfoCheck modify column AttachType varchar(10) comment '附件类型';

alter table UserInfoCheck modify column Status char(1) comment '审核状态(0:待审核，1：审核通过，3：审核拒绝)';

alter table UserInfoCheck modify column Remarks varchar(200) comment '拒绝原因';

alter table UserInfoCheck modify column UpdatedBy int(10) comment '最后修改人';

alter table UserInfoCheck modify column UpdatedTime datetime comment '最后修改时间';

alter table UserInfoCheck modify column Addition1 varchar(240) comment '备用字段1';

alter table UserInfoCheck modify column Addition2 varchar(240) comment '备用字段2';

alter table UserInfoCheck modify column Addition3 varchar(240) comment '备用字段3';

