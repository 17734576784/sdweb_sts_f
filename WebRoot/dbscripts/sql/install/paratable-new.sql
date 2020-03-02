use YDParaBen
go


-- *************** 数据字典表 ***************
if (exists (select name from sysobjects where name='diction'))
begin
	print '<<<<<<<Delete table 数据字典表 on YDParaBen>>>>>>>'
	drop table diction
end
go

print '-------Create table 数据字典表 on YDParaBen-------'
create table diction
(
	type_no			smallint			not null,				/*字典类型号*/
	type_name   varchar(32)   not null,				/*字典类型名*/
	item_name	  varchar(64)   not null,				/*字典项目名*/
	value       int           		null,				/*数值*/
	str_val			varchar(64)   		null,				/*字符值*/
	CONSTRAINT diction_index1 PRIMARY KEY (type_name,item_name)					
)
go
grant select on diction to public
go
create unique  index diction_index2
on diction(type_no,value) 
go

print '========Creating  view  数据字典表=========='
go
if exists (select * from sysobjects where name = 'dictionind')
drop view   dictionind
go

create view dictionind
as select distinct type_no, type_name from diction 
go



-- *************** 终端型号表 ***************
if (exists (select name from sysobjects where name='rtumodel'))
begin
	print '<<<<<<<Delete table 终端型号表 on YDParaBen>>>>>>>'
	drop table rtumodel
end
go

print '-------Create table 终端型号表 on YDParaBen-------'
create table rtumodel
(
	id					smallint	    			not null,		/*终端型号编号*/
	describe		varchar(64)	  			not null, 	/*名称*/
	data_cap		smallint	    					null,		/*数据容量*/
	factory			smallint	    					null,		/*生产厂家*/
	chan_type1	tinyint									null,		/*通道类型1*/
	chan_type2	tinyint									null,		/*通道类型2*/
	meter_port	tinyint									null,		/*电表端口号*/
	safe_inter	smallint								null,		/*心跳周期*/
	prot				tinyint									null,		/*通信协议*/
	pwd_flag		tinyint									null,		/*通信加密*/
	eve_report	tinyint									null,		/*事件主动上报*/
	task_report	tinyint									null,		/*任务主动上报*/
	addr_jz			tinyint									null,		/*地址录入进制*/
	pwd_id			tinyint									null,		/*密码算法编号*/
	key_id			smallint								null,		/*密钥*/
	jc_flag			tinyint      	default 0 null,		/*交流采样*/
	yc_flag			tinyint      	default 0 null,		/*远方抄表*/
	dyjc_flag		tinyint      	default 0 null,		/*电压监测*/
	xbjc_flag		tinyint      	default 0 null,		/*谐波监测*/
	pbjc_flag		tinyint      	default 0 null,		/*配变监测*/
	bdzjc_flag	tinyint      	default 0 null,		/*变电站监测*/
	jc_id				tinyint									null,		/*交采编号*/
	jcfl_num		tinyint									null,		/*交采费率个数*/
	dndata_int	tinyint									null,		/*电能示值整数*/
	dndata_dec	tinyint									null,		/*电能示值小数*/
	                          					
	prot_type		tinyint	 								null,		/*规约类型*/
	meter_num		smallint 								null,		/*采集电表总数*/
	day_datacap	tinyint									null,		/*日数据最大容量*/
	mon_datacap	tinyint									null,		/*月数据最大容量*/
	dbfl1				tinyint				default 1 null,		/*规约中多费率第一费率存储位置*/
	dbfl2				tinyint				default 2 null,		/*规约中多费率第二费率存储位置*/
	dbfl3				tinyint				default 3 null,		/*规约中多费率第三费率存储位置*/
	dbfl4				tinyint				default 4 null,		/*规约中多费率第四费率存储位置*/
	
	CONSTRAINT rtumodel_index1 PRIMARY KEY (id)	
)
go
grant select on rtumodel to public
go


-- *************** 变电站档案表 ***************
if (exists (select name from sysobjects where name='substation'))
begin
	print '<<<<<<<Delete table 变电站档案表 on YDParaBen>>>>>>>'
	drop table substation

end 
go

print '-------Create table 变电站档案表 on YDParaBen-------'
create table substation
(
	id						smallint    	    	not null,	/*变电站编号*/
	describe			varchar(64)  	    	not null,	/*名称*/
  r_subst_no		varchar(32)							null,	/*供电单位编号(SG186)--hzhw20110708*/	
	volt_grade		tinyint	    	default 0 null,	/*电压等级*/
	tf_num				tinyint		    					null,	/*变压器个数*/
	tf_cap				int											null,	/*变压器总容量*/	
	addr					varchar(64)	      			null,	/*地址*/
	postalcode 		varchar(32)	    				null,	/*邮政编码*/
	link_man			varchar(32)	    				null,	/*联系人*/
	tel_no				varchar(32)	    				null,	/*联系电话*/
	status				tinyint 			default 0 null,	/*运行状态*/
	run_date			int											null,	/*投运日期*/
	orderno				smallint								null,	/*顺序号*/
	CONSTRAINT substation_index1 PRIMARY KEY (id)						
)
go
grant select on substation to public
go

-- *************** 线路档案表 ***************
if (exists (select name from sysobjects where name='linepara'))
begin
	print '<<<<<<<Delete table 线路档案表 on YDParaBen>>>>>>>'
	drop table linepara
end
go

