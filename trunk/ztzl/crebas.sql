/*==============================================================*/
/* DBMS name:      ORACLE Version 9i2                           */
/* Created on:     2009/2/20 14:51:10                           */
/*==============================================================*/


DROP TABLE ERRORTYPE CASCADE CONSTRAINTS;

DROP TABLE OPERATORROLE CASCADE CONSTRAINTS;

DROP TABLE OPERATORS CASCADE CONSTRAINTS;

DROP TABLE PRIS CASCADE CONSTRAINTS;

DROP TABLE PRODUCTS CASCADE CONSTRAINTS;

DROP TABLE PRODUCTTYPE CASCADE CONSTRAINTS;

DROP TABLE PROENG CASCADE CONSTRAINTS;

DROP TABLE PROFLOW CASCADE CONSTRAINTS;

DROP TABLE PROREPAIR CASCADE CONSTRAINTS;

DROP TABLE ROLEPRI CASCADE CONSTRAINTS;

DROP TABLE ROLES CASCADE CONSTRAINTS;

/*==============================================================*/
/* Table: ERRORTYPE                                             */
/*==============================================================*/
CREATE TABLE ERRORTYPE  (
   ID                   VARCHAR2(20)                    NOT NULL,
   ERRORVIEW            VARCHAR2(50)                    NOT NULL,
   ERROTYPE             VARCHAR2(30),
   ERRORINFO            VARCHAR2(100),
   INUSE                VARCHAR2(3)                    DEFAULT '1',
   CONSTRAINT PK_ERRORTYPE PRIMARY KEY (ID)
);

COMMENT ON TABLE ERRORTYPE IS
'产品故障类别表';

COMMENT ON COLUMN ERRORTYPE.ID IS
'类别ＩＤ';

COMMENT ON COLUMN ERRORTYPE.ERRORVIEW IS
'故障现象　';

COMMENT ON COLUMN ERRORTYPE.ERROTYPE IS
'故障大类';

COMMENT ON COLUMN ERRORTYPE.ERRORINFO IS
'故障说明　';

COMMENT ON COLUMN ERRORTYPE.INUSE IS
'是否有效';

/*==============================================================*/
/* Table: OPERATORROLE                                          */
/*==============================================================*/
CREATE TABLE OPERATORROLE  (
   ID                   INTEGER                         NOT NULL,
   USERID               INTEGER,
   ROLEID               INTEGER,
   CONSTRAINT PK_OPERATORROLE PRIMARY KEY (ID)
);

COMMENT ON TABLE OPERATORROLE IS
'用户角色对应表';

COMMENT ON COLUMN OPERATORROLE.ID IS
'用户角色对应ID';

COMMENT ON COLUMN OPERATORROLE.USERID IS
'用户ID ';

COMMENT ON COLUMN OPERATORROLE.ROLEID IS
'角色ID';

/*==============================================================*/
/* Table: OPERATORS                                             */
/*==============================================================*/
CREATE TABLE OPERATORS  (
   ID                   INTEGER                         NOT NULL,
   USERNAME             VARCHAR2(20)                    NOT NULL,
   USERPASS             VARCHAR2(50),
   MEMO                 VARCHAR2(100),
   CONSTRAINT PK_OPERATORS PRIMARY KEY (ID)
);

COMMENT ON TABLE OPERATORS IS
'操作员表';

COMMENT ON COLUMN OPERATORS.ID IS
'用户ID';

COMMENT ON COLUMN OPERATORS.USERNAME IS
'用户名 ';

COMMENT ON COLUMN OPERATORS.USERPASS IS
'用户密码';

COMMENT ON COLUMN OPERATORS.MEMO IS
'用户信息';

/*==============================================================*/
/* Table: PRIS                                                  */
/*==============================================================*/
CREATE TABLE PRIS  (
   ID                   INTEGER                         NOT NULL,
   PRINAME              VARCHAR2(30)                    NOT NULL,
   PRIGRADE             VARCHAR2(3),
   OPENTYPE             VARCHAR2(3),
   PRIURL               VARCHAR2(50),
   UPPRIID              INTEGER,
   PRIINFO              VARCHAR2(100),
   CONSTRAINT PK_PRIS PRIMARY KEY (ID)
);

