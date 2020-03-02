use YDParaBen
go


/*hzhw20110618*/
-- *************** 预付费人员定义表 ***************
if (exists (select name from sysobjects where name='yffmandef'))
begin
	print '<<<<<<<Delete table 预付费人员定义表 on YDParaBen>>>>>>>'
	drop table yffmandef
end
go

print '-------Create table 预付费人员定义表 on YDParaBen-------'
create table yffmandef
(
	id						smallint       		 not null,
	name					varchar(32)   		 not null,
	describe      varchar(64)				 		 null,		/*描述*/
	passwd				varchar(32)     			 null,
	
	/*add*/
	apptype				tinyint	 			default 0 null,		/*应用类型权限范围*/
	
	ctrlflag			tinyint	 			default 0 null,		/*控制权限*/
	
	/*add*/
	openflag			tinyint			 	default 0 null,		/*开户销户权限*/
	payflag				tinyint	 			default 0 null,		/*购电 补卡换表 冲正权限*/
	paraflag			tinyint	 			default 0 null,		/*修改参数权限*/
	viewflag			tinyint	 			default 0 null,		/*报表查看*/
	rese1_flag		tinyint	 			default 0 null,		/*权限扩展1 已使用：作为档案权限*/
	rese2_flag		tinyint	 			default 0 null,		/*权限扩展2 已使用：作为用户管理权限*/
	rese3_flag		tinyint	 			default 0 null,		/*权限扩展3 已使用 ： 作为公共档案权限*/
	rese4_flag		tinyint	 			default 0 null,		/*权限扩展4*/
	
	rank					tinyint	 			default 0 null,		/*权限范围*/
	org_id				smallint								null,
	fzman_id			smallint								null,
	reserve1			varchar(64)  						null,
	reserve2			varchar(64)  						null,
	CONSTRAINT 	yffmandef_index1 PRIMARY KEY (id)	
)
go
grant select on yffmandef to public
go
create unique index yffmandef_index2
	on yffmandef(name)
go

insert into yffmandef(id,name,describe,passwd,apptype,ctrlflag,openflag,payflag,paraflag,rese1_flag,rese2_flag,rese3_flag,viewflag,rank, org_id) 
values(1,'sdadmin','sdadmin','123456',7,1,1,1,1,2,1,2,1,0, 1)

go

if (exists (select name from sysobjects where name='gloprotect'))
begin
	print '<<<<<<<Delete table 节假日全局保电参数表 on YDParaBen>>>>>>>'
	drop table gloprotect
end
go

print '-------Create table 节假日全局保电参数表 on YDParaBen-------'
create table gloprotect
(
	id						smallint      			not null,
	app_type			tinyint							not null,			/*预付费应用类型*/
	use_flag			tinyint	 			default 0 null,			/*使用标志*/
	bg_date				int						default 0 null,			/*启用日期-yyyymmdd*/
	bg_time				int						default 0 null,			/*启用时间-hhmiss*/
	
	ed_date				int						default 0 null,			/*结束日期-yyyymmdd*/
	ed_time				int						default 0 null,			/*结束时间-hhmiss*/
	
	reserve1			varchar(64)							null,			/*扩展字段1		*/
	reserve2			varchar(64)							null,			/*扩展字段2		*/
	reserve3			varchar(64)							null,			/*扩展字段3		*/
	reserve4			varchar(64)							null,			/*扩展字段4		*/	
	CONSTRAINT gloprotect_index1 PRIMARY KEY (id)
)
go
grant select on gloprotect to public
go

insert into gloprotect (id, app_type, use_flag, bg_date, bg_time, ed_date,ed_time) values (0, 0, 0, 20080101, 120000, 20080101, 120000)
insert into gloprotect (id, app_type, use_flag, bg_date, bg_time, ed_date,ed_time) values (1, 1, 0, 20080101, 120000, 20080101, 120000)
go


print  '-------Create table 测量点预付费参数表 on YDParaBen-------'
if exists (select * from sysobjects where name = 'mppay_para')
begin
	print '<<<<<<<Delete table 测量点预付费参数表 on YDParaBen>>>>>>>'
	drop table mppay_para
end
go