print '-------Create table 线路档案表 on YDParaBen-------'
create table linepara
(
	id						smallint								not null,		/*线路编号*/
	describe			varchar(64)    	  			not null,		/*名称*/
	type      		tinyint	     			default 0 null,		/*类型*/
	volt_grade		tinyint	     			default 0 null,		/*电压等级*/
	subs_id				smallint     			default 0 null,		/*变电站编号*/
	line_length		decimal(9,2)								null,		/*线路长度*/
	orderno				smallint										null,		/*顺序号*/
	CONSTRAINT linepara_index1 PRIMARY KEY (id)				
)
go
grant select on linepara to public
go

-- *************** 供电所档案表 ***************
if (exists (select name from sysobjects where name='orgpara'))
begin
	print '<<<<<<<Delete table 供电所档案表 on YDParaBen>>>>>>>'
	drop table orgpara
end
go

print '-------Create table 供电所档案表 on YDParaBen-------'
create table orgpara
(
	id						smallint    	   	not null,				/*供电所编号*/
	describe			varchar(64)  	   	not null,				/*名称*/
	r_org_no			varchar(32)						null,				/*供电单位编号(SG186)*/
	addr					varchar(64)						null,				/*地址*/
	postalcode 		varchar(32)	    			null,				/*邮政编码*/
	mail					varchar(64)						null,				/*电子邮件*/			--zkzadd101204
	fax						varchar(64)						null,				/*传真*/					--zkzadd101204
	link_man			varchar(32)	    			null,				/*联系人*/
	telno					varchar(32)						null,				/*联系电话*/
	orderno				smallint							null,				/*顺序号*/
	CONSTRAINT orgpara_index1 PRIMARY KEY (id)				
)
go
grant select on orgpara to public
go



-- *************** 线路负责人定义表 ***************
if (exists (select name from sysobjects where name='line_fzman'))
begin
	print '<<<<<<<Delete table 线路负责人定义表 on YDParaBen>>>>>>>'
	drop table line_fzman
end
go

print '-------Create table 线路负责人定义表 on YDParaBen-------'
create table line_fzman
(
	id					smallint      not null,					/*线路负责人编号*/
	describe		varchar(64)				null,					/*名称*/
	org_id			smallint      not null,					/*所属供电所*/
	telno1			varchar(64)				null,					/*联系电话1*/
	telno2			varchar(64)				null,					/*联系电话2*/
	addr				varchar(64)				null,					/*地址*/
	orderno			smallint					null,					/*顺序号*/
	reserve1 		varchar(64)  			null,					/*扩展字段1*/
	reserve2 		varchar(64)  			null,					/*扩展字段2*/
	CONSTRAINT line_fzman_index1 PRIMARY KEY (id)					
)
go
grant select on line_fzman to public
go

-- *************** 安装人员定义表 ***************
if (exists (select name from sysobjects where name='install_man'))
begin
	print '<<<<<<<Delete table 安装人员定义表 on YDParaBen>>>>>>>'
	drop table install_man
end
go

print '-------Create table 安装人员定义表 on YDParaBen-------'
create table install_man
(
	id					smallint        	not null,					/*安装人员编号*/
	describe		varchar(64)						null,					/*名称*/
	--org_id			smallint        not null,					/*所属供电所*/ --暂时不适用
	telno1			varchar(64)						null,					/*联系电话1*/
	telno2			varchar(64)						null,					/*联系电话2*/
	addr				varchar(64)						null,					/*地址*/
	reserve1 		varchar(64)  					null,					/*扩展字段1*/
	reserve2 		varchar(64)  					null,					/*扩展字段2*/
	CONSTRAINT install_man_index1 PRIMARY KEY (id)					
)
go
grant select on install_man to public
go

insert into install_man (id, describe) values (0, 'steward person')
go


-- *************** 通道通讯配置表***************
go
if exists (select * from sysobjects where name = 'chancommcfg')
begin
	print '<<<<<<<Delete table 通道通讯配置表 on YDParaBen>>>>>>>'
	drop table chancommcfg 
end
go

print '-------Create table 通道通讯配置表 on YDParaBen-------'
create table chancommcfg
(
	commdev_type  tinyint			not null,
	describe			varchar(64)			null,			 		/*描述*/
	def_timeout		smallint				null,					/*默认超时时间*/
	min_timeout		smallint				null,					/*最小超时时间*/
	max_timeout		smallint				null,					/*最大超时时间*/
	CONSTRAINT chancommcfg_index PRIMARY KEY (commdev_type)						  					
)
go
grant  select  on  chancommcfg  to public
go


-- *************** 通道档案表 ***************
if (exists (select name from sysobjects where name='chanpara'))
begin
	print '<<<<<<<Delete table 通道档案表 on YDParaBen>>>>>>>'
	drop table chanpara
end
go

