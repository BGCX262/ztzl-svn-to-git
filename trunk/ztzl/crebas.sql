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
'��Ʒ��������';

COMMENT ON COLUMN ERRORTYPE.ID IS
'���ɣ�';

COMMENT ON COLUMN ERRORTYPE.ERRORVIEW IS
'��������';

COMMENT ON COLUMN ERRORTYPE.ERROTYPE IS
'���ϴ���';

COMMENT ON COLUMN ERRORTYPE.ERRORINFO IS
'����˵����';

COMMENT ON COLUMN ERRORTYPE.INUSE IS
'�Ƿ���Ч';

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
'�û���ɫ��Ӧ��';

COMMENT ON COLUMN OPERATORROLE.ID IS
'�û���ɫ��ӦID';

COMMENT ON COLUMN OPERATORROLE.USERID IS
'�û�ID ';

COMMENT ON COLUMN OPERATORROLE.ROLEID IS
'��ɫID';

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
'����Ա��';

COMMENT ON COLUMN OPERATORS.ID IS
'�û�ID';

COMMENT ON COLUMN OPERATORS.USERNAME IS
'�û��� ';

COMMENT ON COLUMN OPERATORS.USERPASS IS
'�û�����';

COMMENT ON COLUMN OPERATORS.MEMO IS
'�û���Ϣ';

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
'Ȩ�ޱ�';

COMMENT ON COLUMN PRIS.ID IS
'Ȩ��ID';

COMMENT ON COLUMN PRIS.PRINAME IS
'Ȩ����';

COMMENT ON COLUMN PRIS.PRIGRADE IS
'Ȩ�޼���';

COMMENT ON COLUMN PRIS.OPENTYPE IS
'�򿪷�ʽ';

COMMENT ON COLUMN PRIS.PRIURL IS
'Ȩ��URL';

COMMENT ON COLUMN PRIS.UPPRIID IS
'�ϼ�Ȩ�� ';

COMMENT ON COLUMN PRIS.PRIINFO IS
'Ȩ����Ϣ';

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
'��Ʒ������Ϣ��';

COMMENT ON COLUMN PRODUCTS.ID IS
'��ƷID';

COMMENT ON COLUMN PRODUCTS.PROCODE IS
'�ʲ����� ';

COMMENT ON COLUMN PRODUCTS.PRODATE IS
'��������';

COMMENT ON COLUMN PRODUCTS.BARCODE IS
'��Ʒ���� ';

COMMENT ON COLUMN PRODUCTS.LOTNUM IS
'��Ʒ��������';

COMMENT ON COLUMN PRODUCTS.BOXCODE IS
'���';

COMMENT ON COLUMN PRODUCTS.TYPEID IS
'��Ʒ������';

COMMENT ON COLUMN PRODUCTS.PROVERSION IS
'����汾';

COMMENT ON COLUMN PRODUCTS.PROMEMO IS
'��ע��Ϣ';

COMMENT ON COLUMN PRODUCTS.VIRTUALMARK IS
'�����־';

COMMENT ON COLUMN PRODUCTS.REPAIRNUM IS
'���޴���';

COMMENT ON COLUMN PRODUCTS.REJECTMARK IS
'���ϱ�־';

COMMENT ON COLUMN PRODUCTS.PROSTATUS IS
'��Ʒ״̬';

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
'���ID';

COMMENT ON COLUMN PRODUCTTYPE.TYPENAME IS
'�������';

COMMENT ON COLUMN PRODUCTTYPE.FUNCTYPE IS
'�������';

COMMENT ON COLUMN PRODUCTTYPE.TYPEINFO IS
'���˵��';

COMMENT ON COLUMN PRODUCTTYPE.INUSE IS
'�Ƿ�ʹ��';

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
'��Ʒ��װ��Ϣ';

COMMENT ON COLUMN PROENG.ID IS
'��װ�ɣ�';

COMMENT ON COLUMN PROENG.PROCODE IS
'��Ʒ����';