create table mppay_para
(
	rtu_id				int									not null,			/*终端编号*/
	mp_id					smallint 						not null,			/*测量点编号*/
	
	use_flag			tinyint	 			default 0 null,			/*使用标志*/
	
	cacl_type			tinyint				default 0 null,			/*算费类型rcd53 0:无 1:金额计费 2:表底计费 */
	feectrl_type	tinyint				default 0 null,			/*费控方式rcd43 0:本地费控 1:主站费控 */
	pay_type			tinyint				default 0 null,			/*缴费方式rcd163 0:卡式/Token 1:远程 2:主站 */
	writecard_no	varchar(32)							null,			/*信息输出*/
	yffmeter_type	tinyint									null,			/*预付费表类型rcd1504*/

	feeproj_id		smallint								null,			/*费率方案*/
	yffalarm_id		smallint 								null,			/*报警方案*/
	fee_begindate	int											null, 		/*费率启用日期*/
	
	prot_st				tinyint						 			null,			/*保电开始时间*/
	prot_ed				tinyint	 								null,			/*保电结束时间*/

	ngloprot_flag tinyint									null,			/*不参与全局保电标志*/

--fz_cl					tinyint									null,			/*分闸策略 0:默认策略 1:科林策略*/

	key_version		tinyint				default 0 null,			/*密钥版本, 单用于智能表 -STS表KRN*/
	--tariff_index	tinyint				default 1 null,			/*Tariff Index  - TI*/
	
	cryplink_id		tinyint				default 0 null,			/*所属加密机ID, 单用于智能表*/
	
	pay_add				int 										null,			/*缴费附加值*/	
	tz_val				int 					default 0 null,			/*透支值*/

	power_relaf		tinyint									null,			/*动力关联标志*/
	power_rela1		smallint								null,			/*动力关联1*/
	power_rela2		smallint								null,			/*动力关联2*/
	power_relask1	tinyint									null,			/*动力1可控标志*/
	power_relask2	tinyint									null,			/*动力2可控标志*/
	
	--费率更改
	fee_chgf			tinyint									null,			/*费率更改标志*/
	fee_chgid			smallint								null,			/*费率更改ID*/
	fee_chgdate		int											null,			/*费率更改日期*/
	fee_chgtime		int											null,			/*费率更改时间*/
	
	jt_cycle_md			smallint							null,			/*周期切换时间MMDD*/

	cb_cycle_type		tinyint								null, 		/*抄表周期类型 0:每月抄表 1:双月抄表  2:单月抄表 3:季度抄表 4：半年抄表 5：年抄表*/

	cb_dayhour			smallint							null,			/*抄表日DDHH*/
	js_day					tinyint								null,			/*结算日*/

	fxdf_flag				tinyint								null,			/*是否发行电费*/
	fxdf_begindate	int										null,		  /*发行电费起始日期*/
	
	local_maincalcf	tinyint								null,			/*主站算费标志	0 不算费， 1 算费*/

	--20140606 zkz
	ocardproj_id		int										null,			/*外接卡表类型编号*/
	card_rand				varchar(32)						null,			/*随机数*/	/* 现在是外接卡表使用*/
	card_pass				varchar(32)						null,			/*密码*/		/* 现在是外接卡表使用*/
	card_area				varchar(32)						null,			/*区域码*/	/* 现在是外接卡表使用*/
	--20140606 zkz end
	
	reserve1			int								   		null,	
	reserve2			int								   		null,	
	reserve3			int								   		null,	
	
	CONSTRAINT mppay_para_index1 PRIMARY KEY (rtu_id,mp_id)
)
go
grant select on mppay_para to public
go


if (exists (select name from sysobjects where name='mppay_state'))
begin
	print '<<<<<<<Delete table 测量点预付费状态表 on YDParaBen>>>>>>>'
	drop table mppay_state