print '-------Create table 通道档案表 on YDParaBen-------'
create table chanpara
(
	id						smallint    	    not null,	/*编号*/
	describe			varchar(64)  	    not null,	/*名称*/
	use_flag			tinyint	    default 0 null,	/*使用标志*/
	commdev_type	tinyint			default 0 null,	/*通讯介质类型---hzhw   终端通道类型*/   
	--chan_type			tinyint	      		default 0 null,	/*通道类型---hzhw*/
	net_flag			tinyint								null,	/*网络标志---hzhw*/	
	prot_type			tinyint	 							null,	/*通信协议*/	
	chan_port			tinyint       				null,	/*通道端口*/
	baud					int    								null,	/*波特率*/
	check_bit			tinyint	 							null,	/*校验位*/
	data_bit			tinyint	 							null,	/*数据位*/
	stop_bit			tinyint	 							null,	/*停止位*/
	ip_addr				varchar(32)  					null,	/*网络IP*/
	ip_port				int        						null,	/*网络端口*/
	front_name		varchar(32)				not null,	/*所属前置机*/
	nlb_flag			tinyint			default 0 null,	/*负载均衡标志*/
	timeout				smallint		default 0 null,	/*通讯超时时间 单位 秒*/
	sync_time			tinyint								null,	/*对时标志*/		
	ras_id				tinyint								null,	/*无线拨号ID, 通道使用的RAS主站无线拨号ID---hzhw*/	
	CONSTRAINT chanpara_index1 PRIMARY KEY (id)			
)
go
grant select on chanpara to public
go

-- *************** SIM卡档案表 ***************
if (exists (select name from sysobjects where name='simcard'))
begin
	print '<<<<<<<Delete table SIM卡档案表 on YDParaBen>>>>>>>'
	drop table simcard
end
go
--hzhw
print '-------Create table SIM卡档案表 on YDParaBen-------'
create table simcard
(
	id						smallint	    	not	null,		/*编号*/
	tel_no				varchar(32)	    		null,		/*电话号码*/
	ip_addr				varchar(32)					null,		/*对应IP*/
	serial_no			varchar(32)					null,		/*序列号*/
	type					tinyint	  default 0	null,		/*类型:GPRS, CDMA, GSM*/
	app_type			varchar(64)					null,		/*业务类型/办理的业务*/
	actual_flux		int									null,		/*实际流量*/
	overrun_flux	int									null,		/*超流量*/
	status				tinyint   default 0 null,		/*运行状态*/
	run_date			int									null,		/*投运日期*/
	stop_date			int									null,		/*停运日期*/
	org_id				smallint        		null,		/*供电所编号*/	
	CONSTRAINT simcard_index1 PRIMARY KEY (id)	
)
go


create unique  index simcard_index2 
on simcard (tel_no)  
go
grant select on simcard to public
go


-- *************** 客户档案表 ***************
if (exists (select name from sysobjects where name='conspara'))
begin
	print '<<<<<<<Delete table 客户档案表 on YDParaBen>>>>>>>'
	drop table conspara
end
go

print '-------Create table 客户档案表 on YDParaBen-------'
create table conspara
(
	id						smallint    			not null,			/*编号*/
	describe			varchar(64)    	  not null,			/*名称*/
	importantf 		tinyint								null,			/*重点用户*/
	busi_no				varchar(64)						null,			/*营业户号*/
	addr					varchar(64)	      		null,			/*地址*/
	postalcode 		varchar(32)	    			null,			/*邮政编码*/	
	fz_man				varchar(32)	    			null,			/*负责人*/
	tel_no1				varchar(32)	    			null,			/*联系电话1*/
	tel_no2				varchar(32)	    			null,			/*联系电话2*/
	tel_no3				varchar(32)	    			null,			/*联系电话3 供电单位编号 jackadd 孟加拉 20150304*/		
	power_type		tinyint	    default 0 null,			/*电源类型*/
	volt_grade		tinyint	    default 0 null,			/*电压等级*/	--zkzadd101204
	trade1				tinyint								null,			/*所属行业1*/
	trade2				tinyint								null,			/*所属行业2*/
	trade3				tinyint								null,			/*所属行业3*/
	trade4				tinyint								null,			/*所属行业4*/
                                  		
	app_type			tinyint								null,			/*应用类型*/
	subst_id			smallint							null,			/*所属变电站*/
	line_id				smallint							null,			/*所属线路*/
	org_id				smallint							null,			/*所属供电所*/
	line_fzman_id	smallint							null,			/*线路负责人*/
	tr_model			tinyint								null,			/*变压器属性*/
	data_src			tinyint								null,			/*数据来源*/
	cont_cap			decimal(9,2)					null,			/*立约容量*/
	zg_cap				decimal(9,2)					null,			/*主供容量*/
	bak_cap				decimal(9,2)					null,			/*备用容量*/
	self_cap			decimal(9,2)					null,			/*自发容量*/
	safe_load			decimal(9,2)					null,			/*保安负荷*/
	cos_std				decimal(3,2)					null,			/*COS考核标准*/
	kjc_load			decimal(9,2)					null,			/*可监测负荷*/
	kk_load				decimal(9,2)					null,			/*可控负荷*/
	cb_day				tinyint								null,			/*抄表日*/
	vjc_point			tinyint								null,			/*电压检测测量点*/

	/*有序用电标识*/
	kcf_flag			tinyint								null,			/*可错峰*/
  kbf_flag			tinyint								null,			/*可避峰*/
	kxh_flag			tinyint								null,			/*可限荷*/
	kzd_flag			tinyint								null,			/*可中断负荷*/
	kjh_flag    	tinyint								null,			/*可计划负荷*/
                                  		
	remark				varchar(128)					null,			/*说明*/
	power_orderno	smallint							null,			/*电网结构顺序号*/
	mana_orderno	smallint							null,			/*管理结构顺序号*/
	reserve1   		varchar(32)  					null,			/*扩展字段1*/
	CONSTRAINT conspara_index1 PRIMARY KEY (id)				
)
go
grant select on conspara to public
go
create index conspara_index2 
on conspara(org_id,id)  
go
create index conspara_index3 
on conspara(subst_id,line_id,id)  
go