COMMENT ON TABLE PRIS IS
'权限表';

COMMENT ON COLUMN PRIS.ID IS
'权限ID';

COMMENT ON COLUMN PRIS.PRINAME IS
'权限名';

COMMENT ON COLUMN PRIS.PRIGRADE IS
'权限级别';

COMMENT ON COLUMN PRIS.OPENTYPE IS
'打开方式';

COMMENT ON COLUMN PRIS.PRIURL IS
'权限URL';

COMMENT ON COLUMN PRIS.UPPRIID IS
'上级权限 ';

COMMENT ON COLUMN PRIS.PRIINFO IS
'权限信息';

/*==============================================================*/
/* Table: PRODUCTS                                              */
/*==============================================================*/
CREATE TABLE PRODUCTS  (
   ID                   INTEGER                         NOT NULL,
   PROCODE              VARCHAR2(20)                    NOT NULL,
   PRODATE              DATE                           DEFAULT SYSDATE,
   BARCODE              VARCHAR2(20),
   LOTNUM               VARCHAR2(20),
   BOXCODE              VARCHAR2(20),
   TYPEID               INTEGER,
   PROVERSION           VARCHAR2(20),
   PROMEMO              VARCHAR2(100),
   VIRTUALMARK          VARCHAR2(3)                    DEFAULT '0',
   REPAIRNUM            INTEGER                        DEFAULT 0,
   REJECTMARK           VARCHAR2(3)                    DEFAULT '0',
   PROSTATUS            VARCHAR2(3)                    DEFAULT '0',
   CONSTRAINT PK_PRODUCTS PRIMARY KEY (ID)
);

COMMENT ON TABLE PRODUCTS IS
'产品基本信息表';

COMMENT ON COLUMN PRODUCTS.ID IS
'产品ID';

COMMENT ON COLUMN PRODUCTS.PROCODE IS
'资产编码 ';

COMMENT ON COLUMN PRODUCTS.PRODATE IS
'生产日期';

COMMENT ON COLUMN PRODUCTS.BARCODE IS
'产品条码 ';

COMMENT ON COLUMN PRODUCTS.LOTNUM IS
'产品生产批号';

COMMENT ON COLUMN PRODUCTS.BOXCODE IS
'箱号';

COMMENT ON COLUMN PRODUCTS.TYPEID IS
'产品类别编码';

COMMENT ON COLUMN PRODUCTS.PROVERSION IS
'程序版本';

COMMENT ON COLUMN PRODUCTS.PROMEMO IS
'备注信息';

COMMENT ON COLUMN PRODUCTS.VIRTUALMARK IS
'虚拟标志';

COMMENT ON COLUMN PRODUCTS.REPAIRNUM IS
'返修次数';

COMMENT ON COLUMN PRODUCTS.REJECTMARK IS
'报废标志';

COMMENT ON COLUMN PRODUCTS.PROSTATUS IS
'产品状态';

/*==============================================================*/
/* Table: PRODUCTTYPE                                           */
/*==============================================================*/
CREATE TABLE PRODUCTTYPE  (
   ID                   INTEGER                         NOT NULL,
   TYPENAME             VARCHAR(30)                     NOT NULL,
   FUNCTYPE             VARCHAR2(30),
   TYPEINFO             VARCHAR2(100),
   INUSE                VARCHAR2(3)                    DEFAULT '1',
   CONSTRAINT PK_PRODUCTTYPE PRIMARY KEY (ID)
);

COMMENT ON TABLE PRODUCTTYPE IS
'producttype';

COMMENT ON COLUMN PRODUCTTYPE.ID IS
'类别ID';

COMMENT ON COLUMN PRODUCTTYPE.TYPENAME IS
'类别名称';

