prompt PL/SQL Developer import file
prompt Created on 2013年1月30日 by Administrator
set feedback off
set define off
prompt Loading FRAME_MENU...
insert into FRAME_MENU (MENU_ID, MENU_NAME, MENU_SORT, MENU_STATE)
values (1, '权限管理', 1, 'A');
insert into FRAME_MENU (MENU_ID, MENU_NAME, MENU_SORT, MENU_STATE)
values (2, '用户管理', 2, 'A');
insert into FRAME_MENU (MENU_ID, MENU_NAME, MENU_SORT, MENU_STATE)
values (3, '文件管理', 3, 'A');
insert into FRAME_MENU (MENU_ID, MENU_NAME, MENU_SORT, MENU_STATE)
values (4, '日志查询', 4, 'A');
commit;
prompt 4 records loaded
prompt Loading FRAME_URL...
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (1, 'userAction.do?method=treeManager', '部门管理', '部门管理', 'A', 1, 1, 'tab_bmgl');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (3, 'userAction.do?method=userManagerToAdd', '用户注册', '用户注册', 'A', 2, 1, 'tab_yhzc');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (4, 'userAction.do?method=userManager', '用户查询', '用户查询', 'A', 2, 2, 'tab_yhcx');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (5, 'jsp/user/fileManagerAdd.jsp', '文件上传', '文件上传', 'A', 3, 1, 'tab_wjsc');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (6, 'userAction.do?method=uploadLog', '上传报表查询', '上传报表查询', 'A', 4, 1, 'tab_scrz');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (7, 'userAction.do?method=userActionLogManager', '用户操作查询', '用户操作', 'A', 4, 2, 'tab_czrz');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (8, 'userAction.do?method=uploadFileShow', '文件查看', '文件查看', 'A', 3, 2, 'tab_czrz');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (9, 'sysAction.do?method=noticeManager', '公告管理', '公告管理', 'A', 1, 3, 'tab_ggfb');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (10, 'userAction.do?method=expiredFileList', '文件删除', '文件删除', 'A', 3, 3, 'tab_wjsc');
insert into FRAME_URL (URL_ID, URL_VALUE, URL_NAME, URL_DESC, URL_STATE, PARENT_MENU_ID, URL_SORT, URL_TAB)
values (2, 'userAction.do?method=roleManager', '角色管理', '角色管理', 'A', 2, 3, 'tab_jsgl');
commit;
prompt 10 records loaded
prompt Loading FRAME_USER_DEMO...
insert into FRAME_USER (USER_ID, LOGIN_NAME, LOGIN_PSWD, USER_NAME, USER_CODE, SEX, USER_IDCARD, CARD_TYPEID, CARD_CODE, TREE_ID, ROLE_ID, CREATE_TIME, USER_STATE)
values (0, 'admin', '121212', '系统管理员', '000000', 'M', null, 0, null, 0, 0, '20120116144925', 'A');
commit;
prompt 1 records loaded
set feedback on
set define on
prompt Done.