end
go
print '-------Create table 测量点预付费状态表 on YDParaBen-------'
create table mppay_state
(
	rtu_id				int       					not null,			/*终端编号*/	
	mp_id					smallint 						not null,			/*测量点编号*/

	cus_state			tinyint									null,			/*用户状态 0 初始态, 1 正常态, 3:密钥更新 10 销户态*/	

	op_type				tinyint									null,			/*本次操作类型 0 初始态, 1 开户, 2 缴费, 3 补卡, 4 补写卡, 5 冲正, 6 换表, 7 换费率 10 销户 */	
	op_date				int											null,			/*本次操作日期*/	
	op_time				int											null,			/*本次操作时间*/	
	
	pay_money			decimal(12,2)						null,			/*缴费金额*/
	othjs_money		decimal(12,2)						null,			/*结算金额(其它系统)*/
	jy_money			decimal(12,2)						null,			/*结余金额*/
	zb_money			decimal(12,2)						null,			/*追补金额*/
	all_money			decimal(12,2)						null,			/*总金额*/
	
	--add 201406
	buy_dl				decimal(12,2)						null,			/*购电量*/
	pay_bmc				decimal(12,2)						null,			/*表码差*//*又名写卡电量*/
	--end
	
	shutdown_val	decimal(12,2)						null,			/*断电值 金额计费时为:断电金额 */	
	shutdown_val2	decimal(12,2)						null,			/*断电值 金额计费时为:断电金额 本地模拟电表 */	
	
	jsbd_zyz			decimal(12,2)						null,			/*结算总表底*/
	jsbd_zyj			decimal(12,2)						null,			/*结算尖表底*/
	jsbd_zyf			decimal(12,2)						null,			/*结算峰表底*/
	jsbd_zyp			decimal(12,2)						null,			/*结算平表底*/
	jsbd_zyg			decimal(12,2)						null,			/*结算谷表底*/

	jsbd1_zyz			decimal(12,2)						null,			/*动力关联1-结算总表底*/
	jsbd1_zyj			decimal(12,2)						null,			/*动力关联1-结算尖表底*/
	jsbd1_zyf			decimal(12,2)						null,			/*动力关联1-结算峰表底*/
	jsbd1_zyp			decimal(12,2)						null,			/*动力关联1-结算平表底*/
	jsbd1_zyg			decimal(12,2)						null,			/*动力关联1-结算谷表底*/

	jsbd2_zyz			decimal(12,2)						null,			/*动力关联2-结算总表底*/
	jsbd2_zyj			decimal(12,2)						null,			/*动力关联2-结算尖表底*/
	jsbd2_zyf			decimal(12,2)						null,			/*动力关联2-结算峰表底*/
	jsbd2_zyp			decimal(12,2)						null,			/*动力关联2-结算平表底*/
	jsbd2_zyg			decimal(12,2)						null,			/*动力关联2-结算谷表底*/

	jsbd_ymd			int											null,			/*结算时间*/
	buy_times			int											default 0,			/*购电次数*/

	calc_mdhmi		int											null,			/*算费时间-MMDDHHMI*/
	calc_bdymd		int											null,			/*算费表底时间-YYYYMMDD*/	
	
	calc_zyz			decimal(12,2)						null,			/*算费时总表底*/
	calc_zyj			decimal(12,2)						null,			/*算费时尖表底*/
	calc_zyf			decimal(12,2)						null,			/*算费时峰表底*/
	calc_zyp			decimal(12,2)						null,			/*算费时平表底*/
	calc_zyg			decimal(12,2)						null,			/*算费时谷表底*/
	
	calc1_zyz			decimal(12,2)						null,			/*动力关联1-算费时总表底*/
	calc1_zyj			decimal(12,2)						null,			/*动力关联1-算费时尖表底*/
	calc1_zyf			decimal(12,2)						null,			/*动力关联1-算费时峰表底*/
	calc1_zyp			decimal(12,2)						null,			/*动力关联1-算费时平表底*/
	calc1_zyg			decimal(12,2)						null,			/*动力关联1-算费时谷表底*/
	
	calc2_zyz			decimal(12,2)						null,			/*动力关联2-算费时总表底*/
	calc2_zyj			decimal(12,2)						null,			/*动力关联2-算费时尖表底*/
	calc2_zyf			decimal(12,2)						null,			/*动力关联2-算费时峰表底*/
	calc2_zyp			decimal(12,2)						null,			/*动力关联2-算费时平表底*/
	calc2_zyg			decimal(12,2)						null,			/*动力关联2-算费时谷表底*/		
	
	now_remain		decimal(12,2)						null,			/*当前剩余*/
	now_remain2		decimal(12,2)						null,			/*当前剩余 本地模拟电表*/

	bj_bd					decimal(12,2)						null,			/*报警门限*/
	tz_bd					decimal(12,2)						null,			/*跳闸门限*/

--	cs_bd_state		tinyint									null,			/*保电状态  0:正常态 1:保电态*/

	cs_al1_state	tinyint									null,			/*报警1状态  0:正常状态 1:报警状态*/
	cs_al2_state	tinyint									null,			/*报警2状态  0:正常状态 1:报警状态*/
	cs_fhz_state	tinyint									null,			/*分合闸状态 0:分闸状态 1:合闸状态*/

	al1_mdhmi			int											null,			/*报警1状态改变时间-MMDDHHMI*/
	al2_mdhmi			int											null,			/*报警2状态改变时间-MMDDHHMI*/	
	fhz_mdhmi			int											null,			/*分合闸状态改变时间-MMDDHHMI*/

	fz_zyz				decimal(12,2)						null,			/*分闸时总表底*/
	fz1_zyz				decimal(12,2)						null,			/*动力关联1-分闸时总表底*/
	fz2_zyz				decimal(12,2)						null,			/*动力关联2-分闸时总表底*/

	yc_flag1			int											null,			/*异常标志1, 参数错误 */
	yc_flag2			int											null,			/*异常标志2(按位标志), 数据错误 0位:分闸后表字继续走 1位:表底飞走 2位:表底倒转 3位:长时间无数据 4位:长时间不缴费*/
	yc_flag3			int											null,			/*异常标志3, 备用 */

	hb_date				int											null,			/*上次换表日期*/
	hb_time				int											null,			/*上次换表时间*/	
	
	kh_date				int											null,			/*开户日期-YYYYMMDD*/
	xh_date				int											null,			/*销户日期-YYYYMMDD*/

	total_gdz			decimal(12,2)						null,			/*累计购电值*/

	jt_total_zbdl		decimal(12,2)					null,			/*阶梯追补累计用电量*/
	jt_total_dl			decimal(12,2)					null,			/*阶梯累计用电量*/
	jt_reset_ymd		int										null,			/*阶梯上次自动切换日期*/
	jt_reset_mdhmi	int										null,			/*阶梯切换执行时间*/

	fxdf_iall_money		decimal(12,2)				null, 		/*发行电费当月缴费总金额*/
	fxdf_iall_money2	decimal(12,2)				null, 		/*发行电费当月缴费总金额2*/
	fxdf_remain				decimal(12,2)				null,			/*发行电费后剩余金额  		fxdf_after_remain*/
	fxdf_remain2			decimal(12,2)				null,			/*发行电费后剩余金额  		fxdf_after_remain 本地模拟电表*/

	fxdf_ym					int										null,			/*发行电费数据日期YYYYMM*/
	fxdf_data_ymd		int										null,			/*发行电费数据日期-YYYYMMDD*/
	fxdf_calc_mdhmi	int										null,			/*发行电费算费时间-MMDDHHMI*/

	js_bc_ymd			int											null, 	  	/*结算补差日期YYYYMMDD*/
	
	reserve1			int								   		null,			/*seqNo  jackadd 孟加拉 20150304*/
	reserve2			int							   			null,			/*keyNo  jackadd 孟加拉 20150304*/
	reserve3			decimal(12,2)			   		null,	
	reserve4			decimal(12,2)			  	 	null,	
	
	CONSTRAINT mppay_state_index1 PRIMARY KEY (rtu_id,mp_id)
)
go
grant select on mppay_state to public
go