COMMENT ON COLUMN PRODUCTTYPE.FUNCTYPE IS
'功能类别';

COMMENT ON COLUMN PRODUCTTYPE.TYPEINFO IS
'类别说明';

COMMENT ON COLUMN PRODUCTTYPE.INUSE IS
'是否使用';

/*==============================================================*/
/* Table: PROENG                                                */
/*==============================================================*/
CREATE TABLE PROENG  (
   ID                   INTEGER                         NOT NULL,
   PROCODE              VARCHAR2(20)                    NOT NULL,
   PRONAME              VARCHAR2(100),
   TYPEID               INTEGER,
   PRODATE              DATE,
   ENGAREA              VARCHAR2(100),
   AREANAME             VARCHAR2(100),
   ENGADDR              VARCHAR2(200),
   PRINCIPLE            VARCHAR2(50),
   RUNSTATUS            VARCHAR2(3)                    DEFAULT '1',
   INUSE                VARCHAR2(3)                    DEFAULT '1',
   CONSTRAINT PK_PROENG PRIMARY KEY (ID)
);

COMMENT ON TABLE PROENG IS
'产品安装信息';

COMMENT ON COLUMN PROENG.ID IS
'安装ＩＤ';

COMMENT ON COLUMN PROENG.PROCODE IS
'产品编码';

COMMENT ON COLUMN PROENG.PRONAME IS
'产品名称';

COMMENT ON COLUMN PROENG.TYPEID IS
'产品类别';

COMMENT ON COLUMN PROENG.PRODATE IS
'安装时间';

COMMENT ON COLUMN PROENG.ENGAREA IS
'安装地区';

COMMENT ON COLUMN PROENG.AREANAME IS
'小区名称';

COMMENT ON COLUMN PROENG.ENGADDR IS
'安装地址';

COMMENT ON COLUMN PROENG.PRINCIPLE IS
'工程负责人';

COMMENT ON COLUMN PROENG.RUNSTATUS IS
'运行状态　';

COMMENT ON COLUMN PROENG.INUSE IS
'有效标志';

/*==============================================================*/
/* Table: PROFLOW                                               */
/*==============================================================*/
CREATE TABLE PROFLOW  (
   ID                   INTEGER                         NOT NULL,
   PROCODE              VARCHAR2(20)                    NOT NULL,
   TYPEID               INTEGER,
   CURWARE              VARCHAR2(3),
   PROSOURCE            VARCHAR2(3),
   SOURCEOPER           VARCHAR2(20),
   SOURCEDATE           DATE                           DEFAULT SYSDATE,
   SENDINFO             VARCHAR2(100),
   SENDTO               VARCHAR2(3),
   SENDAREA             VARCHAR2(100),
   RECEIVER             VARCHAR2(20),
   RECEIVEDATE          DATE                           DEFAULT SYSDATE,
   RECEIVEINFO          VARCHAR2(100),
   OVERMARK             VARCHAR2(3)                    DEFAULT '0',
   RETURNMARK           VARCHAR2(3)                    DEFAULT '0',
   CONSTRAINT PK_PROFLOW PRIMARY KEY (ID)
);

COMMENT ON TABLE PROFLOW IS
'产品流程信息表';

COMMENT ON COLUMN PROFLOW.ID IS
'流程ID';

COMMENT ON COLUMN PROFLOW.PROCODE IS
'产品编码';

COMMENT ON COLUMN PROFLOW.TYPEID IS
'产品类别';

COMMENT ON COLUMN PROFLOW.CURWARE IS
'当前位置';

COMMENT ON COLUMN PROFLOW.PROSOURCE IS
'产品来源';

COMMENT ON COLUMN PROFLOW.SOURCEOPER IS
'发送人';

COMMENT ON COLUMN PROFLOW.SOURCEDATE IS
'发送日期';

COMMENT ON COLUMN PROFLOW.SENDINFO IS
'发送信息';