COMMENT ON COLUMN PROENG.PRONAME IS
'��Ʒ����';

COMMENT ON COLUMN PROENG.TYPEID IS
'��Ʒ���';

COMMENT ON COLUMN PROENG.PRODATE IS
'��װʱ��';

COMMENT ON COLUMN PROENG.ENGAREA IS
'��װ����';

COMMENT ON COLUMN PROENG.AREANAME IS
'С������';

COMMENT ON COLUMN PROENG.ENGADDR IS
'��װ��ַ';

COMMENT ON COLUMN PROENG.PRINCIPLE IS
'���̸�����';

COMMENT ON COLUMN PROENG.RUNSTATUS IS
'����״̬��';

COMMENT ON COLUMN PROENG.INUSE IS
'��Ч��־';

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
'��Ʒ������Ϣ��';

COMMENT ON COLUMN PROFLOW.ID IS
'����ID';

COMMENT ON COLUMN PROFLOW.PROCODE IS
'��Ʒ����';

COMMENT ON COLUMN PROFLOW.TYPEID IS
'��Ʒ���';

COMMENT ON COLUMN PROFLOW.CURWARE IS
'��ǰλ��';

COMMENT ON COLUMN PROFLOW.PROSOURCE IS
'��Ʒ��Դ';

COMMENT ON COLUMN PROFLOW.SOURCEOPER IS
'������';

COMMENT ON COLUMN PROFLOW.SOURCEDATE IS
'��������';

COMMENT ON COLUMN PROFLOW.SENDINFO IS
'������Ϣ';

COMMENT ON COLUMN PROFLOW.SENDTO IS
'���͵�';

COMMENT ON COLUMN PROFLOW.SENDAREA IS
'���͵���';

COMMENT ON COLUMN PROFLOW.RECEIVER IS
'������';

COMMENT ON COLUMN PROFLOW.RECEIVEDATE IS
'��������';

COMMENT ON COLUMN PROFLOW.RECEIVEINFO IS
'������Ϣ';

COMMENT ON COLUMN PROFLOW.OVERMARK IS
'������־';

COMMENT ON COLUMN PROFLOW.RETURNMARK IS
'�˻ر�־';

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
'��Ʒ������Ϣ��';

COMMENT ON COLUMN PROREPAIR.ID IS
'��Ʒ���ޣɣ�';

COMMENT ON COLUMN PROREPAIR.PROCODE IS
'�ʲ�����';

COMMENT ON COLUMN PROREPAIR.TYPEID IS
'��Ʒ���';

COMMENT ON COLUMN PROREPAIR.ERRORVIEW IS
'��������';

COMMENT ON COLUMN PROREPAIR.SENDDATE IS
'����ʱ�䡡';

COMMENT ON COLUMN PROREPAIR.CHANGEDATE IS
'����ʱ�䡡';

COMMENT ON COLUMN PROREPAIR.ERRORFOR IS
'����ԭ��';

COMMENT ON COLUMN PROREPAIR.CHANGECODE IS
'������Ʒ���롡';

COMMENT ON COLUMN PROREPAIR.WAREMARK IS
'����־';

COMMENT ON COLUMN PROREPAIR.INUSE IS
'��Ч��־';

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
'��ɫȨ�޶�Ӧ��';

COMMENT ON COLUMN ROLEPRI.ID IS
' ��ɫȨ�޶�ӦID';

COMMENT ON COLUMN ROLEPRI.ROLEID IS
'��ɫID';

COMMENT ON COLUMN ROLEPRI.PRIID IS
'Ȩ��ID';

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
'��ɫ��';

COMMENT ON COLUMN ROLES.ID IS
'��ɫID';

COMMENT ON COLUMN ROLES.ROLENAME IS
'��ɫ��';

COMMENT ON COLUMN ROLES.ROLEINFO IS
'��ɫ��Ϣ';

COMMENT ON COLUMN ROLES.ROLEMEMO IS
'��ע��Ϣ';
