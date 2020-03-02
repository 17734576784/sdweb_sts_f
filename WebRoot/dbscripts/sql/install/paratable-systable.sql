use YDparaBen
go


print '========Creating  table 部门信息表=========='
go
if exists (select * from sysobjects where name = 'departments')
drop table     departments
go
create table departments
(
	id   						smallint       	not null,	
	describe       	varchar(32)			not null,
	
	CONSTRAINT departments_index1 PRIMARY KEY (id)		
)
go
grant select on departments to public
go

insert departments values(0, '供电公司')
insert departments values(1, '营销部')
insert departments values(2, '计量中心')

print '========Creating  table 用户信息表=========='
go
if exists (select * from sysobjects where name = 'userpara')
drop table     userpara
go
create table userpara
(
  id        			smallint       	not null,	
	opername				varchar(16)			not	null,		/*用户账号*/
	describe      	varchar(64)					null,		/*用户姓名*/
	pwd      				varchar(64) 		not null,
	departmentid  	smallint        		null,
	displayorder  	smallint        		null,
	superuser 			tinyint	 		 				null, 	/*超级管理员*/
	right_global 		tinyint	 		 				null, 	/*全局权限*/
	right_line 			tinyint	 		 				null, 	/*线路权限*/
	right_fzren 		tinyint	 		 				null, 	/*负责人权限*/

	
	--树是否显示线路，线路负责人，用户
	show_line       tinyint 						null default(1),
	show_fzren    	tinyint 						null default(1),
	show_cons       tinyint	 						null default(1),
	                                		
	show_tree_type	tinyint 						null default(0),	/*0：电网结构，1：管理结构*/
	
	CONSTRAINT userpara_index1 PRIMARY KEY (opername)		
)
go
grant select on userpara to public
go
create index userpara_index2
on userpara(id) on [primary]
go

insert into userpara(id,opername,describe,pwd,departmentid,right_global) values(1,'admin','系统管理员','123456',0,1)


print '========Creating  table 用户权限范围表=========='
go
if exists (select * from sysobjects where name = 'userrankbound')
drop table     userrankbound
go
create table userrankbound
(
	id				int		not null,
	user_id   			smallint       	not null,	
	type       			tinyint					not null,
	subst_id				smallint						null,				/*所属变电站*/
	line_id					smallint						null,				/*所属线路*/
	org_id					smallint						null,				/*所属供电所*/
	cons_id							 int						null,				/*所属台区或居民区*/
	line_fzman_id		smallint						null				/*线路负责人*/	
)
go
grant select on userrankbound to public
go
create unique index userrankbound_index1 
on userrankbound(user_id,type,cons_id)
go



print '========Creating  table 角色信息表=========='
go
if exists (select * from sysobjects where name = 'roleinfo')
drop table    roleinfo
go
create table roleinfo
(
	id           	smallint      not null,	
	describe     	varchar(32)   not null,
  detail     		varchar(64)   		null,
  
  	CONSTRAINT roleinfo_index1 PRIMARY KEY (describe)	
)
go

grant select on roleinfo to public
go
create index roleinfo_index2
on roleinfo(id) on [primary]
go


print '========Creating  table 用户角色表=========='
go
if exists (select * from sysobjects where name = 'user_role')
drop table    user_role
go
create table user_role
(
  	userid       		smallint       not null,
    roleid       		smallint       not null,
    
    CONSTRAINT user_role_index1 PRIMARY KEY (userid,roleid)	
)
go

grant select on user_role to public
go



print '========Creating  table 角色权限表=========='
go
if exists (select * from sysobjects where name = 'rolepermission')
drop table    rolepermission			/* 一个角色仅能够具有一种菜单信息权限 */
go
create table rolepermission
(
    roleid      smallint 	not null,
    moduleid    int 			not null,
    pmoduleid   int 		 	not null,
    
    right00			tinyint				null,	/*本级菜单（页面）是否显示*/
    right01			tinyint				null,	/*本页面的        信息修改权限*/
    right02			tinyint				null,	/*本页面的        终端召测权限*/
    right03			tinyint				null,	/*本页面的        终端设置权限*/
    right04			tinyint				null,	/*本页面的        预付费参数设置权限*/
    right05			tinyint				null,	/*本页面的        负控操作权限*/
    right06			tinyint				null,	/*本页面的        保电操作权限*/
    right07			tinyint				null,	/*本页面的        备用权限*/
    right08			tinyint				null,	/*本页面的        备用权限*/
    right09			tinyint				null,	/*本页面的        备用权限*/
    right10			tinyint				null,	/*本页面的        备用权限*/
    
    CONSTRAINT rolepermission_index1 PRIMARY KEY (roleid,moduleid,pmoduleid)	
)
go

grant select on rolepermission to public
go

print '========Creating  table 菜单信息表=========='
go
if exists (select * from sysobjects where name = 'models')
drop table    models
go
create table models
(
		id              int   	 			not null,	
    describe      	varchar(64)  			null,
    menuid					varchar(32)  			null,
    menupath        varchar(255) 			null,
    parentmoduleid  int  	 	 					null,
    right00					tinyint		 				null,	/*本级菜单（页面）是否显示*/
    right01					tinyint		 				null,	/*本页面的        信息修改权限*/
    right02					tinyint		 				null,	/*本页面的        终端召测权限*/
    right03					tinyint		 				null,	/*本页面的        终端设置权限*/
    right04					tinyint		 				null,	/*本页面的        负控预付费参数设置权限*/
    right05					tinyint		 				null,	/*本页面的        备用权限*/
    right06					tinyint		 				null,	/*本页面的        备用权限*/
    right07					tinyint		 				null,	/*本页面的        备用权限*/
    right08					tinyint		 				null,	/*本页面的        备用权限*/
    right09					tinyint		 				null,	/*本页面的        备用权限*/
    right10					tinyint		 				null,	/*本页面的        备用权限*/
    CONSTRAINT models_index1 PRIMARY KEY (id)
	)
go

grant select on models to public
go



delete from roleinfo
go

/*									  id  		描述									详细   */
insert roleinfo values(1,			'变电站使用',					'变电站使用')
insert roleinfo values(2,			'变电站只读使用',			'变电站只读使用')
insert roleinfo values(3,			'专变使用',						'专变使用')
insert roleinfo values(4,			'专变只读使用',				'专变只读使用')
insert roleinfo values(5,			'公变使用',						'公变使用')
insert roleinfo values(6,			'公变只读使用',				'公变只读使用')
insert roleinfo values(7,			'低压集抄使用',				'低压集抄使用')
insert roleinfo values(8,			'低压集抄只读使用',		'低压集抄只读使用')
insert roleinfo values(9,			'综合应用使用',				'综合应用使用')
insert roleinfo values(10,		'综合应用只读使用',		'综合应用只读使用')
insert roleinfo values(11,		'系统管理使用',				'系统管理使用')
insert roleinfo values(12,		'系统管理只读使用',		'系统管理只读使用')
insert roleinfo values(13,		'农排使用',						'农排使用')
insert roleinfo values(14,		'农排只读使用',				'农排只读使用')

