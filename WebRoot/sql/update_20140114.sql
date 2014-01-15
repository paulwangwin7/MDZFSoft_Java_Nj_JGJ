-----------------------------------------------------
-- 为部门表增加path字段                     --
-----------------------------------------------------
ALTER TABLE frame_tree ADD PATH VARCHAR2(100);
commit;

create index uploadtime_year_month_ind on frame_upload ( SUBSTR(UPLOAD_TIME,0,8) );

