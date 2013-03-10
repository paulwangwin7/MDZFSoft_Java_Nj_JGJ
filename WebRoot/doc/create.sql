-----------------------------------------------------
-- Export file for user NJMD                       --
-- Created by Administrator on 2013/1/30, 11:08:38 --
-----------------------------------------------------

spool create.log

prompt
prompt Creating table FRAME_LOG
prompt ========================
prompt
create table NJMD.FRAME_LOG
(
  LOG_ID      NUMBER(10) not null,
  CREATE_TIME VARCHAR2(14),
  USER_ID     NUMBER(10),
  USER_CODE   VARCHAR2(50),
  TREE_ID     NUMBER(10),
  TREE_NAME   VARCHAR2(200),
  ROLE_ID     NUMBER(10),
  ROLE_NAME   VARCHAR2(200),
  LOG_DESC    VARCHAR2(2000),
  IP_ADD      VARCHAR2(100)
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_LOG
  is '��־��¼��';
comment on column NJMD.FRAME_LOG.LOG_ID
  is 'ID,����';
comment on column NJMD.FRAME_LOG.CREATE_TIME
  is '��־��¼ʱ��';
comment on column NJMD.FRAME_LOG.USER_ID
  is '����¼��id';
comment on column NJMD.FRAME_LOG.USER_CODE
  is '����¼���˺�';
comment on column NJMD.FRAME_LOG.TREE_ID
  is '����¼�˲���id';
comment on column NJMD.FRAME_LOG.TREE_NAME
  is '����¼�˲���';
comment on column NJMD.FRAME_LOG.ROLE_ID
  is '����¼�˽�ɫid';
comment on column NJMD.FRAME_LOG.ROLE_NAME
  is '����¼�˽�ɫ';
comment on column NJMD.FRAME_LOG.LOG_DESC
  is '��־��¼����';
comment on column NJMD.FRAME_LOG.IP_ADD
  is 'IP';
alter table NJMD.FRAME_LOG
  add constraint PK_FRAME_LOG primary key (LOG_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FRAME_MENU
prompt =========================
prompt
create table NJMD.FRAME_MENU
(
  MENU_ID    NUMBER(10) not null,
  MENU_NAME  VARCHAR2(50),
  MENU_SORT  NUMBER(3),
  MENU_STATE CHAR(1) default 'A'
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_MENU
  is '�˵���';
comment on column NJMD.FRAME_MENU.MENU_ID
  is 'ID,����';
comment on column NJMD.FRAME_MENU.MENU_NAME
  is '�˵�������';
comment on column NJMD.FRAME_MENU.MENU_SORT
  is '��ʾ���ȼ�';
comment on column NJMD.FRAME_MENU.MENU_STATE
  is '״̬��A-��Ч��U-��Ч';
alter table NJMD.FRAME_MENU
  add constraint PK_FRAME_MENU primary key (MENU_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table NJMD.FRAME_MENU
  add constraint CKC_MENU_STATE_FRAME_ME
  check (MENU_STATE is null or (MENU_STATE in ('A','U')));

prompt
prompt Creating table FRAME_NOTICE
prompt ===========================
prompt
create table NJMD.FRAME_NOTICE
(
  NOTICE_ID      NUMBER(10) not null,
  NOTICE_TITLE   VARCHAR2(60),
  NOTICE_CONTENT VARCHAR2(1000),
  NOTICE_TYPE    CHAR(1),
  CREATE_TIME    VARCHAR2(14),
  NOTICE_BEGIN   VARCHAR2(12),
  NOTICE_END     VARCHAR2(12),
  USER_ID        NUMBER(10),
  TREE_ID_LIST   VARCHAR2(100)
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_NOTICE
  is '�����';
comment on column NJMD.FRAME_NOTICE.NOTICE_ID
  is 'ID,����';
comment on column NJMD.FRAME_NOTICE.NOTICE_TITLE
  is '�������';
comment on column NJMD.FRAME_NOTICE.NOTICE_CONTENT
  is '��������';
comment on column NJMD.FRAME_NOTICE.NOTICE_TYPE
  is '�������ͣ�1-��ҳ���棻2-��Ϣ����;';
comment on column NJMD.FRAME_NOTICE.CREATE_TIME
  is '���淢��ʱ��';
comment on column NJMD.FRAME_NOTICE.NOTICE_BEGIN
  is '���濪ʼʱ��';
comment on column NJMD.FRAME_NOTICE.NOTICE_END
  is '�������ʱ��';
comment on column NJMD.FRAME_NOTICE.USER_ID
  is '����������id';
comment on column NJMD.FRAME_NOTICE.TREE_ID_LIST
  is '�����б�';
alter table NJMD.FRAME_NOTICE
  add constraint PK_FRAME_NOTICE primary key (NOTICE_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FRAME_REMARK
prompt ===========================
prompt
create table NJMD.FRAME_REMARK
(
  REMARK_ID      NUMBER(10),
  REMARK_TABLE   VARCHAR2(100),
  REMARK_CONTENT VARCHAR2(100),
  CREATE_TIME    VARCHAR2(14)
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FRAME_ROLE
prompt =========================
prompt
create table NJMD.FRAME_ROLE
(
  ROLE_ID     NUMBER(10) not null,
  ROLE_NAME   VARCHAR2(50),
  ROLE_DESC   VARCHAR2(200),
  ROLE_STATE  CHAR(1) default 'A',
  CREATE_USER NUMBER(10),
  CREATE_TIME VARCHAR2(14),
  TREE_ID     NUMBER(10),
  URL_ID_LIST VARCHAR2(200)
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_ROLE
  is '�û���ɫ';
comment on column NJMD.FRAME_ROLE.ROLE_ID
  is 'ID,����';
comment on column NJMD.FRAME_ROLE.ROLE_NAME
  is '��ɫ����';
comment on column NJMD.FRAME_ROLE.ROLE_DESC
  is '��ɫ����';
comment on column NJMD.FRAME_ROLE.ROLE_STATE
  is '״̬��A-��Ч��U-��Ч';
comment on column NJMD.FRAME_ROLE.CREATE_USER
  is '������userid';
comment on column NJMD.FRAME_ROLE.CREATE_TIME
  is '����ʱ��';
comment on column NJMD.FRAME_ROLE.TREE_ID
  is '��������';
comment on column NJMD.FRAME_ROLE.URL_ID_LIST
  is '����url�б��ʶ ���磺1,2,3,...,��';
alter table NJMD.FRAME_ROLE
  add constraint PK_FRAME_ROLE primary key (ROLE_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table NJMD.FRAME_ROLE
  add constraint CKC_ROLE_STATE_FRAME_RO
  check (ROLE_STATE is null or (ROLE_STATE in ('A','U')));

prompt
prompt Creating table FRAME_SERVERINFO
prompt ===============================
prompt
create table NJMD.FRAME_SERVERINFO
(
  SERVERINFO_ID  NUMBER(10) not null,
  RATIO_CPU      NUMBER(3),
  RATIO_MEMORY   NUMBER(3),
  USE_MEMORY     VARCHAR2(20),
  RATIO_HARDDISK NUMBER(3),
  USE_HARDDISK   VARCHAR2(20),
  LETTER         CHAR(1),
  CREATE_TIME    VARCHAR2(14),
  SAVE_IP        VARCHAR2(15)
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_SERVERINFO
  is '������״��';
comment on column NJMD.FRAME_SERVERINFO.SERVERINFO_ID
  is 'ID,����';
comment on column NJMD.FRAME_SERVERINFO.RATIO_CPU
  is 'CPUռ����';
comment on column NJMD.FRAME_SERVERINFO.RATIO_MEMORY
  is '�ڴ�ռ����';
comment on column NJMD.FRAME_SERVERINFO.USE_MEMORY
  is '�ڴ�ʹ�ã���λ��kb��';
comment on column NJMD.FRAME_SERVERINFO.RATIO_HARDDISK
  is 'Ӳ��ռ����';
comment on column NJMD.FRAME_SERVERINFO.USE_HARDDISK
  is 'Ӳ��ʹ�ã���λ��gb��';
comment on column NJMD.FRAME_SERVERINFO.LETTER
  is 'Ӳ���̷�';
comment on column NJMD.FRAME_SERVERINFO.CREATE_TIME
  is '��¼ʱ��';
comment on column NJMD.FRAME_SERVERINFO.SAVE_IP
  is '������IP';
alter table NJMD.FRAME_SERVERINFO
  add constraint PK_FRAME_SERVERINFO primary key (SERVERINFO_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FRAME_TREE
prompt =========================
prompt
create table NJMD.FRAME_TREE
(
  TREE_ID        NUMBER(10) not null,
  TREE_NAME      VARCHAR2(50),
  TREE_DESC      VARCHAR2(200),
  TREE_STATE     CHAR(1) default 'A',
  CREATE_USER    NUMBER(10),
  CREATE_TIME    VARCHAR2(14),
  PARENT_TREE_ID NUMBER(10),
  ORDER_BY       NUMBER(3)
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_TREE
  is '���ż���滮��';
comment on column NJMD.FRAME_TREE.TREE_ID
  is 'ID,����';
comment on column NJMD.FRAME_TREE.TREE_NAME
  is '��������';
comment on column NJMD.FRAME_TREE.TREE_DESC
  is '��������';
comment on column NJMD.FRAME_TREE.TREE_STATE
  is '״̬��A-��Ч��U-��Ч';
comment on column NJMD.FRAME_TREE.CREATE_USER
  is '������userid';
comment on column NJMD.FRAME_TREE.CREATE_TIME
  is '����ʱ��';
comment on column NJMD.FRAME_TREE.PARENT_TREE_ID
  is '�������� ��һ������ʱΪ0��';
comment on column NJMD.FRAME_TREE.ORDER_BY
  is '����';
alter table NJMD.FRAME_TREE
  add constraint PK_TREE_ID primary key (TREE_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table FRAME_UPLOAD
prompt ===========================
prompt
create table NJMD.FRAME_UPLOAD
(
  UPLOAD_ID       NUMBER(10) not null,
  USER_ID         NUMBER(10),
  EDIT_ID         NUMBER(10),
  UPLOAD_NAME     VARCHAR2(100),
  PLAY_PATH       VARCHAR2(200),
  FILE_CREATETIME VARCHAR2(14),
  SHOW_PATH       VARCHAR2(200),
  UPLOAD_TIME     VARCHAR2(14),
  FILE_STATE      CHAR(1) default 'A',
  TREE2_ID        NUMBER(10),
  TREE1_ID        NUMBER(10),
  TREE_NAME       VARCHAR2(100),
  FILE_STATS      CHAR(1) default 0,
  FILE_REMARK     VARCHAR2(2000),
  IP_ADDR         VARCHAR2(15),
  REAL_PATH       VARCHAR2(50),
  FLV_PATH        VARCHAR2(200)
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_UPLOAD
  is '�ϴ��ļ���';
comment on column NJMD.FRAME_UPLOAD.UPLOAD_ID
  is 'ID,����';
comment on column NJMD.FRAME_UPLOAD.USER_ID
  is '�ϴ���ID';
comment on column NJMD.FRAME_UPLOAD.EDIT_ID
  is '�ɼ���ID';
comment on column NJMD.FRAME_UPLOAD.UPLOAD_NAME
  is '�ϴ��ļ���';
comment on column NJMD.FRAME_UPLOAD.PLAY_PATH
  is '�ļ����ŵ�ַ';
comment on column NJMD.FRAME_UPLOAD.FILE_CREATETIME
  is '�ļ�����ʱ��';
comment on column NJMD.FRAME_UPLOAD.SHOW_PATH
  is 'Ԥ����ַ';
comment on column NJMD.FRAME_UPLOAD.UPLOAD_TIME
  is '�ļ��ϴ�ʱ��';
comment on column NJMD.FRAME_UPLOAD.FILE_STATE
  is 'A-��Ч��U-��Ч��F-���ڣ� C-δ������ P-�������';
comment on column NJMD.FRAME_UPLOAD.TREE2_ID
  is '�ϴ��˲���id';
comment on column NJMD.FRAME_UPLOAD.TREE1_ID
  is '�ϴ����ϼ�����id';
comment on column NJMD.FRAME_UPLOAD.TREE_NAME
  is '�ϴ��˲�������';
comment on column NJMD.FRAME_UPLOAD.FILE_STATS
  is '�ļ��ϴ���Ҫ�� 0-��ͨ��1-��Ҫ';
comment on column NJMD.FRAME_UPLOAD.FILE_REMARK
  is '�ļ���ע˵��';
comment on column NJMD.FRAME_UPLOAD.IP_ADDR
  is '�ϴ���IP��ַ';
comment on column NJMD.FRAME_UPLOAD.REAL_PATH
  is '����ǰ׺';
comment on column NJMD.FRAME_UPLOAD.FLV_PATH
  is 'flv�ļ����ŵ�ַ';
alter table NJMD.FRAME_UPLOAD
  add constraint PK_FRAME_UPLOAD primary key (UPLOAD_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table NJMD.FRAME_UPLOAD
  add constraint CKC_FILE_STATE_FRAME_UP
  check (FILE_STATE is null or ( FILE_STATE in ('A','U','F','C','P') ));

prompt
prompt Creating table FRAME_URL
prompt ========================
prompt
create table NJMD.FRAME_URL
(
  URL_ID         NUMBER(10) not null,
  URL_VALUE      VARCHAR2(200),
  URL_NAME       VARCHAR2(50),
  URL_DESC       VARCHAR2(200),
  URL_STATE      CHAR(1) default 'A',
  PARENT_MENU_ID NUMBER(10),
  URL_SORT       NUMBER(3),
  URL_TAB        VARCHAR2(15)
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_URL
  is '���ܵ�ַ';
comment on column NJMD.FRAME_URL.URL_ID
  is 'ID,����';
comment on column NJMD.FRAME_URL.URL_VALUE
  is 'URL��ַ';
comment on column NJMD.FRAME_URL.URL_NAME
  is 'URL����';
comment on column NJMD.FRAME_URL.URL_STATE
  is '״̬��A-��Ч��U-��Ч';
comment on column NJMD.FRAME_URL.PARENT_MENU_ID
  is '�����˵�';
comment on column NJMD.FRAME_URL.URL_SORT
  is 'Tab��ǩ��';
alter table NJMD.FRAME_URL
  add constraint PK_FRAME_URL primary key (URL_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table NJMD.FRAME_URL
  add constraint CKC_URL_STATE_FRAME_UR
  check (URL_STATE is null or (URL_STATE in ('A','U')));

prompt
prompt Creating table FRAME_USER
prompt =========================
prompt
create table NJMD.FRAME_USER
(
  USER_ID     NUMBER(10) not null,
  LOGIN_NAME  VARCHAR2(20),
  LOGIN_PSWD  VARCHAR2(20),
  USER_NAME   VARCHAR2(20),
  USER_CODE   VARCHAR2(30),
  SEX         CHAR(1),
  USER_IDCARD VARCHAR2(20),
  CARD_TYPEID NUMBER(10),
  CARD_CODE   VARCHAR2(50),
  TREE_ID     NUMBER(10),
  ROLE_ID     NUMBER(10),
  CREATE_TIME VARCHAR2(14),
  USER_STATE  CHAR(1) default 'A'
)
tablespace NJMD
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table NJMD.FRAME_USER
  is '�û���';
comment on column NJMD.FRAME_USER.USER_ID
  is 'ID,����';
comment on column NJMD.FRAME_USER.LOGIN_NAME
  is '��¼�˺�';
comment on column NJMD.FRAME_USER.LOGIN_PSWD
  is '��¼����';
comment on column NJMD.FRAME_USER.USER_NAME
  is '��ʵ����';
comment on column NJMD.FRAME_USER.USER_CODE
  is '����';
comment on column NJMD.FRAME_USER.SEX
  is '�Ա�';
comment on column NJMD.FRAME_USER.USER_IDCARD
  is '���֤';
comment on column NJMD.FRAME_USER.CARD_TYPEID
  is '����֤������';
comment on column NJMD.FRAME_USER.CARD_CODE
  is '����֤����';
comment on column NJMD.FRAME_USER.TREE_ID
  is '��������';
comment on column NJMD.FRAME_USER.ROLE_ID
  is '������ɫ';
comment on column NJMD.FRAME_USER.CREATE_TIME
  is 'ע��ʱ��';
comment on column NJMD.FRAME_USER.USER_STATE
  is '״̬��A-��Ч��U-��Ч';
alter table NJMD.FRAME_USER
  add constraint USER_ID primary key (USER_ID)
  using index 
  tablespace NJMD
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating sequence SEQ_LOG_ID
prompt ============================
prompt
create sequence NJMD.SEQ_LOG_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_MENU_ID
prompt =============================
prompt
create sequence NJMD.SEQ_MENU_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_NOTICE_ID
prompt ===============================
prompt
create sequence NJMD.SEQ_NOTICE_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_REMARK_ID
prompt ===============================
prompt
create sequence NJMD.SEQ_REMARK_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ROLE_ID
prompt =============================
prompt
create sequence NJMD.SEQ_ROLE_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SERVERINFO_ID
prompt ===================================
prompt
create sequence NJMD.SEQ_SERVERINFO_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TREE_ID
prompt =============================
prompt
create sequence NJMD.SEQ_TREE_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_UPLOAD_ID
prompt ===============================
prompt
create sequence NJMD.SEQ_UPLOAD_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_URL_ID
prompt ============================
prompt
create sequence NJMD.SEQ_URL_ID
minvalue 1
maxvalue 999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_USER_ID
prompt =============================
prompt
create sequence NJMD.SEQ_USER_ID
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cache 20;


spool off