-- *************** 终端档案表 ***************
if (exists(select name from sysobjects where name='rtupara'))
begin
	print '<<<<<<<<Delete table 终端档案表 on YDParaBen>>>>>>>>'
	drop table rtupara
end
go

print '--------Create table 终端档案表 on YDParaBen--------'
create table rtupara
(
	id						int    									not null,		/*编号*/
	describe			varchar(64)    	  			not null,		/*名称*/
	use_flag			tinyint	      		default 0 null,		/*使用标志*/
	app_type			tinyint	 										null,		/*应用类型	变电站终端不要建立用户*/
	subst_id			smallint										null,		/*所属变电站 - 变电站终端没有所属客户*/
	                                					
	rtu_model			smallint										null,		/*终端型号*/
	prot_type			tinyint	 					default 0 null,		/*通信协议*/
	rtu_addr			int											not null,		/*地址编码*/
	area_code			varchar(16)									null,		/*行政编码 --hzhw*/	
	chan_main			smallint				 default -1 null,		/*主通道编号*/
	chan_bak			smallint				 default -1 null,		/*备通道编号*/
	simcard_id		smallint										null,		/*SIM卡编号*/
	task_num			tinyint						default 0 null,		/*任务总数*/
	resident_num  smallint 					default 0 null,		/*居民用户总数*/
	jlp_num				smallint					default 0 null,		/*测量点个数---hzhw*/
	zjg_num				smallint					default 0 null,		/*总加组个数*/
	yff_flag			tinyint											null,		/*预付费标志*/
	cons_id				smallint										null,		/*所属客户*/
	subproj_id		smallint										null,		/*变电站测量点方案表*/
	fzcb_id				smallint										null,		/*冻结及抄表日模板 ---hzhw*/
	factory				tinyint						default 0 null,		/*生产厂家*/
	rv						decimal(5,1)								null,		/*额定电压*/
	ctrl_type			smallint					default 0 null,		/*控制类型*/
	run_status		smallint					default 0 null,		/*运行状态*/
	orderno				smallint										null,		/*顺序号*/
	inst_site			tinyint											null,		/*安装地点*/
	inst_man			tinyint											null,		/*安装人员*/
	inst_date			int													null,		/*安装日期*/
	stop_date			int													null,		/*停运日期*/
	run_date			int													null,		/*投运日期*/
	made_no				varchar(32)									null,		/*出厂编号*/		
	asset_no			varchar(32)									null,		/*资产编号*/
	bar_code			varchar(32)									null,		/*条形码*/
	inf_code1			varchar(32)									null,		/*接口编码1*/	
	inf_code2			varchar(32)									null,		/*接口编码2*/	
	inf_code3			varchar(32)									null,		/*接口编码3*/		
	reserve1   		varchar(32)  								null,		/*扩展字段1*/	
	reserve2   		varchar(32)  								null,		/*扩展字段2*/	
	CONSTRAINT rtupara_index1 PRIMARY KEY (id)					
)
go
grant select on rtupara to public
go
create index rtupara_index2 
on rtupara(cons_id,id)  
go
create index rtupara_index3 
on rtupara(chan_main,id)  
go

-- *************** 终端扩展档案表 ***************
if (exists(select name from sysobjects where name='rtu_extpara'))
begin
	print '<<<<<<<<Delete table 终端扩展档案表 on YDParaBen>>>>>>>>'
	drop table rtu_extpara
end
go

print '--------Create table 终端扩展档案表 on YDParaBen--------'
create table rtu_extpara
(
	id							int    	    		not null,					/*终端编号*/	
	type1_rp_affirm	tinyint							null,					/*一类数据上报确认*/
	type2_rp_affirm	tinyint							null,					/*二类数据上报确认*/
	type3_rp_affirm	tinyint							null,					/*三类数据上报确认*/		
	CONSTRAINT rtu_extpara_index1 PRIMARY KEY (id)					
)
go
grant select on rtu_extpara to public
  

-- *************** 终端通信参数表 ***************
if (exists(select name from sysobjects where name='rtucomm_para'))
begin
	print '<<<<<<<<Delete table 终端通信参数表 on YDParaBen>>>>>>>>'
	drop table rtucomm_para
end
go

