use ydparaben
go

	--jackadd 孟加拉 20150304 start 
-- *************** 节假日定义表 ***************
if (exists (select name from sysobjects where name='holidaydef'))
begin
	print '<<<<<<<Delete table 节假日定义表 on YDPara>>>>>>>'
	drop table holidaydef
end
go

print '-------Create table 节假日定义表 on YDPara-------'
create table holidaydef
(
	id						smallint       		 not null,
	describe      varchar(64)				 not null,		/*描述*/
	
	sdate					int										 null, 		/*日期 月日*/
	reserve1			varchar(64)  					 null,
	reserve2			varchar(64)  					 null,
	CONSTRAINT 	holidaydef_index1 PRIMARY KEY (id)	
)
go
grant select on holidaydef to public
go


if (exists (select name from sysobjects where name='holidaygroup'))
begin
	print '<<<<<<<Delete table 节假日组表 on YDPara>>>>>>>'
	drop table holidaygroup
end
go

print '-------Create table 节假日组表 on YDPara-------'
create table holidaygroup
(
	id						smallint       		 not null,
	describe      varchar(64)				 not null,		/*描述*/
	grouptype			tinyint	 	   default 0 null,		/*类型*/
	reserve1			varchar(64)  					 null,
	reserve2			varchar(64)  					 null,
	CONSTRAINT 	holidaygroup_index1 PRIMARY KEY (id)	
)
go
grant select on holidaygroup to public
go

if (exists (select name from sysobjects where name='holigitem'))
begin
	print '<<<<<<<Delete table 节假日组分项表表 on YDPara>>>>>>>'
	drop table holigitem
end
go

print '-------Create table 节假日组分项表表 on YDPara-------'
create table holigitem
(
	groupid				smallint       		 not null,		/*节假日组编号*/
	holidayid			smallint       		 not null,		/*节假日编号*/
	reserve1			varchar(64)  					 null,
	reserve2			varchar(64)  					 null,
	CONSTRAINT 	holigitem_index1 PRIMARY KEY (groupid,holidayid)	
)
go
grant select on holigitem to public
go

	--jackadd 孟加拉 20150304 end 
	
	