if (exists (select name from sysobjects where name='mppay_almstate'))
begin
	print '<<<<<<<Delete table 测量点预付费报警及控制状态表 on YDParaBen>>>>>>>'
	drop table mppay_almstate
end
go
print '-------Create table 测量点预付费报警及控制状态表 on YDParaBen-------'
create table mppay_almstate
(
	rtu_id						int       			not null,			/*终端编号*/	
	mp_id							smallint				not null,			/*总加组号*/
	
	qr_al1_1_state 		tinyint							null,			/*报警1-1确认状态(短信方式) 低6位(重试次数0~63) 高2位(0:初始态 1:成功 2:失败)*/
	qr_al1_2_state 		tinyint							null,			/*报警1-2确认状态(声音方式)*/
	qr_al1_3_state 		tinyint							null,			/*报警1-3确认状态(备用方式)*/	

	qr_al2_1_state 		tinyint							null,			/*报警2-1确认状态(短信方式) 低6位(重试次数0~63) 高2位(0:初始态 1:成功 2:失败)*/
	qr_al2_2_state 		tinyint							null,			/*报警2-2确认状态(声音方式)*/
	qr_al2_3_state 		tinyint							null,			/*报警2-3确认状态(备用方式)*/	

	qr_fhz_state			tinyint							null,			/*分合闸确认状态*/
	qr_fhz_rf1_state	tinyint							null,			/*分合闸确认状态(动力1)*/
	qr_fhz_rf2_state	tinyint							null,			/*分合闸确认状态(动力2)*/
	
	qr_fz_times				tinyint							null,			/*分闸次数*/
	qr_fz_rf1_times		tinyint							null,			/*分闸次数(动力1)*/
	qr_fz_rf2_times		tinyint							null,			/*分闸次数(动力2)*/
	
	qr_al1_1_mdhmi		int									null,			/*报警1-1确认状态(短信方式) 发送时间*/
	qr_al1_2_mdhmi		int									null,			/*报警1-2确认状态(声音方式) 发送时间*/
	qr_al1_3_mdhmi		int									null,			/*报警1-3确认状态(备用方式) 发送时间*/

	qr_al2_1_mdhmi		int									null,			/*报警2-1确认状态(短信方式) 发送时间*/
	qr_al2_2_mdhmi		int									null,			/*报警2-2确认状态(声音方式) 发送时间*/
	qr_al2_3_mdhmi		int									null,			/*报警2-3确认状态(备用方式) 发送时间*/

	qr_fhz_mdhmi			int									null,			/*分合闸确认状态 发送时间*/
	qr_fhz_rf1_mdhmi	int									null,			/*分合闸确认状态 发送时间(动力1)*/
	qr_fhz_rf2_mdhmi	int									null,			/*分合闸确认状态 发送时间(动力2)*/
			
	cg_fhz_mdhmi			int									null,			/*成功分合闸时间-MMDDHHMI*/
	cg_fhz_rf1_mdhmi	int									null,			/*成功分合闸时间-MMDDHHMI(动力1)*/
	cg_fhz_rf2_mdhmi	int									null,			/*成功分合闸时间-MMDDHHMI(动力2)*/
		
	qr_al1_1_uuid			int									null,			/*报警1-1确认状态(短信方式) UUID*/
	qr_al2_1_uuid			int									null,			/*报警2-1确认状态(短信方式) UUID*/

	out_info					varchar(64)					null,			/*信息输出*/

	CONSTRAINT mppay_almstate_index1 PRIMARY KEY (rtu_id,mp_id)
)
go
grant select on mppay_almstate to public
go


	--jackadd 孟加拉 20150304 start 