print '--------Create table 终端通信参数表 on YDParaBen--------'
create table rtucomm_para
(
	id							int    	    				not null,			/*终端编号*/
	rtu_ipaddr			varchar(32)							null,			/*终端IP地址*/
	rtu_ipmask			varchar(32)							null,			/*终端IP子网掩码*/
	rtu_ipport			int        							null,			/*终端IP端口*/
	telno						varchar(32)							null,			/*Modem电话---hzhw GSM电话号码*/
	--modem_no				varchar(32)						null,			/*Modem电话*/


	auth_code				varchar(32)							null,			/*消息认证码-通信密码*/
	auth_codelen		tinyint			 default 16 null,			/*消息认证码长度*/
	auth_code_fano	int											null,			/*消息认证方案号*/
	auth_code_facs	int											null,			/*消息认证方案参数*/
	
	online_type			tinyint	 			default 0 null,			/*网络在线方式*/
	nettcp_type			tinyint	 			default 0 null,			/*网络TCP类型 --居民的，不知用途*/

	safe_inter			tinyint									null,			/*心跳周期*/
	
	--终端使用代理方式通信
	proxy_type			tinyint									null,			/*代理类型*/
	proxy_ipaddr		varchar(32)							null,			/*代理IP地址*/
	proxy_ipport		int											null,			/*代理端口*/
	proxy_linktype	tinyint									null,			/*代理服务器连接方式*/
	proxy_username	varchar(32)							null,			/*代理用户名*/
	proxy_userpwd		varchar(32)							null,			/*代理密码*/
	                                				
	--终端使用虚拟专网方式通信      				
	apn_name				varchar(32)							null,			/*APN名称*/
	vpn_user				varchar(32)							null,			/*虚拟专网用户名*/
	vpn_pwd					varchar(32)							null,			/*虚拟专网密码*/
                                  				
	master_ipaddr1	varchar(32)							null,			/*主站IP地址1*/
	master_ipport1	int											null,			/*主站端口1*/
	master_ipaddr2	varchar(32)							null,			/*主站IP地址2*/
	master_ipport2	int											null,			/*主站端口2*/
	gateway_ipaddr	varchar(32)							null,			/*网关IP地址*/
	gateway_ipport	int											null,			/*网关端口*/
                                  				
	mst_telno				varchar(32)							null,			/*主站电话号码*/
	sca_no					varchar(32)							null,			/*短信中心号码*/		
                                  				
	relay_flag			tinyint									null,			/*中继转发标志*/
	relay_type			tinyint				default 0 null,   	/*中继方式 0:自动中继: 1固定中继*/
	CONSTRAINT rtucomm_para_index1 PRIMARY KEY (id)						
)
go
grant select on rtucomm_para to public
go


-- *************** 终端通信扩展参数表 ***************
if (exists(select name from sysobjects where name='rtucomm_extpara'))
begin
	print '<<<<<<<<Delete table 终端通信扩展参数表 on YDParaBen>>>>>>>>'
	drop table rtucomm_extpara
end
go

print '--------Create table 终端通信扩展参数表 on YDParaBen--------'
create table rtucomm_extpara
(
	id							int    	    	not null,					/*编号*/
	scj_delay_time	tinyint						null,					/*数传机延时时间*/
	snd_delay_time	tinyint						null,					/*发送传输延时时间*/
	xy_overtime			smallint					null,					/*响应超时时间*/
	wt_rsend_times	tinyint						null,					/*等待响应重发次数*/
	
	nocomm_maxtime	tinyint						null,					/*允许连续无通信时间*/
	pubcomm_mode		tinyint						null,					/*公网通信模块工作模式*/
	altm_recalltime	smallint					null,					/*永久在线模式重拨间隔*/
	bdjh_recalltime	tinyint						null,					/*被动激活模式重拨次数*/
	bdwt_maxtime		tinyint						null,					/*被动无通信断线时间*/
	online_times		varchar(32)				null,					/*在线时段*/
	
	--集抄通信参数
	jc_commport			tinyint						null,					/*集抄通信端口号*/
	jc_ctlstr				varchar(16)				null,					/*集抄通信控制字*/
	wtf_fm_time			tinyint						null,					/*接收等待报文超时时间*/
	wtf_bt_time			tinyint						null,					/*接收等待字节超时时间*/
	cvf_rs_times		tinyint						null,					/*抄表方接收失败重发次数*/
	relay_num				tinyint						null,					/*中继器数量*/
	jcch_gate_type	tinyint						null,					/*集抄通道路由方式*/
	jcch_gate_js		tinyint						null,					/*集抄通道路由级数*/	
	
	CONSTRAINT rtucomm_extpara_index1 PRIMARY KEY (id)							
)
go
grant select on rtucomm_extpara to public
go


--tszk ???
if (exists (select name from sysobjects where name='jrtucport_para'))
begin
	print '<<<<<<<<Delete table 居民集抄通信端口参数表 on YDParaBen>>>>>>>>'
	drop table jrtucport_para
end
go
print '--------Create table 居民集抄通信端口参数表 on YDParaBen--------'
create table jrtucport_para
(

--集抄端口通信参数
	rtu_id					int	    			not	null,					/*终端编号*/
	jc_commport			tinyint				not null,					/*集抄通信端口号*/
	jc_ctlstr				varchar(16)				null,					/*集抄通信控制字*/
	wtf_fm_time			tinyint						null,					/*接收等待报文超时时间*/
	wtf_bt_time			tinyint						null,					/*接收等待字节超时时间*/
	cvf_rs_times		tinyint						null,					/*抄表方接收失败重发次数*/
	
	CONSTRAINT jrtucport_index1 PRIMARY KEY (rtu_id, jc_commport)
)
go
grant select on jrtucport_para to public
go


-- *************** 冻结及抄表日模板参数表 ***************
if (exists(select name from sysobjects where name='fzcb_template'))
begin
	print '<<<<<<<<Delete table 冻结及抄表日模板参数表 on YDParaBen>>>>>>>>'
	drop table fzcb_template
end
go

print '--------Create table 冻结及抄表日模板参数表 on YDParaBen--------'
create table fzcb_template
(
	id							smallint    	  not null,					/*编号*/
	describe				varchar(64)					null,					/*名称*/
	min_fztime			int									null,					/*分钟冻结间隔*/
	day_fztime			int									null,					/*日冻结时间*/
	mon_fztime			int									null,					/*月冻结时间*/
	cbday_num				tinyint							null,					/*抄表日个数*/
	cbday1_time			int									null,					/*抄表日1*/
	cbday2_time			int									null,					/*抄表日2*/	
	cbday3_time			int									null,					/*抄表日3*/		
	cbday4_time			int									null,					/*抄表日4*/
	CONSTRAINT fzcb_template_index1 PRIMARY KEY (id)					
)
go
grant select on fzcb_template to public



