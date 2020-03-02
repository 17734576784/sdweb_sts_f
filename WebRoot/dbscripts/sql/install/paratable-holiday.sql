use ydparaben
go

	--jackadd �ϼ��� 20150304 start 
-- *************** �ڼ��ն���� ***************
if (exists (select name from sysobjects where name='holidaydef'))
begin
	print '<<<<<<<Delete table �ڼ��ն���� on YDPara>>>>>>>'
	drop table holidaydef
end
go

print '-------Create table �ڼ��ն���� on YDPara-------'
create table holidaydef
(
	id						smallint       		 not null,
	describe      varchar(64)				 not null,		/*����*/
	
	sdate					int										 null, 		/*���� ����*/
	reserve1			varchar(64)  					 null,
	reserve2			varchar(64)  					 null,
	CONSTRAINT 	holidaydef_index1 PRIMARY KEY (id)	
)
go
grant select on holidaydef to public
go


if (exists (select name from sysobjects where name='holidaygroup'))
begin
	print '<<<<<<<Delete table �ڼ������ on YDPara>>>>>>>'
	drop table holidaygroup
end
go

print '-------Create table �ڼ������ on YDPara-------'
create table holidaygroup
(
	id						smallint       		 not null,
	describe      varchar(64)				 not null,		/*����*/
	grouptype			tinyint	 	   default 0 null,		/*����*/
	reserve1			varchar(64)  					 null,
	reserve2			varchar(64)  					 null,
	CONSTRAINT 	holidaygroup_index1 PRIMARY KEY (id)	
)
go
grant select on holidaygroup to public
go

if (exists (select name from sysobjects where name='holigitem'))
begin
	print '<<<<<<<Delete table �ڼ���������� on YDPara>>>>>>>'
	drop table holigitem
end
go

print '-------Create table �ڼ���������� on YDPara-------'
create table holigitem
(
	groupid				smallint       		 not null,		/*�ڼ�������*/
	holidayid			smallint       		 not null,		/*�ڼ��ձ��*/
	reserve1			varchar(64)  					 null,
	reserve2			varchar(64)  					 null,
	CONSTRAINT 	holigitem_index1 PRIMARY KEY (groupid,holidayid)	
)
go
grant select on holigitem to public
go

	--jackadd �ϼ��� 20150304 end 
	
	