-- *************** 节假日定义表 ***************
if (exists (select name from sysobjects where name='holidaydef'))
begin
	print '<<<<<<<Delete table 节假日定义表 on YDParaBen>>>>>>>'
	drop table holidaydef
end
go

print '-------Create table 节假日定义表 on YDParaBen-------'
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
	print '<<<<<<<Delete table 节假日组表 on YDParaBen>>>>>>>'
	drop table holidaygroup
end
go

print '-------Create table 节假日组表 on YDParaBen-------'
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
	print '<<<<<<<Delete table 节假日组分项表表 on YDParaBen>>>>>>>'
	drop table holigitem
end
go

print '-------Create table 节假日组分项表表 on YDParaBen-------'
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
	
	
	
print  '-------Create table 费率计算方案表 on YDParaBen-------'
if exists (select * from sysobjects where name = 'yffratepara')
begin
	print '<<<<<<<Delete table 费率计算方案表 on YDParaBen>>>>>>>'
	drop table yffratepara
end
go

create table yffratepara
(
	id						smallint        	not null,					/*编号*/
	describe			varchar(64)    	  not null,					/*名称*/
	--jackadd 孟加拉 20150304
	rateid				smallint        	not null,					/*费率编号*/
	activdate			int									  null, 		    /*费率激活日期 月日*/
	
	fee_type			tinyint			default 0 null,					/*费率类型*/ /*0 种类A:居民区 3 类型D:非住宅（照明&用电） 4 类型E:商业&办公室*/
	
	/*单费率*/
	rated_z				decimal(12,6)			  	null,					/*总费率*/
	
	/*复费率中使用 平 峰 谷*/
	ratef_j			  decimal(12,6)   			null,					/*尖费率*/
	ratef_f				decimal(12,6)			  	null,					/*峰费率*/	
	ratef_p				decimal(12,6)  				null,					/*平费率*/
	ratef_g				decimal(12,6)   			null,					/*谷费率 非峰费率*/
	
	/*混合费率 不使用*/
	rateh_1			  decimal(12,6)   			null,					/*混合费率1*/
	rateh_2				decimal(12,6)			  	null,					/*混合费率2*/	
	rateh_3				decimal(12,6)  				null,					/*混合费率3*/
	rateh_4				decimal(12,6)   			null,					/*混合费率4*/	
	
	rateh_bl1			tinyint								null,					/*混合比例1*/	
	rateh_bl2			tinyint								null,					/*混合比例2*/		
	rateh_bl3			tinyint								null,					/*混合比例3*/		
	rateh_bl4			tinyint								null,					/*混合比例4*/		
	
	/*阶梯电价*/
	ratej_type 		tinyint								null,					/*阶梯电价类型*/ 	/*0 年度方案, 1月度方案*/
	ratej_num			tinyint								null,					/*阶梯电价数*/
	meterfee_type	tinyint								null,					/*电表费率类型  0单费率 1复费率 20120704*/
	meterfee_r		decimal(12,6)   			null,					/*电表执行电价*/	
	
	ratej_r1		  decimal(12,6)   			null,					/*阶梯费率1*/
	ratej_r2			decimal(12,6)			  	null,					/*阶梯费率2*/	
	ratej_r3			decimal(12,6)  				null,					/*阶梯费率3*/
	ratej_r4			decimal(12,6)   			null,					/*阶梯费率4*/
	--jackadd 孟加拉 20150304 start 
	ratej_r5			decimal(12,6)   			null,					/*阶梯费率5*/
	ratej_r6			decimal(12,6)   			null,					/*阶梯费率6*/
	ratej_r7			decimal(12,6)   			null,					/*阶梯费率7*/
	--jackadd 孟加拉 20150304 end
		
	ratej_td1			decimal(12,6)					null,					/*阶梯梯度值1*/
	ratej_td2			decimal(12,6)					null,					/*阶梯梯度值2*/
	ratej_td3			decimal(12,6)					null,					/*阶梯梯度值3*/
	--jackadd 孟加拉 20150304 start 
	ratej_td4			decimal(12,6)					null,					/*阶梯梯度值4*/
	ratej_td5			decimal(12,6)					null,					/*阶梯梯度值5*/
	ratej_td6			decimal(12,6)					null,					/*阶梯梯度值6*/
	--jackadd 孟加拉 20150304 end 
		
	/*混合阶梯电价 不使用*/
	ratehj_type 	tinyint								null,					/*阶梯电价类型*/ 	/*0 年度方案, 1月度方案*/
	ratehj_num		tinyint								null,					/*阶梯电价数*/
	
	meterfeehj_type	tinyint							null,					/*电表费率类型  0单费率 1复费率 20120704*/
	meterfeehj_r	decimal(12,6)   			null,					/*电表执行电价*/
	
	ratehj_hr1_r1		decimal(12,6)   		null,					/*第1比例电价-阶梯费率1*/
	ratehj_hr1_r2		decimal(12,6)			  null,					/*第1比例电价-阶梯费率2*/
	ratehj_hr1_r3		decimal(12,6)  			null,					/*第1比例电价-阶梯费率3*/
	ratehj_hr1_r4		decimal(12,6)   		null,					/*第1比例电价-阶梯费率4*/
		
	ratehj_hr1_td1	decimal(12,6)				null,					/*第1比例电价-阶梯梯度值1*/
	ratehj_hr1_td2	decimal(12,6)				null,					/*第1比例电价-阶梯梯度值2*/
	ratehj_hr1_td3	decimal(12,6)				null,					/*第1比例电价-阶梯梯度值3*/
	
	ratehj_hr2		decimal(12,6)   			null,					/*第2比例电价*/
	ratehj_hr3		decimal(12,6)   			null,					/*第3比例电价*/
	
	ratehj_bl1		tinyint								null,					/*混合比例1*/	
	ratehj_bl2		tinyint								null,					/*混合比例2*/	
	ratehj_bl3		tinyint								null,					/*混合比例3*/	
	
		
	money_limit		decimal(12,4)   			null,					/*囤积金额限值*/	
	
	inf_code1			varchar(32)						null,					/*接口编码1*/	
	inf_code2			varchar(32)						null,					/*接口编码2*/	
	inf_code3			varchar(32)						null,					/*接口编码3*/
	
	reserve1			decimal(12,2)					null,			
	reserve2			decimal(12,2)					null,			
	CONSTRAINT 		yffratepara_index1 PRIMARY KEY (id)
)
go

