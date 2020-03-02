/*说明：
DRN等同于电表的出厂编号(meterpara.made_no)
SGC对应供电所档案表的供电单位编号（orgpara.r_org_no)
KRN对应mppay_para中的密钥版本(mppay_para.key_version)
TI对应yffratepara中的费率编号(yffratepara.rateid),最好固定默认为：01，否则每次更改TI，DecoderKey也要变
*/


/*
insert diction values(3000,  '采集频率',  	'1',	 				1,  	'')
insert diction values(0,  '采集频率',  	'2',					2,	 	'')
insert diction values(0,  '采集频率',  	'3',	 				3,	 	'')
insert diction values(0,  '采集频率',  	'4',	 				4,	 	'')
insert diction values(0,  '采集频率',  	'5',	 				5,	 	'')
insert diction values(0,  '采集频率',  	'6',	 				6,	 	'')
insert diction values(0,  '采集频率',  	'7',	 				7,	 	'')
go*/


if (exists (select name from sysobjects where name='org_stspara'))
begin
	print '<<<<<<<Delete table 供电所STS参数表 on YDParaBen>>>>>>>'
	drop table org_stspara
end
go
print '-------Create table 供电所STS参数表 on YDParaBen-------'
create table org_stspara
(
	org_id				smallint	         not null,		/*SGC/所属供电所*/
	key_type			tinyint	 	   default 0 null,		/*密钥类型*/
	dkga					tinyint	 	   default 0 null,		/*算法类型*/
	vk1						varchar(512)  				 null,		/*VK1  RSA加密后的串*/
	vk2						varchar(512)  				 null,
	vk3						varchar(512)  				 null,
	vk4						varchar(512)  				 null,
	
	encrypt_type	tinyint			 default 0 null, 		/*加密类型  0:软加密  1:硬加密*/
	key_regno			tinyint			 default 0 null, 		/*加密寄存器类型 1-N*/
	
	kmf					varchar(128)					 null, /*kmf值*/
	reserve1			varchar(64)  					 null,
	reserve2			varchar(64)  					 null,
	CONSTRAINT 	org_stspara_index1 PRIMARY KEY (org_id)	
)
go
grant select on org_stspara to public
go


if (exists (select name from sysobjects where name='org_reg'))
begin
	print '<<<<<<<Delete table 供电所STS注册码参数表 on YDParaBen>>>>>>>'
	drop table org_reg
end
go
print '-------Create table 供电所STS注册码参数表 on YDParaBen-------'
create table org_reg
(
	org_id			smallint		not null,			/*所Id*/
	reg_type                smallint          default 0,		/*注册类型 1、日期,2、3、4、*/
	reg_user	        varchar(64)             not null,			/*注册用户名称*/
	reg_code                varchar(512)           	null,	 			/*编码*/
	use_flag                smallint          default 1,		/*注册码启用标志.0:不启用,1:启用*/
	CONSTRAINT org_reg_index1 PRIMARY KEY (org_id)					
)
go
grant select on org_reg to public
go
create unique  index org_reg_index2
on org_reg(org_id) 
go



if (exists (select name from sysobjects where name='meter_stspara'))
begin
	print '<<<<<<<Delete table 电表STS参数表 on YDParaBen>>>>>>>'
	drop table meter_stspara
end
go

print '-------Create table 电表STS参数表 on YDParaBen-------'
create table meter_stspara
(
	rtu_id						int    	    			not null,
	mp_id						smallint 						not null,
	
	keychange				tinyint			default 0 null, 	/*keychange flag ken/krn/sgc/ti/regno*/
	
	meter_key			varchar(64)  					 null,
	oldmt_key			varchar(64)  					 null,
	ken								int								 null,
	old_ken						int								 null,
	krn						tinyint				default 0 null,			/*KRN*/
	old_krn				tinyint				default 0 null,			
	kt						tinyint				default 0 null,	
	old_kt				tinyint				default 0 null,	
	ti						tinyint				default 1 null,			/*Tariff Index  - TI*/
	old_ti				tinyint								 null,
	regno					tinyint								 null,
	old_regno			tinyint								 null,

	drn						varchar(64)  					 null,
	old_drn				varchar(64)  					 null,
							
	sgc						varchar(64)  					 null,
	old_sgc 			varchar(64)  					 null,
	
	token1				varchar(64)  					 null,
	token2				varchar(64)  					 null,
	token3				varchar(64)  					 null,
	token4				varchar(64)  					 null,
	reserve1			varchar(64)  					 null,
	reserve2			varchar(64)  					 null,
	CONSTRAINT 	meter_stspara_index1 PRIMARY KEY (rtu_id, mp_id)	
)
go
grant select on meter_stspara to public
go


delete diction where type_no=1503

insert diction values(1503,  '预付费金额',			'0 kWh',	 							0, '')
insert diction values(1503,  '预付费金额',			'10 kWh',	 							10, '')
insert diction values(1503,  '预付费金额',			'20 kWh',	 							20, '')
insert diction values(1503,  '预付费金额',			'50 kWh',	 							50, '')
go
delete diction where type_no=1520
--一般工商业/居民  Industrial/Commercial/Residential
insert diction values(1520,  '费率类型',	'Tou Rate',						1, '')		--复费率
insert diction values(1520,  '费率类型',	'Step Rate',	 				2, '')		--阶梯费率
insert diction values(1520,  '费率类型',	'Single Rate',				3, '')		--单费率
go

delete diction where type_no=163
insert diction values(163,  '缴费方式',	'Token',			0,	'')
go


insert into yffratepara(id,describe,rateid,activdate,fee_type,rated_z,money_limit) values (1,'Single Tariff',1,20150101,3,0.52,10)
--insert into yffratepara(id,describe,rateid,activdate,fee_type,ratef_p,ratef_g,ratef_f,money_limit) values (2,'toutariff',5,20150101,4,9.00,7.22,11.85,10000)
--insert into yffratepara(id,describe,rateid,activdate,fee_type,ratej_type,ratej_num,ratej_r1,ratej_r2,ratej_r3,ratej_r4,ratej_r5,ratej_r6,ratej_td1,ratej_td2,ratej_td3,ratej_td4,ratej_td5,money_limit) values(3,'steptariff',1,20150101,0,1,6,3.33,4.73,4.83,4.93,7.98,9.38,75,200,300,400,600,10000)
go