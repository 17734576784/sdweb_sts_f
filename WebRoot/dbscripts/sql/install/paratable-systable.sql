use YDparaBen
go


print '========Creating  table ������Ϣ��=========='
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

insert departments values(0, '���繫˾')
insert departments values(1, 'Ӫ����')
insert departments values(2, '��������')

print '========Creating  table �û���Ϣ��=========='
go
if exists (select * from sysobjects where name = 'userpara')
drop table     userpara
go
create table userpara
(
  id        			smallint       	not null,	
	opername				varchar(16)			not	null,		/*�û��˺�*/
	describe      	varchar(64)					null,		/*�û�����*/
	pwd      				varchar(64) 		not null,
	departmentid  	smallint        		null,
	displayorder  	smallint        		null,
	superuser 			tinyint	 		 				null, 	/*��������Ա*/
	right_global 		tinyint	 		 				null, 	/*ȫ��Ȩ��*/
	right_line 			tinyint	 		 				null, 	/*��·Ȩ��*/
	right_fzren 		tinyint	 		 				null, 	/*������Ȩ��*/

	
	--���Ƿ���ʾ��·����·�����ˣ��û�
	show_line       tinyint 						null default(1),
	show_fzren    	tinyint 						null default(1),
	show_cons       tinyint	 						null default(1),
	                                		
	show_tree_type	tinyint 						null default(0),	/*0�������ṹ��1������ṹ*/
	
	CONSTRAINT userpara_index1 PRIMARY KEY (opername)		
)
go
grant select on userpara to public
go
create index userpara_index2
on userpara(id) on [primary]
go

insert into userpara(id,opername,describe,pwd,departmentid,right_global) values(1,'admin','ϵͳ����Ա','123456',0,1)


print '========Creating  table �û�Ȩ�޷�Χ��=========='
go
if exists (select * from sysobjects where name = 'userrankbound')
drop table     userrankbound
go
create table userrankbound
(
	id				int		not null,
	user_id   			smallint       	not null,	
	type       			tinyint					not null,
	subst_id				smallint						null,				/*�������վ*/
	line_id					smallint						null,				/*������·*/
	org_id					smallint						null,				/*����������*/
	cons_id							 int						null,				/*����̨���������*/
	line_fzman_id		smallint						null				/*��·������*/	
)
go
grant select on userrankbound to public
go
create unique index userrankbound_index1 
on userrankbound(user_id,type,cons_id)
go



print '========Creating  table ��ɫ��Ϣ��=========='
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


print '========Creating  table �û���ɫ��=========='
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



print '========Creating  table ��ɫȨ�ޱ�=========='
go
if exists (select * from sysobjects where name = 'rolepermission')
drop table    rolepermission			/* һ����ɫ���ܹ�����һ�ֲ˵���ϢȨ�� */
go
create table rolepermission
(
    roleid      smallint 	not null,
    moduleid    int 			not null,
    pmoduleid   int 		 	not null,
    
    right00			tinyint				null,	/*�����˵���ҳ�棩�Ƿ���ʾ*/
    right01			tinyint				null,	/*��ҳ���        ��Ϣ�޸�Ȩ��*/
    right02			tinyint				null,	/*��ҳ���        �ն��ٲ�Ȩ��*/
    right03			tinyint				null,	/*��ҳ���        �ն�����Ȩ��*/
    right04			tinyint				null,	/*��ҳ���        Ԥ���Ѳ�������Ȩ��*/
    right05			tinyint				null,	/*��ҳ���        ���ز���Ȩ��*/
    right06			tinyint				null,	/*��ҳ���        �������Ȩ��*/
    right07			tinyint				null,	/*��ҳ���        ����Ȩ��*/
    right08			tinyint				null,	/*��ҳ���        ����Ȩ��*/
    right09			tinyint				null,	/*��ҳ���        ����Ȩ��*/
    right10			tinyint				null,	/*��ҳ���        ����Ȩ��*/
    
    CONSTRAINT rolepermission_index1 PRIMARY KEY (roleid,moduleid,pmoduleid)	
)
go

grant select on rolepermission to public
go

print '========Creating  table �˵���Ϣ��=========='
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
    right00					tinyint		 				null,	/*�����˵���ҳ�棩�Ƿ���ʾ*/
    right01					tinyint		 				null,	/*��ҳ���        ��Ϣ�޸�Ȩ��*/
    right02					tinyint		 				null,	/*��ҳ���        �ն��ٲ�Ȩ��*/
    right03					tinyint		 				null,	/*��ҳ���        �ն�����Ȩ��*/
    right04					tinyint		 				null,	/*��ҳ���        ����Ԥ���Ѳ�������Ȩ��*/
    right05					tinyint		 				null,	/*��ҳ���        ����Ȩ��*/
    right06					tinyint		 				null,	/*��ҳ���        ����Ȩ��*/
    right07					tinyint		 				null,	/*��ҳ���        ����Ȩ��*/
    right08					tinyint		 				null,	/*��ҳ���        ����Ȩ��*/
    right09					tinyint		 				null,	/*��ҳ���        ����Ȩ��*/
    right10					tinyint		 				null,	/*��ҳ���        ����Ȩ��*/
    CONSTRAINT models_index1 PRIMARY KEY (id)
	)
go

grant select on models to public
go



delete from roleinfo
go

/*									  id  		����									��ϸ   */
insert roleinfo values(1,			'���վʹ��',					'���վʹ��')
insert roleinfo values(2,			'���վֻ��ʹ��',			'���վֻ��ʹ��')
insert roleinfo values(3,			'ר��ʹ��',						'ר��ʹ��')
insert roleinfo values(4,			'ר��ֻ��ʹ��',				'ר��ֻ��ʹ��')
insert roleinfo values(5,			'����ʹ��',						'����ʹ��')
insert roleinfo values(6,			'����ֻ��ʹ��',				'����ֻ��ʹ��')
insert roleinfo values(7,			'��ѹ����ʹ��',				'��ѹ����ʹ��')
insert roleinfo values(8,			'��ѹ����ֻ��ʹ��',		'��ѹ����ֻ��ʹ��')
insert roleinfo values(9,			'�ۺ�Ӧ��ʹ��',				'�ۺ�Ӧ��ʹ��')
insert roleinfo values(10,		'�ۺ�Ӧ��ֻ��ʹ��',		'�ۺ�Ӧ��ֻ��ʹ��')
insert roleinfo values(11,		'ϵͳ����ʹ��',				'ϵͳ����ʹ��')
insert roleinfo values(12,		'ϵͳ����ֻ��ʹ��',		'ϵͳ����ֻ��ʹ��')
insert roleinfo values(13,		'ũ��ʹ��',						'ũ��ʹ��')
insert roleinfo values(14,		'ũ��ֻ��ʹ��',				'ũ��ֻ��ʹ��')