create unique  index yffratepara_index2
on yffratepara(id,rateid)
go

grant select on yffratepara to public
go


insert into yffratepara(id,describe,rateid,activdate,fee_type,rated_z,money_limit) values (1,'Single Tariff',1,20150101,3,0.52,10)
--insert into yffratepara(id,describe,rateid,activdate,fee_type,ratef_p,ratef_g,ratef_f,money_limit) values (2,'toutariff',5,20150101,4,9.00,7.22,11.85,10000)
--insert into yffratepara(id,describe,rateid,activdate,fee_type,ratej_type,ratej_num,ratej_r1,ratej_r2,ratej_r3,ratej_r4,ratej_r5,ratej_r6,ratej_td1,ratej_td2,ratej_td3,ratej_td4,ratej_td5,money_limit) values(3,'steptariff',1,20150101,0,1,6,3.33,4.73,4.83,4.93,7.98,9.38,75,200,300,400,600,10000)
go

print  '-------Create table 预付费报警方案表 on YDParaBen-------'
if exists (select * from sysobjects where name = 'yffalarmpara')
begin
	print '<<<<<<<Delete table 预付费报警方案表 on YDParaBen>>>>>>>'
	drop table yffalarmpara
end
go
create table yffalarmpara
(
	id						smallint        not null,				/*编号*/
	describe			varchar(64)    	not null,				/*名称*/

	type					tinyint		default 0 null,				/*报警方式 0:固定值方式 1:比例方式*/

	alarm1				int									null,				/*报警值1*/
	alarm2				int 								null,				/*报警值2*/
	alarm3				int 								null,				/*报警值3*/

	payalm_flag		tinyint							null,				/*缴费通知标志*/
	hzalm_flag		tinyint							null,				/*合闸通知标志*/
	tzalm_flag		tinyint							null,				/*跳闸通知标志*/

	dxalm_flag		tinyint							null,				/*短信告警标志*/
	syalm_flag		tinyint							null,				/*声音告警标志*/

	dxalmcgk_flag	tinyint							null,				/*短信告警发送成功后再控制标志*/
	syalmcgk_flag	tinyint							null,				/*声音告警发送成功后再控制标志*/

	reserve1			decimal(12,3)   		null,
	reserve2			varchar(40)      		null,
	CONSTRAINT yffalarm_index1 PRIMARY KEY (id)
)
go
grant select on yffalarmpara to public
go