-- *************** 测量点档案表 ***************
if (exists (select name from sysobjects where name='mppara'))
begin
	print '<<<<<<<Delete table 测量点档案表 on YDParaBen>>>>>>>'
	drop table mppara
end
go

print '-------Create table 测量点档案表 on YDParaBen-------'
create table mppara
(
	rtu_id						int			    	    not null,		/*终端编号*/
	id								smallint 					not null,		/*编号*/
	describe					varchar(64)	    			null,		/*名称*/
	use_flag					tinyint			default 0 null, 	/*使用标志*/
	mp_type						tinyint		    		not	null, 	/*测量点类型*/
	cons_type					tinyint								null,		/*居民类型 普通用户、重点用户、总表*/
	                                    		
	pt_numerator  		int										null,		/*PT分子*/
	pt_denominator 		int										null,		/*PT分母*/
	pt_ratio 					decimal(8,2)					null,		/*PT变比*/
	ct_numerator  		int										null,		/*CT分子*/
	ct_denominator 		int										null,		/*CT分母*/   /*最大功率激活日期  jackadd 孟加拉 20150304*/
	ct_ratio 					decimal(8,2)					null,		/*CT变比*/
                                      		
	rv								decimal(5,1)					null,		/*额定电压*/
	ri								decimal(5,1)					null,		/*额定电流*/	
	rp								decimal(5,1)					null,		/*额定负荷*/ /*最大功率峰值   jackadd 孟加拉 20150304*/
	mi								decimal(5,1)					null,		/*最大电流*/ /*最大功率非峰值 jackadd 孟加拉 20150304*/
	wiring_mode				tinyint								null,		/*接线方式*/
	
	bak_flag					tinyint	 		default 0 null,	  /*备用标志*/
	main_id						smallint        			null,		/*主测量点编号*/
	
	bd_factor					smallint   default 0  null,		/*表底系数*/ /*最大功率开始时间hh jackadd 孟加拉 20150304*/
	v_factor					smallint   default 0  null,		/*电压系数*/ /*最大功率结束时间hh jackadd 孟加拉 20150304*/
	i_factor					smallint   default 0  null,		/*电流系数*/
	p_factor					smallint   default 0  null,		/*功率系数*/
	cs_factory				smallint   default 0  null,		/*功率因数系数*/
			                                          	
	cl_fa       			smallint   default 0  null,		/*变电站表计参数 小数位*/

	inf_code1					varchar(32)						null,		/*接口编码1*/	
	inf_code2					varchar(32)						null,		/*接口编码2*/	
	inf_code3					varchar(32)						null,		/*接口编码3  SG186导入标志,SG186导入方案,SG186计量点编号*/		
	reserve1   				varchar(32)  					null,		/*扩展字段1 周末日前八个字符代表 654321日使用标志 jackadd 孟加拉 20150304*/
	
	CONSTRAINT mppara_index1 PRIMARY KEY (rtu_id,id)								
)
go
grant select on mppara to public
go

-- *************** 电能表参数表 ***************
if (exists (select name from sysobjects where name='meterpara'))
begin
	print '<<<<<<<Delete table 电能表参数表 on YDParaBen>>>>>>>'
	drop table meterpara
end
go

print '-------Create table 电能表参数表 on YDParaBen-------'
create table meterpara
(
	rtu_id						int    	    			not null,		/*终端编号*/
	mp_id							smallint 					not null,		/*测量点编号*/
	describe					varchar(64)	    			null,		/*名称 ---hzhw*/	
	use_flag			    tinyint	    default 0 null,		/*使用标志 ---hzhw*/
	resident_id				smallint							null,		/*居民用户编号*/
	                                    		
	metermodel_id			smallint        			null,		/*表型号*/
--	wiring_mode				tinyint							null,		/*接线方式*/	//与测量点中接线方式冲突 去掉

	equip_idx					tinyint		    				null,		/*装置序号*/
	comm_speed				int										null, 	/*通信速率*/
	comm_port					tinyint		    				null, 	/*通信端口号*/
	comm_addr					varchar(16)						null,		/*通信地址*/
	comm_prot					tinyint		 						null,		/*通信规约*/
	comm_pwd					varchar(16)						null,		/*通信密码*/
	meter_id     			varchar(16)     			null,   /*ESAM表号*/   --zkz 110110 远程控制及付费时使用。
	                                    		
	fl_num						tinyint								null,		/*电能费率个数*/
	dndata_int				tinyint								null,		/*电量示值整数位*/
	dndata_dec				tinyint								null,		/*电量示值小数位*/	
                                      		
	data_bit					tinyint								null,		/*数据位*/
	stop_bit					tinyint								null,		/*停止位*/
	check_bit					tinyint								null,		/*校验位*/

	p_offset					smallint		default 0 null,		/*有功偏移地址*/
	q_offset					smallint		default 1 null,		/*无功偏移地址*/

	subst_id					smallint							null,		/*变电站编号*/
	line_id						smallint        			null,		/*线路编号*/--变电站出线表为线路总表
	                                    		
	made_no						varchar(32)						null,		/*出厂编号*/			
	asset_no					varchar(32)						null,		/*资产编号*/
	bar_code					varchar(32)						null,		/*条形码*/	
	js_code						varchar(32)						null,		/*局属编号*/
	factory						tinyint								null,		/*厂家*/
	                                    		
	big_kind					tinyint								null,		/*--用户大类号*/
	small_kind				tinyint								null,		/*--用户小类号*/
	                                    		
	                                    		
	prepayflag				tinyint								null,		/*预付费标志*/
	status						tinyint								null,		/*电表当前状态*/--在居民中显示为加载状态--
	                                    		
	inf_code1					varchar(32)						null,		/*接口编码1*/	
	inf_code2					varchar(32)						null,		/*接口编码2*/	
	inf_code3					varchar(32)						null,		/*接口编码3 --所属采集器通信地址*/		
	reserve1   				varchar(32)  					null,		/*扩展字段1*/		
	                                    		
	--所属采集器通信地址	varchar(32)				null,
	

	CONSTRAINT meterpara_index1 PRIMARY KEY (rtu_id, mp_id)				
)
go
grant select on meterpara to public
go