COMMENT ON COLUMN PROFLOW.SENDTO IS
'发送到';

COMMENT ON COLUMN PROFLOW.SENDAREA IS
'发送地区';

COMMENT ON COLUMN PROFLOW.RECEIVER IS
'接收人';

COMMENT ON COLUMN PROFLOW.RECEIVEDATE IS
'接收日期';

COMMENT ON COLUMN PROFLOW.RECEIVEINFO IS
'接收信息';

COMMENT ON COLUMN PROFLOW.OVERMARK IS
'结束标志';

COMMENT ON COLUMN PROFLOW.RETURNMARK IS
'退回标志';

/*==============================================================*/
/* Table: PROREPAIR                                             */
/*==============================================================*/
CREATE TABLE PROREPAIR  (
   ID                   VARCHAR2(20)                    NOT NULL,
   PROCODE              VARCHAR2(20)                    NOT NULL,
   TYPEID               VARCHAR2(20),
   ERRORVIEW            VARCHAR2(100),
   SENDDATE             DATE                           DEFAULT SYSDATE,
   CHANGEDATE           DATE                           DEFAULT SYSDATE,
   ERRORFOR             VARCHAR2(100),
   CHANGECODE           VARCHAR2(20),
   WAREMARK             VARCHAR2(3),
   INUSE                VARCHAR2(3)                    DEFAULT '1',
   CONSTRAINT PK_PROREPAIR PRIMARY KEY (ID)
);

COMMENT ON TABLE PROREPAIR IS
'产品返修信息表';

COMMENT ON COLUMN PROREPAIR.ID IS
'产品返修ＩＤ';

COMMENT ON COLUMN PROREPAIR.PROCODE IS
'资产编码';

COMMENT ON COLUMN PROREPAIR.TYPEID IS
'产品类别';

COMMENT ON COLUMN PROREPAIR.ERRORVIEW IS
'故障现象';

COMMENT ON COLUMN PROREPAIR.SENDDATE IS
'送修时间　';

COMMENT ON COLUMN PROREPAIR.CHANGEDATE IS
'更换时间　';

COMMENT ON COLUMN PROREPAIR.ERRORFOR IS
'故障原因';

COMMENT ON COLUMN PROREPAIR.CHANGECODE IS
'更换产品编码　';

COMMENT ON COLUMN PROREPAIR.WAREMARK IS
'入库标志';

COMMENT ON COLUMN PROREPAIR.INUSE IS
'有效标志';

/*==============================================================*/
/* Table: ROLEPRI                                               */
/*==============================================================*/
CREATE TABLE ROLEPRI  (
   ID                   INTEGER                         NOT NULL,
   ROLEID               INTEGER,
   PRIID                INTEGER,
   CONSTRAINT PK_ROLEPRI PRIMARY KEY (ID)
);

COMMENT ON TABLE ROLEPRI IS
'角色权限对应表';

COMMENT ON COLUMN ROLEPRI.ID IS
' 角色权限对应ID';

COMMENT ON COLUMN ROLEPRI.ROLEID IS
'角色ID';

COMMENT ON COLUMN ROLEPRI.PRIID IS
'权限ID';

/*==============================================================*/
/* Table: ROLES                                                 */
/*==============================================================*/
CREATE TABLE ROLES  (
   ID                   INTEGER                         NOT NULL,
   ROLENAME             VARCHAR2(20)                    NOT NULL,
   ROLEINFO             VARCHAR2(100),
   ROLEMEMO             VARCHAR2(100),
   CONSTRAINT PK_ROLES PRIMARY KEY (ID)
);

COMMENT ON TABLE ROLES IS
'角色表';

COMMENT ON COLUMN ROLES.ID IS
'角色ID';

COMMENT ON COLUMN ROLES.ROLENAME IS
'角色名';

COMMENT ON COLUMN ROLES.ROLEINFO IS
'角色信息';

COMMENT ON COLUMN ROLES.ROLEMEMO IS
'备注信息';