if (exists (select name from sysobjects where name='wasteno'))
begin
	print '<<<<<<<Delete table 流水号表 on YDParaBen>>>>>>>'
	drop table wasteno
end
go
print '-------Create table 流水号表 on YDParaBen-------'
create table wasteno
(
	type				  tinyint    					not null,			/*类型*/	
	prefix			  varchar(40)      				null,

	ymd						int											null,
	hms						int											null,
	wasteidx			int											null,

	reserve1			varchar(40)      				null,
	reserve2			varchar(40)      				null,
	reserve3			varchar(40)      				null,		
	reserve4			varchar(40)      				null,		
	CONSTRAINT wasteno_index1 PRIMARY KEY (type)
)
go
grant select on wasteno to public
go

insert into wasteno (type, prefix, ymd, hms, wasteidx) values (0, '33132', 0, 0, 0)
insert into wasteno (type, prefix, ymd, hms, wasteidx) values (1, '43132', 0, 0, 0)




print '-------Create table 档案模板表 on YDParaBen-------'
create table yffdoc_template
(
	id					smallint			not null,		/*编号*/
	describe		varchar(64)	  not null,			  		/*描述*/
	app_type		tinyint				not null,			/*应用类型*/		
	tpldata 		image					null,			/*模板数据*/
	CONSTRAINT yffdoc_template_index1 PRIMARY KEY (id)	
)
go
grant select on yffdoc_template to public
go