-- *************** 居民用户参数表 ***************
if (exists (select name from sysobjects where name='residentpara'))
begin
	print '<<<<<<<Delete table 居民用户参数表 on YDParaBen>>>>>>>'
	drop table residentpara
end
go

print '-------Create table 居民用户参数表 on YDParaBen-------'
create table residentpara
(
	rtu_id						int    	    			not null,					/*终端编号*/
	id								smallint	    		not null,					/*居民用户编号*/
	describe		      varchar(64)						null,					/*姓名*/
	cons_no						varchar(64)						null,					/*居民户号*/
	address           varchar(64)						null,					/*居民用电地址*/
	post              varchar(64)						null,					/*居民邮编*/
 	phone             varchar(64)						null,					/*电话*/
  mobile	         	varchar(64)						null,					/*移动电话*/
  fax               varchar(64)						null,					/*传真*/
  mail 							varchar(64)						null, 				/*电子邮件*/
                                      		
	inf_code1					varchar(32)						null,					/*接口编码1*/	
	inf_code2					varchar(32)						null,					/*接口编码2*/	
	inf_code3					varchar(32)						null,					/*接口编码3*/		
	reserve1   				varchar(32)  					null,					/*扩展字段1*/	
	CONSTRAINT residentpara_index1 PRIMARY KEY (rtu_id,id)				
)
go
grant select on residentpara to public
go

-- *************** 终端值表 ***************
if (exists(select name from sysobjects where name='rtudata'))
begin
	print '<<<<<<<<Delete table 终端值表 on YDParaBen>>>>>>>>'
	drop table rtudata
end
go

print '--------Create table 终端值表 on YDParaBen--------'
create table rtudata
(
	rtu_id					int    	    			not null,					/*终端编号*/
	frame_ok_cnt		smallint							null,					/*正确侦记数*/
	frame_err_cnt		smallint							null,					/*错误侦记数*/
	last_time				int										null,					/*最后通讯时间*/
	CONSTRAINT rtudata_index1 PRIMARY KEY (rtu_id)
)
go
grant select on rtudata to public
go



if (exists (select name from sysobjects where name='chgmeterrate'))
begin
	print '<<<<<<<Delete table 换表换倍率记录表 on YDParaBen>>>>>>>'
	drop table chgmeterrate
end
go

print '-------Create table 换表换倍率记录表 on YDParaBen-------'
create table chgmeterrate
(
	id						smallint       		not null,			/*编号*/
	rtu_id				int       				not null,			/*终端编号*/	
	mp_id					smallint 					not null,			/*测量点编号*/
	
	chg_date			int								not null,			/*更换日期*/
	chg_time			int 							not null,			/*更换时间*/	
	chg_type			smallint					not null,			/*更换类型*/

	oldct_numerator  		int							null,			/*旧CT分子*/
	oldct_denominator 	int							null,			/*旧CT分母*/
	oldct_ratio   decimal(8,2)        	null,			/*旧ct变比*/
	newct_numerator  		int							null,			/*新CT分子*/
	newct_denominator 	int							null,			/*新CT分母*/
	newct_ratio		decimal(8,2)     			null,			/*新CT变比*/
	
	oldpt_numerator  		int							null,			/*旧PT分子*/
	oldpt_denominator 	int							null,			/*旧PT分母*/		
	oldpt_ratio   decimal(8,2)        	null,			/*旧pt变比*/
	newpt_numerator  		int							null,			/*新PT分子*/
	newpt_denominator 	int							null,			/*新PT分母*/
	newpt_ratio		decimal(8,2)     			null,			/*新pt变比*/		

	old_zyz				decimal(12,2)					null,			/*旧正向总有功表底*/
	old_zyj				decimal(12,2)					null,			/*旧正向尖有功表底*/
	old_zyf				decimal(12,2)					null,			/*旧正向峰有功表底*/
	old_zyp				decimal(12,2)					null,			/*旧正向平有功表底*/
	old_zyg				decimal(12,2)					null,			/*旧正向谷有功表底*/
	old_fyz				decimal(12,2)					null,			/*旧反向总有功表底*/
	old_fyj				decimal(12,2)					null,			/*旧反向尖有功表底*/
	old_fyf				decimal(12,2)					null,			/*旧反向峰有功表底*/
	old_fyp				decimal(12,2)					null,			/*旧反向平有功表底*/
	old_fyg				decimal(12,2)					null,			/*旧反向谷有功表底*/
	old_zwz				decimal(12,2)					null,			/*旧正向总无功表底*/
	old_zwj				decimal(12,2)					null,			/*旧正向尖无功表底*/
	old_zwf				decimal(12,2)					null,			/*旧正向峰无功表底*/
	old_zwp				decimal(12,2)					null,			/*旧正向平无功表底*/
	old_zwg				decimal(12,2)					null,			/*旧正向谷无功表底*/
	old_fwz				decimal(12,2)					null,			/*旧反向总无功表底*/
	old_fwj				decimal(12,2)					null,			/*旧反向尖无功表底*/
	old_fwf				decimal(12,2)					null,			/*旧反向峰无功表底*/
	old_fwp				decimal(12,2)					null,			/*旧反向平无功表底*/
	old_fwg				decimal(12,2)					null,			/*旧反向谷无功表底*/

	new_zyz				decimal(12,2)					null,			/*新正向总有功表底*/
	new_zyj				decimal(12,2)					null,			/*新正向尖有功表底*/
	new_zyf				decimal(12,2)					null,			/*新正向峰有功表底*/
	new_zyp				decimal(12,2)					null,			/*新正向平有功表底*/
	new_zyg				decimal(12,2)					null,			/*新正向谷有功表底*/
	new_fyz				decimal(12,2)					null,			/*新反向总有功表底*/
	new_fyj				decimal(12,2)					null,			/*新反向尖有功表底*/
	new_fyf				decimal(12,2)					null,			/*新反向峰有功表底*/
	new_fyp				decimal(12,2)					null,			/*新反向平有功表底*/
	new_fyg				decimal(12,2)					null,			/*新反向谷有功表底*/
	new_zwz				decimal(12,2)					null,			/*新正向总无功表底*/
	new_zwj				decimal(12,2)					null,			/*新正向尖无功表底*/
	new_zwf				decimal(12,2)					null,			/*新正向峰无功表底*/
	new_zwp				decimal(12,2)					null,			/*新正向平无功表底*/
	new_zwg				decimal(12,2)					null,			/*新正向谷无功表底*/
	new_fwz				decimal(12,2)					null,			/*新反向总无功表底*/
	new_fwj				decimal(12,2)					null,			/*新反向尖无功表底*/
	new_fwf				decimal(12,2)					null,			/*新反向峰无功表底*/
	new_fwp				decimal(12,2)					null,			/*新反向平无功表底*/
	new_fwg				decimal(12,2)					null,			/*新反向谷无功表底*/

	add_zyz				decimal(12,2)					null,			/*追加正向总有功电量*/
	add_zyj				decimal(12,2)					null,			/*追加正向尖有功电量*/
	add_zyf				decimal(12,2)					null,			/*追加正向峰有功电量*/
	add_zyp				decimal(12,2)					null,			/*追加正向平有功电量*/
	add_zyg				decimal(12,2)					null,			/*追加正向谷有功电量*/
	add_fyz				decimal(12,2)					null,			/*追加反向总有功电量*/
	add_fyj				decimal(12,2)					null,			/*追加反向尖有功电量*/
	add_fyf				decimal(12,2)					null,			/*追加反向峰有功电量*/
	add_fyp				decimal(12,2)					null,			/*追加反向平有功电量*/
	add_fyg				decimal(12,2)					null,			/*追加反向谷有功电量*/
	add_zwz				decimal(12,2)					null,			/*追加正向总无功电量*/
	add_zwj				decimal(12,2)					null,			/*追加正向尖无功电量*/
	add_zwf				decimal(12,2)					null,			/*追加正向峰无功电量*/
	add_zwp				decimal(12,2)					null,			/*追加正向平无功电量*/
	add_zwg				decimal(12,2)					null,			/*追加正向谷无功电量*/
	add_fwz				decimal(12,2)					null,			/*追加反向总无功电量*/
	add_fwj				decimal(12,2)					null,			/*追加反向尖无功电量*/
	add_fwf				decimal(12,2)					null,			/*追加反向峰无功电量*/
	add_fwp				decimal(12,2)					null,			/*追加反向平无功电量*/
	add_fwg				decimal(12,2)					null,			/*追加反向谷无功电量*/
	
	reserve1			decimal(12,2)					null,			/*扩展字段*/

	CONSTRAINT chgmeterrate_index1 PRIMARY KEY (id)
)
go
grant select on chgmeterrate to public
go


-- *************** 事项类型配置表 ***************
go
if exists (select * from sysobjects where name = 'eventtype')
begin
	print '<<<<<<<Delete table 事项类型配置表 on YDParaBen>>>>>>>'
	drop table eventtype
end
go

print '-------Create table 事项类型配置表 on YDParaBen-------'
create table eventtype
(
	classno					 smallint         not null,
	typeno					 smallint         not null,
	describe         varchar(64)      not null,
	CONSTRAINT eventtype_index1 PRIMARY KEY (typeno)							
)
go
grant select on eventtype to public
go
create index eventtype_index2 on eventtype (classno, typeno)
go

/*************** 档案模板表 ***************/
if (exists (select name from sysobjects where name='doc_template'))
begin
	print '<<<<<<<Delete table 档案模板表 on YDParaBen>>>>>>>'
	drop table doc_template
end
go

print '-------Create table 档案模板表 on YDParaBen-------'
create table doc_template
(
	id					smallint			not null,		/*编号*/
	describe		varchar(64)	  not null,			  		/*描述*/
	app_type		tinyint				not null,			/*应用类型*/		
	tpldata 		image					null,			/*模板数据*/
	CONSTRAINT doc_template_index1 PRIMARY KEY (id)	
)
go
grant select on doc_template to public
go